package au.akanedev.zombiesurvival.commands;

import au.akanedev.zombiesurvival.Zombiesurvival;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
//import team.lodestar.lodestone.*;

import net.minecraft.network.chat.Component;

//import static foundry.veil.api.client.util.DebugRenderHelper.renderSphere;

public class basicGlobalCommandHandlers {
    public static int basiccommandHandler(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSystemMessage(Component.literal("BasicCommandHandler has Handled the command"));
        return 1;
    }

    public static int lodeStoneHandler(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSystemMessage(Component.literal("LodeStoneHandler has Handled the command"));

        return 1;
    }
}
