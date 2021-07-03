package com.frogastudios.facecavity.listeners;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import com.frogastudios.facecavity.chestcavities.instance.ChestCavityInstance;

public interface OrganOnHitListener {
    float onHit(DamageSource source, LivingEntity attacker, LivingEntity target, ChestCavityInstance chestCavity, ItemStack organ, float damage);
}
