package io.github.frogastudios.facecavity;

import io.github.frogastudios.facecavity.interfaces.FaceCavityEntity;
import io.github.frogastudios.facecavity.util.NetworkUtil;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import io.github.frogastudios.facecavity.registration.FCNetworkingPackets;

import java.util.Optional;

public class FaceCavityServer implements DedicatedServerModInitializer{

    @Override
    public void onInitializeServer() {
        ServerPlayNetworking.registerGlobalReceiver(FCNetworkingPackets.RECEIVED_UPDATE_PACKET_ID, (server, player, handler, buf, sender) -> {
            Optional<FaceCavityEntity> optional = FaceCavityEntity.of(player);
            optional.ifPresent(chestCavityEntity -> NetworkUtil.ReadChestCavityReceiveUpdatePacket(chestCavityEntity.getFaceCavityInstance()));
        });
        ServerPlayNetworking.registerGlobalReceiver(FCNetworkingPackets.HOTKEY_PACKET_ID, (server, player, handler, buf, sender) -> {
            Optional<FaceCavityEntity> optional = FaceCavityEntity.of(player);
            optional.ifPresent(chestCavityEntity -> NetworkUtil.ReadChestCavityHotkeyPacket(chestCavityEntity.getFaceCavityInstance(),buf));
        });
    }
}
