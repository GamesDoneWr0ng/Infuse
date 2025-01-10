package org.gamesdonewr0ng.infuse.sparks.primary;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.sparks.Spark;

public class Invisibility extends Spark {
    public int getCooldown() {return 45*20;}

    public void passive(ServerPlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.INVISIBILITY)) {return;}
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.INVISIBILITY,
                StatusEffectInstance.INFINITE,
                0,
                false,
                true,
                true
        ));
    }

    public void disable(ServerPlayerEntity player, boolean primary) {
        player.removeStatusEffect(StatusEffects.INVISIBILITY);
        super.disable(player, true);
    }
}
