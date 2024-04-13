package net.akane.zombiesurvival.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class PowerSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String powerString = context.getArgument("power", String.class);
        ArrayList<String> PowerList = new ArrayList<>();
        PowerList.add("ElytraForever");
        for (String s : PowerList) {
            if (s != null && CommandSource.shouldSuggest(builder.getRemaining(), s)) {
                builder.suggest(s);
            }
        }
        return builder.buildFuture();
    }
}
