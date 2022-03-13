package net.tigereye.facecavity.registration;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.mob_effect.CCStatusEffect;
import net.tigereye.facecavity.mob_effect.FurnacePower;
import net.tigereye.facecavity.mob_effect.OrganRejection;
import net.tigereye.facecavity.mob_effect.Ruminating;

public class CCStatusEffects {

    public static final StatusEffect ORGAN_REJECTION = new OrganRejection();
    public static final StatusEffect ARROW_DODGE_COOLDOWN = new CCStatusEffect(StatusEffectCategory.NEUTRAL,0x000000);
    public static final StatusEffect DRAGON_BOMB_COOLDOWN = new CCStatusEffect(StatusEffectCategory.NEUTRAL,0x000000);
    public static final StatusEffect DRAGON_BREATH_COOLDOWN = new CCStatusEffect(StatusEffectCategory.NEUTRAL,0x000000);
    public static final StatusEffect EXPLOSION_COOLDOWN = new CCStatusEffect(StatusEffectCategory.NEUTRAL,0x000000);
    public static final StatusEffect FORCEFUL_SPIT_COOLDOWN = new CCStatusEffect(StatusEffectCategory.NEUTRAL,0x000000);
    public static final StatusEffect FURNACE_POWER = new FurnacePower();
    public static final StatusEffect GHASTLY_COOLDOWN = new CCStatusEffect(StatusEffectCategory.NEUTRAL,0x000000);
    public static final StatusEffect IRON_REPAIR_COOLDOWN = new CCStatusEffect(StatusEffectCategory.NEUTRAL,0x000000);
    public static final StatusEffect PYROMANCY_COOLDOWN = new CCStatusEffect(StatusEffectCategory.NEUTRAL,0x000000);
    public static final StatusEffect RUMINATING = new Ruminating();
    public static final StatusEffect SHULKER_BULLET_COOLDOWN = new CCStatusEffect(StatusEffectCategory.NEUTRAL,0x000000);
    public static final StatusEffect SILK_COOLDOWN = new CCStatusEffect(StatusEffectCategory.NEUTRAL,0x000000);
    public static final StatusEffect VENOM_COOLDOWN = new CCStatusEffect(StatusEffectCategory.NEUTRAL,0x000000);
    public static final StatusEffect WATER_VULNERABILITY = new CCStatusEffect(StatusEffectCategory.NEUTRAL,0x000000);


    public static void register(){
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "organ_rejection"), ORGAN_REJECTION);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "arrow_dodge_cooldown"), ARROW_DODGE_COOLDOWN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "dragon_bomb_cooldown"), DRAGON_BOMB_COOLDOWN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "dragon_breath_cooldown"), DRAGON_BREATH_COOLDOWN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "explosion_cooldown"), EXPLOSION_COOLDOWN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "forceful_spit_cooldown"), FORCEFUL_SPIT_COOLDOWN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "furnace_power"), FURNACE_POWER);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "ghastly_cooldown"), GHASTLY_COOLDOWN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "iron_repair_cooldown"), IRON_REPAIR_COOLDOWN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "pyromancy_cooldown"), PYROMANCY_COOLDOWN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "ruminating"), RUMINATING);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "shulker_bullet_cooldown"), SHULKER_BULLET_COOLDOWN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "silk_cooldown"), SILK_COOLDOWN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "venom_cooldown"), VENOM_COOLDOWN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(FaceCavity.MODID, "water_vulnerability"), WATER_VULNERABILITY);
    }
}
