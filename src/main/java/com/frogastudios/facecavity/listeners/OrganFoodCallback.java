package com.frogastudios.facecavity.listeners;

import com.frogastudios.facecavity.interfaces.ChestCavityEntity;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;

public interface OrganFoodCallback {
    Event<OrganFoodCallback> EVENT = EventFactory.createArrayBacked(OrganFoodCallback.class,
            (listeners) -> (item, foodComponent, cce, efs) -> {
                for (OrganFoodCallback listener : listeners) {
                    efs = listener.onEatFood(item, foodComponent, cce, efs);
                }
                return efs;
            });

    EffectiveFoodScores onEatFood(Item item, FoodComponent foodComponent, ChestCavityEntity cce, EffectiveFoodScores efs);
}
