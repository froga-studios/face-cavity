package net.tigereye.facecavity.chestcavities.instance;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.chestcavities.FaceCavityType;
import net.tigereye.facecavity.chestcavities.types.DefaultFaceCavityType;
import net.tigereye.facecavity.chestcavities.types.json.GeneratedFaceCavityAssignmentManager;
import net.tigereye.facecavity.chestcavities.types.json.GeneratedFaceCavityTypeManager;

import java.util.HashMap;
import java.util.Map;

public class FaceCavityInstanceFactory {

    private static final Map<Identifier, FaceCavityType> entityIdentifierMap = new HashMap<>();
    private static final FaceCavityType DEFAULT_CHEST_CAVITY_TYPE = new DefaultFaceCavityType();

    public static FaceCavityInstance newFaceCavityInstance(EntityType<? extends LivingEntity> entityType, LivingEntity owner){
        Identifier entityID = Registry.ENTITY_TYPE.getId(entityType);
        if(GeneratedFaceCavityAssignmentManager.GeneratedFaceCavityAssignments.containsKey(entityID)){
            Identifier chestCavityTypeID = GeneratedFaceCavityAssignmentManager.GeneratedFaceCavityAssignments.get(entityID);
            if(GeneratedFaceCavityTypeManager.GeneratedFaceCavityTypes.containsKey(chestCavityTypeID)){
                return new FaceCavityInstance(GeneratedFaceCavityTypeManager.GeneratedFaceCavityTypes.get(chestCavityTypeID),owner);
            }
        }
        if(entityIdentifierMap.containsKey(entityID)){
            return new FaceCavityInstance(entityIdentifierMap.get(Registry.ENTITY_TYPE.getId(entityType)),owner);
        }
        return new FaceCavityInstance(DEFAULT_CHEST_CAVITY_TYPE,owner);
    }

    public static void register(EntityType<? extends LivingEntity> entityType,FaceCavityType chestCavityType){
        entityIdentifierMap.put(Registry.ENTITY_TYPE.getId(entityType),chestCavityType);
    }
    public static void register(Identifier entityIdentifier, FaceCavityType chestCavityType){
        entityIdentifierMap.put(entityIdentifier,chestCavityType);
    }
}
