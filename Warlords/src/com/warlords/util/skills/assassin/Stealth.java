package com.warlords.util.skills.assassin;

import org.bukkit.entity.Player;

public class Stealth {
	private double dur;
	private Player p;
	public Stealth(double dur,Player p){
		this.dur=dur*2;
		this.p=p;
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
}
