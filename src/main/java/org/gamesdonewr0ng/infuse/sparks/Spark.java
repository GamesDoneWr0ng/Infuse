package org.gamesdonewr0ng.infuse.sparks;

import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

public class Spark {
    public int getCooldown() {return 0;}
    public void passive(ServerPlayerEntity player) {}
    public void activate(ServerPlayerEntity player) {}
    public void active(ServerPlayerEntity player) {}

    public void disable(ServerPlayerEntity player, boolean primary) {
        if (primary) {
            DataHandler.setPrimary((IEntityDataSaver) player, "");
            DataHandler.setActivePrimary(  (IEntityDataSaver) player, false);
            DataHandler.setCooldownPrimary((IEntityDataSaver) player, 0);
        } else {
            DataHandler.setSupport((IEntityDataSaver) player, "");
            DataHandler.setActiveSupport(  (IEntityDataSaver) player, false);
            DataHandler.setCooldownSupport((IEntityDataSaver) player, 0);
        }
    }
}
