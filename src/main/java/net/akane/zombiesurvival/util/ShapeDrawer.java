package net.akane.zombiesurvival.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.lang.reflect.Array;
import java.util.function.Function;

public class ShapeDrawer {
	public static void drawCylinder(BlockPos startPos, int radius, int heightLimit) {
		MinecraftClient client = MinecraftClient.getInstance();

		double centerX = startPos.getX() + 0.5;
		double centerZ = startPos.getZ() + 0.5;

		for (int y = startPos.getY(); y >= -64; y--) {
			double currentRadius = radius * Math.sqrt(1.0 - ((double)(startPos.getY() - y) / heightLimit));

			for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 16) {
				double xOffset = currentRadius * Math.cos(theta);
				double zOffset = currentRadius * Math.sin(theta);

				double particleX = centerX + xOffset;
				double particleY = y;
				double particleZ = centerZ + zOffset;

				client.world.addParticle(ParticleTypes.FLAME, particleX, particleY, particleZ, 0.0, 0.0, 0.0);
			}
		}
	}

	/**
	 * @param startPos Position Of center of Sphere
	 * @param radius Radius of Sphere
	 * @param color Color, Not Implemented
	 */
	public static void drawSphere(BlockPos startPos, float radius, float[] color, World world) {
		MinecraftClient client = MinecraftClient.getInstance();

		// Center coordinates of the sphere
		double centerX = startPos.getX();
		double centerY = startPos.getY();
		double centerZ = startPos.getZ();


		// Density of particles (number of particles per unit distance)
		double density = 0.1; // Adjust this value to control the density

		for (double phi = 0; phi <= Math.PI; phi += 1.0 / radius) {
			for (double theta = 0; theta <= 2 * Math.PI; theta += 1.0 / radius) {
				double x = centerX + radius * Math.sin(phi) * Math.cos(theta);
				double y = centerY + radius * Math.sin(phi) * Math.sin(theta);
				double z = centerZ + radius * Math.cos(phi);

				client.world.addParticle(ParticleTypes.SONIC_BOOM, x, y, z, 0, 0, 0);
			}
		}
	}

	public static void drawSphereLine(BlockPos startPos, float radius, float[] color, World world) {
		MinecraftClient client = MinecraftClient.getInstance();
		// Center coordinates of the sphere
		double centerX = startPos.getX();
		double centerY = startPos.getY();
		double centerZ = startPos.getZ();


		// Density of particles (number of particles per unit distance)
		double density = 0.1; // Adjust this value to control the density

		for (double phi = 0; phi <= Math.PI; phi += 1.0 / radius) {
			for (double theta = 0; theta <= 2 * Math.PI; theta += 1.0 / radius) {
				double x = centerX + radius * Math.sin(phi) * Math.cos(theta);
				double y = centerY + radius * Math.sin(phi) * Math.sin(theta);
				double z = centerZ + radius * Math.cos(phi);

				world.setBlockState(new BlockPos((int) x, (int) y, (int) z), Blocks.STONE.getDefaultState());
			}
		}
	}
}
