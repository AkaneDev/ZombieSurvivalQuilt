package au.akanedev.zombiesurvival.powerTools;

import au.akanedev.zombiesurvival.Zombiesurvival;
import au.akanedev.zombiesurvival.powers.GojoPower;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;

public class PowerHandler {
    private final PowerManager powerManager = new PowerManager();

    // Register powers for a player
    public void playerJoined(ServerPlayer player) {
        powerManager.registerPower(player, "Gojo", new GojoPower());
        // Register other powers here as necessary
    }

    // Activate a power for a player
    public void activatePower(ServerPlayer player, String powerName) {
        powerManager.activatePower(player, powerName);
    }

    // Deactivate a power for a player
    public void deactivatePower(ServerPlayer player, String powerName) {
        powerManager.deactivatePower(player, powerName);
    }

    // Tick powers for all players
    public void tick(MinecraftServer server) {
        powerManager.tick(server);
    }

    // Remove powers when a player leaves
    public void playerLeft(ServerPlayer player) {
        powerManager.removePlayerPowers(player);
    }
}

