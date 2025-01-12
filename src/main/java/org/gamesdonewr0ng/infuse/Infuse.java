package org.gamesdonewr0ng.infuse;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.gamesdonewr0ng.infuse.commands.*;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;
import org.gamesdonewr0ng.infuse.sparks.primary.Feather;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Infuse implements ModInitializer {
    private final Logger LOGGER = LoggerFactory.getLogger("Infuse");

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            InfuseCommand.register(dispatcher);
            TrustCommand.register(dispatcher);
            WithdrawCommand.register(dispatcher);
            PDrain.register(dispatcher);
            SDrain.register(dispatcher);
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            boolean display = server.getTicks() % 10 == 0;
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                SparksHandler.handlePlayerAction(player, display);
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (DataHandler.getSupport((IEntityDataSaver) handler.getPlayer()).isEmpty()) {
                SparksHandler.setSupport(handler.getPlayer(), switch (new Random().nextInt(4)) {
                    case 0 -> "Speed";
                    case 1 -> "Ocean";
                    case 2 -> "Fire";
                    case 3 -> "Emerald";
                    default -> "";
                });
                handler.getPlayer().unlockRecipes(List.of(
                        Identifier.of("infuse", "emerald_item"),
                        Identifier.of("infuse", "fire_item"),
                        Identifier.of("infuse", "ocean_item"),
                        Identifier.of("infuse", "speed_item"),
                        Identifier.of("infuse", "feather_item"),
                        Identifier.of("infuse", "frost_item"),
                        Identifier.of("infuse", "haste_item"),
                        Identifier.of("infuse", "heart_item"),
                        Identifier.of("infuse", "invisibility_item"),
                        Identifier.of("infuse", "regeneration_item"),
                        Identifier.of("infuse", "strength_item"),
                        Identifier.of("infuse", "lightning_item")
                        ));
            }

            handler.getPlayer().networkHandler.sendPacket(new ResourcePackSendS2CPacket(
                    UUID.randomUUID(),
                    "https://gamesdonewr0ng.github.io/Infuse/src/main/resources/infusePack.zip",
                    "8027248b823c01473c037fb764761137dd9a531d",
                    true,
                    Optional.of(Text.literal("Infuse icons"))));
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if (DataHandler.getPrimary((IEntityDataSaver) handler.getPlayer()).equals("Feather")) {
                ((Feather) Objects.requireNonNull(SparksHandler.getPrimary(handler.getPlayer()))).removeBlocks(handler.getPlayer());
            }
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            NbtCompound oldNbt = ((IEntityDataSaver) oldPlayer).infuse$getPersistentData();
            NbtCompound newNbt = ((IEntityDataSaver) newPlayer).infuse$getPersistentData();
            for (String key : oldNbt.getKeys()) {
                if (key.startsWith("infuse")) {
                    newNbt.put(key, oldNbt.get(key));
                }
            }

            if (Math.random() > 0.5F) {
                SparksHandler.setSupport(newPlayer, "Removed");
            } else {
                SparksHandler.setPrimary(newPlayer, "Removed");
            }
        });
    }
}