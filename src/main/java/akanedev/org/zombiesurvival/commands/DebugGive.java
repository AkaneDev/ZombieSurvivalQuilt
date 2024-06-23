package akanedev.org.zombiesurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
//import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.command.GiveCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.*;

public class DebugGive {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("debugGive").then(argument("itemName", StringArgumentType.string()))
					.requires(source -> source.hasPermissionLevel(2))
                    .executes(DebugGive::executer)
        );
    }

    private static int executer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        Identifier itemID = new Identifier(StringArgumentType.getString(context, "power"));
        Item item = Registries.ITEM.get(itemID);
        ItemStack itemStack;
        // Check if the item exists
        if (item != null) {
            // Create an ItemStack with quantity 1
            itemStack = new ItemStack(item);
        } else {
            // Item not found
            // or throw an exception, depending on your needs
            context.getSource().sendError(Text.of("PUT A ITEM IN"));
            return 0;
        }
        assert player != null;
        player.giveItemStack(itemStack);
        return 1;
    }
}
