package io.github.frogastudios.facecavity.facecavities.types;

import io.github.frogastudios.facecavity.registration.FCOrganScores;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;

import java.util.Map;

public class NullFaceCavity extends BaseFaceCavity implements FaceCavityType {
    @Override
    public void fillFaceCavityInventory(FaceCavityInventory faceCavity) {
        faceCavity.clear();
    }

    @Override
    public boolean catchExceptionalOrgan(ItemStack slot, Map<Identifier, Float> organScores){
        return true;
    }

    @Override
    public void loadBaseOrganScores(Map<Identifier, Float> organScores){
        organScores.clear();
        organScores.put(FCOrganScores.LUCK, 1f);
        organScores.put(FCOrganScores.DEFENSE, 4.75f);
        organScores.put(FCOrganScores.HEALTH, 1f);
        organScores.put(FCOrganScores.NUTRITION, 4f);
        organScores.put(FCOrganScores.FILTRATION, 2f);
        organScores.put(FCOrganScores.DETOXIFICATION, 1f);
        organScores.put(FCOrganScores.STRENGTH, 8f);
        organScores.put(FCOrganScores.SPEED, 8f);
        organScores.put(FCOrganScores.NERVOUS_SYSTEM, 1f);
        organScores.put(FCOrganScores.METABOLISM, 1f);
        organScores.put(FCOrganScores.DIGESTION, 1f);
        organScores.put(FCOrganScores.BREATH, 2f);
        organScores.put(FCOrganScores.ENDURANCE, 2f);
    }
}
