package io.github.frogastudios.facecavity.facecavities.types.insects;

import net.tigereye.chestcavity.registration.CCItems;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.facecavities.types.BaseFaceCavity;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SpiderFaceCavity extends BaseFaceCavity implements FaceCavityType {
    @Override
    public void fillFaceCavityInventory(FaceCavityInventory faceCavity) {
        faceCavity.clear();
        faceCavity.setStack(0, new ItemStack(CCItems.INSECT_MUSCLE, CCItems.INSECT_MUSCLE.getMaxCount()));
        faceCavity.setStack(1, new ItemStack(CCItems.INSECT_HEART, CCItems.INSECT_HEART.getMaxCount()));
        faceCavity.setStack(2, new ItemStack(CCItems.INSECT_HEART, CCItems.INSECT_HEART.getMaxCount()));
        faceCavity.setStack(3, new ItemStack(CCItems.INSECT_CAECA, CCItems.INSECT_CAECA.getMaxCount()));
        faceCavity.setStack(4, new ItemStack(CCItems.INSECT_CAECA, CCItems.INSECT_CAECA.getMaxCount()));
        faceCavity.setStack(5, new ItemStack(CCItems.INSECT_CAECA, CCItems.INSECT_CAECA.getMaxCount()));
        faceCavity.setStack(6, new ItemStack(CCItems.INSECT_CAECA, CCItems.INSECT_CAECA.getMaxCount()));
        faceCavity.setStack(7, new ItemStack(CCItems.INSECT_CAECA, CCItems.INSECT_CAECA.getMaxCount()));
        faceCavity.setStack(8, new ItemStack(CCItems.INSECT_MUSCLE, CCItems.INSECT_MUSCLE.getMaxCount()));

        faceCavity.setStack(9, new ItemStack(CCItems.INSECT_MUSCLE, CCItems.INSECT_MUSCLE.getMaxCount()));
        faceCavity.setStack(10, new ItemStack(CCItems.INSECT_HEART, CCItems.INSECT_HEART.getMaxCount()));
        faceCavity.setStack(11, new ItemStack(CCItems.INSECT_STOMACH, CCItems.INSECT_STOMACH.getMaxCount()));
        faceCavity.setStack(12, new ItemStack(CCItems.INSECT_INTESTINE, CCItems.INSECT_INTESTINE.getMaxCount()));
        faceCavity.setStack(13, new ItemStack(CCItems.INSECT_INTESTINE, CCItems.INSECT_INTESTINE.getMaxCount()));
        faceCavity.setStack(14, new ItemStack(CCItems.INSECT_INTESTINE, CCItems.INSECT_INTESTINE.getMaxCount()));
        faceCavity.setStack(15, new ItemStack(CCItems.INSECT_INTESTINE, CCItems.INSECT_INTESTINE.getMaxCount()));
        faceCavity.setStack(16, new ItemStack(CCItems.INSECT_INTESTINE, CCItems.INSECT_INTESTINE.getMaxCount()));
        faceCavity.setStack(17, new ItemStack(CCItems.INSECT_MUSCLE, CCItems.INSECT_MUSCLE.getMaxCount()));

        faceCavity.setStack(18, new ItemStack(CCItems.INSECT_MUSCLE, CCItems.INSECT_MUSCLE.getMaxCount()));
        faceCavity.setStack(19, new ItemStack(CCItems.INSECT_MUSCLE, CCItems.INSECT_MUSCLE.getMaxCount()));
        faceCavity.setStack(20, new ItemStack(CCItems.INSECT_LUNG, CCItems.INSECT_LUNG.getMaxCount()));
        faceCavity.setStack(21, new ItemStack(CCItems.INSECT_LUNG, CCItems.INSECT_LUNG.getMaxCount()));
        faceCavity.setStack(22, new ItemStack(CCItems.INSECT_LUNG, CCItems.INSECT_LUNG.getMaxCount()));
        faceCavity.setStack(23, new ItemStack(CCItems.INSECT_MUSCLE, CCItems.INSECT_MUSCLE.getMaxCount()));
        faceCavity.setStack(24, new ItemStack(CCItems.INSECT_MUSCLE, CCItems.INSECT_MUSCLE.getMaxCount()));
        faceCavity.setStack(25, new ItemStack(CCItems.SILK_GLAND, CCItems.SILK_GLAND.getMaxCount()));
        faceCavity.setStack(26, new ItemStack(CCItems.SILK_GLAND, CCItems.SILK_GLAND.getMaxCount()));
    }

    @Override
    public void generateRareOrganDrops(Random random, int looting, List<ItemStack> loot) {
        LinkedList<Item> organPile = new LinkedList<>();
        for(int i = 0; i < 3; i++){
            organPile.add(CCItems.INSECT_HEART);
        }
        for(int i = 0; i < 8; i++){
            organPile.add(CCItems.INSECT_MUSCLE);
        }
        for(int i = 0; i < 5; i++){
            organPile.add(CCItems.INSECT_INTESTINE);
        }
        for(int i = 0; i < 5; i++){
            organPile.add(CCItems.INSECT_CAECA);
        }
        organPile.add(CCItems.SILK_GLAND);
        organPile.add(CCItems.SILK_GLAND);
        organPile.add(CCItems.INSECT_STOMACH);
        int rolls = 1 + random.nextInt(3) + random.nextInt(3);
        FaceCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);
    }

}
