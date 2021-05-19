package io.github.frogastudios.facecavity.listeners;

import io.github.frogastudios.facecavity.FaceCavity;
import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;

public class OrganOnHitListeners {

    public static void register(){
        OrganOnHitCallback.EVENT.register(OrganOnHitListeners::TickLaunching);
    }

    private static void TickLaunching(LivingEntity attacker, LivingEntity target, FaceCavityInstance cc) {
        float launchingDiff = cc.getOrganScore(FCOrganScores.LAUNCHING)-cc.getChestCavityType().getDefaultOrganScore(FCOrganScores.LAUNCHING);
        if(launchingDiff != 0 && attacker.isInRange(target,4)){
            double KBRes = target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            target.addVelocity(0,Math.max(0, FaceCavity.config.LAUNCHING_POWER*launchingDiff*(1 - KBRes)),0);
        }
    }
}