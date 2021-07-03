package com.frogastudios.facecavity.listeners;

import com.frogastudios.facecavity.registration.CCOrganScores;
import com.frogastudios.facecavity.registration.CCStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import com.frogastudios.facecavity.ChestCavity;
import com.frogastudios.facecavity.chestcavities.instance.ChestCavityInstance;

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

    public static void UpdateAppendix(LivingEntity entity, ChestCavityInstance cc) {
        //Update Max Health Modifier
        if(cc.getOldOrganScore(CCOrganScores.LUCK) != cc.getOrganScore(CCOrganScores.LUCK)){
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_LUCK);
            if(att != null) {
                EntityAttributeModifier mod = new EntityAttributeModifier(APPENDIX_ID, "ChestCavityAppendixLuck",
                        (cc.getOrganScore(CCOrganScores.LUCK) - cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.LUCK))
                                * ChestCavity.config.APPENDIX_LUCK, EntityAttributeModifier.Operation.ADDITION);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    public static void UpdateHeart(LivingEntity entity, ChestCavityInstance cc) {
        //Update Max Health Modifier
        if(cc.getOldOrganScore(CCOrganScores.HEALTH) != cc.getOrganScore(CCOrganScores.HEALTH)){
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if(att != null) {
                EntityAttributeModifier mod = new EntityAttributeModifier(HEART_ID, "ChestCavityHeartMaxHP",
                        (cc.getOrganScore(CCOrganScores.HEALTH) - cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.HEALTH))
                                * ChestCavity.config.HEART_HP, EntityAttributeModifier.Operation.ADDITION);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    public static void UpdateStrength(LivingEntity entity, ChestCavityInstance cc) {
        if(cc.getOldOrganScore(CCOrganScores.STRENGTH) != cc.getOrganScore(CCOrganScores.STRENGTH)) {
            //Update Damage Modifier and Speed Modifier
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            if (att != null) {
                EntityAttributeModifier mod = new EntityAttributeModifier(MUSCLE_STRENGTH_ID, "ChestCavityMuscleAttackDamage",
                        (cc.getOrganScore(CCOrganScores.STRENGTH) - cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.STRENGTH))
                        * ChestCavity.config.MUSCLE_STRENGTH / 8, EntityAttributeModifier.Operation.MULTIPLY_BASE);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    public static void UpdateSpeed(LivingEntity entity, ChestCavityInstance cc) {
        if(cc.getOldOrganScore(CCOrganScores.SPEED) != cc.getOrganScore(CCOrganScores.SPEED)) {
            //Update Damage Modifier and Speed Modifier
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if(att != null) {
                EntityAttributeModifier mod = new EntityAttributeModifier(MUSCLE_SPEED_ID, "ChestCavityMovementSpeed",
                        (cc.getOrganScore(CCOrganScores.SPEED) - cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.SPEED))
                                * ChestCavity.config.MUSCLE_SPEED / 8, EntityAttributeModifier.Operation.MULTIPLY_BASE);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    public static void UpdateSpine(LivingEntity entity, ChestCavityInstance cc) {
        if(cc.getOldOrganScore(CCOrganScores.NERVES) != cc.getOrganScore(CCOrganScores.NERVES)
                && cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.NERVES) != 0) {
            //Update Speed Modifier. No spine? NO MOVING.
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if(att != null) {
                EntityAttributeModifier mod = new EntityAttributeModifier(SPINE_ID, "ChestCavitySpineMovement",
                        Math.min(0, cc.getOrganScore(CCOrganScores.NERVES) - cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.NERVES))
                        / cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.NERVES), EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    public static void UpdateKnockbackResistance(LivingEntity entity, ChestCavityInstance cc) {
        if(cc.getOldOrganScore(CCOrganScores.KNOCKBACK_RESISTANT) != cc.getOrganScore(CCOrganScores.KNOCKBACK_RESISTANT)) {
            //Update Knockback Res Modifier
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            if(att != null) {
                EntityAttributeModifier mod = new EntityAttributeModifier(KNOCKBACK_RESISTANCE_ID, "ChestCavityKnockbackResistance",
                        (cc.getOrganScore(CCOrganScores.KNOCKBACK_RESISTANT) - cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.KNOCKBACK_RESISTANT))
                                * .1, EntityAttributeModifier.Operation.ADDITION);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    public static void UpdateIncompatibility(LivingEntity entity, ChestCavityInstance cc) {
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
