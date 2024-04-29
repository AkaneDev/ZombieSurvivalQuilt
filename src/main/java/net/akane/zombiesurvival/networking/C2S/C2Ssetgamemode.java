package net.akane.zombiesurvival.networking.C2S;

import io.netty.buffer.Unpooled;
import net.akane.zombiesurvival.ZombieSurvival;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;

public class C2Ssetgamemode {
	private static final Identifier PACKET_ID = new Identifier(ZombieSurvival.modID, "change_gamemode");
	private String gameMode;

	public C2Ssetgamemode(String gameMode) {
		this.gameMode = gameMode;
	}

	public void write(PacketByteBuf buf) {
		buf.writeString(gameMode);
	}

	public static class Serializer {
		public static void serialize(PacketByteBuf buf, C2Ssetgamemode packet) {
			packet.write(buf);
		}

		public static C2Ssetgamemode deserialize(PacketByteBuf buf) {
			return new C2Ssetgamemode(buf.readString());
		}
	}

	public static CustomPayloadC2SPacket createPacket(String gameMode) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeIdentifier(PACKET_ID);
		Serializer.serialize(buf, new C2Ssetgamemode(gameMode));
		return new CustomPayloadC2SPacket(PACKET_ID, buf);
	}
}
