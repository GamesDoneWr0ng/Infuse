package org.gamesdonewr0ng.infuse.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

public class TrustCommand {
    private static final String helpMessage = """
Usage options:
/trust add <player>
/trust remove <player>
/trust listonline""";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("trust")
                .then(CommandManager.literal("add")
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .executes(context -> {
                                    if (context.getSource().isExecutedByPlayer()) {
                                        DataHandler.setTrustList((IEntityDataSaver) context.getSource().getPlayer(),
                                                EntityArgumentType.getPlayer(context, "player").getUuid().toString(), true);
                                    } else {
                                        context.getSource().sendFeedback(() -> Text.literal("Need to be a player."), false);
                                    }
                                    return Command.SINGLE_SUCCESS;
                                })))

                .then(CommandManager.literal("remove")
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .executes(context -> {
                                    if (context.getSource().isExecutedByPlayer()) {
                                        DataHandler.setTrustList((IEntityDataSaver) context.getSource().getPlayer(),
                                                EntityArgumentType.getPlayer(context, "player").getUuid().toString(), false);
                                    } else {
                                        context.getSource().sendFeedback(() -> Text.literal("Need to be a player."), false);
                                    }
                                    return Command.SINGLE_SUCCESS;
                                })))

                .then(CommandManager.literal("listonline")
                        .executes(context -> {
                            if (!context.getSource().isExecutedByPlayer()) {
                                context.getSource().sendFeedback(() -> Text.literal("Need to be a player."), false);
                            }
                            String output = "";
                            for (ServerPlayerEntity player : context.getSource().getServer().getPlayerManager().getPlayerList()) {
                                if (DataHandler.getTrustList((IEntityDataSaver) context.getSource().getPlayer(), player.getUuid().toString())) {
                                    output += "\n" + player.getName().getString();
                                }
                            }
                            String finalOutput = output;
                            context.getSource().sendFeedback(() -> Text.literal(finalOutput), false);
                            return Command.SINGLE_SUCCESS;
                        }))

                .executes(context -> {
                    context.getSource().sendFeedback(() -> Text.literal(helpMessage), false);
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
}
