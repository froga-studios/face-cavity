package net.tigereye.facecavity.crossmod.requiem;

import net.minecraft.util.Identifier;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.crossmod.CrossModContent;

public class CCRequiem {
    public static String MODID = "requiem";
    public static String NAME = "Requiem";
    public static boolean REQUIEM_ACTIVE = false;
    public static Identifier PLAYER_SHELL_ID = new Identifier(MODID,"player_shell");

    public static void register(){
        if(CrossModContent.checkIntegration(MODID,NAME,FaceCavity.config.REQUIEM_INTEGRATION)){
            REQUIEM_ACTIVE = true;
        }
    }
}
