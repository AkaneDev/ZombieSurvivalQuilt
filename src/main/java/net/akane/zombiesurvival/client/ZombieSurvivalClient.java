package net.akane.zombiesurvival.client;

import com.mojang.blaze3d.platform.InputUtil;
//import io.github.apace100.apoli.ApoliClient;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.akane.zombiesurvival.Powers.GoThroughWalls.Ghost;
import net.akane.zombiesurvival.ZombieSurvival;
import net.akane.zombiesurvival.client.commands.ScoreBoardClientCommand;
import net.akane.zombiesurvival.commands.ScoreBoardTagListCommand;
import net.akane.zombiesurvival.networking.C2S.C2Ssetgamemode;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBind;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.item.ElytraItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.command.api.client.ClientCommandManager;
import org.quiltmc.qsl.command.api.client.ClientCommandRegistrationCallback;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import net.akane.akanemaths.SecToTick;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class ZombieSurvivalClient implements ClientModInitializer {
    public static KeyBind usePrimaryActivePowerKeybind;
    public static KeyBind useSecondaryActivePowerKeybind;
	public static KeyBind useDashKeybind;
	private static final Identifier PACKET_ID = new Identifier(ZombieSurvival.modID, "change_gamemode");
	public SecToTick dashCooldownSeconds = new SecToTick(5);
	public int dashCooldown = dashCooldownSeconds.GetTicks();
    @Override
    public void onInitializeClient(ModContainer container) {
        HudRenderCallback.EVENT.register(new CoolDownHudOverlay());
        usePrimaryActivePowerKeybind = new KeyBind("key." + ZombieSurvival.modID + ".primary_active", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "category." + ZombieSurvival.modID);
        useSecondaryActivePowerKeybind = new KeyBind("key." + ZombieSurvival.modID + ".secondary_active", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "category." + ZombieSurvival.modID);
		useDashKeybind = new KeyBind("key." + ZombieSurvival.modID + ".dash", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category." + ZombieSurvival.modID);
        KeyBindingHelper.registerKeyBinding(usePrimaryActivePowerKeybind);
        KeyBindingHelper.registerKeyBinding(useSecondaryActivePowerKeybind);
		KeyBindingHelper.registerKeyBinding(useDashKeybind);
		ClientTickEvents.END.register(client -> {
			ClientPlayerEntity player = client.player;
			if (player != null) {
				if (useDashKeybind.wasPressed() && ((dashCooldown < 0) || player.getScoreboardTags().contains("OP"))) {
					player.addVelocity(player.getRotationVecClient().multiply(2));
					dashCooldown = dashCooldownSeconds.GetTicks();
				}
				if (usePrimaryActivePowerKeybind.wasPressed()) {
					Ghost.toggleghostMode(player);
				}
			}

			if (!(dashCooldown < 0)) {
				dashCooldown -= 1;
			}
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, buildContext, environment) -> {
			dispatcher.register(ClientCommandManager.literal("taglistClient").executes(ScoreBoardClientCommand::execute)
			);
		});

    }
}
