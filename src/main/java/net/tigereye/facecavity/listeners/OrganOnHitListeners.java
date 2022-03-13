package net.tigereye.facecavity.listeners;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;
import net.tigereye.facecavity.registration.CCDamageSource;
import net.tigereye.facecavity.registration.CCOrganScores;
import net.tigereye.facecavity.registration.CCStatusEffects;
import net.tigereye.facecavity.util.OrganUtil;

import java.util.Iterator;
import java.util.List;

public class OrganOnHitListeners {

    public static void register(){
        OrganOnHitCallback.EVENT.register(OrganOnHitListeners::TickLaunching);
    }

    private static void TickLaunching(LivingEntity attacker, LivingEntity target, FaceCavityInstance cc) {
        float launchingDiff = cc.getOrganScore(CCOrganScores.LAUNCHING)-cc.getFaceCavityType().getDefaultOrganScore(CCOrganScores.LAUNCHING);
        if(launchingDiff != 0 && attacker.isInRange(target,4)){
            double KBRes = target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            target.addVelocity(0,Math.max(0,FaceCavity.config.LAUNCHING_POWER*launchingDiff*(1 - KBRes)),0);
        }
    }
}