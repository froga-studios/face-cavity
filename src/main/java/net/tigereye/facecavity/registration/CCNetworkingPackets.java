package net.tigereye.facecavity.registration;

import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.util.NetworkUtil;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CCNetworkingPackets {
    public static final Identifier ORGAN_DATA_PACKET_ID = new Identifier(FaceCavity.MODID,"organ_data");
    public static final Identifier UPDATE_PACKET_ID = new Identifier(FaceCavity.MODID,"update");
    public static final Identifier RECEIVED_UPDATE_PACKET_ID = new Identifier(FaceCavity.MODID,"received_update");

    public static final Identifier HOTKEY_PACKET_ID = new Identifier(FaceCavity.MODID, "hotkey");

    public static void register(){
        ServerLoginConnectionEvents.QUERY_START.register(NetworkUtil::sendOrganDataPacket);
        ServerPlayNetworking.registerGlobalReceiver(CCNetworkingPackets.RECEIVED_UPDATE_PACKET_ID, (server, player, handler, buf, sender) -> {
            Optional<FaceCavityEntity> optional = FaceCavityEntity.of(player);
            optional.ifPresent(chestCavityEntity -> NetworkUtil.ReadFaceCavityReceivedUpdatePacket(chestCavityEntity.getFaceCavityInstance()));
        });

        ServerPlayNetworking.registerGlobalReceiver(CCNetworkingPackets.HOTKEY_PACKET_ID, (server, player, handler, buf, sender) -> {
            Optional<FaceCavityEntity> optional = FaceCavityEntity.of(player);
            optional.ifPresent(chestCavityEntity -> NetworkUtil.ReadFaceCavityHotkeyPacket(chestCavityEntity.getFaceCavityInstance(),buf));
        });
        ServerLoginNetworking.registerGlobalReceiver(CCNetworkingPackets.ORGAN_DATA_PACKET_ID,(server, handler, understood, buf, synchronizer, sender) -> {
        });
    }

    public static void registerClient(){
        ClientPlayNetworking.registerGlobalReceiver(CCNetworkingPackets.UPDATE_PACKET_ID, (client, handler, buf, responseSender) -> {
            Optional<FaceCavityEntity> optional = FaceCavityEntity.of(client.cameraEntity);
            optional.ifPresent(chestCavityEntity -> NetworkUtil.ReadFaceCavityUpdatePacket(chestCavityEntity.getFaceCavityInstance(), buf));
        });
        ClientLoginNetworking.registerGlobalReceiver(CCNetworkingPackets.ORGAN_DATA_PACKET_ID, (client, handler, buf, responseSender) -> {
            NetworkUtil.readOrganDataPacket(buf);
            return CompletableFuture.completedFuture(PacketByteBufs.empty());
        });
    }
}
