package com.warlords.util;

import org.bukkit.inventory.Inventory;

public class CraftConfirmation {
	private int type;
	private Inventory i;

	public CraftConfirmation(int type, Inventory i) {
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
