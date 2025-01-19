package org.gamesdonewr0ng.infuse.commands;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

public class Suggesters {
    private static final String[] supports = {"Speed", "Ocean", "Fire", "Emerald"};
    private static final String[] primaries = {"Feather", "Frost", "Haste", "Heart", "Invisibility", "Regeneration", "Strength", "Lightning", "Ender"};
    private static final String[] abilities = {"All", "Speed", "Ocean", "Fire", "Emerald", "Feather", "Frost", "Haste", "Heart", "Invisibility", "Regeneration", "Strength", "Lightning", "Ender"};

    public static final SuggestionProvider<ServerCommandSource> SUPPORT = SuggestionProviders.register(
            Identifier.of("infuse", "support_suggestion_provider"),
            (context, builder) -> CommandSource.suggestMatching(supports, builder)
    );

    public static final SuggestionProvider<ServerCommandSource> PRIMARY = SuggestionProviders.register(
            Identifier.of("infuse", "primary_suggestion_provider"),
            (context, builder) -> CommandSource.suggestMatching(primaries, builder)
    );

    public static final SuggestionProvider<ServerCommandSource> ABILITIES = SuggestionProviders.register(
            Identifier.of("infuse", "ability_suggestion_provider"),
            (context, builder) -> CommandSource.suggestMatching(abilities, builder)
    );
}
