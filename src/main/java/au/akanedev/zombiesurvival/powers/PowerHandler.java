package au.akanedev.zombiesurvival.powers;

import au.akanedev.zombiesurvival.Zombiesurvival;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

import java.util.Collection;
import java.util.List;

public class PowerHandler {
    gojo gojo = Zombiesurvival.Gojo;
    public void tick(MinecraftServer server) {
        if (server != null) {
            List<ServerPlayer> players = server.getPlayerList().getPlayers();
            for (ServerPlayer player : players) {
                Collection<Objective> powers = player.getScoreboard().getObjectives();
                for (Objective obj : powers) {
                    String objName = obj.getName();
                    if (objName.equals("NahIdWin")) {
                        gojo.tick(player);
                    }
                }
            }
        }
    }
}
