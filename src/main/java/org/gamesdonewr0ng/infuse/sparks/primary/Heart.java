package org.gamesdonewr0ng.infuse.sparks.primary;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.gamesdonewr0ng.infuse.DataHandler;
import org.gamesdonewr0ng.infuse.sparks.Spark;
import org.gamesdonewr0ng.infuse.sparks.SparksHandler;
import org.gamesdonewr0ng.infuse.util.IEntityDataSaver;

import java.util.Objects;

public class Heart extends Spark {
    public int getCooldown() {
        return 60*20;
    }

    public void passive(ServerPlayerEntity player) {
        EntityAttributeInstance attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        assert attributeInstance != null;
        if (attributeInstance.hasModifier(Identifier.of("infuse", "heart"))) return;
        attributeInstance.addTemporaryModifier(
                new EntityAttributeModifier(
                        Identifier.of("infuse", "heart"),
                        10,
                        EntityAttributeModifier.Operation.ADD_VALUE
                )
        );
    }

    public void activate(ServerPlayerEntity player) {
        SparksHandler.activatePrimary(player, 30*20);
        EntityAttributeInstance attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        assert attributeInstance != null;
        attributeInstance.removeModifier(Identifier.of("infuse", "heart"));
        attributeInstance.addTemporaryModifier(
                new EntityAttributeModifier(
                        Identifier.of("infuse", "heart"),
                        20,
                        EntityAttributeModifier.Operation.ADD_VALUE
                )
        );
        player.setHealth(40);
        super.activate(player);

    }

    public void active(ServerPlayerEntity player) {
        if (DataHandler.getCooldownPrimary((IEntityDataSaver) player) == 0) {
            float health = player.getHealth();
            EntityAttributeInstance attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            assert attributeInstance != null;
            attributeInstance.removeModifier(Identifier.of("infuse", "heart"));
            attributeInstance.addTemporaryModifier(
                    new EntityAttributeModifier(
                            Identifier.of("infuse", "heart"),
                            10,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
            );
            player.setHealth(Math.min(health, 30F));
        }
    }

    public void disable(ServerPlayerEntity player, boolean primary) {
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).removeModifier(Identifier.of("infuse", "heart"));
        super.disable(player, true);
    }
}
