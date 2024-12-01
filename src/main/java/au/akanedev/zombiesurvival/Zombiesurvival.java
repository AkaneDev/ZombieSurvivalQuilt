package au.akanedev.zombiesurvival;

import au.akanedev.zombiesurvival.powers.PowerHandler;
import au.akanedev.zombiesurvival.powers.gojo;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Zombiesurvival implements ModInitializer {
    public static final String MODID = "zombiesurvival";
    public static final String MODDEVNAME = "AkaneDev";
    public static Logger LOGGER = LogManager.getLogger(MODID);
    public static Logger LogDev = LogManager.getLogger(MODDEVNAME);
    public static gojo Gojo = new gojo();
    public static PowerHandler powerHandler = new PowerHandler();

    @Override
    public void onInitialize() {
        LOGGER.info("Hello I have been initialized!, 'Im dyslexic' - Noah");
        LogDev.info("If you have this mod and i didnt give it to you, then you dont deserve it");
        Gojo.init(new Vec3(20, 20, 20));
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            powerHandler.tick(server);
        });
    }
}
