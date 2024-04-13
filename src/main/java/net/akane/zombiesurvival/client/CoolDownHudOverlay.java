package net.akane.zombiesurvival.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;

public class CoolDownHudOverlay implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext drawContext, float v) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = client.getWindow().getWidth();
            int height = client.getWindow().getHeight();

            x = width / 2;
            y = height;
            drawContext.drawText(client.textRenderer, "Test", x, y, 0, true);
        }
    }
}
