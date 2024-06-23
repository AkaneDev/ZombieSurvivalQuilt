package akanedev.org.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

import static net.minecraft.server.command.CommandManager.literal;

public class CommandSenderCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("whoami")
                        .executes(CommandSenderCommand::execute)
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        context.getSource().sendFeedback(() -> Text.literal("Command Sender Is: " + source.getName()), true);
        return 1;
    }
}
