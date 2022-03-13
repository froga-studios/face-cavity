package net.tigereye.facecavity.chestcavities.types;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.chestcavities.FaceCavityInventory;
import net.tigereye.facecavity.chestcavities.FaceCavityType;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;
import net.tigereye.facecavity.chestcavities.organs.OrganData;
import net.tigereye.facecavity.chestcavities.organs.OrganManager;
import net.tigereye.facecavity.registration.CCOrganScores;
import net.tigereye.facecavity.util.FaceCavityUtil;

import java.util.*;

public class DefaultFaceCavityType implements FaceCavityType {

    private Map<Identifier,Float> defaultOrganScores = null;
    private FaceCavityInventory defaultFaceCavity = new FaceCavityInventory();
    private Map<Identifier,Float> baseOrganScores = new HashMap<>();
    private Map<Ingredient,Map<Identifier,Float>> exceptionalOrganList = new HashMap<>();
    private List<ItemStack> droppableOrgans = new LinkedList<>();
    private List<Integer> forbiddenSlots = new ArrayList<>();
    private boolean bossFaceCavity = false;
    private boolean playerFaceCavity = false;

    public DefaultFaceCavityType(){
        prepareDefaultFaceCavity();
    }

    private void prepareDefaultFaceCavity(){
        for(int i = 0; i < defaultFaceCavity.size(); i++){
            defaultFaceCavity.setStack(i,new ItemStack(Items.DIRT,64));
        }
    }



    @Override
    public Map<Identifier,Float> getDefaultOrganScores(){
        if(defaultOrganScores == null){
            defaultOrganScores = new HashMap<>();
            if(!FaceCavityUtil.determineDefaultOrganScores(this)){
                defaultOrganScores = null;
            }
        }
        return defaultOrganScores;
    }
    @Override
    public float getDefaultOrganScore(Identifier id){return getDefaultOrganScores().getOrDefault(id,0f);}
    @Override
    public FaceCavityInventory getDefaultFaceCavity(){return defaultFaceCavity;}
    public void setDefaultFaceCavity(FaceCavityInventory inv){defaultFaceCavity = inv;}

    public Map<Identifier,Float> getBaseOrganScores(){return baseOrganScores;}
    public float getBaseOrganScore(Identifier id){return getBaseOrganScores().getOrDefault(id,0f);}
    public void setBaseOrganScores(Map<Identifier,Float> organScores){baseOrganScores = organScores;}
    public void setBaseOrganScore(Identifier id, float score){baseOrganScores.put(id,score);}

    public Map<Ingredient,Map<Identifier,Float>> getExceptionalOrganList(){return exceptionalOrganList;}
    public Map<Identifier,Float> getExceptionalOrganScore(ItemStack itemStack){
        for(Ingredient ingredient:
                getExceptionalOrganList().keySet()){
            if(ingredient.test(itemStack)){
                return getExceptionalOrganList().get(ingredient);
            }
        }
        return null;
    }
    public void setExceptionalOrganList(Map<Ingredient,Map<Identifier,Float>> list){exceptionalOrganList = list;}
    public void setExceptionalOrgan(Ingredient ingredient,Map<Identifier,Float> scores){exceptionalOrganList.put(ingredient,scores);}

    public List<ItemStack> getDroppableOrgans(){
        if(droppableOrgans == null){
            deriveDroppableOrgans();
        }
        return droppableOrgans;}
    public void setDroppableOrgans(List<ItemStack> list){droppableOrgans = list;}
    private void deriveDroppableOrgans() {
        droppableOrgans = new LinkedList<>();
        for(int i = 0; i < defaultFaceCavity.size(); i++){
            ItemStack stack = defaultFaceCavity.getStack(i);
            if(OrganManager.isTrueOrgan(stack.getItem())){
                droppableOrgans.add(stack);
            }
        }
    }

    public List<Integer> getForbiddenSlots(){return forbiddenSlots;}
    public void setForbiddenSlots(List<Integer> list){forbiddenSlots = list;}
    public void forbidSlot(int slot){forbiddenSlots.add(slot);}
    public void allowSlot(int slot){
        int index = forbiddenSlots.indexOf(slot);
        if(index != -1){
            forbiddenSlots.remove(index);
        }
    }
    @Override
    public boolean isSlotForbidden(int index){
        return forbiddenSlots.contains(index);
    }

    public boolean isBossFaceCavity(){return bossFaceCavity;}
    public void setBossFaceCavity(boolean bool){
        bossFaceCavity = bool;}

    public boolean isPlayerFaceCavity(){return playerFaceCavity;}
    public void setPlayerFaceCavity(boolean bool){playerFaceCavity = bool;}

    @Override
    public void fillFaceCavityInventory(FaceCavityInventory chestCavity) {
        chestCavity.clear();
        for(int i = 0; i < chestCavity.size(); i++){
            chestCavity.setStack(i,defaultFaceCavity.getStack(i));
        }
    }

    @Override
    public void loadBaseOrganScores(Map<Identifier, Float> organScores){
        organScores.clear();
    }

    @Override
    public OrganData catchExceptionalOrgan(ItemStack slot){
        Map<Identifier,Float> organMap = getExceptionalOrganScore(slot);
        if(organMap != null){
            OrganData organData = new OrganData();
            organData.organScores = organMap;
            organData.pseudoOrgan = true;
            return organData;
        }
        return null;
    }

    @Override
    public List<ItemStack> generateLootDrops(Random random, int looting) {
        List<ItemStack> loot = new ArrayList<>();
        if(playerFaceCavity){
            return loot;
        }
        if(bossFaceCavity){
            generateGuaranteedOrganDrops(random,looting,loot);
            return loot;
        }
        if(random.nextFloat() < FaceCavity.config.UNIVERSAL_DONOR_RATE + (FaceCavity.config.ORGAN_BUNDLE_LOOTING_BOOST*looting)) {
            generateRareOrganDrops(random,looting,loot);
        }
        return loot;
    }
    public void generateRareOrganDrops(Random random, int looting, List<ItemStack> loot){
        LinkedList<ItemStack> organPile = new LinkedList<>(getDroppableOrgans());
        int rolls = 1 + random.nextInt(3) + random.nextInt(3);
        FaceCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);

    }
    public void generateGuaranteedOrganDrops(Random random, int looting, List<ItemStack> loot){
        LinkedList<ItemStack> organPile = new LinkedList<>(getDroppableOrgans());
        int rolls = 3 + random.nextInt(2+looting) + random.nextInt(2+looting);
        FaceCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);
    }

    @Override
    public void setOrganCompatibility(FaceCavityInstance instance){
        FaceCavityInventory chestCavity = instance.inventory;
        //first, make all organs personal
        for(int i = 0; i < chestCavity.size();i++){
            ItemStack itemStack = chestCavity.getStack(i);
            if(itemStack != null && itemStack != itemStack.EMPTY){
                NbtCompound tag = new NbtCompound();
                tag.putUuid("owner",instance.compatibility_id);
                tag.putString("name",instance.owner.getDisplayName().getString());
                itemStack.setSubNbt(FaceCavity.COMPATIBILITY_TAG.toString(),tag);
            }
        }
    }

    @Override
    public float getHeartBleedCap(){
        if(bossFaceCavity){
            return 5;
        }
        return Float.MAX_VALUE;
    }

    @Override
    public boolean isOpenable(FaceCavityInstance instance){
        boolean weakEnough = instance.owner.getHealth() <= FaceCavity.config.CHEST_OPENER_ABSOLUTE_HEALTH_THRESHOLD
                || instance.owner.getHealth() <= instance.owner.getMaxHealth()*FaceCavity.config.CHEST_OPENER_FRACTIONAL_HEALTH_THRESHOLD;
        boolean chestVulnerable = instance.owner.getEquippedStack(EquipmentSlot.CHEST).isEmpty();
        boolean easeOfAccess = instance.getOrganScore(CCOrganScores.EASE_OF_ACCESS) > 0;
        return chestVulnerable && (easeOfAccess || weakEnough);
    }

    @Override
    public void onDeath(FaceCavityInstance cc) {
        cc.projectileQueue.clear();
        if(cc.connectedCrystal != null) {
            cc.connectedCrystal.setBeamTarget(null);
            cc.connectedCrystal = null;
        }
        if(cc.opened && !(playerFaceCavity && FaceCavity.config.KEEP_FACE_CAVITY)) {
            FaceCavityUtil.dropUnboundOrgans(cc);
        }
        if(playerFaceCavity){
            FaceCavityUtil.insertWelfareOrgans(cc);
        }
    }


}
