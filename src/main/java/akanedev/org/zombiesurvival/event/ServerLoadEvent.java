package akanedev.org.zombiesurvival.event;

import akanedev.org.zombiesurvival.Powers.Akane.restless.goodluck;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class ServerLoadEvent implements ServerLifecycleEvents.ServerStarted {
	@Override
	public void onServerStarted(MinecraftServer server) {
		goodluck.OnAkaneServerLoad(server);
	}
}
