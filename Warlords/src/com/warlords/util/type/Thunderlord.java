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
import com.warlords.util.itemlist.ItemListThunder;

public class Thunderlord extends SpielKlasse {
	public static String name = "Thunderlord";
	private static String description = "";
	private static int eps = 7;
	private static int eph = 10;
	public static int epmax = 200;
	public static int hpmax = 6750;
	// Lightningbolt
	public static final String boltname = "Lightning Bolt";
	public double dminbolt = 215;
	public double dmaxbolt = 399;
	public double ccbolt = 0.2;
	public double cmulbolt = 2.0;
	public int ebolt = 30;
	public double range = 60.0;
	public double cooldown = 2.0;

	// Elytra
	public double celytra = 40;
	public double espeed = 1.0;
	public double eelytra = 7;

	public Thunderlord(int level, Player p) {
		super(level, name, description, eps, eph, epmax, hpmax, p);
	}

	@Override
	public void addAbility(int j) {
		switch (j) {
		case 8:
			p.getInventory().setItem(8, ItemListGenerel.getElytraRune(celytra, eelytra, espeed));
		default:
			break;
		}

	}

	@Override
	public void setMainAbility() {
		ItemStack is = WeaponUtil.generateItemStack(getWeapon(), getName());
		if (is != null) {
			ItemMeta im = is.getItemMeta();
			if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 5) {
				im.setLore(ItemListThunder.getThunderMain(ebolt, ccbolt, cmulbolt,
						((double) (int) (dminbolt * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxbolt * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, range, cooldown));
			} else {
				im.setLore(
						ItemListThunder.getThunderMain(ebolt, ccbolt, cmulbolt, dminbolt, dmaxbolt, range, cooldown));
			}
			im.setDisplayName(ChatColor.GREEN + "Firebolt");
			is.setItemMeta(im);
			p.getInventory().setItem(0, is);
		}
	}
	@Override
	public ItemStack getMainAbility() {
		ItemStack is = WeaponUtil.generateItemStack(getWeapon(), getName());
		if (is != null) {
			ItemMeta im = is.getItemMeta();
			if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 5) {
				im.setLore(ItemListThunder.getThunderMain(ebolt, ccbolt, cmulbolt,
						((double) (int) (dminbolt * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxbolt * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, range, cooldown));
			} else {
				im.setLore(
						ItemListThunder.getThunderMain(ebolt, ccbolt, cmulbolt, dminbolt, dmaxbolt, range, cooldown));
			}
			im.setDisplayName(ChatColor.GREEN + "Firebolt");
			is.setItemMeta(im);
		}
		return is;
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
			if (getEnergy() >= ebolt) {
				if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 0) {
					SkillUtil.shootLightningBolt(p, dminbolt * (1.0 + getWeapon().getBoost()),
							dmaxbolt * (1.0 + getWeapon().getBoost()), ccbolt, cmulbolt, cooldown, range);
				} else {
					SkillUtil.shootLightningBolt(p, dminbolt, dmaxbolt, ccbolt, cmulbolt, cooldown, range);
				}

				doCooldown(j);
				removeEnergy(ebolt);
			}
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
		return 0;
	}

	@Override
	public ItemStack getAbility(int i) {
		switch (i) {
		case 0:
			return WeaponUtil.generateItemStack(getWeapon(), getName());
		case 8:
			return ItemListGenerel.getElytraRune(celytra, eelytra, espeed);
		default:
			break;
		}
		return null;
	}
}
