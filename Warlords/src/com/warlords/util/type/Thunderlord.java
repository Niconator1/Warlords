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
	private static int eps = 200;// 10
	private static int eph = 10;
	public static int epmax = 200;
	public static int hpmax = 5500;
	// Lightningbolt
	public static final String boltname = "Lightning Bolt";
	public double dminbolt = 215;
	public double dmaxbolt = 399;
	public double ccbolt = 0.2;
	public double cmulbolt = 2.0;
	public int ebolt = 30;
	public double rangebolt = 60.0;
	public double cbolt = 2.0;
	// Chain Lightning
	public static final String chainname = "Chain Lightning";
	public double dminchain = 243;
	public double dmaxchain = 485;
	public double ccchain = 0.2;
	public double cmulchain = 1.75;
	public int echain = 20;
	public double rangechain = 10.0;
	public double cchain = 10.8;
	public int countchain = 2;
	public double increasechain = 0.1;
	public double reductionchain = 0.1;
	public double reductionmaxchain = 0.3;
	public double durchain = 4.5;

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
		case 1:
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 5) {
				p.getInventory().setItem(1, ItemListThunder.getThunderRedRune(
						((double) (int) (cchain * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), echain, ccchain,
						cmulchain, ((double) (int) (dminchain * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxchain * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, countchain,
						rangechain, increasechain, reductionchain, reductionmaxchain, durchain));
			} else {
				p.getInventory().setItem(1,
						ItemListThunder.getThunderRedRune(
								((double) (int) (cchain * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), echain,
								ccchain, cmulchain, ((double) (int) (dminchain * 100.0)) / 100.0,
								((double) (int) (dmaxchain * 100.0)) / 100.0, countchain, rangechain, increasechain,
								reductionchain, reductionmaxchain, durchain));
			}
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
			if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 5) {
				im.setLore(ItemListThunder.getThunderMain(ebolt, ccbolt, cmulbolt,
						((double) (int) (dminbolt * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxbolt * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, rangebolt,
						cbolt));
			} else {
				im.setLore(
						ItemListThunder.getThunderMain(ebolt, ccbolt, cmulbolt, dminbolt, dmaxbolt, rangebolt, cbolt));
			}
			im.setDisplayName(ChatColor.GREEN + "Lightning Bolt");
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
						((double) (int) (dmaxbolt * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, rangebolt,
						cbolt));
			} else {
				im.setLore(
						ItemListThunder.getThunderMain(ebolt, ccbolt, cmulbolt, dminbolt, dmaxbolt, rangebolt, cbolt));
			}
			im.setDisplayName(ChatColor.GREEN + "Lightning Bolt");
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
				if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 5) {
					SkillUtil.shootLightningBolt(p, dminbolt * (1.0 + getWeapon().getBoost()),
							dmaxbolt * (1.0 + getWeapon().getBoost()), ccbolt, cmulbolt, cbolt, rangebolt);
				} else {
					SkillUtil.shootLightningBolt(p, dminbolt, dmaxbolt, ccbolt, cmulbolt, cbolt, rangebolt);
				}

				doCooldown(j);
				removeEnergy(ebolt);
			}
			break;
		case 1:
			if (getEnergy() >= echain) {
				boolean sucess = false;
				if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 5) {
					sucess = SkillUtil.doChainLightning(p, dminchain * (1.0 + getWeapon().getBoost()),
							dmaxchain * (1.0 + getWeapon().getBoost()), ccchain, cmulchain, rangechain,countchain,reductionchain,durchain);
				} else {
					sucess = SkillUtil.doChainLightning(p, dminchain, dmaxchain, ccchain, cmulchain, rangechain,countchain,reductionchain,durchain);
				}
				if (sucess) {
					p.getWorld().playSound(p.getLocation(), "shaman.chainlightning.activation", 1, 1);
					doCooldown(j);
					removeEnergy(echain);
				}
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
		case 1:
			is.setAmount((int) Math.round(cchain * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);
			im = p.getInventory().getItem(1).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + chainname);
			is.setItemMeta(im);
			p.getInventory().setItem(1, is);
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
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 5) {
				return ItemListThunder.getThunderRedRune(
						((double) (int) (cchain * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), echain, ccchain,
						cmulchain, ((double) (int) (dminchain * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxchain * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, countchain,
						rangechain, increasechain, reductionchain, reductionmaxchain, durchain);
			} else {
				return ItemListThunder.getThunderRedRune(
						((double) (int) (cchain * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), echain, ccchain,
						cmulchain, ((double) (int) (dminchain * 100.0)) / 100.0,
						((double) (int) (dmaxchain * 100.0)) / 100.0, countchain, rangechain, increasechain,
						reductionchain, reductionmaxchain, durchain);
			}
		case 8:
			return ItemListGenerel.getElytraRune(celytra, eelytra, espeed);
		default:
			break;
		}
		return null;
	}
}
