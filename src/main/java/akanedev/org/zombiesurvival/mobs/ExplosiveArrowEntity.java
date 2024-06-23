package akanedev.org.zombiesurvival.mobs;

import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.*;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class ExplosiveArrowEntity extends ArrowEntity {

	private LivingEntity arm = new LivingEntity(EntityType.PLAYER, getWorld()) {
		@Override
		public Iterable<ItemStack> getArmorItems() {
			return null;
		}

		@Override
		public ItemStack getEquippedStack(EquipmentSlot slot) {
			return null;
		}

		@Override
		public void equipStack(EquipmentSlot slot, ItemStack stack) {

		}

		@Override
		public Arm getMainArm() {
			return null;
		}
	};

	{
		arm.setCustomName(Text.literal("AIRSTRIKE FROM THE DEATH STAR"));
	}

	private final TntEntity Airstrike_tnt = new TntEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), arm);

	private static final int EXPOSED_POTION_DECAY_DURATION = 600;
	private static final int DEFAULT_COLOR = -1;
	private static final byte EFFECT_PARTICLE_SPAWN_INTERVAL = 0;
	private static final TrackedData<Integer> COLOR = null;
	private Potion potion;
	private boolean colorSet;

	public ExplosiveArrowEntity(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public ExplosiveArrowEntity(EntityType<ExplosiveArrowEntity> explosiveArrowEntityEntityType, World world) {
        super(explosiveArrowEntityEntityType, world);
    }


	@Override
	protected ItemStack asItemStack() {
		return null;
	}

	public void tick() {
		super.tick();
		if (this.getWorld().isClient) {
			if (this.inGround) {
				Airstrike_tnt.setFuse(0);
				this.getWorld().spawnEntity(Airstrike_tnt);
				this.kill();
				if (this.inGroundTime % 5 == 0) {
					this.spawnParticles(1);
				}
			} else {
				this.spawnParticles(2);
			}
		}
	}

	private void spawnParticles(int amount) {
		int i = this.getColor();
		if (i != -1 && amount > 0) {
			double d = (double)(i >> 16 & 255) / 255.0;
			double e = (double)(i >> 8 & 255) / 255.0;
			double f = (double)(i >> 0 & 255) / 255.0;

			for(int j = 0; j < amount; ++j) {
				this.getWorld().addParticle(ParticleTypes.ENTITY_EFFECT, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), d, e, f);
			}

		}
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(COLOR, -1);
	}

	public int getColor() {
		return (Integer)this.dataTracker.get(COLOR);
	}

	private void setColor(int color) {
		this.colorSet = true;
		this.dataTracker.set(COLOR, color);
	}

	@Override
	protected void onHit(LivingEntity target) {
		Airstrike_tnt.setFuse(0);
		this.getWorld().spawnEntity(Airstrike_tnt);
		this.kill();
		super.onHit(target);
	}
}
