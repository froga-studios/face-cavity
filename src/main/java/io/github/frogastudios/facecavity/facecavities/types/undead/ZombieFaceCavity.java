package io.github.frogastudios.facecavity.facecavities.types.undead;

import net.tigereye.chestcavity.registration.CCItems;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.facecavities.types.BaseFaceCavity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ZombieFaceCavity extends BaseFaceCavity implements FaceCavityType {

    @Override
    public void fillFaceCavityInventory(FaceCavityInventory faceCavity) {
        faceCavity.clear();
        //faceCavity.setStack(0, new ItemStack(Items.ROTTEN_FLESH, Items.ROTTEN_FLESH.getMaxCount()));
        faceCavity.setStack(1, new ItemStack(CCItems.ROTTEN_RIB, CCItems.ROTTEN_RIB.getMaxCount()));
        faceCavity.setStack(2, new ItemStack(CCItems.ROTTEN_APPENDIX, CCItems.ROTTEN_APPENDIX.getMaxCount()));
        faceCavity.setStack(3, new ItemStack(CCItems.ROTTEN_LUNG, CCItems.ROTTEN_LUNG.getMaxCount()));
        faceCavity.setStack(4, new ItemStack(CCItems.ROTTEN_HEART, CCItems.ROTTEN_HEART.getMaxCount()));
        faceCavity.setStack(5, new ItemStack(CCItems.ROTTEN_LUNG, CCItems.ROTTEN_LUNG.getMaxCount()));
        faceCavity.setStack(6, ItemStack.EMPTY);
        //faceCavity.setStack(7, new ItemStack(CCItems.ROTTEN_RIB, CCItems.ROTTEN_RIB.getMaxCount()));
        faceCavity.setStack(8, new ItemStack(Items.ROTTEN_FLESH, Items.ROTTEN_FLESH.getMaxCount()));
        faceCavity.setStack(9, new ItemStack(Items.ROTTEN_FLESH, Items.ROTTEN_FLESH.getMaxCount()));
        faceCavity.setStack(10, new ItemStack(CCItems.ROTTEN_RIB, CCItems.ROTTEN_RIB.getMaxCount()));
        faceCavity.setStack(11, new ItemStack(CCItems.ROTTEN_SPLEEN, CCItems.ROTTEN_SPLEEN.getMaxCount()));
        faceCavity.setStack(12, new ItemStack(CCItems.ROTTEN_KIDNEY, CCItems.ROTTEN_KIDNEY.getMaxCount()));
        faceCavity.setStack(13, new ItemStack(CCItems.ROTTEN_SPINE, CCItems.ROTTEN_SPINE.getMaxCount()));
        faceCavity.setStack(14, new ItemStack(CCItems.ROTTEN_KIDNEY, CCItems.ROTTEN_KIDNEY.getMaxCount()));
        faceCavity.setStack(15, new ItemStack(CCItems.ROTTEN_LIVER, CCItems.ROTTEN_LIVER.getMaxCount()));
        //faceCavity.setStack(16, new ItemStack(CCItems.ROTTEN_RIB, CCItems.ROTTEN_RIB.getMaxCount()));
        //faceCavity.setStack(17, new ItemStack(Items.ROTTEN_FLESH, Items.ROTTEN_FLESH.getMaxCount()));
        faceCavity.setStack(18, new ItemStack(Items.ROTTEN_FLESH, Items.ROTTEN_FLESH.getMaxCount()));
        //faceCavity.setStack(19, new ItemStack(Items.ROTTEN_FLESH, Items.ROTTEN_FLESH.getMaxCount()));
        //faceCavity.setStack(20, new ItemStack(CCItems.ROTTEN_INTESTINE, CCItems.ROTTEN_INTESTINE.getMaxCount()));
        faceCavity.setStack(21, new ItemStack(CCItems.ROTTEN_INTESTINE, CCItems.ROTTEN_INTESTINE.getMaxCount()));
        faceCavity.setStack(22, new ItemStack(CCItems.ROTTEN_STOMACH, CCItems.ROTTEN_STOMACH.getMaxCount()));
        faceCavity.setStack(23, new ItemStack(CCItems.ROTTEN_INTESTINE, CCItems.ROTTEN_INTESTINE.getMaxCount()));
        faceCavity.setStack(24, new ItemStack(CCItems.ROTTEN_INTESTINE, CCItems.ROTTEN_INTESTINE.getMaxCount()));
        faceCavity.setStack(25, new ItemStack(Items.ROTTEN_FLESH, Items.ROTTEN_FLESH.getMaxCount()));
        //faceCavity.setStack(26, new ItemStack(Items.ROTTEN_FLESH, Items.ROTTEN_FLESH.getMaxCount()));
    }

    @Override
    public void shapeFaceCavity() {
        forbiddenSlots.add(0);
        forbiddenSlots.add(7);
        forbiddenSlots.add(16);
        forbiddenSlots.add(17);
        forbiddenSlots.add(19);
        forbiddenSlots.add(20);
        forbiddenSlots.add(26);
    }

    @Override
    public void loadBaseOrganScores(Map<Identifier, Float> organScores){
        organScores.clear();
        organScores.put(FCOrganScores.LUCK, .5f);
        organScores.put(FCOrganScores.DEFENSE, 2.375f);
        organScores.put(FCOrganScores.HEALTH, 0.5f);
        organScores.put(FCOrganScores.NUTRITION, 2f);
        organScores.put(FCOrganScores.FILTRATION, 1f);
        organScores.put(FCOrganScores.DETOXIFICATION, .5f);
        organScores.put(FCOrganScores.STRENGTH, 4f);
        organScores.put(FCOrganScores.SPEED, 4f);
        organScores.put(FCOrganScores.NERVOUS_SYSTEM, .5f);
        organScores.put(FCOrganScores.METABOLISM, .5f);
        organScores.put(FCOrganScores.DIGESTION, .5f);
        organScores.put(FCOrganScores.BREATH, 1f);
        organScores.put(FCOrganScores.ENDURANCE, 1f);
    }

    @Override
    public void generateRareOrganDrops(Random random, int looting, List<ItemStack> loot) {
        LinkedList<Item> organPile = new LinkedList<>();
        organPile.add(CCItems.ROTTEN_APPENDIX);
        organPile.add(CCItems.ROTTEN_HEART);
        organPile.add(CCItems.ROTTEN_INTESTINE);
        organPile.add(CCItems.ROTTEN_INTESTINE);
        organPile.add(CCItems.ROTTEN_INTESTINE);
        organPile.add(CCItems.ROTTEN_KIDNEY);
        organPile.add(CCItems.ROTTEN_KIDNEY);
        organPile.add(CCItems.ROTTEN_LIVER);
        organPile.add(CCItems.ROTTEN_LUNG);
        organPile.add(CCItems.ROTTEN_LUNG);
        organPile.add(CCItems.ROTTEN_SPLEEN);
        organPile.add(CCItems.ROTTEN_STOMACH);
        int rolls = 1 + random.nextInt(3) + random.nextInt(3);
        FaceCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);
    }

}
