package org.ceiridge.stickcrafter;

import net.minecraft.item.Item;

public class StickItemForCalc {
	public Item type;
	public short multiplier;

	public StickItemForCalc(Item type, short multiplier) {
		this.type = type;
		this.multiplier = multiplier;
	}

	public StickItemForCalc(Item type, int multiplier) { // int for more code readability
		this(type, (short) multiplier);
	}
}
