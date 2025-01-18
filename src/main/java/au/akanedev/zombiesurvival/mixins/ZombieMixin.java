package au.akanedev.zombiesurvival.mixins;

import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Zombie.class)
public class ZombieMixin {
    /**
     * @author Akane
     * @reason Remove SunSensitivity
     */
    @Overwrite
    public boolean isSunSensitive() {
        return false;
    }
}
