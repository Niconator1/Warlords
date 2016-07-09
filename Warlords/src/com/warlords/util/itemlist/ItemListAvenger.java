package com.warlords.util.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.util.type.Avenger;

public class ItemListAvenger {

	public static ItemStack getAvengerRedRune(double cconsecrate, int econsecrate, double ccconsecrate,
			double cmulconsecrate, double dminconsecrate, double dmaxconsecrate, double strikeconsecrate,
			double dconsecrate) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (1));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Avenger.consecratename);
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

	public static ItemStack getAvengerPurpleRune(double clinfusion, int elinfusion, double slinfusion,
			double dlinfusion) {
		ItemStack ret = new ItemStack(Material.GLOWSTONE_DUST, 1);
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN +  Avenger.infusionname);
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

	public static ItemStack getAvengerBlueRune(double chradiance, int ehradiance, double cchradiance,
			double cmhradiance, double dminhradiance, double dmaxhradiance) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (10));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN +  Avenger.hradiancename);
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

	public static ItemStack getAvengerYellowRune(double cwrath, int countwrath, int rwrath, double epswrath,
			double dwrath) {
		ItemStack ret = new ItemStack(Material.INK_SACK, 1, (short) (14));
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + Avenger.wrathname);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + cwrath + " seconds");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Burst with incredible holy power,");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "causing your Avenger's Strikes to");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "hit up to " + ChatColor.YELLOW + countwrath + ChatColor.GRAY
				+ " additional enemies");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "that are within " + ChatColor.YELLOW + rwrath + ChatColor.GRAY
				+ " blocks of");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "your target. Your energy per second");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "is increased by " + ChatColor.YELLOW + epswrath
				+ ChatColor.GRAY + " for the");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "duration of the effect. Lasts");
		lore.add(ChatColor.RESET + "" + ChatColor.GOLD + dwrath + ChatColor.GRAY + " seconds.");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}

	public static List<String> getAvengerMain(int estrike, double ccstrike, double cmulstrike, double dminstrike,
			double dmaxstrike) {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + estrike);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + ccstrike * 100.0 + "%");
		lore.add(
				ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + cmulstrike * 100.0 + "%");
		lore.add("");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Strike the targeted enemy player,");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "causing " + ChatColor.RED + dminstrike + ChatColor.GRAY
				+ " - " + ChatColor.RED + dmaxstrike+ChatColor.GRAY+" damage");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "and removing " + ChatColor.GOLD + "3 " + ChatColor.GRAY
				+ "energy.");
		return lore;
	}
}
