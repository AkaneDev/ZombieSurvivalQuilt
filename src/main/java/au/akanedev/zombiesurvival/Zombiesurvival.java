package au.akanedev.zombiesurvival;

import au.akanedev.zombiesurvival.powerTools.PowerHandler;
import foundry.veil.Veil;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Zombiesurvival implements ModInitializer {
    public static final String MODID = "zombiesurvival";
    public static final String MODDEVNAME = "AkaneDev";
    public static Logger LOGGER = LogManager.getLogger(MODID);
    public static Logger LogDev = LogManager.getLogger(MODDEVNAME);
    PowerHandler powerHandler = new PowerHandler();

    @Override
    public void onInitialize() {
        LOGGER.info("Hello I have been initialized!, 'Im dyslexic' - Noah");
        LogDev.info("If you have this mod and i didnt give it to you, then you dont deserve it");

    }
}
