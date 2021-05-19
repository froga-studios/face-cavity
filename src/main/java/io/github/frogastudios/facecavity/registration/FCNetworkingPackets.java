package io.github.frogastudios.facecavity.registration;

import io.github.frogastudios.facecavity.FaceCavity;
import io.github.frogastudios.facecavity.interfaces.FaceCavityEntity;
import io.github.frogastudios.facecavity.util.NetworkUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class FCNetworkingPackets {
    public static final Identifier UPDATE_PACKET_ID = new Identifier(FaceCavity.MODID,"update");
    public static final Identifier RECEIVED_UPDATE_PACKET_ID = new Identifier(FaceCavity.MODID,"received_update");

    public static final Identifier HOTKEY_PACKET_ID = new Identifier(FaceCavity.MODID, "hotkey");

    public static void register(){
        ServerPlayNetworking.registerGlobalReceiver(FCNetworkingPackets.RECEIVED_UPDATE_PACKET_ID, (server, player, handler, buf, sender) -> {
            Optional<FaceCavityEntity> optional = FaceCavityEntity.of(player);
            optional.ifPresent(chestCavityEntity -> NetworkUtil.ReadChestCavityReceiveUpdatePacket(chestCavityEntity.getFaceCavityInstance()));
        });

        ServerPlayNetworking.registerGlobalReceiver(FCNetworkingPackets.HOTKEY_PACKET_ID, (server, player, handler, buf, sender) -> {
            Optional<FaceCavityEntity> optional = FaceCavityEntity.of(player);
            optional.ifPresent(chestCavityEntity -> NetworkUtil.ReadChestCavityHotkeyPacket(chestCavityEntity.getFaceCavityInstance(),buf));
        });
    }

    public static void registerClient(){
        ClientPlayNetworking.registerGlobalReceiver(FCNetworkingPackets.UPDATE_PACKET_ID, (client, handler, buf, responseSender) -> {
            Optional<FaceCavityEntity> optional = FaceCavityEntity.of(client.cameraEntity);
            optional.ifPresent(chestCavityEntity -> NetworkUtil.ReadChestCavityUpdatePacket(chestCavityEntity.getFaceCavityInstance(), buf));
        });
    }
}
