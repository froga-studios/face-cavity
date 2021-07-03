package com.frogastudios.facecavity.listeners;

import com.frogastudios.facecavity.interfaces.ChestCavityEntity;
import com.frogastudios.facecavity.registration.CCItems;
import com.frogastudios.facecavity.registration.CCOrganScores;
import com.frogastudios.facecavity.registration.CCStatusEffects;
import com.frogastudios.facecavity.registration.CCTags;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;

public class OrganFoodListeners {

    public static void register(){
        OrganFoodCallback.EVENT.register(OrganFoodListeners::applyHerbivorousCarnivorous);
        OrganFoodCallback.EVENT.register(OrganFoodListeners::applyRot);
        OrganFoodCallback.EVENT.register(OrganFoodListeners::applyFurnacePower);
    }

    private static EffectiveFoodScores applyHerbivorousCarnivorous(Item food, FoodComponent foodComponent, ChestCavityEntity cce, EffectiveFoodScores efs) {
        if(foodComponent.isMeat() || food.isIn(CCTags.CARNIVORE_FOOD)){
            efs.digestion += cce.getChestCavityInstance().getOrganScore(CCOrganScores.CARNIVOROUS_DIGESTION);
            efs.nutrition += cce.getChestCavityInstance().getOrganScore(CCOrganScores.CARNIVOROUS_NUTRITION);
        }
        else{
            efs.digestion += cce.getChestCavityInstance().getOrganScore(CCOrganScores.HERBIVOROUS_DIGESTION);
            efs.nutrition += cce.getChestCavityInstance().getOrganScore(CCOrganScores.HERBIVOROUS_NUTRITION);
        }
        return efs;
    }

    private static EffectiveFoodScores applyRot(Item food, FoodComponent foodComponent, ChestCavityEntity cce, EffectiveFoodScores efs) {
        if(food.isIn(CCTags.ROTTEN_FOOD)){
            efs.digestion += cce.getChestCavityInstance().getOrganScore(CCOrganScores.ROT_DIGESTION);
            efs.nutrition += cce.getChestCavityInstance().getOrganScore(CCOrganScores.ROTGUT);
        }
        return efs;
    }

    private static EffectiveFoodScores applyFurnacePower(Item food, FoodComponent foodComponent, ChestCavityEntity cce, EffectiveFoodScores efs) {
        if(food == CCItems.FURNACE_POWER){
            int power = 0;
            if(cce.getChestCavityInstance().owner.hasStatusEffect(CCStatusEffects.FURNACE_POWER)){
                power = cce.getChestCavityInstance().owner.getStatusEffect(CCStatusEffects.FURNACE_POWER).getAmplifier() + 1;
            }
            //herbivorous will have gotten a false positive, so that needs corrected
            efs.digestion -= cce.getChestCavityInstance().getOrganScore(CCOrganScores.HERBIVOROUS_DIGESTION);
            efs.nutrition -= cce.getChestCavityInstance().getOrganScore(CCOrganScores.HERBIVOROUS_NUTRITION);
            //nutrition scales with furnaces
            efs.nutrition += power;
        }
        return efs;
    }
}
