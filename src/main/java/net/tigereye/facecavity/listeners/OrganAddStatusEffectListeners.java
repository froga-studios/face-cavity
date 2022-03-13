package net.tigereye.facecavity.listeners;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;
import net.tigereye.facecavity.interfaces.CCStatusEffect;
import net.tigereye.facecavity.interfaces.CCStatusEffectInstance;
import net.tigereye.facecavity.registration.CCOrganScores;

public class OrganAddStatusEffectListeners {

    public static void register(){
        OrganAddStatusEffectCallback.EVENT.register(OrganAddStatusEffectListeners::ApplyBuffPurging);
        OrganAddStatusEffectCallback.EVENT.register(OrganAddStatusEffectListeners::ApplyDetoxification);
        OrganAddStatusEffectCallback.EVENT.register(OrganAddStatusEffectListeners::ApplyWithered);
    }

    private static StatusEffectInstance ApplyBuffPurging(LivingEntity entity, FaceCavityInstance cc, StatusEffectInstance instance) {
        if(cc.getOrganScore(CCOrganScores.BUFF_PURGING) > 0
                && ((CCStatusEffect)(instance.getEffectType())).CC_IsBeneficial())
        {
            CCStatusEffectInstance ccInstance = (CCStatusEffectInstance) instance;
            ccInstance.CC_setDuration((int)(instance.getDuration()/
                    (1+(FaceCavity.config.BUFF_PURGING_DURATION_FACTOR*cc.getOrganScore(CCOrganScores.BUFF_PURGING)))));
        }
        return instance;
    }

    private static StatusEffectInstance ApplyDetoxification(LivingEntity entity, FaceCavityInstance cc, StatusEffectInstance instance) {
        if(cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.DETOXIFICATION) <= 0
        || cc.getOrganScore(CCOrganScores.DETOXIFICATION) == cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.DETOXIFICATION))
        {
            return instance;
        }
        CCStatusEffect ccStatusEffect = (CCStatusEffect)instance.getEffectType();
        if(ccStatusEffect.CC_IsHarmful()){
            CCStatusEffectInstance ccInstance = (CCStatusEffectInstance) instance;
            float detoxRatio = cc.getOrganScore(CCOrganScores.DETOXIFICATION)/cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.DETOXIFICATION);
            ccInstance.CC_setDuration((int) Math.max(1,instance.getDuration() * 2 / (1 + detoxRatio)));
        }
        return instance;
    }

    private static StatusEffectInstance ApplyFiltration(LivingEntity entity, FaceCavityInstance cc, StatusEffectInstance instance) {
        float filtrationDiff = cc.getOrganScore(CCOrganScores.FILTRATION) - cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.FILTRATION);
        if(filtrationDiff > 0
                && instance.getEffectType() == StatusEffects.POISON)
        {
            CCStatusEffectInstance ccInstance = (CCStatusEffectInstance) instance;
            ccInstance.CC_setDuration((int)(instance.getDuration()/
                    (1+(FaceCavity.config.FILTRATION_DURATION_FACTOR*cc.getOrganScore(CCOrganScores.FILTRATION)))));
        }
        return instance;
    }

    private static StatusEffectInstance ApplyWithered(LivingEntity entity, FaceCavityInstance cc, StatusEffectInstance instance) {
        if(cc.getOrganScore(CCOrganScores.WITHERED) > 0
                && instance.getEffectType() == StatusEffects.WITHER)
        {
            CCStatusEffectInstance ccInstance = (CCStatusEffectInstance) instance;
            ccInstance.CC_setDuration((int)(instance.getDuration()/
                    (1+(FaceCavity.config.WITHERED_DURATION_FACTOR*cc.getOrganScore(CCOrganScores.WITHERED)))));
        }
        return instance;
    }
}
