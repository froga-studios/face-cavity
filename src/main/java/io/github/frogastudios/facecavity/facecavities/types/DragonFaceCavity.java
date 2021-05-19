package io.github.frogastudios.facecavity.facecavities.types;

import io.github.frogastudios.facecavity.FaceCavity;
import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import net.tigereye.chestcavity.registration.CCItems;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DragonFaceCavity extends BaseFaceCavity implements FaceCavityType {
    protected static final float heartbleedCap = 5f;

    @Override
    public void fillFaceCavityInventory(FaceCavityInventory faceCavity) {
        faceCavity.clear();
        faceCavity.setStack(0, new ItemStack(CCItems.DRAGON_MUSCLE, CCItems.DRAGON_MUSCLE.getMaxCount()));
        faceCavity.setStack(1, new ItemStack(CCItems.DRAGON_RIB, CCItems.DRAGON_RIB.getMaxCount()));
        faceCavity.setStack(2, new ItemStack(CCItems.DRAGON_APPENDIX, CCItems.DRAGON_APPENDIX.getMaxCount()));
        faceCavity.setStack(3, new ItemStack(CCItems.DRAGON_LUNG, CCItems.DRAGON_LUNG.getMaxCount()));
        faceCavity.setStack(4, new ItemStack(CCItems.DRAGON_HEART, CCItems.DRAGON_HEART.getMaxCount()));
        faceCavity.setStack(5, new ItemStack(CCItems.DRAGON_LUNG, CCItems.DRAGON_LUNG.getMaxCount()));
        faceCavity.setStack(6, ItemStack.EMPTY);
        faceCavity.setStack(7, new ItemStack(CCItems.DRAGON_RIB, CCItems.DRAGON_RIB.getMaxCount()));
        faceCavity.setStack(8, new ItemStack(CCItems.DRAGON_MUSCLE, CCItems.DRAGON_MUSCLE.getMaxCount()));
        faceCavity.setStack(9, new ItemStack(CCItems.DRAGON_MUSCLE, CCItems.DRAGON_MUSCLE.getMaxCount()));
        faceCavity.setStack(10, new ItemStack(CCItems.DRAGON_RIB, CCItems.DRAGON_RIB.getMaxCount()));
        faceCavity.setStack(11, new ItemStack(CCItems.DRAGON_SPLEEN, CCItems.DRAGON_SPLEEN.getMaxCount()));
        faceCavity.setStack(12, new ItemStack(CCItems.DRAGON_KIDNEY, CCItems.DRAGON_KIDNEY.getMaxCount()));
        faceCavity.setStack(13, new ItemStack(CCItems.DRAGON_SPINE, CCItems.DRAGON_SPINE.getMaxCount()));
        faceCavity.setStack(14, new ItemStack(CCItems.DRAGON_KIDNEY, CCItems.DRAGON_KIDNEY.getMaxCount()));
        faceCavity.setStack(15, new ItemStack(CCItems.DRAGON_LIVER, CCItems.DRAGON_LIVER.getMaxCount()));
        faceCavity.setStack(16, new ItemStack(CCItems.DRAGON_RIB, CCItems.DRAGON_RIB.getMaxCount()));
        faceCavity.setStack(17, new ItemStack(CCItems.DRAGON_MUSCLE, CCItems.DRAGON_MUSCLE.getMaxCount()));
        faceCavity.setStack(18, new ItemStack(CCItems.DRAGON_MUSCLE, CCItems.DRAGON_MUSCLE.getMaxCount()));
        faceCavity.setStack(19, new ItemStack(CCItems.DRAGON_MUSCLE, CCItems.DRAGON_MUSCLE.getMaxCount()));
        faceCavity.setStack(20, new ItemStack(CCItems.MANA_REACTOR, CCItems.MANA_REACTOR.getMaxCount()));
        faceCavity.setStack(21, new ItemStack(CCItems.MANA_REACTOR, CCItems.MANA_REACTOR.getMaxCount()));
        faceCavity.setStack(22, new ItemStack(CCItems.MANA_REACTOR, CCItems.MANA_REACTOR.getMaxCount()));
        faceCavity.setStack(23, new ItemStack(CCItems.MANA_REACTOR, CCItems.MANA_REACTOR.getMaxCount()));
        faceCavity.setStack(24, new ItemStack(CCItems.MANA_REACTOR, CCItems.MANA_REACTOR.getMaxCount()));
        faceCavity.setStack(25, new ItemStack(CCItems.DRAGON_MUSCLE, CCItems.DRAGON_MUSCLE.getMaxCount()));
        faceCavity.setStack(26, new ItemStack(CCItems.DRAGON_MUSCLE, CCItems.DRAGON_MUSCLE.getMaxCount()));
    }

    @Override
    public void loadBaseOrganScores(Map<Identifier, Float> organScores){
        organScores.clear();
        organScores.put(FCOrganScores.STRENGTH, 1f);
        organScores.put(FCOrganScores.NERVOUS_SYSTEM, .5f);
        organScores.put(FCOrganScores.BREATH, .25f);
    }

    @Override
    public void setOrganCompatibility(FaceCavityInstance cc){
        //the dragon is guaranteed to have 1-3 compatible organs
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
                universalOrgans--;
            }
        }
    }

    @Override
    public void generateGuaranteedOrganDrops(Random random, int looting, List<ItemStack> loot) {
        LinkedList<Item> organPile = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            organPile.add(CCItems.DRAGON_RIB);
        }
        for (int i = 0; i < 8; i++) {
            organPile.add(CCItems.DRAGON_MUSCLE);
        }
        for (int i = 0; i < 5; i++) {
            organPile.add(CCItems.MANA_REACTOR);
        }
        organPile.add(CCItems.DRAGON_APPENDIX);
        organPile.add(CCItems.DRAGON_HEART);
        organPile.add(CCItems.DRAGON_KIDNEY);
        organPile.add(CCItems.DRAGON_KIDNEY);
        organPile.add(CCItems.DRAGON_LIVER);
        organPile.add(CCItems.DRAGON_LUNG);
        organPile.add(CCItems.DRAGON_LUNG);
        organPile.add(CCItems.DRAGON_SPINE);
        organPile.add(CCItems.DRAGON_SPLEEN);
        int rolls = 3 + random.nextInt(2+looting) + random.nextInt(2+looting);
        FaceCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);
    }

    @Override
    public float getHeartBleedCap(){
        return heartbleedCap;
    }

}
