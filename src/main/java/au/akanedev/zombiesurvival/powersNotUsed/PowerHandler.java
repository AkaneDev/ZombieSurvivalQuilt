package au.akanedev.zombiesurvival.powersNotUsed;

import au.akanedev.zombiesurvival.Zombiesurvival;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;


import java.util.Collection;
import java.util.List;

public class PowerHandler {
    public void tick(MinecraftServer server) {
        if (server != null) {
            List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
            for (ServerPlayerEntity player : players) {
                Collection<ScoreboardObjective> powers = player.getScoreboard().getObjectives();
                for (ScoreboardObjective obj : powers) {
                    String objName = obj.getName();
                }
            }
        }
    }
}
