package com.warlords.util.data.types;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.warlords.main.Warlords;
import com.warlords.util.data.AllyUtilMethods;

public class WarlordsPlayerAllys{
	private ArrayList<UUID> list;
	private Player p;
	public WarlordsPlayerAllys(Player p) {
		this.p=p;
		loadAllys();
	}
	public ArrayList<UUID> getAllys(){
		return list;
	}
	public void saveAllys(){
		AllyUtilMethods.save(getAllys(), Warlords.getPlugin(Warlords.class).getDataFolder(),"/allys/"+p.getUniqueId());
	}
	private void loadAllys(){
		list = AllyUtilMethods.load(Warlords.getPlugin(Warlords.class).getDataFolder(),"/allys/"+p.getUniqueId());
	}
	public void addAlly(UUID id){
		list.add(id);
	}
	public void setAllys(ArrayList<UUID> list2) {
		list = list2;		
	}
	public void removeAlly(UUID id) {
		list.remove(id);
	}
	public boolean hasPlayer(Player p2){
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				if(p2.getUniqueId().compareTo(list.get(i))==0){
					return true;
				}
			}
		}
		return false;
	}
}
