package com.warlords.util.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.util.type.Hunter;

public class ItemListHunter {

	public static ItemStack getHunterRedRune(double cburst, int eburst, double dminburst, double cc, double cm,
			double dburst) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (1));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Hunter.burstname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cburst + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + eburst);
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Your next arrows will detonate on impact");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "and deal " + ChatColor.RED + dminburst + " " + ChatColor.GRAY
				+ "additional damage,");
		lore.add(ChatColor.RESET + "" + ChatColor.RED + 10 + "%" + ChatColor.GRAY + " crit chance " + ChatColor.GRAY
				+ "and " + ChatColor.RED + 75 + "%" + ChatColor.GRAY + " crit multiplier.");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Lasts " + ChatColor.GOLD + dburst + ChatColor.GRAY
				+ " seconds" + ChatColor.GRAY + ".");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static ItemStack getHunterPurpleRune(double cwarp, int ewarp, double dwarp) {
		ItemStack ret = new ItemStack(Material.GLOWSTONE_DUST, 1);
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Hunter.warpname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cwarp + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + ewarp);
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Your next arrows will heal you " + ChatColor.YELLOW + 21
				+ "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "of your dealt damage from arrows.");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Lasts " + ChatColor.GOLD + dwarp + ChatColor.GRAY + " seconds"
				+ ChatColor.GRAY + ".");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static ItemStack getHunterBlueRune(double cshield, int eshield, double dshield) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (10));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Hunter.shieldname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cshield + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + eshield);
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Your next arrows deal " + ChatColor.RED + 15 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "more damage.");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Lasts " + ChatColor.GOLD + dshield + ChatColor.GRAY
				+ " seconds" + ChatColor.GRAY + ".");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static ItemStack getHunterYellowRune(double cinf, int companionlive, double dinf) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (14));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Hunter.infname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cinf + " seconds");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Call for an companion from the wilds.");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "This companion helps you causing your damage ");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "to be increased by " + ChatColor.RED + 30 + "%"
				+ ChatColor.GRAY + ".");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Lasts " + ChatColor.GOLD + dinf + ChatColor.GRAY + " seconds"
				+ ChatColor.GRAY + ".");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static List<String> getHunterMain(int eball, double ccball, double cmulball, double dminball,
			double dmaxball) {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + eball);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + ccball * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmulball * 100.0 + "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Shoot an arrow of pure energy");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "which deals " + ChatColor.RED + dminball + " "
				+ ChatColor.GRAY + "-" + ChatColor.RED + " " + dmaxball + " " + ChatColor.GRAY + "damage.");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Can be strengthened by skills.");
		return lore;
	}
}
