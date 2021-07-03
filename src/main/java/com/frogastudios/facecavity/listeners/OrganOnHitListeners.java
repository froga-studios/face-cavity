package com.frogastudios.facecavity.listeners;

import com.frogastudios.facecavity.registration.CCOrganScores;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import com.frogastudios.facecavity.ChestCavity;
import com.frogastudios.facecavity.chestcavities.instance.ChestCavityInstance;

public class OrganOnHitListeners {

    public static void register(){
        OrganOnHitCallback.EVENT.register(OrganOnHitListeners::TickLaunching);
    }

    private static void TickLaunching(LivingEntity attacker, LivingEntity target, ChestCavityInstance cc) {
        float launchingDiff = cc.getOrganScore(CCOrganScores.LAUNCHING)-cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.LAUNCHING);
        if(launchingDiff != 0 && attacker.isInRange(target,4)){
            double KBRes = target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            target.addVelocity(0,Math.max(0,ChestCavity.config.LAUNCHING_POWER*launchingDiff*(1 - KBRes)),0);
        }
    }
}