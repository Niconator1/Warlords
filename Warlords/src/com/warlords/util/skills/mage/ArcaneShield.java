package com.warlords.util.skills.mage;

import org.bukkit.entity.Player;

public class ArcaneShield {
	private double dur;
	private Player p;
	public ArcaneShield(double dur,Player p){
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
