package com.warlords.util.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.util.type.Crusader;

public class ItemListCrusader {

	public static ItemStack getCrusaderRedRune(double cconsecrate, int econsecrate, double ccconsecrate,
			double cmulconsecrate, double dminconsecrate, double dmaxconsecrate, double strikeconsecrate,
			double dconsecrate) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (1));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Crusader.consecratename);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cconsecrate + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy: " + ChatColor.YELLOW + econsecrate);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + ccconsecrate * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmulconsecrate * 100.0
				+ "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Consecrate the ground below your");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "feet, declaring it sacred. Enemies");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "standing on it will take " + ChatColor.RED + dminconsecrate);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "- " + ChatColor.RED + " " + dmaxconsecrate + ChatColor.GRAY
				+ " damage per second and");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "take " + ChatColor.RED + strikeconsecrate * 100.0 + "%"
				+ ChatColor.GRAY + " increased damage");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "from your paladin strikes. Lasts");
		lore.add(ChatColor.RESET + "" + ChatColor.YELLOW + dconsecrate + ChatColor.GRAY + " seconds.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static ItemStack getCrusaderPurpleRune(double clinfusion, int elinfusion, double slinfusion,
			double dlinfusion) {
		ItemStack ret = new ItemStack(Material.GLOWSTONE_DUST, 1);
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Crusader.infusionname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + clinfusion + " seconds");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "You become infused with light,");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "restoring " + ChatColor.GREEN + elinfusion + ChatColor.GRAY
				+ " energy and");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "increasing your movement speed by");
		lore.add(ChatColor.RESET + "" + ChatColor.YELLOW + slinfusion * 100.0 + "%" + ChatColor.GRAY + " for "
				+ ChatColor.GOLD + dlinfusion + ChatColor.GRAY + " seconds.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static ItemStack getCrusaderBlueRune(double chradiance, int ehradiance, double cchradiance,
			double cmhradiance, double dminhradiance, double dmaxhradiance) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (10));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Crusader.hradiancename);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + chradiance + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + ehradiance);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + cchradiance * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmhradiance * 100.0
				+ "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Radiate with holy energy, healing");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "yourself and all nearby allies for");
		lore.add(ChatColor.RESET + "" + ChatColor.GREEN + dminhradiance + ChatColor.GRAY + " - " + ChatColor.GREEN
				+ dmaxhradiance + ChatColor.GRAY + " health.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static ItemStack getCrusaderYellowRune(double cpresence, int epspresence, double spresence,
			double dpresence) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (14));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Crusader.presencename);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cpresence + " seconds");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Your presence on the battlefield");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "inspires your allies, increasing");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "their energy regeneration by " + ChatColor.RED + epspresence);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "per second and their movement speed");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "by " + ChatColor.RED + spresence * 100.0 + "%"
				+ ChatColor.GRAY + " for " + ChatColor.GOLD + dpresence + ChatColor.GRAY + " seconds.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static List<String> getCrusaderMain(int estrike, double ccstrike, double cmulstrike, double dminstrike,
			double dmaxstrike, int egive, int countstrike) {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + estrike);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + ccstrike * 100.0 + "%");
		lore.add(
				ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmulstrike * 100.0 + "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Strike the targeted enemy player,");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "causing " + ChatColor.RED + dminstrike + ChatColor.GRAY
				+ " - " + ChatColor.RED + dmaxstrike + ChatColor.GRAY + " damage");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "and restoring " + ChatColor.YELLOW + egive + ChatColor.GRAY
				+ " energy to " + ChatColor.GOLD + countstrike);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "nearby allies.");
		return lore;
	}
}
