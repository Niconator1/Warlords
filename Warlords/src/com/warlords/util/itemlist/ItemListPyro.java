package com.warlords.util.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.util.type.Pyromancer;

public class ItemListPyro {

	public static ItemStack getPyroRedRune(double cburst, int eburst, double ccburst, double cmulburst,
			double dminburst, double dmaxburst) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (1));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Pyromancer.burstname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cburst + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + eburst);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + ccburst * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmulburst * 100.0 + "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Launch a flame burst that will");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "explode for " + ChatColor.RED + dminburst + " "
				+ ChatColor.GRAY + "-" + ChatColor.RED + " " + dmaxburst);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "damage. The critical chance");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "increases by " + ChatColor.RED + "1.0% " + ChatColor.GRAY
				+ "for each");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "travelled block. Up to 100%.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static ItemStack getPyroPurpleRune(double cwarp, int ewarp, double dwarp, double rwarp) {
		ItemStack ret = new ItemStack(Material.GLOWSTONE_DUST, 1);
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Pyromancer.warpname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cwarp + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + ewarp);
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Activate to place a time rune on the");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "ground. After " + ChatColor.GOLD + dwarp + " "
				+ ChatColor.GRAY + "seconds, you will warp");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "back to that location and restore " + ChatColor.GREEN
				+ rwarp * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "of your health.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static ItemStack getPyroBlueRune(double cshield, int eshield, double d, double rshield, double dshield) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (10));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Pyromancer.shieldname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cshield + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + eshield);
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Surround yourself with arcane energy,");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "creating a shield that will absorb up to");
		lore.add(ChatColor.RESET + "" + ChatColor.YELLOW + d + " " + ChatColor.GRAY + "(" + ChatColor.YELLOW
				+ rshield * 100.0 + "% " + ChatColor.GRAY + "of your maximum");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "health) incoming damage. Lasts " + ChatColor.GOLD + dshield);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "seconds.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static ItemStack getPyroYellowRune(double cinf, double ccinf, double cmulinf, double dinf) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (14));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Pyromancer.infname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cinf + " seconds");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Combust into a molten inferno,");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "increasing your Crit Chance by " + ChatColor.RED
				+ ccinf * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "and your Crit Multiplier by " + ChatColor.RED
				+ cmulinf * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "for " + ChatColor.GOLD + dinf + " " + ChatColor.GRAY
				+ "seconds.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static List<String> getPyroMain(int eball, double ccball, double cmulball, double dminball,
			double dmaxball) {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + eball);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + ccball * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmulball * 100.0 + "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Shoots a fireball that will");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "explode for " + ChatColor.RED + dminball + " "
				+ ChatColor.GRAY + "-" + ChatColor.RED + " " + dmaxball);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "damage. A direct hit will cause");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "the enemy to take an additional");
		lore.add(ChatColor.RESET + "" + ChatColor.RED + "15%" + ChatColor.GRAY + " extra damage. ");
		return lore;
	}
}
