package io.github.frogastudios.facecavity;

import io.github.frogastudios.facecavity.ui.FaceCavityScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import io.github.frogastudios.facecavity.registration.FCNetworkingPackets;

public class FaceCavityClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(FaceCavity.FACE_CAVITY_SCREEN_HANDLER, FaceCavityScreen::new);
        FCNetworkingPackets.registerClient();
    }
}
