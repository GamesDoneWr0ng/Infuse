package org.gamesdonewr0ng.infuse.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommonNetworkHandler.class)
public class SendPacketMixin {

    //private static final Logger logger = LoggerFactory.getLogger("Packet logger");

    @Final @Shadow protected MinecraftServer server;

    @Inject(method = "send", at = @At("HEAD"), cancellable = true)
    public void sendPacket(Packet<?> packet, @Nullable PacketCallbacks callbacks, CallbackInfo info) {
        /*if (!(packet instanceof WorldTimeUpdateS2CPacket || packet instanceof ChunkDataS2CPacket || packet instanceof KeepAliveS2CPacket || packet instanceof GameMessageS2CPacket || packet instanceof EntityS2CPacket)) {
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
        } else if (packet instanceof NbtQueryResponseS2CPacket nbtResponse) {
           NbtCompound nbt = nbtResponse.getNbt();
           if (nbt != null && nbt.contains("infuse_data") && nbt.contains("Inventory")) {
               NbtCompound infuseData = (NbtCompound) nbt.get("infuse_data");
               assert infuseData != null;
               if (infuseData.getString("infuse_primary").equals("Invisibility") &&
                   infuseData.getBoolean("infuse_active_primary") &&
                   infuseData.getInt("infuse_cooldown_primary") != 0)
               {
                   NbtList inventory = (NbtList) nbt.get("Inventory");
                   assert inventory != null;
                   for (net.minecraft.nbt.NbtElement nbtElement : inventory) {
                       for (int slot : new int[]{100, 101, 102, 103, nbt.getInt("SelectedItemSlot")}) {
                           NbtCompound item = (NbtCompound) nbtElement;
                           if (item.getByte("Slot") == slot) {
                               item.putString("id", "minecraft:air");
                           }
                       }
                   }
               }
           }
        }
    }
}
