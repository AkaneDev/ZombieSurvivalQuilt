package net.akane.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.SneakyThrows;
import net.akane.zombiesurvival.Debug.DropAllHeadsAtWorldSpawn;
import net.akane.zombiesurvival.ZombieSurvival;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import javax.script.*;

import java.util.Objects;
import java.util.Random;

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
		dispatcher.register(
			literal("eval")
				.then(argument("evaluate", StringArgumentType.string())
					.then(argument("arg", StringArgumentType.string())
						.executes(calc::executeCode)
					)
				)
		);
	}

	private static int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		String expression = StringArgumentType.getString(context, "evaluate");
		if (expression != null) {
			double result = evaluate(expression);
			source.sendFeedback(Text.literal(String.format("Result %s", result)), true);
			return 1;
		}
		return 0;
	}

	private static int executeCode(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerCommandSource source = context.getSource();
		String expression = StringArgumentType.getString(context, "evaluate");
		String arg = StringArgumentType.getString(context, "arg");
		if (expression != null) {
			double result = evaluatecode(expression, arg, Objects.requireNonNull(source.getPlayer()), source.getWorld(), context, source);
			source.sendFeedback(Text.literal(String.format("Result %s", result)), true);
			return 1;
		}
		return 0;
	}

	public static double evaluatecode(String expression, String arg, PlayerEntity player, World world, CommandContext<ServerCommandSource> context, ServerCommandSource source) {
		for (String tag : player.getScoreboardTags()) {
			if (tag.equalsIgnoreCase("RandomBlockDrops")) {
				return 0;
			}
		}
		ScriptEngine js = new ScriptEngineManager().getEngineByName("rhino");
		if (js == null) {
			player.sendMessage(Text.of("JAVASCRIPT ENGINE IS NULL"), false);
			ZombieSurvival.LOGGER.error("JAVASCRIPT ENGINE IS NULL");
			return 0;
		}
//		ItemStack[] allItems = DropAllHeadsAtWorldSpawn.getAllItems();
//		allItems.


		Bindings bindings = js.createBindings();
		bindings.put("stdout", System.out);
		bindings.put("player", player);
		bindings.put("context", context);
		bindings.put("source", source);
		bindings.put("world", world);
		bindings.put("getblock", getBlockLookingAt(player));
		bindings.put("arg", arg);
		bindings.put("textofarg", Text.of(arg));

		try {
			// Example of a controlled operation
			Object result = js.eval(expression, bindings);
			player.sendMessage(Text.of("Result: " + result), false);
			return result instanceof Number ? ((Number) result).doubleValue() : 0;
		} catch (ScriptException e) {
			e.printStackTrace();
			player.sendMessage(Text.of("Error evaluating expression: " + e.getMessage()), false);
			return 0;
		}
	}

	public static BlockPos getBlockLookingAt(PlayerEntity player) {
		MinecraftServer server = player.getServer();
		if (server != null) {
			World world = player.getWorld();
			double reachDistance = 6;
			Vec3d eyePos = player.getCameraPosVec(1.0F);
			Vec3d lookDir = player.getRotationVec(1.0F);
			Vec3d traceEnd = eyePos.add(lookDir.x * reachDistance, lookDir.y * reachDistance, lookDir.z * reachDistance);
			BlockHitResult blockHitResult = world.raycast(new RaycastContext(eyePos, traceEnd, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));
			if (blockHitResult.getType() == HitResult.Type.BLOCK) {
				return blockHitResult.getBlockPos();
			}
		}
		return null;
	}

}
