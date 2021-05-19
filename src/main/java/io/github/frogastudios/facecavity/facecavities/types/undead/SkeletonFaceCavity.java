package io.github.frogastudios.facecavity.facecavities.types.undead;

import net.tigereye.chestcavity.registration.CCItems;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.facecavities.types.BaseFaceCavity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SkeletonFaceCavity extends BaseFaceCavity implements FaceCavityType {
    @Override
    public void fillFaceCavityInventory(FaceCavityInventory faceCavity) {
        faceCavity.clear();
        faceCavity.setStack(1, new ItemStack(CCItems.ROTTEN_RIB, CCItems.ROTTEN_RIB.getMaxCount()));
        faceCavity.setStack(2, ItemStack.EMPTY);
        faceCavity.setStack(3, ItemStack.EMPTY);
        faceCavity.setStack(4, ItemStack.EMPTY);
        faceCavity.setStack(5, ItemStack.EMPTY);
        faceCavity.setStack(6, ItemStack.EMPTY);
        faceCavity.setStack(7, new ItemStack(CCItems.ROTTEN_RIB, CCItems.ROTTEN_RIB.getMaxCount()));
        faceCavity.setStack(10, new ItemStack(CCItems.ROTTEN_RIB, CCItems.ROTTEN_RIB.getMaxCount()));
        faceCavity.setStack(11, ItemStack.EMPTY);
        faceCavity.setStack(12, ItemStack.EMPTY);
        faceCavity.setStack(13, new ItemStack(CCItems.ROTTEN_SPINE, CCItems.ROTTEN_SPINE.getMaxCount()));
        faceCavity.setStack(14, ItemStack.EMPTY);
        faceCavity.setStack(15, ItemStack.EMPTY);
        faceCavity.setStack(16, new ItemStack(CCItems.ROTTEN_RIB, CCItems.ROTTEN_RIB.getMaxCount()));
    }

    @Override
    public void shapeFaceCavity() {
        forbiddenSlots.add(0);
        forbiddenSlots.add(8);
        forbiddenSlots.add(9);
        forbiddenSlots.add(17);
        forbiddenSlots.add(18);
        forbiddenSlots.add(19);
        forbiddenSlots.add(20);
        forbiddenSlots.add(21);
        forbiddenSlots.add(22);
        forbiddenSlots.add(23);
        forbiddenSlots.add(24);
        forbiddenSlots.add(25);
        forbiddenSlots.add(26);
    }

    @Override
    public void loadBaseOrganScores(Map<Identifier, Float> organScores){
        organScores.clear();
        organScores.put(FCOrganScores.LUCK, 1f);
        organScores.put(FCOrganScores.DEFENSE, 2.375f);
        organScores.put(FCOrganScores.HEALTH, 1f);
        organScores.put(FCOrganScores.NUTRITION, 4f);
        organScores.put(FCOrganScores.FILTRATION, 2f);
        organScores.put(FCOrganScores.DETOXIFICATION, 1f);
        organScores.put(FCOrganScores.STRENGTH, 8f);
        organScores.put(FCOrganScores.SPEED, 8f);
        organScores.put(FCOrganScores.NERVOUS_SYSTEM, .5f);
        organScores.put(FCOrganScores.METABOLISM, 1f);
        organScores.put(FCOrganScores.DIGESTION, 1f);
        organScores.put(FCOrganScores.BREATH, 2f);
        organScores.put(FCOrganScores.ENDURANCE, 2f);
    }

    @Override
    public void generateRareOrganDrops(Random random, int looting, List<ItemStack> loot) {
        LinkedList<Item> organPile = new LinkedList<>();
        for(int i = 0; i < 4; i++){
            organPile.add(CCItems.ROTTEN_RIB);
        }
        organPile.add(CCItems.ROTTEN_SPINE);
        int rolls = 1 + random.nextInt(1) + random.nextInt(1);
        FaceCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);
    }

}
