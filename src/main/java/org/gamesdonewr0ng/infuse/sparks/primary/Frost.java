package org.gamesdonewr0ng.infuse.sparks.primary;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

public class Frost extends Spark {
    public int getCooldown() {return 60*20;}

    public void passive(ServerPlayerEntity player) {
        BlockPos playerPos = player.getBlockPos();
        ServerWorld world = player.getServerWorld();
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                for (int dz = -2; dz <= 2; dz++) {
                    if (Math.abs(dx)+Math.abs(dy)+Math.abs(dz) > 2) continue;
                    BlockPos pos = playerPos.add(dx, dy, dz);
                    if (world.getBlockState(pos).isIn(BlockTags.ICE)) {
                        player.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.SPEED,
                                40,
                                9,
                                false,
                                true,
                                true
                        ));
                        return;
                    } else if (world.getBlockState(pos).isIn(BlockTags.SNOW)) {
                        if (player.hasStatusEffect(StatusEffects.SPEED) && player.getStatusEffect(StatusEffects.SPEED).getAmplifier() > 2) {continue;}
                        player.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.SPEED,
                                40,
                                2,
                                false,
                                true,
                                true
                        ));
                    }
                }
            }
        }
    }

    public void activate(ServerPlayerEntity player) {
        DataHandler.setActivePrimary((IEntityDataSaver) player, true);
        DataHandler.setCooldownPrimary((IEntityDataSaver) player, 30*20);
    }
}
