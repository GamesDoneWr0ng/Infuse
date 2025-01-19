package org.gamesdonewr0ng.infuse.sparks.primary;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityEquipmentUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

import java.util.List;
import java.util.Objects;

public class Invisibility extends Spark {
    public int getCooldown() {return 45*20;}

    public void passive(ServerPlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.INVISIBILITY)) {return;}
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.INVISIBILITY,
                StatusEffectInstance.INFINITE,
                0,
                false,
                true,
                true
        ));
    }

    public void activate(ServerPlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.INVISIBILITY,
                20*20,
                1,
                false,
                false,
                true
        ));
        for (ServerPlayerEntity otherPlayer : Objects.requireNonNull(player.getServer()).getPlayerManager().getPlayerList()) {
            if (!player.equals(otherPlayer)) {
                otherPlayer.networkHandler.sendPacket(
                        new EntityEquipmentUpdateS2CPacket(player.getId(), List.of(
                                Pair.of(EquipmentSlot.HEAD,     ItemStack.EMPTY),
                                Pair.of(EquipmentSlot.CHEST,    ItemStack.EMPTY),
                                Pair.of(EquipmentSlot.LEGS,     ItemStack.EMPTY),
                                Pair.of(EquipmentSlot.FEET,     ItemStack.EMPTY),
                                Pair.of(EquipmentSlot.MAINHAND, ItemStack.EMPTY),
                                Pair.of(EquipmentSlot.OFFHAND,  ItemStack.EMPTY)
                        ))
                );
            }
        }
        SparksHandler.activatePrimary(player, 20*20);
        super.activate(player);
    }

    public void active(ServerPlayerEntity player) {
        DefaultedList<ItemStack> armor = player.getInventory().armor;
        if (DataHandler.getCooldownPrimary((IEntityDataSaver) player) == 0) {
            for (ServerPlayerEntity otherPlayer : Objects.requireNonNull(player.getServer()).getPlayerManager().getPlayerList()) {
                if (!player.equals(otherPlayer)) {
                    otherPlayer.networkHandler.sendPacket(
                            new EntityEquipmentUpdateS2CPacket(player.getId(), List.of(
                                    Pair.of(EquipmentSlot.HEAD,     armor.get(3)),
                                    Pair.of(EquipmentSlot.CHEST,    armor.get(2)),
                                    Pair.of(EquipmentSlot.LEGS,     armor.get(1)),
                                    Pair.of(EquipmentSlot.FEET,     armor.get(0)),
                                    Pair.of(EquipmentSlot.MAINHAND, player.getMainHandStack())
                            ))
                    );
                }
            }
        }
    }

    public void disable(ServerPlayerEntity player, boolean primary) {
        player.removeStatusEffect(StatusEffects.INVISIBILITY);
        super.disable(player, true);
    }
}
