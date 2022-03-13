package net.tigereye.facecavity.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.registration.CCOrganScores;
import net.tigereye.facecavity.util.FaceCavityUtil;
import net.tigereye.facecavity.util.OrganUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Mixin(value = PotionEntity.class, priority = 1100)
public class MixinPotionEntity extends ThrownItemEntity {

    public MixinPotionEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    @Shadow
    protected Item getDefaultItem() {
        return null;
    }

    @Inject(at = @At("TAIL"), method = "damageEntitiesHurtByWater")
    private void FaceCavityPotionEntityDamageEntitiesHurtByWaterMixin(CallbackInfo info) {
        FaceCavityUtil.splashHydrophobicWithWater((PotionEntity) (Object) this);
    }

}
