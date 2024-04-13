package net.akane.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.akane.zombiesurvival.commands.suggestions.PowerSuggestionProvider;
import net.akane.zombiesurvival.util.IEntityDataSaver;
import net.akane.zombiesurvival.util.PowerData;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class PowerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("power")
                        .then(argument("player", EntityArgumentType.player())
                                .then(argument("power", StringArgumentType.string())
                                        .executes(PowerCommand::setPower)
                                )
                        )
        );
    }

    private static int setPower(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerEntity target = EntityArgumentType.getPlayer(context, "player");
        String powerValueString = StringArgumentType.getString(context, "power");

//        double powerValue;
//        try {
//            powerValue = Double.parseDouble(powerValueString);
//        } catch (NumberFormatException e) {
//            context.getSource().sendError(Text.of("Invalid power value. Must be a valid number."));
//            return 0;
//        }

        ServerPlayerEntity player = context.getSource().getPlayer();
        if (target == null) {
            context.getSource().sendError(Text.of("Player not found."));
            return 0;
        }
        PowerData.setPower((IEntityDataSaver) target, powerValueString);
        target.addCommandTag(powerValueString);
        // Store the power value in the player's NBT data
        context.getSource().sendFeedback(() -> Text.literal(String.format("Set power for player %s to %s", target.getDisplayName().getString(), powerValueString)), true);
        return 1;
    }

}
