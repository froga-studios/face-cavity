package io.github.frogastudios.facecavity.registration;

import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstanceFactory;
import io.github.frogastudios.facecavity.facecavities.types.*;
import io.github.frogastudios.facecavity.facecavities.types.artificial.BlazeFaceCavity;
import io.github.frogastudios.facecavity.facecavities.types.artificial.SnowGolemFaceCavity;
import io.github.frogastudios.facecavity.facecavities.types.humanoid.EndermanFaceCavity;
import io.github.frogastudios.facecavity.facecavities.types.humanoid.HumanFaceCavity;
import io.github.frogastudios.facecavity.facecavities.types.humanoid.PlayerFaceCavity;
import io.github.frogastudios.facecavity.facecavities.types.insects.BeeFaceCavity;
import io.github.frogastudios.facecavity.facecavities.types.insects.CaveSpiderFaceCavity;
import io.github.frogastudios.facecavity.facecavities.types.insects.InsectFaceCavity;
import io.github.frogastudios.facecavity.facecavities.types.insects.SpiderFaceCavity;
import io.github.frogastudios.facecavity.facecavities.types.undead.SkeletonFaceCavity;
import io.github.frogastudios.facecavity.facecavities.types.undead.WitherFaceCavity;
import io.github.frogastudios.facecavity.facecavities.types.undead.WitherSkeletonFaceCavity;
import io.github.frogastudios.facecavity.facecavities.types.undead.ZombieFaceCavity;
import net.minecraft.entity.EntityType;

public class FCFaceCavityTypes {
    public static final FaceCavityType BASE_FACE_CAVITY = new BaseFaceCavity();
    public static final FaceCavityType EMPTY_FACE_CAVITY = new EmptyFaceCavity();
    public static final FaceCavityType NULL_FACE_CAVITY = new NullFaceCavity();

 /*   public static final FaceCavityType OMNIVORE_FACE_CAVITY = new OmnivoreFaceCavity();
    public static final FaceCavityType CARNIVORE_FACE_CAVITY = new CarnivoreChestCavity();
    public static final FaceCavityType DOLPHIN_FACE_CAVITY = new DolphinChestCavity();
    public static final FaceCavityType HERBIVORE_FACE_CAVITY = new HerbivoreChestCavity();
    public static final FaceCavityType LARGE_FISH_FACE_CAVITY = new LargeFishChestCavity();
    public static final FaceCavityType LLAMA_FACE_CAVITY = new LlamaChestCavity();
    public static final FaceCavityType PUFFERFISH_FACE_CAVITY = new PufferfishChestCavity();
    public static final FaceCavityType RABBIT_FACE_CAVITY = new RabbitChestCavity();
    public static final FaceCavityType RUMINANT_FACE_CAVITY = new RuminantChestCavity();
    public static final FaceCavityType SMALL_OMNIVORE_FACE_CAVITY = new SmallOmnivoreChestCavity();
    public static final FaceCavityType SMALL_CARNIVORE_FACE_CAVITY = new SmallCarnivoreChestCavity();
    public static final FaceCavityType SMALL_FISH_FACE_CAVITY = new SmallFishChestCavity();
    public static final FaceCavityType SMALL_HERBIVORE_FACE_CAVITY = new SmallHerbivoreChestCavity();
    public static final FaceCavityType TURTLE_FACE_CAVITY = new TurtleChestCavity();*/

    public static final FaceCavityType BEE_FACE_CAVITY = new BeeFaceCavity();
    public static final FaceCavityType CAVE_SPIDER_FACE_CAVITY = new CaveSpiderFaceCavity();
    public static final FaceCavityType INSECT_FACE_CAVITY = new InsectFaceCavity();
    public static final FaceCavityType SPIDER_FACE_CAVITY = new SpiderFaceCavity();

    public static final FaceCavityType SKELETON_FACE_CAVITY = new SkeletonFaceCavity();
   // public static final FaceCavityType WITHER_FACE_CAVITY = new WitherFaceCavity();
    public static final FaceCavityType WITHER_SKELETON_FACE_CAVITY = new WitherSkeletonFaceCavity();
    public static final FaceCavityType ZOMBIE_FACE_CAVITY = new ZombieFaceCavity();

    public static final FaceCavityType ENDERMAN_FACE_CAVITY = new EndermanFaceCavity();
    public static final FaceCavityType HUMAN_FACE_CAVITY = new HumanFaceCavity();
    public static final FaceCavityType PLAYER_FACE_CAVITY = new PlayerFaceCavity();

    public static final FaceCavityType BLAZE_FACE_CAVITY = new BlazeFaceCavity();
    public static final FaceCavityType SNOW_GOLEM_FACE_CAVITY = new SnowGolemFaceCavity();

    public static final FaceCavityType CREEPER_FACE_CAVITY = new CreeperFaceCavity();
    public static final FaceCavityType DRAGON_FACE_CAVITY = new DragonFaceCavity();
    public static final FaceCavityType GHAST_FACE_CAVITY = new GhastFaceCavity();
    public static final FaceCavityType SHULKER_FACE_CAVITY = new ShulkerFaceCavity();
    public static final FaceCavityType SLIME_FACE_CAVITY = new SlimeFaceCavity();

    public static void register(){
  /*      FaceCavityInstanceFactory.register(EntityType.BAT, SMALL_OMNIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.CAT, SMALL_CARNIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.CHICKEN, SMALL_OMNIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.FOX, SMALL_CARNIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.RABBIT, RABBIT_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.OCELOT, SMALL_CARNIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.PARROT, SMALL_OMNIVORE_FACE_CAVITY);

        FaceCavityInstanceFactory.register(EntityType.COD, SMALL_FISH_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.PUFFERFISH,PUFFERFISH_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.SALMON,SMALL_FISH_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.TROPICAL_FISH,SMALL_FISH_FACE_CAVITY);

        FaceCavityInstanceFactory.register(EntityType.COW, RUMINANT_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.DOLPHIN, DOLPHIN_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.DONKEY, HERBIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.HORSE, HERBIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.LLAMA, LLAMA_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.TRADER_LLAMA, LLAMA_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.MOOSHROOM, RUMINANT_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.MULE, HERBIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.PANDA, CARNIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.PIG, OMNIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.POLAR_BEAR, CARNIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.SHEEP, RUMINANT_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.STRIDER, OMNIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.TURTLE, TURTLE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.WOLF, CARNIVORE_FACE_CAVITY);
*/
   //     FaceCavityInstanceFactory.register(EntityType.HOGLIN, OMNIVORE_FACE_CAVITY);
      //  FaceCavityInstanceFactory.register(EntityType.RAVAGER, OMNIVORE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.SHULKER, SHULKER_FACE_CAVITY);
        //FaceCavityInstanceFactory.register(EntityType.PIGLIN, OMNIVORE_FACE_CAVITY);
        //FaceCavityInstanceFactory.register(EntityType.PIGLIN_BRUTE, OMNIVORE_FACE_CAVITY);

        FaceCavityInstanceFactory.register(EntityType.BEE, BEE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.SPIDER,SPIDER_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.CAVE_SPIDER,CAVE_SPIDER_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.ENDERMITE,INSECT_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.SILVERFISH,INSECT_FACE_CAVITY);

        //FaceCavityInstanceFactory.register(EntityType.SQUID,LARGE_FISH_FACE_CAVITY);
        //FaceCavityInstanceFactory.register(EntityType.ELDER_GUARDIAN,LARGE_FISH_FACE_CAVITY);
        //FaceCavityInstanceFactory.register(EntityType.GUARDIAN,LARGE_FISH_FACE_CAVITY);

        FaceCavityInstanceFactory.register(EntityType.DROWNED,ZOMBIE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.HUSK,ZOMBIE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.PHANTOM,ZOMBIE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.ZOGLIN,ZOMBIE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.ZOMBIFIED_PIGLIN,ZOMBIE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.ZOMBIE,ZOMBIE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.ZOMBIE_HORSE,ZOMBIE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.ZOMBIE_VILLAGER,ZOMBIE_FACE_CAVITY);

        FaceCavityInstanceFactory.register(EntityType.SKELETON,SKELETON_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.SKELETON_HORSE,SKELETON_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.STRAY,SKELETON_FACE_CAVITY);

        FaceCavityInstanceFactory.register(EntityType.WITHER_SKELETON,WITHER_SKELETON_FACE_CAVITY);
        //FaceCavityInstanceFactory.register(EntityType.WITHER,WITHER_FACE_CAVITY);

        FaceCavityInstanceFactory.register(EntityType.EVOKER, HUMAN_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.PILLAGER, HUMAN_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.VILLAGER, HUMAN_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.VINDICATOR, HUMAN_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.WANDERING_TRADER, HUMAN_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.WITCH, HUMAN_FACE_CAVITY);
        //FaceCavityInstanceFactory.register(EntityType.VEX, SMALL_OMNIVORE_FACE_CAVITY);

        FaceCavityInstanceFactory.register(EntityType.BLAZE,BLAZE_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.CREEPER, CREEPER_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.ENDER_DRAGON, DRAGON_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.ENDERMAN, ENDERMAN_FACE_CAVITY);
        //FaceCavityInstanceFactory.register(EntityType.IRON_GOLEM, IRON_GOLEM_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.GHAST, GHAST_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.MAGMA_CUBE, SLIME_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.SLIME, SLIME_FACE_CAVITY);
        FaceCavityInstanceFactory.register(EntityType.SNOW_GOLEM, SNOW_GOLEM_FACE_CAVITY);

        FaceCavityInstanceFactory.register(EntityType.PLAYER, PLAYER_FACE_CAVITY);
    }
}
