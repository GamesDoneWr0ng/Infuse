package org.gamesdonewr0ng.infuse.sparks.support;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;

public class Ocean extends Spark {
    public int getCooldown() {return 60*20;}

    public void passive(ServerPlayerEntity player) {
        if (!player.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.CONDUIT_POWER,
                    StatusEffectInstance.INFINITE,
                    0,
                    false,
                    false,
                    true
            ));
        }

        if (!player.hasStatusEffect(StatusEffects.DOLPHINS_GRACE)) {
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.DOLPHINS_GRACE,
                    StatusEffectInstance.INFINITE,
                    0,
                    false,
                    false,
                    true
            ));
        }
    }

    public void activate(ServerPlayerEntity player) {
        SparksHandler.activateSupport(player, 30*20);
    }

    public void active(ServerPlayerEntity player) {
        if (player.isTouchingWater()) {
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.REGENERATION,
                    40,
                    0,
                    false,
                    true,
                    true
            ));
        }
    }

    public void disable(ServerPlayerEntity player, boolean primary) {
        player.removeStatusEffect(StatusEffects.CONDUIT_POWER);
        player.removeStatusEffect(StatusEffects.DOLPHINS_GRACE);
        super.disable(player, false);
    }
}
