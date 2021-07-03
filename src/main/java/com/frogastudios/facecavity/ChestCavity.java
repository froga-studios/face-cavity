package com.frogastudios.facecavity;

import com.frogastudios.facecavity.chestcavities.organs.OrganManager;
import com.frogastudios.facecavity.chestcavities.types.json.GeneratedChestCavityAssignmentManager;
import com.frogastudios.facecavity.chestcavities.types.json.GeneratedChestCavityTypeManager;
import com.frogastudios.facecavity.config.CCConfig;
import com.frogastudios.facecavity.crossmod.CrossModContent;
import com.frogastudios.facecavity.registration.*;
import com.frogastudios.facecavity.ui.ChestCavityScreenHandler;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.fabricmc.api.ModInitializer;
import net.tigereye.chestcavity.registration.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChestCavity implements ModInitializer {
	public static final String MODID = "chestcavity";
    public static final boolean DEBUG_MODE = false;
	public static final Logger LOGGER = LogManager.getLogger();
	public static CCConfig config;
	public static final ScreenHandlerType<ChestCavityScreenHandler> CHEST_CAVITY_SCREEN_HANDLER;
	public static final Identifier CHEST_CAVITY_SCREEN_ID = new Identifier(MODID,"chest_cavity_screen");
	public static final Identifier COMPATIBILITY_TAG = new Identifier(MODID,"organ_compatibility");
	public static final ItemGroup ORGAN_ITEM_GROUP = FabricItemGroupBuilder.build(
			new Identifier(MODID, "organs"),
			() -> new ItemStack(CCItems.HUMAN_STOMACH));

	//public static final ScreenHandlerType<ScreenHandler> CHEST_CAVITY_SCREEN_HANDLER;

	static{
		CHEST_CAVITY_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(CHEST_CAVITY_SCREEN_ID, ChestCavityScreenHandler::new);
	}
	@Override
	public void onInitialize() {
		//Register mod resources
		AutoConfig.register(CCConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(CCConfig.class).getConfig();
		CCItems.register();
		CCRecipes.register();
		CCEnchantments.register();
		CCListeners.register();
		CCStatusEffects.register();
		CCTagOrgans.init();
		CCCommands.register();
		CCNetworkingPackets.register();
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new OrganManager());
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new GeneratedChestCavityTypeManager());
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new GeneratedChestCavityAssignmentManager());
		CrossModContent.register();


	}
}
