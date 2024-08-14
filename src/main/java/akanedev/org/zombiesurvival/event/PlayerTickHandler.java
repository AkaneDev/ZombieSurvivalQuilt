package akanedev.org.zombiesurvival.event;

import akanedev.org.zombiesurvival.Powers.Akane.restless.immortal.toggleableImmortal;
import akanedev.org.zombiesurvival.Powers.Gojo.Gojo;
import akanedev.org.zombiesurvival.ZombieSurvival;
import com.mojang.authlib.GameProfile;
import net.akane.akanedata.DataArray;
import net.akane.akanemaths.SecToTick;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
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
import net.minecraft.world.World;

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
						Gojo.checkAndApplyInfinity(player.getWorld(), player, 3.5);
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
							food.setCount(1);
							inventory.offHand.set(0, food);
						}
					}
					if (tag.equalsIgnoreCase("Akanes") || tag.equalsIgnoreCase("debug")) {
						for (String tag2 : player.getScoreboardTags()) {
							if (tag2.equalsIgnoreCase("immortal")) {
								player.heal(444);
								player.setAbsorptionAmount(666);
							}
						}
						player.fallDistance = 0.0f;
						if (player.isDead()) {
							player.getWorld().createExplosion(player, player.getX(), player.getY(), player.getZ(), 100f, World.ExplosionSourceType.MOB);
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
