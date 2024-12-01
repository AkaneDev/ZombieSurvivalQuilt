package au.akanedev.zombiesurvival.powersNotUsed;

import au.akanedev.zombiesurvival.Zombiesurvival;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

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