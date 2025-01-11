package org.gamesdonewr0ng.infuse.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerEntity.class)
public class ModifyDamageMixin {
    @ModifyArgs(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getDamageAgainst(Lnet/minecraft/entity/Entity;FLnet/minecraft/entity/damage/DamageSource;)F"))
    private void modifyDamage(Args args) {
        float amount = args.get(1);
        Entity entity = args.get(0);
        if ((Object) this instanceof ServerPlayerEntity player) {
            if (entity instanceof LivingEntity target) {
                String support = DataHandler.getSupport((IEntityDataSaver) player);
                String primary = DataHandler.getPrimary((IEntityDataSaver) player);
                boolean activePrimary = DataHandler.getActivePrimary((IEntityDataSaver) player);
                boolean activeSupport = DataHandler.getActiveSupport((IEntityDataSaver) player);

                if (activePrimary && primary.equals("Frost")) {
                    if (target.getFrozenTicks() > 0) {
                        amount += 3;
                    }
                    target.setFrozenTicks(30*20);
                } else if (activeSupport && support.equals("Fire") && player.isOnFire()) {
                    amount += 1;
                } else if (activeSupport && support.equals("Ocean") && player.isTouchingWater()) {
                    amount += 2;
                }
            }
        }

        args.set(1, amount);
    }
}
