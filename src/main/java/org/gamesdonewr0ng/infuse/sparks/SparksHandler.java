package org.gamesdonewr0ng.infuse.sparks;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.primary.*;
import org.gamesdonewr0ng.infuse.sparks.support.*;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

import java.util.Arrays;

public class SparksHandler {
    public static void handlePlayerAction(ServerPlayerEntity player, boolean display) {
        Spark support = getSupport(player);
        Spark primary = getPrimary(player);

        if (display) {
            int primaryCooldown = DataHandler.getCooldownPrimary((IEntityDataSaver) player);
            int supportCooldown = DataHandler.getCooldownSupport((IEntityDataSaver) player);
            String primaryIcon;
            if (DataHandler.getActivePrimary((IEntityDataSaver) player)) {
                primaryIcon = switch (DataHandler.getPrimary((IEntityDataSaver) player)) {
                    case "Feather" -> "";
                    case "Frost" -> "";
                    case "Haste" -> "";
                    case "Heart" -> "";
                    case "Invisibility" -> "";
                    case "Regeneration" -> "";
                    case "Strength" -> "";
                    case "Lightning" -> "";
                        default -> "";
                };
            } else {
                primaryIcon = switch (DataHandler.getPrimary((IEntityDataSaver) player)) {
                    case "Feather" -> "";
                    case "Frost" -> "";
                    case "Haste" -> "";
                    case "Heart" -> "";
                    case "Invisibility" -> "";
                    case "Regeneration" -> "";
                    case "Strength" -> "";
                    case "Lightning" -> "";
                    default -> "";
                };
            }
            String supportIcon;
            if (DataHandler.getActiveSupport((IEntityDataSaver) player)) {
                supportIcon = switch (DataHandler.getSupport((IEntityDataSaver) player)) {
                    case "Emerald" -> "";
                    case "Fire" -> "";
                    case "Ocean" -> "";
                    case "Speed" -> "";
                    default -> "";
                };
            } else {
                supportIcon = switch (DataHandler.getSupport((IEntityDataSaver) player)) {
                    case "Emerald" -> "";
                    case "Fire" -> "";
                    case "Ocean" -> "";
                    case "Speed" -> "";
                    default -> "";
                };
            }

            String primaryCooldownText = primaryCooldown == 0 ? "" : primaryCooldown / (60 * 20) + ":" + (primaryCooldown % (60 * 20)) / 20;
            String supportCooldownText = supportCooldown == 0 ? "" : supportCooldown / (60 * 20) + ":" + (supportCooldown % (60 * 20)) / 20;

            player.networkHandler.sendPacket(new GameMessageS2CPacket(Text.literal("%s %s  %s %s".formatted(
                    primaryCooldownText,
                    primaryIcon,
                    supportIcon,
                    supportCooldownText
            )), true));
        }

        boolean activePrimary = DataHandler.getActivePrimary((IEntityDataSaver) player);
        int cooldownPrimary = DataHandler.getCooldownPrimary((IEntityDataSaver) player);

        boolean activeSupport = DataHandler.getActiveSupport((IEntityDataSaver) player);
        int cooldownSupport = DataHandler.getCooldownSupport((IEntityDataSaver) player);

        if (primary != null) {primary.passive(player);}
        if (support != null) {support.passive(player);}


        // active ability
        if (activePrimary && primary != null) {primary.active(player);}
        if (activeSupport && support != null) {support.active(player);}

        boolean offhand = isOffhandActivated(player);

        if (activePrimary && cooldownPrimary == 0 && primary != null) {
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

        if (activeSupport && cooldownSupport == 0 && support != null) {
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
        player.sendMessage(Text.literal("Set support to " + val).withColor(Colors.GREEN));
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
        player.sendMessage(Text.literal("Set primary to " + val).withColor(Colors.GREEN));
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

    public static void setAbility(ServerPlayerEntity player, String val) {
        if (Arrays.asList(new String[]{"Speed", "Ocean", "Fire", "Emerald"}).contains(val)) {
            setSupport(player, val);
        } else {
            setPrimary(player, val);
        }
    }

    public static void activatePrimary(ServerPlayerEntity player, int ticks) {
        DataHandler.setActivePrimary((IEntityDataSaver) player, true);
        DataHandler.setCooldownPrimary((IEntityDataSaver) player, ticks);
    }

    public static void activateSupport(ServerPlayerEntity player, int ticks) {
        DataHandler.setActiveSupport((IEntityDataSaver) player, true);
        DataHandler.setCooldownSupport((IEntityDataSaver) player, ticks);
    }
}
