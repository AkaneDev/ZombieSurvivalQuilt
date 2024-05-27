package net.akane.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.akane.zombiesurvival.ZombieSurvival;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class sudoCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(
			literal("sudo")
				.then(argument("command", StringArgumentType.string())
					.executes(sudoCommand::execute)
				)
		);
	}

	private static int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		ServerCommandSource console = source.withLevel(4);
		String command = StringArgumentType.getString(context, "command");

		PlayerEntity playerEntity = context.getSource().getPlayer();
		boolean isConsole = (Objects.equals(context.getSource().getName(), "Server"));
		boolean isAkane = (Objects.equals(context.getSource().getName(), "AkaneDev"));
		boolean isDebug = ZombieSurvival._DEBUG;
        boolean isTaggedCorrectly = false;
		if (!isConsole) {
			if (playerEntity != null) {
				for (String tag : playerEntity.getScoreboardTags()) {
					isTaggedCorrectly = tag.equalsIgnoreCase("sudoUser");
				}
			}
			if (isAkane || isTaggedCorrectly || isDebug) {
				context.getSource().getServer().getCommandManager().executePrefixedCommand(console, command);
				return 1;
			}
			return 0;
		}
		return 0;
	}
}
