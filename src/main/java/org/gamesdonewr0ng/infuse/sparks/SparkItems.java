package org.gamesdonewr0ng.infuse.sparks;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Optional;

public class SparkItems {
    public static ItemStack getItemstack(String ability) {
        Style NON_ITALIC = Style.EMPTY.withItalic(false);

        ItemStack item = new ItemStack(Items.POTION, 1);
        NbtComponent nbtComponent = NbtComponent.DEFAULT.apply(nbtCompound -> nbtCompound.putString("infuse_ability", ability));
        switch (ability) {
            case "Emerald" -> item.applyComponentsFrom(ComponentMap.builder()
                    .add(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .add(DataComponentTypes.CUSTOM_NAME, Text.literal("Emerald Effect").setStyle(NON_ITALIC).formatted(Formatting.GREEN))
                    .add(DataComponentTypes.LORE, new LoreComponent(List.of(
                            Text.literal("Support Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Grants ").setStyle(NON_ITALIC).append(Text.literal("Hero of the Village III").formatted(Formatting.ITALIC)).formatted(Formatting.GRAY),
                            Text.empty(),
                            Text.literal("Spark Ability").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Upgrades ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("Hero of the Village III ").formatted(Formatting.ITALIC)).append(Text.literal("to ").formatted(Formatting.RESET)).append(Text.literal("Hero of the Village ∞").formatted(Formatting.ITALIC)),
                            Text.empty(),
                            Text.literal("Duration: ").setStyle(NON_ITALIC).formatted(Formatting.DARK_GRAY).append(Text.literal("90s ").formatted(Formatting.DARK_AQUA)).append(Text.literal("Cooldown: ").formatted(Formatting.DARK_GRAY)).append(Text.literal("5m").formatted(Formatting.DARK_AQUA))
                    )))
                    .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(Colors.GREEN), List.of()))
                    .add(DataComponentTypes.CUSTOM_DATA, nbtComponent)
                    .build());

            case "Fire" -> item.applyComponentsFrom(ComponentMap.builder()
                    .add(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .add(DataComponentTypes.CUSTOM_NAME, Text.literal("Fire Effect").setStyle(NON_ITALIC).formatted(Formatting.RED))
                    .add(DataComponentTypes.LORE, new LoreComponent(List.of(
                            Text.literal("Support Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Grants ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("Fire Resistance").formatted(Formatting.ITALIC)),
                            Text.literal("+1 Attack Damage while ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("on fire").formatted(Formatting.GOLD)),
                            Text.empty(),
                            Text.literal("Spark Ability").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Regenerate health while ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("on fire").formatted(Formatting.GOLD)),
                            Text.empty(),
                            Text.literal("Duration: ").setStyle(NON_ITALIC).formatted(Formatting.DARK_GRAY).append(Text.literal("30s ").formatted(Formatting.DARK_AQUA)).append(Text.literal("Cooldown: ").formatted(Formatting.DARK_GRAY)).append(Text.literal("60s").formatted(Formatting.DARK_AQUA))
                    )))
                    .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(Colors.RED), List.of()))
                    .add(DataComponentTypes.CUSTOM_DATA, nbtComponent)
                    .build());

            case "Ocean" -> item.applyComponentsFrom(ComponentMap.builder()
                    .add(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .add(DataComponentTypes.CUSTOM_NAME, Text.literal("Ocean Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE))
                    .add(DataComponentTypes.LORE, new LoreComponent(List.of(
                            Text.literal("Support Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Grants ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("Dolphin's Grace").formatted(Formatting.ITALIC)),
                            Text.literal("Grants ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("Conduit Power").formatted(Formatting.ITALIC)),
                            Text.empty(),
                            Text.literal("Spark Ability").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("+2 Attack Damage while ").setStyle(NON_ITALIC) .formatted(Formatting.GRAY).append(Text.literal("in water").formatted(Formatting.BLUE)),
                            Text.literal("Regenerate health while ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("in water").formatted(Formatting.BLUE)),
                            Text.empty(),
                            Text.literal("Duration: ").formatted(Formatting.DARK_GRAY).append(Text.literal("30s ").formatted(Formatting.DARK_AQUA)).append(Text.literal("Cooldown: ").formatted(Formatting.DARK_GRAY)).append(Text.literal("60s").formatted(Formatting.DARK_AQUA))
                    )))
                    .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(Colors.BLUE), List.of()))
                    .add(DataComponentTypes.CUSTOM_DATA, nbtComponent)
                    .build());

            case "Speed" -> item.applyComponentsFrom(ComponentMap.builder()
                    .add(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .add(DataComponentTypes.CUSTOM_NAME, Text.literal("Speed Effect").setStyle(NON_ITALIC).formatted(Formatting.AQUA))
                    .add(DataComponentTypes.LORE, new LoreComponent(List.of(
                            Text.literal("Support Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Grants ").setStyle(NON_ITALIC).append(Text.literal("Speed II").formatted(Formatting.ITALIC)).formatted(Formatting.GRAY),
                            Text.empty(),
                            Text.literal("Spark Ability").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Dash forward").setStyle(NON_ITALIC).formatted(Formatting.GRAY),
                            Text.empty(),
                            Text.literal("Duration: ").setStyle(NON_ITALIC).formatted(Formatting.DARK_GRAY).append(Text.literal("1s ").formatted(Formatting.DARK_AQUA)).append(Text.literal("Cooldown: ").formatted(Formatting.DARK_GRAY)).append(Text.literal("15s").formatted(Formatting.DARK_AQUA))
                    )))
                    .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(Colors.ALTERNATE_WHITE), List.of()))
                    .add(DataComponentTypes.CUSTOM_DATA, nbtComponent)
                    .build());

            case "Feather" -> item.applyComponentsFrom(ComponentMap.builder()
                    .add(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .add(DataComponentTypes.CUSTOM_NAME, Text.literal("Feather Effect").setStyle(NON_ITALIC).formatted(Formatting.GRAY))
                    .add(DataComponentTypes.LORE, new LoreComponent(List.of(
                            Text.literal("Primary Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("No fall damage").setStyle(NON_ITALIC).formatted(Formatting.GRAY),
                            Text.literal("Can run on ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("Water").formatted(Formatting.BLUE)).append(Text.literal(" & ").formatted(Formatting.GRAY)).append(Text.literal("Lava").formatted(Formatting.GOLD)),
                            Text.empty(),
                            Text.literal("Spark Ability").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Burst of ").setStyle(NON_ITALIC).append(Text.literal("Levitation XXX").formatted(Formatting.ITALIC)).formatted(Formatting.GRAY),
                            Text.empty(),
                            Text.literal("Duration: ").setStyle(NON_ITALIC).formatted(Formatting.DARK_GRAY).append(Text.literal("2s ").formatted(Formatting.DARK_AQUA)).append(Text.literal("Cooldown: ").formatted(Formatting.DARK_GRAY)).append(Text.literal("30s").formatted(Formatting.DARK_AQUA)))))
                    .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(Colors.GRAY), List.of()))
                    .add(DataComponentTypes.CUSTOM_DATA, nbtComponent)
                    .build());

            case "Frost" -> item.applyComponentsFrom(ComponentMap.builder()
                    .add(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .add(DataComponentTypes.CUSTOM_NAME, Text.literal("Frost Effect").setStyle(NON_ITALIC).formatted(Formatting.DARK_AQUA))
                    .add(DataComponentTypes.LORE, new LoreComponent(List.of(
                            Text.literal("Primary Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Speed X").setStyle(NON_ITALIC).formatted(Formatting.GRAY, Formatting.ITALIC).append(Text.literal(" on ").setStyle(NON_ITALIC).formatted(Formatting.GRAY)).append(Text.literal("Ice").setStyle(NON_ITALIC).formatted(Formatting.DARK_AQUA)).append(Text.literal(" & ").setStyle(NON_ITALIC).append(Text.literal("Speed III").formatted(Formatting.ITALIC, Formatting.GRAY))).append(Text.literal(" on ").setStyle(NON_ITALIC).formatted(Formatting.GRAY)).append(Text.literal("Snow").setStyle(NON_ITALIC).formatted(Formatting.AQUA)),
                            Text.empty(),
                            Text.literal("Spark Ability").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Inflict ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("Frostbite ").formatted(Formatting.ITALIC, Formatting.AQUA)).append(Text.literal("on attacked enemies")),
                            Text.literal("+3 Attack damage").formatted(Formatting.ITALIC, Formatting.GRAY).append(Text.literal(" on frosted enemies").setStyle(NON_ITALIC)),
                            Text.empty(),
                            Text.literal("Duration: ").setStyle(NON_ITALIC).formatted(Formatting.DARK_GRAY).append(Text.literal("30s ").formatted(Formatting.DARK_AQUA)).append(Text.literal("Cooldown: ").formatted(Formatting.DARK_GRAY)).append(Text.literal("90s").formatted(Formatting.DARK_AQUA)))))
                    .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(Colors.BLUE), List.of()))
                    .add(DataComponentTypes.CUSTOM_DATA, nbtComponent)
                    .build());

            case "Haste" -> item.applyComponentsFrom(ComponentMap.builder()
                    .add(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .add(DataComponentTypes.CUSTOM_NAME, Text.literal("Haste Effect").setStyle(NON_ITALIC).formatted(Formatting.GOLD))
                    .add(DataComponentTypes.LORE, new LoreComponent(List.of(
                            Text.literal("Primary Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Grants ").setStyle(NON_ITALIC).append(Text.literal("Haste II").formatted(Formatting.ITALIC)).formatted(Formatting.GRAY),
                            Text.empty(),
                            Text.literal("Spark Ability").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Grants ").setStyle(NON_ITALIC).append(Text.literal("Instamine").formatted(Formatting.ITALIC)).append(Text.literal(" and doubles ore drops")).formatted(Formatting.GRAY),
                            Text.empty(),
                            Text.literal("Duration: ").setStyle(NON_ITALIC).formatted(Formatting.DARK_GRAY).append(Text.literal("45s ").formatted(Formatting.DARK_AQUA)).append(Text.literal("Cooldown: ").formatted(Formatting.DARK_GRAY)).append(Text.literal("75s").formatted(Formatting.DARK_AQUA)))))
                    .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(Colors.YELLOW), List.of()))
                    .add(DataComponentTypes.CUSTOM_DATA, nbtComponent)
                    .build());

            case "Heart" -> item.applyComponentsFrom(ComponentMap.builder()
                    .add(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .add(DataComponentTypes.CUSTOM_NAME, Text.literal("Heart Effect").setStyle(NON_ITALIC).formatted(Formatting.DARK_RED))
                    .add(DataComponentTypes.LORE, new LoreComponent(List.of(
                            Text.literal("Primary Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Grants ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("15 total hearts").formatted(Formatting.RED)),
                            Text.empty(),
                            Text.literal("Spark Ability").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Grants ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("20 total hearts").formatted(Formatting.RED)).append(Text.literal(" and a ").formatted(Formatting.GRAY)).append(Text.literal("full heal").formatted(Formatting.ITALIC)),
                            Text.empty(),
                            Text.literal("Duration: ").setStyle(NON_ITALIC).formatted(Formatting.DARK_GRAY).append(Text.literal("30s ").formatted(Formatting.DARK_AQUA)).append(Text.literal("Cooldown: ").formatted(Formatting.DARK_GRAY)).append(Text.literal("60s").formatted(Formatting.DARK_AQUA)))))
                    .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(Colors.RED), List.of()))
                    .add(DataComponentTypes.CUSTOM_DATA, nbtComponent)
                    .build());

            case "Invisibility" -> item.applyComponentsFrom(ComponentMap.builder()
                    .add(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .add(DataComponentTypes.CUSTOM_NAME, Text.literal("Invisibility Effect").setStyle(NON_ITALIC).formatted(Formatting.GRAY))
                    .add(DataComponentTypes.LORE, new LoreComponent(List.of(
                            Text.literal("Primary Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Grants ").setStyle(NON_ITALIC).append(Text.literal("Invisibility").formatted(Formatting.ITALIC)).formatted(Formatting.GRAY),
                            Text.empty(),
                            Text.literal("Spark Ability").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Hides armor and particles").setStyle(NON_ITALIC).formatted(Formatting.GRAY),
                            Text.empty(),
                            Text.literal("Duration: ").setStyle(NON_ITALIC).formatted(Formatting.DARK_GRAY).append(Text.literal("20s ").formatted(Formatting.DARK_AQUA)).append(Text.literal("Cooldown: ").formatted(Formatting.DARK_GRAY)).append(Text.literal("45s").formatted(Formatting.DARK_AQUA)))))
                    .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(Colors.WHITE), List.of()))
                    .add(DataComponentTypes.CUSTOM_DATA, nbtComponent)
                    .build());

            case "Regeneration" -> item.applyComponentsFrom(ComponentMap.builder()
                    .add(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .add(DataComponentTypes.CUSTOM_NAME, Text.literal("Regeneration Effect").setStyle(NON_ITALIC).formatted(Formatting.DARK_PURPLE))
                    .add(DataComponentTypes.LORE, new LoreComponent(List.of(
                            Text.literal("Primary Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Grants ").setStyle(NON_ITALIC).append(Text.literal("Regeneration II").formatted(Formatting.ITALIC)).append(Text.literal(" when attacking enemies")).formatted(Formatting.GRAY),
                            Text.empty(),
                            Text.literal("Spark Ability").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Regenerate self and teammates in a 16 block radius").setStyle(NON_ITALIC).formatted(Formatting.GRAY),
                            Text.empty(),
                            Text.literal("Duration: ").setStyle(NON_ITALIC).formatted(Formatting.DARK_GRAY).append(Text.literal("15s ").formatted(Formatting.DARK_AQUA)).append(Text.literal("Cooldown: ").formatted(Formatting.DARK_GRAY)).append(Text.literal("80s").formatted(Formatting.DARK_AQUA)))))
                    .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(Colors.LIGHT_RED), List.of()))
                    .add(DataComponentTypes.CUSTOM_DATA, nbtComponent)
                    .build());

            case "Strength" -> item.applyComponentsFrom(ComponentMap.builder()
                    .add(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .add(DataComponentTypes.CUSTOM_NAME, Text.literal("Strength Effect").setStyle(NON_ITALIC).formatted(Formatting.RED))
                    .add(DataComponentTypes.LORE, new LoreComponent(List.of(
                            Text.literal("Primary Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Grants ").setStyle(NON_ITALIC).append(Text.literal("Strength I").formatted(Formatting.ITALIC)).formatted(Formatting.GRAY),
                            Text.empty(),
                            Text.literal("Spark Ability").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Upgrades to ").setStyle(NON_ITALIC).append(Text.literal("Strength II").formatted(Formatting.ITALIC)).formatted(Formatting.GRAY),
                            Text.empty(),
                            Text.literal("Duration: ").setStyle(NON_ITALIC).formatted(Formatting.DARK_GRAY).append(Text.literal("30s ").formatted(Formatting.DARK_AQUA)).append(Text.literal("Cooldown: ").formatted(Formatting.DARK_GRAY)).append(Text.literal("120s").formatted(Formatting.DARK_AQUA)))))
                    .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(Colors.RED), List.of()))
                    .add(DataComponentTypes.CUSTOM_DATA, nbtComponent)
                    .build());

            case "Lightning" -> item.applyComponentsFrom(ComponentMap.builder()
                    .add(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .add(DataComponentTypes.CUSTOM_NAME, Text.literal("Lightning Effect").setStyle(NON_ITALIC).formatted(Formatting.DARK_AQUA))
                    .add(DataComponentTypes.LORE, new LoreComponent(List.of(
                            Text.literal("Primary Effect").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Chance to strike enemies with ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("Lightning").formatted(Formatting.DARK_AQUA)),
                            Text.empty(),
                            Text.literal("Spark Ability").setStyle(NON_ITALIC).formatted(Formatting.BLUE),
                            Text.literal("Strike enemies with ").setStyle(NON_ITALIC).formatted(Formatting.GRAY).append(Text.literal("Lightning").formatted(Formatting.DARK_AQUA)).append(Text.literal(" in a 16 block radius").formatted(Formatting.GRAY)),
                            Text.empty(),
                            Text.literal("Duration: ").setStyle(NON_ITALIC).formatted(Formatting.DARK_GRAY).append(Text.literal("10s ").formatted(Formatting.DARK_AQUA)).append(Text.literal("Cooldown: ").formatted(Formatting.DARK_GRAY)).append(Text.literal("80s").formatted(Formatting.DARK_AQUA)))))
                    .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(Colors.YELLOW), List.of()))
                    .add(DataComponentTypes.CUSTOM_DATA, nbtComponent)
                    .build());
        }

        return item;
    }
}
