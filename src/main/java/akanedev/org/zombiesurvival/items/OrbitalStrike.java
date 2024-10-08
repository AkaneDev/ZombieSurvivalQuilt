package akanedev.org.zombiesurvival.items;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class OrbitalStrike extends Item {
	public OrbitalStrike(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		double maxReach = 1000; //The farthest target the cameraEntity can detect
		float tickDelta = 1.0F; //Used for tracking animation progress; no tracking is 1.0F
		boolean includeFluids = true; //Whether to detect fluids as blocks

		HitResult hit = user.raycast(maxReach, tickDelta, includeFluids);

		float[] redColor = new float[]{1.0f, 0.0f, 0.0f, 1.0f};
		int interger = 320;
		for (int i = 319+64; i >= 0; i--) {
			interger--;
			for (int i2 = 0; i2 < 1; i2++) {
				TntEntity Airstrike_tnt = new TntEntity(world, hit.getPos().x, interger, hit.getPos().z, new PlayerEntity(world, new BlockPos((int) hit.getPos().x, (int) hit.getPos().y, (int) hit.getPos().z), 0, new GameProfile(UUID.randomUUID(), "Airstrike")) {
					@Override
					public boolean isSpectator() {
						return false;
					}

					@Override
					public boolean isCreative() {
						return false;
					}
				});
				Airstrike_tnt.setNoGravity(true);
				Airstrike_tnt.setFuse(0);
				// DO SOMETHING HERE
//				world.spawnEntity(Airstrike_tnt);
				world.createExplosion(new PlayerEntity(world, new BlockPos((int) hit.getPos().x, (int) hit.getPos().y, (int) hit.getPos().z), 0, new GameProfile(UUID.randomUUID(), "Airstrike")) {
					@Override
					public boolean isSpectator() {
						return false;
					}

					@Override
					public boolean isCreative() {
						return false;
					}
				}, hit.getPos().x, interger, hit.getPos().z, 10, World.ExplosionSourceType.MOB);
			}
		}
		if (hit.getType() == HitResult.Type.BLOCK) {
			BlockPos hitPos = ((BlockHitResult) hit).getBlockPos();
			// Get all entities within a radius of the hit position
			List<Entity> entities = world.getOtherEntities(user, new Box(hitPos).expand(100.0), entity -> true);
			for (Entity entity : entities) {
				// Do something with each entity, such as applying damage or effects
				if (entity instanceof LivingEntity) {
					// For example, apply damage to living entities within the radius
					entity.teleport(hit.getPos().x, hit.getPos().y, hit.getPos().z);
				}
			}
		}
		return super.use(world, user, hand);
	}

	private boolean isPlayerNearCoordinates(PlayerEntity player, BlockPos targetPos, double distance) {
		Vec3d playerPos = player.getPos();
		double distanceSquared = playerPos.squaredDistanceTo(Vec3d.ofCenter(targetPos));

		// Compare squared distances to avoid expensive square root calculations
		return distanceSquared <= distance * distance;
	}

}
