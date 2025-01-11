package org.gamesdonewr0ng.infuse.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class TakeDamageMixin {

    @Inject(method = "damage",
            at = @At(value = "HEAD"),
            cancellable = true)
    private void damage(DamageSource source, float originalAmount, CallbackInfoReturnable<Boolean> info) {
        if ((Object) this instanceof ServerPlayerEntity player) {
            String primary = DataHandler.getPrimary((IEntityDataSaver) player);
            String support = DataHandler.getSupport((IEntityDataSaver) player);
            if (primary.equals("Lightning")) {
                if (source.isIn(DamageTypeTags.IS_LIGHTNING)) {
                    info.setReturnValue(false);
                    info.cancel();
                }
            } else if (primary.equals("Feather")) {
                if (source.isIn(DamageTypeTags.IS_FALL)) {
                    info.setReturnValue(false);
                    info.cancel();
                }
            } else if (support.equals("Fire")) {
                if (source.isIn(DamageTypeTags.IS_FIRE) || source.isIn(DamageTypeTags.BURN_FROM_STEPPING)) {
                    info.setReturnValue(false);
                    info.cancel();
                }
            }
        }
    }
}
