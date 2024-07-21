package akanedev.org.zombiesurvival.Powers.Akane.restless;

import akanedev.org.zombiesurvival.Powers.Akane.restless.immortal.toggleableImmortal;
import net.minecraft.server.MinecraftServer;

public class goodluck {
	public static void dontmindthisryanjustAkaneStuff() {

	}

	public static void OnAkaneServerLoad(MinecraftServer server) {
		toggleableImmortal.init(server);
	}
}
