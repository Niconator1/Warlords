package com.warlords.util.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.util.type.Test;

public class ItemListTest {
	public static ItemStack getPurpleRune(double clazor, int elazor, double cclazor, double cmullazor,
			double dminlazor, double dmaxlazor, double dlazor) {
		ItemStack ret = new ItemStack(Material.GLOWSTONE_DUST, 1);
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Test.lazorname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + clazor + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy: " + ChatColor.YELLOW + elazor);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + cclazor * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmullazor * 100.0 + "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Fires a laser which will hit enemys in him");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "for " + ChatColor.RED + dminlazor + " "
				+ ChatColor.GRAY + "-" + ChatColor.RED + " " + dmaxlazor);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "damage. The laser will follow your view and lasts");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "for "+ChatColor.YELLOW+dlazor+ChatColor.GRAY+" seconds.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static List<String> getTestMain(int eocelot, double ccocelot, double cmulocelot, double dminocelot,
			double dmaxocelot) {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + eocelot);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + ccocelot * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmulocelot * 100.0 + "%");
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
