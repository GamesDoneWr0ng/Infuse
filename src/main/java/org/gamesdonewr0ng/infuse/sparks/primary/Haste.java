package org.gamesdonewr0ng.infuse.sparks.primary;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;

public class Haste extends Spark {
    public int getCooldown() {return 75*20;}

    public void passive(ServerPlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.HASTE)) {return;}
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.HASTE,
                StatusEffectInstance.INFINITE,
                1,
                false,
                false,
                true
        ));
    }

    public void activate(ServerPlayerEntity player) {
        SparksHandler.activatePrimary(player, 45*20);

        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.HASTE,
                45*20,
                StatusEffectInstance.MAX_AMPLIFIER,
                false,
                true,
                true
        ));
        super.activate(player);
    }

    public void disable(ServerPlayerEntity player, boolean primary) {
        player.removeStatusEffect(StatusEffects.HASTE);
        super.disable(player, true);
    }
}
