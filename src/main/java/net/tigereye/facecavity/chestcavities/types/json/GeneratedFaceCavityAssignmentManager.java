package net.tigereye.facecavity.chestcavities.types.json;

import com.google.gson.Gson;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.tigereye.facecavity.FaceCavity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class GeneratedFaceCavityAssignmentManager implements SimpleSynchronousResourceReloadListener {
    private static final String RESOURCE_LOCATION = "entity_assignment";
    private final FaceCavityAssignmentSerializer SERIALIZER = new FaceCavityAssignmentSerializer();
    public static Map<Identifier, Identifier> GeneratedFaceCavityAssignments = new HashMap<>();

    @Override
    public Identifier getFabricId() {
        return new Identifier(FaceCavity.MODID, RESOURCE_LOCATION);
    }

    @Override
    public void reload(ResourceManager manager) {
        GeneratedFaceCavityAssignments.clear();
        FaceCavity.LOGGER.info("Loading chest cavity assignments.");
        for(Identifier id : manager.findResources(RESOURCE_LOCATION, path -> path.endsWith(".json"))) {
            try(InputStream stream = manager.getResource(id).getInputStream()) {
                Reader reader = new InputStreamReader(stream);
                GeneratedFaceCavityAssignments.putAll(SERIALIZER.read(id,new Gson().fromJson(reader,FaceCavityAssignmentJsonFormat.class)));
            } catch(Exception e) {
                FaceCavity.LOGGER.error("Error occurred while loading resource json " + id.toString(), e);
            }
        }
        FaceCavity.LOGGER.info("Loaded "+GeneratedFaceCavityAssignments.size()+" chest cavity assignments.");
    }
}
