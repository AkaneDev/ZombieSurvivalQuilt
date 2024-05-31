package net.akane.zombiesurvival.Powers.Gojo;

import net.akane.zombiesurvival.Powers.Gojo.entities.projectiles.ampflicationblue;
import net.akane.zombiesurvival.Powers.Gojo.entities.projectiles.hollowpurple;
import net.akane.zombiesurvival.Powers.Gojo.entities.projectiles.reversalred;
import net.akane.zombiesurvival.ZombieSurvival;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import static net.akane.zombiesurvival.ZombieSurvival.modID;

public class Gojo {

	public static final EntityType<reversalred> REVERSEALRED = EntityType.Builder.create(reversalred::new, SpawnGroup.MISC).build(modID + ":reversal_red");
	public static final EntityType<ampflicationblue> AMPLFCATIONBLUE = EntityType.Builder.create(ampflicationblue::new, SpawnGroup.MONSTER).build(modID + ":amplification_blue");
	public static final EntityType<hollowpurple> HOLLOWPURPLE = EntityType.Builder.create(hollowpurple::new, SpawnGroup.MONSTER).build(modID + ":hollow_purple");

	public static void GojoReg(){
		ZombieSurvival.DEBUG_LOGGER.info("THIS IS PAIN WHY NO WORK");
		registerattacksprojectiles();
	}

	public static void enableFlight(PlayerEntity player) {
		player.getAbilities().allowFlying = true;
		player.sendAbilitiesUpdate();
	}

	public static void disableFlight(PlayerEntity player) {
		player.getAbilities().allowFlying = false;
		player.sendAbilitiesUpdate();
	}

	public static void registerattacksprojectiles() {
		Registry.register(Registries.ENTITY_TYPE, new Identifier(modID, "reversal_red"), REVERSEALRED);
		Registry.register(Registries.ENTITY_TYPE, new Identifier(modID, "amplification_blue"), AMPLFCATIONBLUE);
		Registry.register(Registries.ENTITY_TYPE, new Identifier(modID, "hollow_purple"), HOLLOWPURPLE);
	}

	@ClientOnly
	public static void GojoClient() {
		EntityRendererRegistry.register(Gojo.AMPLFCATIONBLUE, (context) ->
			new EntityRenderer<ampflicationblue>(context) {
				@Override
				public Identifier getTexture(ampflicationblue entity) {
					return new Identifier(modID, "textures/entity/amplification_blue.png");
				}
			});
		EntityRendererRegistry.register(Gojo.REVERSEALRED, (context) ->
			new EntityRenderer<reversalred>(context) {
				@Override
				public Identifier getTexture(reversalred entity) {
					return new Identifier(modID, "textures/entity/reversal_red.png");
				}
			});
		EntityRendererRegistry.register(Gojo.HOLLOWPURPLE, (context) ->
			new EntityRenderer<hollowpurple>(context) {
				@Override
				public Identifier getTexture(hollowpurple entity) {
					return new Identifier(modID, "textures/entity/hollow_purple.png");
				}
			});
	}

}
