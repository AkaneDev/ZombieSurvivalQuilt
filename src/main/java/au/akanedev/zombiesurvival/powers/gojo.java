package au.akanedev.zombiesurvival.powers;

import au.akanedev.zombiesurvival.Zombiesurvival;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;
import javax.swing.*;

public class gojo {
    private Vec3 Size;
    public void init(Vec3 Size) {
        Zombiesurvival.LOGGER.info("Gojo Powers Loaded");
        this.Size = Size;
        Zombiesurvival.LOGGER.info("Infinity Size: {}", Size);
    }

    public void tick(Entity user) {
        for (Entity projectile : Objects.requireNonNull(user.getServer()).overworld().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, (LivingEntity) user, AABB
                .ofSize(user.position(), Size.x, Size.y, Size.z))) {
            if (projectile != null) {
                if (projectile instanceof Projectile) {
                    projectile.setDeltaMovement(new Vec3(0f, 0f, 0f));
                } else {
                    projectile.setDeltaMovement(new Vec3(0f, 0.001f, 0f));
                }
            }
        }
    }



}
