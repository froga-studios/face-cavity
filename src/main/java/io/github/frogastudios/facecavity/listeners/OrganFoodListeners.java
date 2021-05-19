package io.github.frogastudios.facecavity.listeners;

import io.github.frogastudios.facecavity.interfaces.FaceCavityEntity;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import io.github.frogastudios.facecavity.registration.FCTags;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;

public class OrganFoodListeners {

    public static void register(){
        OrganFoodCallback.EVENT.register(OrganFoodListeners::applyHerbivorousCarnivorous);
        OrganFoodCallback.EVENT.register(OrganFoodListeners::applyRot);
    }

    private static EffectiveFoodScores applyHerbivorousCarnivorous(Item food, FoodComponent foodComponent, FaceCavityEntity cce, EffectiveFoodScores efs) {
        if(foodComponent.isMeat()){
            efs.digestion += cce.getFaceCavityInstance().getOrganScore(FCOrganScores.CARNIVOROUS_DIGESTION);
            efs.nutrition += cce.getFaceCavityInstance().getOrganScore(FCOrganScores.CARNIVOROUS_NUTRITION);
        }
        else{
            efs.digestion += cce.getFaceCavityInstance().getOrganScore(FCOrganScores.HERBIVOROUS_DIGESTION);
            efs.nutrition += cce.getFaceCavityInstance().getOrganScore(FCOrganScores.HERBIVOROUS_NUTRITION);
        }
        return efs;
    }

    private static EffectiveFoodScores applyRot(Item food, FoodComponent foodComponent, FaceCavityEntity cce, EffectiveFoodScores efs) {
        if(food.isIn(FCTags.ROTTEN_FOOD)){
            efs.digestion += cce.getFaceCavityInstance().getOrganScore(FCOrganScores.ROT_DIGESTION);
            efs.nutrition += cce.getFaceCavityInstance().getOrganScore(FCOrganScores.ROTGUT);
        }
        return efs;
    }
}
