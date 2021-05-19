package io.github.frogastudios.facecavity.facecavities.instance;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.registration.FCFaceCavityTypes;

import java.util.HashMap;
import java.util.Map;

public class FaceCavityInstanceFactory {

    private static final Map<Identifier, FaceCavityType> entityIdentifierMap = new HashMap<>();

    public static FaceCavityInstance newChestCavityInstance(EntityType<? extends LivingEntity> entityType, LivingEntity owner){
        if(entityIdentifierMap.containsKey(Registry.ENTITY_TYPE.getId(entityType))){
            return new FaceCavityInstance(entityIdentifierMap.get(Registry.ENTITY_TYPE.getId(entityType)),owner);
        }
        return new FaceCavityInstance(FCFaceCavityTypes.BASE_FACE_CAVITY,owner);
    }

    public static void register(EntityType<? extends LivingEntity> entityType, FaceCavityType chestCavityType){
        entityIdentifierMap.put(Registry.ENTITY_TYPE.getId(entityType),chestCavityType);
    }
    public static void register(Identifier entityIdentifier, FaceCavityType chestCavityType){
        entityIdentifierMap.put(entityIdentifier,chestCavityType);
    }
}
