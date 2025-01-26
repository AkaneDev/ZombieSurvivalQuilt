package au.akanedev.zombiesurvival.Spawning;


import au.akanedev.zombiesurvival.Zombiesurvival;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;


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
                    List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
                    for (ServerPlayerEntity player : players) {
                        World world = player.getServerWorld();
                        ZombieEntity zombie = new ZombieEntity(world);
                        zombie.setPos(player.getX(), player.getY(), player.getZ());
                        world.spawnEntity(zombie);
                        if (player.getName().getString().contains("AkaneDev") || FabricLoader.getInstance().isDevelopmentEnvironment()) {
                            Zombiesurvival.LOGGER.info("Spawning");
                            player.sendMessage(Text.literal("Spawning"));
                            player.sendMessage(Text.literal("Time Between Spawns in ticks: " + maxtimeTick * 60 + ", in Seconds: " + maxtimeTick));
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
