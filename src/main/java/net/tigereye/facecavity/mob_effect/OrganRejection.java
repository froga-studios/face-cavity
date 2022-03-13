package net.tigereye.facecavity.mob_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.registration.CCDamageSource;

import java.util.Optional;

public class OrganRejection extends CCStatusEffect{

    public OrganRejection(){
        super(StatusEffectCategory.NEUTRAL, 0xC8FF00);
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration <= 1;
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!(entity.world.isClient)){
            entity.damage(CCDamageSource.ORGAN_REJECTION, FaceCavity.config.ORGAN_REJECTION_DAMAGE);
        }
    }
}
