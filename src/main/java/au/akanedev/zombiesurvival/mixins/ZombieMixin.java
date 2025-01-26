package au.akanedev.zombiesurvival.mixins;

import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ZombieEntity.class)
public class ZombieMixin {
    /**
     * @author Akane
     * @reason Remove SunSensitivity
     */
    @Overwrite
    public boolean burnsInDaylight() {
        return false;
    }
}
