package com.warlords.util.data.inventory;

import org.bukkit.inventory.Inventory;

public class UpgradeConfirmation {
	private int type;
	private Inventory i;

	public UpgradeConfirmation(int type, Inventory i) {
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
