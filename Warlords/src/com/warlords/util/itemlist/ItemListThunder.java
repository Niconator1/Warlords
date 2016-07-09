package com.warlords.util.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class ItemListThunder {

	public static List<String> getThunderMain(int ebolt, double ccbolt, double cmulbolt, double dminbolt,
			double dmaxbolt, double range, double cooldown) {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + ebolt);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + ccbolt * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmulbolt * 100.0 + "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Hurl a fast, piercing bolt of lightning");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "that deals " + ChatColor.RED + dminbolt + ChatColor.GRAY
				+ " - " + ChatColor.RED + dmaxbolt + ChatColor.GRAY + " damage to all");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "enemies it passes through. Each target hit");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "reduces the cooldown of Chain Lightning by");
		lore.add(
				ChatColor.RESET + "" + ChatColor.GOLD + cooldown + ChatColor.GRAY + " seconds. Has a maximum range of");
		lore.add(ChatColor.RESET + "" + ChatColor.YELLOW + (int)(range+0.5) + ChatColor.GRAY + " blocks.");
		return lore;
	}
}
