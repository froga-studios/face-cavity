package io.github.frogastudios.facecavity.facecavities.types.humanoid;

import net.tigereye.chestcavity.registration.CCItems;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.facecavities.types.BaseFaceCavity;

import java.util.*;

public class HumanFaceCavity extends BaseFaceCavity implements FaceCavityType {
    /*
    public HumanfaceCavity(){
        fillfaceCavityInventory(defaultfaceCavity);
        FaceCavityUtil.determineDefaultOrganScores(this);
    }
    */
    @Override
    public void fillFaceCavityInventory(FaceCavityInventory faceCavity) {
        faceCavity.clear();
        faceCavity.setStack(0, new ItemStack(Items.BAMBOO, CCItems.HUMAN_MUSCLE.getMaxCount()));

    }

    @Override
    public void generateRareOrganDrops(Random random, int looting, List<ItemStack> loot) {
        LinkedList<Item> organPile = new LinkedList<>();
        for(int i = 0; i < 4; i++){
            organPile.add(CCItems.HUMAN_RIB);
        }
        for(int i = 0; i < 8; i++){
            organPile.add(CCItems.HUMAN_MUSCLE);
        }
        for(int i = 0; i < 4; i++){
            organPile.add(CCItems.HUMAN_INTESTINE);
        }
        organPile.add(CCItems.HUMAN_APPENDIX);
        organPile.add(CCItems.HUMAN_HEART);
        organPile.add(CCItems.HUMAN_KIDNEY);
        organPile.add(CCItems.HUMAN_KIDNEY);
        organPile.add(CCItems.HUMAN_LIVER);
        organPile.add(CCItems.HUMAN_LUNG);
        organPile.add(CCItems.HUMAN_LUNG);
        organPile.add(CCItems.HUMAN_SPINE);
        organPile.add(CCItems.HUMAN_SPLEEN);
        organPile.add(CCItems.HUMAN_STOMACH);
        int rolls = 1 + random.nextInt(3) + random.nextInt(3);
        FaceCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);
    }

}
