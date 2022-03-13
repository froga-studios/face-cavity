package net.tigereye.facecavity.listeners;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;

public interface OrganOnHitListener {
    float onHit(DamageSource source, LivingEntity attacker, LivingEntity target, FaceCavityInstance chestCavity, ItemStack organ, float damage);
}
