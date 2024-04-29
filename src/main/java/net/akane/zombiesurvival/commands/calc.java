package net.akane.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.akane.akanemaths.MathEvaluator.evaluate;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class calc {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(
			literal("calc")
				.then(argument("evaluate", StringArgumentType.string())
					.executes(calc::execute)
				)
		);
	}

	private static int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		String expression = StringArgumentType.getString(context, "evaluate");
		if (expression != null) {
			double result = evaluate(expression);
			source.sendFeedback(() -> Text.literal(String.format("Result %s", result)), true);
			return 1;
		}
		return 0;
	}
}
