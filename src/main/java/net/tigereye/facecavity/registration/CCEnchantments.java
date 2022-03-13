package net.tigereye.facecavity.registration;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.enchantments.MalpracticeCurseEnchantment;
import net.tigereye.facecavity.enchantments.ONegativeEnchantment;
import net.tigereye.facecavity.enchantments.SurgicalEnchantment;
import net.tigereye.facecavity.enchantments.TomophobiaEnchantment;

public class CCEnchantments {
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
