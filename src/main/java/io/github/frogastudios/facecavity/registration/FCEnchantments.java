package io.github.frogastudios.facecavity.registration;

import io.github.frogastudios.facecavity.FaceCavity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import io.github.frogastudios.facecavity.enchantments.MalpracticeCurseEnchantment;
import io.github.frogastudios.facecavity.enchantments.ONegativeEnchantment;
import io.github.frogastudios.facecavity.enchantments.SurgicalEnchantment;
import io.github.frogastudios.facecavity.enchantments.TomophobiaEnchantment;

public class FCEnchantments {
    public static final Enchantment O_NEGATIVE = new ONegativeEnchantment();
    public static final Enchantment SURGICAL = new SurgicalEnchantment();
    public static final Enchantment MALPRACTICE = new MalpracticeCurseEnchantment();
    public static final Enchantment TOMOPHOBIA = new TomophobiaEnchantment();

    public static void register() {
        Registry.register(Registry.ENCHANTMENT, new Identifier(FaceCavity.MODID, "o_negative"), O_NEGATIVE);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FaceCavity.MODID, "surgical"), SURGICAL);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FaceCavity.MODID, "malpractice"), MALPRACTICE);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FaceCavity.MODID, "tomophobia"), TOMOPHOBIA);
    }
}
