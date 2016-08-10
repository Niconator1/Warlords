package com.warlords.util.data.inventory;

import org.bukkit.inventory.Inventory;

public class Confirmation {
	private Inventory i;
	private int id;
	public Confirmation(int id,Inventory i){
		this.id=id;
		this.i=i;
	}
	public Inventory getInventory(){
		return i;
	}
	public int getSlot(){
		return id;
	}
}
