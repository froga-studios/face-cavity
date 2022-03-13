package net.tigereye.facecavity.interfaces;

import net.minecraft.entity.Entity;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;

import java.util.Optional;

public interface FaceCavityEntity {
    static Optional<FaceCavityEntity> of(Entity entity) {
        if (entity instanceof FaceCavityEntity) {
            return Optional.of(((FaceCavityEntity) entity));
        }
        return Optional.empty();
    }

    FaceCavityInstance getFaceCavityInstance();
    void setFaceCavityInstance(FaceCavityInstance cc);
}
