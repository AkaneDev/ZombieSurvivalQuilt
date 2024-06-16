package net.akane.zombiesurvival.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GiveSkullCommand {
    public static ItemStack createSkull(Entity entity) {
        ItemStack skull = new ItemStack(Registry.ITEM.get(new Identifier("minecraft", "player_head")));

        NbtCompound tag = skull.getOrCreateNbt();
        NbtCompound skullOwner = new NbtCompound();
        skullOwner.putString("Id", entity.getUuidAsString());
        skullOwner.putString("Name", entity.getDisplayName().getString());

        // For players, add skull texture if available
        if (entity instanceof ServerPlayerEntity) {
            GameProfile profile = ((ServerPlayerEntity) entity).getGameProfile();
            Collection<Property> properties = profile.getProperties().get("textures");
            if (!properties.isEmpty()) {
                Property property = properties.iterator().next();
                skullOwner.put("textures", NbtString.of(property.getValue()));
            }
        }

        tag.put("SkullOwner", skullOwner);
        skull.setNbt(tag);

        return skull;
    }


    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException, CommandSyntaxException {
        Collection<? extends Entity> targets = EntityArgumentType.getEntities(context, "targets");
        for (Entity entity : targets) {
            ItemStack skull = createSkull(entity);
            if (!Objects.requireNonNull(context.getSource().getPlayer()).getInventory().insertStack(skull)) {
                context.getSource().sendError(Text.literal("Unable to give skull to player."));
                return 0;
            }
        }
        context.getSource().sendFeedback(Text.literal("Given skull to entities."), true);
        return targets.size();
    }


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("giveSkull")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("targets", EntityArgumentType.entities())
                        .executes(context -> execute(context)));

        dispatcher.register(literal);
    }

}

