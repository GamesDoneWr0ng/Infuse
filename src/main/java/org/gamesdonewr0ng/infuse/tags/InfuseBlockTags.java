package org.gamesdonewr0ng.infuse.tags;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class InfuseBlockTags {
    public static final TagKey<Block> HASTE_MULTIPLIES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "ores"));
}
