package au.akanedev.zombiesurvival.commands;

import au.akanedev.zombiesurvival.Zombiesurvival;
import com.mojang.brigadier.context.CommandContext;
//import team.lodestar.lodestone.*;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

//import static foundry.veil.api.client.util.DebugRenderHelper.renderSphere;

public class basicGlobalCommandHandlers {
    public static int basiccommandHandler(CommandContext<ServerCommandSource> context) {
        context.getSource().sendMessage(Text.literal("BasicCommandHandler has Handled the command"));
        return 1;
    }

    public static int lodeStoneHandler(CommandContext<ServerCommandSource> context) {
        context.getSource().sendMessage(Text.literal("LodeStoneHandler has Handled the command"));

        return 1;
    }
}
