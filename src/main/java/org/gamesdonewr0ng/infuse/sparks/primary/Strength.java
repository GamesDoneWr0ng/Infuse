package org.gamesdonewr0ng.infuse.sparks.primary;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;

public class Strength extends Spark {
    public int getCooldown() {return 120*20;}

    public void passive(ServerPlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.STRENGTH)) {return;}
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.STRENGTH,
                StatusEffectInstance.INFINITE,
                0,
                false,
                false,
                true
        ));
    }

    public void activate(ServerPlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.STRENGTH,
                30*20,
                1,
                false,
                true,
                true
        ));
        SparksHandler.activatePrimary(player, 30*20);
        super.activate(player);
    }

    public void disable(ServerPlayerEntity player, boolean primary) {
        player.removeStatusEffect(StatusEffects.STRENGTH);
        super.disable(player, true);
    }
}
