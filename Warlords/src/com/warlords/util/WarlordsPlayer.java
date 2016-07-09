package com.warlords.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.warlords.main.Warlords;

public class WarlordsPlayer{
	private String uuid;
	private String klasse = "";
	private int weaponSlot = 0;
	private boolean isMode = false;
	private boolean isPlayerAttack = true;
	public WarlordsPlayer(String uuid) {
		this.uuid=uuid;
	}
	public SpielKlasse getSk() {
		return Warlords.getKlasse(getPlayer());
	}
	private Player getPlayer() {
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			if((p.getUniqueId()+"").equals(uuid)){
				return p;
			}
		}
		return null;
	}
	public void setPlayerAttackMode(boolean mode){
		this.isPlayerAttack=mode;
	}
	public boolean getPlayerAttackMode(){
		return isPlayerAttack;
	}
	public void setMode(boolean mode){
		this.isMode=mode;
	}
	public boolean getMode(){
		return isMode;
	}
	public void setKlasse(String klasse){
		this.klasse=klasse;
	}
	public String getKlasse(){
		return klasse;
	}
	public int getWeaponSlot() {
		return weaponSlot;
	}
	public void setWeaponSlot(int weaponSlot) {
		this.weaponSlot = weaponSlot;
	}
	public String getUUID(){
		return uuid;
	}
	public ArrayList<Weapon> getWeaponlist(){
		ArrayList<Weapon> list = FileUtilMethods.load(Warlords.getPlugin(Warlords.class).getDataFolder(),"/weapons/"+getPlayer().getUniqueId());
		return list;
	}
	public void saveWeaponlist(ArrayList<Weapon> list){
		FileUtilMethods.save(list, Warlords.getPlugin(Warlords.class).getDataFolder(),"/weapons/"+getPlayer().getUniqueId());
	}		
}
