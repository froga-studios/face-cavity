package com.frogastudios.facecavity.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public interface CCHungerManagerInterface {
    
    public void ccEat(Item item, PlayerEntity player);
}