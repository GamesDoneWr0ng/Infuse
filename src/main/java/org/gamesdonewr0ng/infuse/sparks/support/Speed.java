package org.gamesdonewr0ng.infuse.sparks.support;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

public class Speed extends Spark {
    public int getCooldown() {return 15*20;}

    public void passive(ServerPlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.SPEED) && player.getStatusEffect(StatusEffects.SPEED).getAmplifier()>1) {return;}
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.SPEED,
                StatusEffectInstance.INFINITE,
                1,
                false,
                false,
                true
        ));
    }

    public void activate(ServerPlayerEntity player) {
        Vec3d lookDirection = player.getRotationVec(1.0F); // Player's look direction
        Vec3d velocity = lookDirection.multiply(2); // Adjust multiplier for speed
        player.addVelocity(velocity.x, velocity.y, velocity.z);
        player.velocityModified = true; // Ensure server updates the velocity

        DataHandler.setActiveSupport((IEntityDataSaver) player, true);
        DataHandler.setCooldownSupport((IEntityDataSaver) player, 20);
    }

    public void disable(ServerPlayerEntity player, boolean primary) {
        player.removeStatusEffect(StatusEffects.SPEED);
        super.disable(player, false);
    }
}
