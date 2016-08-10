package com.warlords.util.data.inventory;

import org.bukkit.inventory.Inventory;

public class SkinGUI {
	private int type;
	private Inventory i;

	public SkinGUI(int type, Inventory i) {
		this.type = type;
		this.i = i;
	}

	public Inventory getInventory() {
		return i;
	}

	public int getType() {
		return type;
	}
}
