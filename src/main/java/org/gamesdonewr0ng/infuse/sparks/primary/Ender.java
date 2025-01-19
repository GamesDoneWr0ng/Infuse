package org.gamesdonewr0ng.infuse.sparks.primary;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Ender extends Spark {
    public int getCooldown() {return 120*20;}

    public void passive(ServerPlayerEntity player) {
        ItemStack item = player.getMainHandStack();
        if (item.isOf(Items.DRAGON_BREATH)) {
            if (item.getComponents().contains(DataComponentTypes.FOOD)) {return;}
            ComponentMap components = ComponentMap.builder()
                    .add(DataComponentTypes.FOOD,
                            new FoodComponent(
                                    0,
                                    0,
                                    true,
                                    1.6F,
                                    Optional.of(new ItemStack(Items.GLASS_BOTTLE, 1)),
                                    List.of()))
                    .build();
            item.applyComponentsFrom(components);
        }

        for (ServerPlayerEntity otherPlayer : Objects.requireNonNull(player.getServer()).getPlayerManager().getPlayerList()) {
            if (!otherPlayer.equals(player) && player.distanceTo(otherPlayer) < 64) {
                otherPlayer.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.GLOWING,
                        20,
                        0,
                        false,
                        false,
                        true
                ));
            }
        }
    }

    public void activate(ServerPlayerEntity player) {
        DataHandler.setActivePrimary((IEntityDataSaver) player, true);
        DataHandler.setCooldownPrimary((IEntityDataSaver) player, 15*20);
    }

    public void active(ServerPlayerEntity player) {
        if (DataHandler.getCooldownPrimary((IEntityDataSaver) player) % 5 != 0) return;

        World world = player.getWorld();
        Vec3d pos = player.getPos();

        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world, pos.getX(), pos.getY(), pos.getZ());
        cloud.setOwner(player);
        cloud.setRadius(3.0F);
        cloud.setDuration(10);
        cloud.setParticleType(ParticleTypes.DRAGON_BREATH);
        cloud.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE));
        world.spawnEntity(cloud);
    }
}
