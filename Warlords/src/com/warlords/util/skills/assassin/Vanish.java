package com.warlords.util.skills.assassin;

import org.bukkit.entity.Player;

public class Vanish {
	private double dur;
	private Player p;
	private double speed;
	public Vanish(double dur,Player p,double speed){
		this.dur=dur*2;
		this.p=p;
		this.speed=speed;
	}
	public double getDuration(){
		return dur;
	}
	public Player getPlayer(){
		return p;
	}
	public void decreaseDuration() {
		dur--;
	}
	public double getBoost() {
		return speed;
	}
}
