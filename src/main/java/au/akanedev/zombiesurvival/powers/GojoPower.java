package au.akanedev.zombiesurvival.powers;

import au.akanedev.zombiesurvival.powerTools.Power;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Map;

public class GojoPower implements Power {
    private boolean active = false;
    String name = "Gojo";

    @Override
    public void activate(ServerPlayerEntity player) {
        if (!active) {
            active = true;
            player.sendMessage(Text.literal("Gojo's power activated"));
            // Additional activation effects like granting invincibility or buffs
        }
    }

    @Override
    public void deactivate(ServerPlayerEntity player) {
        if (active) {
            active = false;
            player.sendMessage(Text.literal("Gojo's power deactivated."));
            // Remove effects like invincibility
        }
    }

    @Override
    public void tick(ServerPlayerEntity player) {
        if (active) {
            // Code that runs every tick when the power is active (e.g., effects, cooldowns)
            // Example: Apply effects, regenerate health, etc.
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String getPowerName() {
        return this.name;
    }

    @Override
    public Map<String, Map<String, String>> getEffects() {
        return Map.of();
    }

    @Override
    public void setEffects(Map<String, Map<String, String>> effects) {

    }
}
