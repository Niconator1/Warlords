package com.warlords.util.skills.shaman;

import java.util.ArrayList;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class ChainLightning {
	private ArrayList<ArmorStand> a;
	private int dur;
	private int maxdur;
	private double red;
	private Player p;

	public ChainLightning(Player p, ArrayList<ArmorStand> alist, double red, double dur) {
		this.p = p;
		this.a = alist;
		this.red = red;
		this.dur = (int) (dur * 20);
		this.maxdur = this.dur;
	}

	public ArrayList<ArmorStand> getStand() {
		return a;
	}

	public int getDur() {
		return dur;
	}

	public void decreaseduration() {
		dur--;
	}

	public double getReduction() {
		return red;
	}

	public Player getPlayer() {
		return p;
	}

	public int getMaxDur() {
		return maxdur;
	}
}
