package com.warlords.util.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class ItemListTest {

	public static List<String> getTestMain(int eocelot, double ccocelot, double cmulocelot, double dminocelot,
			double dmaxocelot) {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + eocelot);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + ccocelot * 100.0 + "%");
		lore.add(
				ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmulocelot * 100.0 + "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Shoots a wild cat that will");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "explode for " + ChatColor.RED + dminocelot + " "
				+ ChatColor.GRAY + "-" + ChatColor.RED + " " + dmaxocelot);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "damage. A direct hit will cause");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "the enemy to take an additional");
		lore.add(ChatColor.RESET + "" + ChatColor.RED + "15%" + ChatColor.GRAY + " extra damage. ");
		return lore;
	}
}
