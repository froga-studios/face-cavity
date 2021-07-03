package com.frogastudios.facecavity.mob_effect;

import com.frogastudios.facecavity.registration.CCDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import com.frogastudios.facecavity.ChestCavity;

public class OrganRejection extends CCStatusEffect{

    public OrganRejection(){
        super(StatusEffectType.NEUTRAL, 0xC8FF00);
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration <= 1;
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!(entity.world.isClient)){
            entity.damage(CCDamageSource.ORGAN_REJECTION, ChestCavity.config.ORGAN_REJECTION_DAMAGE);
        }
    }
}
