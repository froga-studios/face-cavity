package com.frogastudios.facecavity.registration;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import com.frogastudios.facecavity.ChestCavity;
import com.frogastudios.facecavity.enchantments.MalpracticeCurseEnchantment;
import com.frogastudios.facecavity.enchantments.ONegativeEnchantment;
import com.frogastudios.facecavity.enchantments.SurgicalEnchantment;
import com.frogastudios.facecavity.enchantments.TomophobiaEnchantment;

public class CCEnchantments {
    public static final Enchantment O_NEGATIVE = new ONegativeEnchantment();
    public static final Enchantment SURGICAL = new SurgicalEnchantment();
    public static final Enchantment MALPRACTICE = new MalpracticeCurseEnchantment();
    public static final Enchantment TOMOPHOBIA = new TomophobiaEnchantment();

    public static void register() {
        Registry.register(Registry.ENCHANTMENT, new Identifier(ChestCavity.MODID, "o_negative"), O_NEGATIVE);
        Registry.register(Registry.ENCHANTMENT, new Identifier(ChestCavity.MODID, "surgical"), SURGICAL);
        Registry.register(Registry.ENCHANTMENT, new Identifier(ChestCavity.MODID, "malpractice"), MALPRACTICE);
        Registry.register(Registry.ENCHANTMENT, new Identifier(ChestCavity.MODID, "tomophobia"), TOMOPHOBIA);
    }
}
