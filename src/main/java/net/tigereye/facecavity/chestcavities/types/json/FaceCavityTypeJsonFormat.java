package net.tigereye.facecavity.chestcavities.types.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FaceCavityTypeJsonFormat {
    JsonArray defaultFaceCavity;
    JsonArray baseOrganScores;
    JsonArray exceptionalOrgans;
    JsonArray forbiddenSlots;
    boolean bossFaceCavity = false;
    boolean playerFaceCavity = false;
    float dropRateMultiplier = 1;
}
