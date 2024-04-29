package net.akane.zombiesurvival.event;

import com.mojang.authlib.GameProfile;
import net.akane.zombiesurvival.Powers.GoThroughWalls.Ghost;
import net.akane.zombiesurvival.ZombieSurvival;
import net.akane.akanedata.DataArray;
import net.akane.akanemaths.SecToTick;
import net.akane.zombiesurvival.everyoneAllatonce.GetAllPlayerLocation;
import net.akane.zombiesurvival.util.IEntityDataSaver;
import net.akane.zombiesurvival.util.PowerData;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.message.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class PlayerTickHandler implements ServerTickEvents.StartTick{
    public Integer maxEnchantmentLevel = 127;
	private SecToTick cooldown = new SecToTick(10);
	private int cooldownint = cooldown.GetTicks();
    @Override
    public void onStartTick(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player != null) {
                for (String tag : player.getScoreboardTags()) {
					if (tag.equalsIgnoreCase("ElytraForever")) {
						player.fallDistance = 0.0f;
						PlayerInventory inventory = player.getInventory();
						ItemStack elytra = new ItemStack(Items.ELYTRA);
						elytra.addEnchantment(Enchantments.UNBREAKING, maxEnchantmentLevel);
						elytra.addEnchantment(Enchantments.MENDING, maxEnchantmentLevel);
						elytra.addEnchantment(Enchantments.BINDING_CURSE, maxEnchantmentLevel);
						elytra.addEnchantment(Enchantments.VANISHING_CURSE, maxEnchantmentLevel);
						if (inventory.armor.get(2) != elytra) {
							inventory.armor.set(2, elytra);
						}
					}
				}
            }
			if (cooldownint <= 0) {
				GetAllPlayerLocation.getLocation(server);
				cooldownint = cooldown.GetTicks();
			}
			else {
				cooldownint -= 1;
			}
        }
    }
}
