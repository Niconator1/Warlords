package com.warlords.util.skills.unique;

import org.bukkit.entity.Player;

public class Sphere {

	private Player p;
	private double dmax;
	private double dmin;
	private double cc;
	private double cmul;
	private double dmaxe;
	private double dmine;
	private double cce;
	private double cmule;
	private double dur;
	private double rad;
	private double rade;

	public Sphere(Player p, double dmin, double dmax, double cc, double cmul, double dur, double rad, double dmine,
			double dmaxe, double cce, double cmule, double rade) {
		this.p = p;
		this.dmin = dmin;
		this.dmax = dmax;
		this.cc = cc;
		this.cmul = cmul;
		this.dur = dur * 2;
		this.rad = rad;
		this.dmine = dmine;
		this.dmaxe = dmaxe;
		this.cce = cce;
		this.cmule = cmule;
		this.rade = rade;
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

	public double getDur() {
		return dur;
	}

	public double getRad() {
		return rad;
	}

	public void decreaseDuration() {
		dur--;
	}

	public void setRad(double d) {
		this.rad = d;
	}

	public double getDmaxE() {
		return dmaxe;
	}

	public double getDminE() {
		return dmine;
	}

	public double getCcE() {
		return cce;
	}

	public double getCmulE() {
		return cmule;
	}

	public double getRadE() {
		return rade;
	}

}
