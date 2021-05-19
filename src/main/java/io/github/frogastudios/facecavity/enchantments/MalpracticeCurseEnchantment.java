package io.github.frogastudios.facecavity.enchantments;

import io.github.frogastudios.facecavity.registration.FCEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class MalpracticeCurseEnchantment extends Enchantment {
    public MalpracticeCurseEnchantment(){
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public int getMinPower(int level) {
        return 25;
    }

    public int getMaxPower(int level) {
        return 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return true;
    }

    public boolean isTreasure() {
        return true;
    }

    public boolean canAccept(Enchantment other) {
        return super.canAccept(other)
                && other != FCEnchantments.TOMOPHOBIA;
    }

    public boolean isCursed() {
        return true;
    }
}