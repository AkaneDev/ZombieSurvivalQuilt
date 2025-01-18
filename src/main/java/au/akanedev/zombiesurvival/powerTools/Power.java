package au.akanedev.zombiesurvival.powerTools;

import net.minecraft.server.level.ServerPlayer;

import java.util.Map;

public interface Power {
    void activate(ServerPlayer player);
    void deactivate(ServerPlayer player);
    void tick(ServerPlayer player);
    String name = "NotAssigned";
    boolean isActive();
    String getPowerName();
    // Default method to manage effects dictionary
    Map<String, Map<String, String>> getEffects();
    void setEffects(Map<String, Map<String, String>> effects);
}

