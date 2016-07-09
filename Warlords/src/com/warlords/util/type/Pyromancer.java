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
import com.warlords.util.itemlist.ItemListPyro;

public class Pyromancer extends SpielKlasse {
	public static String name = "Pyromancer";
	private static String description = "";
	private static int eps = 7;
	private static int eph = 10;
	public static int epmax = 200;
	public static int hpmax = 6000;
	// Fireball
	public static final String ballname = "Fireball";
	public double dminball = 334.8;
	public double dmaxball = 433.2;
	public double ccball = 0.2;
	public double cmulball = 1.75;
	public int eball = 35;
	// Burst
	public static final String burstname = "Flameburst";
	public double dminburst = 557;
	public double dmaxburst = 753;
	public double ccburst = 0.2;
	public double cmulburst = 2.1;
	public int eburst = 30;
	public double cburst = 10.8;// 10.8
	// Warp
	public static final String warpname = "Time Warp";
	public int ewarp = 15;
	public double cwarp = 33.75;
	public double rwarp = 0.3;
	public int dwarp = 5;
	// Shield
	public static final String shieldname = "Arcane Shield";
	public int eshield = 20;
	public double cshield = 36;
	public double rshield = 0.5;
	public int dshield = 6;
	// Inferno
	public static final String infname = "Inferno";
	public double cinf = 54;
	public double ccinf = 0.2;
	public double cmulinf = 0.5;
	public int dinf = 18;

	// Elytra
	public double celytra = 40;
	public double espeed = 1.0;
	public double eelytra = 10;

	public Pyromancer(int level, Player p) {
		super(level, name, description, eps, eph, epmax, hpmax, p);
	}

	@Override
	public void addAbility(int j) {
		switch (j) {
		case 1:
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 0) {
				p.getInventory().setItem(1, ItemListPyro.getPyroRedRune(
						((double) (int) (cburst * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eburst, ccburst,
						cmulburst, ((double) (int) (dminburst * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxburst * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0));
			} else {
				p.getInventory().setItem(1,
						ItemListPyro.getPyroRedRune(
								((double) (int) (cburst * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eburst,
								ccburst, cmulburst, ((double) (int) (dminburst * 100.0)) / 100.0,
								((double) (int) (dmaxburst * 100.0)) / 100.0));
			}
			break;
		case 2:
			p.getInventory().setItem(2, ItemListPyro.getPyroPurpleRune(
					((double) (int) (cwarp * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), ewarp, dwarp, rwarp));
			break;
		case 3:
			p.getInventory().setItem(3,
					ItemListPyro.getPyroBlueRune(
							((double) (int) (cshield * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eshield,
							rshield * getMaxHP(), rshield, dshield));
			break;
		case 4:
			p.getInventory().setItem(4, ItemListPyro.getPyroYellowRune(
					((double) (int) (cinf * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), ccinf, cmulinf, dinf));
			break;
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
			if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 0) {
				im.setLore(ItemListPyro.getPyroMain(eball, ccball, cmulball,
						((double) (int) (dminball * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxball * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0));
			} else {
				im.setLore(ItemListPyro.getPyroMain(eball, ccball, cmulball, dminball, dmaxball));
			}
			im.setDisplayName(ChatColor.GREEN + "Fireball");
			is.setItemMeta(im);
			p.getInventory().setItem(0, is);
		}
	}
	@Override
	public ItemStack getMainAbility() {
		ItemStack is = WeaponUtil.generateItemStack(getWeapon(), getName());
		if (is != null) {
			ItemMeta im = is.getItemMeta();
			if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 0) {
				im.setLore(ItemListPyro.getPyroMain(eball, ccball, cmulball,
						((double) (int) (dminball * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxball * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0));
			} else {
				im.setLore(ItemListPyro.getPyroMain(eball, ccball, cmulball, dminball, dmaxball));
			}
			im.setDisplayName(ChatColor.GREEN + "Fireball");
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
			if (getEnergy() >= eball) {
				if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 0) {
					SkillUtil.shootFireball(p, dminball * (1.0 + getWeapon().getBoost()),
							dmaxball * (1.0 + getWeapon().getBoost()), ccball + ccBoost, cmulball + cmulBoost);
				} else {
					SkillUtil.shootFireball(p, dminball, dmaxball, ccball + ccBoost, cmulball + cmulBoost);
				}

				doCooldown(j);
				removeEnergy(eball);
			}
			break;
		case 1:
			if (getEnergy() >= eburst) {
				if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 0) {
					SkillUtil.shootFlameburst(p, dminburst * (1.0 + getWeapon().getBoost()),
							dmaxburst * (1.0 + getWeapon().getBoost()), ccburst + ccBoost, cmulburst + cmulBoost);
				} else {
					SkillUtil.shootFlameburst(p, dminburst, dmaxburst, ccburst + ccBoost, cmulburst + cmulBoost);
				}
				doCooldown(j);
				removeEnergy(eburst);
			}
			break;
		case 2:
			if (getEnergy() >= ewarp) {
				SkillUtil.addTimeWarp(p);
				doCooldown(j);
				removeEnergy(ewarp);
			}
			break;
		case 3:
			if (getEnergy() >= eshield) {
				SkillUtil.addArcaneShield(dshield, p);
				doCooldown(j);
				removeEnergy(eshield);
			}
			break;
		case 4:
			SkillUtil.addInferno(dinf, p);
			doCooldown(j);
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
			is.setAmount((int) Math.round(cburst * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);
			im = p.getInventory().getItem(1).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + burstname);
			is.setItemMeta(im);
			p.getInventory().setItem(1, is);
			break;
		case 2:
			is.setAmount((int) Math.round(cwarp * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);
			im = p.getInventory().getItem(2).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + warpname);
			is.setItemMeta(im);
			p.getInventory().setItem(2, is);
			break;
		case 3:
			is.setAmount((int) Math.round(cshield * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);
			im = p.getInventory().getItem(3).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + shieldname);
			is.setItemMeta(im);
			p.getInventory().setItem(3, is);
			break;
		case 4:
			is.setAmount((int) Math.round(cinf * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);
			im = p.getInventory().getItem(4).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + infname);
			is.setItemMeta(im);
			p.getInventory().setItem(4, is);
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
		return rwarp;
	}

	@Override
	public ItemStack getAbility(int i) {
		switch (i) {
		case 0:
			return WeaponUtil.generateItemStack(getWeapon(), getName());
		case 1:
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 0) {
				return ItemListPyro.getPyroRedRune(
						((double) (int) (cburst * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eburst, ccburst,
						cmulburst, ((double) (int) (dminburst * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxburst * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0);
			} else {
				return ItemListPyro.getPyroRedRune(
						((double) (int) (cburst * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eburst, ccburst,
						cmulburst, ((double) (int) (dminburst * 100.0)) / 100.0,
						((double) (int) (dmaxburst * 100.0)) / 100.0);
			}
		case 2:
			return ItemListPyro.getPyroPurpleRune(
					((double) (int) (cwarp * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), ewarp, dwarp, rwarp);
		case 3:
			return ItemListPyro.getPyroBlueRune(
					((double) (int) (cshield * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eshield,
					rshield * getMaxHP(), rshield, dshield);
		case 4:
			return ItemListPyro.getPyroYellowRune(
					((double) (int) (cinf * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), ccinf, cmulinf, dinf);
		case 8:
			return ItemListGenerel.getElytraRune(celytra, eelytra, espeed);
		default:
			break;
		}
		return null;
	}
}
