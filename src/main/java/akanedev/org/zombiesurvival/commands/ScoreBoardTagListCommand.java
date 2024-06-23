package akanedev.org.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.quiltmc.qsl.command.api.client.QuiltClientCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class ScoreBoardTagListCommand {
	public static void registerServer(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(
			literal("taglistServer")
				.executes(ScoreBoardTagListCommand::executeServer)
		);
	}

	private static int executeServer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		PlayerEntity player = context.getSource().getPlayer();
		for (String s : player.getScoreboardTags()) {
			player.sendMessage(Text.literal(s), false);
		}
		return 1;
	}
}
