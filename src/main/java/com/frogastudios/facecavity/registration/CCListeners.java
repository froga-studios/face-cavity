package com.frogastudios.facecavity.registration;

import com.frogastudios.facecavity.listeners.*;
import net.tigereye.chestcavity.listeners.*;

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
