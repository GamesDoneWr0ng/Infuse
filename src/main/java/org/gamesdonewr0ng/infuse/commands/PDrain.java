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

public class PDrain {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("pdrain").executes(context -> {
            if (!context.getSource().isExecutedByPlayer()) {
                context.getSource().sendFeedback(() -> Text.literal("Must be a player."), false);
                return Command.SINGLE_SUCCESS;
            }
            ServerPlayerEntity player = context.getSource().getPlayer();
            String primary = DataHandler.getPrimary((IEntityDataSaver) player);
            if (!(primary.equals("Removed") || primary.isEmpty())) {
                ItemStack item = SparkItems.getItemstack(primary);

                if (player.getInventory().getEmptySlot() != -1) {
                    player.giveItemStack(item);
                } else {
                    player.dropItem(item, true);
                }
                SparksHandler.setPrimary(player, "Removed");
            } else {
                context.getSource().sendFeedback(() -> Text.literal("No primary to withdraw."), false);
            }
            return Command.SINGLE_SUCCESS;
        }));
    }
}
