package io.github.frogastudios.facecavity.facecavities.types.humanoid;

import io.github.frogastudios.facecavity.registration.FCItems;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.facecavities.types.BaseFaceCavity;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EndermanFaceCavity extends BaseFaceCavity implements FaceCavityType {


    @Override
    public void fillFaceCavityInventory(FaceCavityInventory faceCavity) {
        faceCavity.clear();
    }
/*
    @Override
    public void generateRareOrganDrops(Random random, int looting, List<ItemStack> loot) {
        LinkedList<Item> organPile = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            organPile.add(FCItems.ENDER_RIB);
        }
        for (int i = 0; i < 8; i++) {
            organPile.add(FCItems.ENDER_MUSCLE);
        }
        for (int i = 0; i < 4; i++) {
            organPile.add(FCItems.ENDER_INTESTINE);
        }
        organPile.add(FCItems.ENDER_APPENDIX);
        organPile.add(FCItems.ENDER_HEART);
        organPile.add(FCItems.ENDER_KIDNEY);
        organPile.add(FCItems.ENDER_KIDNEY);
        organPile.add(FCItems.ENDER_LIVER);
        organPile.add(FCItems.ENDER_LUNG);
        organPile.add(FCItems.ENDER_LUNG);
        organPile.add(FCItems.ENDER_SPINE);
        organPile.add(FCItems.ENDER_SPLEEN);
        organPile.add(FCItems.ENDER_STOMACH);
        int rolls = 1 + random.nextInt(3) + random.nextInt(3);
        FaceCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);
  }
 */
}
