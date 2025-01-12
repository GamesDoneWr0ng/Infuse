package org.gamesdonewr0ng.infuse;

import net.minecraft.nbt.NbtCompound;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

public class DataHandler {

    public static void setPrimary(IEntityDataSaver player, String val) {
        NbtCompound nbt = player.infuse$getPersistentData();
        nbt.putString("infuse_primary", val);
    }

    public static String getPrimary(IEntityDataSaver player) {
        NbtCompound nbt = player.infuse$getPersistentData();
        return nbt.getString("infuse_primary");
    }

    public static void setSupport(IEntityDataSaver player, String val) {
        NbtCompound nbt = player.infuse$getPersistentData();
        nbt.putString("infuse_support", val);
    }

    public static String getSupport(IEntityDataSaver player) {
        NbtCompound nbt = player.infuse$getPersistentData();
        return nbt.getString("infuse_support");
    }

    public static void setCooldownSupport(IEntityDataSaver player, int val) {
        NbtCompound nbt = player.infuse$getPersistentData();
        nbt.putInt("infuse_cooldown_support", Math.max(val, 0));
    }

    public static int getCooldownSupport(IEntityDataSaver player) {
        NbtCompound nbt = player.infuse$getPersistentData();
        return  nbt.getInt("infuse_cooldown_support");
    }

    public static void setActiveSupport(IEntityDataSaver player, boolean val) {
        NbtCompound nbt = player.infuse$getPersistentData();
        nbt.putBoolean("infuse_active_support", val);
    }

    public static Boolean getActiveSupport(IEntityDataSaver player) {
        NbtCompound nbt = player.infuse$getPersistentData();
        return  nbt.getBoolean("infuse_active_support");
    }

    public static void setCooldownPrimary(IEntityDataSaver player, int val) {
        NbtCompound nbt = player.infuse$getPersistentData();
        nbt.putInt("infuse_cooldown_primary", Math.max(val, 0));
    }

    public static int getCooldownPrimary(IEntityDataSaver player) {
        NbtCompound nbt = player.infuse$getPersistentData();
        return  nbt.getInt("infuse_cooldown_primary");
    }

    public static void setActivePrimary(IEntityDataSaver player, boolean val) {
        NbtCompound nbt = player.infuse$getPersistentData();
        nbt.putBoolean("infuse_active_primary", val);
    }

    public static Boolean getActivePrimary(IEntityDataSaver player) {
        NbtCompound nbt = player.infuse$getPersistentData();
        return  nbt.getBoolean("infuse_active_primary");
    }

    public static void setTrustList(IEntityDataSaver player, String other, boolean val) {
        NbtCompound nbt = player.infuse$getPersistentData();
        nbt.putBoolean(String.format("infuse_trustlist_%s", other), val);
    }

    public static boolean getTrustList(IEntityDataSaver player, String other) {
        NbtCompound nbt = player.infuse$getPersistentData();
        return nbt.getBoolean(String.format("infuse_trustlist_%s", other));
    }
}
