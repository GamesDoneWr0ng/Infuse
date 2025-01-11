package org.gamesdonewr0ng.infuse.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerCommonNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommonNetworkHandler.class)
public class SendPacketMixin {

    private static final Logger logger = LoggerFactory.getLogger("Packet logger");

    @Final @Shadow protected MinecraftServer server;

    @Inject(method = "send", at = @At("HEAD"), cancellable = true)
    public void sendPacket(Packet<?> packet, @Nullable PacketCallbacks callbacks, CallbackInfo info) {
        /*if (!(packet instanceof WorldTimeUpdateS2CPacket || packet instanceof ChunkDataS2CPacket || packet instanceof KeepAliveS2CPacket || packet instanceof GameMessageS2CPacket)) {
            logger.info(packet.toString());
        }*/
        if (packet instanceof EntityEquipmentUpdateS2CPacket equipmentUpdate) {
            for (ServerWorld world : server.getWorlds()) {
                Entity entity = world.getEntityById(equipmentUpdate.getEntityId());
                if (entity != null) {
                    if (entity instanceof ServerPlayerEntity player) {
                        if (DataHandler.getPrimary((IEntityDataSaver) player).equals("Invisibility") &&
                            DataHandler.getActivePrimary((IEntityDataSaver) player) &&
                            DataHandler.getCooldownPrimary((IEntityDataSaver) player) != 0)
                        {
                            info.cancel();
                        }
                    }
                    break;
                }
            }
        } else if (packet instanceof EntityStatusS2CPacket statusUpdate) {
            for (ServerWorld world : server.getWorlds()) {
                Entity entity = statusUpdate.getEntity(world);
                if (entity != null) {
                    if (entity instanceof ServerPlayerEntity player) {
                        if (DataHandler.getPrimary((IEntityDataSaver) player).equals("Invisibility") &&
                                DataHandler.getActivePrimary((IEntityDataSaver) player) &&
                                DataHandler.getCooldownPrimary((IEntityDataSaver) player) != 0)
                        {
                            info.cancel();
                        }
                    }
                    break;
                }
            }
        }
    }
}
