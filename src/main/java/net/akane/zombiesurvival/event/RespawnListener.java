package net.akane.zombiesurvival.event;

import net.akane.zombiesurvival.ZombieSurvival;
import net.akane.akanedata.DataArray;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

public class RespawnListener implements ServerPlayerEvents.AfterRespawn{
    @Override
    public void afterRespawn(ServerPlayerEntity serverPlayerEntity, ServerPlayerEntity serverPlayerEntity1, boolean b) {
        if (serverPlayerEntity != null) {
            DataArray playerData = PlayerHealthHandler.readPlayerHealthData(serverPlayerEntity);
            assert playerData != null;
            ZombieSurvival.LOGGER.info(playerData.getName() + ", " + playerData.getPlayerUUID().toString() + ", " + playerData.getAmount());
            double healthamount = playerData.getAmount();
            EntityAttributeInstance healthatt = serverPlayerEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (healthatt != null) {
                ZombieSurvival.LOGGER.info(healthatt.getBaseValue());
                healthatt.setBaseValue(healthamount);
                serverPlayerEntity.setHealth((float) (healthamount * 100.0f));
            }
        }
    }
}
