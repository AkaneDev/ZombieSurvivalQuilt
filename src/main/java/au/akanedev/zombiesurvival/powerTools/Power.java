package au.akanedev.zombiesurvival.powerTools;

import net.minecraft.server.level.ServerPlayer;

public interface Power {
    void activate(ServerPlayer player);
    void deactivate(ServerPlayer player);
    void tick(ServerPlayer player);
    boolean isActive();
}

