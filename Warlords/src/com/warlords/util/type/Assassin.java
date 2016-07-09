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
import com.warlords.util.itemlist.ItemListAssassin;
import com.warlords.util.itemlist.ItemListGenerel;

public class Assassin extends SpielKlasse {
	public static String name = "Assassin";
	private static String description = "";
	private static int eps = 5;
	private static int eph = 12;
	public static int epmax = 120;
	public static int hpmax = 7500;
	// Fireball
	public static final String ballname = "Shadow Step";
	public double dminball = 325;
	public double dmaxball = 455;
	public double ccball = 0.3;
	public double cmulball = 2;
	public int eball = 45;
	// Burst
	public static final String burstname = "Back Stab";
	public double dminburst = 380;
	public double dmaxburst = 720;
	public double ccburst = 0.65;
	public double cmulburst = 2.15;
	public int eburst = 20;
	public double cburst = 12.0;
	// Warp
	public static final String warpname = "Vanish";
	public int ewarp = 15;
	public double cwarp = 22.5;
	public double rwarp = 0.4;
	public int dwarp = 5;
	// Shield
	public static final String shieldname = "Stealth";
	public double cshield = 50;
	public int dshield = 4;
	// Inferno
	public static final String infname = "Deadly Poison";
	public double cinf = 80;
	public double ccinf = 0.2;
	public double cmulinf = 0.5;
	public double dinf = 18;
	public int ownp = 425;
	public int maxp = 720;
	// Elytra
	public double celytra = 40;
	public double espeed = 1.75;
	public double eelytra = 10;

	public Assassin(int level, Player p) {
		super(level, name, description, eps, eph, epmax, hpmax, p);
	}

	@Override
	public void addAbility(int j) {
		switch (j) {
		case 1:
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 1) {
				p.getInventory().setItem(1, ItemListAssassin.getAssassinRedRune(
						((double) (int) (cburst * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eburst, ccburst,
						cmulburst, ((double) (int) (dminburst * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxburst * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0));
			} else {
				p.getInventory().setItem(1,
						ItemListAssassin.getAssassinRedRune(
								((double) (int) (cburst * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eburst,
								ccburst, cmulburst, ((double) (int) (dminburst * 100.0)) / 100.0,
								((double) (int) (dmaxburst * 100.0)) / 100.0));
			}
			break;
		case 2:
			p.getInventory().setItem(2, ItemListAssassin.getAssassinPurpleRune(
					((double) (int) (cwarp * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), ewarp, dwarp, rwarp));
			break;
		case 3:
			p.getInventory().setItem(3, ItemListAssassin.getAssassinBlueRune(
					((double) (int) (cshield * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), dshield));
			break;
		case 4:
			if (getWeapon().getSkill() == 2 && getWeapon().getKlass() == 1) {
				p.getInventory().setItem(4,
						ItemListAssassin.getAssassinYellowRune(
								((double) (int) (cinf * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0),
								((double) (int) (getPoisonMax() * 100.0) / 100.0),
								((double) (int) (getPoisonOwn() * 100.0) / 100.0)));
			} else {
				p.getInventory().setItem(4, ItemListAssassin.getAssassinYellowRune(
						((double) (int) (cinf * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), maxp, ownp));
			}
			break;
		case 8:
			p.getInventory().setItem(8,ItemListGenerel.getElytraRune(celytra, eelytra, espeed));
		default:
			break;
		}

	}

	@Override
	public void setMainAbility() {
		ItemStack is = WeaponUtil.generateItemStack(getWeapon(), getName());
		if (is != null) {
			ItemMeta im = is.getItemMeta();
			if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 1) {
				im.setLore(ItemListAssassin.getAssassinMain(eball, ccball, cmulball,
						((double) (int) (dminball * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxball * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0));
			} else {
				im.setLore(ItemListAssassin.getAssassinMain(eball, ccball, cmulball, dminball, dmaxball));
			}
			im.setDisplayName(ChatColor.GREEN + "Shadow Step");
			is.setItemMeta(im);
			p.getInventory().setItem(0, is);
		}
	}
	@Override
	public ItemStack getMainAbility(){
		ItemStack is = WeaponUtil.generateItemStack(getWeapon(), getName());
		if (is != null) {
			ItemMeta im = is.getItemMeta();
			if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 1) {
				im.setLore(ItemListAssassin.getAssassinMain(eball, ccball, cmulball,
						((double) (int) (dminball * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxball * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0));
			} else {
				im.setLore(ItemListAssassin.getAssassinMain(eball, ccball, cmulball, dminball, dmaxball));
			}
			im.setDisplayName(ChatColor.GREEN + "Shadow Step");
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
		if(!Warlords.isUsingVanish(p)){
			switch (j) {
			case 0:
				if (getEnergy() >= eball) {
					boolean sucess = false;
					if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 1) {
						sucess = SkillUtil.doShadowstep(p, dminball * (1.0 + getWeapon().getBoost()),
								dmaxball * (1.0 + getWeapon().getBoost()), ccball, cmulball);
					} else {
						sucess = SkillUtil.doShadowstep(p, dminball, dmaxball, ccball, cmulball);
					}
					if (sucess == true) {
						doCooldown(j);
						removeEnergy(eball);
					}
				}
				break;
			case 1:
				if (getEnergy() >= eburst) {
					boolean sucess = false;
					if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 1) {
						sucess = SkillUtil.doBackstap(p, dminburst * (1.0 + getWeapon().getBoost()),
								dmaxburst * (1.0 + getWeapon().getBoost()), ccburst, cmulburst);
					} else {
						sucess = SkillUtil.doBackstap(p, dminburst, dmaxburst, ccburst, cmulburst);
					}
					if (sucess == true) {
						doCooldown(j);
						removeEnergy(eburst);
					}
				}
				break;
			case 2:
				if (getEnergy() >= ewarp) {
					SkillUtil.addVanish(rwarp,dwarp,p);
					doCooldown(j);
					removeEnergy(ewarp);
				}
				break;
			case 3:
				SkillUtil.addStealth(dshield, p);
				doCooldown(j);
				break;
			case 4:
				break;
			case 8:
				if(p.getInventory().getChestplate()!=null){
					break;
				}
				p.getInventory().setChestplate(ItemListGenerel.getElytraRune(celytra, eelytra, espeed));
				SkillUtil.addElytra(espeed, eelytra,p);
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
		return rwarp;
	}

	public double getPoisonMax() {
		if (getWeapon().getSkill() == 2 && getWeapon().getKlass() == 1) {
			return maxp * (1 + getWeapon().getBoost());
		}
		return maxp;
	}

	public double getPoisonOwn() {
		if (getWeapon().getSkill() == 2 && getWeapon().getKlass() == 1) {
			return ownp * (1 + getWeapon().getBoost());
		}
		return ownp;
	}

	@Override
	public ItemStack getAbility(int i) {
		switch (i) {
		case 0:
			return WeaponUtil.generateItemStack(getWeapon(), getName());
		case 1:
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 1) {
				return ItemListAssassin.getAssassinRedRune(
						((double) (int) (cburst * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eburst, ccburst,
						cmulburst, ((double) (int) (dminburst * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxburst * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0);
			} else {
				return ItemListAssassin.getAssassinRedRune(
						((double) (int) (cburst * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), eburst, ccburst,
						cmulburst, ((double) (int) (dminburst * 100.0)) / 100.0,
						((double) (int) (dmaxburst * 100.0)) / 100.0);
			}
		case 2:
			return ItemListAssassin.getAssassinPurpleRune(
					((double) (int) (cwarp * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), ewarp, dwarp, rwarp);
		case 3:
			return ItemListAssassin.getAssassinBlueRune(
					((double) (int) (cshield * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), dshield);
		case 4:
			if (getWeapon().getSkill() == 2 && getWeapon().getKlass() == 1) {
				return ItemListAssassin.getAssassinYellowRune(
						((double) (int) (cinf * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0),
						((double) (int) (getPoisonMax() * 100.0) / 100.0),
						((double) (int) (getPoisonOwn() * 100.0) / 100.0));
			} else {
				return ItemListAssassin.getAssassinYellowRune(
						((double) (int) (cinf * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), maxp, ownp);
			}
		case 8:
			return ItemListGenerel.getElytraRune(celytra, eelytra, espeed);
		default:
			break;
		}
		return null;
	}
}
