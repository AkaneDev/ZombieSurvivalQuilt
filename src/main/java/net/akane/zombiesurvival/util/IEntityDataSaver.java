package net.akane.zombiesurvival.util;

import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Unique;

public interface IEntityDataSaver {
    NbtCompound getPersistantData();

    @Unique
    NbtCompound zombieSurvival$getPersistentData();

    NbtCompound getPersistentData();
}
