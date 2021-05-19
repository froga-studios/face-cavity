package io.github.frogastudios.facecavity.util;

import io.github.frogastudios.facecavity.FaceCavity;

import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import io.github.frogastudios.facecavity.interfaces.FaceCavityEntity;
import io.github.frogastudios.facecavity.listeners.*;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.items.FaceCavityOrgan;
import io.github.frogastudios.facecavity.registration.FCEnchantments;
import io.github.frogastudios.facecavity.registration.FCOtherOrgans;
import net.tigereye.chestcavity.items.Organ;
import net.tigereye.chestcavity.registration.CCStatusEffects;

import java.util.*;
import java.util.function.Consumer;

public class FaceCavityUtil {

    public static void addOrganScore(Identifier id, float value, Map<Identifier,Float> organScores){
        organScores.put(id,organScores.getOrDefault(id,0f)+value);
    }

    public static float applyBoneDefense(FaceCavityInstance cc, float damage){
        float boneDiff = (cc.getOrganScore(FCOrganScores.DEFENSE) - cc.getChestCavityType().getDefaultOrganScore(FCOrganScores.DEFENSE))/4;
        return (float)(damage*Math.pow(1- FaceCavity.config.BONE_DEFENSE,boneDiff));
    }

    public static int applyBreathInWater(FaceCavityInstance cc, int oldAir, int newAir){
        //if your chest cavity is untouched or normal, we do nothing
        if(!cc.opened || ( cc.getChestCavityType().getDefaultOrganScore(FCOrganScores.BREATH) == cc.getOrganScore(FCOrganScores.BREATH) &&
                cc.getChestCavityType().getDefaultOrganScore(FCOrganScores.WATERBREATH) == cc.getOrganScore(FCOrganScores.WATERBREATH))){
            return newAir;
        }

        float airLoss = 1;
        //if you have waterbreath, you can breath underwater. Yay! This will overwrite any incoming air loss.
        float waterBreath = cc.getOrganScore(FCOrganScores.WATERBREATH);
        if(waterBreath > 0){
            airLoss += (-2*waterBreath)+cc.lungRemainder;
        }

        //if you don't (or you are still breath negative),
        //we check how well your lungs can hold oxygen
        if (airLoss > 0){
            if(oldAir == newAir){
                //this would indicate that resperation was a success
                airLoss = 0;
            }
            else {
                float breath = cc.getOrganScore(FCOrganScores.BREATH);
                airLoss *= (oldAir - newAir); //if you are downing at bonus speed, ok
                if (airLoss > 0) {
                    float lungRatio = 20f;
                    if (breath != 0) {
                        lungRatio = Math.min(2 / breath, 20f);
                    }
                    airLoss = (airLoss * lungRatio) + cc.lungRemainder;
                }
            }
        }

        cc.lungRemainder = airLoss % 1;
        int airResult = Math.min(oldAir - ((int) airLoss),cc.owner.getMaxAir());
        //I don't trust vanilla to do this job right, so I will choke you myself
        if (airResult <= -20) {
            airResult = 0;
            cc.lungRemainder = 0;
            cc.owner.damage(DamageSource.DROWN, 2.0F);
        }
        return airResult;
    }

    public static int applyBreathOnLand(FaceCavityInstance cc, int oldAir, int airGain){
        //we have to recreate breath mechanics here I'm afraid
        //if your chest cavity is untouched or normal, we do nothing

        if(!cc.opened|| ( cc.getChestCavityType().getDefaultOrganScore(FCOrganScores.BREATH) == cc.getOrganScore(FCOrganScores.BREATH) &&
                cc.getChestCavityType().getDefaultOrganScore(FCOrganScores.WATERBREATH) == cc.getOrganScore(FCOrganScores.WATERBREATH))){
            return oldAir;
        }

        float airLoss;
        if(cc.owner.hasStatusEffect(StatusEffects.WATER_BREATHING) || cc.owner.hasStatusEffect(StatusEffects.CONDUIT_POWER)){
            airLoss = 0;
        }
        else{airLoss = 1;}


        //if you have breath, you can breath on land. Yay!
        //if in contact with water or rain apply on quarter your water breath as well
        //(so 2 gills can survive in humid conditions)
        float breath = cc.getOrganScore(FCOrganScores.BREATH);
        if(cc.owner.isTouchingWaterOrRain()){
            breath += cc.getOrganScore(FCOrganScores.WATERBREATH)/4;
        }
        if(breath > 0){
            airLoss += (-airGain * (breath) / 2) + cc.lungRemainder;
        }

        //if you don't then unless you have the water breathing status effect you must hold your watery breath.
        if (airLoss > 0) {
            //first, check if resperation cancels the sequence.
            int resperation = EnchantmentHelper.getRespiration(cc.owner);
            if (cc.owner.getRandom().nextInt(resperation + 1) != 0) {
                airLoss = 0;
            }
            else{
                //then, we apply our beath capacity
                float waterbreath = cc.getOrganScore(FCOrganScores.WATERBREATH);
                float gillRatio = 20f;
                if (waterbreath != 0) {
                    gillRatio = Math.min(2 / waterbreath, 20f);
                }
                airLoss = airLoss * gillRatio + cc.lungRemainder;
            }
        }
        else{
            if(oldAir == cc.owner.getMaxAir()){
                return oldAir;
            }
        }

        cc.lungRemainder = airLoss % 1;
        //we finally undo the air gained in vanilla while calculating final results
        int airResult = Math.min(oldAir - ((int) airLoss) - airGain,cc.owner.getMaxAir());
        //I don't trust vanilla to do this job right, so I will choke you myself
        if (airResult <= -20) {
            airResult = 0;
            cc.lungRemainder = 0;
            cc.owner.damage(DamageSource.DROWN, 2.0F);
        }
        return airResult;
    }

    public static float applyDefenses(FaceCavityInstance cc, DamageSource source, float damage){
        if(!cc.opened){
            return damage;
        }
        if(attemptArrowDodging(cc,source)){
            return 0;
        }
        if(!source.bypassesArmor()) {
            damage = applyBoneDefense(cc,damage);
        }
        if(source == DamageSource.FALL || source == DamageSource.FLY_INTO_WALL){
            damage = applyImpactResistant(cc,damage);
        }
        if(source.isFire()){
            damage = applyFireResistant(cc,damage);
        }
        return damage;
    }

    public static float applyFireResistant(FaceCavityInstance cc, float damage){
        float fireproof = cc.getOrganScore(FCOrganScores.FIRE_RESISTANT);
        if(fireproof > 0){
            return (float)(damage*Math.pow(1- FaceCavity.config.FIREPROOF_DEFENSE,fireproof/4));
        }
        return damage;
    }

    public static float applyImpactResistant(FaceCavityInstance cc, float damage){
        float impactResistant = cc.getOrganScore(FCOrganScores.IMPACT_RESISTANT);
        if(impactResistant > 0){
            return (float)(damage*Math.pow(1- FaceCavity.config.IMPACT_DEFENSE,impactResistant/4));
        }
        return damage;
    }

    public static float applyNutrition(FaceCavityInstance cc, float nutrition, float saturation){
        if(nutrition == 4){
            return saturation;
        }
        if(nutrition < 0){
            cc.owner.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER,(int)(saturation*nutrition*800)));
            return 0;
        }
        return saturation*nutrition/4;
        //TODO: find a use for intestines for non-players
    }

    public static int applySpleenMetabolism(FaceCavityInstance cc, int foodStarvationTimer){
        if(!cc.opened){
            return foodStarvationTimer;
        }
        cc.spleenTimer++;
        if(cc.spleenTimer >= 2){
            foodStarvationTimer += cc.getOrganScore(FCOrganScores.METABOLISM) - 1;
            cc.spleenTimer = 0;
        }
        return foodStarvationTimer;
        //TODO: find a use for spleens for non-players
    }

    public static int applyDigestion(FaceCavityInstance cc, float digestion, int hunger){
        if(digestion == 1){
            return hunger;
        }
        if(digestion < 0){
            cc.owner.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,(int)(-hunger*digestion*400)));
            return 0;
        }
        //sadly, in order to get saturation at all we must grant at least half a haunch of food, unless we embrace incompatibility
        return Math.max((int)(hunger*digestion),1);
        //TODO: find a use for stomachs for non-players
    }

    public static boolean attemptArrowDodging(FaceCavityInstance cc, DamageSource source){
        float dodge = cc.getOrganScore(FCOrganScores.ARROW_DODGING);
        if(dodge == 0){
            return false;
        }
        if(cc.owner.hasStatusEffect(CCStatusEffects.ARROW_DODGE_COOLDOWN)){
            return false;
        }
        if (!(source instanceof ProjectileDamageSource)) {
            return false;
        }
        if(!OrganUtil.teleportRandomly(cc.owner, FaceCavity.config.ARROW_DODGE_DISTANCE/dodge)){
            return false;
        }
        cc.owner.addStatusEffect(new StatusEffectInstance(CCStatusEffects.ARROW_DODGE_COOLDOWN, (int) (FaceCavity.config.ARROW_DODGE_COOLDOWN/dodge), 0, false, false, true));
        return true;
    }

    public static void destroyOrgansWithKey(FaceCavityInstance cc, Identifier organ){
        for (int i = 0; i < cc.inventory.size(); i++)
        {
            ItemStack slot = cc.inventory.getStack(i);
            if (slot != null && slot != ItemStack.EMPTY)
            {
                Map<Identifier,Float> organScores = lookupOrganScore(slot,cc.owner);
                if(organScores != null && organScores.containsKey(organ)){
                    cc.inventory.removeStack(i);
                }
            }
        }
        cc.inventory.markDirty();
    }

    public static boolean determineDefaultOrganScores(FaceCavityType chestCavityType) {
        Map<Identifier,Float> organScores = chestCavityType.getDefaultOrganScores();
        chestCavityType.loadBaseOrganScores(organScores);
        try {
            for (int i = 0; i < chestCavityType.getDefaultChestCavity().size(); i++) {
                ItemStack itemStack = chestCavityType.getDefaultChestCavity().getStack(i);
                if (itemStack != null && itemStack != ItemStack.EMPTY) {
                    Item slotitem = itemStack.getItem();
                    if (!chestCavityType.catchExceptionalOrgan(itemStack, organScores)) {//if a manager handles an organ in a special way, this lets it skip the normal evaluation.
                        Map<Identifier, Float> organMap = lookupOrganScore(itemStack, null);
                        if (organMap != null) {
                            organMap.forEach((key, value) ->
                                    addOrganScore(key, value * Math.min(((float) itemStack.getCount()) / itemStack.getMaxCount(), 1), organScores));
                        }
                    }
                    CompoundTag tag = itemStack.getTag();
                }
            }
        }
        catch(IllegalStateException e){
            FaceCavity.LOGGER.warn(e.getMessage()+". Chest Cavity will attempt to calculate this default organ score later.");
            return false;
        }
        return true;
    }

    public static int getCompatibilityLevel(FaceCavityInstance cc, ItemStack itemStack){
        if(itemStack != null && itemStack != ItemStack.EMPTY) {
            if(EnchantmentHelper.getLevel(FCEnchantments.MALPRACTICE,itemStack)>0){
                return 0;
            }
            int oNegative = EnchantmentHelper.getLevel(FCEnchantments.O_NEGATIVE,itemStack);
            int ownership = 0;
            CompoundTag tag = itemStack.getTag();
            if (tag != null && tag.contains(FaceCavity.COMPATIBILITY_TAG.toString())) {
                tag = tag.getCompound(FaceCavity.COMPATIBILITY_TAG.toString());
                if (tag.getUuid("owner").equals(cc.compatibility_id)) {
                    ownership = 2;
                }
            } else {
                ownership = 1;
            }
            return Math.max(oNegative,ownership);
        }
        return 1;
    }


    public static void evaluateChestCavity(FaceCavityInstance cc) {
        Map<Identifier,Float> organScores = cc.getOrganScores();
        if(!cc.opened){
            organScores.clear();
            organScores.putAll(cc.getChestCavityType().getDefaultOrganScores());
        }
        else {
            cc.onHitListeners.clear();
            cc.getChestCavityType().loadBaseOrganScores(organScores);

            for (int i = 0; i < cc.inventory.size(); i++) {
                ItemStack itemStack = cc.inventory.getStack(i);
                if (itemStack != null && itemStack != ItemStack.EMPTY) {
                    Item slotitem = itemStack.getItem();
                    if (!cc.getChestCavityType().catchExceptionalOrgan(itemStack,organScores)) {//if a manager chooses to handle some organ in a special way, this lets it skip the normal evaluation.
                        Map<Identifier, Float> organMap = lookupOrganScore(itemStack,cc.owner);
                        if (organMap != null) {
                            organMap.forEach((key, value) ->
                                    addOrganScore(key, value * Math.min(((float)itemStack.getCount()) / itemStack.getMaxCount(),1),organScores));
                        }
                        if(slotitem instanceof OrganOnHitListener){
                            cc.onHitListeners.add(new OrganOnHitContext(itemStack,(OrganOnHitListener)slotitem));
                        }
                    }
                    if (slotitem instanceof Organ) {
                        int compatibility = getCompatibilityLevel(cc,itemStack);
                        if(compatibility < 1){
                            addOrganScore(FCOrganScores.INCOMPATIBILITY, 1, organScores);
                        }
                    }
                }
            }
        }
        organUpdate(cc);
    }

    public static void generateChestCavityIfOpened(FaceCavityInstance cc){
        if(cc.opened) {
            cc.inventory.readTags(cc.getChestCavityType().getDefaultChestCavity().getTags());
            cc.getChestCavityType().setOrganCompatibility(cc);
        }
    }

    public static boolean isHydroPhobicOrAllergic(LivingEntity entity){
        Optional<FaceCavityEntity> optional = FaceCavityEntity.of(entity);
        if(optional.isPresent()){
            FaceCavityInstance cc = optional.get().getFaceCavityInstance();
            return (cc.getOrganScore(FCOrganScores.HYDROALLERGENIC) > 0) || (cc.getOrganScore(FCOrganScores.HYDROPHOBIA) > 0);
        }
        return false;
    }

    protected static Map<Identifier,Float> lookupOrganScore(ItemStack itemStack, LivingEntity owner){
        Item item = itemStack.getItem();
        if(item instanceof FaceCavityOrgan){
            if(owner != null) {
                return ((FaceCavityOrgan) item).getOrganQualityMap(itemStack, owner);
            }
            else{
                return ((FaceCavityOrgan) item).getOrganQualityMap(itemStack);
            }
        }
        else if(FCOtherOrgans.map.containsKey(item)){
            return FCOtherOrgans.map.get(item);
        }
        else{
            for (Tag<Item> itemTag:
                    FCOtherOrgans.tagMap.keySet()) {
                if(item.isIn(itemTag)){
                    return FCOtherOrgans.tagMap.get(itemTag);
                }
            }
        }
        return null;
    }

    public static StatusEffectInstance onAddStatusEffect(FaceCavityInstance cc, StatusEffectInstance effect) {
        return OrganAddStatusEffectCallback.EVENT.invoker().onAddStatusEffect(cc.owner, cc,effect);
    }

    public static float onHit(FaceCavityInstance cc, DamageSource source, LivingEntity target, float damage){
        if(cc.opened) {
            //this is for individual organs
            for (OrganOnHitContext e:
                    cc.onHitListeners) {
                damage = e.listener.onHit(source,cc.owner,target,cc,e.organ,damage);
            }
            //this is for organ scores
            //OrganOnHitCallback.EVENT.invoker().onHit(source,cc.owner,target,cc,damage);
            organUpdate(cc);
        }
        return damage;
    }

    public static void onTick(FaceCavityInstance cc){
        if(cc.updatePacket != null){
            NetworkUtil.SendS2CChestCavityUpdatePacket(cc,cc.updatePacket);
        }/*
        if(CCRequiem.REQUIEM_ACTIVE) {
            if (cc.owner instanceof Possessable && ((Possessable) cc.owner).isBeingPossessed()) {
                Optional<FaceCavityEntity> option = FaceCavityEntity.of(((Possessable) cc.owner).getPossessor());
                if(option.isPresent()){
                    FaceCavityInstance possessorCC = option.get().getChestCavityInstance();
                    openChestCavity(possessorCC);
                    possessorCC.organScores.clear();
                    possessorCC.organScores.putAll(cc.organScores);
                }
            }
        }*/
        if(cc.opened) {
            OrganTickCallback.EVENT.invoker().onOrganTick(cc.owner, cc);
            organUpdate(cc);
        }
    }

    public static FaceCavityInventory openFaceCavity(FaceCavityInstance cc){
        if(!cc.opened) {
            try {
                cc.inventory.removeListener(cc);
            }
            catch(NullPointerException ignored){}
            cc.opened = true;
            generateChestCavityIfOpened(cc);
            cc.inventory.addListener(cc);
        }
        return cc.inventory;
    }


    public static void organUpdate(FaceCavityInstance cc){
        Map<Identifier,Float> organScores = cc.getOrganScores();
        if(!cc.oldOrganScores.equals(organScores))
        {
            if(FaceCavity.DEBUG_MODE && cc.owner instanceof PlayerEntity) {
                io.github.frogastudios.facecavity.util.FaceCavityUtil.outputOrganScoresString(System.out::println,cc);
            }
            OrganUpdateCallback.EVENT.invoker().onOrganUpdate(cc.owner, cc);
            cc.oldOrganScores.clear();
            cc.oldOrganScores.putAll(organScores);
            NetworkUtil.SendS2CChestCavityUpdatePacket(cc);
        }
    }

    public static void outputOrganScoresString(Consumer<String> output, FaceCavityInstance cc){
        try {
            Text name = cc.owner.getDisplayName();
            output.accept("[Chest Cavity] Displaying " + name.getString() +"'s organ scores:");
        }
        catch(Exception e){
            output.accept("[Chest Cavity] Displaying organ scores:");
        }
        cc.getOrganScores().forEach((key, value) ->
                output.accept(key.getPath() + ": " + value + " "));
    }

    public static float applySwimSpeedInWater(FaceCavityInstance cc) {
        if(!cc.opened || !cc.owner.isTouchingWater()){return 1;}
        float speedDiff = cc.getOrganScore(FCOrganScores.SWIM_SPEED) - cc.getChestCavityType().getDefaultOrganScore(FCOrganScores.SWIM_SPEED);
        if(speedDiff == 0){return 1;}
        else{
            return Math.max(0,1+(speedDiff* FaceCavity.config.SWIMSPEED_FACTOR/8));
        }

    }

    public static void clearForbiddenSlots(FaceCavityInstance cc) {
        try {
            cc.inventory.removeListener(cc);
        } catch(NullPointerException ignored){}
        for(int i = 0; i < cc.inventory.size();i++){
            if(cc.getChestCavityType().isSlotForbidden(i)){
                cc.owner.dropStack(cc.inventory.removeStack(i));
            }
        }
        cc.inventory.addListener(cc);
    }

    public static List<ItemStack> drawOrgansFromPile(List<Item> organPile, int rolls, Random random){
        List<ItemStack> loot = new ArrayList<>();
        drawOrgansFromPile(organPile,rolls,random,loot);
        return loot;
    }
    public static void drawOrgansFromPile(List<Item> organPile, int rolls, Random random, List<ItemStack> loot){
        for (int i = 0; i < rolls; i++) {
            if(organPile.isEmpty()){
                break;
            }
            int roll = random.nextInt(organPile.size());
            int count = 1;
            Item rolledItem = organPile.get(roll);
            if (rolledItem.getMaxCount() > 1) {
                count += random.nextInt(rolledItem.getMaxCount());
            }
            loot.add(new ItemStack(organPile.remove(roll), count));
        }
    }
}
