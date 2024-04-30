package net.akane.zombiesurvival.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;

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

	public static void drawSphere(BlockPos startPos, int radius, int heightLimit) {
		MinecraftClient client = MinecraftClient.getInstance();

		// Center coordinates of the sphere
		double centerX = 0;
		double centerY = 0;
		double centerZ = 0;


		// Density of particles (number of particles per unit distance)
		double density = 0.1; // Adjust this value to control the density

		for (double phi = 0; phi <= Math.PI; phi += 1.0 / radius) {
			for (double theta = 0; theta <= 2 * Math.PI; theta += 1.0 / radius) {
				double x = centerX + radius * Math.sin(phi) * Math.cos(theta);
				double y = centerY + radius * Math.sin(phi) * Math.sin(theta);
				double z = centerZ + radius * Math.cos(phi);

				client.world.addParticle(ParticleTypes.ASH, x, y, z, 0.0, 0.0, 0.0);
			}
		}
	}

}
