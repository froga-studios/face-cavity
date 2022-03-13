package net.tigereye.facecavity.crossmod.backrooms;

import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.crossmod.CrossModContent;

public class CCBackrooms {
    public static final String MODID = "backrooms";
    private static final String NAME = "Backrooms";

    public static void register(){
        if(CrossModContent.checkIntegration(MODID,NAME,FaceCavity.config.BACKROOMS_INTEGRATION)){
            CCBackroomsLootRegister.register();
        }
    }
}
