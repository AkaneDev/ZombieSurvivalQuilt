package akanedev.org.zombiesurvival.mobs;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.GiantEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.UUID;
import java.util.function.Predicate;

public class giant_ai extends GiantEntity {

    private static final UUID BABY_SPEED_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    public static final float field_30519 = 0.05F;
    public static final int field_30515 = 50;
    public static final int field_30516 = 40;
    public static final int field_30517 = 7;
    protected static final float field_41028 = 0.81F;
    private static final float field_30518 = 0.1F;
    private boolean canBreakDoors;
    private int inWaterTime;
    private int ticksUntilWaterConversion;

    public giant_ai(EntityType<? extends GiantEntity> entityType, World world) {
        super(EntityType.GIANT, world);
    }
    protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 80.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[]{ZombifiedPiglinEntity.class}));
        this.targetSelector.add(2, new TargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new TargetGoal(this, MerchantEntity.class, false));
        this.targetSelector.add(3, new TargetGoal(this, IronGolemEntity.class, true));
        this.targetSelector.add(5, new TargetGoal(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
    }
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 10.440001F;
    }

    public static DefaultAttributeContainer.Builder createGiantAttributes() {
        double health = 150.0;
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, health).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.005).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 350.0);
    }

	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return world.getPathfindingCostFromLight(pos);
	}
}
