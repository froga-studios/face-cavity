package com.frogastudios.facecavity.listeners;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import com.frogastudios.facecavity.chestcavities.instance.ChestCavityInstance;

public interface OrganTickCallback {
    Event<OrganTickCallback> EVENT = EventFactory.createArrayBacked(OrganTickCallback.class,
            (listeners) -> (player, chestCavity) -> {
                for (OrganTickCallback listener : listeners) {
                    listener.onOrganTick(player,chestCavity);
                }
            });

    void onOrganTick(LivingEntity player, ChestCavityInstance chestCavity);
}
