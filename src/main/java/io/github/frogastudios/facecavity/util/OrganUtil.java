package io.github.frogastudios.facecavity.util;

import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import io.github.frogastudios.facecavity.interfaces.FaceCavityEntity;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.explosion.Explosion;
import io.github.frogastudios.facecavity.FaceCavity;
import net.tigereye.chestcavity.interfaces.CCStatusEffectInstance;
import net.tigereye.chestcavity.registration.CCStatusEffects;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class OrganUtil {

    public static void explode(LivingEntity entity, float explosionYield) {
        if (!entity.world.isClient) {
            Explosion.DestructionType destructionType = entity.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            entity.world.createExplosion(null, entity.getX(), entity.getY(), entity.getZ(), (float)Math.sqrt(explosionYield), destructionType);
            spawnEffectsCloud(entity);
        }

    }

    public static List<StatusEffectInstance> getStatusEffects(ItemStack organ){
        CompoundTag tag = organ.getOrCreateTag();
        ListTag listTag;
        if (!tag.contains("CustomPotionEffects", 9)) {
            return new ArrayList<>();
        }
        else{
            listTag = tag.getList("CustomPotionEffects",10);
            List<StatusEffectInstance> list = new ArrayList<>();
            for(int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag = listTag.getCompound(i);
                StatusEffectInstance statusEffectInstance = StatusEffectInstance.fromTag(compoundTag);
                if (statusEffectInstance != null) {
                    list.add(statusEffectInstance);
                }
            }
            return list;
        }
    }

    public static void milkSilk(LivingEntity entity){
        if(!entity.hasStatusEffect(CCStatusEffects.SILK_COOLDOWN)){
            FaceCavityEntity.of(entity).ifPresent(cce -> {
                if(cce.getFaceCavityInstance().opened){
                    float silk = cce.getFaceCavityInstance().getOrganScore(FCOrganScores.SILK);
                    if(silk > 0){
                        if(spinWeb(entity,silk)) {
                            entity.addStatusEffect(new StatusEffectInstance(CCStatusEffects.SILK_COOLDOWN, FaceCavity.config.SILK_COOLDOWN,0,false,false,true));
                        }
                    }
                }
            });
        }
    }

    public static void queueDragonBombs(LivingEntity entity, FaceCavityInstance cc, int bombs){
        if(entity instanceof PlayerEntity){
            ((PlayerEntity)entity).addExhaustion(bombs*.6f);
        }
        for(int i = 0; i < bombs;i++){
            cc.projectileQueue.add(OrganUtil::spawnDragonBomb);
        }
        entity.addStatusEffect(new StatusEffectInstance(CCStatusEffects.DRAGON_BOMB_COOLDOWN, FaceCavity.config.DRAGON_BOMB_COOLDOWN, 0, false, false, true));
    }

    public static void queueForcefulSpit(LivingEntity entity, FaceCavityInstance cc, int projectiles){
        if(entity instanceof PlayerEntity){
            ((PlayerEntity)entity).addExhaustion(projectiles*.1f);
        }
        for(int i = 0; i < projectiles;i++){
            cc.projectileQueue.add(OrganUtil::spawnSpit);
        }
        entity.addStatusEffect(new StatusEffectInstance(CCStatusEffects.FORCEFUL_SPIT_COOLDOWN, FaceCavity.config.FORCEFUL_SPIT_COOLDOWN, 0, false, false, true));
    }

    public static void queueGhastlyFireballs(LivingEntity entity, FaceCavityInstance cc, int ghastly){
        if(entity instanceof PlayerEntity){
            ((PlayerEntity)entity).addExhaustion(ghastly*.3f);
        }
        for(int i = 0; i < ghastly;i++){
            cc.projectileQueue.add(OrganUtil::spawnGhastlyFireball);
        }
        entity.addStatusEffect(new StatusEffectInstance(CCStatusEffects.GHASTLY_COOLDOWN, FaceCavity.config.GHASTLY_COOLDOWN, 0, false, false, true));
    }

    public static void queuePyromancyFireballs(LivingEntity entity, FaceCavityInstance cc, int pyromancy){
        if(entity instanceof PlayerEntity){
            ((PlayerEntity)entity).addExhaustion(pyromancy*.1f);
        }
        for(int i = 0; i < pyromancy;i++){
            cc.projectileQueue.add(OrganUtil::spawnPyromancyFireball);
        }
        entity.addStatusEffect(new StatusEffectInstance(CCStatusEffects.PYROMANCY_COOLDOWN, FaceCavity.config.PYROMANCY_COOLDOWN, 0, false, false, true));
    }

    public static void queueShulkerBullets(LivingEntity entity, FaceCavityInstance cc, int shulkerBullets){
        if(entity instanceof PlayerEntity){
            ((PlayerEntity)entity).addExhaustion(shulkerBullets*.3f);
        }
        for(int i = 0; i < shulkerBullets;i++){
            cc.projectileQueue.add(OrganUtil::spawnShulkerBullet);
        }
        entity.addStatusEffect(new StatusEffectInstance(CCStatusEffects.SHULKER_BULLET_COOLDOWN, FaceCavity.config.SHULKER_BULLET_COOLDOWN, 0, false, false, true));
    }

    public static void setStatusEffects(ItemStack organ, ItemStack potion){
        List<StatusEffectInstance> potionList = PotionUtil.getPotionEffects(potion);
        List<StatusEffectInstance> list = new ArrayList<>();
        for (StatusEffectInstance effect : potionList) {
            StatusEffectInstance effectCopy = new StatusEffectInstance(effect);
            ((CCStatusEffectInstance) effectCopy).CC_setDuration(Math.max(1,effectCopy.getDuration()/4));
            list.add(effectCopy);
        }
        setStatusEffects(organ,list);
    }

    public static void setStatusEffects(ItemStack organ, List<StatusEffectInstance> list){
        CompoundTag tag = organ.getOrCreateTag();
        ListTag listTag = new ListTag();

        for(int i = 0; i < list.size(); ++i) {
            StatusEffectInstance effect = list.get(i);
            if (effect != null) {
                CompoundTag compoundTag = new CompoundTag();
                listTag.add(effect.toTag(compoundTag));
            }
        }
        tag.put("CustomPotionEffects",listTag);
    }

    public static void shearSilk(LivingEntity entity){
        FaceCavityEntity.of(entity).ifPresent(cce -> {
            if(cce.getFaceCavityInstance().opened){
                float silk = cce.getFaceCavityInstance().getOrganScore(FCOrganScores.SILK);

                if(silk > 0){
                    if(silk >= 2){
                        ItemStack stack = new ItemStack(Items.COBWEB,((int)silk)/2);
                        ItemEntity itemEntity = new ItemEntity(entity.world, entity.getX(), entity.getY(), entity.getZ(), stack);
                        entity.world.spawnEntity(itemEntity);
                    }
                    if(silk % 2 >= 1){
                        ItemStack stack = new ItemStack(Items.STRING);
                        ItemEntity itemEntity = new ItemEntity(entity.world, entity.getX(), entity.getY(), entity.getZ(), stack);
                        entity.world.spawnEntity(itemEntity);
                    }
                }
            }
        });
    }

    public static void spawnEffectsCloud(LivingEntity entity) {
        Collection<StatusEffectInstance> collection = entity.getStatusEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(entity.world, entity.getX(), entity.getY(), entity.getZ());
            areaEffectCloudEntity.setRadius(2.5F);
            areaEffectCloudEntity.setRadiusOnUse(-0.5F);
            areaEffectCloudEntity.setWaitTime(10);
            areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
            Iterator<StatusEffectInstance> var3 = collection.iterator();

            while(var3.hasNext()) {
                StatusEffectInstance statusEffectInstance = var3.next();
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
            }

            entity.world.spawnEntity(areaEffectCloudEntity);
        }

    }

    public static void spawnSpit(LivingEntity entity){
        Vec3d entityFacing = entity.getRotationVector().normalize();

        LlamaEntity fakeLlama = new LlamaEntity(EntityType.LLAMA,entity.world);
        fakeLlama.setPos(entity.getX(),entity.getY(),entity.getZ());
        fakeLlama.pitch = entity.pitch;
        fakeLlama.yaw = entity.yaw;
        fakeLlama.bodyYaw = entity.bodyYaw;
        LlamaSpitEntity llamaSpitEntity = new LlamaSpitEntity(entity.world, fakeLlama);
        llamaSpitEntity.setOwner(entity);
        llamaSpitEntity.setVelocity(entityFacing.x*2,entityFacing.y*2,entityFacing.z*2);
        entity.world.spawnEntity(llamaSpitEntity);
        entityFacing = entityFacing.multiply(-.1D);
        entity.addVelocity(entityFacing.x,entityFacing.y,entityFacing.z);
    }

    public static void spawnDragonBomb(LivingEntity entity){
        Vec3d entityFacing = entity.getRotationVector().normalize();
        DragonFireballEntity fireballEntity = new DragonFireballEntity(entity.world, entity, entityFacing.x, entityFacing.y, entityFacing.z);
        fireballEntity.updatePosition(fireballEntity.getX(), entity.getBodyY(0.5D) + 0.3D, fireballEntity.getZ());
        entity.world.spawnEntity(fireballEntity);
        entityFacing = entityFacing.multiply(-0.2D);
        entity.addVelocity(entityFacing.x,entityFacing.y,entityFacing.z);
    }

    public static void spawnGhastlyFireball(LivingEntity entity){
        Vec3d entityFacing = entity.getRotationVector().normalize();
        FireballEntity fireballEntity = new FireballEntity(entity.world, entity, entityFacing.x, entityFacing.y, entityFacing.z);
        fireballEntity.updatePosition(fireballEntity.getX(), entity.getBodyY(0.5D) + 0.3D, fireballEntity.getZ());
        entity.world.spawnEntity(fireballEntity);
        entityFacing = entityFacing.multiply(-.8D);
        entity.addVelocity(entityFacing.x,entityFacing.y,entityFacing.z);
    }

    public static void spawnPyromancyFireball(LivingEntity entity){
        Vec3d entityFacing = entity.getRotationVector().normalize();
        SmallFireballEntity smallFireballEntity = new SmallFireballEntity(entity.world, entity, entityFacing.x + entity.getRandom().nextGaussian() * .1, entityFacing.y, entityFacing.z + entity.getRandom().nextGaussian() * .1);
        smallFireballEntity.updatePosition(smallFireballEntity.getX(), entity.getBodyY(0.5D) + 0.3D, smallFireballEntity.getZ());
        entity.world.spawnEntity(smallFireballEntity);
        entityFacing = entityFacing.multiply(-.2D);
        entity.addVelocity(entityFacing.x,entityFacing.y,entityFacing.z);
    }

    public static void spawnShulkerBullet(LivingEntity entity){
        //Vec3d entityFacing = entity.getRotationVector().normalize();
        TargetPredicate targetPredicate = new TargetPredicate();
        targetPredicate.setBaseMaxDistance(FaceCavity.config.SHULKER_BULLET_TARGETING_RANGE*2);
        LivingEntity target = entity.world.getClosestEntity(LivingEntity.class,
                targetPredicate, entity, entity.getX(), entity.getY(),entity.getZ(),
                new Box(entity.getX()- FaceCavity.config.SHULKER_BULLET_TARGETING_RANGE,entity.getY()- FaceCavity.config.SHULKER_BULLET_TARGETING_RANGE,entity.getZ()- FaceCavity.config.SHULKER_BULLET_TARGETING_RANGE,
                        entity.getX()+ FaceCavity.config.SHULKER_BULLET_TARGETING_RANGE,entity.getY()+ FaceCavity.config.SHULKER_BULLET_TARGETING_RANGE,entity.getZ()+ FaceCavity.config.SHULKER_BULLET_TARGETING_RANGE));
        if(target == null){
            return;
        }
        ShulkerBulletEntity shulkerBulletEntity = new ShulkerBulletEntity(entity.world,entity,target, Direction.Axis.Y);
        shulkerBulletEntity.updatePosition(shulkerBulletEntity.getX(), entity.getBodyY(0.5D) + 0.3D, shulkerBulletEntity.getZ());
        entity.world.spawnEntity(shulkerBulletEntity);
        //entityFacing = entityFacing.multiply(-.4D);
        //entity.addVelocity(entityFacing.x,entityFacing.y,entityFacing.z);
    }

    public static boolean spinWeb(LivingEntity entity, float silkScore){
        int hungerCost = 0;
        PlayerEntity player = null;
        if(entity instanceof PlayerEntity){
            player = (PlayerEntity)entity;
            if(player.getHungerManager().getFoodLevel() < 18){
                return false;
            }
        }

        if(silkScore >= 2) {
            BlockPos pos = entity.getBlockPos().offset(entity.getHorizontalFacing().getOpposite());
            if(entity.getEntityWorld().getBlockState(pos).getBlock().is(Blocks.AIR)){
                if(silkScore >= 3) {
                    hungerCost = 32;
                    silkScore -= 3;
                    entity.getEntityWorld().setBlockState(pos, Blocks.WHITE_WOOL.getDefaultState(), 2);
                }
                else{
                    hungerCost = 16;
                    silkScore -= 2;
                    entity.getEntityWorld().setBlockState(pos, Blocks.COBWEB.getDefaultState(), 2);
                }
            }
        }
        while(silkScore >= 1) {
            silkScore--;
            hungerCost += 8;
            entity.dropItem(Items.STRING);
        }
        if(player != null){
            player.getHungerManager().addExhaustion(hungerCost);
        }
        return hungerCost > 0;
    }

    public static boolean teleportRandomly(LivingEntity entity, float range) {
        if (!entity.world.isClient() && entity.isAlive()) {
            for(int i = 0; i < FaceCavity.config.MAX_TELEPORT_ATTEMPTS; i++) {
                double d = entity.getX() + ((entity.getRandom().nextDouble() - 0.5D) * range);
                double e = Math.max(1, entity.getY() + ((entity.getRandom().nextDouble() - 0.5D) * range));
                double f = entity.getZ() + ((entity.getRandom().nextDouble() - 0.5D) * range);
                if(teleportTo(entity, d, e, f)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean teleportTo(LivingEntity entity, double x, double y, double z) {
        BlockPos.Mutable targetPos = new BlockPos.Mutable(x, y, z);
        BlockState blockState = entity.world.getBlockState(targetPos);
        while(targetPos.getY() > 0 && !(blockState.getMaterial().blocksMovement()
                || blockState.getMaterial().isLiquid()))
        {
            targetPos.move(Direction.DOWN);
            blockState = entity.world.getBlockState(targetPos);
        }
        if(targetPos.getY() <= 0){
            return false;
        }

        targetPos.move(Direction.UP);
        blockState = entity.world.getBlockState(targetPos);
        BlockState blockState2 = entity.world.getBlockState(targetPos.up());
        while(blockState.getMaterial().blocksMovement()
                || blockState.getMaterial().isLiquid()
                || blockState2.getMaterial().blocksMovement()
                || blockState2.getMaterial().isLiquid()){
            targetPos.move(Direction.UP);
            blockState = entity.world.getBlockState(targetPos);
            blockState2 = entity.world.getBlockState(targetPos.up());
        }

        if(entity.world.getDimension().hasCeiling() && targetPos.getY() >= entity.world.getDimensionHeight()){
            return false;
        }
        entity.teleport(x, targetPos.getY()+.1, z);
        if (!entity.isSilent()) {
            entity.world.playSound((PlayerEntity)null, entity.prevX, entity.prevY, entity.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, entity.getSoundCategory(), 1.0F, 1.0F);
            entity.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
        }

        return true;
    }
}
