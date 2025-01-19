package org.gamesdonewr0ng.infuse.sparks.primary;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;
import org.joml.Vector3f;

public class Lightning extends Spark {
    public int getCooldown() {return 80*20;}

    public void activate(ServerPlayerEntity player) {
        SparksHandler.activatePrimary(player, 10*20);
        super.activate(player);
    }

    public void active(ServerPlayerEntity player) {
        if ((DataHandler.getCooldownPrimary((IEntityDataSaver) player) % 5) != 0) {return;}
        this.aura(player, new DustParticleEffect(new Vector3f(0.0157f, 0.3412f, 0.4392f), 2), 16, 2);
        if ((DataHandler.getCooldownPrimary((IEntityDataSaver) player) % 4) != 0) {return;}

        World world = player.getWorld();
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            Vec3d pos = player.getPos();
            Box box = new Box(pos.subtract(16,16,16), pos.add(16,16,16));
            for (Entity entity : serverWorld.getOtherEntities(player, box)) {
                if (entity == null) {continue;}
                if (entity instanceof ServerPlayerEntity otherPlayer) {
                    if (DataHandler.getTrustList((IEntityDataSaver) player, otherPlayer.getUuid().toString())) {continue;}
                }
                if (player.distanceTo(entity) <= 16) {
                    LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, serverWorld);
                    lightningEntity.setPosition(entity.getPos());
                    serverWorld.spawnEntity(lightningEntity);
                }
            }
        }
    }
}
