package org.gamesdonewr0ng.infuse.sparks.primary;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

import java.util.HashMap;

public class Feather extends Spark {
    private static HashMap<ServerPlayerEntity, HashMap<BlockPos, BlockState>> blocks = new HashMap<>();

    public int getCooldown() {return 30*20;}

    public void passive(ServerPlayerEntity player) {
        if (!blocks.containsKey(player)) {
            blocks.put(player, new HashMap<>());
        }

        BlockPos blockPos = player.getBlockPos();
        ServerWorld world = player.getServerWorld();

        HashMap<BlockPos, BlockState> playerBlocks = new HashMap<>();

        if (player.isSneaking() ||
                world.getBlockState(blockPos.add(0,0,0)).isOf(Blocks.WATER) ||
                world.getBlockState(blockPos.add(0,1,0)).isOf(Blocks.WATER) ||
                world.getBlockState(blockPos.add(0,0,0)).isOf(Blocks.LAVA)  ||
                world.getBlockState(blockPos.add(0,1,0)).isOf(Blocks.LAVA)) return;

        for (int i = 0; i < 3; i++) {
            blockPos = blockPos.add(0,-1,0);
            if (!world.isAir(blockPos)) {
                break;
            }
        }

        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                for (int dz = -2; dz <= 2; dz++) {
                    if (Math.abs(dx)+Math.abs(dy)+Math.abs(dz) > 2) continue;
                    BlockPos pos = blockPos.add(dx, dy, dz);
                    BlockState block = world.getBlockState(pos);
                    if (blocks.get(player).containsKey(pos)) {
                        playerBlocks.put(pos, blocks.get(player).get(pos));
                    } else if (block.isOf(Blocks.WATER) || block.isOf(Blocks.LAVA)) {
                        playerBlocks.put(pos, block);
                        world.setBlockState(pos, block.isOf(Blocks.WATER) ? Blocks.ICE.getDefaultState() : Blocks.OBSIDIAN.getDefaultState());
                    }
                }
            }
        }

        for (BlockPos pos : blocks.get(player).keySet()) {
            if (!playerBlocks.containsKey(pos)) {
                world.setBlockState(pos, blocks.get(player).get(pos));
            }
        }

        blocks.put(player, playerBlocks);
    }

    public void activate(ServerPlayerEntity player) {
        DataHandler.setActivePrimary((IEntityDataSaver) player, true);
        DataHandler.setCooldownPrimary((IEntityDataSaver) player, 40);

        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.LEVITATION,
                40,
                29,
                false,
                true,
                true
        ));
    }
}
