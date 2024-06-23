package akanedev.org.zombiesurvival.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;

public class CylinderDrawer {
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
}
