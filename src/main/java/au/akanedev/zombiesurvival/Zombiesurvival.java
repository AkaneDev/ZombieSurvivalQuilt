package au.akanedev.zombiesurvival;

import au.akanedev.zombiesurvival.Spawning.spawning;
import au.akanedev.zombiesurvival.commands.PowerCommands;
import au.akanedev.zombiesurvival.powerTools.Power;
import au.akanedev.zombiesurvival.powerTools.PowerHandler;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Zombiesurvival implements ModInitializer {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("zombieConfig.json");
    public static final String MODID = "zombiesurvival";
    public static final String MODDEVNAME = "AkaneDev";
    public static Logger LOGGER = LogManager.getLogger(MODID);
    public static Logger LogDev = LogManager.getLogger(MODDEVNAME);
    PowerHandler powerHandler = new PowerHandler();
    public static spawning spawnHelper = new spawning();

    @Override
    public void onInitialize() {
        if (!Files.exists(CONFIG_PATH)) {
            SaveConfig();
        }
        LoadConfig();
        LOGGER.info("Hello I have been initialized!,\n'Im dyslexic' - Noah");
        LogDev.info("If you have this mod and i didnt give it to you, then you dont deserve it");
        eventJoin();
        PowerCommands.registerCommands();
        registerEvents();
    }

    public static void LoadConfig() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String jsonContent = Files.readString(CONFIG_PATH);
                JsonObject jsonObject = com.google.gson.JsonParser.parseString(jsonContent).getAsJsonObject();
                boolean isEnabled = jsonObject.get("isEnabled").getAsBoolean();
                int SpawnTime = jsonObject.get("SpawnTime").getAsInt();
                spawnHelper.enabled = isEnabled;
                spawnHelper.maxtimeTick = SpawnTime;
                // Use the loaded values
//                LOGGER.info("Config loaded: isEnabled = " + isEnabled);
            } catch (Exception e) {
                LOGGER.error("Failed to load config", e);
            }
        }
    }

    public static void SaveConfig() {
        JsonObject config = new JsonObject();
        config.addProperty("isEnabled", spawnHelper.enabled); // Keep this static for now
        config.addProperty("SpawnTime", spawnHelper.maxtimeTick);

        // Save it to file
        try {
            Files.writeString(CONFIG_PATH, config.toString());
        } catch (IOException e) {
            LOGGER.error("Failed to write config", e);
        }
    }

    public void registerEvents() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tick(server);
        });
    }

    public void eventJoin() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayer player = handler.getPlayer();
            if (FabricLoader.getInstance().isDevelopmentEnvironment()){
                player.sendSystemMessage(Component.literal("Config Details: " + spawnHelper.enabled + ", MaxTimeInTicks: " + spawnHelper.maxtimeTick * 60 + ", MaxtimeSeconds: " + spawnHelper.maxtimeTick));
            }
            // Send a message to the player
//            player.sendSystemMessage(Component.literal("Welcome to the server, " + player.getName().getString() + "!"), false);
//            powerHandler.playerJoined(player);
            // Log the join event
            System.out.println(player.getName().getString() + " has joined the game.");
        });
    }

    public void tick(MinecraftServer server) {
        spawnHelper.tick(server);
    }
}
