package org.gamesdonewr0ng.infuse.sparks;

import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
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
            Formatting primaryColor;
            if (DataHandler.getActivePrimary((IEntityDataSaver) player)) {
                switch (DataHandler.getPrimary((IEntityDataSaver) player)) {
                    case "Feather"      -> {primaryIcon = " ";  primaryColor = Formatting.DARK_GREEN;}
                    case "Frost"        -> {primaryIcon = " ";  primaryColor = Formatting.AQUA;}
                    case "Haste"        -> {primaryIcon = " "; primaryColor = Formatting.GOLD;}
                    case "Heart"        -> {primaryIcon = " "; primaryColor = Formatting.DARK_PURPLE;}
                    case "Invisibility" -> {primaryIcon = " ";  primaryColor = Formatting.GRAY;}
                    case "Regeneration" -> {primaryIcon = " "; primaryColor = Formatting.RED;}
                    case "Strength"     -> {primaryIcon = " "; primaryColor = Formatting.DARK_RED;}
                    case "Lightning"    -> {primaryIcon = " ";  primaryColor = Formatting.YELLOW;}
                    case "Ender"        -> {primaryIcon = " "; primaryColor = Formatting.DARK_PURPLE;}
                    default             -> {primaryIcon = " "; primaryColor = Formatting.WHITE;}
                }
            } else {
                primaryColor = Formatting.WHITE;
                primaryIcon = switch (DataHandler.getPrimary((IEntityDataSaver) player)) {
                    case "Feather"      -> " ";
                    case "Frost"        -> " ";
                    case "Haste"        -> " ";
                    case "Heart"        -> " ";
                    case "Invisibility" -> " ";
                    case "Regeneration" -> " ";
                    case "Strength"     -> " ";
                    case "Lightning"    -> " ";
                    case "Ender"        -> " ";
                    default             -> " ";
                };
            }

            String supportIcon;
            Formatting supportColor;
            if (DataHandler.getActiveSupport((IEntityDataSaver) player)) {
                switch (DataHandler.getSupport((IEntityDataSaver) player)) {
                    case "Emerald" -> {supportIcon = " "; supportColor = Formatting.GREEN;}
                    case "Fire"    -> {supportIcon = " "; supportColor = Formatting.GOLD;}
                    case "Ocean"   -> {supportIcon = " "; supportColor = Formatting.BLUE;}
                    case "Speed"   -> {supportIcon = " "; supportColor = Formatting.YELLOW;}
                    default        -> {supportIcon = " "; supportColor = Formatting.WHITE;}
                }
            } else {
                supportColor = Formatting.WHITE;
                supportIcon = switch (DataHandler.getSupport((IEntityDataSaver) player)) {
                    case "Emerald" -> " ";
                    case "Fire" -> " ";
                    case "Ocean" -> " ";
                    case "Speed" -> " ";
                    default -> " ";
                };
            }

            String primaryCooldownText = primaryCooldown == 0 ? "     " : primaryCooldown / (60 * 20) + ":" + String.format("%02d", (primaryCooldown % (60 * 20)) / 20);
            String supportCooldownText = supportCooldown == 0 ? "     " : supportCooldown / (60 * 20) + ":" + String.format("%02d", (supportCooldown % (60 * 20)) / 20);

            player.networkHandler.sendPacket(new GameMessageS2CPacket(
                    Text.literal("").append(Text.literal(primaryCooldownText).formatted(Formatting.BOLD, primaryColor))
                    .append(Text.literal(primaryIcon + "  " + supportIcon).formatted(Formatting.RESET))
                    .append(Text.literal(supportCooldownText).formatted(Formatting.BOLD, supportColor))
                    , true));
        }

        if (primary != null) {primary.passive(player);}
        if (support != null) {support.passive(player);}

        boolean activePrimary = DataHandler.getActivePrimary((IEntityDataSaver) player);
        boolean activeSupport = DataHandler.getActiveSupport((IEntityDataSaver) player);

        // active ability
        if (activePrimary && primary != null) {primary.active(player);}
        if (activeSupport && support != null) {support.active(player);}

        int cooldownPrimary = DataHandler.getCooldownPrimary((IEntityDataSaver) player);
        int cooldownSupport = DataHandler.getCooldownSupport((IEntityDataSaver) player);

        if (activePrimary && cooldownPrimary == 0 && primary != null) {
            DataHandler.setActivePrimary((IEntityDataSaver) player, false);
            DataHandler.setCooldownPrimary((IEntityDataSaver) player, primary.getCooldown());
        } else if (cooldownPrimary > 0) {
            DataHandler.setCooldownPrimary((IEntityDataSaver) player, cooldownPrimary - 1);
        }

        if (activeSupport && cooldownSupport == 0 && support != null) {
            DataHandler.setActiveSupport((IEntityDataSaver) player, false);
            DataHandler.setCooldownSupport((IEntityDataSaver) player, support.getCooldown());
        } else if (cooldownSupport > 0) {
            DataHandler.setCooldownSupport((IEntityDataSaver) player, cooldownSupport - 1);
        }
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
            case "Ender" -> new Ender();
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
