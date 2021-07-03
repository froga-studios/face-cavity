package com.frogastudios.facecavity;

import com.frogastudios.facecavity.listeners.KeybindingClientListeners;
import com.frogastudios.facecavity.registration.CCKeybindings;
import com.frogastudios.facecavity.registration.CCNetworkingPackets;
import com.frogastudios.facecavity.ui.ChestCavityScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.resource.ResourceType;

public class ChestCavityClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(ChestCavity.CHEST_CAVITY_SCREEN_HANDLER, ChestCavityScreen::new);

        CCNetworkingPackets.registerClient();
        CCKeybindings.register();
        KeybindingClientListeners.register();
    }
}
