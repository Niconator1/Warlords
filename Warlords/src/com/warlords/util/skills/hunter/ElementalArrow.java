package com.warlords.util.skills.hunter;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ElementalArrow {
	private Arrow a;
	private Vector v;
	private double dmin;
	private Player shooter;
	private double dmax;
	private double critc;
	private double critm;
	private Location start;
	private boolean explosive;
	private boolean blood;
	private boolean mark;
	public ElementalArrow(Arrow a,Vector v,double dmin,double dmax,double critc,double critm,Player shooter,Location l,boolean is,boolean mark,boolean blood){
		this.a=a;
		this.v=v;
		this.dmin=dmin;
		this.dmax=dmax;
		this.shooter=shooter;
		this.critc=critc;
		this.critm=critm;
		this.start=l;
		this.explosive=is;
		this.mark=mark;
		this.blood=blood;
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
	public boolean isExplosive() {
		return explosive;
	}
	public boolean isBlood() {
		return blood;
	}
	public boolean isMarking() {
		return mark;
	}
}
