package net.akane.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class FalseAdvancementCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("falseadvancement")
                        .then(argument("player", StringArgumentType.string())
                                .then(argument("advancement", StringArgumentType.string())
                                        .executes(FalseAdvancementCommand::execute)
                                )
                        )
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        // Get the player's name and the advancement name from command arguments
        String player = StringArgumentType.getString(context, "player");
        String advancement = StringArgumentType.getString(context, "advancement");

        // Format the message
        String message = player + " has completed the challenge §5[" + advancement + "]";

        // Send the message to the command sender
        context.getSource().sendFeedback(() -> Text.literal(message), false);

        // Return success
        return 1;
    }


}
