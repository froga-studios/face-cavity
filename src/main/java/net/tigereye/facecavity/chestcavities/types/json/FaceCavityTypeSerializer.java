package net.tigereye.facecavity.chestcavities.types.json;

import com.google.gson.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.chestcavities.FaceCavityInventory;
import net.tigereye.facecavity.chestcavities.types.GeneratedFaceCavityType;

import java.util.*;

public class FaceCavityTypeSerializer {
    public GeneratedFaceCavityType read(Identifier id, FaceCavityTypeJsonFormat cctJson) {
        //FaceCavityTypeJsonFormat cctJson = new Gson().fromJson(json, FaceCavityTypeJsonFormat.class);

        if (cctJson.defaultFaceCavity == null) {
            throw new JsonSyntaxException("Chest Cavity Types must have a default chest cavity!");
        }

        if (cctJson.exceptionalOrgans == null) cctJson.exceptionalOrgans = new JsonArray();
        if (cctJson.baseOrganScores == null) cctJson.baseOrganScores = new JsonArray();
        if (cctJson.forbiddenSlots == null) cctJson.forbiddenSlots = new JsonArray();
        //bossFaceCavity defaults to false
        //playerFaceCavity defaults to false
        //dropRateMultiplier defaults to true

        GeneratedFaceCavityType cct = new GeneratedFaceCavityType();
        cct.setForbiddenSlots(readForbiddenSlotsFromJson(id,cctJson));
        cct.setDefaultFaceCavity(readDefaultFaceCavityFromJson(id,cctJson,cct.getForbiddenSlots()));
        cct.setBaseOrganScores(readBaseOrganScoresFromJson(id,cctJson));
        cct.setExceptionalOrganList(readExceptionalOrgansFromJson(id,cctJson));
        cct.setDropRateMultiplier(cctJson.dropRateMultiplier);
        cct.setPlayerFaceCavity(cctJson.playerFaceCavity);
        cct.setBossFaceCavity(cctJson.bossFaceCavity);

        /*
        Ingredient input = Ingredient.fromJson(recipeJson.ingredient);
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.result))
                // Validate the inputted item actually exists
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.result));
        ItemStack output = new ItemStack(outputItem, recipeJson.count);
        */


        return cct;
    }

    private FaceCavityInventory readDefaultFaceCavityFromJson(Identifier id, FaceCavityTypeJsonFormat cctJson, List<Integer> forbiddenSlots){
        FaceCavityInventory inv = new FaceCavityInventory();
        int i = 0;
        for (JsonElement entry:
                cctJson.defaultFaceCavity) {
            ++i;
            try {
                JsonObject obj = entry.getAsJsonObject();
                if (!obj.has("item")) {
                    FaceCavity.LOGGER.error("Missing item component in entry no." + i + " in " + id.toString() + "'s default chest cavity");
                } else if (!obj.has("position")) {
                    FaceCavity.LOGGER.error("Missing position component in entry no. " + i + " in " + id.toString() + "'s default chest cavity");
                } else {
                    Identifier itemID = new Identifier(obj.get("item").getAsString());
                    Optional<Item> itemOptional = Registry.ITEM.getOrEmpty(new Identifier(obj.get("item").getAsString()));
                    if (itemOptional.isPresent()) {
                        Item item = itemOptional.get();
                        ItemStack stack;
                        if (obj.has("count")) {
                            int count = obj.get("count").getAsInt();
                            stack = new ItemStack(item, count);
                        } else {
                            stack = new ItemStack(item, item.getMaxCount());
                        }
                        int pos = obj.get("position").getAsInt();
                        if(pos >= inv.size()) {
                            FaceCavity.LOGGER.error("Position component is out of bounds in entry no. " + i + " in " + id.toString() + "'s default chest cavity");
                        }
                        else if(forbiddenSlots.contains(pos)){
                            FaceCavity.LOGGER.error("Position component is forbidden in entry no. " + i + " in " + id.toString() + "'s default chest cavity");
                        }
                        else{
                            inv.setStack(pos, stack);
                        }
                    } else {
                        FaceCavity.LOGGER.error("Unknown "+itemID.toString()+" in entry no. " + i + " in " + id.toString() + "'s default chest cavity");
                    }

                }
            }
            catch(Exception e){
                FaceCavity.LOGGER.error("Error parsing entry no. " + i + " in " + id.toString() + "'s default chest cavity");
            }
        }
        return inv;
    }

    private Map<Identifier,Float> readBaseOrganScoresFromJson(Identifier id, FaceCavityTypeJsonFormat cctJson){
        return readOrganScoresFromJson(id, cctJson.baseOrganScores);
    }

    private Map<Ingredient, Map<Identifier,Float>> readExceptionalOrgansFromJson(Identifier id, FaceCavityTypeJsonFormat cctJson){
        Map<Ingredient, Map<Identifier,Float>> exceptionalOrgans = new HashMap<>();

        int i = 0;
        for (JsonElement entry:
                cctJson.exceptionalOrgans) {
            ++i;
            try {
                JsonObject obj = entry.getAsJsonObject();
                if (!obj.has("ingredient")) {
                    FaceCavity.LOGGER.error("Missing ingredient component in entry no." + i + " in " + id.toString() + "'s exceptional organs");
                } else if (!obj.has("value")) {
                    FaceCavity.LOGGER.error("Missing value component in entry no. " + i + " in " + id.toString() + "'s exceptional organs");
                } else {
                    Ingredient ingredient = Ingredient.fromJson(obj.get("ingredient"));
                    exceptionalOrgans.put(ingredient,readOrganScoresFromJson(id,obj.get("value").getAsJsonArray()));
                }
            }
            catch(Exception e){
                FaceCavity.LOGGER.error("Error parsing entry no. " + i + " in " + id.toString() + "'s exceptional organs");
            }
        }

        return exceptionalOrgans;
    }

    private Map<Identifier,Float> readOrganScoresFromJson(Identifier id, JsonArray json){
        Map<Identifier,Float> organScores = new HashMap<>();
        for (JsonElement entry:
                json) {
            try {
                JsonObject obj = entry.getAsJsonObject();
                if (!obj.has("id")) {
                    FaceCavity.LOGGER.error("Missing id component in " + id.toString() + "'s organ scores");
                } else if (!obj.has("value")) {
                    FaceCavity.LOGGER.error("Missing value component in " + id.toString() + "'s organ scores");
                } else {
                    Identifier ability = new Identifier(obj.get("id").getAsString());
                    organScores.put(ability,obj.get("value").getAsFloat());
                }
            }
            catch(Exception e){
                FaceCavity.LOGGER.error("Error parsing " + id.toString() + "'s organ scores!");
            }
        }
        return organScores;
    }

    private List<Integer> readForbiddenSlotsFromJson(Identifier id, FaceCavityTypeJsonFormat cctJson){
        List<Integer> list = new ArrayList<>();
        for (JsonElement entry:
                cctJson.forbiddenSlots) {
            try {
                int slot = entry.getAsInt();
                list.add(slot);
            }
            catch(Exception e){
                FaceCavity.LOGGER.error("Error parsing " + id.toString() + "'s organ scores!");
            }
        }
        return list;
    }
}
