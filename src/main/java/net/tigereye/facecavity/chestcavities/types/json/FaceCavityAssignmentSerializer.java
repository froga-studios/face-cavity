package net.tigereye.facecavity.chestcavities.types.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.Identifier;
import net.tigereye.facecavity.FaceCavity;

import java.util.HashMap;
import java.util.Map;

public class FaceCavityAssignmentSerializer {
    //remember: the first identifier is the entity, the second is the chest cavity type
    public Map<Identifier, Identifier> read(Identifier id, FaceCavityAssignmentJsonFormat ccaJson) {

        if (ccaJson.facecavity == null) {
            throw new JsonSyntaxException("Chest cavity assignment " + id + " must have a chest cavity type");
        }
        if (ccaJson.entities == null) {
            throw new JsonSyntaxException("Chest cavity assignment " + id + " must have a list of entities");
        }
        //bossFaceCavity should default to false
        //playerFaceCavity should default to false

        Map<Identifier, Identifier> assignments = new HashMap<>();
        Identifier facecavitytype = new Identifier(ccaJson.facecavity);
        int i = 0;
        for (JsonElement entry :
                ccaJson.entities) {
            ++i;
            try {
                assignments.put(new Identifier(entry.getAsString()),facecavitytype);
            } catch (Exception e) {
                FaceCavity.LOGGER.error("Error parsing entry no. " + i + " in " + id.toString() + "'s entity list");
            }
        }
        return assignments;
    }
}