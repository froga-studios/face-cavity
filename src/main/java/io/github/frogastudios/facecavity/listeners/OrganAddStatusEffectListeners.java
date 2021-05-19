package io.github.frogastudios.facecavity.listeners;

import io.github.frogastudios.facecavity.FaceCavity;
import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.tigereye.chestcavity.interfaces.CCStatusEffect;
import net.tigereye.chestcavity.interfaces.CCStatusEffectInstance;

public class OrganAddStatusEffectListeners {

    public static void register(){
        OrganAddStatusEffectCallback.EVENT.register(OrganAddStatusEffectListeners::ApplyBuffPurging);
        OrganAddStatusEffectCallback.EVENT.register(OrganAddStatusEffectListeners::ApplyDetoxification);
        OrganAddStatusEffectCallback.EVENT.register(OrganAddStatusEffectListeners::ApplyWithered);
    }

    private static StatusEffectInstance ApplyBuffPurging(LivingEntity entity, FaceCavityInstance cc, StatusEffectInstance instance) {
        if(cc.getOrganScore(FCOrganScores.BUFF_PURGING) > 0
                && ((CCStatusEffect)(instance.getEffectType())).CC_IsBeneficial())
        {
            CCStatusEffectInstance ccInstance = (CCStatusEffectInstance) instance;
            ccInstance.CC_setDuration((int)(instance.getDuration()/
                    (1+(FaceCavity.config.BUFF_PURGING_DURATION_FACTOR*cc.getOrganScore(FCOrganScores.BUFF_PURGING)))));
        }
        return instance;
    }

    private static StatusEffectInstance ApplyDetoxification(LivingEntity entity, FaceCavityInstance cc, StatusEffectInstance instance) {
        if(cc.getChestCavityType().getDefaultOrganScore(FCOrganScores.DETOXIFICATION) <= 0
        || cc.getOrganScore(FCOrganScores.DETOXIFICATION) == cc.getChestCavityType().getDefaultOrganScore(FCOrganScores.DETOXIFICATION))
        {
            return instance;
        }
        CCStatusEffect ccStatusEffect = (CCStatusEffect)instance.getEffectType();
        if(ccStatusEffect.CC_IsHarmful()){
            CCStatusEffectInstance ccInstance = (CCStatusEffectInstance) instance;
            float detoxRatio = cc.getOrganScore(FCOrganScores.DETOXIFICATION)/cc.getChestCavityType().getDefaultOrganScore(FCOrganScores.DETOXIFICATION);
            ccInstance.CC_setDuration((int) Math.max(1,instance.getDuration() * 2 / (1 + detoxRatio)));
        }
        return instance;
    }

    private static StatusEffectInstance ApplyFiltration(LivingEntity entity, FaceCavityInstance cc, StatusEffectInstance instance) {
        float filtrationDiff = cc.getOrganScore(FCOrganScores.FILTRATION) - cc.getChestCavityType().getDefaultOrganScore(FCOrganScores.FILTRATION);
        if(filtrationDiff > 0
                && instance.getEffectType() == StatusEffects.POISON)
        {
            CCStatusEffectInstance ccInstance = (CCStatusEffectInstance) instance;
            ccInstance.CC_setDuration((int)(instance.getDuration()/
                    (1+(FaceCavity.config.FILTRATION_DURATION_FACTOR*cc.getOrganScore(FCOrganScores.FILTRATION)))));
        }
        return instance;
    }

    private static StatusEffectInstance ApplyWithered(LivingEntity entity, FaceCavityInstance cc, StatusEffectInstance instance) {
        if(cc.getOrganScore(FCOrganScores.WITHERED) > 0
                && instance.getEffectType() == StatusEffects.WITHER)
        {
            CCStatusEffectInstance ccInstance = (CCStatusEffectInstance) instance;
            ccInstance.CC_setDuration((int)(instance.getDuration()/
                    (1+(FaceCavity.config.WITHERED_DURATION_FACTOR*cc.getOrganScore(FCOrganScores.WITHERED)))));
        }
        return instance;
    }
}
