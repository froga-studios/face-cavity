package net.tigereye.facecavity.mixin;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.registration.CCOrganScores;
import net.tigereye.facecavity.util.MathUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ExperienceOrbEntity.class, priority = 1100)
public abstract class MixinExperienceOrbEntity extends Entity {
    @Shadow private int amount;

    public MixinExperienceOrbEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject( at = @At("HEAD"),method = "onPlayerCollision")
    public void onPlayerCollision(PlayerEntity player,CallbackInfo info) {
        if (!this.world.isClient && player.experiencePickUpDelay == 0){
            FaceCavityEntity.of(player).ifPresent(ccPlayerEntityInterface -> {
                float xp_gain_stat = ccPlayerEntityInterface.getFaceCavityInstance().getOrganScore(CCOrganScores.XP_GAIN);
                double xp_multiplier = Math.pow(xp_gain_stat,0.3);
                this.amount= MathUtil.quantum_round(xp_multiplier*this.amount);
            });
        }
    }

}
