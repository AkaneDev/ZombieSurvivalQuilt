package net.akane.zombiesurvival.client.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.quiltmc.qsl.command.api.client.QuiltClientCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class ScoreBoardClientCommand {
	public static int execute(CommandContext<QuiltClientCommandSource> context) {
		PlayerEntity player = context.getSource().getPlayer();
		for (String s : player.getScoreboardTags()) {
			player.sendMessage(Text.literal(s), false);
		}
		return 1;
	}

}
