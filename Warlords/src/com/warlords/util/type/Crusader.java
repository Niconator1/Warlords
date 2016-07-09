package com.warlords.util.type;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.util.SkillUtil;
import com.warlords.util.SpielKlasse;
import com.warlords.util.WeaponUtil;
import com.warlords.util.itemlist.ItemListCrusader;
import com.warlords.util.itemlist.ItemListGenerel;

public class Crusader extends SpielKlasse {
	public static String name = "Crusader";
	private static String description = "";
	private static int eps = 10;
	private static int eph = 10;
	public static int epmax = 160;
	public static int hpmax = 8250;
	// Crusader's Strike
	public static final String cstrikename = "Crusader's Strike";
	public double dminastrike = 326;
	public double dmaxastrike = 441;
	public double ccastrike = 0.25;
	public double cmulastrike = 1.75;
	public int eastrike = 45;
	public int egive = 15;
	public double rstrike = 3;
	public int countstrike = 2;
	public double rallies = 10.0;
	// Consecrate
	public static final String consecratename = "Consecrate";
	public double cconsecrate = 9;
	public int econsecrate = 25;
	public double ccconsecrate = 0.15;
	public double cmulconsecrate = 2.0;
	public double dminconsecrate = 145.2;
	public double dmaxconsecrate = 195.8;
	public double strikeconsecrate = 0.15;
	public double dconsecrate = 5.0;
	public double rconsecrate = 5.5;
	// Light Infusion
	public static final String infusionname = "Light Infusion";
	public double clinfusion = 18;
	public int elinfusion = 60;
	public double slinfusion = 0.3;
	public double dlinfusion = 8;
	// Holy Radiance
	public static final String hradiancename = "Holy Radiance";
	public double chradiance = 22.5;
	public int ehradiance = 10;
	public double cchradiance = 0.15;
	public double cmulhradiance = 1.75;
	public double hminhradiance = 582;
	public double hmaxhradiance = 760;
	public double rhradiance = 6;
	// Inspiring Presence
	public static final String presencename = "Inspiring Presence";
	public double cpresence = 60;// 81
	public double spresence = 0.3;
	public int epspresence = 5;
	public double dpresence = 12;
	public double rpresence = 5;
	// Elytra
	public double celytra = 40;
	public double espeed = 1.0;
	public double eelytra = 10;

	public Crusader(int level, Player p) {
		super(level, name, description, eps, eph, epmax, hpmax, p);
	}

	@Override
	public void addAbility(int j) {
		switch (j) {
		case 1:
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 4) {
				p.getInventory().setItem(1,
						ItemListCrusader.getCrusaderRedRune(
								((double) (int) (cconsecrate * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0),
								econsecrate, ccconsecrate, cmulconsecrate,
								((double) (int) (dminconsecrate * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
								((double) (int) (dmaxconsecrate * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
								strikeconsecrate, dconsecrate));
			} else {
				p.getInventory()
						.setItem(1,
								ItemListCrusader
										.getCrusaderRedRune(
												((double) (int) (cconsecrate * (1.0 - getWeapon().getCooldown())
														* 100.0) / 100.0),
												econsecrate, ccconsecrate, cmulconsecrate,
												((double) (int) (dminconsecrate * 100.0)) / 100.0,
												((double) (int) (dmaxconsecrate * 100.0)) / 100.0, strikeconsecrate,
												dconsecrate));
			}
			break;
		case 2:
			p.getInventory().setItem(2,
					ItemListCrusader.getCrusaderPurpleRune(
							((double) (int) (clinfusion * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0),
							elinfusion, slinfusion, dlinfusion));
			break;
		case 3:
			p.getInventory().setItem(3,
					ItemListCrusader.getCrusaderBlueRune(
							((double) (int) (chradiance * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0),
							ehradiance, cchradiance, cmulhradiance, ((double) (int) (hminhradiance * 100.0)) / 100.0,
							((double) (int) (hmaxhradiance * 100.0)) / 100.0));
			break;
		case 4:
			p.getInventory().setItem(4,
					ItemListCrusader.getCrusaderYellowRune(
							((double) (int) (cpresence * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0),
							epspresence, spresence, dpresence));
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
			if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 4) {
				im.setLore(ItemListCrusader.getCrusaderMain(eastrike, ccastrike, cmulastrike,
						((double) (int) (dminastrike * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxastrike * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, egive,
						countstrike));
			} else {
				im.setLore(ItemListCrusader.getCrusaderMain(eastrike, ccastrike, cmulastrike, dminastrike, dmaxastrike,
						egive, countstrike));
			}
			im.setDisplayName(ChatColor.GREEN + cstrikename);
			is.setItemMeta(im);
			p.getInventory().setItem(0, is);
		}
	}
	@Override
	public ItemStack getMainAbility() {
		ItemStack is = WeaponUtil.generateItemStack(getWeapon(), getName());
		if (is != null) {
			ItemMeta im = is.getItemMeta();
			if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 4) {
				im.setLore(ItemListCrusader.getCrusaderMain(eastrike, ccastrike, cmulastrike,
						((double) (int) (dminastrike * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxastrike * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, egive,
						countstrike));
			} else {
				im.setLore(ItemListCrusader.getCrusaderMain(eastrike, ccastrike, cmulastrike, dminastrike, dmaxastrike,
						egive, countstrike));
			}
			im.setDisplayName(ChatColor.GREEN + cstrikename);
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
			if (getEnergy() >= eastrike) {
				boolean sucess = false;
				if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 4) {
					sucess = SkillUtil.doCrusaderStrike(p, dminastrike * (1.0 + getWeapon().getBoost()),
							dmaxastrike * (1.0 + getWeapon().getBoost()), ccastrike, cmulastrike, rstrike, egive,
							countstrike, rallies);
				} else {
					sucess = SkillUtil.doCrusaderStrike(p, dminastrike, dmaxastrike, ccastrike, cmulastrike, rstrike,
							egive, countstrike, rallies);
				}
				if (sucess) {
					p.getWorld().playSound(p.getLocation(), "paladin.paladinstrike.activation", 1, 1);
					removeEnergy(eastrike);
				}
			}
			break;
		case 1:
			if (getEnergy() >= econsecrate) {
				if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 4) {
					SkillUtil.addConsecrate(p, dminconsecrate * (1.0 + getWeapon().getBoost()),
							dmaxconsecrate * (1.0 + getWeapon().getBoost()), ccconsecrate, cmulconsecrate,
							strikeconsecrate, dconsecrate, rconsecrate);
				} else {
					SkillUtil.addConsecrate(p, dminconsecrate, dmaxconsecrate, ccconsecrate, cmulconsecrate,
							strikeconsecrate, dconsecrate, rconsecrate);
				}
				doCooldown(j);
				removeEnergy(econsecrate);
			}
			break;
		case 2:
			SkillUtil.addLightInfusion(elinfusion, slinfusion, dlinfusion, p);
			doCooldown(j);
			break;
		case 3:
			if (getEnergy() >= ehradiance) {
				SkillUtil.doHolyRadiance(hminhradiance, hmaxhradiance, cchradiance, cmulhradiance, rhradiance, p);
				doCooldown(j);
			}
			break;
		case 4:
			SkillUtil.addInspiringPresense(dpresence, p, rpresence, spresence, epspresence);
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
			is.setAmount((int) Math.round(cconsecrate * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);
			im = p.getInventory().getItem(1).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + consecratename);
			is.setItemMeta(im);
			p.getInventory().setItem(1, is);
			break;
		case 2:
			is.setAmount((int) Math.round(clinfusion * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);
			im = p.getInventory().getItem(2).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + infusionname);
			is.setItemMeta(im);
			p.getInventory().setItem(2, is);
			break;
		case 3:
			is.setAmount((int) Math.round(chradiance * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);
			im = p.getInventory().getItem(3).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + hradiancename);
			is.setItemMeta(im);
			p.getInventory().setItem(3, is);
			break;
		case 4:
			is.setAmount((int) Math.round(cpresence * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);
			im = p.getInventory().getItem(4).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + presencename);
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
		return 0.0;
	}

	@Override
	public ItemStack getAbility(int i) {
		switch (i) {
		case 0:
			return WeaponUtil.generateItemStack(getWeapon(), getName());
		case 1:
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 4) {
				return ItemListCrusader.getCrusaderRedRune(
						((double) (int) (cconsecrate * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), econsecrate,
						ccconsecrate, cmulconsecrate,
						((double) (int) (dminconsecrate * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxconsecrate * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						strikeconsecrate, dconsecrate);
			} else {
				return ItemListCrusader.getCrusaderRedRune(
						((double) (int) (cconsecrate * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), econsecrate,
						ccconsecrate, cmulconsecrate, ((double) (int) (dminconsecrate * 100.0)) / 100.0,
						((double) (int) (dmaxconsecrate * 100.0)) / 100.0, strikeconsecrate, dconsecrate);
			}
		case 2:
			return ItemListCrusader.getCrusaderPurpleRune(
					((double) (int) (clinfusion * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), elinfusion,
					slinfusion, dlinfusion);
		case 3:
			return ItemListCrusader.getCrusaderBlueRune(
					((double) (int) (chradiance * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), ehradiance,
					cchradiance, cmulhradiance, ((double) (int) (hminhradiance * 100.0)) / 100.0,
					((double) (int) (hmaxhradiance * 100.0)) / 100.0);
		case 4:
			return ItemListCrusader.getCrusaderYellowRune(
					((double) (int) (cpresence * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), epspresence,
					spresence, dpresence);
		case 8:
			return ItemListGenerel.getElytraRune(celytra, eelytra, espeed);
		default:
			break;
		}
		return null;
	}
}
