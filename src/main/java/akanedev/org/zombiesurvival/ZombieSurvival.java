package akanedev.org.zombiesurvival;

import net.akane.akanemaths.MathEvaluator;
import net.akane.zombiesurvival.Debug.DropAllHeadsAtWorldSpawn;
import net.akane.zombiesurvival.Powers.Gojo.Gojo;
import net.akane.zombiesurvival.Powers.RBD.start_Random_Block_Drops;
import akanedev.org.zombiesurvival.commands.*;
import net.akane.zombiesurvival.event.PlayerHealthHandler;
import net.akane.zombiesurvival.event.PlayerTickHandler;
import net.akane.zombiesurvival.event.RespawnListener;
import net.akane.zombiesurvival.items.OrbitalStrike;
import net.akane.zombiesurvival.mobs.giant_ai_moreHP;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.mozilla.javascript.engine.RhinoScriptEngineFactory;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registry;
import net.akane.zombiesurvival.mobs.giant_ai;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ZombieSurvival implements ModInitializer {
    // TODO: Powers: Rileys: PassThrough Blocks, Ryan: Gojo
    private static final Path CONFIG_DIR = Paths.get("config", "ZombieSurvival");
    private static final Path DATA_FILE = CONFIG_DIR.resolve("ZombieSurvivalServerData.properties");
    public static final String modID = "zombiesurvival";
    public static final String modDev = "Akane";
    public static Boolean _DEBUG = false;
    public static final Logger LOGGER = LogManager.getLogger(modID);
	public static final Logger DEBUG_LOGGER = LogManager.getLogger("AkaneDev");
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
		if (QuiltLoader.isDevelopmentEnvironment()) {
			_DEBUG = true;
		}
		LOGGER.info("---JS Engines---");
		ScriptEngineManager manager = new ScriptEngineManager();
		List<ScriptEngineFactory> engineFactories = manager.getEngineFactories();
		if (engineFactories.isEmpty()) {
			LOGGER.info("No JavaScript engines detected.");
		} else {
			for (ScriptEngineFactory factory : engineFactories) {
				LOGGER.info("Engine Name: {}", factory.getEngineName());
				LOGGER.info("Engine Version: {}", factory.getEngineVersion());
				LOGGER.info("Language Name: {}", factory.getLanguageName());
				LOGGER.info("Language Version: {}", factory.getLanguageVersion());
				LOGGER.info("Supported Extensions: {}", factory.getExtensions());
				LOGGER.info("Supported Mime Types: {}", factory.getMimeTypes());
				LOGGER.info("-----");
			}
		}
		LOGGER.info("---Rhino JS Engines---");
		RhinoScriptEngineFactory factory = new RhinoScriptEngineFactory();
		LOGGER.info("Engine Name: {}", factory.getEngineName());
		LOGGER.info("Engine Version: {}", factory.getEngineVersion());
		LOGGER.info("Language Name: {}", factory.getLanguageName());
		LOGGER.info("Language Version: {}", factory.getLanguageVersion());
		LOGGER.info("---End Rhino JS Engines---");
		LOGGER.info("---End JS Engines---");
		MathEvaluator.main(new String[0]);
		FabricDefaultAttributeRegistry.register(GIANT_AI_ENTITY_TYPE, giant_ai.createGiantAttributes());
		FabricDefaultAttributeRegistry.register(GIANT_AI_HP_ENTITY_TYPE, giant_ai_moreHP.createGiantAttributes());
		PlayerHealthHandler.GenerateFolderAndFiles();
		start_Random_Block_Drops.register();
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, dedicated1) -> {
			net.akane.zombiesurvival.commands.spheretest.register(dispatcher);
			net.akane.zombiesurvival.commands.calc.register(dispatcher);
			net.akane.zombiesurvival.commands.GiveSkullCommand.register(dispatcher);
			net.akane.zombiesurvival.commands.HealthCommand.register(dispatcher);
			net.akane.zombiesurvival.commands.RenameCommand.register(dispatcher);
			net.akane.zombiesurvival.commands.sudoCommand.register(dispatcher);
			net.akane.zombiesurvival.commands.ScoreBoardTagListCommand.registerServer(dispatcher);
			net.akane.zombiesurvival.commands.CommandSenderCommand.register(dispatcher);
			net.akane.zombiesurvival.commands.FalseAdvancementCommand.register(dispatcher);
			net.akane.zombiesurvival.commands.makimaislisteningcommand.register(dispatcher);
			net.akane.zombiesurvival.commands.NullIfy.register(dispatcher);
		});

		Registry.register(Registries.ITEM, new Identifier(modID, "orbitalstrike"), OrbitalStrike);
		Gojo.GojoReg();
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			DropAllHeadsAtWorldSpawn.SpawnHeads(server.getOverworld().toServerWorld());
		});

		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());

		ServerPlayerEvents.AFTER_RESPAWN.register(new RespawnListener());
	}
}
