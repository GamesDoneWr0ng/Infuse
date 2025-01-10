package org.gamesdonewr0ng.infuse.sparks.primary;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

public class Regeneration extends Spark {
    public int getCooldown() {return 80*20;}

    public void activate(ServerPlayerEntity player) {
        DataHandler.setActivePrimary((IEntityDataSaver) player, true);
        DataHandler.setCooldownPrimary((IEntityDataSaver) player, 15*20);
    }

    public void active(ServerPlayerEntity player) {
        if ((DataHandler.getCooldownPrimary((IEntityDataSaver) player) % 20) != 0) {return;}

        if (player.hasStatusEffect(StatusEffects.REGENERATION) && player.getStatusEffect(StatusEffects.REGENERATION).getAmplifier() > 1) {return;}
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.REGENERATION,
                60,
                1,
                false,
                true,
                true
        ));

        World world = player.getWorld();
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            Vec3d pos = player.getPos();
            Box box = new Box(pos.subtract(16,16,16), pos.add(16,16,16));
            for (Entity entity : serverWorld.getOtherEntities(player, box)) {
                if (entity == null) {continue;}
                if (player.distanceTo(entity) <= 16 && entity instanceof ServerPlayerEntity otherPlayer) {
                    if (!DataHandler.getTrustList((IEntityDataSaver) player, otherPlayer.getUuid().toString())) {continue;}
                    if (otherPlayer.hasStatusEffect(StatusEffects.REGENERATION) && otherPlayer.getStatusEffect(StatusEffects.REGENERATION).getAmplifier() > 1) {return;}
                    otherPlayer.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.REGENERATION,
                            60,
                            1,
                            false,
                            true,
                            true
                    ));
                }
            }
        }
    }
}
