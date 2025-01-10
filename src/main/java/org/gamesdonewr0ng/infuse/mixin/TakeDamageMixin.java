package org.gamesdonewr0ng.infuse.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public abstract class TakeDamageMixin {

    @Inject(method = "damage",
            at = @At(value = "HEAD"),
            cancellable = true)
    private void damage(DamageSource source, float originalAmount, CallbackInfoReturnable<Boolean> info) {
        if ((Object) this instanceof ServerPlayerEntity player) {
            String primary = DataHandler.getPrimary((IEntityDataSaver) player);
            if (primary.equals("Lightning")) {
                if (source.getType().msgId().equals("lightningBolt")) {
                    info.setReturnValue(false);
                    info.cancel();
                }
            } else if (primary.equals("Feather")) {
                if (source.getType().msgId().equals("fall")) {
                    info.setReturnValue(false);
                    info.cancel();
                }
            }
        }
    }

    @ModifyArgs(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"))
    private void modifyDamage(Args args) {
        DamageSource source = args.get(0);
        float amount = args.get(1);
        if ((Object) this instanceof LivingEntity target) {
            if (source.getAttacker() instanceof ServerPlayerEntity player) {
                if (DataHandler.getActivePrimary((IEntityDataSaver) player) && DataHandler.getPrimary((IEntityDataSaver) player).equals("Frost")) {
                    if (target.getFrozenTicks() > 0) {
                        amount += 3;
                    }
                    target.setFrozenTicks(30*20);
                }
            }
        }

        args.set(1, amount);
    }
}
