package com.frogastudios.facecavity.crossmod.backrooms;

import com.frogastudios.facecavity.ChestCavity;
import com.frogastudios.facecavity.crossmod.CrossModContent;

public class CCBackrooms {
    public static final String MODID = "backrooms";
    private static final String NAME = "Backrooms";

    public static void register(){
        if(CrossModContent.checkIntegration(MODID,NAME,ChestCavity.config.BACKROOMS_INTEGRATION)){
            CCBackroomsLootRegister.register();
        }
    }
}
