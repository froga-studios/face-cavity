package net.tigereye.facecavity.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.tigereye.facecavity.FaceCavity;

public class CCModMenuIntegration implements ModMenuApi {
    //@Override
    //public String getModId() {
    //    return FaceCavity.MODID; // Return your modid here
    //}

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(CCConfig.class, parent).get();
    }
}