package io.github.frogastudios.facecavity;


import io.github.frogastudios.facecavity.config.CCConfig;

import io.github.frogastudios.facecavity.registration.*;
import io.github.frogastudios.facecavity.ui.FaceCavityScreenHandler;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FaceCavity implements ModInitializer {
	public static final String MODID = "facecavity";
    public static final boolean DEBUG_MODE = false;
	public static final Logger LOGGER = LogManager.getLogger();
	public static CCConfig config;
	public static final ScreenHandlerType<FaceCavityScreenHandler> FACE_CAVITY_SCREEN_HANDLER;
	public static final Identifier FACE_CAVITY_SCREEN_ID = new Identifier(MODID,"face_cavity_screen");
	public static final Identifier COMPATIBILITY_TAG = new Identifier(MODID,"organ_compatibility");


	static{
		FACE_CAVITY_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(FACE_CAVITY_SCREEN_ID, FaceCavityScreenHandler::new);
	}
	@Override
	public void onInitialize() {
		//Register mod resources
		AutoConfig.register(CCConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(CCConfig.class).getConfig();
		FCItems.register();
		FCEnchantments.register();
		FCListeners.register();
		FCOtherOrgans.init();
		FCFaceCavityTypes.register();
		FCNetworkingPackets.register();
	}
}
