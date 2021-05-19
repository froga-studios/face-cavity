package io.github.frogastudios.facecavity.registration;

import io.github.frogastudios.facecavity.listeners.*;

public class FCListeners {
    public static void register(){
        OrganTickListeners.register();
        OrganAddStatusEffectListeners.register();
        OrganFoodListeners.register();
        OrganFoodEffectListeners.register();
        OrganOnHitListeners.register();
    }
}
