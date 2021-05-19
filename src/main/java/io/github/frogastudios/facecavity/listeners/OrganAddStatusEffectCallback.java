package io.github.frogastudios.facecavity.listeners;

import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public interface OrganAddStatusEffectCallback {
    Event<OrganAddStatusEffectCallback> EVENT = EventFactory.createArrayBacked(OrganAddStatusEffectCallback.class,
            (listeners) -> (player, chestCavity, statusEffectInstance) -> {
                for (OrganAddStatusEffectCallback listener : listeners) {
                    statusEffectInstance = listener.onAddStatusEffect(player,chestCavity,statusEffectInstance);
                }
                return statusEffectInstance;
            });

    StatusEffectInstance onAddStatusEffect(LivingEntity player, FaceCavityInstance chestCavity, StatusEffectInstance statusEffectInstance);
}
