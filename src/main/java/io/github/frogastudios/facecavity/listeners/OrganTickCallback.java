package io.github.frogastudios.facecavity.listeners;

import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface OrganTickCallback {
    Event<OrganTickCallback> EVENT = EventFactory.createArrayBacked(OrganTickCallback.class,
            (listeners) -> (player, chestCavity) -> {
                for (OrganTickCallback listener : listeners) {
                    listener.onOrganTick(player,chestCavity);
                }
            });

    void onOrganTick(LivingEntity player, FaceCavityInstance chestCavity);
}
