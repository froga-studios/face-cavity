package com.frogastudios.facecavity.mob_effect;

import com.frogastudios.facecavity.registration.CCItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import com.frogastudios.facecavity.ChestCavity;

public class Ruminating extends CCStatusEffect{

    public Ruminating(){
        super(StatusEffectType.BENEFICIAL, 0xC8FF00);
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % ChestCavity.config.RUMINATION_TIME == 1;
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity){
            if(!(entity.world.isClient)) {
                HungerManager hungerManager = ((PlayerEntity) entity).getHungerManager();
                hungerManager.eat(CCItems.CUD, new ItemStack(CCItems.CUD));
            }
        }
    }
}
