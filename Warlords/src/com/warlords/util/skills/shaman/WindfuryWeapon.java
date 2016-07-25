package com.warlords.util.skills.shaman;

import org.bukkit.entity.Player;

public class WindfuryWeapon {
	private double dur;
	private Player p;
	private int count;
	private double chance;
	private double mul;
	public WindfuryWeapon(Player p,double chance, double mul, int count, double dur){
		this.dur=dur*20;
		this.p=p;
		this.chance=chance;
		this.mul=mul;
		this.count=count;
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
	public double chance(){
		return chance;
	}
	public int count(){
		return count;
	}
	public double mul(){
		return mul;
	}
}
