package com.warlords.util.skills.hunter;

import org.bukkit.entity.Player;

public class BloodArrow {
	private double dur;
	private Player p;
	public BloodArrow(Player p,double dur){
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
