package com.warlords.util.skills.shaman;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Lightningbolt {
	private ArmorStand a;
	private Vector v;
	private double dmin;
	private Player shooter;
	private double dmax;
	private double critc;
	private double critm;
	private Location start;
	private double range;
	private double cooldown;
	public Lightningbolt(ArmorStand f, Vector v, double dmin, double dmax, double critc, double critm, Player shooter,
			Location l, double range, double cooldown) {
		this.a=f;
		this.v=v;
		this.dmin=dmin;
		this.dmax=dmax;
		this.shooter=shooter;
		this.critc=critc;
		this.critm=critm;
		this.start=l;
		this.range=range;
		this.cooldown=cooldown;
	}
	public ArmorStand getStand(){
		return a;
	}
	public Vector getVector(){
		return v;
	}
	public Player getShooter(){
		return shooter;
	}
	public double dmin(){
		return dmin;
	}
	public double dmax(){
		return dmax;
	}
	public double critc(){
		return critc;
	}
	public double critm(){
		return critm;
	}
	public Location start(){
		return start;
	}
	public double cooldown(){
		return cooldown;
	}
	public double getRange(){
		return range;
	}
}
