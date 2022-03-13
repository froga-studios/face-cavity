package net.tigereye.facecavity.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.listeners.OrganOnHitCallback;
import net.tigereye.facecavity.registration.CCOrganScores;
import net.tigereye.facecavity.util.FaceCavityUtil;
import net.tigereye.facecavity.util.NetworkUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Entity.class)
public class MixinEntity {

    @ModifyVariable(at = @At("HEAD"), ordinal = 0, method = "fall")
    public double chestCavityEntityFallMixin(double finalHeightDifference, double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition){
        if (heightDifference < 0.0D) {
            Optional<FaceCavityEntity> cce = FaceCavityEntity.of((Entity) (Object) this);
            if (cce.isPresent()) {
                finalHeightDifference = heightDifference * (1 - (cce.get().getFaceCavityInstance().getOrganScore(CCOrganScores.BUOYANT)/3));
            }
        }
        return finalHeightDifference;
    }

    @Inject(at = @At("RETURN"), method = "interact", cancellable = true)
    public void chestCavityEntityInteractMixin(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info){
        if(info.getReturnValue() == ActionResult.PASS && ((Entity)(Object)this) instanceof EnderDragonPart){
            FaceCavity.LOGGER.info("Attempting to open dragon's " + ((EnderDragonPart)(Object)this).name);
            EnderDragonEntity dragon = ((EnderDragonPart)(Object)this).owner;
            if(dragon != null){
                FaceCavity.LOGGER.info("Dragon was not null");
                info.setReturnValue(dragon.interact(player,hand));
            }
        }
    }
/*
    @Inject(at = @At("TAIL"), method = "dealDamage")
    public void chestCavityDealDamageMixin(LivingEntity attacker, Entity target, CallbackInfo info) {
        Optional<FaceCavityEntity> cce = FaceCavityEntity.of(attacker);
        if (cce.isPresent() && target instanceof LivingEntity) {
            OrganOnHitCallback.EVENT.invoker().onHit(attacker, (LivingEntity)target, cce.get().getFaceCavityInstance());
        }
    }*/
    
}
