package org.gamesdonewr0ng.infuse.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;

public class InfuseCommand {
    static String helpMessage = """
Usage options:
/infuse setSupport <support> [player]
/infuse setPrimary <primary> [player]""";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("infuse")
                .then(CommandManager.literal("setSupport")
                        .requires(source -> source.hasPermissionLevel(2))

                        .then(CommandManager.argument("value", StringArgumentType.string())
                                .suggests(Suggesters.SUPPORT)

                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .executes(context -> {
                                            String support = StringArgumentType.getString(context, "value");
                                            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                                            if (player == null) {
                                                if (context.getSource().isExecutedByPlayer()) {
                                                    player = context.getSource().getPlayer();
                                                } else {
                                                    context.getSource().sendFeedback(() -> Text.literal("Missing player."), false);
                                                    return Command.SINGLE_SUCCESS;
                                                }
                                            }
                                            SparksHandler.setSupport(player, support);
                                            return Command.SINGLE_SUCCESS;
                                        }))

                                .executes(context -> { // setSupport value
                                    String support = StringArgumentType.getString(context, "value");
                                    if (context.getSource().isExecutedByPlayer()) {
                                        SparksHandler.setSupport(context.getSource().getPlayer(), support);
                                    } else {
                                        context.getSource().sendFeedback(() -> Text.literal("Missing target."), false);
                                    }
                                    return Command.SINGLE_SUCCESS;
                                }))

                        .executes(context -> { // setSupport
                            context.getSource().sendFeedback(() -> Text.literal("Missing value."), false);
                            return Command.SINGLE_SUCCESS;
                        }))

                .then(CommandManager.literal("setPrimary")
                        .requires(source -> source.hasPermissionLevel(2))

                        .then(CommandManager.argument("value", StringArgumentType.string())
                                .suggests(Suggesters.PRIMARY)

                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .executes(context -> {
                                            String primary = StringArgumentType.getString(context, "value");
                                            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                                            if (player == null) {
                                                if (context.getSource().isExecutedByPlayer()) {
                                                    player = context.getSource().getPlayer();
                                                } else {
                                                    context.getSource().sendFeedback(() -> Text.literal("Missing player."), false);
                                                    return Command.SINGLE_SUCCESS;
                                                }
                                            }
                                            SparksHandler.setPrimary(player, primary);
                                            return Command.SINGLE_SUCCESS;
                                        }))

                                .executes(context -> { // setSupport value
                                    String primary = StringArgumentType.getString(context, "value");
                                    if (context.getSource().isExecutedByPlayer()) {
                                        SparksHandler.setPrimary(context.getSource().getPlayer(), primary);
                                    } else {
                                        context.getSource().sendFeedback(() -> Text.literal("Missing target."), false);
                                    }
                                    return Command.SINGLE_SUCCESS;
                                }))

                        .executes(context -> { // setSupport
                            context.getSource().sendFeedback(() -> Text.literal("Missing value."), false);
                            return Command.SINGLE_SUCCESS;
                        }))

                .executes(context -> {
                    context.getSource().sendFeedback(() -> Text.literal(helpMessage), false);
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
}
