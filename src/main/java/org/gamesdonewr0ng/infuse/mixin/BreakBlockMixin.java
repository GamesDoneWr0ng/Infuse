package org.gamesdonewr0ng.infuse.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.tags.InfuseBlockTags;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Block.class)
public class BreakBlockMixin {
    @Inject(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;",
            at = @At("TAIL"),
            cancellable = true)
    private static void getDroppedStacks(
            BlockState state,
            ServerWorld world,
            BlockPos pos,
            @Nullable BlockEntity blockEntity,
            @Nullable Entity entity,
            ItemStack stack,
            CallbackInfoReturnable<List<ItemStack>> info
    ) {
        if (EnchantmentHelper.getLevel(world.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), stack) == 0 &&
            state.isIn(InfuseBlockTags.HASTE_MULTIPLIES) &&
            entity instanceof ServerPlayerEntity player
        ) {
            if (!player.isCreative() &&
                DataHandler.getPrimary((IEntityDataSaver) player).equals("Haste") &&
                DataHandler.getActivePrimary((IEntityDataSaver) player)
            ) {
                List<ItemStack> returnValue = info.getReturnValue();
                for (ItemStack itemStack : returnValue) {
                    itemStack.setCount(itemStack.getCount() * 2);
                }
                info.setReturnValue(returnValue);
            }
        }
    }
}
