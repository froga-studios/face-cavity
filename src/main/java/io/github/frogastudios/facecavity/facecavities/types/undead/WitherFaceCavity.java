package io.github.frogastudios.facecavity.facecavities.types.undead;

import io.github.frogastudios.facecavity.FaceCavity;
import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import net.tigereye.chestcavity.registration.CCItems;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.facecavities.types.BaseFaceCavity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WitherFaceCavity extends BaseFaceCavity implements FaceCavityType {
    protected static final float heartbleedCap = 5f;

    @Override
    public void fillFaceCavityInventory(FaceCavityInventory faceCavity) {
        faceCavity.clear();
        faceCavity.setStack(0, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(1, new ItemStack(CCItems.WITHERED_RIB, CCItems.WITHERED_RIB.getMaxCount()));
        faceCavity.setStack(2, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(3, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(4, new ItemStack(Items.NETHER_STAR, 1));
        faceCavity.setStack(5, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(6, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(7, new ItemStack(CCItems.WITHERED_RIB, CCItems.WITHERED_RIB.getMaxCount()));
        faceCavity.setStack(8, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(9, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(10, new ItemStack(CCItems.WITHERED_RIB, CCItems.WITHERED_RIB.getMaxCount()));
        faceCavity.setStack(11, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(12, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(13, new ItemStack(CCItems.WITHERED_SPINE, CCItems.WITHERED_SPINE.getMaxCount()));
        faceCavity.setStack(14, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(15, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(16, new ItemStack(CCItems.WITHERED_RIB, CCItems.WITHERED_RIB.getMaxCount()));
        faceCavity.setStack(17, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(18, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(19, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(20, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(21, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(22, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(23, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(24, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(25, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
        faceCavity.setStack(26, new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
    }

    @Override
    public boolean catchExceptionalOrgan(ItemStack slot, Map<Identifier, Float> organScores){
        if(slot.getItem() == Items.NETHER_STAR){
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.HEALTH, 1f*slot.getCount(),organScores);
            return true;
        }
        return false;
    }

    @Override
    public void loadBaseOrganScores(Map<Identifier, Float> organScores){
        organScores.clear();
        organScores.put(FCOrganScores.LUCK, 1f);
        organScores.put(FCOrganScores.DEFENSE, 2.375f);
        organScores.put(FCOrganScores.NUTRITION, 4f);
        organScores.put(FCOrganScores.FILTRATION, 2f);
        organScores.put(FCOrganScores.DETOXIFICATION, 1f);
        organScores.put(FCOrganScores.NERVOUS_SYSTEM, .5f);
        organScores.put(FCOrganScores.METABOLISM, 1f);
        organScores.put(FCOrganScores.DIGESTION, 1f);
        organScores.put(FCOrganScores.BREATH, 2f);
    }

    @Override
    public void setOrganCompatibility(FaceCavityInstance cc){
        //the wither is guaranteed to have 1-3 compatible organs
        for(int i = 0; i < cc.inventory.size();i++){
            ItemStack itemStack = cc.inventory.getStack(i);
            if(itemStack != null && itemStack != ItemStack.EMPTY){
                CompoundTag tag = new CompoundTag();
                tag.putUuid("owner",cc.compatibility_id);
                tag.putString("name",cc.owner.getDisplayName().getString());
                itemStack.putSubTag(FaceCavity.COMPATIBILITY_TAG.toString(),tag);
            }
        }
        int universalOrgans = 0;
        Random random = cc.owner.getRandom();
        universalOrgans = 1+random.nextInt(2)+random.nextInt(2);

        while(universalOrgans > 0){
            int i = random.nextInt(cc.inventory.size());
            ItemStack itemStack = cc.inventory.getStack(i);
            if(itemStack != null && itemStack != ItemStack.EMPTY){
                itemStack.removeSubTag(FaceCavity.COMPATIBILITY_TAG.toString());
            }
            universalOrgans--;
        }
    }

    @Override
    public void generateRareOrganDrops(Random random, int looting, List<ItemStack> loot) {
        LinkedList<Item> organPile = new LinkedList<>();
        for(int i = 0; i < 4; i++){
            organPile.add(CCItems.WITHERED_RIB);
        }
        organPile.add(CCItems.WITHERED_SPINE);
        int rolls = 1 + random.nextInt(1) + random.nextInt(1);
        FaceCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);
    }

    @Override
    public void generateGuaranteedOrganDrops(Random random, int looting, List<ItemStack> loot) {
        int soulsandCount = 16 + random.nextInt(4 + 4*looting) + random.nextInt(4 + 4*looting) + random.nextInt(4 + 4*looting) + random.nextInt(4 + 4*looting);
        soulsandCount = Math.min(64*21,soulsandCount);
        while(soulsandCount > CCItems.WRITHING_SOULSAND.getMaxCount()){
            loot.add(new ItemStack(CCItems.WRITHING_SOULSAND, CCItems.WRITHING_SOULSAND.getMaxCount()));
            soulsandCount -= CCItems.WRITHING_SOULSAND.getMaxCount();
        }
        loot.add(new ItemStack(CCItems.WRITHING_SOULSAND, soulsandCount));
    }

    @Override
    public float getHeartBleedCap(){
        return heartbleedCap;
    }

    @Override
    public boolean isOpenable(FaceCavityInstance cc){
        if(cc.owner instanceof WitherEntity){
            return ((WitherEntity)cc.owner).getInvulnerableTimer() <= 0 && super.isOpenable(cc);
        }
        return super.isOpenable(cc);
    }

}
