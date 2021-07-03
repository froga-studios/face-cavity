package com.frogastudios.facecavity.listeners;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import com.frogastudios.facecavity.chestcavities.instance.ChestCavityInstance;

public interface OrganOnHitCallback {
    Event<OrganOnHitCallback> EVENT = EventFactory.createArrayBacked(OrganOnHitCallback.class,
            (listeners) -> (attacker, target, chestCavity) -> {
                for (OrganOnHitCallback listener : listeners) {
                    listener.onHit(attacker, target, chestCavity);
                }
            });

    void onHit(LivingEntity attacker, LivingEntity target, ChestCavityInstance chestCavity);
}
