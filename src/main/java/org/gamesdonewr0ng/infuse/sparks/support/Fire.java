package org.gamesdonewr0ng.infuse.sparks.support;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;

public class Fire extends Spark {
    public int getCooldown() {return 60*20;}

    public void passive(ServerPlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {return;}
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.FIRE_RESISTANCE,
                StatusEffectInstance.INFINITE,
                0,
                false,
                false,
                true
        ));
    }

    public void activate(ServerPlayerEntity player) {
        SparksHandler.activateSupport(player, 30*20);
    }

    public void active(ServerPlayerEntity player) {
        if (player.isOnFire()) {
            if (player.hasStatusEffect(StatusEffects.STRENGTH) && player.getStatusEffect(StatusEffects.STRENGTH).getAmplifier() > 0) return;
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.STRENGTH,
                    40,
                    0,
                    false,
                    true,
                    true
            ));
        }
    }

    public void disable(ServerPlayerEntity player, boolean primary) {
        player.removeStatusEffect(StatusEffects.FIRE_RESISTANCE);
        super.disable(player, false);
    }
}
