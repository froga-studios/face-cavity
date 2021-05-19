package io.github.frogastudios.facecavity.facecavities.types;

import net.tigereye.chestcavity.registration.CCItems;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ShulkerFaceCavity extends BaseFaceCavity implements FaceCavityType {
    @Override
    public void fillFaceCavityInventory(FaceCavityInventory faceCavity) {
        faceCavity.clear();
        faceCavity.setStack(0, new ItemStack(CCItems.ANIMAL_MUSCLE, CCItems.ANIMAL_MUSCLE.getMaxCount()));
        faceCavity.setStack(1, new ItemStack(CCItems.ANIMAL_RIB, CCItems.ANIMAL_RIB.getMaxCount()));
        faceCavity.setStack(2, new ItemStack(CCItems.ANIMAL_APPENDIX, CCItems.ANIMAL_APPENDIX.getMaxCount()));
        faceCavity.setStack(3, new ItemStack(CCItems.ANIMAL_LUNG, CCItems.ANIMAL_LUNG.getMaxCount()));
        faceCavity.setStack(4, new ItemStack(CCItems.ANIMAL_HEART, CCItems.ANIMAL_HEART.getMaxCount()));
        faceCavity.setStack(5, new ItemStack(CCItems.ANIMAL_LUNG, CCItems.ANIMAL_LUNG.getMaxCount()));
        faceCavity.setStack(6, ItemStack.EMPTY);
        faceCavity.setStack(7, new ItemStack(CCItems.ANIMAL_RIB, CCItems.ANIMAL_RIB.getMaxCount()));
        faceCavity.setStack(8, new ItemStack(CCItems.ANIMAL_MUSCLE, CCItems.ANIMAL_MUSCLE.getMaxCount()));
        faceCavity.setStack(9, new ItemStack(CCItems.ANIMAL_MUSCLE, CCItems.ANIMAL_MUSCLE.getMaxCount()));
        faceCavity.setStack(10, new ItemStack(CCItems.ANIMAL_RIB, CCItems.ANIMAL_RIB.getMaxCount()));
        faceCavity.setStack(11, new ItemStack(CCItems.SHULKER_SPLEEN, CCItems.SHULKER_SPLEEN.getMaxCount()));
        faceCavity.setStack(12, new ItemStack(CCItems.ANIMAL_KIDNEY, CCItems.ANIMAL_KIDNEY.getMaxCount()));
        faceCavity.setStack(13, new ItemStack(CCItems.ANIMAL_SPINE, CCItems.ANIMAL_SPINE.getMaxCount()));
        faceCavity.setStack(14, new ItemStack(CCItems.ANIMAL_KIDNEY, CCItems.ANIMAL_KIDNEY.getMaxCount()));
        faceCavity.setStack(15, new ItemStack(CCItems.ANIMAL_LIVER, CCItems.ANIMAL_LIVER.getMaxCount()));
        faceCavity.setStack(16, new ItemStack(CCItems.ANIMAL_RIB, CCItems.ANIMAL_RIB.getMaxCount()));
        faceCavity.setStack(17, new ItemStack(CCItems.ANIMAL_MUSCLE, CCItems.ANIMAL_MUSCLE.getMaxCount()));
        faceCavity.setStack(18, new ItemStack(CCItems.ANIMAL_MUSCLE, CCItems.ANIMAL_MUSCLE.getMaxCount()));
        faceCavity.setStack(19, new ItemStack(CCItems.ANIMAL_MUSCLE, CCItems.ANIMAL_MUSCLE.getMaxCount()));
        faceCavity.setStack(20, new ItemStack(CCItems.ANIMAL_INTESTINE, CCItems.ANIMAL_INTESTINE.getMaxCount()));
        faceCavity.setStack(21, new ItemStack(CCItems.ANIMAL_INTESTINE, CCItems.ANIMAL_INTESTINE.getMaxCount()));
        faceCavity.setStack(22, new ItemStack(CCItems.ANIMAL_STOMACH, CCItems.ANIMAL_STOMACH.getMaxCount()));
        faceCavity.setStack(23, new ItemStack(CCItems.ANIMAL_INTESTINE, CCItems.ANIMAL_INTESTINE.getMaxCount()));
        faceCavity.setStack(24, new ItemStack(CCItems.ANIMAL_INTESTINE, CCItems.ANIMAL_INTESTINE.getMaxCount()));
        faceCavity.setStack(25, new ItemStack(CCItems.ANIMAL_MUSCLE, CCItems.ANIMAL_MUSCLE.getMaxCount()));
        faceCavity.setStack(26, new ItemStack(CCItems.ANIMAL_MUSCLE, CCItems.ANIMAL_MUSCLE.getMaxCount()));
    }

    @Override
    public void generateRareOrganDrops(Random random, int looting, List<ItemStack> loot) {
        LinkedList<Item> organPile = new LinkedList<>();
        for(int i = 0; i < 4; i++){
            organPile.add(CCItems.ANIMAL_RIB);
        }
        for(int i = 0; i < 8; i++){
            organPile.add(CCItems.ANIMAL_MUSCLE);
        }
        for(int i = 0; i < 4; i++){
            organPile.add(CCItems.ANIMAL_INTESTINE);
        }
        organPile.add(CCItems.ANIMAL_APPENDIX);
        organPile.add(CCItems.ANIMAL_HEART);
        organPile.add(CCItems.ANIMAL_KIDNEY);
        organPile.add(CCItems.ANIMAL_KIDNEY);
        organPile.add(CCItems.ANIMAL_LIVER);
        organPile.add(CCItems.ANIMAL_LUNG);
        organPile.add(CCItems.ANIMAL_LUNG);
        organPile.add(CCItems.ANIMAL_SPINE);
        organPile.add(CCItems.SHULKER_SPLEEN);
        organPile.add(CCItems.ANIMAL_STOMACH);
        int rolls = 1 + random.nextInt(3) + random.nextInt(3);
        FaceCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);
    }

}
