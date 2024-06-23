package akanedev.org.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.akane.zombiesurvival.util.ShapeDrawer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class spheretest {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(
			literal("sphere")
				.then(argument("radius", FloatArgumentType.floatArg())
					.executes(spheretest::execute)
				)
		);
	}

	private static int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		Float radius = FloatArgumentType.getFloat(context, "radius");
		if (radius != null) {
			float[] color = {1.0f, 0.0f, 0.0f, 1.0f}; // Red color
			ShapeDrawer.drawSphereLine(Objects.requireNonNull(source.getEntity()).getBlockPos(), radius, color, context.getSource().getWorld());
			source.sendFeedback(() -> Text.literal(String.format("Done")), true);
			return 1;
		}
		return 0;
	}
}
