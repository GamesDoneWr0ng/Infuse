package org.gamesdonewr0ng.infuse.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.SparkItems;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

public class SDrain {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("sdrain").executes(context -> {
            if (!context.getSource().isExecutedByPlayer()) {
                context.getSource().sendFeedback(() -> Text.literal("Must be a player."), false);
                return Command.SINGLE_SUCCESS;
            }
            ServerPlayerEntity player = context.getSource().getPlayer();
            String support = DataHandler.getSupport((IEntityDataSaver) player);
            if (!(support.equals("Removed") || support.isEmpty())) {
                ItemStack item = SparkItems.getItemstack(support);

                if (player.getInventory().getEmptySlot() != -1) {
                    player.giveItemStack(item);
                } else {
                    player.dropItem(item, true);
                }
                SparksHandler.setSupport(player, "Removed");
            } else {
                context.getSource().sendFeedback(() -> Text.literal("No support to withdraw."), false);
            }
            return Command.SINGLE_SUCCESS;
        }));
    }
}
