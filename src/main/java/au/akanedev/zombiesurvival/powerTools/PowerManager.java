package au.akanedev.zombiesurvival.powerTools;

import au.akanedev.zombiesurvival.Zombiesurvival;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PowerManager {
    private final Map<UUID, Map<String, Power>> playerPowers = new HashMap<>();

    // Register a power for a player
    public void registerPower(ServerPlayerEntity player, String powerName, Power power) {
        Zombiesurvival.LOGGER.info("Registered A Player called: {}", player.getName().getString());
        Zombiesurvival.LOGGER.info("Player Got Power Called called: {}", powerName);
        Zombiesurvival.LOGGER.info("Internal Power Name: {}", power.getPowerName());
        UUID playerId = player.getUuid();
        playerPowers.putIfAbsent(playerId, new HashMap<>());
        playerPowers.get(playerId).put(powerName, power);
    }

    // Activate a specific power for a player
    public void activatePower(ServerPlayerEntity player, String powerName) {
        Map<String, Power> powers = playerPowers.get(player.getUuid());
        if (powers != null) {
            Power power = powers.get(powerName);
            if (power != null && !power.isActive()) {
                power.activate(player);
            }
        }
    }

    // Deactivate a specific power for a player
    public void deactivatePower(ServerPlayerEntity player, String powerName) {
        Map<String, Power> powers = playerPowers.get(player.getUuid());
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
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                Map<String, Power> powers = playerPowers.get(player.getUuid());
                if (powers != null) {
                    for (Power power : powers.values()) {
                        power.tick(player); // Tick all active powers for this player
                    }
                }
            }
        }
    }

    // Get all powers for a player
    public Map<String, Power> getPlayerPowers(ServerPlayerEntity player) {
        return playerPowers.getOrDefault(player.getUuid(), new HashMap<>());
    }

    // Remove all powers for a player when they leave
    public void removePlayerPowers(ServerPlayerEntity player) {
        playerPowers.remove(player.getUuid());
    }
}
