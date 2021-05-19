package io.github.frogastudios.facecavity.enchantments;

import io.github.frogastudios.facecavity.registration.FCEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.entity.EquipmentSlot;

public class SurgicalEnchantment extends LuckEnchantment {
    public SurgicalEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND);
    }

    public int getMinPower(int level) {
        return 15 + (level - 1) * 9;
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean canAccept(Enchantment other) {
        return super.canAccept(other) &&
                other.canCombine(Enchantments.LOOTING)
                && other != FCEnchantments.TOMOPHOBIA;
    }
}
