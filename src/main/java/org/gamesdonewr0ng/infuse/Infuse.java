package org.gamesdonewr0ng.infuse;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.commands.InfuseCommand;
import org.gamesdonewr0ng.infuse.commands.TrustCommand;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;
import org.gamesdonewr0ng.infuse.sparks.primary.Feather;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

import java.util.Random;

public class Infuse implements ModInitializer {
    //private final Logger logger = LoggerFactory.getLogger("Infuse");

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            InfuseCommand.register(dispatcher);
            TrustCommand.register(dispatcher);
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
            }
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if (DataHandler.getPrimary((IEntityDataSaver) handler.getPlayer()).equals("Feather")) {
                ((Feather) SparksHandler.getPrimary(handler.getPlayer())).removeBlocks(handler.getPlayer());
            }
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            NbtCompound oldNbt = ((IEntityDataSaver) oldPlayer).getPersistentData();
            NbtCompound newNbt = ((IEntityDataSaver) newPlayer).getPersistentData();
            for (String key : oldNbt.getKeys()) {
                if (key.startsWith("infuse")) {
                    newNbt.put(key, oldNbt.get(key));
                }
            }

            if (Math.random() > 0.5F) {
                SparksHandler.setSupport(newPlayer, "removed");
            } else {
                SparksHandler.setPrimary(newPlayer, "removed");
            }
        });
    }
}