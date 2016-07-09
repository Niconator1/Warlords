package com.warlords.util.skills.mage;

import org.bukkit.entity.Player;

public class Inferno {
	private double dur;
	private Player p;
	public Inferno(double dur,Player p){
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
