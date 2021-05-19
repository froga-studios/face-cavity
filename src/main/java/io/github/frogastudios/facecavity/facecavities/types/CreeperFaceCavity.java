package io.github.frogastudios.facecavity.facecavities.types;

import io.github.frogastudios.facecavity.registration.FCOrganScores;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.registration.FCItems;
import net.tigereye.chestcavity.registration.CCItems;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class CreeperFaceCavity extends BaseFaceCavity implements FaceCavityType {
    @Override
    public void fillFaceCavityInventory(FaceCavityInventory faceCavity) {
        faceCavity.clear();
        faceCavity.setStack(0, new ItemStack(Items.OAK_LEAVES, 16));
        faceCavity.setStack(1, new ItemStack(Items.STICK, 4));
        faceCavity.setStack(2, ItemStack.EMPTY);
        faceCavity.setStack(3, ItemStack.EMPTY);
        faceCavity.setStack(4, new ItemStack(CCItems.CREEPER_APPENDIX, CCItems.CREEPER_APPENDIX.getMaxCount()));
        faceCavity.setStack(5, ItemStack.EMPTY);
        faceCavity.setStack(6, ItemStack.EMPTY);
        faceCavity.setStack(7, new ItemStack(Items.STICK, 4));
        faceCavity.setStack(8, new ItemStack(Items.OAK_LEAVES, 16));
        faceCavity.setStack(9, new ItemStack(Items.OAK_LEAVES, 16));
        faceCavity.setStack(10, new ItemStack(Items.STICK, 4));
        faceCavity.setStack(11, ItemStack.EMPTY);
        faceCavity.setStack(12, new ItemStack(Items.GUNPOWDER, 1));
        faceCavity.setStack(13, new ItemStack(Items.OAK_LOG, 1));
        faceCavity.setStack(14, new ItemStack(Items.GUNPOWDER, 1));
        faceCavity.setStack(15, ItemStack.EMPTY);
        faceCavity.setStack(16, new ItemStack(Items.STICK, 4));
        faceCavity.setStack(17, new ItemStack(Items.OAK_LEAVES, 16));
        faceCavity.setStack(18, new ItemStack(Items.OAK_LEAVES, 16));
        faceCavity.setStack(19, new ItemStack(Items.OAK_LEAVES, 16));
        faceCavity.setStack(20, ItemStack.EMPTY);
        faceCavity.setStack(21, ItemStack.EMPTY);
        faceCavity.setStack(22, new ItemStack(Items.GUNPOWDER, 1));
        faceCavity.setStack(23, ItemStack.EMPTY);
        faceCavity.setStack(24, ItemStack.EMPTY);
        faceCavity.setStack(25, new ItemStack(Items.OAK_LEAVES, 16));
        faceCavity.setStack(26, new ItemStack(Items.OAK_LEAVES, 16));
    }

    @Override
    public boolean catchExceptionalOrgan(ItemStack slot, Map<Identifier, Float> organScores){
        //creepers are plant monsters, using leaves for flesh and wood for bone
        if(slot.getItem().isIn(ItemTags.LEAVES)){
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.STRENGTH, 4f*slot.getCount()/slot.getMaxCount(),organScores);
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.SPEED, 4f*slot.getCount()/slot.getMaxCount(),organScores);
            return true;
        }
        if(slot.getItem() == Items.STICK){
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.DEFENSE, .25f*slot.getCount(),organScores);
            return true;
        }
        if(slot.getItem().isIn(ItemTags.LOGS)){
            FaceCavityUtil.addOrganScore(FCOrganScores.DEFENSE, .75f*slot.getCount(),organScores);
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.NERVOUS_SYSTEM, 1f*slot.getCount(),organScores);
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.HEALTH, 1f*slot.getCount(),organScores);
            return true;
        }
        return false;
    }

    @Override
    public void generateRareOrganDrops(Random random, int looting, List<ItemStack> loot) {
        loot.add(new ItemStack(CCItems.CREEPER_APPENDIX));
    }

}
