package com.warlords.util;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.warlords.boss.Pontiff;
import com.warlords.main.Warlords;
import com.warlords.util.type.Assassin;
import com.warlords.util.type.Avenger;
import com.warlords.util.type.Crusader;
import com.warlords.util.type.Demon;
import com.warlords.util.type.Hunter;
import com.warlords.util.type.Pyromancer;
import com.warlords.util.type.Test;
import com.warlords.util.type.Thunderlord;

import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.PacketPlayOutCustomSoundEffect;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_10_R1.SoundCategory;

public class UtilMethods {

	public static double damage(double critc, double critm, double min, double max, Player shooter, String type) {
		double randdmg = Math.random() * (max - min) + min;
		double rand = Math.random();
		double dmg = randdmg;
		if (rand < critc) {
			dmg *= critm;
			if (shooter.isOnline()) {
				shooter.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
						+ " did " + ChatColor.RED + ChatColor.BOLD + Math.round(dmg) + ChatColor.GRAY
						+ " critical damage.");
			}
		} else {
			if (shooter.isOnline()) {
				shooter.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
						+ " did " + ChatColor.RED + Math.round(dmg) + ChatColor.GRAY + " damage.");
			}
		}
		return dmg;
	}

	public static double hptohealth(double dmg) {
		return dmg / 200.0;
	}

	public static double envdamage(String reason, double dmg, SpielKlasse victim) {
		dmg *= victim.damagemultiplier;
		if (victim.getShield() > 0) {
			if (victim.getShield() >= dmg) {
				victim.damageShield(dmg);
				dmg = 0;
			} else {
				dmg = dmg - victim.getShield();
				victim.damageShield(victim.getShield());
			}
			if (dmg == 0) {
				doDamageMessageENV(true, dmg, reason, victim.p);
			} else {
				doDamageMessageENV(false, dmg, reason, victim.p);
			}
		} else {
			doDamageMessageENV(false, dmg, reason, victim.p);
		}
		return dmg;
	}

	public static void doDamageMessageENV(boolean absorbed, double dmg, String type, Player victim) {
		if (absorbed == true) {
			victim.sendMessage(
					ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + "You absorbed " + type + " damage.");
		} else {
			victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + "You took " + ChatColor.RED
					+ Math.round(dmg) + ChatColor.GRAY + " " + type + " damage.");
		}
	}

	public static double heal(String reason, double min, double max, double critc, double critm, Player shooter,
			SpielKlasse victim) {
		double randheal = Math.random() * (max - min) + min;
		double rand = Math.random() * 100.0;
		double heal = randheal;
		if (rand < critc * 100.0) {
			heal *= critm;
			if (heal > (victim.getMaxHP() - victim.healthtohp())) {
				heal = victim.getMaxHP() - victim.healthtohp();
				if (heal > 0) {
					doHealMessage(true, reason, shooter, victim.p, heal);
				}
			} else {
				doHealMessage(true, reason, shooter, victim.p, heal);
			}
		} else {
			if (heal > (victim.getMaxHP() - victim.healthtohp())) {
				heal = victim.getMaxHP() - victim.healthtohp();
				if (heal > 0) {
					doHealMessage(false, reason, shooter, victim.p, heal);
				}
			} else {
				doHealMessage(false, reason, shooter, victim.p, heal);
			}
		}
		return heal;
	}

	public static void doHealMessage(boolean crit, String reason, Player healer, Player victim, double heal) {
		if (healer.equals(victim)) {
			if (healer.isOnline()) {
				if (crit == true) {
					healer.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + reason
							+ " healed you for critical " + ChatColor.GREEN + ChatColor.BOLD + Math.round(heal)
							+ ChatColor.GRAY + " health.");
				} else {
					healer.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + reason
							+ " healed you for " + ChatColor.GREEN + Math.round(heal) + ChatColor.GRAY + " health.");
				}
			}
		} else {
			if (crit == true) {
				if (healer.isOnline()) {
					healer.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + reason
							+ " healed " + victim.getName() + " for critical " + ChatColor.GREEN + ChatColor.BOLD
							+ Math.round(heal) + ChatColor.GRAY + " health.");
				}
				victim.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + healer.getName()
						+ "'s " + reason + " healed you for critical " + ChatColor.GREEN + ChatColor.BOLD
						+ Math.round(heal) + ChatColor.GRAY + " health.");
			} else {
				if (healer.isOnline()) {
					healer.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + reason
							+ " healed " + victim.getName() + " for " + ChatColor.GREEN + Math.round(heal)
							+ ChatColor.GRAY + " health.");
				}
				victim.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫" + ChatColor.GRAY + healer.getName()
						+ "'s " + reason + " healed you for " + ChatColor.GREEN + Math.round(heal) + ChatColor.GRAY
						+ " health.");
			}
		}
	}

	public static double damage(String reason, double critc, double critm, double min, double max, Player shooter,
			SpielKlasse victim) {
		double randdmg = Math.random() * (max - min) + min;
		double rand = Math.random() * 100.0;
		double dmg = randdmg;
		dmg *= victim.damagemultiplier;
		if (rand < critc * 100.0) {
			dmg *= critm;
			if (victim.getShield() > 0) {
				if (victim.getShield() >= dmg) {
					victim.damageShield(dmg);
					dmg = 0;
				} else {
					dmg = dmg - victim.getShield();
					victim.damageShield(victim.getShield());
				}
				if (dmg == 0) {
					doDamageMessage(true, true, dmg, reason, shooter, victim.p);
				} else {
					doDamageMessage(false, true, dmg, reason, shooter, victim.p);
				}
			} else {
				doDamageMessage(false, true, dmg, reason, shooter, victim.p);
			}
		} else {
			if (victim.getShield() > 0) {
				if (victim.getShield() >= dmg) {
					victim.damageShield(dmg);
					dmg = 0;
				} else {
					dmg = dmg - victim.getShield();
					victim.damageShield(victim.getShield());
				}
				if (dmg == 0) {
					doDamageMessage(true, false, dmg, reason, shooter, victim.p);
				} else {
					doDamageMessage(false, false, dmg, reason, shooter, victim.p);
				}
			} else {
				doDamageMessage(false, false, dmg, reason, shooter, victim.p);
			}
		}
		return dmg;
	}

	public static void doDamageMessage(boolean absorbed, boolean crit, double dmg, String type, Player shooter,
			Player victim) {
		if (absorbed == true) {
			if (shooter.isOnline()) {
				shooter.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
						+ " was absorbed by " + victim.getName() + ".");
			}
			victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + "You absorbed "
					+ shooter.getName() + "'s " + type + " hit.");
		} else {
			if (crit == true) {
				if (shooter.isOnline()) {
					shooter.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
							+ " hit " + victim.getName() + " for critical " + ChatColor.RED + ChatColor.BOLD
							+ Math.round(dmg) + ChatColor.GRAY + " damage.");
				}
				victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + shooter.getName()
						+ "'s " + type + " hit you for critical " + ChatColor.RED + ChatColor.BOLD + Math.round(dmg)
						+ ChatColor.GRAY + " damage.");
			} else {
				if (shooter.isOnline()) {
					shooter.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
							+ " hit " + victim.getName() + " for " + ChatColor.RED + Math.round(dmg) + ChatColor.GRAY
							+ " damage.");
				}
				victim.sendMessage(
						ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + shooter.getName() + "'s " + type
								+ " hit you for " + ChatColor.RED + Math.round(dmg) + ChatColor.GRAY + " damage.");
			}
		}
	}

	public static double melee(Weapon weapon, Player shooter) {
		if (weapon != null) {
			double randdmg = Math.random() * (weapon.getDmax() - weapon.getDmin()) + weapon.getDmin();
			double rand = Math.random() * 100.0;
			double dmg = randdmg;
			if (rand < weapon.getCc() * 100.0) {
				dmg *= weapon.getCm();
				if (shooter.isOnline()) {
					shooter.sendMessage(
							ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "You did " + ChatColor.RED
									+ ChatColor.BOLD + Math.round(dmg) + ChatColor.GRAY + " critical melee damage.");
				}
			} else {
				if (shooter.isOnline()) {
					shooter.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "You did "
							+ ChatColor.RED + Math.round(dmg) + ChatColor.GRAY + " melee damage.");
				}
			}
			return dmg;
		}
		return 0;
	}

	public static final Vector rotateAroundAxisX(Vector v, double angle) {
		double y, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		y = v.getY() * cos - v.getZ() * sin;
		z = v.getY() * sin + v.getZ() * cos;
		return v.setY(y).setZ(z);
	}

	public static final Vector rotateAroundAxisY(Vector v, double angle) {
		double x, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		x = v.getX() * cos + v.getZ() * sin;
		z = v.getX() * -sin + v.getZ() * cos;
		return v.setX(x).setZ(z);
	}

	public static double melee(Weapon weapon, Player shooter, SpielKlasse victim) {
		if (weapon != null) {
			double randdmg = Math.random() * (weapon.getDmax() - weapon.getDmin()) + weapon.getDmin();
			double rand = Math.random() * 100.0;
			double dmg = randdmg;
			dmg *= victim.damagemultiplier;
			if (rand < weapon.getCc() * 100.0) {
				dmg *= weapon.getCm();
				if (victim.getShield() > 0) {
					if (victim.getShield() >= dmg) {
						victim.damageShield(dmg);
						dmg = 0;
					} else {
						dmg = dmg - victim.getShield();
						victim.damageShield(victim.getShield());
					}
					if (dmg == 0) {
						doDamageMessageMelee(true, true, dmg, "melee", shooter, victim.p);
					} else {
						doDamageMessageMelee(false, true, dmg, "melee", shooter, victim.p);
					}
				} else {
					doDamageMessageMelee(false, true, dmg, "melee", shooter, victim.p);
				}
			} else {
				if (victim.getShield() > 0) {
					if (victim.getShield() >= dmg) {
						victim.damageShield(dmg);
						dmg = 0;
					} else {
						dmg = dmg - victim.getShield();
						victim.damageShield(victim.getShield());
					}
					if (dmg == 0) {
						doDamageMessageMelee(true, false, dmg, "melee hit", shooter, victim.p);
					} else {
						doDamageMessageMelee(false, false, dmg, "melee hit", shooter, victim.p);
					}
				} else {
					doDamageMessageMelee(false, false, dmg, "melee hit", shooter, victim.p);
				}
			}
			return dmg;
		}
		return 0;
	}

	public static void doDamageMessageMelee(boolean absorbed, boolean crit, double dmg, String type, Player shooter,
			Player victim) {
		if (absorbed == true) {
			if (shooter.isOnline()) {
				shooter.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
						+ " was absorbed by " + victim.getName() + ".");
			}
			victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + "You absorbed "
					+ shooter.getName() + "'s " + type + " hit.");
		} else {
			if (crit == true) {
				if (shooter.isOnline()) {
					shooter.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "You hit "
							+ victim.getName() + " for critical " + ChatColor.RED + ChatColor.BOLD + Math.round(dmg)
							+ ChatColor.GRAY + " melee damage.");
				}
				victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + shooter.getName()
						+ " hit you for critical " + ChatColor.RED + ChatColor.BOLD + Math.round(dmg) + ChatColor.GRAY
						+ " melee damage.");
			} else {
				if (shooter.isOnline()) {
					shooter.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "You hit "
							+ victim.getName() + " for " + ChatColor.RED + Math.round(dmg) + ChatColor.GRAY
							+ " melee damage.");
				}
				victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + shooter.getName()
						+ " hit you for " + ChatColor.RED + Math.round(dmg) + ChatColor.GRAY + " melee damage.");
			}
		}

	}

	public static void giveItems(SpielKlasse py, boolean drop) {
		WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/players/" + py.p.getUniqueId());
		if (wp != null) {
			if (!wp.getKlasse().equals("")) {
				if (wp.getMode() == true) {
					for (int i = 0; i < 9; i++) {
						if (py.p.getInventory().getItem(i) != null) {
							py.p.getInventory().setItem(i, null);
						}
					}
				}
			}
		}
		if (drop == true) {
			for (int i = 0; i < 9; i++) {
				if (py.p.getInventory().getItem(i) != null) {
					py.p.getWorld().dropItemNaturally(py.p.getLocation(), py.p.getInventory().getItem(i));
					py.p.getInventory().setItem(i, null);
				}
			}
		}
		py.applySpeed();
		py.p.getInventory().setItem(0, WeaponUtil.generateItemStack(py.getWeapon(), py.getName()));
		for (int i = 1; i < 9; i++) {
			py.addAbility(i);
		}
	}

	public static double healthtohp(double health) {
		return health * 200.0;
	}

	public static void setHealth(LivingEntity le, double hp) {
		for (Pontiff p : Warlords.pontiff) {
			if (p.getSkel().getUniqueId().compareTo(le.getUniqueId()) == 0) {
				if (!p.removeHealth((int) Math.round(healthtohp(hp)))) {

				}
				return;
			}
		}
		CraftLivingEntity cle = ((CraftLivingEntity) le);
		cle.getHandle().world.broadcastEntityEffect(cle.getHandle(), (byte) 2);
		if (le.getHealth() < hp) {
			le.setHealth(0);
		} else {
			le.setHealth(le.getHealth() - hp);
		}
	}

	public static void chooseClass(Player p, SpielKlasse py) {
		for (int i = 0; i < Warlords.player.size(); i++) {
			SpielKlasse sk = Warlords.player.get(i);
			if (sk.p.equals(p)) {
				Warlords.player.remove(sk);
			}
		}
		WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/players/" + p.getUniqueId());
		if (wp != null) {
			UtilMethods.giveItems(py, true);
			wp.setMode(true);
			p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(10);
			py.applySpeed();
			wp.setKlasse(py.getName());
			PlayerUtil.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(), "/players/" + p.getUniqueId());
		}
	}

	public static void doRegiveClass(Player p) {
		WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/players/" + p.getUniqueId());
		if (wp == null) {
			wp = new WarlordsPlayer("" + p.getUniqueId());
			ArrayList<Weapon> w = new ArrayList<Weapon>();
			w.add(WeaponUtil.generateRandomWeapon());
			FileUtilMethods.save(w, Warlords.getPlugin(Warlords.class).getDataFolder(), "/weapons/" + p.getUniqueId());
			PlayerUtil.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(), "/players/" + p.getUniqueId());
		} else {
			SpielKlasse py = null;
			if (wp.getKlasse().equals("Pyromancer")) {
				py = new Pyromancer(90, p);
			} else if (wp.getKlasse().equals("Assassin")) {
				py = new Assassin(90, p);
			} else if (wp.getKlasse().equals("Test")) {
				py = new Test(90, p);
			} else if (wp.getKlasse().equals("Avenger")) {
				py = new Avenger(90, p);
			} else if (wp.getKlasse().equals("Demon")) {
				py = new Demon(90, p);
			} else if (wp.getKlasse().equals("Hunter")) {
				py = new Hunter(90, p);
			} else if (wp.getKlasse().equals("Crusader")) {
				py = new Crusader(90, p);
			} else if (wp.getKlasse().equals("Thunderlord")) {
				py = new Thunderlord(90, p);
			}
			if (py != null) {
				p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(20);
				Warlords.player.add(py);
				if (wp.getMode() == true) {
					py.p.getInventory().setItem(0, WeaponUtil.generateItemStack(py.getWeapon(), py.getName()));
					for (int i = 1; i < 9; i++) {
						py.addAbility(i);
					}
				}
				py.applySpeed();
			}
		}
	}

	public static void sendParticlePacket(Player p, EnumParticle type, double x, double y, double z, float vx, float vy,
			float vz, float v, int count) {
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, true, (float) x, (float) y,
				(float) z, vx, vy, vz, v, count, null);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public static void sendSoundPacket(Player p, String sound, Location l) {
		PacketPlayOutCustomSoundEffect packet = new PacketPlayOutCustomSoundEffect(sound, SoundCategory.MASTER,
				l.getX(), l.getY(), l.getZ(), 1f, 1f);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
}
