package net.akane.zombiesurvival;

import net.akane.zombiesurvival.Debug.DropAllHeadsAtWorldSpawn;
import net.akane.zombiesurvival.Powers.RBD.start_Random_Block_Drops;
import net.akane.zombiesurvival.commands.*;
import net.akane.zombiesurvival.event.PlayerHealthHandler;
import net.akane.zombiesurvival.event.PlayerTickHandler;
import net.akane.zombiesurvival.event.RespawnListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.GiantEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import net.akane.zombiesurvival.mobs.giant_ai;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZombieSurvival implements ModInitializer {
    // TODO: Powers: C0mm0ns: elytraPower, Rileys: PassThrough Blocks,
    private static final Path CONFIG_DIR = Paths.get("config", "ZombieSurvival");
    private static final Path DATA_FILE = CONFIG_DIR.resolve("ZombieSurvivalServerData.properties");
    public static final String modID = "zombiesurvival";
    public static final String modDev = "Akane";
    public static final Boolean _DEBUG = false;
    public static final Logger LOGGER = LogManager.getLogger();
    public static final EntityType<giant_ai> GIANT_AI_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            modID + ":custom_giant",
            EntityType.Builder.create(giant_ai::new, SpawnGroup.MONSTER).build(modID + ":custom_giant")
    );
    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(GIANT_AI_ENTITY_TYPE, giant_ai.createGiantAttributes());
        PlayerHealthHandler.GenerateFolderAndFiles();
        start_Random_Block_Drops.register();
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, dedicated1) -> {
            PowerCommand.register(dispatcher);
            GiveSkullCommand.register(dispatcher);
            HealthCommand.register(dispatcher);
            RenameCommand.register(dispatcher);
            DebugGive.register(dispatcher);
            CommandSenderCommand.register(dispatcher);
            FalseAdvancementCommand.register(dispatcher);
            makimaislisteningcommand.register(dispatcher);
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            DropAllHeadsAtWorldSpawn.SpawnHeads(server.getOverworld().toServerWorld());
        });

        ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());

        ServerPlayerEvents.AFTER_RESPAWN.register(new RespawnListener());

    }
}
