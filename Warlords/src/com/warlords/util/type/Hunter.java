package com.warlords.util.type;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.main.Warlords;
import com.warlords.util.SkillUtil;
import com.warlords.util.SpielKlasse;
import com.warlords.util.WeaponUtil;
import com.warlords.util.itemlist.ItemListGenerel;
import com.warlords.util.itemlist.ItemListHunter;

public class Hunter extends SpielKlasse {
	// Ranger
	public static String name = "Hunter";
	private static String description = "";
	private static int eps = 7;
	private static int eph = 7;
	public static int epmax = 120;
	public static int hpmax = 6750;
	// Elemental Arrow
	public static final String ballname = "Elemental Arrow";
	public double dminball = 225.5;
	public double dmaxball = 320;
	public double ccball = 0.2;
	public double cmulball = 1.65;
	public int eball = 20;
	// Explosiv Arrow
	public static final String burstname = "Explosive Arrow";
	public double cc = 0.1;
	public double cm = 0.75;
	public double dburst = 4.5;
	public double dminburst = 110;
	public int eburst = 15;
	public double cburst = 14.5;
	// Blood Arrow
	public static final String warpname = "Blood Arrow";
	public double dwarp = 4.5;
	public int ewarp = 15;
	public double cwarp = 14.5;
	// Marking Arrow
	public static final String shieldname = "Marking Arrow";
	public int eshield = 15;
	public double dshield = 7.5;
	public double cshield = 22;
	public double dmgboost = 1.15;
	// Companion
	public static final String infname = "Companion";
	public double companiondmgboost = 1.3;
	public double dinf = 12;
	public double cinf = 75;
	public int companionlive = 800;
	// Elytra
	public double celytra = 40;
	public double espeed = 1.25;
	public double eelytra = 10;

	// Zwischenwerte
	public double dmgb = 0;// explo
	public double dmgb1 = 1;// marking
	public double dmgb2 = 1;// compa

	public Hunter(int level, Player p) {
		super(level, name, description, eps, eph, epmax, hpmax, p);
	}

	@Override
	public void addAbility(int j) {
		switch (j) {
		case 1:
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 3) {
				p.getInventory().setItem(1,
						ItemListHunter.getHunterRedRune(
								((double) (int) (cburst * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eburst,
								(double) (int) (dminburst * (1.0 + getWeapon().getBoost()) * 100.0) / 100.0, dburst));

			} else {
				p.getInventory().setItem(1,
						ItemListHunter.getHunterRedRune(
								((double) (int) (cburst * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eburst,
								(double) (int) (dminburst * 100.0) / 100.0, dburst));
			}
			break;
		case 2:
			p.getInventory().setItem(2, ItemListHunter.getHunterPurpleRune(
					((double) (int) (cwarp * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), ewarp, dwarp));
			break;
		case 3:
			p.getInventory().setItem(3, ItemListHunter.getHunterBlueRune(
					((double) (int) (cshield * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eshield, dshield));
			break;
		case 4:
			p.getInventory().setItem(4, ItemListHunter.getHunterYellowRune(
					((double) (int) (cinf * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), dinf));

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
			if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 3) {
				im.setLore(ItemListHunter.getHunterMain(eball, ccball, cmulball,
						((double) (int) (dminball * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxball * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0));
			} else {
				im.setLore(ItemListHunter.getHunterMain(eball, ccball, cmulball, dminball, dmaxball));
			}
			im.setDisplayName(ChatColor.GREEN + "Elemental Arrow");
			is.setItemMeta(im);
			p.getInventory().setItem(0, is);
		}
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
		if (!Warlords.isUsingVanish(p)) {
			switch (j) {
			case 0:
				if (getEnergy() >= eball) {
					boolean f = false;
					if (dmgb > 0) {
						f = true;
					}
					boolean m = false;
					if (dmgb1 > 1) {
						m = true;
					}
					if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 3) {
						SkillUtil.doElementelarrow(p,
								(dminball * (1.0 + getWeapon().getBoost()) + dmgb) * dmgb1 * dmgb2,
								(dmaxball * (1.0 + getWeapon().getBoost()) + dmgb) * dmgb1 * dmgb2, ccball + ccBoost,
								cmulball + cmulBoost, f, m);
					} else {
						SkillUtil.doElementelarrow(p, (dminball + dmgb) * dmgb1 * dmgb2,
								(dmaxball + dmgb) * dmgb1 * dmgb2, ccball + ccBoost, cmulball + cmulBoost, f, m);
					}

					doCooldown(j);
					removeEnergy(eball);

				}
				break;
			case 1:
				if (getEnergy() >= eburst) {

					if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 3) {
						SkillUtil.addExplosivarrow(p, dminburst * (1.0 + getWeapon().getBoost()), cc, cm, dburst);
					} else {
						SkillUtil.addExplosivarrow(p, dminburst, cc, cm, dburst);
					}
					doCooldown(j);
					removeEnergy(eburst);

				}
				break;
			case 2:
				if (getEnergy() >= ewarp) {
					SkillUtil.addBloodarrow(p, dwarp);
					doCooldown(j);
					removeEnergy(ewarp);
				}
				break;
			case 3:
				if (getEnergy() >= eshield) {
					SkillUtil.addMarkingarrow(p, dshield);
					doCooldown(j);
					removeEnergy(eshield);
				}
				break;
			case 4:
				SkillUtil.doCompanion(p, dinf);
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
		return 0;
	}

	@Override
	public ItemStack getAbility(int i) {
		switch (i) {
		case 0:
			return WeaponUtil.generateItemStack(getWeapon(), getName());
		case 1:
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 3) {
				return ItemListHunter.getHunterRedRune(
						((double) (int) (cburst * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eburst,
						((double) (int) (dminburst * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, dburst);
			} else {
				return ItemListHunter.getHunterRedRune(
						((double) (int) (cburst * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eburst,
						((double) (int) (dminburst * 100.0)) / 100.0, dburst);
			}
		case 2:
			return ItemListHunter.getHunterPurpleRune(
					((double) (int) (cwarp * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), ewarp, dwarp);
		case 3:
			return ItemListHunter.getHunterBlueRune(
					((double) (int) (cshield * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eshield, dshield);
		case 4:
			return ItemListHunter.getHunterYellowRune(
					((double) (int) (cinf * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), dinf);

		case 8:
			return ItemListGenerel.getElytraRune(celytra, eelytra, espeed);
		default:
			break;
		}
		return null;
	}
}
