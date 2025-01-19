package org.gamesdonewr0ng.infuse.mixin;

import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class SwapHandsMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onPlayerAction",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/network/packet/c2s/play/PlayerActionC2SPacket;getAction()Lnet/minecraft/network/packet/c2s/play/PlayerActionC2SPacket$Action;",
                    shift = At.Shift.AFTER),
            cancellable = true)
    private void onSwapHands(PlayerActionC2SPacket packet, CallbackInfo ci) {
        if (packet.getAction() == PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND) {
            ci.cancel();
            Spark primary = SparksHandler.getPrimary(player);
            Spark support = SparksHandler.getSupport(player);
            if (player.isSneaking() && support != null) {
                if (DataHandler.getCooldownSupport((IEntityDataSaver) player) == 0) {
                    support.activate(player);
                }
            } else if (primary != null) {
                if (DataHandler.getCooldownPrimary((IEntityDataSaver) player) == 0) {
                    primary.activate(player);
                }
            }
        }
    }
}
