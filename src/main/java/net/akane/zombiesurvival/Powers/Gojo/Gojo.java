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
import net.minecraft.util.registry.Registry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import static net.akane.zombiesurvival.ZombieSurvival.modID;

public class Gojo {

	public static final EntityType<reversalred> REVERSEALRED = EntityType.Builder.create(reversalred::new, SpawnGroup.MISC).build(modID + ":reversal_red");
	public static final EntityType<ampflicationblue> AMPLFCATIONBLUE = EntityType.Builder.create(ampflicationblue::new, SpawnGroup.MONSTER).build(modID + ":amplification_blue");
	public static final EntityType<hollowpurple> HOLLOWPURPLE = EntityType.Builder.create(hollowpurple::new, SpawnGroup.MONSTER).build(modID + ":hollow_purple");

	public static void GojoReg(){
		ZombieSurvival.DEBUG_LOGGER.info("THIS IS PAIN WHY NO WORK");
	}

	public static void enableFlight(PlayerEntity player) {
		player.getAbilities().allowFlying = true;
		player.sendAbilitiesUpdate();
	}

	public static void disableFlight(PlayerEntity player) {
		player.getAbilities().allowFlying = false;
		player.sendAbilitiesUpdate();
	}

}
