package akanedev.org.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.akane.akanedata.DataArray;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static net.akane.akanemaths.MathEvaluator.evaluate;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class NullIfy {

	public static List<Text> players = new ArrayList<>();

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(
			literal("nullify")
				.executes(NullIfy::execute)
		);
	}

	private static int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		if (source.getEntity() instanceof PlayerEntity player) {
			if(!players.contains(player.getDisplayName())) {
				players.add(player.getDisplayName());
			}
			player.kill();
		}
		return 0;
	}
}
