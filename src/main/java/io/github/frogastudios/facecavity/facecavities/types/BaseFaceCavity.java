package io.github.frogastudios.facecavity.facecavities.types;

import io.github.frogastudios.facecavity.FaceCavity;
import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;

import java.util.*;

public class BaseFaceCavity implements FaceCavityType {

    private Map<Identifier,Float> defaultOrganScores = null;
    private FaceCavityInventory defaultChestCavity = new FaceCavityInventory();
    public List<Integer> forbiddenSlots = new ArrayList<>();

    public BaseFaceCavity(){
        fillFaceCavityInventory(defaultChestCavity);
        shapeFaceCavity();
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
    public FaceCavityInventory getDefaultChestCavity(){return defaultChestCavity;}

    @Override
    public boolean isSlotForbidden(int index){
        return forbiddenSlots.contains(index);
    }

    @Override
    public void fillFaceCavityInventory(FaceCavityInventory chestCavity) {
        chestCavity.clear();
        for(int i = 0; i < chestCavity.size(); i++){
            chestCavity.setStack(i,new ItemStack(Items.DIRT,64));
        }
    }

    @Override
    public void shapeFaceCavity() {
    }

    @Override
    public void loadBaseOrganScores(Map<Identifier, Float> organScores){
        organScores.clear();
    }

    @Override
    public boolean catchExceptionalOrgan(ItemStack slot, Map<Identifier, Float> organScores){
        return false;
    }

    @Override
    public List<ItemStack> generateLootDrops(Random random, int looting) {
        List<ItemStack> loot = new ArrayList<>();
        if(random.nextFloat() < FaceCavity.config.UNIVERSAL_DONOR_RATE + (FaceCavity.config.ORGAN_BUNDLE_LOOTING_BOOST*looting)) {
            generateRareOrganDrops(random,looting,loot);
        }
        generateGuaranteedOrganDrops(random,looting,loot);
        return loot;
    }
    public void generateRareOrganDrops(Random random, int looting, List<ItemStack> loot){}
    public void generateGuaranteedOrganDrops(Random random, int looting, List<ItemStack> loot){}

    @Override
    public void setOrganCompatibility(FaceCavityInstance instance){
        FaceCavityInventory chestCavity = instance.inventory;
        //first, make all organs personal
        for(int i = 0; i < chestCavity.size();i++){
            ItemStack itemStack = chestCavity.getStack(i);
            if(itemStack != null && itemStack != itemStack.EMPTY){
                CompoundTag tag = new CompoundTag();
                tag.putUuid("owner",instance.compatibility_id);
                tag.putString("name",instance.owner.getDisplayName().getString());
                itemStack.putSubTag(FaceCavity.COMPATIBILITY_TAG.toString(),tag);
            }
        }
        int universalOrgans = 0;
        Random random = instance.owner.getRandom();
        if(random.nextFloat() < FaceCavity.config.UNIVERSAL_DONOR_RATE){
            universalOrgans = 1+random.nextInt(3)+random.nextInt(3);
        }
        //each attempt, roll a random slot in the chestcavity and turn that organ, if any, compatible
        while(universalOrgans > 0){
            int i = random.nextInt(chestCavity.size());
            ItemStack itemStack = chestCavity.getStack(i);
            if(itemStack != null && itemStack != ItemStack.EMPTY){
                itemStack.removeSubTag(FaceCavity.COMPATIBILITY_TAG.toString());
            }
            universalOrgans--;
        }
    }

    @Override
    public float getHeartBleedCap(){return Float.MAX_VALUE;}

    @Override
    public boolean isOpenable(FaceCavityInstance instance){
        boolean weakEnough = instance.owner.getHealth() <= FaceCavity.config.CHEST_OPENER_ABSOLUTE_HEALTH_THRESHOLD
                || instance.owner.getHealth() <= instance.owner.getMaxHealth()* FaceCavity.config.CHEST_OPENER_FRACTIONAL_HEALTH_THRESHOLD;
        boolean chestVulnerable = instance.owner.getEquippedStack(EquipmentSlot.CHEST).isEmpty();
        boolean easeOfAccess = instance.getOrganScore(FCOrganScores.EASE_OF_ACCESS) > 0;
        return chestVulnerable && (easeOfAccess || weakEnough);
    }

    @Override
    public void onDeath(FaceCavityInstance cc) {
        cc.projectileQueue.clear();
        if(cc.connectedCrystal != null) {
            cc.connectedCrystal.setBeamTarget(null);
            cc.connectedCrystal = null;
        }
        if(cc.opened) {
            return;
        }
    }

}
