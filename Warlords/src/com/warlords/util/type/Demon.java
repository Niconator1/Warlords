package com.warlords.util.type;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.util.SkillUtil;
import com.warlords.util.SpielKlasse;
import com.warlords.util.WeaponUtil;
import com.warlords.util.itemlist.ItemListDemon;
import com.warlords.util.itemlist.ItemListGenerel;

public class Demon extends SpielKlasse {
	public static String name = "Demon";
	private static String description = "";
	private static int eps = 10;
	private static int eph = 10;
	public static int epmax = 320;
	public static int hpmax = 10000;
	// Demon's Strike
	public static final String astrikename = "Demon's Strike";
	public double dminastrike = 423.62;
	public double dmaxastrike = 572.3;
	public double ccastrike = 0.25;
	public double cmulastrike = 1.85;
	public int eastrike = 45;
	public int etake = 3;
	public double rstrike = 3;
	// Consecrate
	public static final String consecratename = "Cursed Ground";
	public double cconsecrate = 9;
	public int econsecrate = 25;
	public double ccconsecrate = 0.20;
	public double cmulconsecrate = 2.0;
	public double dminconsecrate = 145.2;
	public double dmaxconsecrate = 195.8;
	public double strikeconsecrate = 0.15;
	public double dconsecrate = 5.0;
	public double rconsecrate = 5.5;
	// Light Infusion
	public static final String infusionname = "Shadow Infusion";
	public double clinfusion = 18;
	public int elinfusion = 60;
	public double slinfusion = 0.3;
	public double dlinfusion = 8;
	// Holy Radiance
	public static final String hradiancename = "Unholy Radiance";
	public double chradiance = 22.5;
	public int ehradiance = 10;
	public double cchradiance = 0.15;
	public double cmulhradiance = 1.75;
	public double hminhradiance = 582;
	public double hmaxhradiance = 760;
	public double rhradiance = 6;
	// Demon's Wrath
	public static final String wrathname = "Demon's Wrath";
	public double cwrath = 60;// 76.5
	public int countwrath = 2;
	public int rwrath = 5;
	public int epswrath = 10;
	public double dwrath = 12;
	// Sphere
	public static final String spherename = "Demonic Sphere";
	public double csphere = 60.0;// 36
	public int esphere = 25;
	public double ccsphere = 0.5;// 0.75
	public double cmulsphere = 2.0;// 2.0
	public double dminsphere = 150.0;// 200
	public double dmaxsphere = 170.0;// 500
	public double dsphere = 15.0;
	public double rsphere = 7.0;
	public double ccesphere = 0.20;
	public double cmulesphere = 1.5;
	public double dminesphere = 500.0;
	public double dmaxesphere = 750.0;
	public double resphere = 3.5;
	// Elytra
	public double celytra = 40;
	public double espeed = 1.0;
	public double eelytra = 20;

	public Demon(int level, Player p) {
		super(level, name, description, eps, eph, epmax, hpmax, p);
	}

	@Override
	public void addAbility(int j) {
		switch (j) {
		case 1:
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 2) {
				p.getInventory().setItem(1,
						ItemListDemon.getDemonRedRune(
								((double) (int) (cconsecrate * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0),
								econsecrate, ccconsecrate, cmulconsecrate,
								((double) (int) (dminconsecrate * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
								((double) (int) (dmaxconsecrate * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
								strikeconsecrate, dconsecrate));
			} else {
				p.getInventory()
						.setItem(1,
								ItemListDemon
										.getDemonRedRune(
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
					ItemListDemon.getDemonPurpleRune(
							((double) (int) (clinfusion * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0),
							elinfusion, slinfusion, dlinfusion));
			break;
		case 3:
			p.getInventory().setItem(3,
					ItemListDemon.getDemonBlueRune(
							((double) (int) (chradiance * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0),
							ehradiance, cchradiance, cmulhradiance, ((double) (int) (hminhradiance * 100.0)) / 100.0,
							((double) (int) (hmaxhradiance * 100.0)) / 100.0));
			break;
		case 4:
			p.getInventory().setItem(4,
					ItemListDemon.getDemonYellowRune(
							((double) (int) (cwrath * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), countwrath,
							rwrath, epswrath, dwrath));
			break;
		case 5:
			if (getWeapon().getSkill() == 5 && getWeapon().getKlass() == 2) {
				p.getInventory().setItem(5, ItemListDemon.getSpecialRune(
						((double) (int) (csphere * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), esphere,
						ccsphere, cmulsphere,
						((double) (int) (dminsphere * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxsphere * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, dsphere));
			} else {
				p.getInventory().setItem(5,
						ItemListDemon.getSpecialRune(
								((double) (int) (csphere * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), esphere,
								ccsphere, cmulsphere, ((double) (int) (dminsphere * 100.0)) / 100.0,
								((double) (int) (dmaxsphere * 100.0)) / 100.0, dsphere));
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
			if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 2) {
				im.setLore(ItemListDemon.getDemonMain(eastrike, ccastrike, cmulastrike,
						((double) (int) (dminastrike * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxastrike * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0));
			} else {
				im.setLore(ItemListDemon.getDemonMain(eastrike, ccastrike, cmulastrike, dminastrike, dmaxastrike));
			}
			im.setDisplayName(ChatColor.GREEN + "Demon's Strike");
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
		switch (j) {
		case 0:
			if (getEnergy() >= eastrike) {
				boolean sucess = false;
				if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 2) {
					sucess = SkillUtil.doDemonStrike(p, dminastrike * (1.0 + getWeapon().getBoost()),
							dmaxastrike * (1.0 + getWeapon().getBoost()), ccastrike, cmulastrike, rstrike);
				} else {
					sucess = SkillUtil.doDemonStrike(p, dminastrike, dmaxastrike, ccastrike, cmulastrike, rstrike);
				}
				if (sucess) {
					p.getWorld().playSound(p.getLocation(), "paladin.paladinstrike.activation", 1, 1);
					removeEnergy(eastrike);
				}
			}
			break;
		case 1:
			if (getEnergy() >= econsecrate) {
				if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 2) {
					SkillUtil.addDConsecrate(p, dminconsecrate * (1.0 + getWeapon().getBoost()),
							dmaxconsecrate * (1.0 + getWeapon().getBoost()), ccconsecrate, cmulconsecrate,
							strikeconsecrate, dconsecrate, rconsecrate);
				} else {
					SkillUtil.addDConsecrate(p, dminconsecrate, dmaxconsecrate, ccconsecrate, cmulconsecrate,
							strikeconsecrate, dconsecrate, rconsecrate);
				}
				doCooldown(j);
				removeEnergy(econsecrate);
			}
			break;
		case 2:
			SkillUtil.addShadowInfusion(elinfusion, slinfusion, dlinfusion, p);
			doCooldown(j);
			break;
		case 3:
			if (getEnergy() >= ehradiance) {
				SkillUtil.doUnholyRadiance(hminhradiance, hmaxhradiance, cchradiance, cmulhradiance, rhradiance, p);
				doCooldown(j);
			}
			break;
		case 4:
			SkillUtil.addDemonWrath(dwrath, p, rwrath, countwrath);
			doCooldown(j);
			break;
		case 5:
			if (getEnergy() >= esphere) {
				if (getWeapon().getSkill() == 5 && getWeapon().getKlass() == 2) {
					SkillUtil.addSphere(p, dminsphere * (1.0 + getWeapon().getBoost()),
							dmaxsphere * (1.0 + getWeapon().getBoost()), ccsphere, cmulsphere, dsphere, rsphere,
							dminesphere * (1.0 + getWeapon().getBoost()), dmaxesphere * (1.0 + getWeapon().getBoost()),
							ccesphere, cmulesphere, resphere);
				} else {
					SkillUtil.addSphere(p, dminsphere, dmaxsphere, ccsphere, cmulsphere, dsphere, rsphere, dminesphere,
							dmaxesphere, ccesphere, cmulesphere, resphere);
				}
				doCooldown(j);
				removeEnergy(esphere);
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
			is.setAmount((int) Math.round(cwrath * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);
			im = p.getInventory().getItem(4).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + wrathname);
			is.setItemMeta(im);
			p.getInventory().setItem(4, is);
			break;
		case 5:
			is.setAmount((int) Math.round(csphere * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);// 15
			im = p.getInventory().getItem(5).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + spherename);
			is.setItemMeta(im);
			p.getInventory().setItem(5, is);
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
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 2) {
				return ItemListDemon.getDemonRedRune(
						((double) (int) (cconsecrate * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), econsecrate,
						ccconsecrate, cmulconsecrate,
						((double) (int) (dminconsecrate * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxconsecrate * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						strikeconsecrate, dconsecrate);
			} else {
				return ItemListDemon.getDemonRedRune(
						((double) (int) (cconsecrate * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), econsecrate,
						ccconsecrate, cmulconsecrate, ((double) (int) (dminconsecrate * 100.0)) / 100.0,
						((double) (int) (dmaxconsecrate * 100.0)) / 100.0, strikeconsecrate, dconsecrate);
			}
		case 2:
			return ItemListDemon.getDemonPurpleRune(
					((double) (int) (clinfusion * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), elinfusion,
					slinfusion, dlinfusion);
		case 3:
			return ItemListDemon.getDemonBlueRune(
					((double) (int) (chradiance * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), ehradiance,
					cchradiance, cmulhradiance, ((double) (int) (hminhradiance * 100.0)) / 100.0,
					((double) (int) (hmaxhradiance * 100.0)) / 100.0);
		case 4:
			return ItemListDemon.getDemonYellowRune(
					((double) (int) (cwrath * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), countwrath, rwrath,
					epswrath, dwrath);
		case 5:
			if (getWeapon().getSkill() == 5 && getWeapon().getKlass() == 2) {
				return ItemListDemon.getSpecialRune(
						((double) (int) (csphere * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), esphere,
						ccsphere, cmulsphere,
						((double) (int) (dminsphere * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxsphere * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, dsphere);
			} else {
				return ItemListDemon.getSpecialRune(
						((double) (int) (csphere * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), esphere,
						ccsphere, cmulsphere, ((double) (int) (dminsphere * 100.0)) / 100.0,
						((double) (int) (dmaxsphere * 100.0)) / 100.0, dsphere);
			}
		case 8:
			return ItemListGenerel.getElytraRune(celytra, eelytra, espeed);
		default:
			break;
		}
		return null;
	}
}
