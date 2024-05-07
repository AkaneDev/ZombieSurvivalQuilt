
package net.akane.zombiesurvival.Powers.Gojo;

import net.minecraft.entity.player.PlayerEntity;

public class Gojo {
	public void gojoReg(PlayerEntity player){

	}

	public static void enableFlight(PlayerEntity player) {
		player.getAbilities().allowFlying = true;
		player.sendAbilitiesUpdate();
	}
 }
