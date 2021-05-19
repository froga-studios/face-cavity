package io.github.frogastudios.facecavity.registration;

import io.github.frogastudios.facecavity.FaceCavity;
import io.github.frogastudios.facecavity.items.ChestOpener;
import io.github.frogastudios.facecavity.items.FaceOpener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class FCItems {

	public static final Item.Settings FACE_OPENER_SETTINGS = new Item.Settings().maxCount(1).group(ItemGroup.TOOLS);
	public static final Item FACE_OPENER = new ChestOpener();
	public static void register() {
		registerItem("face_opener", FACE_OPENER);
	}
	private static void registerItem(String name, Item item) {
		Registry.register(Registry.ITEM, FaceCavity.MODID + ":" + name, item);
    }
}
