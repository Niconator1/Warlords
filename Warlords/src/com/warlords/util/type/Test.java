package com.warlords.util.type;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.util.SkillUtil;
import com.warlords.util.SpielKlasse;
import com.warlords.util.WeaponUtil;
import com.warlords.util.itemlist.ItemListGenerel;

public class Test extends SpielKlasse {
	public static String name = "Test";
	private static String description = "";
	private static int eps = 100;
	private static int eph = 100;
	public static int epmax = 1000;
	public static int hpmax = 1000000;
	// Elytra
	public double celytra = 40;
	public double espeed = 0.75;
	public double eelytra = 5;

	public boolean isWrath = false;

	public Test(int level, Player p) {
		super(level, name, description, eps, eph, epmax, hpmax, p);
	}

	@Override
	public void addAbility(int j) {
		switch (j) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:

			break;
		case 6:
			break;
		case 8:
			p.getInventory().setItem(8, ItemListGenerel.getElytraRune(celytra, eelytra, espeed));
		default:
			break;
		}

	}

	@Override
	public void setMainAbility() {
	}

	@Override
	public ItemStack getMainAbility() {
		return null;
	}

	@Override
	public void doAbility(int j) {
		ItemStack is = p.getInventory().getItem(j);
		if (is == null) {
			return;
		}
		if (is.getDurability() == 8 || is.getDurability() == 15) {
			return;
		}
		switch (j) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 6:
			break;
		case 8:
			if (p.getInventory().getChestplate() != null) {
				break;
			}
			p.getInventory().setChestplate(ItemListGenerel.getElytraRune(celytra, eelytra, espeed));
			SkillUtil.addElytra(espeed, eelytra, p);
			break;
		default:
			break;
		}
	}

	@Override
	public void doCooldown(int j) {
		ItemStack is = new ItemStack(Material.INK_SACK, 1);
		ItemMeta im;
		switch (j) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 6:
			break;
		case 8:
			is.setAmount((int) Math.round(celytra * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);
			im = p.getInventory().getItem(8).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + "Elytra");
			is.setItemMeta(im);
			p.getInventory().setItem(8, is);
			break;
		default:
			break;
		}
	}

	@Override
	public double getHeal() {
		return 0.0;
	}

	@Override
	public ItemStack getAbility(int i) {
		switch (i) {
		case 0:
			return WeaponUtil.generateItemStack(getWeapon(), getName());
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 6:
			break;
		case 8:
			return ItemListGenerel.getElytraRune(celytra, eelytra, espeed);
		default:
			break;
		}
		return null;
	}
}
