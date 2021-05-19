package io.github.frogastudios.facecavity.facecavities.types.humanoid;

import io.github.frogastudios.facecavity.FaceCavity;
import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import io.github.frogastudios.facecavity.registration.FCItems;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.GameRules;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import net.tigereye.chestcavity.items.Organ;
import net.tigereye.chestcavity.registration.CCItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerFaceCavity extends HumanFaceCavity implements FaceCavityType {

    @Override
    public void setOrganCompatibility(FaceCavityInstance fc){
        for(int i = 0; i < fc.inventory.size();i++){
            ItemStack itemStack = fc.inventory.getStack(i);
            if(itemStack != null && itemStack.getItem() instanceof Organ){
                CompoundTag tag = new CompoundTag();
                tag.putUuid("owner",fc.compatibility_id);
                tag.putString("name",fc.owner.getDisplayName().getString());
                itemStack.putSubTag(FaceCavity.COMPATIBILITY_TAG.toString(),tag);
            }
        }
    }

    @Override
    public void onDeath(FaceCavityInstance fc){
        fc.projectileQueue.clear();
        if(fc.connectedCrystal != null) {
            fc.connectedCrystal.setBeamTarget(null);
            fc.connectedCrystal = null;
        }
        if(fc.opened && !FaceCavity.config.KEEP_FACE_CAVITY) {
            return;
        }
        insertWelfareOrgans(fc);
    }

    protected void insertWelfareOrgans(FaceCavityInstance fc){
        //urgently essential organs are: heart, spine, lung, and just a touch of strength
        if(fc.getOrganScore(FCOrganScores.HEALTH) == 0){
            forcefullyAddStack(fc, new ItemStack(CCItems.ROTTEN_HEART),4);
        }
        if(fc.getOrganScore(FCOrganScores.BREATH) == 0){
            forcefullyAddStack(fc, new ItemStack(CCItems.ROTTEN_LUNG),3);
        }
        if(fc.getOrganScore(FCOrganScores.NERVOUS_SYSTEM) == 0){
            forcefullyAddStack(fc, new ItemStack(CCItems.ROTTEN_SPINE),13);
        }
        if(fc.getOrganScore(FCOrganScores.STRENGTH) == 0){
            forcefullyAddStack(fc, new ItemStack(Items.ROTTEN_FLESH,16),0);
        }
    }

    protected void forcefullyAddStack(FaceCavityInstance fc, ItemStack stack, int slot){
        if(!fc.inventory.canInsert(stack)) {
            if (!fc.inventory.canInsert(stack) && fc.owner.getEntityWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && fc.owner instanceof PlayerEntity) {
                if (!((PlayerEntity) fc.owner).inventory.insertStack(stack)) {
                    fc.owner.dropStack(fc.inventory.removeStack(slot));
                }
            } else {
                fc.owner.dropStack(fc.inventory.removeStack(slot));
            }
        }
        fc.inventory.addStack(stack);
    }

    @Override
    public List<ItemStack> generateLootDrops(Random random, int looting){
        return new ArrayList<>();
    }


}
