package io.github.frogastudios.facecavity.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import io.github.frogastudios.facecavity.FaceCavity;

public class CCModMenuIntegration implements ModMenuApi {
    @Override
    public String getModId() {
        return FaceCavity.MODID; // Return your modid here
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(CCConfig.class, parent).get();
    }
}