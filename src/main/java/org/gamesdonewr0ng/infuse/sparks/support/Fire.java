package org.gamesdonewr0ng.infuse.sparks.support;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;

import java.util.Objects;

public class Fire extends Spark {
    public int getCooldown() {return 60*20;}

    public void activate(ServerPlayerEntity player) {
        SparksHandler.activateSupport(player, 30*20);
        super.activate(player);
    }

    public void active(ServerPlayerEntity player) {
        if (player.isOnFire()) {
            if (player.hasStatusEffect(StatusEffects.REGENERATION) && Objects.requireNonNull(player.getStatusEffect(StatusEffects.REGENERATION)).getAmplifier() > 0) return;
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
}
