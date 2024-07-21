package akanedev.org.zombiesurvival.Powers.Akane.restless.immortal;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Objects;

public class toggleableImmortal {

	static AbstractTeam immortals;
	static Scoreboard scoreboard;

	public static void init(MinecraftServer server) {
		scoreboard = server.getScoreboard();
		immortals = scoreboard.addTeam("immortals");
	}

	public static boolean isImmortal(PlayerEntity player) {
		return Objects.requireNonNull(player.getScoreboardTeam()).isEqual(immortals);
	}

	public static void setImmortal(PlayerEntity player, boolean value) {
		if (value) {
			scoreboard.addPlayerToTeam(player.getEntityName(), (Team) immortals);
		} else {
			scoreboard.removePlayerFromTeam(player.getEntityName(), (Team) immortals);
		}
	}
}
