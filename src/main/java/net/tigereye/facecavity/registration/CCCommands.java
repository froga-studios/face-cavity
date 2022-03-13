package net.tigereye.facecavity.registration;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.util.FaceCavityUtil;

import java.util.Optional;

public class CCCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("facecavity")
                .then(CommandManager.literal("getscores")
                    .executes(CCCommands::getScoresNoArgs)
                    .then(CommandManager.argument("entity", EntityArgumentType.entity())
                        .executes(CCCommands::getScores)))
            );
            dispatcher.register(CommandManager.literal("facecavity")
                .then(CommandManager.literal("resetFaceCavity").requires(source -> source.hasPermissionLevel(2))
                    .executes(CCCommands::resetFaceCavityNoArgs)
                    .then(CommandManager.argument("entity", EntityArgumentType.entity())
                        .executes(CCCommands::resetFaceCavity)))
            );
        });

    }

    private static int getScoresNoArgs(CommandContext<ServerCommandSource> context) {
        Entity entity;
        try {
            entity = context.getSource().getEntityOrThrow();
        }
        catch(Exception e){
            context.getSource().sendError(new LiteralText("getScores failed to get entity"));
            return -1;
        }
        Optional<FaceCavityEntity> optional = FaceCavityEntity.of(entity);
        if(optional.isPresent()){
            FaceCavityUtil.outputOrganScoresString((string) -> {
                context.getSource().sendFeedback(new LiteralText(string),false);
            },optional.get().getFaceCavityInstance());
            return 1;
        }
        return 0;
    }

    private static int getScores(CommandContext<ServerCommandSource> context) {
        Entity entity;
        try {
            entity = EntityArgumentType.getEntity(context, "entity");
        }
        catch(Exception e){
            context.getSource().sendError(new LiteralText("getScores failed to get entity"));
            return -1;
        }
        Optional<FaceCavityEntity> optional = FaceCavityEntity.of(entity);
        if(optional.isPresent()){
            FaceCavityUtil.outputOrganScoresString((string) -> {
                context.getSource().sendFeedback(new LiteralText(string),false);
            },optional.get().getFaceCavityInstance());
            return 1;
        }
        return 0;
    }

    private static int resetFaceCavityNoArgs(CommandContext<ServerCommandSource> context) {
        Entity entity;
        try {
            entity = context.getSource().getEntityOrThrow();
        }
        catch(Exception e){
            context.getSource().sendError(new LiteralText("resetFaceCavity failed to get entity"));
            return -1;
        }
        Optional<FaceCavityEntity> optional = FaceCavityEntity.of(entity);
        if(optional.isPresent()){
            FaceCavityUtil.generateFaceCavityIfOpened(optional.get().getFaceCavityInstance());
            return 1;
        }
        return 0;
    }

    private static int resetFaceCavity(CommandContext<ServerCommandSource> context) {
        Entity entity;
        try {
            entity = EntityArgumentType.getEntity(context, "entity");
        }
        catch(Exception e){
            context.getSource().sendError(new LiteralText("getFaceCavity failed to get entity"));
            return -1;
        }
        Optional<FaceCavityEntity> optional = FaceCavityEntity.of(entity);
        if(optional.isPresent()){
            FaceCavityUtil.generateFaceCavityIfOpened(optional.get().getFaceCavityInstance());
            return 1;
        }
        return 0;
    }

}