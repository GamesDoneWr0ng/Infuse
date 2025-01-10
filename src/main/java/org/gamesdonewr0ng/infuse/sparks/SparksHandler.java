package org.gamesdonewr0ng.infuse.sparks;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.primary.*;
import org.gamesdonewr0ng.infuse.sparks.support.*;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

public class SparksHandler {
    public static void handlePlayerAction(ServerPlayerEntity player) {
        Spark support = getSupport(player);
        Spark primary = getPrimary(player);

        boolean activePrimary = DataHandler.getActivePrimary((IEntityDataSaver) player);
        int cooldownPrimary = DataHandler.getCooldownPrimary((IEntityDataSaver) player);

        boolean activeSupport = DataHandler.getActiveSupport((IEntityDataSaver) player);
        int cooldownSupport = DataHandler.getCooldownSupport((IEntityDataSaver) player);

        if (primary != null) {primary.passive(player);}
        if (support != null) {support.passive(player);}


        // active ability
        if (activePrimary) {primary.active(player);}
        if (activeSupport) {support.active(player);}

        boolean offhand = isOffhandActivated(player);

        if (activePrimary && cooldownPrimary == 0) {
            DataHandler.setActivePrimary((IEntityDataSaver) player, false);
            DataHandler.setCooldownPrimary((IEntityDataSaver) player, primary.getCooldown());
        } else if (cooldownPrimary > 0) {
            DataHandler.setCooldownPrimary((IEntityDataSaver) player, cooldownPrimary - 1);
        } else {
            if (primary != null && offhand) {
                // Check if the player is sneaking
                if (!player.isSneaking()) {
                    primary.activate(player);
                }
            }
        }

        if (activeSupport && cooldownSupport == 0) {
            DataHandler.setActiveSupport((IEntityDataSaver) player, false);
            DataHandler.setCooldownSupport((IEntityDataSaver) player, support.getCooldown());
        } else if (cooldownSupport > 0) {
            DataHandler.setCooldownSupport((IEntityDataSaver) player, cooldownSupport - 1);
        } else {
            if (support != null && offhand) {
                // Check if the player is sneaking
                if (player.isSneaking()) {
                    support.activate(player);
                }
            }
        }
    }

    public static boolean isOffhandActivated(ServerPlayerEntity player) {
        if (player.getOffHandStack().getCount() == 0) {
            return false;
        }
        if (player.getMainHandStack().getCount() == 0) {
            player.getInventory().setStack(player.getInventory().selectedSlot, player.getOffHandStack()); // return item
        } else {
            int slot = player.getInventory().getEmptySlot();
            if (slot != -1 && slot != PlayerInventory.OFF_HAND_SLOT) {
                player.getInventory().setStack(slot, player.getOffHandStack()); // something in main hand
            } else {
                player.dropItem(player.getOffHandStack(), true); // no space in inventory
            }
        }
        player.getInventory().setStack(PlayerInventory.OFF_HAND_SLOT, ItemStack.EMPTY);
        return true;
    }

    public static void setSupport(ServerPlayerEntity player, String val) {
        Spark spark = getSupport(player);
        if (spark != null) {
            spark.disable(player, false);
        }

        DataHandler.setSupport((IEntityDataSaver) player, val);
    }

    public static Spark getSupport(ServerPlayerEntity player) {
        return switch (DataHandler.getSupport((IEntityDataSaver) player)) {
            case "Speed" -> new Speed();
            case "Ocean" -> new Ocean();
            case "Emerald" -> new Emerald();
            case "Fire" -> new Fire();
            default -> null;
        };
    }

    public static void setPrimary(ServerPlayerEntity player, String val) {
        Spark spark = getPrimary(player);
        if (spark != null) {
            spark.disable(player, true);
        }

        DataHandler.setPrimary((IEntityDataSaver) player, val);
    }

    public static Spark getPrimary(ServerPlayerEntity player) {
        return switch (DataHandler.getPrimary((IEntityDataSaver) player)) {
            case "Lightning" -> new Lightning();
            case "Strength" -> new Strength();
            case "Regeneration" -> new Regeneration();
            case "Heart" -> new Heart();
            case "Haste" -> new Haste();
            case "Frost" -> new Frost();
            case "Feather" -> new Feather();
            case "Invisibility" -> new Invisibility();
            default -> null;
        };
    }
}
