package net.tigereye.facecavity.listeners;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;

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
