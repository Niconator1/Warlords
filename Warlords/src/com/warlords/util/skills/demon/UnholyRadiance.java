package com.warlords.util.skills.demon;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class UnholyRadiance {
	private ArmorStand a;
	private Player shooter;
	private Player target;
	private double hmin;
	private double hmax;
	private double critc;
	private double critm;
	private Location start;
	public UnholyRadiance(ArmorStand f, double hmin, double hmax, double critc,
			double critm, Player shooter, Player target, Location l) {
		this.a=f;
		this.hmin=hmin;
		this.hmax=hmax;
		this.critc=critc;
		this.critm=critm;
		this.shooter=shooter;
		this.target=target;
		this.start=l;
	}
	public ArmorStand getStand(){
		return a;
	}
	public Player getShooter(){
		return shooter;
	}
	public double hmin(){
		return hmin;
	}
	public double hmax(){
		return hmax;
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
	public Player getTarget(){
		return target;
	}
	
}
