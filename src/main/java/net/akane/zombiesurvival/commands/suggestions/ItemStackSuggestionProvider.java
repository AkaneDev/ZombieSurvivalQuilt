package net.akane.zombiesurvival.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ItemStackSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String itemStack = context.getArgument("itemName", String.class);
        ArrayList<ItemStack> ItemStackList = new ArrayList<>();
        for (Item l : Registries.ITEM) {
            ItemStackList.add(l.getDefaultStack());
        }
        for (ItemStack l : ItemStackList) {
            if (l != null && CommandSource.shouldSuggest(builder.getRemaining(), String.valueOf(l.getName()))) {
                builder.suggest(String.valueOf(l.getName()));
            }
        }
        return builder.buildFuture();
    }
}
