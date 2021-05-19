package io.github.frogastudios.facecavity.listeners;

import com.mojang.datafixers.util.Pair;
import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import io.github.frogastudios.facecavity.registration.FCTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class OrganFoodEffectListeners {

    public static void register(){
        OrganFoodEffectCallback.EVENT.register(OrganFoodEffectListeners::applyRotgut);
    }

    private static List<Pair<StatusEffectInstance, Float>> applyRotgut(List<Pair<StatusEffectInstance, Float>> list, ItemStack itemStack, World world, LivingEntity entity, FaceCavityInstance cc) {
        float rotten = cc.getOrganScore(FCOrganScores.ROTGUT)+cc.getOrganScore(FCOrganScores.ROT_DIGESTION);
        if(rotten > 0){
            if(itemStack.getItem().isIn(FCTags.ROTTEN_FOOD)) {
                list.removeIf(pair -> pair.getFirst().getEffectType() == StatusEffects.HUNGER);
            }
            else {
                StatusEffectInstance hunger = new StatusEffectInstance(StatusEffects.HUNGER, 600);
                list.add(new Pair<>(hunger, rotten * .2f));
            }
        }
        return list;
    }
}
