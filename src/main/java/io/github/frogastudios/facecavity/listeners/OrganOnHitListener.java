package io.github.frogastudios.facecavity.listeners;

import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

public interface OrganOnHitListener {
    float onHit(DamageSource source, LivingEntity attacker, LivingEntity target, FaceCavityInstance chestCavity, ItemStack organ, float damage);
}
