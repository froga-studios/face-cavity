package net.tigereye.facecavity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.tigereye.facecavity.chestcavities.organs.OrganManager;
import net.tigereye.facecavity.chestcavities.types.json.GeneratedFaceCavityAssignmentManager;
import net.tigereye.facecavity.chestcavities.types.json.GeneratedFaceCavityTypeManager;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.listeners.KeybindingClientListeners;
import net.tigereye.facecavity.registration.CCKeybindings;
import net.tigereye.facecavity.util.NetworkUtil;
import net.tigereye.facecavity.registration.CCNetworkingPackets;
import net.tigereye.facecavity.ui.FaceCavityScreen;

import java.util.Optional;

public class FaceCavityClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(FaceCavity.FACE_CAVITY_SCREEN_HANDLER, FaceCavityScreen::new);

        CCNetworkingPackets.registerClient();
        CCKeybindings.register();
        KeybindingClientListeners.register();
    }
}
