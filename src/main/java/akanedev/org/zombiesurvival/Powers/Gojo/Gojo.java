package akanedev.org.zombiesurvival.Powers.Gojo;

//import net.akane.zombiesurvival.Powers.Gojo.entities.projectiles.ampflicationblue;
//import net.akane.zombiesurvival.Powers.Gojo.entities.projectiles.hollowpurple;
//import net.akane.zombiesurvival.Powers.Gojo.entities.projectiles.reversalred;
import akanedev.org.zombiesurvival.ZombieSurvival;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.registry.Registry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class Gojo {

//	public static final EntityType<reversalred> REVERSEALRED = EntityType.Builder.create(reversalred::new, SpawnGroup.MISC).build(modID + ":reversal_red");
//	public static final EntityType<ampflicationblue> AMPLFCATIONBLUE = EntityType.Builder.create(ampflicationblue::new, SpawnGroup.MONSTER).build(modID + ":amplification_blue");
//	public static final EntityType<hollowpurple> HOLLOWPURPLE = EntityType.Builder.create(hollowpurple::new, SpawnGroup.MONSTER).build(modID + ":hollow_purple");

	public static void GojoReg(){
		ZombieSurvival.DEBUG_LOGGER.info("THIS IS PAIN WHY NO WORK");
	}

	public static void enableFlight(PlayerEntity player) {
		player.getAbilities().allowFlying = true;
		player.sendAbilitiesUpdate();
	}

	public static void checkAndApplyInfinity(World world, Entity player, double radius) {
		// Define a bounding box around the boss to limit the search area
		Box searchBox = new Box(player.getX() - radius, player.getY() - radius, player.getZ() - radius,
			player.getX() + radius, player.getY() + radius, player.getZ() + radius);
		Box normalizebox = new Box(player.getX() - (radius + radius), player.getY() - (radius + radius), player.getZ() - (radius + radius),
			player.getX() + (radius + radius), player.getY() + (radius + radius), player.getZ() + (radius + radius));
		for (Entity entity : world.getEntitiesByClass(Entity.class, searchBox, e -> true)) {
			if (entity.squaredDistanceTo(player) < radius * radius) {
				if (!entity.equals(player) || !entity.getName().equals(player.getName()) || !(entity == player)) {
					entity.setNoGravity(true);
				}
				if (entity instanceof ProjectileEntity) {
					ProjectileEntity projectileEntity = (ProjectileEntity) entity;
					if (!(projectileEntity.getOwner() == player)) {
						entity.setVelocity(Vec3d.ZERO);
					}
				} else if (!(entity == player)){
					entity.setVelocity(Vec3d.ZERO);
					entity.horizontalSpeed = 0f;
					entity.teleport(entity.getX(), entity.getY(), entity.getZ());
				}
				if (entity == player) {
					entity.setNoGravity(false);
				}
			}
		}
		for (Entity entity : world.getEntitiesByClass(Entity.class, normalizebox, e -> true)) {
			if (entity.squaredDistanceTo(player) > radius * radius) {
				entity.setNoGravity(false);
			}
		}
	}

	public static void disableFlight(PlayerEntity player) {
		player.getAbilities().allowFlying = false;
		player.sendAbilitiesUpdate();
	}

}
