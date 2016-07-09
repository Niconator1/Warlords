package com.warlords.util.skills.hunter;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Companion {
	private int live;
	private Player p;
	private Entity endermite;
	private double dur;
	public Companion(Player p, int live, Entity endermite,double dur){	
		this.dur=dur*2;
		this.p=p;
		this.setLive(live);
		this.endermite=endermite;
	}

	public Player getPlayer(){
		return p;
	}



	public Entity getEndermite() {
		return endermite;
	}

	public double getDur() {
		return dur;
	}

	public void decreaseDuration() {
		dur--;
	}

	public int getLive() {
		return live;
	}

	public void setLive(int live) {
		this.live = live;
	}
}

