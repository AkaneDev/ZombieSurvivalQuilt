package net.akane.zombiesurvival;

import net.akane.akanemaths.MathEvaluator;
import net.akane.zombiesurvival.Debug.DropAllHeadsAtWorldSpawn;
import net.akane.zombiesurvival.Powers.RBD.start_Random_Block_Drops;
import net.akane.zombiesurvival.commands.*;
import net.akane.zombiesurvival.event.PlayerHealthHandler;
import net.akane.zombiesurvival.event.PlayerTickHandler;
import net.akane.zombiesurvival.event.RespawnListener;
import net.akane.zombiesurvival.items.OrbitalStrike;
import net.akane.zombiesurvival.mobs.ExplosiveArrowEntity;
import net.akane.zombiesurvival.mobs.giant_ai_moreHP;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.command.v2.FabricEntitySelectorReader;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
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
import net.akane.zombiesurvival.mobs.giant_ai;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZombieSurvival implements ModInitializer {
    // TODO: Powers: Rileys: PassThrough Blocks, Ryan: Gojo
    private static final Path CONFIG_DIR = Paths.get("config", "ZombieSurvival");
    private static final Path DATA_FILE = CONFIG_DIR.resolve("ZombieSurvivalServerData.properties");
    public static final String modID = "zombiesurvival";
    public static final String modDev = "Akane";
    public static final Boolean _DEBUG = false;
    public static final Logger LOGGER = LogManager.getLogger();
	public static String NBT_KEY = modID + ":clipping";
	public Identifier PACKET_ID = new Identifier(modID, "update");
    public static final EntityType<giant_ai> GIANT_AI_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            modID + ":giant_ai",
            EntityType.Builder.create(giant_ai::new, SpawnGroup.MONSTER).build(modID + ":giant_ai")
    );

	private static final Identifier CUSTOM_ARROW_ID = new Identifier(ZombieSurvival.modID, "explode_arrow");



	public static final Item OrbitalStrike = new OrbitalStrike(new Item.Settings());

	public static final EntityType<giant_ai_moreHP> GIANT_AI_HP_ENTITY_TYPE = Registry.register(
		Registries.ENTITY_TYPE,
		modID + ":giant_hp_ai",
		EntityType.Builder.create(giant_ai_moreHP::new, SpawnGroup.MONSTER).build(modID + ":giant_hp_ai")
	);

	@Override
	public void onInitialize(ModContainer mod) {
		MathEvaluator.main();
		FabricDefaultAttributeRegistry.register(GIANT_AI_ENTITY_TYPE, giant_ai.createGiantAttributes());
		FabricDefaultAttributeRegistry.register(GIANT_AI_HP_ENTITY_TYPE, giant_ai_moreHP.createGiantAttributes());
		PlayerHealthHandler.GenerateFolderAndFiles();
		start_Random_Block_Drops.register();
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, dedicated1) -> {
			spheretest.register(dispatcher);
			calc.register(dispatcher);
			GiveSkullCommand.register(dispatcher);
			HealthCommand.register(dispatcher);
			RenameCommand.register(dispatcher);
			sudoCommand.register(dispatcher);
			ScoreBoardTagListCommand.registerServer(dispatcher);
			CommandSenderCommand.register(dispatcher);
			FalseAdvancementCommand.register(dispatcher);
			makimaislisteningcommand.register(dispatcher);
		});

		Registry.register(Registries.ITEM, new Identifier(modID, "orbitalstrike"), OrbitalStrike);

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			DropAllHeadsAtWorldSpawn.SpawnHeads(server.getOverworld().toServerWorld());
		});

		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());

		ServerPlayerEvents.AFTER_RESPAWN.register(new RespawnListener());
	}
}
