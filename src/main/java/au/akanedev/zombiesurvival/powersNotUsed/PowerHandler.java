package au.akanedev.zombiesurvival.powersNotUsed;

import au.akanedev.zombiesurvival.Zombiesurvival;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Objective;

import java.util.Collection;
import java.util.List;

public class PowerHandler {
    public void tick(MinecraftServer server) {
        if (server != null) {
            List<ServerPlayer> players = server.getPlayerList().getPlayers();
            for (ServerPlayer player : players) {
                Collection<Objective> powers = player.getScoreboard().getObjectives();
                for (Objective obj : powers) {
                    String objName = obj.getName();
                }
            }
        }
    }
}
