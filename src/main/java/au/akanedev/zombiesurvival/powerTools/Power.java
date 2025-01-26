package au.akanedev.zombiesurvival.powerTools;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;

public interface Power {
    void activate(ServerPlayerEntity player);
    void deactivate(ServerPlayerEntity player);
    void tick(ServerPlayerEntity player);
    String name = "NotAssigned";
    boolean isActive();
    String getPowerName();
    // Default method to manage effects dictionary
    Map<String, Map<String, String>> getEffects();
    void setEffects(Map<String, Map<String, String>> effects);
}

