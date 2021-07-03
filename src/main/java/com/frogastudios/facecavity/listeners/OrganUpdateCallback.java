package com.frogastudios.facecavity.listeners;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import com.frogastudios.facecavity.chestcavities.instance.ChestCavityInstance;

public interface OrganUpdateCallback {
    Event<OrganUpdateCallback> EVENT = EventFactory.createArrayBacked(OrganUpdateCallback.class,
            (listeners) -> (player, chestCavity) -> {
                for (OrganUpdateCallback listener : listeners) {
                    listener.onOrganUpdate(player,chestCavity);
                }
            });

    void onOrganUpdate(LivingEntity player, ChestCavityInstance chestCavity);
}
