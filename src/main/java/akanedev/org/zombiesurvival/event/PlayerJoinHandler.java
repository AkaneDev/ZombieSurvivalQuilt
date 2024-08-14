package akanedev.org.zombiesurvival.event;

import akanedev.org.zombiesurvival.Powers.Akane.restless.immortal.toggleableImmortal;
import akanedev.org.zombiesurvival.ZombieSurvival;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;

public class PlayerJoinHandler implements ServerPlayConnectionEvents.Join{
	@Override
	public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
		if (ZombieSurvival._DEBUG) {
//			toggleableImmortal.setImmortal(handler.getPlayer(), true);
		}
	}
}
