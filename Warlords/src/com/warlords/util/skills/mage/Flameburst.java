package com.warlords.util.skills.mage;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Flameburst {
	private Arrow a;
	private Vector v;
	private double dmin;
	private Player shooter;
	private double dmax;
	private double critc;
	private double critm;
	private Location start;
	public Flameburst(Arrow a,Vector v,double dmin,double dmax,double critc,double critm,Player shooter,Location l){
		this.a=a;
		this.v=v;
		this.dmin=dmin;
		this.dmax=dmax;
		this.shooter=shooter;
		this.critc=critc;
		this.critm=critm;
		this.start=l;
	}
	public Arrow getArrow(){
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
}
