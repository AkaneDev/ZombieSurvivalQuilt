package net.akane.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class RenameCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("rename")
					.requires(source -> source.hasPermissionLevel(2))
					.then(argument("name", StringArgumentType.string())
							.executes(RenameCommand::renameItem)
					)
        );
    }

    private static int renameItem(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        String name = StringArgumentType.getString(context, "name");
        if (name == null) {
            context.getSource().sendError(Text.of("PUT A NAME IN"));
            return 0;
        }
        assert player != null;
        player.getInventory().getMainHandStack().setCustomName(Text.of(name));
        return 1;
    }
}
