package au.akanedev.zombiesurvival.commands;

import au.akanedev.zombiesurvival.Zombiesurvival;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import team.lodestar.lodestone.*;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class PowerCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(LiteralArgumentBuilder.<ServerCommandSource>literal("Activate")
                    .executes(basicGlobalCommandHandlers::basiccommandHandler)
            );
            dispatcher.register(LiteralArgumentBuilder.<ServerCommandSource>literal("lodestoneTest")
            .executes(basicGlobalCommandHandlers::lodeStoneHandler));

            dispatcher.register(LiteralArgumentBuilder.<ServerCommandSource>literal("ZombieSurvival")
                    .then(commandEnable.registerEnable())
                    .then(commandEnable.registerDisable())
                    .then(commandEnable.registerSetSpawnTime())
                    .then(commandEnable.registerSetSpawnTimeTicks())
                    .then(commandEnable.registerOrbitalStrike())
                    .then(commandEnable.registerRaycastOrbitalStrike())
            );

//            dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("SettimeOfZombieSurvival")
//                .requires(source -> source.hasPermission(2))
//                .then(Commands.argument("Amountoftime", FloatArgumentType.floatArg())
//                    .executes(ctx -> {
////                        ctx.getSource().sendSystemMessage(Component.literal("Attempting to get int"));
//                        float amount = FloatArgumentType.getFloat(ctx, "Amountoftime");
//                        CommandSourceStack source = (CommandSourceStack)ctx.getSource();
//                        MinecraftServer server = source.getServer();
//                        source.sendSuccess(() -> {
//                            Zombiesurvival.spawnHelper.maxtimeTick = amount;
//                            Zombiesurvival.spawnHelper.current = amount * 60;
//                            Zombiesurvival.SaveConfig();
//                            return Component.literal("Time Set to: " + amount + " ticks");
//                        }, true);
//                        return 1;
//                    }))
//            );
        });
    }
}
