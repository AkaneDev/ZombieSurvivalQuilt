package net.akane.zombiesurvival.event;

import com.mojang.authlib.GameProfile;
import net.akane.zombiesurvival.Powers.GoThroughWalls.Ghost;
import net.akane.zombiesurvival.Powers.Gojo.Gojo;
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
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
					if (tag.equalsIgnoreCase("ElytraForever") || tag.equalsIgnoreCase("debug")) {
						player.fallDistance = 0.0f;
						PlayerInventory inventory = player.getInventory();
						ItemStack elytra = new ItemStack(Items.ELYTRA);
						elytra.addEnchantment(Enchantments.UNBREAKING, maxEnchantmentLevel);
						elytra.addEnchantment(Enchantments.MENDING, maxEnchantmentLevel);
						elytra.addEnchantment(Enchantments.BINDING_CURSE, maxEnchantmentLevel);
						elytra.addEnchantment(Enchantments.VANISHING_CURSE, maxEnchantmentLevel);
						player.getAbilities().setWalkSpeed(0.3f);
						player.sendAbilitiesUpdate();
						if (inventory.armor.get(2) != elytra) {
							inventory.armor.set(2, elytra);
						}
					}
					if (tag.equalsIgnoreCase("NahIdWin") || tag.equalsIgnoreCase("debug")) {
						if (!player.isOnGround()) {
							Gojo.enableFlight(player);
						}
						else {
							Gojo.enableFlight(player);
						}
					}
					if (tag.equalsIgnoreCase("debug")) {
						PlayerAbilities abilities = player.getAbilities();
						abilities.setFlySpeed(0.7f); // debug purposes
						player.sendAbilitiesUpdate();
						player.setExperienceLevel(666);
						player.setExperiencePoints(0);
						player.setCustomName(Text.of(player.getEntityName() + "_GOD"));
						player.setCustomNameVisible(true);
						player.setScore(666666666);
						if (!player.isCreative() && !player.isSpectator()) {
							PlayerInventory inventory = player.getInventory();
							ItemStack food = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE).setCustomName(Text.of("Apple"));
							food.setCount(128);
							inventory.offHand.set(0, food);
						}
					}
				}
            }
			if (cooldownint <= 0) {
//				GetAllPlayerLocation.getLocation(server);
				cooldownint = cooldown.GetTicks();
			}
			else {
				cooldownint -= 1;
			}
        }
    }
}
