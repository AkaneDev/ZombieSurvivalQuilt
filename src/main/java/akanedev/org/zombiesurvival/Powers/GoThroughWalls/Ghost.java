package akanedev.org.zombiesurvival.Powers.GoThroughWalls;

import akanedev.org.zombiesurvival.ZombieSurvival;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

import java.util.Set;

public class Ghost {
	private static boolean toggleGhost = false;
	public static void toggleghostMode(PlayerEntity player) {
		ZombieSurvival.LOGGER.info("Player is attempting ghost: " + player.getName().getString());
		if (toggleGhost) {
			toggleGhost = false;
		} else {
			toggleGhost = true;
		}
	}
}
