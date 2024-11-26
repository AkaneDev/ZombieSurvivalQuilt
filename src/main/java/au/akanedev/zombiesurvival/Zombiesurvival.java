package au.akanedev.zombiesurvival;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Zombiesurvival implements ModInitializer {
    public static final String MODID = "zombiesurvival";
    public static final String MODDEVNAME = "AkaneDev";
    Logger LOGGER = LogManager.getLogger(MODID);
    Logger LogDev = LogManager.getLogger(MODDEVNAME);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello I have been initialized!, 'Im dyslexic' - Noah");
        LogDev.info("If you have this mod and i didnt give it to you, then you dont deserve it");
    }
}
