package org.gamesdonewr0ng.infuse.mixin;

import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(ServerPlayerEntity.class)
public class DealDamageMixin {
    @Inject(method = "attack", at = @At("HEAD"))
    private void attack(Entity target, CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        ItemStack weapon = player.getMainHandStack();

        if (EnchantmentHelper.getLevel(player.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT), weapon) != 0) {
            removeFireAspect(weapon);
        }

        String primary = DataHandler.getPrimary((IEntityDataSaver) player);
        if (primary.equals("Lightning")) {
            // check critical and random
            if (player.fallDistance > 0.0F &&
                    !player.isOnGround() &&
                    !player.isTouchingWater() &&
                    !player.hasStatusEffect(StatusEffects.BLINDNESS) &&
                    !player.isClimbing() &&
                    !player.hasVehicle() &&
                    !player.isSprinting() &&
                     player.getAttackCooldownProgress(0.5F) > 0.9F &&
                    Math.random() > 0.5F) {

                World world = target.getWorld();
                if (!world.isClient() && world instanceof ServerWorld serverWorld) {
                    LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, serverWorld);
                    lightningEntity.setPosition(target.getPos());
                    serverWorld.spawnEntity(lightningEntity);
                }
            }
        } else if (primary.equals("Regeneration")) {
            if (player.fallDistance > 0.0F &&
                    !player.isOnGround() &&
                    !player.isTouchingWater() &&
                    !player.hasStatusEffect(StatusEffects.BLINDNESS) &&
                    !player.isClimbing() &&
                    !player.hasVehicle() &&
                    !player.isSprinting() &&
                    player.getAttackCooldownProgress(0.5F) > 0.9F) {
                if (player.hasStatusEffect(StatusEffects.REGENERATION) && Objects.requireNonNull(player.getStatusEffect(StatusEffects.REGENERATION)).getAmplifier()>1) {return;}
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.REGENERATION,
                        80,
                        1,
                        false,
                        false,
                        true
                ));
            }
        }
        if (DataHandler.getSupport((IEntityDataSaver) player).equals("Fire")) {
            target.setOnFireFor(4);
        }
    }

    @Unique
    private void removeFireAspect(ItemStack weapon) {
        ItemEnchantmentsComponent enchantments = EnchantmentHelper.getEnchantments(weapon);

        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(enchantments);
        builder.remove(entry -> entry.matchesKey(Enchantments.FIRE_ASPECT));

        EnchantmentHelper.set(weapon, builder.build());
    }
}
