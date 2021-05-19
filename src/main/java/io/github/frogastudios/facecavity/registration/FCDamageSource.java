package io.github.frogastudios.facecavity.registration;

import net.minecraft.entity.damage.DamageSource;

public class FCDamageSource extends DamageSource {
    public static final DamageSource HEARTBLEED = new FCDamageSource("ccHeartbleed").setBypassesArmor();
    public static final DamageSource ORGAN_REJECTION = new FCDamageSource("ccOrganRejection").setBypassesArmor();

    public FCDamageSource(String name) {
        super(name);
    }


}
