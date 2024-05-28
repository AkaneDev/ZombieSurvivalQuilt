package net.akane.zombiesurvival.Powers.RBD;

import net.akane.zombiesurvival.Debug.DropAllHeadsAtWorldSpawn;
import net.akane.zombiesurvival.util.IEntityDataSaver;
import net.akane.zombiesurvival.util.PowerData;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Random;

public class start_Random_Block_Drops {
    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) -> {
            if (player != null) {
                for (String tag : player.getScoreboardTags()) {
                    if (tag.equalsIgnoreCase("RandomBlockDrops")) {
                        ItemStack newItemStack = RandomItem(); // Replace this with your method to get a random item stack
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3); // Remove the block
						if (player.getRandom().nextInt(2) == 1) {
							TntEntity tnt = new TntEntity(world, pos.getX(), pos.getY(), pos.getZ(), player);
							tnt.setFuse(0);
							tnt.setNoGravity(true);
							world.spawnEntity(tnt);
						}
						else {
							world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), newItemStack)); // Spawn the dropped item
						}
                    }
                }
            }
			return true;
        });
    }

    private static ItemStack RandomItem() {
        ItemStack[] allItems = DropAllHeadsAtWorldSpawn.getAllItems();
        Random randomItemGenerator = new Random();
        return allItems[randomItemGenerator.nextInt(allItems.length)];
    }
}
