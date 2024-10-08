package akanedev.org.zombiesurvival.networking.C2S;

import akanedev.org.zombiesurvival.ZombieSurvival;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;

public class C2Sdopowers {
	private static final Identifier PACKET_ID = new Identifier(ZombieSurvival.modID, "change_gamemode");
	private String gameMode;

	public C2Sdopowers(String gameMode) {
		this.gameMode = gameMode;
	}

	public void write(PacketByteBuf buf) {
		buf.writeString(gameMode);
	}

	public static class Serializer {
		public static void serialize(PacketByteBuf buf, C2Sdopowers packet) {
			packet.write(buf);
		}

		public static C2Sdopowers deserialize(PacketByteBuf buf) {
			return new C2Sdopowers(buf.readString());
		}
	}

	public static CustomPayloadC2SPacket createPacket(String gameMode) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeIdentifier(PACKET_ID);
		Serializer.serialize(buf, new C2Sdopowers(gameMode));
		return new CustomPayloadC2SPacket(PACKET_ID, buf);
	}
}
