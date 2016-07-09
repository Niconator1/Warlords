package com.warlords.util.type;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.util.SkillUtil;
import com.warlords.util.SpielKlasse;
import com.warlords.util.WeaponUtil;
import com.warlords.util.itemlist.ItemListAvenger;
import com.warlords.util.itemlist.ItemListGenerel;
import com.warlords.util.itemlist.ItemListTest;

public class Test extends SpielKlasse {
	public static String name = "Test";
	private static String description = "";
	private static int eps = 100;
	private static int eph = 100;
	public static int epmax = 1000;
	public static int hpmax = 400000;
	// Lightning
	public static final String lazorname = "FIRIN' MAH LAZOR";
	public double dminlazor = 100;
	public double dmaxlazor = 150;
	public double cclazor = 0.2;
	public double cmullazor = 1.75;
	public double clazor = 30;
	public double dlazor = 4;
	public int elazor = 10;
	public int rlazor = 40;
	// Cat Bolt
	public static final String ocelotname = "Cat Bolt";
	public double dminocelot = 334.8;
	public double dmaxocelot = 433.2;
	public double ccocelot = 0.2;
	public double cmulocelot = 1.75;
	public int eocelot = 35;
	// Consecrate
	public static final String consecratename = "Consecrate";
	public double cconsecrate = 9;
	public int econsecrate = 25;
	public double ccconsecrate = 0.20;
	public double cmulconsecrate = 1.75;
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
	public double rhradiance = 3;
	// Ground Slam
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
			if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 2) {
				p.getInventory().setItem(1,
						ItemListAvenger.getAvengerRedRune(
								((double) (int) (cconsecrate * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0),
								econsecrate, ccconsecrate, cmulconsecrate,
								((double) (int) (dminconsecrate * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
								((double) (int) (dmaxconsecrate * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
								strikeconsecrate, dconsecrate));
			} else {
				p.getInventory()
						.setItem(1,
								ItemListAvenger
										.getAvengerRedRune(
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
					ItemListAvenger.getAvengerPurpleRune(
							((double) (int) (clinfusion * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0),
							elinfusion, slinfusion, dlinfusion));
			break;
		case 3:
			p.getInventory().setItem(3,
					ItemListAvenger.getAvengerBlueRune(
							((double) (int) (chradiance * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0),
							ehradiance, cchradiance, cmulhradiance, ((double) (int) (hminhradiance * 100.0)) / 100.0,
							((double) (int) (hmaxhradiance * 100.0)) / 100.0));
			break;
		case 4:

			break;
		case 6:
			if (getWeapon().getSkill() == 6 && getWeapon().getKlass() == 2) {
				p.getInventory().setItem(6, ItemListTest.getPurpleRune(
						((double) (int) (clazor * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), elazor, cclazor,
						cmullazor, ((double) (int) (dminlazor * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxlazor * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, dlazor));
			} else {
				p.getInventory().setItem(6,
						ItemListTest.getPurpleRune(
								((double) (int) (clazor * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), elazor,
								cclazor, cmullazor, ((double) (int) (dminlazor * 100.0)) / 100.0,
								((double) (int) (dmaxlazor * 100.0)) / 100.0, dlazor));
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
				im.setLore(ItemListTest.getTestMain(eocelot, ccocelot, cmulocelot,
						((double) (int) (dminocelot * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxocelot * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0));
			} else {
				im.setLore(ItemListTest.getTestMain(eocelot, ccocelot, cmulocelot, dminocelot, dmaxocelot));
			}
			im.setDisplayName(ChatColor.GREEN + ocelotname);
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
			if (getEnergy() >= eocelot) {
				if (getWeapon().getSkill() == 0 && getWeapon().getKlass() == 0) {
					SkillUtil.shootCat(p, dminocelot * (1.0 + getWeapon().getBoost()),
							dmaxocelot * (1.0 + getWeapon().getBoost()), ccocelot + ccBoost, cmulocelot + cmulBoost);
				} else {
					SkillUtil.shootCat(p, dminocelot, dmaxocelot, ccocelot + ccBoost, cmulocelot + cmulBoost);
				}

				doCooldown(j);
				removeEnergy(eocelot);
			}
			break;
		case 1:
			if (getEnergy() >= econsecrate) {
				if (getWeapon().getSkill() == 1 && getWeapon().getKlass() == 2) {
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
			// SkillUtil.addAvengerWrath(dwrath,p,rwrath,countwrath);
			// doCooldown(j);
			break;
		case 6:
			if (getEnergy() >= elazor) {
				if (getWeapon().getSkill() == 6 && getWeapon().getKlass() == 2) {
					SkillUtil.addLazor(p, dminlazor * (1.0 + getWeapon().getBoost()),
							dmaxlazor * (1.0 + getWeapon().getBoost()), cclazor, cmullazor, dlazor, rlazor);
				} else {
					SkillUtil.addLazor(p, dminlazor, dmaxlazor, cclazor, cmullazor, dlazor, rlazor);
				}
				doCooldown(j);
				removeEnergy(elazor);
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
			// is.setAmount((int) Math.round(cwrath * (1.0 -
			// getWeapon().getCooldown())));
			// is.setDurability((short) 8);
			// im = p.getInventory().getItem(4).getItemMeta();
			// im.setDisplayName(ChatColor.GRAY + wrathname);
			// is.setItemMeta(im);
			// p.getInventory().setItem(4, is);
			break;
		case 6:
			is.setAmount((int) Math.round(clazor * (1.0 - getWeapon().getCooldown())));
			is.setDurability((short) 8);
			im = p.getInventory().getItem(6).getItemMeta();
			im.setDisplayName(ChatColor.GRAY + lazorname);
			is.setItemMeta(im);
			p.getInventory().setItem(6, is);
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
				return ItemListAvenger.getAvengerRedRune(
						((double) (int) (cconsecrate * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), econsecrate,
						ccconsecrate, cmulconsecrate,
						((double) (int) (dminconsecrate * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxconsecrate * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						strikeconsecrate, dconsecrate);
			} else {
				return ItemListAvenger.getAvengerRedRune(
						((double) (int) (cconsecrate * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), econsecrate,
						ccconsecrate, cmulconsecrate, ((double) (int) (dminconsecrate * 100.0)) / 100.0,
						((double) (int) (dmaxconsecrate * 100.0)) / 100.0, strikeconsecrate, dconsecrate);
			}
		case 2:
			return ItemListAvenger.getAvengerPurpleRune(
					((double) (int) (clinfusion * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), elinfusion,
					slinfusion, dlinfusion);
		case 3:
			return ItemListAvenger.getAvengerBlueRune(
					((double) (int) (chradiance * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), ehradiance,
					cchradiance, cmulhradiance, ((double) (int) (hminhradiance * 100.0)) / 100.0,
					((double) (int) (hmaxhradiance * 100.0)) / 100.0);
		// case 4:
		// return ItemListAvenger.getAvengerYellowRune(
		// ((double) (int) (cwrath * (1.0 - getWeapon().getCooldown()) * 100.0)
		// / 100.0), countwrath,
		// rwrath,epswrath, dwrath);
		case 6:
			if (getWeapon().getSkill() == 6 && getWeapon().getKlass() == 2) {
				return ItemListTest.getPurpleRune(
						((double) (int) (clazor * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), elazor, cclazor,
						cmullazor, ((double) (int) (dminlazor * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0,
						((double) (int) (dmaxlazor * (1.0 + getWeapon().getBoost()) * 100.0)) / 100.0, dlazor);
			} else {
				return ItemListTest.getPurpleRune(
						((double) (int) (clazor * (1.0 - getWeapon().getCooldown()) * 100.0) / 100.0), elazor, cclazor,
						cmullazor, ((double) (int) (dminlazor * 100.0)) / 100.0,
						((double) (int) (dmaxlazor * 100.0)) / 100.0, dlazor);
			}
		case 8:
			return ItemListGenerel.getElytraRune(celytra, eelytra, espeed);
		default:
			break;
		}
		return null;
	}
}
