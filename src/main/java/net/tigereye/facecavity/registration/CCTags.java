package net.tigereye.facecavity.registration;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.tigereye.facecavity.FaceCavity;

public class CCTags {
    private static final TagFactory<Item> factory = TagFactory.ITEM;
    public static final Tag<Item> BUTCHERING_TOOL = TagFactory.ITEM.create(new Identifier(FaceCavity.MODID,"butchering_tool"));
    public static final Tag<Item> ROTTEN_FOOD = TagFactory.ITEM.create(new Identifier(FaceCavity.MODID,"rotten_food"));
    public static final Tag<Item> CARNIVORE_FOOD = TagFactory.ITEM.create(new Identifier(FaceCavity.MODID,"carnivore_food"));
    public static final Tag<Item> SALVAGEABLE = TagFactory.ITEM.create(new Identifier(FaceCavity.MODID,"salvageable"));
    public static final Tag<Item> IRON_REPAIR_MATERIAL = TagFactory.ITEM.create(new Identifier(FaceCavity.MODID,"iron_repair_material"));

    //public static final Tag<Item> BUTCHERING_TOOL = TagRegistry.item(new Identifier(FaceCavity.MODID,"butchering_tool"));
    //public static final Tag<Item> ROTTEN_FOOD = TagRegistry.item(new Identifier(FaceCavity.MODID,"rotten_food"));
    //public static final Tag<Item> CARNIVORE_FOOD = TagRegistry.item(new Identifier(FaceCavity.MODID,"carnivore_food"));
    //public static final Tag<Item> SALVAGEABLE = TagRegistry.item(new Identifier(FaceCavity.MODID,"salvageable"));
    //public static final Tag<Item> IRON_REPAIR_MATERIAL = TagRegistry.item(new Identifier(FaceCavity.MODID,"iron_repair_material"));
}
