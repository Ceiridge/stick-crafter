package org.ceiridge.stickcrafter;

import org.ceiridge.stickcrafter.util.TimeStopper;
import net.fabricmc.api.ModInitializer;

public class StickCrafter implements ModInitializer {
	public static boolean doQuickMove, instantTrades;
	public static TimeStopper quickMoveStopper = new TimeStopper(200); // 200ms delay
	
	@Override
	public void onInitialize() {
		System.out.println("StickCrafter initialized!");
	}
}
