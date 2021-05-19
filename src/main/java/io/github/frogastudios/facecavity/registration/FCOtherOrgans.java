package io.github.frogastudios.facecavity.registration;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class FCOtherOrgans {

    public static Map<Item,Map<Identifier,Float>> map = new HashMap<>();
    public static Map<Tag<Item>,Map<Identifier,Float>> tagMap = new HashMap<>();

    public static void init(){
        Map<Identifier,Float> dirt = new HashMap<>();
        dirt.put(FCOrganScores.LUCK,1f/27);
        dirt.put(FCOrganScores.HEALTH,1f/27);
        dirt.put(FCOrganScores.STRENGTH,8f/27);
        dirt.put(FCOrganScores.SPEED,8f/27);
        dirt.put(FCOrganScores.NERVOUS_SYSTEM,1f/27);
        dirt.put(FCOrganScores.DETOXIFICATION,1f/27);
        dirt.put(FCOrganScores.FILTRATION,2f/27);
        dirt.put(FCOrganScores.ENDURANCE,2f/27);
        dirt.put(FCOrganScores.METABOLISM,1f/27);
        dirt.put(FCOrganScores.BREATH,2f/27);
        dirt.put(FCOrganScores.NUTRITION,4f/27);
        dirt.put(FCOrganScores.DEFENSE,4f/27);
        dirt.put(FCOrganScores.DIGESTION,1f/27);
        map.put(Items.DIRT,dirt);

        Map<Identifier,Float> rottenFlesh = new HashMap<>();
        rottenFlesh.put(FCOrganScores.STRENGTH,.5f);
        rottenFlesh.put(FCOrganScores.SPEED,.5f);
        map.put(Items.ROTTEN_FLESH,rottenFlesh);

        Map<Identifier,Float> animalFlesh = new HashMap<>();
        animalFlesh.put(FCOrganScores.STRENGTH,.75f);
        animalFlesh.put(FCOrganScores.SPEED,.75f);
        map.put(Items.BEEF,animalFlesh);
        map.put(Items.PORKCHOP,animalFlesh);
        map.put(Items.MUTTON,animalFlesh);

        Map<Identifier,Float> defense = new HashMap<>();
        defense.put(FCOrganScores.DEFENSE,.5f);
        map.put(Items.BONE,defense);
        //map.put(Items.IRON_BARS,defense);

        Map<Identifier,Float> ironbars = new HashMap<>();
        ironbars.put(FCOrganScores.DEFENSE,1.25f);
        ironbars.put(FCOrganScores.BUOYANT,-.5f);
        ironbars.put(FCOrganScores.SPEED,-.25f);
        ironbars.put(FCOrganScores.FIRE_RESISTANT,1f);
        map.put(Items.IRON_BARS,ironbars);

        Map<Identifier,Float> ironblock = new HashMap<>();
        ironblock.put(FCOrganScores.DEFENSE,2f);
        ironblock.put(FCOrganScores.BUOYANT,-1f);
        ironblock.put(FCOrganScores.SPEED,-1f);
        ironblock.put(FCOrganScores.FIRE_RESISTANT,1f);
        map.put(Items.IRON_BLOCK,ironblock);

        Map<Identifier,Float> goldblock = new HashMap<>();
        goldblock.put(FCOrganScores.LUCK,1.25f);
        goldblock.put(FCOrganScores.BUOYANT,-1f);
        goldblock.put(FCOrganScores.SPEED,-1f);
        goldblock.put(FCOrganScores.FIRE_RESISTANT,1f);
        map.put(Items.GOLD_BLOCK,goldblock);

        Map<Identifier,Float> emeraldblock = new HashMap<>();
        emeraldblock.put(FCOrganScores.LUCK,1f);
        emeraldblock.put(FCOrganScores.BUOYANT,-1f);
        emeraldblock.put(FCOrganScores.SPEED,-1f);
        emeraldblock.put(FCOrganScores.FIRE_RESISTANT,1f);
        map.put(Items.EMERALD_BLOCK,emeraldblock);

        Map<Identifier,Float> diamondblock = new HashMap<>();
        diamondblock.put(FCOrganScores.LUCK,1.25f);
        diamondblock.put(FCOrganScores.DEFENSE,2f);
        diamondblock.put(FCOrganScores.BUOYANT,-1f);
        diamondblock.put(FCOrganScores.SPEED,-1f);
        diamondblock.put(FCOrganScores.FIRE_RESISTANT,1f);
        map.put(Items.DIAMOND_BLOCK,diamondblock);

        Map<Identifier,Float> netheriteblock = new HashMap<>();
        netheriteblock.put(FCOrganScores.LUCK,1.25f);
        netheriteblock.put(FCOrganScores.DEFENSE,3f);
        netheriteblock.put(FCOrganScores.BUOYANT,-1.5f);
        netheriteblock.put(FCOrganScores.SPEED,-1.5f);
        netheriteblock.put(FCOrganScores.FIRE_RESISTANT,4f);
        map.put(Items.NETHERITE_BLOCK,netheriteblock);

        Map<Identifier,Float> gunpowder = new HashMap<>();
        gunpowder.put(FCOrganScores.EXPLOSIVE,3f*Items.GUNPOWDER.getMaxCount());
        map.put(Items.GUNPOWDER,gunpowder);

        Map<Identifier,Float> tnt = new HashMap<>();
        tnt.put(FCOrganScores.EXPLOSIVE,16f*Items.GUNPOWDER.getMaxCount());
        map.put(Items.TNT,tnt);

        Map<Identifier,Float> ease_of_access = new HashMap<>();
        ease_of_access.put(FCOrganScores.EASE_OF_ACCESS,1f*Items.OAK_DOOR.getMaxCount());
        tagMap.put(ItemTags.DOORS,ease_of_access);
        tagMap.put(ItemTags.TRAPDOORS,ease_of_access);

        Map<Identifier,Float> glowstoneDust = new HashMap<>();
        glowstoneDust.put(FCOrganScores.GLOWING,1f*Items.GLOWSTONE_DUST.getMaxCount());
        map.put(Items.GLOWSTONE_DUST,glowstoneDust);

        Map<Identifier,Float> glowstone = new HashMap<>();
        glowstone.put(FCOrganScores.GLOWING,2f*Items.GLOWSTONE.getMaxCount());
        map.put(Items.GLOWSTONE,glowstone);

        Map<Identifier,Float> blazerod = new HashMap<>();
        blazerod.put(FCOrganScores.PYROMANCY,1f);
        blazerod.put(FCOrganScores.HYDROALLERGENIC,1f);
        map.put(Items.BLAZE_ROD,blazerod);

        Map<Identifier,Float> obsidian = new HashMap<>();
        obsidian.put(FCOrganScores.DEFENSE,.5f);
        obsidian.put(FCOrganScores.FIRE_RESISTANT,1f);
        map.put(Items.OBSIDIAN,obsidian);
        map.put(Items.CRYING_OBSIDIAN,obsidian);
    }
}