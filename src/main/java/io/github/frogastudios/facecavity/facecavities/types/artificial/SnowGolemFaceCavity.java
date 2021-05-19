package io.github.frogastudios.facecavity.facecavities.types.artificial;

import io.github.frogastudios.facecavity.registration.FCOrganScores;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.facecavities.types.BaseFaceCavity;

import java.util.Map;

public class SnowGolemFaceCavity extends BaseFaceCavity implements FaceCavityType {
    @Override
    public void fillFaceCavityInventory(FaceCavityInventory faceCavity) {
        faceCavity.clear();

    }

    @Override
    public boolean catchExceptionalOrgan(ItemStack slot, Map<Identifier, Float> organScores){
        //charcoal functions as heart and bone and spine
        //snowballs function as muscle
        if(slot.getItem() == Items.SNOWBALL){
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.STRENGTH, slot.getCount()/(3f*slot.getMaxCount()),organScores);
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.SPEED, slot.getCount()/(3f*slot.getMaxCount()),organScores);
            return true;
        }
        if(slot.getItem() == Items.CHARCOAL){
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.HEALTH, slot.getCount()*(1f/3),organScores);
            FaceCavityUtil.addOrganScore(FCOrganScores.DEFENSE, slot.getCount()*(4.75f/3),organScores);
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.NERVOUS_SYSTEM, slot.getCount()*(1f/3),organScores);
            return true;
        }
        if(slot.getItem() == Items.SNOW_BLOCK){
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.STRENGTH, slot.getCount()/(1f*slot.getMaxCount()),organScores);
            io.github.frogastudios.facecavity.util.FaceCavityUtil.addOrganScore(FCOrganScores.SPEED, slot.getCount()/(1f*slot.getMaxCount()),organScores);
            return true;
        }
        return false;
    }

    @Override
    public void loadBaseOrganScores(Map<Identifier, Float> organScores){
        //snow golems dont breath, don't eat, and dont have blood
        //as such they don't need the organs related to such
        organScores.clear();
        organScores.put(FCOrganScores.LUCK, 1f);
        organScores.put(FCOrganScores.NUTRITION, 4f);
        organScores.put(FCOrganScores.FILTRATION, 2f);
        organScores.put(FCOrganScores.DETOXIFICATION, 1f);
        organScores.put(FCOrganScores.METABOLISM, 1f);
        organScores.put(FCOrganScores.DIGESTION, 1f);
        organScores.put(FCOrganScores.BREATH, 2f);
    }

}
