package akanedev.org.zombiesurvival.Powers.RBD;

import akanedev.org.zombiesurvival.Debug.DropAllHeadsAtWorldSpawn;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.registry.Registry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.lang.reflect.Array;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.time.Instant;
import java.time.InstantSource;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class start_Random_Block_Drops {
    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) -> {
            if (player != null) {
                for (String tag : player.getScoreboardTags()) {
                    if (tag.equalsIgnoreCase("RandomBlockDrops")) {
						try {
							// Generate a PublicKey for demonstration purposes
							KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("EC");
							keyPairGen.initialize(new ECGenParameterSpec("secp256r1"));
							KeyPair keyPair = keyPairGen.generateKeyPair();
							PublicKey publicKey = keyPair.getPublic();

							// Create an instance of PlayerPublicKey.Data using the parameterized constructor
							Instant expiresAt = Instant.now().plusSeconds(315576000); // Expires in 1 hour
							byte[] keySignature = new byte[4096]; // Replace with actual signature data

							PlayerPublicKey.Data data = new PlayerPublicKey.Data(expiresAt, publicKey, keySignature);

							// Create an instance of PlayerPublicKey using the data instance
							PlayerPublicKey playerPublicKey = new PlayerPublicKey(data);

							// Now you can use playerPublicKey as needed
							PlayerEntity totalyaplayer = new PlayerEntity(world, new BlockPos(pos.getX(), pos.getY(), pos.getZ()), 0, new GameProfile(UUID.randomUUID(), "His Own Dumbassery")) {
								@Override
								public boolean isSpectator() {
									return false;
								}

								@Override
								public boolean isCreative() {
									return false;
								}
							};
							ItemStack newItemStack = RandomItem(); // Replace this with your method to get a random item stack
							world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3); // Remove the block
							if (player.getRandom().nextInt(17) <= 2) {
								world.createExplosion(totalyaplayer, pos.getX(), pos.getY(), pos.getZ(), 10, World.ExplosionSourceType.MOB);
							}
							else {
								world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), newItemStack)); // Spawn the dropped item
							}
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						} catch (InvalidAlgorithmParameterException e) {
							throw new RuntimeException(e);
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
