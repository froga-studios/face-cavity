package com.frogastudios.facecavity.mixin;

import com.frogastudios.facecavity.interfaces.ChestCavityEntity;
import com.frogastudios.facecavity.listeners.OrganOnHitCallback;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper {

    @Inject(at = @At("TAIL"), method = "onTargetDamaged")
    private static void chestCavityDealDamageMixin(LivingEntity user, Entity target, CallbackInfo info) {
        Optional<ChestCavityEntity> cce = ChestCavityEntity.of(user);
        if (cce.isPresent() && target instanceof LivingEntity) {
            OrganOnHitCallback.EVENT.invoker().onHit(user, (LivingEntity)target, cce.get().getChestCavityInstance());
        }
    }
    
}