package com.warlords.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.warlords.main.Warlords;
import com.warlords.util.skills.Elytra;
import com.warlords.util.skills.assassin.Stealth;
import com.warlords.util.skills.assassin.Vanish;
import com.warlords.util.skills.demon.DConsecrate;
import com.warlords.util.skills.demon.DWrath;
import com.warlords.util.skills.demon.ShadowInfusion;
import com.warlords.util.skills.hunter.BloodArrow;
import com.warlords.util.skills.hunter.Companion;
import com.warlords.util.skills.hunter.ElementalArrow;
import com.warlords.util.skills.hunter.ExplosivArrow;
import com.warlords.util.skills.hunter.MarkingArrow;
import com.warlords.util.skills.mage.ArcaneShield;
import com.warlords.util.skills.mage.Fireball;
import com.warlords.util.skills.mage.Flameburst;
import com.warlords.util.skills.mage.Inferno;
import com.warlords.util.skills.mage.TimeWarp;
import com.warlords.util.skills.paladin.Consecrate;
import com.warlords.util.skills.paladin.LightInfusion;
import com.warlords.util.skills.paladin.Presence;
import com.warlords.util.skills.paladin.Wrath;
import com.warlords.util.skills.shaman.Lightningbolt;
import com.warlords.util.skills.test.CatBolt;
import com.warlords.util.skills.test.Lazor;
import com.warlords.util.skills.unique.Sphere;
import com.warlords.util.type.Hunter;
import com.warlords.util.type.Pyromancer;

import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityDestroy;

public class SkillUtil extends UtilMethods {
	public static void shootFireball(Player p, double dmin, double dmax, double critc, double critm) {
		double pitch = ((p.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
		double yaw = ((p.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.sin(pitch) * Math.sin(yaw);
		double z = Math.cos(pitch);
		Vector vector = new Vector(x, z, y).multiply(1.25);
		Arrow f = p.launchProjectile(Arrow.class);
		f.setVelocity(vector);
		f.setShooter(p);
		Warlords.fireball.add(new Fireball(f, vector, dmin, dmax, critc, critm, p, p.getLocation()));
		for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(f.getEntityId());
			((CraftPlayer) p2).getHandle().playerConnection.sendPacket(packet);
		}
		p.getWorld().playSound(p.getLocation(), "mage.fireball.activation", 1, 1);
	}

	public static void shootFlameburst(Player p, double dmin, double dmax, double critc, double critm) {
		double pitch = ((p.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
		double yaw = ((p.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.sin(pitch) * Math.sin(yaw);
		double z = Math.cos(pitch);
		Vector vector = new Vector(x, z, y).multiply(0.95);
		Arrow f = p.launchProjectile(Arrow.class);
		f.setVelocity(vector);
		Warlords.burst.add(new Flameburst(f, vector, dmin, dmax, critc, critm, p, p.getLocation()));
		for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(f.getEntityId());
			((CraftPlayer) p2).getHandle().playerConnection.sendPacket(packet);
		}
		p.getWorld().playSound(p.getLocation(), "mage.fireball.activation", 1, 1);
	}

	public static boolean isFlameburst(Entity entity) {
		for (int i = 0; i < Warlords.burst.size(); i++) {
			if (entity instanceof Arrow) {
				if (Warlords.burst.get(i).getArrow().getUniqueId().compareTo(entity.getUniqueId()) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isFireball(Entity entity) {
		for (int i = 0; i < Warlords.fireball.size(); i++) {
			if (entity instanceof Arrow) {
				if (Warlords.fireball.get(i).getArrow().getUniqueId().compareTo(entity.getUniqueId()) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public static void doFlameburstHit(Projectile entity) {
		for (int i = 0; i < Warlords.burst.size(); i++) {
			if (entity instanceof Arrow) {
				if (Warlords.burst.get(i).getArrow().equals((Arrow) entity)) {
					Flameburst b = Warlords.burst.get(i);
					Arrow a = b.getArrow();
					Location l = a.getLocation();
					a.getWorld().playSound(l, "mage.flameburst.impact", 1, 1);
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						UtilMethods.sendParticlePacket(p, EnumParticle.LAVA, l.getX(), l.getY() + 1.0, l.getZ(), 0f, 0f,
								0f, 0f, 8);
						UtilMethods.sendParticlePacket(p, EnumParticle.EXPLOSION_LARGE, l.getX(), l.getY() + 1.0,
								l.getZ(), 0f, 0f, 0f, 0f, 1);
					}
					double d = ((int) l.distance(b.start())) * 0.01;
					if (PlayerUtil.isAttackingPlayers(b.getShooter())) {
						List<Player> list = a.getWorld().getPlayers();
						for (int j = 0; j < list.size(); j++) {
							if (list.get(j).isDead() == false) {
								if (list.get(j).getLocation().add(0, 1, 0.).distance(a.getLocation()) <= 4.5) {
									Player shooter = b.getShooter();
									WarlordsPlayerAllys al = new WarlordsPlayerAllys(shooter);
									if (!al.hasPlayer(list.get(j))) {
										SpielKlasse sk = Warlords.getKlasse(list.get(j));
										if (sk != null) {
											if (!shooter.equals(list.get(j)) && list.get(j).isDead() == false) {
												double dmg = damage("Flameburst", b.critc() + d, b.critm(), b.dmin(),
														b.dmax(), shooter, sk);
												if (dmg != 0) {
													sk.removeHealth(sk.hptohealth(dmg));
												}
												sendSoundPacket(shooter, "entity.arrow.hit_player",
														shooter.getLocation());
											}
										}
									}
								}
							}
						}
					}
					for (LivingEntity e : a.getWorld().getLivingEntities()) {
						if ((!(e instanceof Player)) && (!(e instanceof ArmorStand))) {
							if (e.isDead() == false) {
								if (e.getLocation().add(0, 1, 0).distance(a.getLocation()) <= 4.5) {
									double dmg = hptohealth(damage(b.critc() + d, b.critm(), b.dmin(), b.dmax(),
											b.getShooter(), "Flameburst"));
									double hp = e.getHealth();
									if (hp < dmg) {
										WeaponUtil.doWeapon(e, b.getShooter());
									}
									setHealth(e, dmg);
									sendSoundPacket(b.getShooter(), "entity.arrow.hit_player",
											b.getShooter().getLocation());
								}
							}
						}
					}
					Warlords.burst.get(i).getArrow().remove();
					Warlords.burst.remove(i);
				}
			}
		}
	}

	public static void doFireballHit(Projectile entity) {
		for (int i = 0; i < Warlords.fireball.size(); i++) {
			if (entity instanceof Arrow) {
				if (Warlords.fireball.get(i).getArrow().equals((Arrow) entity)) {
					Fireball f = Warlords.fireball.get(i);
					Arrow a = f.getArrow();
					Location l = a.getLocation();
					a.getWorld().playSound(l, "mage.fireball.impact", 1, 1);
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						UtilMethods.sendParticlePacket(p, EnumParticle.LAVA, l.getX(), l.getY() + 1.0, l.getZ(), 0f, 0f,
								0f, 0f, 8);
						UtilMethods.sendParticlePacket(p, EnumParticle.EXPLOSION_LARGE, l.getX(), l.getY() + 1.0,
								l.getZ(), 0f, 0f, 0f, 0f, 1);
					}
					if (PlayerUtil.isAttackingPlayers(f.getShooter())) {
						List<Player> list = a.getWorld().getPlayers();
						for (int j = 0; j < list.size(); j++) {
							if (list.get(j).isDead() == false) {
								if (list.get(j).getLocation().distance(a.getLocation()) <= 4.5) {
									Player shooter = f.getShooter();
									WarlordsPlayerAllys al = new WarlordsPlayerAllys(shooter);
									if (!al.hasPlayer(list.get(j))) {
										SpielKlasse sk = Warlords.getKlasse(list.get(j));
										if (sk != null) {
											if (!shooter.equals(list.get(j)) && list.get(j).isDead() == false) {
												double dmg = damage("Fireball", f.critc(), f.critm(), f.dmin(),
														f.dmax(), shooter, sk);
												if (dmg != 0) {
													sk.removeHealth(sk.hptohealth(dmg));
												}
												sendSoundPacket(shooter, "entity.arrow.hit_player",
														shooter.getLocation());
											}
										}
									}
								}
							}
						}
					}
					for (LivingEntity e : a.getWorld().getLivingEntities()) {
						if ((!(e instanceof Player)) && (!(e instanceof ArmorStand))) {
							if (e.isDead() == false) {
								if (e.getLocation().distance(a.getLocation()) <= 4.5) {
									double dmg = hptohealth(damage(f.critc(), f.critm(), f.dmin(), f.dmax(),
											f.getShooter(), "Fireball"));
									double hp = e.getHealth();
									if (hp < dmg) {
										WeaponUtil.doWeapon(e, f.getShooter());
									}
									setHealth(e, dmg);
									sendSoundPacket(f.getShooter(), "entity.arrow.hit_player",
											f.getShooter().getLocation());
								}
							}
						}
					}
					Warlords.fireball.get(i).getArrow().remove();
					Warlords.fireball.remove(i);
				}
			}
		}
	}

	public static void addTimeWarp(Player p) {
		TimeWarp tw = new TimeWarp(p, p.getLocation());
		p.getWorld().playSound(p.getLocation(), "mage.timewarp.activation", 1, 1);
		Warlords.warp.add(tw);
	}

	public static void addArcaneShield(double dur, Player p) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			if (sk instanceof Pyromancer) {
				Pyromancer py = (Pyromancer) sk;
				sk.shield = py.rshield * py.getMaxHP();
			}
			p.getWorld().playSound(p.getLocation(), "mage.arcaneshield.activation", 1.5f, 1);
			Warlords.shield.add(new ArcaneShield(dur, p));
		}
	}

	public static void addInferno(double dur, Player p) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			if (sk instanceof Pyromancer) {
				Pyromancer py = (Pyromancer) sk;
				sk.ccBoost = py.ccinf;
				sk.cmulBoost = py.cmulinf;
			}
			p.getWorld().playSound(p.getLocation(), "mage.inferno.activation", 1, 1);
			Warlords.inferno.add(new Inferno(dur, p));
		}
	}

	public static boolean doShadowstep(Player p, double dmin, double dmax, double critc, double critm) {
		for (LivingEntity le : p.getWorld().getLivingEntities()) {
			if (le.isDead() == false) {
				if (le instanceof Player) {
					if (PlayerUtil.isAttackingPlayers(p)) {
						Player p2 = (Player) le;
						if (!p2.equals(p)) {
							WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
							if (!a.hasPlayer(p2)) {
								double pitch = ((p2.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
								double yaw = ((p2.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
								double x = Math.sin(pitch) * Math.cos(yaw);
								double y = Math.sin(pitch) * Math.sin(yaw);
								if (p2.getLocation().add(0, 1, 0).distance(
										p.getLocation().add(new Vector(x, 0, y).normalize().multiply(-3))) <= 4) {
									SpielKlasse sk = Warlords.getKlasse(p2);
									if (sk != null) {
										Location l2 = p2.getLocation();
										Location target = l2.add(new Vector(x, 0, y).normalize().multiply(-2));
										if (target.getBlock().getType() == Material.AIR) {
											p.teleport(target);
										} else {
											p.teleport(p2);
										}

										double dmg = damage("Shadow Step", critc, critm, dmin, dmax, p, sk);
										if (dmg != 0) {
											sk.removeHealth(sk.hptohealth(dmg));
										}
										sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
										return true;
									}
								}
							}
						}
					}
				} else {
					if (!(le instanceof ArmorStand)) {
						double pitch = ((le.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
						double yaw = ((le.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
						double x = Math.sin(pitch) * Math.cos(yaw);
						double y = Math.sin(pitch) * Math.sin(yaw);
						if (le.getLocation().add(0, 1, 0)
								.distance(p.getLocation().add(new Vector(x, 0, y).normalize().multiply(-3))) <= 4) {
							Location l2 = le.getLocation();
							Location target = l2.add(new Vector(x, 0, y).normalize().multiply(-2));
							if (target.getBlock().getType() == Material.AIR) {
								p.teleport(target);
							} else {
								p.teleport(le);
							}
							double dmg = hptohealth(damage(critc, critm, dmin, dmax, p, "Shadow Step"));
							double hp = le.getHealth();
							if (hp < dmg) {
								WeaponUtil.doWeapon(le, p);
							}
							setHealth(le, dmg);
							sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
							return true;
						}
					}
				}
			}
		}
		p.sendMessage("Enemy out of Range");
		return false;
	}

	public static boolean doBackstap(Player p, double dmin, double dmax, double critc, double critm) {
		for (LivingEntity le : p.getWorld().getLivingEntities()) {
			if (le.isDead() == false) {
				if (le instanceof Player) {
					if (PlayerUtil.isAttackingPlayers(p)) {
						Player p2 = (Player) le;
						if (!p2.equals(p)) {
							WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
							if (!a.hasPlayer(p2)) {
								if (p2.getLocation().add(0, 1, 0).distance(p.getLocation()) <= 4) {
									SpielKlasse sk = Warlords.getKlasse(p2);
									if (sk != null) {
										double pitch = ((p.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
										double yaw = ((p.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
										double x = Math.sin(pitch) * Math.cos(yaw);
										double y = Math.sin(pitch) * Math.sin(yaw);
										double pitch2 = ((p2.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
										double yaw2 = ((p2.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
										double x2 = Math.sin(pitch2) * Math.cos(yaw2);
										double y2 = Math.sin(pitch2) * Math.sin(yaw2);
										Vector richtung = new Vector(x, 0, y).normalize();
										Vector richtung2 = new Vector(x2, 0, y2).normalize();
										double deltx = richtung2.getX() - richtung.getX();
										double deltz = richtung2.getZ() - richtung.getZ();
										double accur = Math.sqrt(deltx * deltx + deltz * deltz);
										if (accur < 0.5) {
											double dmg = damage("Back Stab", critc, critm, dmin, dmax, p, sk);
											if (dmg != 0) {
												sk.removeHealth(sk.hptohealth(dmg));
											}
											sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
											return true;
										}
									}
								}
							}
						}
					}
				} else {
					if (!(le instanceof ArmorStand)) {
						if (le.getLocation().add(0, 1, 0).distance(p.getLocation()) <= 4) {
							double pitch = ((p.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
							double yaw = ((p.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
							double x = Math.sin(pitch) * Math.cos(yaw);
							double y = Math.sin(pitch) * Math.sin(yaw);
							double pitch2 = ((le.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
							double yaw2 = ((le.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
							double x2 = Math.sin(pitch2) * Math.cos(yaw2);
							double y2 = Math.sin(pitch2) * Math.sin(yaw2);
							Vector richtung = new Vector(x, 0, y).normalize();
							Vector richtung2 = new Vector(x2, 0, y2).normalize();
							double deltx = richtung2.getX() - richtung.getX();
							double deltz = richtung2.getZ() - richtung.getZ();
							double accur = Math.sqrt(deltx * deltx + deltz * deltz);
							if (accur < 0.5) {
								double dmg = hptohealth(damage(critc, critm, dmin, dmax, p, "Back Stab"));
								double hp = le.getHealth();
								if (hp < dmg) {
									WeaponUtil.doWeapon(le, p);
								}
								setHealth(le, dmg);
								sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
								return true;
							}
						}
					}
				}
			}
		}
		p.sendMessage("Enemy out of range/Not behind Enemy");
		return false;
	}

	public static void addStealth(int dshield, Player p) {
		Warlords.stealth.add(new Stealth(dshield, p));
	}

	public static void addVanish(double rwarp, int dwarp, Player p) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			sk.speedBoost = sk.speedBoost + rwarp;
			sk.applySpeed();
			Warlords.vanish.add(new Vanish(dwarp, p, rwarp));
			for (Player sp : Bukkit.getServer().getOnlinePlayers()) {
				sp.hidePlayer(p);
			}
		}
	}

	public static void doDeadlyPoison(double poisonMax, double poisonOwn, Player p, LivingEntity le) {
		if (le instanceof Player) {
			SpielKlasse sk = Warlords.getKlasse((Player) le);
			if (sk != null) {
				if (sk.healthtohp() <= poisonMax) {
					double dmg = damage(0, 1, poisonMax, poisonMax, p, "Deadly Poison");
					((Player) le).sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + p.getName()
							+ "'s " + "Deadly Poison" + " hit you for " + ChatColor.RED + Math.round(dmg)
							+ ChatColor.GRAY + " damage.");
					sk.removeHealth(dmg + 1);
					SpielKlasse sk2 = Warlords.getKlasse(p);
					if (sk2 != null) {
						double own = envdamage("Deadly Poison", poisonOwn, sk2);
						sk2.removeHealth(sk2.hptohealth(own));
						sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
					}
				} else {
					p.sendMessage("Enemy had to much health left");
				}
			}
		} else {
			if (healthtohp(le.getHealth()) <= poisonMax) {
				WeaponUtil.doWeapon(le, p);
				setHealth(le, le.getHealth());
				SpielKlasse sk = Warlords.getKlasse(p);
				if (sk != null) {
					double own = envdamage("Deadly Poison", poisonOwn, sk);
					sk.removeHealth(sk.hptohealth(own));
					sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
				}
			} else {
				p.sendMessage("Enemy had to much health left");
			}
		}
	}

	public static void addLazor(Player p, double d, double e, double cclazor, double cmullazor, double dlazor,
			int rlazor) {
		Warlords.lazor.add(new Lazor(p, d, e, cclazor, cmullazor, dlazor, rlazor));
	}

	public static boolean doAvengerStrike(Player p, double d, double e, double f, double g, double r) {
		for (LivingEntity le : p.getWorld().getLivingEntities()) {
			if (le.isDead() == false) {
				if (le instanceof Player) {
					if (PlayerUtil.isAttackingPlayers(p)) {
						Player p2 = (Player) le;
						if (!p2.equals(p)) {
							WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
							if (!a.hasPlayer(p2)) {
								Location m = p.getLocation()
										.add(p.getLocation().getDirection().setY(0).normalize().multiply(0.75));
								if (distance2D(m, le.getLocation()) <= r
										&& Math.abs(m.getY() - le.getLocation().getY()) < 2.0) {
									Consecrate is = null;
									for (Consecrate c : Warlords.consecrate) {
										Location l = c.getLocation();
										l.setY(0);
										Location l2 = p2.getLocation();
										l2.setY(0);
										if (l.distance(l2) <= c.getRad()
												&& p2.getUniqueId().compareTo(c.getP().getUniqueId()) != 0
												&& (p2.getLocation().getY() - c.getLocation().getY()) <= 2
												&& p.getUniqueId().compareTo(c.getP().getUniqueId()) == 0) {
											is = c;
										}
									}
									SpielKlasse sk = Warlords.getKlasse(p2);
									if (sk != null) {
										sk.removeEnergy(3);
										double dmg = 0;
										if (is != null) {
											dmg = damage("Avenger's Strike", f, g, d * (1 + is.getStrike()),
													e * (1 + is.getStrike()), p, sk);
										} else {
											dmg = damage("Avenger's Strike", f, g, d, e, p, sk);
										}
										if (dmg != 0) {
											sk.removeHealth(sk.hptohealth(dmg));
										}
										sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
										Location mid = le.getLocation();
										for (Player p3 : Bukkit.getOnlinePlayers()) {
											UtilMethods.sendParticlePacket(p3, EnumParticle.REDSTONE, mid.getX(),
													mid.getY() + 1.0, mid.getZ(), 0.3f, 0.6f, 0.3f, 0f, 7);
											for (int i = 0; i < 20; i++) {
												double x = Math.random() - 0.5;
												double y = Math.random() * 1.9;
												double z = Math.random() - 0.5;
												UtilMethods.sendParticlePacket(p3, EnumParticle.SPELL_MOB,
														mid.getX() + x, mid.getY() + y, mid.getZ() + z, 1f, 1f, 1f, 1,
														0);
											}
										}
										for (int i = 0; i < Warlords.awrath.size(); i++) {
											if (Warlords.awrath.get(i).getPlayer().equals(p)) {
												LivingEntity[] lea = new LivingEntity[Warlords.awrath.get(i)
														.getCount()];
												lea[0] = le;
												for (int j = 1; j < Warlords.awrath.get(i).getCount(); j++) {
													lea[j] = doAvengerStrike2(lea, p, le.getLocation(), d, e, f, g,
															Warlords.awrath.get(i).getRad());
												}
												doAvengerStrike2(lea, p, le.getLocation(), d, e, f, g,
														Warlords.awrath.get(i).getRad());
											}
										}
										return true;
									}
								}
							}
						}
					}
				} else {
					if (!(le instanceof ArmorStand)) {
						Location m = p.getLocation()
								.add(p.getLocation().getDirection().setY(0).normalize().multiply(0.75));
						if (distance2D(m, le.getLocation()) <= r
								&& Math.abs(m.getY() - le.getLocation().getY()) < 2.0) {
							Consecrate is = null;
							for (Consecrate c : Warlords.consecrate) {
								Location l = c.getLocation();
								l.setY(0);
								Location l2 = le.getLocation();
								l2.setY(0);
								if (l.distance(l2) <= c.getRad()
										&& (le.getLocation().getY() - c.getLocation().getY()) <= 2
										&& p.getUniqueId().compareTo(c.getP().getUniqueId()) == 0) {
									is = c;
								}
							}
							double dmg = 0;
							if (is != null) {
								dmg = hptohealth(damage(f, g, d * (1 + is.getStrike()), e * (1 + is.getStrike()), p,
										"Avenger's Strike"));
							} else {
								dmg = hptohealth(damage(f, g, d, e, p, "Avenger's Strike"));
							}
							sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
							double hp = le.getHealth();
							if (hp < dmg) {
								WeaponUtil.doWeapon(le, p);
							}
							setHealth(le, dmg);
							Location mid = le.getLocation();
							for (Player p2 : Bukkit.getOnlinePlayers()) {
								UtilMethods.sendParticlePacket(p2, EnumParticle.REDSTONE, mid.getX(), mid.getY() + 1.0,
										mid.getZ(), 0.3f, 0.6f, 0.3f, 0f, 7);
								for (int i = 0; i < 20; i++) {
									double x = Math.random() - 0.5;
									double y = Math.random() * 1.9;
									double z = Math.random() - 0.5;
									UtilMethods.sendParticlePacket(p2, EnumParticle.SPELL_MOB, mid.getX() + x,
											mid.getY() + y, mid.getZ() + z, 1f, 1f, 1f, 1, 0);
								}
							}
							for (int i = 0; i < Warlords.awrath.size(); i++) {
								if (Warlords.awrath.get(i).getPlayer().equals(p)) {
									LivingEntity[] lea = new LivingEntity[Warlords.awrath.get(i).getCount()];
									lea[0] = le;
									for (int j = 1; j < Warlords.awrath.get(i).getCount(); j++) {
										lea[j] = doAvengerStrike2(lea, p, le.getLocation(), d, e, f, g,
												Warlords.awrath.get(i).getRad());
									}
									doAvengerStrike2(lea, p, le.getLocation(), d, e, f, g,
											Warlords.awrath.get(i).getRad());
								}
							}
							return true;
						}
					}
				}
			}
		}
		p.sendMessage("Enemy out of range");
		return false;
	}

	private static LivingEntity doAvengerStrike2(LivingEntity[] lea, Player p, Location m, double d, double e, double f,
			double g, double r) {
		for (LivingEntity le : p.getWorld().getLivingEntities()) {
			if (le.isDead() == false) {
				boolean alreadyHit = false;
				for (int i = 0; i < lea.length; i++) {
					if (lea[i] != null) {
						if (lea[i].getUniqueId().compareTo(le.getUniqueId()) == 0) {
							alreadyHit = true;
						}
					}
				}
				if (alreadyHit == false) {
					if (le instanceof Player) {
						if (PlayerUtil.isAttackingPlayers(p)) {
							Player p2 = (Player) le;
							if (!p2.equals(p)) {
								WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
								if (!a.hasPlayer(p2)) {
									if (distance2D(m, le.getLocation()) <= r
											&& Math.abs(m.getY() - le.getLocation().getY()) < 2.0) {
										Consecrate is = null;
										for (Consecrate c : Warlords.consecrate) {
											Location l = c.getLocation();
											l.setY(0);
											Location l2 = p2.getLocation();
											l2.setY(0);
											if (l.distance(l2) <= c.getRad()
													&& p2.getUniqueId().compareTo(c.getP().getUniqueId()) != 0
													&& (p2.getLocation().getY() - c.getLocation().getY()) <= 2
													&& p.getUniqueId().compareTo(c.getP().getUniqueId()) == 0) {
												is = c;
											}
										}
										SpielKlasse sk = Warlords.getKlasse(p2);
										if (sk != null) {
											sk.removeEnergy(3);
											double dmg = 0;
											if (is != null) {
												dmg = damage("Avenger's Strike", f, g, d * (1 + is.getStrike()),
														e * (1 + is.getStrike()), p, sk);
											} else {
												dmg = damage("Avenger's Strike", f, g, d, e, p, sk);
											}
											if (dmg != 0) {
												sk.removeHealth(sk.hptohealth(dmg));
											}
											sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
											Location mid = le.getLocation();
											for (Player p3 : Bukkit.getOnlinePlayers()) {
												UtilMethods.sendParticlePacket(p3, EnumParticle.REDSTONE, mid.getX(),
														mid.getY() + 1.0, mid.getZ(), 0.3f, 0.6f, 0.3f, 0f, 7);
												for (int i = 0; i < 20; i++) {
													double x = Math.random() - 0.5;
													double y = Math.random() * 1.9;
													double z = Math.random() - 0.5;
													UtilMethods.sendParticlePacket(p3, EnumParticle.SPELL_MOB,
															mid.getX() + x, mid.getY() + y, mid.getZ() + z, 1f, 1f, 1f,
															1, 0);
												}
											}
											return le;
										}
									}
								}
							}
						}
					} else {
						if (!(le instanceof ArmorStand)) {
							if (distance2D(m, le.getLocation()) <= r
									&& Math.abs(m.getY() - le.getLocation().getY()) < 2.0) {
								Consecrate is = null;
								for (Consecrate c : Warlords.consecrate) {
									Location l = c.getLocation();
									l.setY(0);
									Location l2 = le.getLocation();
									l2.setY(0);
									if (l.distance(l2) <= c.getRad()
											&& (le.getLocation().getY() - c.getLocation().getY()) <= 2
											&& p.getUniqueId().compareTo(c.getP().getUniqueId()) == 0) {
										is = c;
									}
								}
								double dmg = 0;
								if (is != null) {
									dmg = hptohealth(damage(f, g, d * (1 + is.getStrike()), e * (1 + is.getStrike()), p,
											"Avenger's Strike"));
								} else {
									dmg = hptohealth(damage(f, g, d, e, p, "Avenger's Strike"));
								}
								sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
								double hp = le.getHealth();
								if (hp < dmg) {
									WeaponUtil.doWeapon(le, p);

								}
								setHealth(le, dmg);
								Location mid = le.getLocation();
								for (Player p2 : Bukkit.getOnlinePlayers()) {
									UtilMethods.sendParticlePacket(p2, EnumParticle.REDSTONE, mid.getX(),
											mid.getY() + 1.0, mid.getZ(), 0.3f, 0.6f, 0.3f, 0f, 7);
									for (int i = 0; i < 20; i++) {
										double x = Math.random() - 0.5;
										double y = Math.random() * 1.9;
										double z = Math.random() - 0.5;
										UtilMethods.sendParticlePacket(p2, EnumParticle.SPELL_MOB, mid.getX() + x,
												mid.getY() + y, mid.getZ() + z, 1f, 1f, 1f, 1, 0);
									}
								}
								return le;
							}
						}
					}
				}

			}
		}
		return null;
	}

	public static void addConsecrate(Player p, double dmin, double dmax, double cc, double cmul, double strike,
			double dur, double rad) {
		Location l = p.getLocation();
		l.setY(l.getBlockY());
		for (int i = 0; i < p.getLocation().getY(); i++) {
			l.subtract(0, 1, 0);
			if (!l.getBlock().isEmpty()) {
				l.add(0, 1, 0);
				break;
			}
		}
		Consecrate c = new Consecrate(p, dmin, dmax, cc, cmul, strike, dur, rad, l);
		l.getWorld().playSound(l, "paladin.consecrate.activation", 1, 1);
		Warlords.consecrate.add(c);
	}

	public static void addLightInfusion(int elinfusion, double slinfusion, double dlinfusion, Player p) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			sk.speedBoost = sk.speedBoost + slinfusion;
			sk.applySpeed();
			Warlords.linfusion.add(new LightInfusion(dlinfusion, p, slinfusion));
			sk.addEnergy(elinfusion);
		}
		p.getWorld().playSound(p.getLocation(), "paladin.infusionoflight.activation", 1, 1);

	}

	public static void doHolyRadiance(double hminhradiance, double hmaxhradiance, double cchradiance,
			double cmulhradiance, double rhradiance, Player p) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			if (sk.healthtohp() < sk.getMaxHP()) {
				sk.addHealth(sk.hptohealth(
						heal("Holy Radiance", hminhradiance, hmaxhradiance, cchradiance, cmulhradiance, p, sk)));
			}
		}
		for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
			Location l = p.getLocation();
			for (int i = 0; i < 30; i++) {
				double r = Math.random() * 3 * Math.PI;
				double x = Math.cos(r) * rhradiance;
				double z = Math.sin(r) * rhradiance;
				double y = Math.random() * 2;
				UtilMethods.sendParticlePacket(p2, EnumParticle.SPELL_MOB, l.getX() + x, l.getY() + y, l.getZ() + z, 1f,
						1f, 1f, 1f, 0);
			}
			UtilMethods.sendParticlePacket(p2, EnumParticle.HEART, l.getX(), l.getY() + 2.0, l.getZ(), 0f, 0f, 0f, 0f,
					1);
		}
		p.getLocation().getWorld().playSound(p.getLocation(), "paladin.holyradiance.activation", 1, 1);
		// Armor Stand follow the teammates or Location with addition will
		// follow the teammates
	}

	public static void addAvengerWrath(double dur, Player p, double r, int count,int epsboost) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			sk.epsBoost = sk.epsBoost + epsboost;
			p.getWorld().playSound(p.getLocation(), "paladin.avengerswrath.activation", 1, 1);
			Warlords.awrath.add(new Wrath(dur, p, r, count,epsboost));
		}
	}

	public static void doElementelarrow(Player p, double d, double e, double ccball, double cmulball, boolean explosive,
			boolean mark) {
		double pitch = ((p.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
		double yaw = ((p.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.sin(pitch) * Math.sin(yaw);
		double z = Math.cos(pitch);
		Vector vector = new Vector(x, z, y).multiply(1.25);
		Arrow f = p.launchProjectile(Arrow.class);
		f.setVelocity(vector);
		f.setShooter(p);
		boolean blood = false;
		for (BloodArrow b : Warlords.bloodarrow) {
			if (b.getPlayer().getUniqueId().compareTo(p.getUniqueId()) == 0) {
				blood = true;
			}
		}
		Warlords.elementalarrow
				.add(new ElementalArrow(f, vector, d, e, ccball, cmulball, p, p.getLocation(), explosive, mark, blood));

	}

	public static boolean isElementelarrow(Entity entity) {
		for (int i = 0; i < Warlords.elementalarrow.size(); i++) {
			if (entity instanceof Arrow) {
				if (Warlords.elementalarrow.get(i).getArrow().getUniqueId().compareTo(entity.getUniqueId()) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public static void doElementelarrowHit(Projectile entity) {
		for (int i = 0; i < Warlords.elementalarrow.size(); i++) {
			if (entity instanceof Arrow) {
				if (Warlords.elementalarrow.get(i).getArrow().equals((Arrow) entity)) {
					ElementalArrow f = Warlords.elementalarrow.get(i);
					Arrow a = f.getArrow();
					Location l = a.getLocation();
					if (f.isExplosive()) {
						for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
							UtilMethods.sendParticlePacket(p2, EnumParticle.FIREWORKS_SPARK, l.getX(), l.getY() + 0.3f,
									l.getZ(), 0f, 0f, 0f, 0.05f, 10);
						}
					}
					List<Player> list = a.getWorld().getPlayers();
					for (int j = 0; j < list.size(); j++) {
						if (list.get(j).isDead() == false) {
							if (list.get(j).getLocation().distance(a.getLocation()) <= 2.5) {
								SpielKlasse sk = Warlords.getKlasse(list.get(j));
								if (sk != null) {
									Player shooter = f.getShooter();
									WarlordsPlayerAllys al = new WarlordsPlayerAllys(shooter);
									if (!al.hasPlayer(list.get(j))) {
										if (!shooter.equals(list.get(j)) && list.get(j).isDead() == false) {
											double dmg = damage("Elemental Arrow", f.critc(), f.critm(), f.dmin(),
													f.dmax(), shooter, sk);
											sendSoundPacket(shooter, "entity.arrow.hit_player", shooter.getLocation());
											if (dmg != 0) {
												sk.removeHealth(sk.hptohealth(dmg));
												for (BloodArrow e : Warlords.bloodarrow) {
													if (e.getPlayer().getUniqueId()
															.compareTo(shooter.getUniqueId()) == 0) {
														double heal = dmg * 0.21;
														SpielKlasse sk2 = Warlords.getKlasse(shooter);
														if (sk2 != null) {
															if (sk2.healthtohp() < sk2.getMaxHP()) {
																sk2.addHealth(sk2.hptohealth(heal("Blood Arrow", heal,
																		heal, 0, 1, sk2.p, sk2)));
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					for (LivingEntity e : a.getWorld().getLivingEntities()) {
						Player shooter = f.getShooter();
						if ((!(e instanceof Player)) && (!(e instanceof ArmorStand))) {
							if (e.isDead() == false) {
								if (e.getLocation().distance(a.getLocation()) <= 2.5) {
									boolean comp = false;
									for (Companion e4 : Warlords.companion) {
										if (e4.getPlayer().getUniqueId().compareTo(shooter.getUniqueId()) == 0) {
											if (e.getUniqueId().compareTo(e4.getEndermite().getUniqueId()) == 0) {
												comp = true;
											}
										}
									}
									if (comp == false) {
										double dmg = hptohealth(damage(f.critc(), f.critm(), f.dmin(), f.dmax(),
												f.getShooter(), "Elemental Arrow"));

										double hp = e.getHealth();
										if (hp < dmg) {
											WeaponUtil.doWeapon(e, f.getShooter());
										}
										sendSoundPacket(shooter, "entity.arrow.hit_player", shooter.getLocation());
										setHealth(e, dmg);
										for (BloodArrow e3 : Warlords.bloodarrow) {
											if (e3.getPlayer().getUniqueId().compareTo(shooter.getUniqueId()) == 0) {
												double heal = healthtohp(dmg) * 0.21;
												SpielKlasse sk2 = Warlords.getKlasse(shooter);
												if (sk2 != null) {
													if (sk2.healthtohp() < sk2.getMaxHP()) {
														sk2.addHealth(sk2.hptohealth(
																heal("Blood Arrow", heal, heal, 0, 1, sk2.p, sk2)));
													}
												}
											}
										}
									}
								}
							}
						}
					}
					f.getArrow().remove();
					Warlords.elementalarrow.remove(f);
				}
			}
		}
	}

	public static void addExplosivarrow(Player p, double d, double cc, double cm, double dur) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			if (sk instanceof Hunter) {
				Hunter h = (Hunter) sk;
				h.dmgb = h.dminburst;
				sk.ccBoost = h.cc;
				sk.cmulBoost = h.cm;
			}
			Warlords.explosivarrow.add(new ExplosivArrow(d, p, cc, cm, dur));
		}
	}

	public static void addBloodarrow(Player p, double dur) {
		Warlords.bloodarrow.add(new BloodArrow(p, dur));
	}

	public static void addMarkingarrow(Player p, double dur) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			if (sk instanceof Hunter) {
				Hunter py = (Hunter) sk;
				py.dmgb1 = py.dmgboost;

			}
			Warlords.markingarrow.add(new MarkingArrow(dur, p));
		}
	}

	public static void doCompanion(Player p, double dur) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			Endermite e = (Endermite) p.getWorld().spawnEntity(p.getLocation(), EntityType.ENDERMITE);
			e.setMaxHealth(UtilMethods.hptohealth(800));
			if (sk instanceof Hunter) {
				Hunter h = (Hunter) sk;
				h.dmgb2 = h.companiondmgboost;
			}
			Warlords.companion.add(new Companion(p, 800, e, dur));
		}

	}

	public static boolean doDemonStrike(Player p, double d, double e, double f, double g, double r) {
		for (LivingEntity le : p.getWorld().getLivingEntities()) {
			if (le.isDead() == false) {
				if (le instanceof Player) {
					if (PlayerUtil.isAttackingPlayers(p)) {
						Player p2 = (Player) le;
						if (!p2.equals(p)) {
							WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
							if (!a.hasPlayer(p2)) {
								Location m = p.getLocation()
										.add(p.getLocation().getDirection().setY(0).normalize().multiply(0.75));
								if (distance2D(m, le.getLocation()) <= r
										&& Math.abs(m.getY() - le.getLocation().getY()) < 2.0) {
									DConsecrate is = null;
									for (DConsecrate c : Warlords.dconsecrate) {
										Location l = c.getLocation();
										l.setY(0);
										Location l2 = p2.getLocation();
										l2.setY(0);
										if (l.distance(l2) <= c.getRad()
												&& p2.getUniqueId().compareTo(c.getP().getUniqueId()) != 0
												&& (p2.getLocation().getY() - c.getLocation().getY()) <= 2
												&& p.getUniqueId().compareTo(c.getP().getUniqueId()) == 0) {
											is = c;
										}
									}
									SpielKlasse sk = Warlords.getKlasse(p2);
									if (sk != null) {
										sk.removeEnergy(3);
										double dmg = 0;
										if (is != null) {
											dmg = damage("Demon's Strike", f, g, d * (1 + is.getStrike()),
													e * (1 + is.getStrike()), p, sk);
										} else {
											dmg = damage("Demon's Strike", f, g, d, e, p, sk);
										}
										if (dmg != 0) {
											sk.removeHealth(sk.hptohealth(dmg));
										}
										sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
										Location mid = le.getLocation();
										for (Player p3 : Bukkit.getOnlinePlayers()) {
											UtilMethods.sendParticlePacket(p3, EnumParticle.REDSTONE, mid.getX(),
													mid.getY() + 1.0, mid.getZ(), 0.3f, 0.6f, 0.3f, 0f, 7);
											for (int i = 0; i < 20; i++) {
												double x = Math.random() - 0.5;
												double y = Math.random() * 1.9;
												double z = Math.random() - 0.5;
												UtilMethods.sendParticlePacket(p3, EnumParticle.SPELL_MOB,
														mid.getX() + x, mid.getY() + y, mid.getZ() + z, 0f, 0f, 0f, 0f,
														0);
											}
										}
										for (int i = 0; i < Warlords.dwrath.size(); i++) {
											if (Warlords.dwrath.get(i).getPlayer().equals(p)) {
												LivingEntity[] lea = new LivingEntity[Warlords.dwrath.get(i)
														.getCount()];
												lea[0] = le;
												for (int j = 1; j < Warlords.dwrath.get(i).getCount(); j++) {
													lea[j] = doDemonStrike2(lea, p, le.getLocation(), d, e, f, g,
															Warlords.dwrath.get(i).getRad());
												}
												doDemonStrike2(lea, p, le.getLocation(), d, e, f, g,
														Warlords.dwrath.get(i).getRad());
											}
										}
										return true;
									}
								}
							}
						}
					}
				} else {
					if (!(le instanceof ArmorStand)) {
						Location m = p.getLocation()
								.add(p.getLocation().getDirection().setY(0).normalize().multiply(0.75));
						if (distance2D(m, le.getLocation()) <= r
								&& Math.abs(m.getY() - le.getLocation().getY()) < 2.0) {
							DConsecrate is = null;
							for (DConsecrate c : Warlords.dconsecrate) {
								Location l = c.getLocation();
								l.setY(0);
								Location l2 = le.getLocation();
								l2.setY(0);
								if (l.distance(l2) <= c.getRad()
										&& (le.getLocation().getY() - c.getLocation().getY()) <= 2
										&& p.getUniqueId().compareTo(c.getP().getUniqueId()) == 0) {
									is = c;
								}
							}
							double dmg = 0;
							if (is != null) {
								dmg = hptohealth(damage(f, g, d * (1 + is.getStrike()), e * (1 + is.getStrike()), p,
										"Demon's Strike"));
							} else {
								dmg = hptohealth(damage(f, g, d, e, p, "Demon's Strike"));
							}
							double hp = le.getHealth();
							if (hp < dmg) {
								WeaponUtil.doWeapon(le, p);

							}
							sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
							setHealth(le, dmg);
							Location mid = le.getLocation();
							for (Player p2 : Bukkit.getOnlinePlayers()) {
								UtilMethods.sendParticlePacket(p2, EnumParticle.REDSTONE, mid.getX(), mid.getY() + 1.0,
										mid.getZ(), 0.3f, 0.6f, 0.3f, 0f, 7);
								for (int i = 0; i < 20; i++) {
									double x = Math.random() - 0.5;
									double y = Math.random() * 1.9;
									double z = Math.random() - 0.5;
									UtilMethods.sendParticlePacket(p2, EnumParticle.SPELL_MOB, mid.getX() + x,
											mid.getY() + y, mid.getZ() + z, 0f, 0f, 0f, 0f, 0);
								}
							}
							for (int i = 0; i < Warlords.dwrath.size(); i++) {
								if (Warlords.dwrath.get(i).getPlayer().equals(p)) {
									LivingEntity[] lea = new LivingEntity[Warlords.dwrath.get(i).getCount()];
									lea[0] = le;
									for (int j = 1; j < Warlords.dwrath.get(i).getCount(); j++) {
										lea[j] = doDemonStrike2(lea, p, le.getLocation(), d, e, f, g,
												Warlords.dwrath.get(i).getRad());
									}
									doDemonStrike2(lea, p, le.getLocation(), d, e, f, g,
											Warlords.dwrath.get(i).getRad());
								}
							}
							return true;
						}
					}
				}
			}
		}
		p.sendMessage("Enemy out of range");
		return false;
	}

	private static LivingEntity doDemonStrike2(LivingEntity[] lea, Player p, Location m, double d, double e, double f,
			double g, double r) {
		for (LivingEntity le : p.getWorld().getLivingEntities()) {
			if (le.isDead() == false) {
				boolean alreadyHit = false;
				for (int i = 0; i < lea.length; i++) {
					if (lea[i] != null) {
						if (lea[i].getUniqueId().compareTo(le.getUniqueId()) == 0) {
							alreadyHit = true;
						}
					}
				}
				if (alreadyHit == false) {
					if (le instanceof Player) {
						if (PlayerUtil.isAttackingPlayers(p)) {
							Player p2 = (Player) le;
							if (!p2.equals(p)) {
								WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
								if (!a.hasPlayer(p2)) {
									if (distance2D(m, le.getLocation()) <= r
											&& Math.abs(m.getY() - le.getLocation().getY()) < 2.0) {
										DConsecrate is = null;
										for (DConsecrate c : Warlords.dconsecrate) {
											Location l = c.getLocation();
											l.setY(0);
											Location l2 = p2.getLocation();
											l2.setY(0);
											if (l.distance(l2) <= c.getRad()
													&& p2.getUniqueId().compareTo(c.getP().getUniqueId()) != 0
													&& (p2.getLocation().getY() - c.getLocation().getY()) <= 2
													&& p.getUniqueId().compareTo(c.getP().getUniqueId()) == 0) {
												is = c;
											}
										}
										SpielKlasse sk = Warlords.getKlasse(p2);
										if (sk != null) {
											sk.removeEnergy(3);
											double dmg = 0;
											if (is != null) {
												dmg = damage("Demon's Strike", f, g, d * (1 + is.getStrike()),
														e * (1 + is.getStrike()), p, sk);
											} else {
												dmg = damage("Demon's Strike", f, g, d, e, p, sk);
											}
											if (dmg != 0) {
												sk.removeHealth(sk.hptohealth(dmg));
											}
											sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
											Location mid = le.getLocation();
											for (Player p3 : Bukkit.getOnlinePlayers()) {
												UtilMethods.sendParticlePacket(p3, EnumParticle.REDSTONE, mid.getX(),
														mid.getY() + 1.0, mid.getZ(), 0.3f, 0.6f, 0.3f, 0f, 7);
												for (int i = 0; i < 20; i++) {
													double x = Math.random() - 0.5;
													double y = Math.random() * 1.9;
													double z = Math.random() - 0.5;
													UtilMethods.sendParticlePacket(p3, EnumParticle.SPELL_MOB,
															mid.getX() + x, mid.getY() + y, mid.getZ() + z, 0f, 0f, 0f,
															0f, 0);
												}
											}
											return le;
										}
									}
								}
							}
						}
					} else {
						if (!(le instanceof ArmorStand)) {
							if (distance2D(m, le.getLocation()) <= r
									&& Math.abs(m.getY() - le.getLocation().getY()) < 2.0) {
								DConsecrate is = null;
								for (DConsecrate c : Warlords.dconsecrate) {
									Location l = c.getLocation();
									l.setY(0);
									Location l2 = le.getLocation();
									l2.setY(0);
									if (l.distance(l2) <= c.getRad()
											&& (le.getLocation().getY() - c.getLocation().getY()) <= 2
											&& p.getUniqueId().compareTo(c.getP().getUniqueId()) == 0) {
										is = c;
									}
								}
								double dmg = 0;
								if (is != null) {
									dmg = hptohealth(damage(f, g, d * (1 + is.getStrike()), e * (1 + is.getStrike()), p,
											"Demon's Strike"));
								} else {
									dmg = hptohealth(damage(f, g, d, e, p, "Demon's Strike"));
								}
								double hp = le.getHealth();
								if (hp < dmg) {
									WeaponUtil.doWeapon(le, p);

								}
								sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
								setHealth(le, dmg);
								Location mid = le.getLocation();
								for (Player p2 : Bukkit.getOnlinePlayers()) {
									UtilMethods.sendParticlePacket(p2, EnumParticle.REDSTONE, mid.getX(),
											mid.getY() + 1.0, mid.getZ(), 0.3f, 0.6f, 0.3f, 0f, 7);
									for (int i = 0; i < 20; i++) {
										double x = Math.random() - 0.5;
										double y = Math.random() * 1.9;
										double z = Math.random() - 0.5;
										UtilMethods.sendParticlePacket(p2, EnumParticle.SPELL_MOB, mid.getX() + x,
												mid.getY() + y, mid.getZ() + z, 0f, 0f, 0f, 0f, 0);
									}
								}
								return le;
							}
						}
					}
				}

			}
		}
		return null;
	}

	public static void addDConsecrate(Player p, double dmin, double dmax, double cc, double cmul, double strike,
			double dur, double rad) {
		Location l = p.getLocation();
		l.setY(l.getBlockY());
		for (int i = 0; i < p.getLocation().getY(); i++) {
			l.subtract(0, 1, 0);
			if (!l.getBlock().isEmpty()) {
				l.add(0, 1, 0);
				break;
			}
		}
		DConsecrate c = new DConsecrate(p, dmin, dmax, cc, cmul, strike, dur, rad, l);
		l.getWorld().playSound(l, "paladin.consecrate.activation", 1, 1);
		Warlords.dconsecrate.add(c);
	}

	public static void addShadowInfusion(int elinfusion, double slinfusion, double dlinfusion, Player p) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			sk.speedBoost = sk.speedBoost + slinfusion;
			sk.applySpeed();
			Warlords.sinfusion.add(new ShadowInfusion(dlinfusion, p, slinfusion));
			sk.addEnergy(elinfusion);
		}
		p.getWorld().playSound(p.getLocation(), "paladin.infusionoflight.activation", 1, 1);

	}

	public static void doUnholyRadiance(double hminhradiance, double hmaxhradiance, double cchradiance,
			double cmulhradiance, double rhradiance, Player p) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			if (sk.healthtohp() < sk.getMaxHP()) {
				sk.addHealth(sk.hptohealth(
						heal("Unholy Radiance", hminhradiance, hmaxhradiance, cchradiance, cmulhradiance, p, sk)));
			}
		}
		for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
			Location l = p.getLocation();
			for (int i = 0; i < 30; i++) {
				double r = Math.random() * 3 * Math.PI;
				double x = Math.cos(r) * rhradiance;
				double z = Math.sin(r) * rhradiance;
				double y = Math.random() * 2;
				UtilMethods.sendParticlePacket(p2, EnumParticle.SPELL_MOB, l.getX() + x, l.getY() + y, l.getZ() + z, 1f,
						0f, 0f, 1f, 0);
			}
			UtilMethods.sendParticlePacket(p2, EnumParticle.VILLAGER_ANGRY, l.getX(), l.getY() + 2.0, l.getZ(), 0f, 0f,
					0f, 0f, 1);
		}
		p.getLocation().getWorld().playSound(p.getLocation(), "paladin.holyradiance.activation", 1, 1);
		// Armor Stand follow the teammates or Location with addition will
		// follow the teammates
	}

	public static void addDemonWrath(double dur, Player p, double r, int count,int epsboost) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			sk.epsBoost = sk.epsBoost + epsboost;
			p.getWorld().playSound(p.getLocation(), "paladin.avengerswrath.activation", 1, 1);
			Warlords.dwrath.add(new DWrath(dur, p, r, count,epsboost));
		}
	}

	public static boolean doCrusaderStrike(Player p, double d, double e, double f, double g, double r, int egive,
			int countstrike, double rallies) {
		int count = 0;
		for (LivingEntity le : p.getWorld().getLivingEntities()) {
			if (le.isDead() == false) {
				if (le instanceof Player) {
					if (PlayerUtil.isAttackingPlayers(p)) {
						Player p2 = (Player) le;
						if (!p2.equals(p)) {
							WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
							if (!a.hasPlayer(p2)) {
								Location m = p.getLocation()
										.add(p.getLocation().getDirection().setY(0).normalize().multiply(0.75));
								if (distance2D(m, le.getLocation()) <= r
										&& Math.abs(m.getY() - le.getLocation().getY()) < 2.0) {
									Consecrate is = null;
									for (Consecrate c : Warlords.consecrate) {
										Location l = c.getLocation();
										l.setY(0);
										Location l2 = p2.getLocation();
										l2.setY(0);
										if (l.distance(l2) <= c.getRad()
												&& p2.getUniqueId().compareTo(c.getP().getUniqueId()) != 0
												&& (p2.getLocation().getY() - c.getLocation().getY()) <= 2
												&& p.getUniqueId().compareTo(c.getP().getUniqueId()) == 0) {
											is = c;
										}
									}
									SpielKlasse sk = Warlords.getKlasse(p2);
									if (sk != null) {
										sk.removeEnergy(3);
										double dmg = 0;
										if (is != null) {
											dmg = damage("Crusader's Strike", f, g, d * (1 + is.getStrike()),
													e * (1 + is.getStrike()), p, sk);
										} else {
											dmg = damage("Crusader's Strike", f, g, d, e, p, sk);
										}
										sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
										if (dmg != 0) {
											sk.removeHealth(sk.hptohealth(dmg));
										}
										Location mid = le.getLocation();
										for (Player p3 : Bukkit.getOnlinePlayers()) {
											UtilMethods.sendParticlePacket(p3, EnumParticle.REDSTONE, mid.getX(),
													mid.getY() + 1.0, mid.getZ(), 0.3f, 0.6f, 0.3f, 0f, 7);
											for (int i = 0; i < 20; i++) {
												double x = Math.random() - 0.5;
												double y = Math.random() * 1.9;
												double z = Math.random() - 0.5;
												UtilMethods.sendParticlePacket(p3, EnumParticle.SPELL_MOB,
														mid.getX() + x, mid.getY() + y, mid.getZ() + z, 1f, 1f, 1f, 1,
														0);
											}
										}
										if (a.getAllys() != null) {
											for (UUID id : a.getAllys()) {
												Player p3 = Bukkit.getPlayer(id);
												if (p3 != null) {
													if (p3.isDead() == false) {
														if (p3.getLocation().distance(p.getLocation()) < rallies) {
															SpielKlasse ska = Warlords.getKlasse(p3);
															if (ska != null) {
																if (count < countstrike) {
																	ska.addEnergy(egive);
																	count++;
																}
															}
														}
													}
												}
											}
										}
										return true;
									}
								}
							}
						}
					}
				} else {
					if (!(le instanceof ArmorStand)) {
						Location m = p.getLocation()
								.add(p.getLocation().getDirection().setY(0).normalize().multiply(0.75));
						if (distance2D(m, le.getLocation()) <= r
								&& Math.abs(m.getY() - le.getLocation().getY()) < 2.0) {
							Consecrate is = null;
							for (Consecrate c : Warlords.consecrate) {
								Location l = c.getLocation();
								l.setY(0);
								Location l2 = le.getLocation();
								l2.setY(0);
								if (l.distance(l2) <= c.getRad()
										&& (le.getLocation().getY() - c.getLocation().getY()) <= 2
										&& p.getUniqueId().compareTo(c.getP().getUniqueId()) == 0) {
									is = c;
								}
							}
							double dmg = 0;
							if (is != null) {
								dmg = hptohealth(damage(f, g, d * (1 + is.getStrike()), e * (1 + is.getStrike()), p,
										"Crusader's Strike"));
							} else {
								dmg = hptohealth(damage(f, g, d, e, p, "Crusader's Strike"));
							}
							double hp = le.getHealth();
							if (hp < dmg) {
								WeaponUtil.doWeapon(le, p);

							}
							sendSoundPacket(p, "entity.arrow.hit_player", p.getLocation());
							setHealth(le, dmg);
							Location mid = le.getLocation();
							for (Player p2 : Bukkit.getOnlinePlayers()) {
								UtilMethods.sendParticlePacket(p2, EnumParticle.REDSTONE, mid.getX(), mid.getY() + 1.0,
										mid.getZ(), 0.3f, 0.6f, 0.3f, 0f, 7);
								for (int i = 0; i < 20; i++) {
									double x = Math.random() - 0.5;
									double y = Math.random() * 1.9;
									double z = Math.random() - 0.5;
									UtilMethods.sendParticlePacket(p2, EnumParticle.SPELL_MOB, mid.getX() + x,
											mid.getY() + y, mid.getZ() + z, 1f, 1f, 1f, 1, 0);
								}
							}
							WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
							if (a.getAllys() != null) {
								for (UUID id : a.getAllys()) {
									Player p3 = Bukkit.getPlayer(id);
									if (p3 != null) {
										if (p3.isDead() == false) {
											if (p3.getLocation().distance(p.getLocation()) < rallies) {
												SpielKlasse ska = Warlords.getKlasse(p3);
												if (ska != null) {
													if (count < countstrike) {
														ska.addEnergy(egive);
														count++;
													}
												}
											}
										}
									}
								}
							}
							return true;
						}
					}
				}
			}
		}
		p.sendMessage("Enemy out of range");
		return false;
	}

	public static void addInspiringPresense(double dur, Player p, double r, double spresence, int epspresence) {
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			sk.speedBoost = sk.speedBoost + spresence;
			sk.epsBoost = sk.epsBoost + epspresence;
			sk.applySpeed();
			p.getWorld().playSound(p.getLocation(), "paladin.inspiringpresence.activation", 1, 1);
			ArrayList<Player> list = new ArrayList<Player>();
			WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
			if (a.getAllys() != null) {
				for (UUID id : a.getAllys()) {
					Player p2 = Bukkit.getPlayer(id);
					if (p2 != null) {
						if (p2.isDead() == false) {
							if (p2.getLocation().distance(p.getLocation()) < r) {
								SpielKlasse ska = Warlords.getKlasse(p2);
								if (ska != null) {
									ska.speedBoost = ska.speedBoost + spresence;
									ska.epsBoost = ska.epsBoost + epspresence;
									ska.applySpeed();
									list.add(p2);
								}
							}
						}
					}
				}
			}
			Warlords.presence.add(new Presence(p, list, dur, spresence, epspresence));
		}
	}

	public static void shootLightningBolt(Player p, double dmin, double dmax, double critc, double critm,
			double cooldown, double range) {
		double pitch = ((p.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
		double yaw = ((p.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.sin(pitch) * Math.sin(yaw);
		double z = Math.cos(pitch);
		Vector vector = new Vector(x, z, y).multiply(1.875);
		ArmorStand f = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
		CraftArmorStand a = (CraftArmorStand) f;
		a.getHandle().noclip = true;
		f.setVelocity(vector);
		f.setVisible(false);
		f.setInvulnerable(true);
		f.setHelmet(new ItemStack(Material.SAPLING, 1, (short) 3));
		f.setHeadPose(f.getHeadPose().setX(p.getLocation().getPitch() / 90.0 * 0.5 * Math.PI));
		Warlords.lightningbolt
				.add(new Lightningbolt(f, vector, dmin, dmax, critc, critm, p, p.getLocation(), range, cooldown));
		p.getWorld().playSound(p.getLocation(), "shaman.lightningbolt.activation", 1, 1);
	}

	public static void shootCat(Player p, double dmin, double dmax, double critc, double critm) {
		double pitch = ((p.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
		double yaw = ((p.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.sin(pitch) * Math.sin(yaw);
		double z = Math.cos(pitch);
		Vector vector = new Vector(x, z, y).multiply(1.25);
		Arrow f = p.launchProjectile(Arrow.class);
		f.setVelocity(vector);
		f.setShooter(p);
		Warlords.catbolt.add(new CatBolt(f, vector, dmin, dmax, critc, critm, p, p.getLocation()));
		for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(f.getEntityId());
			((CraftPlayer) p2).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public static boolean isCatBolt(Projectile entity) {
		for (int i = 0; i < Warlords.catbolt.size(); i++) {
			if (entity instanceof Arrow) {
				if (Warlords.catbolt.get(i).getArrow().getUniqueId().compareTo(entity.getUniqueId()) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public static void doCatboltHit(Projectile entity) {
		for (int i = 0; i < Warlords.catbolt.size(); i++) {
			if (entity instanceof Arrow) {
				if (Warlords.catbolt.get(i).getArrow().equals((Arrow) entity)) {
					CatBolt f = Warlords.catbolt.get(i);
					Arrow a = f.getArrow();
					Location l = a.getLocation();
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						UtilMethods.sendParticlePacket(p, EnumParticle.EXPLOSION_LARGE, l.getX(), l.getY() + 1.0,
								l.getZ(), 0f, 0f, 0f, 0f, 1);
					}
					if (PlayerUtil.isAttackingPlayers(f.getShooter())) {
						List<Player> list = a.getWorld().getPlayers();
						for (int j = 0; j < list.size(); j++) {
							if (list.get(j).isDead() == false) {
								if (list.get(j).getLocation().distance(a.getLocation()) <= 4.5) {
									Player shooter = f.getShooter();
									WarlordsPlayerAllys al = new WarlordsPlayerAllys(shooter);
									if (!al.hasPlayer(list.get(j))) {
										SpielKlasse sk = Warlords.getKlasse(list.get(j));
										if (sk != null) {
											if (!shooter.equals(list.get(j)) && list.get(j).isDead() == false) {
												double dmg = damage("Cat Bolt", f.critc(), f.critm(), f.dmin(),
														f.dmax(), shooter, sk);
												if (dmg != 0) {
													sk.removeHealth(sk.hptohealth(dmg));
												}
												sendSoundPacket(shooter, "entity.arrow.hit_player", shooter.getLocation());
											}
										}
									}
								}
							}
						}
					}
					for (LivingEntity e : a.getWorld().getLivingEntities()) {
						if ((!(e instanceof Player)) && (!(e instanceof ArmorStand))) {
							if (e.isDead() == false) {
								if (e.getLocation().distance(a.getLocation()) <= 4.5) {
									double dmg = hptohealth(damage(f.critc(), f.critm(), f.dmin(), f.dmax(),
											f.getShooter(), "Cat Bolt"));
									double hp = e.getHealth();
									if (hp < dmg) {
										WeaponUtil.doWeapon(e, f.getShooter());
									}
									sendSoundPacket(f.getShooter(), "entity.arrow.hit_player", f.getShooter().getLocation());
									setHealth(e, dmg);
								}
							}
						}
					}
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (Warlords.catbolt.get(i).getOcelot() != null) {
							PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(
									Warlords.catbolt.get(i).getOcelot().getBukkitEntity().getEntityId());
							((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
						}
					}
					Warlords.catbolt.get(i).getArrow().remove();
					Warlords.catbolt.remove(i);
				}
			}
		}

	}

	public static void addSphere(Player p, double dmin, double dmax, double cc, double cmul, double d, double r,
			double dmine, double dmaxe, double cce, double cmule, double rade) {
		if (p.getName().equals("NiconatorTM") || p.getName().equals("Zer0TM")) {
			Sphere c = new Sphere(p, dmin, dmax, cc, cmul, d, r, dmine, dmaxe, cce, cmule, rade);
			p.getLocation().getWorld().playSound(p.getLocation(), "mage.firebreath.activation", 1, 1);
			Warlords.sphere.add(c);
		} else {
			p.sendMessage("You are not allowed to use this");
		}
	}

	public static void addElytra(double espeed, double eelytra, Player p) {
		Warlords.elytra.add(new Elytra(espeed, eelytra, p));
	}

	public static void removeAbilitys(Player p) {
		for (int i = 0; i < Warlords.warp.size(); i++) {
			TimeWarp tw = Warlords.warp.get(i);
			if (tw.getPlayer().equals(p)) {
				Warlords.warp.remove(i);
			}
		}
		for (int i = 0; i < Warlords.shield.size(); i++) {
			ArcaneShield tw = Warlords.shield.get(i);
			if (tw.getPlayer().equals(p)) {
				Warlords.shield.remove(i);
			}
		}
		for (int i = 0; i < Warlords.inferno.size(); i++) {
			Inferno tw = Warlords.inferno.get(i);
			if (tw.getPlayer().equals(p)) {
				Warlords.inferno.remove(i);
			}
		}
		for (int i = 0; i < Warlords.stealth.size(); i++) {
			Stealth tw = Warlords.stealth.get(i);
			if (tw.getPlayer().equals(p)) {
				Warlords.stealth.remove(i);
			}
		}
		for (int i = 0; i < Warlords.vanish.size(); i++) {
			Vanish tw = Warlords.vanish.get(i);
			if (tw.getPlayer().equals(p)) {
				for (Player sp : Bukkit.getServer().getOnlinePlayers()) {
					sp.showPlayer(p);
				}
				Warlords.vanish.remove(i);
			}
		}
		for (int i = 0; i < Warlords.elytra.size(); i++) {
			Elytra tw = Warlords.elytra.get(i);
			if (tw.getPlayer().equals(p)) {
				p.getInventory().setChestplate(null);
				Warlords.elytra.remove(i);
			}
		}
		for (int i = 0; i < Warlords.lazor.size(); i++) {
			Lazor tw = Warlords.lazor.get(i);
			if (tw.getShooter().equals(p)) {
				tw.clearList();
				Warlords.lazor.remove(i);
			}
		}
		for (int i = 0; i < Warlords.sphere.size(); i++) {
			Sphere s = Warlords.sphere.get(i);
			if (s.getP().equals(p)) {
				Warlords.sphere.remove(i);
			}
		}
		for (int i = 0; i < Warlords.consecrate.size(); i++) {
			Consecrate s = Warlords.consecrate.get(i);
			if (s.getP().equals(p)) {
				Warlords.consecrate.remove(i);
			}
		}
		for (int i = 0; i < Warlords.linfusion.size(); i++) {
			LightInfusion s = Warlords.linfusion.get(i);
			if (s.getPlayer().equals(p)) {
				Warlords.linfusion.remove(i);
			}
		}
		for (int i = 0; i < Warlords.awrath.size(); i++) {
			Wrath s = Warlords.awrath.get(i);
			if (s.getPlayer().equals(p)) {
				Warlords.awrath.remove(i);
			}
		}
		for (int i = 0; i < Warlords.explosivarrow.size(); i++) {
			ExplosivArrow tw = Warlords.explosivarrow.get(i);
			if (tw.getPlayer().equals(p)) {
				Warlords.explosivarrow.remove(i);
			}
		}
		for (int i = 0; i < Warlords.bloodarrow.size(); i++) {
			BloodArrow tw = Warlords.bloodarrow.get(i);
			if (tw.getPlayer().equals(p)) {
				Warlords.bloodarrow.remove(i);
			}
		}
		for (int i = 0; i < Warlords.markingarrow.size(); i++) {
			MarkingArrow tw = Warlords.markingarrow.get(i);
			if (tw.getPlayer().equals(p)) {
				Warlords.markingarrow.remove(i);
			}
		}
		for (int i = 0; i < Warlords.dconsecrate.size(); i++) {
			DConsecrate s = Warlords.dconsecrate.get(i);
			if (s.getP().equals(p)) {
				Warlords.dconsecrate.remove(i);
			}
		}
		for (int i = 0; i < Warlords.sinfusion.size(); i++) {
			ShadowInfusion s = Warlords.sinfusion.get(i);
			if (s.getPlayer().equals(p)) {
				Warlords.sinfusion.remove(i);
			}
		}
		for (int i = 0; i < Warlords.dwrath.size(); i++) {
			DWrath s = Warlords.dwrath.get(i);
			if (s.getPlayer().equals(p)) {
				Warlords.dwrath.remove(i);
			}
		}
	}

	public static double distance2D(Location m2, Location m1) {
		double dx = m2.getX() - m1.getX();
		double dz = m2.getZ() - m1.getZ();
		return Math.sqrt(dx * dx + dz * dz);
	}

}
