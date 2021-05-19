package io.github.frogastudios.facecavity.facecavities.types.artificial;

import io.github.frogastudios.facecavity.registration.FCItems;
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

public class BlazeFaceCavity extends BaseFaceCavity implements FaceCavityType {
    @Override
    public void fillFaceCavityInventory(FaceCavityInventory faceCavity) {
        faceCavity.clear();
    }

    @Override
    public boolean catchExceptionalOrgan(ItemStack slot, Map<Identifier, Float> organScores){
        //creepers are plant monsters, using leaves for flesh and wood for bone
        if(slot.getItem() == Items.MAGMA_BLOCK){
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.DETOXIFICATION, 2f,organScores);
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.BREATH, 4f,organScores);
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.FILTRATION, 4f,organScores);
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.METABOLISM, 2f,organScores);
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.LUCK, 2f,organScores);
            return true;
        }
        return false;
    }
/*
    @Override
    public void generateRareOrganDrops(Random random, int looting, List<ItemStack> loot) {
        LinkedList<Item> organPile = new LinkedList<>();
        organPile.add(FCItems.ACTIVE_BLAZE_ROD);
        organPile.add(FCItems.BLAZE_SHELL);
        organPile.add(FCItems.BLAZE_SHELL);
        organPile.add(FCItems.BLAZE_SHELL);
        organPile.add(FCItems.BLAZE_SHELL);
        organPile.add(FCItems.BLAZE_SHELL);
        organPile.add(FCItems.BLAZE_SHELL);
        organPile.add(FCItems.BLAZE_CORE);
        int rolls = 1 + random.nextInt(3) + random.nextInt(3);
        io.github.frogastudios.facecavity.util.FaceCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);
    }
*/
}
