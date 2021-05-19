package io.github.frogastudios.facecavity.facecavities;

import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.Random;

public interface FaceCavityType {

    public Map<Identifier,Float> getDefaultOrganScores();
    public float getDefaultOrganScore(Identifier id);
    public FaceCavityInventory getDefaultChestCavity();
    public boolean isSlotForbidden(int index);

    public void fillFaceCavityInventory(FaceCavityInventory chestCavity);
    public void shapeFaceCavity();
    public void loadBaseOrganScores(Map<Identifier, Float> organScores);
    public boolean catchExceptionalOrgan(ItemStack slot,Map<Identifier, Float> organScores);

    public List<ItemStack> generateLootDrops(Random random, int looting);

    public void setOrganCompatibility(FaceCavityInstance instance);
    public float getHeartBleedCap();
    public boolean isOpenable(FaceCavityInstance instance);
    public void onDeath(FaceCavityInstance instance);
}
