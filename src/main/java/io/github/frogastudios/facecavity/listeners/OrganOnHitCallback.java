package io.github.frogastudios.facecavity.listeners;

import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface OrganOnHitCallback {
    Event<OrganOnHitCallback> EVENT = EventFactory.createArrayBacked(OrganOnHitCallback.class,
            (listeners) -> (attacker, target, chestCavity) -> {
                for (OrganOnHitCallback listener : listeners) {
                    listener.onHit(attacker, target, chestCavity);
                }
            });

    void onHit(LivingEntity attacker, LivingEntity target, FaceCavityInstance chestCavity);
}
