package io.github.frogastudios.facecavity.interfaces;

import io.github.frogastudios.facecavity.FaceCavityClient;
import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import net.minecraft.entity.Entity;
import net.tigereye.chestcavity.interfaces.ChestCavityEntity;

import java.util.Optional;

public interface FaceCavityEntity {

    static Optional<FaceCavityEntity> of(Entity entity) {
        return Optional.of(((FaceCavityEntity) entity));
    }
 ChestCavityEntity FaceCavityEntity = io.github.frogastudios.facecavity.interfaces.FaceCavityEntity;
    FaceCavityInstance getFaceCavityInstance();
    void setFaceCavityInstance(FaceCavityInstance cc);
}
