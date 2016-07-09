package com.warlords.util.skills.paladin;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Consecrate {

	private Player p;
	private double dmax;
	private double dmin;
	private double cc;
	private double cmul;
	private double strike;
	private double dur;
	private double rad;
	private Location l;

	public Consecrate(Player p, double dmin, double dmax, double cc, double cmul, double strike, double dur, double rad,
			Location l) {
		this.p = p;
		this.dmin = dmin;
		this.dmax = dmax;
		this.cc = cc;
		this.cmul = cmul;
		this.strike = strike;
		this.dur = dur;
		this.rad = rad;
		this.l = l;
	}

	public Player getP() {
		return p;
	}

	public double getDmax() {
		return dmax;
	}

	public double getDmin() {
		return dmin;
	}

	public double getCc() {
		return cc;
	}

	public double getCmul() {
		return cmul;
	}

	public double getStrike() {
		return strike;
	}

	public double getDur() {
		return dur;
	}

	public double getRad() {
		return rad;
	}

	public Location getLocation() {
		return l;
	}

	public void decreaseDuration() {
		dur--;
	}

}
