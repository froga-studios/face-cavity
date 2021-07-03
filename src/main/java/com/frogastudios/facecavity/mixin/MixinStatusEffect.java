package com.frogastudios.facecavity.mixin;

import com.frogastudios.facecavity.interfaces.CCStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StatusEffect.class)
public class MixinStatusEffect implements CCStatusEffect {

    @Shadow
    private StatusEffectType type;

    @Override
    public boolean CC_IsHarmful() {
        return (type == StatusEffectType.HARMFUL);
    }

    @Override
    public boolean CC_IsBeneficial() {
        return (type == StatusEffectType.BENEFICIAL);
    }
}
