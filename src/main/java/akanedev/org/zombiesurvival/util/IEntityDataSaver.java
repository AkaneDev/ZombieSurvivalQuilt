package akanedev.org.zombiesurvival.util;

import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Unique;

public interface IEntityDataSaver {
    @Unique
    NbtCompound zombieSurvival$getPersistentData();

    NbtCompound getPersistentData();
}
