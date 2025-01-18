package au.akanedev.zombiesurvival.powerTools;

import au.akanedev.zombiesurvival.Zombiesurvival;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PowerManager {
    private final Map<UUID, Map<String, Power>> playerPowers = new HashMap<>();

    // Register a power for a player
    public void registerPower(ServerPlayer player, String powerName, Power power) {
        Zombiesurvival.LOGGER.info("Registered A Player called: {}", player.getName().getString());
        Zombiesurvival.LOGGER.info("Player Got Power Called called: {}", powerName);
        Zombiesurvival.LOGGER.info("Internal Power Name: {}", power.getPowerName());
        UUID playerId = player.getUUID();
        playerPowers.putIfAbsent(playerId, new HashMap<>());
        playerPowers.get(playerId).put(powerName, power);
    }

    // Activate a specific power for a player
    public void activatePower(ServerPlayer player, String powerName) {
        Map<String, Power> powers = playerPowers.get(player.getUUID());
        if (powers != null) {
            Power power = powers.get(powerName);
            if (power != null && !power.isActive()) {
                power.activate(player);
            }
        }
    }

    // Deactivate a specific power for a player
    public void deactivatePower(ServerPlayer player, String powerName) {
        Map<String, Power> powers = playerPowers.get(player.getUUID());
        if (powers != null) {
            Power power = powers.get(powerName);
            if (power != null && power.isActive()) {
                power.deactivate(player);
            }
        }
    }

    // Tick powers for all players
    public void tick(MinecraftServer server) {
        if (server != null) {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                Map<String, Power> powers = playerPowers.get(player.getUUID());
                if (powers != null) {
                    for (Power power : powers.values()) {
                        power.tick(player); // Tick all active powers for this player
                    }
                }
            }
        }
    }

    // Get all powers for a player
    public Map<String, Power> getPlayerPowers(ServerPlayer player) {
        return playerPowers.getOrDefault(player.getUUID(), new HashMap<>());
    }

    // Remove all powers for a player when they leave
    public void removePlayerPowers(ServerPlayer player) {
        playerPowers.remove(player.getUUID());
    }
}
