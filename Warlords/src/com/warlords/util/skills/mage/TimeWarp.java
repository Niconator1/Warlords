package com.warlords.util.skills.mage;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TimeWarp {
	private Player p;
	private Location l;
	private ArrayList<Location> locs = new ArrayList<Location>();
	
	public TimeWarp(Player p,Location l){
		this.p=p;
		this.l=l;

	}
	
	public Player getPlayer(){
		return p;
	}
	public Location getLocation(){
		return l;
	}
	public ArrayList<Location> getPositions(){
		return locs;
	}
	public void addLocation(Location ps){
		this.locs.add(ps);
	}
}
