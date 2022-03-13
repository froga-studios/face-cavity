package net.tigereye.facecavity.crossmod;

import net.fabricmc.loader.api.FabricLoader;
import net.tigereye.facecavity.crossmod.backrooms.CCBackrooms;
import net.tigereye.facecavity.crossmod.requiem.CCRequiem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrossModContent {
    public static final Logger LOGGER = LogManager.getLogger();

    public static void register(){
        CCBackrooms.register();
        CCRequiem.register();
    }

    public static boolean checkIntegration(String modid, String name, boolean config){
        if (FabricLoader.getInstance().isModLoaded(modid)){
            CrossModContent.LOGGER.info("[Chest Cavity] "+name+" Detected!");
            if(config) {
                CrossModContent.LOGGER.info("[Chest Cavity] Integrating with "+name);
                return true;
            }
            else{
                CrossModContent.LOGGER.info("[Chest Cavity] "+name+" integration has been disabled in the config.");
            }
        }
        return false;
    }
}
