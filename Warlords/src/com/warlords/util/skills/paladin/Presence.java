package com.warlords.util.skills.paladin;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Presence {
	private double dur;
	private Player p;
	private double speed;
	private int eps;
	private ArrayList<Player> allies;
	public Presence(Player p,ArrayList<Player> allies,double dur,double speed,int eps){
		this.dur=dur*20;
		this.p=p;
		this.speed=speed;
		this.eps=eps;
		this.allies=allies;
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
	public int getEPS(){
		return eps;
	}
	public ArrayList<Player> getAllys() {
		return allies;
	}
	public double getSpeed() {
		return speed;
	}
}
