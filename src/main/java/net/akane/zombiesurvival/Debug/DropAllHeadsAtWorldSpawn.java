package net.akane.zombiesurvival.Debug;

import net.akane.zombiesurvival.ZombieSurvival;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;

public class DropAllHeadsAtWorldSpawn {
    public static void SpawnHeads(ServerWorld world) {
        getAllHeads(world);
    }

    public static ItemStack[] getAllItems() {
        ItemStack[] itemStacks = new ItemStack[Registries.ITEM.size()];
        int index = 0;
        for (Identifier itemId : Registries.ITEM.getIds()) {
			ItemStack item = new ItemStack(Registries.ITEM.get(itemId));
			ZombieSurvival.LOGGER.info(item.getName().getString());
			if (!item.getName().getString().equalsIgnoreCase("Orbital Strike")) {
				itemStacks[index] = item;
				index++;
			}
        }
        return itemStacks;
    }

    private static ItemStack[] getAllHeads(World world) {
        ItemStack[] itemStacks = new ItemStack[Registries.ITEM.size()];
		ZombieSurvival.DEBUG_LOGGER.info("You wouldn't believe it but if you have this mod and i didn't give it to you then, its stolen");
        if (ZombieSurvival._DEBUG && world.getRegistryKey().getValue().toString().equalsIgnoreCase("betaworld")) {
            int index = 0;
            for (Identifier itemId : Registries.ITEM.getIds()) {
                String name = new ItemStack(Registries.ITEM.get(itemId)).getName().getString();
                ItemStack item = new ItemStack(Registries.ITEM.get(itemId));
                world.spawnEntity(new ItemEntity(world, world.getSpawnPos().getX(), world.getSpawnPos().getY(), world.getSpawnPos().getZ(), item));
            }
        }
        return itemStacks;
    }

}
