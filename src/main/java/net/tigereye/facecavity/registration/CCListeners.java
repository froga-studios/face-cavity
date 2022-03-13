package net.tigereye.facecavity.registration;

import net.tigereye.facecavity.listeners.*;

public class CCListeners {
    public static void register(){
        LootRegister.register();
        OrganUpdateListeners.register();
        OrganTickListeners.register();
        OrganActivationListeners.register();
        OrganAddStatusEffectListeners.register();
        OrganFoodListeners.register();
        OrganFoodEffectListeners.register();
        OrganOnHitListeners.register();
    }
}
