package net.akane.zombiesurvival.Powers.Gojo.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

public class ampflicationblue extends ProjectileEntity {
	public ampflicationblue(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initDataTracker() {

	}

	public static DefaultAttributeContainer.Builder createAmplificationBlueAttributes() {
		return DefaultAttributeContainer.builder()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0) // Adjust as needed
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0) // Adjust as needed
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5); // Adjust as needed
	}
}
