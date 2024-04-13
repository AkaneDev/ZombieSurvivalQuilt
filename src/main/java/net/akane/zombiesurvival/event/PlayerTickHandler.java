package net.akane.zombiesurvival.event;

import com.mojang.authlib.GameProfile;
import net.akane.zombiesurvival.ZombieSurvival;
import net.akane.zombiesurvival.akanedata.DataArray;
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
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class PlayerTickHandler implements ServerTickEvents.StartTick{
    public Integer maxEnchantmentLevel = 127;
    @Override
    public void onStartTick(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player != null) {
                for (String tag : player.getCommandTags()) {
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
                PowerData.TickCooldown((IEntityDataSaver) player);
            }
        }
    }
}
