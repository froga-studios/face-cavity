package io.github.frogastudios.facecavity.registration;

import io.github.frogastudios.facecavity.FaceCavity;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class FCTags {
    public static final Tag<Item> BUTCHERING_TOOL = TagRegistry.item(new Identifier(FaceCavity.MODID,"butchering_tool"));
    public static final Tag<Item> ROTTEN_FOOD = TagRegistry.item(new Identifier(FaceCavity.MODID,"rotten_food"));
    public static final Tag<Item> SALVAGEABLE = TagRegistry.item(new Identifier(FaceCavity.MODID,"salvageable"));
}
