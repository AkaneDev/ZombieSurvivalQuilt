package au.akanedev.zombiesurvival.Spawning;


import au.akanedev.zombiesurvival.Zombiesurvival;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

public class spawning {
    public float maxtimeTick = 30;
    public float current = 0;
    public boolean enabled = false;

    static Random rand = new Random();

    public void tick(MinecraftServer server) {
        if (enabled) {
            if (current <= 0) {
                int number = rand.nextInt(11);
                if (number == 1) {
                    List<ServerPlayer> players = server.getPlayerList().getPlayers();
                    for (ServerPlayer player : players) {
                        Level world = player.getCommandSenderWorld();
                        Zombie zombie = new Zombie(world);
                        zombie.setPos(player.getX(), player.getY(), player.getZ());
                        world.addFreshEntity(zombie);
                        if (player.getName().getString().contains("AkaneDev") || FabricLoader.getInstance().isDevelopmentEnvironment()) {
                            Zombiesurvival.LOGGER.info("Spawning");
                            player.sendSystemMessage(Component.literal("Spawning"));
                            player.sendSystemMessage(Component.literal("Time Between Spawns in ticks: " + maxtimeTick * 60 + ", in Seconds: " + maxtimeTick));
                            Zombiesurvival.LOGGER.info("Time Between Spawns in ticks: " + maxtimeTick * 60 + ", in Seconds: " + maxtimeTick);
                        }
                    }
                }
                current = maxtimeTick * 60;
            } else {
                current--;
            }
        }
    }
}
