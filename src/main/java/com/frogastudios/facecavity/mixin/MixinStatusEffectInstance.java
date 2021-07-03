package com.frogastudios.facecavity.mixin;

import com.frogastudios.facecavity.interfaces.CCStatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StatusEffectInstance.class)
public abstract class MixinStatusEffectInstance implements CCStatusEffectInstance {

	@Shadow
	private int duration;

	public void CC_setDuration(int duration) {
		this.duration = duration;
	}
}
