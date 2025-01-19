package org.gamesdonewr0ng.infuse.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.SparkItems;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Arrays;
import java.util.Objects;

@Mixin(LivingEntity.class)
public class ConsumeItemMixin {

    @Redirect(method = "consumeItem()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;finishUsing(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack onConsume(ItemStack instance, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity player) {
            if (instance.getComponents().contains(DataComponentTypes.CUSTOM_DATA)) {
                NbtCompound customData = Objects.requireNonNull(instance.getComponents().get(DataComponentTypes.CUSTOM_DATA)).copyNbt();
                if (customData.contains("infuse_ability")) {
                    String support = DataHandler.getSupport((IEntityDataSaver) player);
                    String primary = DataHandler.getPrimary((IEntityDataSaver) player);
                    SparksHandler.setAbility(player, customData.getString("infuse_ability"));
                    if (Arrays.asList(new String[]{"Speed", "Ocean", "Fire", "Emerald"}).contains(customData.getString("infuse_ability"))) {
                        if (!(support.isEmpty() || support.equals("Removed"))) {
                            return SparkItems.getItemstack(support);
                        }
                    } else {
                        if (!(primary.isEmpty() || primary.equals("Removed"))) {
                            return SparkItems.getItemstack(primary);
                        }
                    }
                }
            } else if (DataHandler.getPrimary((IEntityDataSaver) player).equals("Ender") && instance.isOf(Items.DRAGON_BREATH)) {
                if (!(user.hasStatusEffect(StatusEffects.REGENERATION) && Objects.requireNonNull(user.getStatusEffect(StatusEffects.REGENERATION)).getAmplifier() > 1)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.REGENERATION,
                            200,
                            1,
                            false,
                            true,
                            true
                    ));
                }
                if (!(user.hasStatusEffect(StatusEffects.ABSORPTION))) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.ABSORPTION,
                            4*60*20,
                            0,
                            false,
                            true,
                            true
                    ));
                }
            }
        }
        return instance.finishUsing(world, user);
    }
}
