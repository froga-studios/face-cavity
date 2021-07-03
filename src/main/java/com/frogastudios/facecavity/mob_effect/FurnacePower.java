package com.frogastudios.facecavity.mob_effect;

import com.frogastudios.facecavity.registration.CCItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import com.frogastudios.facecavity.chestcavities.instance.ChestCavityInstance;
import com.frogastudios.facecavity.interfaces.ChestCavityEntity;

import java.util.Optional;

public class FurnacePower extends CCStatusEffect{

    public FurnacePower(){
        super(StatusEffectType.BENEFICIAL, 0xC8FF00);
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity){
            if(!(entity.world.isClient)) {
                Optional<ChestCavityEntity> optional = ChestCavityEntity.of(entity);
                if (optional.isPresent()) {
                    ChestCavityEntity cce = optional.get();
                    ChestCavityInstance cc = cce.getChestCavityInstance();
                    cc.furnaceProgress++;
                    if (cc.furnaceProgress >= 200) {
                        cc.furnaceProgress = 0;
                        HungerManager hungerManager = ((PlayerEntity) entity).getHungerManager();
                        ItemStack furnaceFuel = new ItemStack(CCItems.FURNACE_POWER);
                        for (int i = 0; i <= amplifier; i++) {
                            hungerManager.eat(CCItems.FURNACE_POWER, furnaceFuel);
                        }
                    }
                }
            }
        }
    }
}
