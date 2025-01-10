package org.gamesdonewr0ng.infuse.sparks.primary;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

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
        DataHandler.setCooldownPrimary((IEntityDataSaver) player, 45*20);
        DataHandler.setActivePrimary((IEntityDataSaver) player, true);

        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.HASTE,
                45*20,
                StatusEffectInstance.MAX_AMPLIFIER,
                false,
                true,
                true
        ));
    }

    public void disable(ServerPlayerEntity player, boolean primary) {
        player.removeStatusEffect(StatusEffects.HASTE);
        super.disable(player, true);
    }
}
