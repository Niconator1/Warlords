package com.warlords.util.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.util.type.Assassin;

public class ItemListAssassin {

	public static ItemStack getAssassinRedRune(double cburst, int eburst, double ccburst, double cmulburst,
			double dminburst, double dmaxburst) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (1));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Assassin.burstname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cburst + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + eburst);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + ccburst * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmulburst * 100.0 + "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Stab a knive in the back of your enemy");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "and deal " + ChatColor.RED + dminburst + " " + ChatColor.GRAY
				+ "-" + ChatColor.RED + " " + dmaxburst);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "damage.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}


	public static ItemStack getAssassinPurpleRune(double cwarp, int ewarp, double dwarp,double rwarp) {
		ItemStack ret = new ItemStack(Material.GLOWSTONE_DUST, 1);
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Assassin.warpname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cwarp + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + ewarp);
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "You will vanish and get " + ChatColor.YELLOW + rwarp*100.0+"%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "speed. You also can not hit or heal someone");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "or get hit or healed by someone.");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "After " + ChatColor.AQUA + dwarp+ChatColor.GRAY+" seconds you are");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "able to seen by enemys.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}


	public static ItemStack getAssassinBlueRune(double cshield, double dshield) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (10));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Assassin.shieldname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cshield + " seconds");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "You feel stealthy and so you can");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "hit your enemies and lose every time " + ChatColor.AQUA + "2"
				+ " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "on your other cooldowns. Lasts " + ChatColor.GOLD + dshield);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "seconds.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}


	public static ItemStack getAssassinYellowRune(double cinf, double maxliveenemy, double yourdmg) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (14));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Assassin.infname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cinf + " seconds");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Kill someone under " + ChatColor.GREEN + maxliveenemy);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "life, but also damage yourself"
				);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "with "+ ChatColor.RED + yourdmg+ChatColor.GRAY+".");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static List<String> getAssassinMain(int eball, double ccball, double cmulball, double dminball,
			double dmaxball) {   
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + eball);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + ccball * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmulball * 100.0 + "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Teleport you behind a enemy through the");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "shadows and deal " + ChatColor.RED + dminball + " "
				+ ChatColor.GRAY + "-" + ChatColor.RED + " " + dmaxball+ChatColor.GRAY+" damage");
		return lore;
	}
}
