package net.tigereye.facecavity.mob_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.registration.CCItems;

import java.util.Optional;

public class FurnacePower extends CCStatusEffect{

    public FurnacePower(){
        super(StatusEffectCategory.BENEFICIAL, 0xC8FF00);
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity){
            if(!(entity.world.isClient)) {
                Optional<FaceCavityEntity> optional = FaceCavityEntity.of(entity);
                if (optional.isPresent()) {
                    FaceCavityEntity cce = optional.get();
                    FaceCavityInstance cc = cce.getFaceCavityInstance();
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
