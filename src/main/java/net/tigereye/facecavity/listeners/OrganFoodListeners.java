package net.tigereye.facecavity.listeners;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.registration.CCItems;
import net.tigereye.facecavity.registration.CCOrganScores;
import net.tigereye.facecavity.registration.CCStatusEffects;
import net.tigereye.facecavity.registration.CCTags;

public class OrganFoodListeners {

    public static void register(){
        OrganFoodCallback.EVENT.register(OrganFoodListeners::applyHerbivorousCarnivorous);
        OrganFoodCallback.EVENT.register(OrganFoodListeners::applyRot);
        OrganFoodCallback.EVENT.register(OrganFoodListeners::applyFurnacePower);
    }

    private static EffectiveFoodScores applyHerbivorousCarnivorous(Item food, FoodComponent foodComponent, FaceCavityEntity cce, EffectiveFoodScores efs) {
        if(foodComponent.isMeat() || food.getDefaultStack().isIn(CCTags.CARNIVORE_FOOD)){
            efs.digestion += cce.getFaceCavityInstance().getOrganScore(CCOrganScores.CARNIVOROUS_DIGESTION);
            efs.nutrition += cce.getFaceCavityInstance().getOrganScore(CCOrganScores.CARNIVOROUS_NUTRITION);
        }
        else{
            efs.digestion += cce.getFaceCavityInstance().getOrganScore(CCOrganScores.HERBIVOROUS_DIGESTION);
            efs.nutrition += cce.getFaceCavityInstance().getOrganScore(CCOrganScores.HERBIVOROUS_NUTRITION);
        }
        return efs;
    }

    private static EffectiveFoodScores applyRot(Item food, FoodComponent foodComponent, FaceCavityEntity cce, EffectiveFoodScores efs) {
        if(food.getDefaultStack().isIn(CCTags.ROTTEN_FOOD)){
            efs.digestion += cce.getFaceCavityInstance().getOrganScore(CCOrganScores.ROT_DIGESTION);
            efs.nutrition += cce.getFaceCavityInstance().getOrganScore(CCOrganScores.ROTGUT);
        }
        return efs;
    }

    private static EffectiveFoodScores applyFurnacePower(Item food, FoodComponent foodComponent, FaceCavityEntity cce, EffectiveFoodScores efs) {
        if(food == CCItems.FURNACE_POWER){
            int power = 0;
            if(cce.getFaceCavityInstance().owner.hasStatusEffect(CCStatusEffects.FURNACE_POWER)){
                power = cce.getFaceCavityInstance().owner.getStatusEffect(CCStatusEffects.FURNACE_POWER).getAmplifier() + 1;
            }
            //herbivorous will have gotten a false positive, so that needs corrected
            efs.digestion -= cce.getFaceCavityInstance().getOrganScore(CCOrganScores.HERBIVOROUS_DIGESTION);
            efs.nutrition -= cce.getFaceCavityInstance().getOrganScore(CCOrganScores.HERBIVOROUS_NUTRITION);
            //nutrition scales with furnaces
            efs.nutrition += power;
        }
        return efs;
    }
}
