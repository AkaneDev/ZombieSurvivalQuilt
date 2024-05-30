
package net.akane.zombiesurvival.Powers.Gojo;

import net.akane.zombiesurvival.Powers.Gojo.entities.projectiles.ampflicationblue;
import net.akane.zombiesurvival.Powers.Gojo.entities.projectiles.hollowpurple;
import net.akane.zombiesurvival.Powers.Gojo.entities.projectiles.reversalred;
import net.akane.zombiesurvival.mobs.giant_ai;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.akane.zombiesurvival.ZombieSurvival.modID;

public class Gojo {

	public static final EntityType<reversalred> REVERSEALRED = Registry.register(
		Registries.ENTITY_TYPE,
		modID + ":reversal_red",
		EntityType.Builder.create(reversalred::new, SpawnGroup.MISC).build(modID + ":reversal_red")
	);
	public static final EntityType<ampflicationblue> AMPLFCATIONBLUE = Registry.register(
		Registries.ENTITY_TYPE,
		modID + ":amplification_blue",
		EntityType.Builder.create(ampflicationblue::new, SpawnGroup.MONSTER).build(modID + ":amplification_blue")
	);
	public static final EntityType<hollowpurple> HOLLOWPURPLE = Registry.register(
		Registries.ENTITY_TYPE,
		modID + ":hollow_purple",
		EntityType.Builder.create(hollowpurple::new, SpawnGroup.MONSTER).build(modID + ":hollow_purple")
	);

	public static void gojoReg(){
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


}
