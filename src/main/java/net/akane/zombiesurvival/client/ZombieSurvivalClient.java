package net.akane.zombiesurvival.client;

import io.github.apace100.apoli.ApoliClient;
import net.akane.zombiesurvival.ZombieSurvival;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ZombieSurvivalClient implements ClientModInitializer {
    public static KeyBinding usePrimaryActivePowerKeybind;
    public static KeyBinding useSecondaryActivePowerKeybind;
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new CoolDownHudOverlay());
        usePrimaryActivePowerKeybind = new KeyBinding("key." + ZombieSurvival.modID + ".primary_active", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category." + ZombieSurvival.modID);
        useSecondaryActivePowerKeybind = new KeyBinding("key." + ZombieSurvival.modID + ".secondary_active", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "category." + ZombieSurvival.modID);
        KeyBindingHelper.registerKeyBinding(usePrimaryActivePowerKeybind);
        KeyBindingHelper.registerKeyBinding(useSecondaryActivePowerKeybind);
    }
}
