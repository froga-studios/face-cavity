package io.github.frogastudios.facecavity.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public interface FCHungerManagerInterface {
    
    public void fcEat(Item item, PlayerEntity player);
}