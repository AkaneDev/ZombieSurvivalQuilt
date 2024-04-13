package net.akane.zombiesurvival.akanedata;

import java.util.UUID;

public class DataArray {
    private final String Name;
    private final UUID PlayerUUID;
    private final float Amount;
    public DataArray(String name, UUID playerUUID, float amount) {
        this.Name = name;
        this.PlayerUUID = playerUUID;
        this.Amount = amount;
    }

    public DataArray(String name, UUID playerUUID) {
        this.Name = name;
        this.PlayerUUID = playerUUID;
        this.Amount = 0.0f;
    }

    public String getName() {
        return this.Name;
    }

    public UUID getPlayerUUID() {
        return this.PlayerUUID;
    }

    public float getAmount() {
        return this.Amount;
    }

}
