package net.akane.zombiesurvival.util;

import net.akane.akanemaths.SecToTick;
import net.minecraft.nbt.NbtCompound;

import java.util.Objects;

public class PowerData {
    public static String setPower (IEntityDataSaver player, String name) {
        NbtCompound nbt = player.getPersistentData();
        String powerName = nbt.getString("powerName");
        powerName = name;
        nbt.putString("powerName", powerName);
        // sync data
        return powerName;
    }

    public static String removePower (IEntityDataSaver player, String name) {
        NbtCompound nbt = player.getPersistentData();
        String powerName = nbt.getString("powerName");
        powerName = "None";
        nbt.putString("powerName", powerName);
        // sync Data
        return powerName;
    }

    public static int addCooldown (IEntityDataSaver player, int cooldown) {
        NbtCompound nbt = player.getPersistentData();
        int Pcooldown = nbt.getInt("powerCooldown");
        SecToTick cooldownTick = new SecToTick(cooldown);
        SecToTick MaxCoolDownTick = new SecToTick(30);
        if (cooldownTick.GetTicks() >= MaxCoolDownTick.GetTicks()){
            Pcooldown = MaxCoolDownTick.GetTicks();
        }
        else {
            Pcooldown = cooldownTick.GetTicks();
        }
        nbt.putInt("powerCooldown", Pcooldown);
        // sync Data
        return Pcooldown;
    }

    public static String GetPower (IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getString("powerName");
    }

    public static int TickCooldown (IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        int Pcooldown = nbt.getInt("powerCooldown");
        if (Pcooldown -1 <= 0) {
            Pcooldown = 0;
        }
        else {
            Pcooldown = Pcooldown -1;
        }
        nbt.putInt("powerCooldown", Pcooldown);
        //sync
        return Pcooldown;
    }

}
