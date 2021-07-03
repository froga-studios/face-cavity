package com.frogastudios.facecavity.listeners;

import com.frogastudios.facecavity.registration.CCOrganScores;
import com.frogastudios.facecavity.registration.CCTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import com.frogastudios.facecavity.chestcavities.instance.ChestCavityInstance;

import java.util.List;

public class OrganFoodEffectListeners {

    public static void register(){
        OrganFoodEffectCallback.EVENT.register(OrganFoodEffectListeners::applyRotgut);
    }

    private static List<Pair<StatusEffectInstance, Float>> applyRotgut(List<Pair<StatusEffectInstance, Float>> list, ItemStack itemStack, World world, LivingEntity entity, ChestCavityInstance cc) {
        float rotten = cc.getOrganScore(CCOrganScores.ROTGUT)+cc.getOrganScore(CCOrganScores.ROT_DIGESTION);
        if(rotten > 0){
            if(itemStack.getItem().isIn(CCTags.ROTTEN_FOOD)) {
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
