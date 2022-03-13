package net.tigereye.facecavity.listeners;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;
import net.tigereye.facecavity.registration.CCOrganScores;
import net.tigereye.facecavity.registration.CCStatusEffects;

import java.util.UUID;

public class OrganUpdateListeners {

    private static final UUID APPENDIX_ID = UUID.fromString("ac606ec3-4cc3-42b5-9399-7fa8ceba8722");
    private static final UUID HEART_ID = UUID.fromString("edb1e124-a951-48bd-b711-782ec1364722");
    private static final UUID MUSCLE_STRENGTH_ID = UUID.fromString("bf560396-9855-496e-a942-99824467e1ad");
    private static final UUID MUSCLE_SPEED_ID = UUID.fromString("979aa156-3f01-45d3-8784-56185eeef96d");
    private static final UUID SPINE_ID = UUID.fromString("8f56feed-589f-416f-86c5-315765d41f57");
    private static final UUID KNOCKBACK_RESISTANCE_ID = UUID.fromString("673566d3-5daa-40d7-955f-cbabc27a84cf");

    public static void register(){
        OrganUpdateCallback.EVENT.register(OrganUpdateListeners::UpdateAppendix);
        OrganUpdateCallback.EVENT.register(OrganUpdateListeners::UpdateHeart);
        OrganUpdateCallback.EVENT.register(OrganUpdateListeners::UpdateStrength);
        OrganUpdateCallback.EVENT.register(OrganUpdateListeners::UpdateSpeed);
        OrganUpdateCallback.EVENT.register(OrganUpdateListeners::UpdateSpine);
        OrganUpdateCallback.EVENT.register(OrganUpdateListeners::UpdateKnockbackResistance);
        OrganUpdateCallback.EVENT.register(OrganUpdateListeners::UpdateIncompatibility);
    }

    public static void UpdateAppendix(LivingEntity entity, FaceCavityInstance cc) {
        //Update Max Health Modifier
        if(cc.getOldOrganScore(CCOrganScores.LUCK) != cc.getOrganScore(CCOrganScores.LUCK)){
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_LUCK);
            if(att != null) {
                EntityAttributeModifier mod = new EntityAttributeModifier(APPENDIX_ID, "FaceCavityAppendixLuck",
                        (cc.getOrganScore(CCOrganScores.LUCK) - cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.LUCK))
                                * FaceCavity.config.APPENDIX_LUCK, EntityAttributeModifier.Operation.ADDITION);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    public static void UpdateHeart(LivingEntity entity, FaceCavityInstance cc) {
        //Update Max Health Modifier
        if(cc.getOldOrganScore(CCOrganScores.HEALTH) != cc.getOrganScore(CCOrganScores.HEALTH)){
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if(att != null) {
                EntityAttributeModifier mod = new EntityAttributeModifier(HEART_ID, "FaceCavityHeartMaxHP",
                        (cc.getOrganScore(CCOrganScores.HEALTH) - cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.HEALTH))
                                * FaceCavity.config.HEART_HP, EntityAttributeModifier.Operation.ADDITION);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    public static void UpdateStrength(LivingEntity entity, FaceCavityInstance cc) {
        if(cc.getOldOrganScore(CCOrganScores.STRENGTH) != cc.getOrganScore(CCOrganScores.STRENGTH)) {
            //Update Damage Modifier and Speed Modifier
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            if (att != null) {
                EntityAttributeModifier mod = new EntityAttributeModifier(MUSCLE_STRENGTH_ID, "FaceCavityMuscleAttackDamage",
                        (cc.getOrganScore(CCOrganScores.STRENGTH) - cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.STRENGTH))
                        * FaceCavity.config.MUSCLE_STRENGTH / 8, EntityAttributeModifier.Operation.MULTIPLY_BASE);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    public static void UpdateSpeed(LivingEntity entity, FaceCavityInstance cc) {
        if(cc.getOldOrganScore(CCOrganScores.SPEED) != cc.getOrganScore(CCOrganScores.SPEED)) {
            //Update Damage Modifier and Speed Modifier
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if(att != null) {
                EntityAttributeModifier mod = new EntityAttributeModifier(MUSCLE_SPEED_ID, "FaceCavityMovementSpeed",
                        (cc.getOrganScore(CCOrganScores.SPEED) - cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.SPEED))
                                * FaceCavity.config.MUSCLE_SPEED / 8, EntityAttributeModifier.Operation.MULTIPLY_BASE);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    public static void UpdateSpine(LivingEntity entity, FaceCavityInstance cc) {
        if(cc.getOldOrganScore(CCOrganScores.NERVES) != cc.getOrganScore(CCOrganScores.NERVES)
                && cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.NERVES) != 0) {
            //Update Speed Modifier. No spine? NO MOVING.
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if(att != null) {
                EntityAttributeModifier mod = new EntityAttributeModifier(SPINE_ID, "FaceCavitySpineMovement",
                        Math.min(0, cc.getOrganScore(CCOrganScores.NERVES) - cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.NERVES))
                        / cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.NERVES), EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    public static void UpdateKnockbackResistance(LivingEntity entity, FaceCavityInstance cc) {
        if(cc.getOldOrganScore(CCOrganScores.KNOCKBACK_RESISTANT) != cc.getOrganScore(CCOrganScores.KNOCKBACK_RESISTANT)) {
            //Update Knockback Res Modifier
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            if(att != null) {
                EntityAttributeModifier mod = new EntityAttributeModifier(KNOCKBACK_RESISTANCE_ID, "FaceCavityKnockbackResistance",
                        (cc.getOrganScore(CCOrganScores.KNOCKBACK_RESISTANT) - cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.KNOCKBACK_RESISTANT))
                                * .1, EntityAttributeModifier.Operation.ADDITION);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    public static void UpdateIncompatibility(LivingEntity entity, FaceCavityInstance cc) {
        if(cc.getOldOrganScore(CCOrganScores.INCOMPATIBILITY) != cc.getOrganScore(CCOrganScores.INCOMPATIBILITY)) {
            try {
                entity.removeStatusEffect(CCStatusEffects.ORGAN_REJECTION);
            }
            catch(Exception ignore){}
        }
    }

    private static void ReplaceAttributeModifier(EntityAttributeInstance att, EntityAttributeModifier mod)
    {
        //removes any existing mod and replaces it with the updated one.
        att.removeModifier(mod);
        att.addPersistentModifier(mod);
    }
}
