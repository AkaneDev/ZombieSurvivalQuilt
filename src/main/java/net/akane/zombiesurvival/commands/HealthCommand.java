package net.akane.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.akane.zombiesurvival.event.PlayerHealthHandler;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class HealthCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("health")
					.requires(source -> source.hasPermissionLevel(2))
					.then(argument("targets", EntityArgumentType.players())
						.then(argument("amount", FloatArgumentType.floatArg())
							.executes(HealthCommand::execute)
						)
					)
        );
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException, CommandSyntaxException {
        Collection<? extends Entity> targets = EntityArgumentType.getEntities(context, "targets");
        double amount = FloatArgumentType.getFloat(context, "amount");
        for (Entity entity : targets) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                EntityAttributeInstance healthatt = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
                if (healthatt != null) {
                    healthatt.setBaseValue(amount);
                    player.setHealth((float) amount + 1000.00f);
                    PlayerHealthHandler.savePlayerHealthData(amount, player.getUuid(), player);
                }
            }
            context.getSource().sendFeedback(() -> Text.literal(String.format("Added %s Health to %s", amount, entity.getDisplayName().getString())), true);
        }
        return targets.size();
    }

}
