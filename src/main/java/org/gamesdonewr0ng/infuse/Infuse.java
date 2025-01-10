package org.gamesdonewr0ng.infuse;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.commands.InfuseCommand;
import org.gamesdonewr0ng.infuse.commands.TrustCommand;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;

public class Infuse implements ModInitializer {
    //private final Logger logger = LoggerFactory.getLogger("Infuse");

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            InfuseCommand.register(dispatcher);
            TrustCommand.register(dispatcher);
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                SparksHandler.handlePlayerAction(player);
            }
        });
    }
}