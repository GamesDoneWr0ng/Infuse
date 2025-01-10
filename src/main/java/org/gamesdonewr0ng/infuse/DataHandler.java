package org.gamesdonewr0ng.infuse;

import net.minecraft.nbt.NbtCompound;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

public class DataHandler {

    public static void setPrimary(IEntityDataSaver player, String val) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putString("primary", val);
    }

    public static String getPrimary(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getString("primary");
    }

    public static void setSupport(IEntityDataSaver player, String val) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putString("support", val);
    }

    public static String getSupport(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getString("support");
    }

    public static void setCooldownSupport(IEntityDataSaver player, int val) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("cooldown_support", Math.max(val, 0));
    }

    public static int getCooldownSupport(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return  nbt.getInt("cooldown_support");
    }

    public static void setActiveSupport(IEntityDataSaver player, boolean val) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putBoolean("active_support", val);
    }

    public static Boolean getActiveSupport(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return  nbt.getBoolean("active_support");
    }

    public static void setCooldownPrimary(IEntityDataSaver player, int val) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("cooldown_primary", Math.max(val, 0));
    }

    public static int getCooldownPrimary(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return  nbt.getInt("cooldown_primary");
    }

    public static void setActivePrimary(IEntityDataSaver player, boolean val) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putBoolean("active_primary", val);
    }

    public static Boolean getActivePrimary(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return  nbt.getBoolean("active_primary");
    }

    public static void setTrustList(IEntityDataSaver player, String other, boolean val) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putBoolean(String.format("trustlist_%s", other), val);
    }

    public static boolean getTrustList(IEntityDataSaver player, String other) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getBoolean(String.format("trustlist_%s", other));
    }
}
