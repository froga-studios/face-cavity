package net.tigereye.facecavity;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.tigereye.facecavity.chestcavities.organs.OrganManager;
import net.tigereye.facecavity.chestcavities.types.json.GeneratedFaceCavityAssignmentManager;
import net.tigereye.facecavity.chestcavities.types.json.GeneratedFaceCavityTypeManager;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.registration.CCNetworkingPackets;
import net.tigereye.facecavity.util.NetworkUtil;

import java.util.Optional;

public class FaceCavityServer implements DedicatedServerModInitializer{

    @Override
    public void onInitializeServer() {
        ServerPlayNetworking.registerGlobalReceiver(CCNetworkingPackets.RECEIVED_UPDATE_PACKET_ID, (server, player, handler, buf, sender) -> {
            Optional<FaceCavityEntity> optional = FaceCavityEntity.of(player);
            optional.ifPresent(chestCavityEntity -> NetworkUtil.ReadFaceCavityReceivedUpdatePacket(chestCavityEntity.getFaceCavityInstance()));
        });
        ServerPlayNetworking.registerGlobalReceiver(CCNetworkingPackets.HOTKEY_PACKET_ID, (server, player, handler, buf, sender) -> {
            Optional<FaceCavityEntity> optional = FaceCavityEntity.of(player);
            optional.ifPresent(chestCavityEntity -> NetworkUtil.ReadFaceCavityHotkeyPacket(chestCavityEntity.getFaceCavityInstance(),buf));
        });
    }
}
