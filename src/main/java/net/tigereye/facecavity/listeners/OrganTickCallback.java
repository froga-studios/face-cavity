package net.tigereye.facecavity.listeners;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;

public interface OrganTickCallback {
    Event<OrganTickCallback> EVENT = EventFactory.createArrayBacked(OrganTickCallback.class,
            (listeners) -> (player, chestCavity) -> {
                for (OrganTickCallback listener : listeners) {
                    listener.onOrganTick(player,chestCavity);
                }
            });

    void onOrganTick(LivingEntity player, FaceCavityInstance chestCavity);
}
