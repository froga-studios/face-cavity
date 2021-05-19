package io.github.frogastudios.facecavity.facecavities.types;

import io.github.frogastudios.facecavity.registration.FCOrganScores;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;

import java.util.Map;

public class SlimeFaceCavity extends BaseFaceCavity implements FaceCavityType {
    protected static final float heartbleedCap = 5f;

    @Override
    public void fillFaceCavityInventory(FaceCavityInventory chestCavity) {
        chestCavity.clear();
        chestCavity.setStack(4, new ItemStack(Items.SLIME_BALL, 1));
    }

    @Override
    public boolean catchExceptionalOrgan(ItemStack slot, Map<Identifier, Float> organScores){
        if(slot.getItem() == Items.SLIME_BALL){
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.HEALTH, slot.getCount()*.5f,organScores);
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.STRENGTH, slot.getCount(),organScores);
            FaceCavityUtil.addOrganScore(FCOrganScores.SPEED, slot.getCount(),organScores);
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.DEFENSE, slot.getCount(),organScores);
            return true;
        }
        return false;
    }

    @Override
    public void loadBaseOrganScores(Map<Identifier, Float> organScores){
        organScores.clear();
        organScores.put(FCOrganScores.HEALTH, 0.5f);
    }
}
