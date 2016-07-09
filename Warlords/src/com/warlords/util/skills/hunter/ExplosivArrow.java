package com.warlords.util.skills.hunter;

import org.bukkit.entity.Player;

public class ExplosivArrow {
    private Player p;
    private double dur;
	private double dmin;
	private double cc;
	private double cm;

	public ExplosivArrow(double dmin, Player p, double cc, double cm,double dur){
		this.dur=dur*2;
		this.dmin=dmin;
		this.p=p;
		this.cc=cc;
		this.cm=cm;
	}
	public double dmin(){
		return dmin;
	}
	public Player getPlayer() {
		return p;
	}
	public double getCc() {
		return cc;
	}
	public double getCm() {
		return cm;
	}
	public void decreaseDuration() {
		dur--;
		
	}
	public double getDur() {
		return dur;
	}


}