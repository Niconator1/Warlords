package com.warlords.util.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemListGenerel {
	public static ItemStack getElytraRune(double celytea,double energy,double speed) {
		ItemStack ret = new ItemStack(Material.ELYTRA, 1);
		ItemMeta i = ret.getItemMeta();
		i.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "Elytra");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + celytea + " seconds");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost per second: " + ChatColor.YELLOW + energy);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Speed: " + ChatColor.GREEN + speed * 100.0 + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Use your glorious wings to fly up in the sky");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "to escape fights or attack enemys from above");
		i.setLore(lore);
		ret.setItemMeta(i);
		return ret;
	}
}
