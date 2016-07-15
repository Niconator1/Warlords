package com.warlords.util.skills.shaman;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class ChainLightning {
	private ArrayList<ArmorStand> a;
	private Player shooter;
	private Player target;
	private double dmin;
	private double dmax;
	private double critc;
	private double critm;
	private Location start;
	private int dur = 10;

	public ChainLightning(ArrayList<ArmorStand> f, double dmin, double dmax, double critc, double critm, Player shooter,
			Player target, Location l) {
		this.a = f;
		this.dmin = dmin;
		this.dmax = dmax;
		this.shooter = shooter;
		this.target = target;
		this.critc = critc;
		this.critm = critm;
		this.start = l;
	}

	public ArrayList<ArmorStand> getStand() {
		return a;
	}
	public int getDur(){
		return dur;
	}
	public void decreaseduration(){
		dur--;
	}
	public Player getTarget() {
		return target;
	}

	public Player getShooter() {
		return shooter;
	}

	public double dmin() {
		return dmin;
	}

	public double dmax() {
		return dmax;
	}

	public double critc() {
		return critc;
	}

	public double critm() {
		return critm;
	}

	public Location start() {
		return start;
	}
}
