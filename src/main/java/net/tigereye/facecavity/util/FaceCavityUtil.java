package net.tigereye.facecavity.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.chestcavities.FaceCavityInventory;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;
import net.tigereye.facecavity.chestcavities.FaceCavityType;
import net.tigereye.facecavity.chestcavities.organs.OrganManager;
import net.tigereye.facecavity.chestcavities.organs.OrganData;
import net.tigereye.facecavity.crossmod.requiem.CCRequiem;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.listeners.*;
import net.tigereye.facecavity.registration.*;

import java.util.*;
import java.util.function.Consumer;

public class FaceCavityUtil {

    public static void addOrganScore(Identifier id, float value, Map<Identifier,Float> organScores){
        organScores.put(id,organScores.getOrDefault(id,0f)+value);
    }

    public static float applyBoneDefense(FaceCavityInstance cc, float damage){
        float boneDiff = (cc.getOrganScore(CCOrganScores.DEFENSE) - cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.DEFENSE))/4;
        return (float)(damage*Math.pow(1-FaceCavity.config.BONE_DEFENSE,boneDiff));
    }

    public static int applyBreathInWater(FaceCavityInstance cc, int oldAir, int newAir){
        //if your chest cavity is untouched or normal, we do nothing
        if(!cc.opened || ( cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.BREATH_CAPACITY) == cc.getOrganScore(CCOrganScores.BREATH_CAPACITY) &&
                cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.WATERBREATH) == cc.getOrganScore(CCOrganScores.WATERBREATH))){
            return newAir;
        }

        float airLoss = 1;
        //if you have waterbreath, you can breath underwater. Yay! This will overwrite any incoming air loss.
        float waterBreath = cc.getOrganScore(CCOrganScores.WATERBREATH);
        if(cc.owner.isSprinting()) {
            waterBreath /= 4;
        }
        if(waterBreath > 0){
            airLoss += (-2*waterBreath);
        }

        //if you don't (or you are still breath negative),
        //we check how well your lungs can hold oxygen
        if (airLoss > 0){
            if(oldAir == newAir){
                //this would indicate that resperation was a success
                airLoss = 0;
            }
            else {
                float capacity = cc.getOrganScore(CCOrganScores.BREATH_CAPACITY);
                airLoss *= (oldAir - newAir); //if you are downing at bonus speed, ok
                if (airLoss > 0) {
                    float lungRatio = 20f;
                    if (capacity != 0) {
                        lungRatio = Math.min(2 / capacity, 20f);
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

        if(!cc.opened|| ( cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.BREATH_RECOVERY) == cc.getOrganScore(CCOrganScores.BREATH_RECOVERY) &&
                cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.BREATH_CAPACITY) == cc.getOrganScore(CCOrganScores.BREATH_CAPACITY) &&
                cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.WATERBREATH) == cc.getOrganScore(CCOrganScores.WATERBREATH))){
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
        float breath = cc.getOrganScore(CCOrganScores.BREATH_RECOVERY);
        if(cc.owner.isSprinting()) {
            breath /= 4;
        }
        if(cc.owner.isTouchingWaterOrRain()){
            breath += cc.getOrganScore(CCOrganScores.WATERBREATH)/4;
        }
        if(breath > 0){
            airLoss += (-airGain * (breath) / 2);// + cc.lungRemainder;
        }

        //if you don't then unless you have the water breathing status effect you must hold your watery breath.
        //its also possible to not have enough breath to keep up with airLoss
        if (airLoss > 0) {
            //first, check if resperation cancels the sequence.
            int resperation = EnchantmentHelper.getRespiration(cc.owner);
            if (cc.owner.getRandom().nextInt(resperation + 1) != 0) {
                airLoss = 0;
            }
            else{
                //then, we apply our breath capacity
                float capacity = cc.getOrganScore(CCOrganScores.BREATH_CAPACITY);
                float breathRatio = 20f;
                if (capacity != 0) {
                    breathRatio = Math.min(2 / capacity, 20f);
                }
                airLoss = (airLoss * breathRatio) + cc.lungRemainder;
            }
        }
        else if(oldAir == cc.owner.getMaxAir()) {
            return oldAir;
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
        if(source == DamageSource.FALL){
            damage = applyLeapingToFallDamage(cc,damage);
        }
        if(source == DamageSource.FALL || source == DamageSource.FLY_INTO_WALL){
            damage = applyImpactResistant(cc,damage);
        }
        if(source.isFire()){
            damage = applyFireResistant(cc,damage);
        }
        return damage;
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

    public static float applyFireResistant(FaceCavityInstance cc, float damage){
        float fireproof = cc.getOrganScore(CCOrganScores.FIRE_RESISTANT);
        if(fireproof > 0){
            return (float)(damage*Math.pow(1-FaceCavity.config.FIREPROOF_DEFENSE,fireproof/4));
        }
        return damage;
    }

    public static float applyImpactResistant(FaceCavityInstance cc, float damage){
        float impactResistant = cc.getOrganScore(CCOrganScores.IMPACT_RESISTANT);
        if(impactResistant > 0){
            return (float)(damage*Math.pow(1-FaceCavity.config.IMPACT_DEFENSE,impactResistant/4));
        }
        return damage;
    }


    public static Float applyLeaping(FaceCavityInstance cc, float velocity) {
        float leaping = cc.getOrganScore(CCOrganScores.LEAPING);
        float defaultLeaping = cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.LEAPING);
        return velocity * Math.max(0,1+((leaping-defaultLeaping)*.25f));
    }

    public static float applyLeapingToFallDamage(FaceCavityInstance cc, float damage){
        float leapingDiff = cc.getOrganScore(CCOrganScores.LEAPING) - cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.LEAPING);
        if(leapingDiff > 0) {
            return Math.max(0, damage - (leapingDiff*leapingDiff/4));
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
        float metabolismDiff = cc.getOrganScore(CCOrganScores.METABOLISM)-cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.METABOLISM);
        if(metabolismDiff == 0){
            return foodStarvationTimer;
        }

        if(metabolismDiff > 0){
            cc.metabolismRemainder += metabolismDiff;
            foodStarvationTimer += (int)cc.metabolismRemainder;
        }
        else{// metabolismDiff < 0
            cc.metabolismRemainder += 1 - 1/((-metabolismDiff)+1);
            foodStarvationTimer -= (int)cc.metabolismRemainder;
        }
        cc.metabolismRemainder = cc.metabolismRemainder % 1;
        return foodStarvationTimer;
        //TODO: find a use for spleens for non-players
    }

    public static float applySwimSpeedInWater(FaceCavityInstance cc) {
        if(!cc.opened || !cc.owner.isTouchingWater()){return 1;}
        float speedDiff = cc.getOrganScore(CCOrganScores.SWIM_SPEED) - cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.SWIM_SPEED);
        if(speedDiff == 0){return 1;}
        else{
            return Math.max(0,1+(speedDiff*FaceCavity.config.SWIMSPEED_FACTOR/8));
        }

    }

    public static boolean attemptArrowDodging(FaceCavityInstance cc, DamageSource source){
        float dodge = cc.getOrganScore(CCOrganScores.ARROW_DODGING);
        if(dodge == 0){
            return false;
        }
        if(cc.owner.hasStatusEffect(CCStatusEffects.ARROW_DODGE_COOLDOWN)){
            return false;
        }
        if (!(source instanceof ProjectileDamageSource)) {
            return false;
        }
        if(!OrganUtil.teleportRandomly(cc.owner,FaceCavity.config.ARROW_DODGE_DISTANCE/dodge)){
            return false;
        }
        cc.owner.addStatusEffect(new StatusEffectInstance(CCStatusEffects.ARROW_DODGE_COOLDOWN, (int) (FaceCavity.config.ARROW_DODGE_COOLDOWN/dodge), 0, false, false, true));
        return true;
    }

    public static void clearForbiddenSlots(FaceCavityInstance cc) {
        try {
            cc.inventory.removeListener(cc);
        } catch(NullPointerException ignored){}
        for(int i = 0; i < cc.inventory.size();i++){
            if(cc.getFaceCavityType().isSlotForbidden(i)){
                cc.owner.dropStack(cc.inventory.removeStack(i));
            }
        }
        cc.inventory.addListener(cc);
    }

    public static void destroyOrgansWithKey(FaceCavityInstance cc, Identifier organ){
        for (int i = 0; i < cc.inventory.size(); i++)
        {
            ItemStack slot = cc.inventory.getStack(i);
            if (slot != null && slot != ItemStack.EMPTY)
            {
                OrganData organData = lookupOrgan(slot,cc.getFaceCavityType());
                if(organData != null && organData.organScores.containsKey(organ)){
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
            for (int i = 0; i < chestCavityType.getDefaultFaceCavity().size(); i++) {
                ItemStack itemStack = chestCavityType.getDefaultFaceCavity().getStack(i);
                if (itemStack != null && itemStack != ItemStack.EMPTY) {
                    Item slotitem = itemStack.getItem();

                    OrganData data = lookupOrgan(itemStack,chestCavityType);
                    if (data != null) {
                        data.organScores.forEach((key, value) ->
                                addOrganScore(key, value * Math.min(((float)itemStack.getCount()) / itemStack.getMaxCount(),1),organScores)
                        );
                    }

                }
            }
        }
        catch(IllegalStateException e){
            FaceCavity.LOGGER.warn(e.getMessage()+". Chest Cavity will attempt to calculate this default organ score later.");
            return false;
        }
        return true;
    }

    public static void drawOrgansFromPile(List<ItemStack> organPile, int rolls, Random random, List<ItemStack> loot){
        for (int i = 0; i < rolls; i++) {
            if(organPile.isEmpty()){
                break;
            }
            int roll = random.nextInt(organPile.size());
            int count = 1;
            ItemStack rolledItem = organPile.remove(roll).copy();
            if (rolledItem.getCount() > 1) {
                count += random.nextInt(rolledItem.getMaxCount());
            }
            rolledItem.setCount(count);
            loot.add(rolledItem);
        }
    }

    public static void dropUnboundOrgans(FaceCavityInstance cc) {
        if(FaceCavity.config.REQUIEM_INTEGRATION){
            if(Registry.ENTITY_TYPE.getId(cc.owner.getType()).compareTo(CCRequiem.PLAYER_SHELL_ID) == 0){
                return; //player shells shall not drop organs
            }
        }
        try {
            cc.inventory.removeListener(cc);
        } catch(NullPointerException ignored){}
        for(int i = 0; i < cc.inventory.size(); i++){
            ItemStack itemStack = cc.inventory.getStack(i);
            if(itemStack != null && itemStack != ItemStack.EMPTY) {
                int compatibility = getCompatibilityLevel(cc,itemStack);
                if(compatibility < 2){
                    cc.owner.dropStack(cc.inventory.removeStack(i));
                }
            }
        }
        cc.inventory.addListener(cc);
        evaluateFaceCavity(cc);
    }

    public static void evaluateFaceCavity(FaceCavityInstance cc) {
        Map<Identifier,Float> organScores = cc.getOrganScores();
        if(!cc.opened){
            organScores.clear();
            if(cc.getFaceCavityType().getDefaultOrganScores() != null) {
                organScores.putAll(cc.getFaceCavityType().getDefaultOrganScores());
            }
            else{

            }
        }
        else {
            cc.onHitListeners.clear();
            cc.getFaceCavityType().loadBaseOrganScores(organScores);

            for (int i = 0; i < cc.inventory.size(); i++) {
                ItemStack itemStack = cc.inventory.getStack(i);
                if (itemStack != null && itemStack != ItemStack.EMPTY) {
                    Item slotitem = itemStack.getItem();

                    OrganData data = lookupOrgan(itemStack,cc.getFaceCavityType());
                    if (data != null) {
                        data.organScores.forEach((key, value) ->
                                addOrganScore(key, value * Math.min(((float)itemStack.getCount()) / itemStack.getMaxCount(),1),organScores)
                        );
                        if(slotitem instanceof OrganOnHitListener){
                            cc.onHitListeners.add(new OrganOnHitContext(itemStack,(OrganOnHitListener)slotitem));
                        }
                        if (!data.pseudoOrgan) {
                            int compatibility = getCompatibilityLevel(cc,itemStack);
                            if(compatibility < 1){
                                addOrganScore(CCOrganScores.INCOMPATIBILITY, 1, organScores);
                            }
                        }
                    }

                }
            }
        }
        organUpdate(cc);
    }

    public static void forcefullyAddStack(FaceCavityInstance cc, ItemStack stack, int slot){
        if(!cc.inventory.canInsert(stack)) {
            if (!cc.inventory.canInsert(stack) && cc.owner.getEntityWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && cc.owner instanceof PlayerEntity) {
                if (!((PlayerEntity) cc.owner).getInventory().insertStack(stack)) {
                    cc.owner.dropStack(cc.inventory.removeStack(slot));
                }
            } else {
                cc.owner.dropStack(cc.inventory.removeStack(slot));
            }
        }
        cc.inventory.addStack(stack);
    }

    public static void generateFaceCavityIfOpened(FaceCavityInstance cc){
        if(cc.opened) {
            cc.inventory.readTags(cc.getFaceCavityType().getDefaultFaceCavity().getTags());
            cc.getFaceCavityType().setOrganCompatibility(cc);
        }
    }

    public static int getCompatibilityLevel(FaceCavityInstance cc, ItemStack itemStack){
        if(itemStack != null && itemStack != ItemStack.EMPTY) {
            if(EnchantmentHelper.getLevel(CCEnchantments.MALPRACTICE,itemStack)>0){
                return 0;
            }
            int oNegative = EnchantmentHelper.getLevel(CCEnchantments.O_NEGATIVE,itemStack);
            int ownership = 0;
            NbtCompound tag = itemStack.getNbt();
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

    public static void insertWelfareOrgans(FaceCavityInstance cc){
        //urgently essential organs are: heart, spine, lung, and just a touch of strength
        if(cc.getOrganScore(CCOrganScores.HEALTH) == 0){
            forcefullyAddStack(cc, new ItemStack(CCItems.ROTTEN_HEART),4);
        }
        if(cc.getOrganScore(CCOrganScores.BREATH_RECOVERY) == 0){
            forcefullyAddStack(cc, new ItemStack(CCItems.ROTTEN_LUNG),3);
        }
        if(cc.getOrganScore(CCOrganScores.NERVES) == 0){
            forcefullyAddStack(cc, new ItemStack(CCItems.ROTTEN_SPINE),13);
        }
        if(cc.getOrganScore(CCOrganScores.STRENGTH) == 0){
            forcefullyAddStack(cc, new ItemStack(Items.ROTTEN_FLESH,16),0);
        }
    }

    public static boolean isHydroPhobicOrAllergic(LivingEntity entity){
        Optional<FaceCavityEntity> optional = FaceCavityEntity.of(entity);
        if(optional.isPresent()){
            FaceCavityInstance cc = optional.get().getFaceCavityInstance();
            return (cc.getOrganScore(CCOrganScores.HYDROALLERGENIC) > 0) || (cc.getOrganScore(CCOrganScores.HYDROPHOBIA) > 0);
        }
        return false;
    }

    protected static OrganData lookupOrgan(ItemStack itemStack, FaceCavityType cct){
        OrganData organData = cct.catchExceptionalOrgan(itemStack);
        if(organData != null){ //check for exceptional organs
            return organData;
        }
        else if(OrganManager.hasEntry(itemStack.getItem())){ //check for normal organs
            return OrganManager.getEntry(itemStack.getItem());
        }
        else{ //check for tag organs
            for (Tag<Item> itemTag:
                    CCTagOrgans.tagMap.keySet()) {
                if(itemStack.isIn(itemTag)){
                    organData = new OrganData();
                    organData.pseudoOrgan = true;
                    organData.organScores = CCTagOrgans.tagMap.get(itemTag);
                    return organData;
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
            NetworkUtil.SendS2CFaceCavityUpdatePacket(cc,cc.updatePacket);
        }/*
        if(CCRequiem.REQUIEM_ACTIVE) {
            if (cc.owner instanceof Possessable && ((Possessable) cc.owner).isBeingPossessed()) {
                Optional<FaceCavityEntity> option = FaceCavityEntity.of(((Possessable) cc.owner).getPossessor());
                if(option.isPresent()){
                    FaceCavityInstance possessorCC = option.get().getFaceCavityInstance();
                    openFaceCavity(possessorCC);
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
            generateFaceCavityIfOpened(cc);
            cc.inventory.addListener(cc);
        }
        return cc.inventory;
    }

    public static void organUpdate(FaceCavityInstance cc){
        Map<Identifier,Float> organScores = cc.getOrganScores();
        if(!cc.oldOrganScores.equals(organScores))
        {
            if(FaceCavity.DEBUG_MODE && cc.owner instanceof PlayerEntity) {
                FaceCavityUtil.outputOrganScoresString(System.out::println,cc);
            }
            OrganUpdateCallback.EVENT.invoker().onOrganUpdate(cc.owner, cc);
            cc.oldOrganScores.clear();
            cc.oldOrganScores.putAll(organScores);
            NetworkUtil.SendS2CFaceCavityUpdatePacket(cc);
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

    public static void splashHydrophobicWithWater(PotionEntity splash){
        Box box = splash.getBoundingBox().expand(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = splash.world.getEntitiesByClass(LivingEntity.class, box, FaceCavityUtil::isHydroPhobicOrAllergic);
        if (!list.isEmpty()) {
            for(LivingEntity livingEntity:list) {
                double d = splash.squaredDistanceTo(livingEntity);
                if(d < 16.0D) {
                    Optional<FaceCavityEntity> optional = FaceCavityEntity.of(livingEntity);
                    if (optional.isPresent()) {
                        FaceCavityInstance cc = optional.get().getFaceCavityInstance();
                        float allergy = cc.getOrganScore(CCOrganScores.HYDROALLERGENIC);
                        float phobia = cc.getOrganScore(CCOrganScores.HYDROPHOBIA);
                        if (allergy > 0) {
                            livingEntity.damage(DamageSource.magic(livingEntity, splash.getOwner()), allergy/26);
                        }
                        if (phobia > 0) {
                            OrganUtil.teleportRandomly(livingEntity,phobia*32);
                        }
                    }
                }
            }
        }
    }
}
