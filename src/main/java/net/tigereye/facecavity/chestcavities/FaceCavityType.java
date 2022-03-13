package net.tigereye.facecavity.chestcavities;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;
import net.tigereye.facecavity.chestcavities.organs.OrganData;

import java.util.List;
import java.util.Map;
import java.util.Random;

public interface FaceCavityType {

    public Map<Identifier,Float> getDefaultOrganScores();
    public float getDefaultOrganScore(Identifier id);
    public FaceCavityInventory getDefaultFaceCavity();
    public boolean isSlotForbidden(int index);

    public void fillFaceCavityInventory(FaceCavityInventory chestCavity);
    public void loadBaseOrganScores(Map<Identifier, Float> organScores);
    public OrganData catchExceptionalOrgan(ItemStack slot);

    public List<ItemStack> generateLootDrops(Random random, int looting);

    public void setOrganCompatibility(FaceCavityInstance instance);
    public float getHeartBleedCap();
    public boolean isOpenable(FaceCavityInstance instance);
    public void onDeath(FaceCavityInstance instance);
}
