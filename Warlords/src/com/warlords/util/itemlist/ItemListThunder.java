package com.warlords.util.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.util.type.Thunderlord;

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
		lore.add(ChatColor.RESET + "" + ChatColor.YELLOW + (int) (range + 0.5) + ChatColor.GRAY + " blocks.");
		return lore;
	}

	public static ItemStack getThunderRedRune(double cchain, int echain, double ccchain, double cmulchain,
			double dminchain, double dmaxchain, int countchain, double rangechain, double increasechain,
			double reductionchain, double reductionmaxchain, double durchain) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (1));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Thunderlord.chainname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cchain + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + echain);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + ccchain * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmulchain * 100.0 + "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Disrcharge a bolt of lightning at the");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "targeted enemy player that deals " + ChatColor.RED + dminchain
				+ ChatColor.GRAY + " -");
		lore.add(ChatColor.RESET + "" + ChatColor.RED + dmaxchain + ChatColor.GRAY + " damage and jumps to "
				+ ChatColor.RED + countchain);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "additional targets within " + ChatColor.YELLOW
				+ (int) (rangechain + 0.5) + ChatColor.GRAY + " blocks.");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Each time the lightning jumps the damage");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "is increased by " + ChatColor.RED + increasechain * 100.0
				+ "%" + ChatColor.GRAY + ". You gain");
		lore.add(ChatColor.RESET + "" + ChatColor.YELLOW + reductionchain * 100.0 + "% " + ChatColor.GRAY
				+ "damage resistance for each");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "target hit, up to " + ChatColor.YELLOW
				+ reductionmaxchain * 100.0 + "% " + ChatColor.GRAY + "damage");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "resistance. This buff lasts " + ChatColor.GOLD + durchain);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "seconds.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}
}
