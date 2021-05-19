package io.github.frogastudios.facecavity.listeners;

import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface OrganUpdateCallback {
    Event<OrganUpdateCallback> EVENT = EventFactory.createArrayBacked(OrganUpdateCallback.class,
            (listeners) -> (player, chestCavity) -> {
                for (OrganUpdateCallback listener : listeners) {
                    listener.onOrganUpdate(player,chestCavity);
                }
            });

    void onOrganUpdate(LivingEntity player, FaceCavityInstance chestCavity);
}
