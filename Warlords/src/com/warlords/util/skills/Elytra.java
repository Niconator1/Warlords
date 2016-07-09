package com.warlords.util.skills;

import org.bukkit.entity.Player;

public class Elytra {
	private double dur;
	private double s;
	private Player p;
	private double buf = 0.0;
	public Elytra(double dur,double s,Player p){
		this.dur=dur;
		this.p=p;
		this.s=s;
	}
	public double getSpeed(){
		return dur;
	}
	public double getEnergy(){
		return s;
	}
	public Player getPlayer(){
		return p;
	}
	public boolean increaseBuf(){
		buf+=s;
		if(buf>=20){
			buf=0;
			return true;
		}
		else{
			return false;
		}
	}
}