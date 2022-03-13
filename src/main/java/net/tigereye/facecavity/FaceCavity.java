package net.tigereye.facecavity;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.tigereye.facecavity.chestcavities.organs.OrganManager;
import net.tigereye.facecavity.chestcavities.types.json.GeneratedFaceCavityAssignmentManager;
import net.tigereye.facecavity.chestcavities.types.json.GeneratedFaceCavityTypeManager;
import net.tigereye.facecavity.config.CCConfig;
import net.tigereye.facecavity.crossmod.CrossModContent;
import net.fabricmc.api.ModInitializer;
import net.tigereye.facecavity.registration.*;
import net.tigereye.facecavity.ui.FaceCavityScreenHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaceCavity implements ModInitializer {
	public static final String MODID = "facecavity";
    public static final boolean DEBUG_MODE = false;
	public static final Logger LOGGER = LogManager.getLogger();
	public static CCConfig config;
	public static final ScreenHandlerType<FaceCavityScreenHandler> FACE_CAVITY_SCREEN_HANDLER;
	public static final Identifier FACE_CAVITY_SCREEN_ID = new Identifier(MODID,"chest_cavity_screen");
	public static final Identifier COMPATIBILITY_TAG = new Identifier(MODID,"organ_compatibility");
	public static final ItemGroup ORGAN_ITEM_GROUP = FabricItemGroupBuilder.build(
			new Identifier(MODID, "organs"),
			() -> new ItemStack(CCItems.HUMAN_STOMACH));

	//public static final ScreenHandlerType<ScreenHandler> FACE_CAVITY_SCREEN_HANDLER;

	static{
		FACE_CAVITY_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(FACE_CAVITY_SCREEN_ID, FaceCavityScreenHandler::new);
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
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new GeneratedFaceCavityTypeManager());
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new GeneratedFaceCavityAssignmentManager());
		CrossModContent.register();


	}
}
