package com.warlords.util.skills.hunter;

import org.bukkit.entity.Player;

public class MarkingArrow {
	private double dur;
	private Player p;
	public MarkingArrow(double dur,Player p){
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
