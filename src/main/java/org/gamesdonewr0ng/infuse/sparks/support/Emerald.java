package org.gamesdonewr0ng.infuse.sparks.support;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;

public class Emerald extends Spark {
    public int getCooldown() {return 5*60*20;}

    @Override
    public void passive(ServerPlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE) && player.getStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE).getAmplifier()>2) {return;}
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.HERO_OF_THE_VILLAGE,
                StatusEffectInstance.INFINITE,
                2,
                false,
                false,
                true
        ));
    }

    public void activate(ServerPlayerEntity player) {
        SparksHandler.activateSupport(player, 90*20);
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.HERO_OF_THE_VILLAGE,
                90*20,
                199,
                false,
                true,
                true
        ));
    }

    public void disable(ServerPlayerEntity player, boolean primary) {
        player.removeStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE);
        super.disable(player, false);
    }
}
