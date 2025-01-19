package org.gamesdonewr0ng.infuse.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.HitResult;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderPearlEntity.class)
public class EnderPearlEntityMixin {

    @Inject(method = "onCollision", at = @At("HEAD"))
    private void onEnderPearlCollision(HitResult hitResult, CallbackInfo ci) {
        EnderPearlEntity pearl = (EnderPearlEntity) (Object) this;

        if (!pearl.getWorld().isClient && pearl.getOwner() instanceof ServerPlayerEntity player) {
            if (DataHandler.getPrimary((IEntityDataSaver) player).equals("Ender")) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.RESISTANCE,
                        5*20,
                        0,
                        false,
                        true,
                        true
                ));
            }
        }
    }
}
