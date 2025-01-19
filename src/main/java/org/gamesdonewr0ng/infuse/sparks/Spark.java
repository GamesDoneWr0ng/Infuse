package org.gamesdonewr0ng.infuse.sparks;

import net.minecraft.particle.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

public class Spark {
    public int getCooldown() {return 0;}
    public void passive(ServerPlayerEntity player) {}
    public void activate(ServerPlayerEntity player) {
        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.PLAYERS);
    }
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

    public void aura(ServerPlayerEntity player, ParticleEffect particleEffect, float radius, float density) {
        Vec3d origin = player.getPos();
        for (float i = 0; i < 2*Math.PI; i+= (1/(radius*density))) {
            Vec3d pos = origin.add(radius * Math.cos(i), 0, radius * Math.sin(i));
            player.getServerWorld().spawnParticles(particleEffect, pos.getX(), pos.getY(), pos.getZ(), 1, 0, 0, 0, 0);
        }
    }
}
