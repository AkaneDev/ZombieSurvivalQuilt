package net.akane.zombiesurvival.mixin;

import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.*;

import java.util.Random;

@Mixin(MathHelper.class)
public class MathMixin {
//	@Unique
//	private static Random random = new Random();
//
//	@Final
//	@Shadow
//	private static final float[] SINE_TABLE = (float[]) Util.make(new float[65536], (sineTable) -> {
//		for(int i = 0; i < sineTable.length; ++i) {
//			sineTable[i] = (float)Math.sin((double)i * Math.PI * 2.0 / 65536.0);
//		}
//
//	});
//
//	/**
//	 * @author a
//	 * @reason a
//	 */
//	@Overwrite
//	public static float sin(float value) {
//		return SINE_TABLE[(int)(value * 10430.378F * Math.random()) & '\uffff'];
//	}
//
//	/**
//	 * @author a
//	 * @reason a
//	 */
//	@Overwrite
//	public static float cos(float value) {
//		return SINE_TABLE[(int)(value * (10430.378F + 16384.0F) * Math.random()) & '\uffff'];
//	}
}
