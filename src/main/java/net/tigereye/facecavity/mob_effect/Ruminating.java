package net.tigereye.facecavity.mob_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.registration.CCItems;

public class Ruminating extends CCStatusEffect{

    public Ruminating(){
        super(StatusEffectCategory.BENEFICIAL, 0xC8FF00);
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % FaceCavity.config.RUMINATION_TIME == 1;
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
