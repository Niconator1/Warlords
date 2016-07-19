package com.warlords.main;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.util.Vector;

import com.warlords.boss.Pontiff;
import com.warlords.util.Confirmation;
import com.warlords.util.FileUtilMethods;
import com.warlords.util.PlayerUtil;
import com.warlords.util.SkillUtil;
import com.warlords.util.SpielKlasse;
import com.warlords.util.UtilMethods;
import com.warlords.util.WarlordsPlayer;
import com.warlords.util.WarlordsPlayerAllys;
import com.warlords.util.Weapon;
import com.warlords.util.WeaponUtil;
import com.warlords.util.skills.Elytra;
import com.warlords.util.skills.assassin.Stealth;
import com.warlords.util.skills.assassin.Vanish;
import com.warlords.util.skills.demon.DConsecrate;
import com.warlords.util.skills.demon.DWrath;
import com.warlords.util.skills.demon.ShadowInfusion;
import com.warlords.util.skills.demon.UnholyRadiance;
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
import com.warlords.util.skills.paladin.HolyRadiance;
import com.warlords.util.skills.paladin.LightInfusion;
import com.warlords.util.skills.paladin.Presence;
import com.warlords.util.skills.paladin.Wrath;
import com.warlords.util.skills.shaman.ChainLightning;
import com.warlords.util.skills.shaman.Lightningbolt;
import com.warlords.util.skills.test.CatBolt;
import com.warlords.util.skills.unique.Sphere;
import com.warlords.util.type.Assassin;
import com.warlords.util.type.Avenger;
import com.warlords.util.type.Crusader;
import com.warlords.util.type.Demon;
import com.warlords.util.type.Hunter;
import com.warlords.util.type.Pyromancer;
import com.warlords.util.type.Test;
import com.warlords.util.type.Thunderlord;

import net.minecraft.server.v1_10_R1.BossBattle;
import net.minecraft.server.v1_10_R1.BossBattleServer;
import net.minecraft.server.v1_10_R1.EntityOcelot;
import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutBoss;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_10_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_10_R1.WorldServer;

public class Warlords extends JavaPlugin {
	public static ArrayList<Fireball> fireball = new ArrayList<Fireball>();
	public static ArrayList<Flameburst> burst = new ArrayList<Flameburst>();
	public static ArrayList<TimeWarp> warp = new ArrayList<TimeWarp>();
	public static ArrayList<ArcaneShield> shield = new ArrayList<ArcaneShield>();
	public static ArrayList<Inferno> inferno = new ArrayList<Inferno>();
	public static ArrayList<SpielKlasse> player = new ArrayList<SpielKlasse>();
	public static ArrayList<Stealth> stealth = new ArrayList<Stealth>();
	public static ArrayList<Vanish> vanish = new ArrayList<Vanish>();
	public static ArrayList<Consecrate> consecrate = new ArrayList<Consecrate>();
	public static ArrayList<LightInfusion> linfusion = new ArrayList<LightInfusion>();
	public static ArrayList<HolyRadiance> hradiance = new ArrayList<HolyRadiance>();
	public static ArrayList<Wrath> awrath = new ArrayList<Wrath>();
	public static ArrayList<ElementalArrow> elementalarrow = new ArrayList<ElementalArrow>();
	public static ArrayList<ExplosivArrow> explosivarrow = new ArrayList<ExplosivArrow>();
	public static ArrayList<BloodArrow> bloodarrow = new ArrayList<BloodArrow>();
	public static ArrayList<MarkingArrow> markingarrow = new ArrayList<MarkingArrow>();
	public static ArrayList<Companion> companion = new ArrayList<Companion>();
	public static ArrayList<DConsecrate> dconsecrate = new ArrayList<DConsecrate>();
	public static ArrayList<ShadowInfusion> sinfusion = new ArrayList<ShadowInfusion>();
	public static ArrayList<UnholyRadiance> uhradiance = new ArrayList<UnholyRadiance>();
	public static ArrayList<DWrath> dwrath = new ArrayList<DWrath>();
	public static ArrayList<Presence> presence = new ArrayList<Presence>();
	public static ArrayList<Lightningbolt> lightningbolt = new ArrayList<Lightningbolt>();
	public static ArrayList<ChainLightning> clightning = new ArrayList<ChainLightning>();
	// Unique
	public static ArrayList<Sphere> sphere = new ArrayList<Sphere>();
	public static ArrayList<CatBolt> catbolt = new ArrayList<CatBolt>();
	public static Objective obj;
	public static ArrayList<Elytra> elytra = new ArrayList<Elytra>();
	public static ArrayList<Confirmation> confirmation = new ArrayList<Confirmation>();
	public static ArrayList<Pontiff> pontiff = new ArrayList<Pontiff>();

	public void onEnable() {
		this.getLogger().info("Warlords");
		getServer().getPluginManager().registerEvents(new EventManager(), this);
		loopsGeneral();
		loopsPyro();
		loopsAssassin();
		loopsPaladin();
		loopsDemon();
		loopsHunter();
		loopsThunderlord();
		loopsPontiff();
		loopsSpecial();
		for (Player p : Bukkit.getOnlinePlayers()) {
			UtilMethods.doRegiveClass(p);
		}
	}

	public void onDisable() {
		for (int i = 0; i < pontiff.size(); i++) {
			PacketPlayOutBoss destroy = new PacketPlayOutBoss(PacketPlayOutBoss.Action.REMOVE,
					pontiff.get(i).getBossServer());
			for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
				((CraftPlayer) p2).getHandle().playerConnection.sendPacket(destroy);
			}
			pontiff.get(i).getSkel().remove();
		}
		for (int i = 0; i < fireball.size(); i++) {
			fireball.get(i).getArrow().remove();
		}
		for (int i = 0; i < burst.size(); i++) {
			burst.get(i).getArrow().remove();
		}
		for (int i = 0; i < elementalarrow.size(); i++) {
			elementalarrow.get(i).getArrow().remove();
		}
		for (int i = 0; i < companion.size(); i++) {
			companion.get(i).getEndermite().remove();
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
			p.setWalkSpeed(0.2f);
		}
		for (int i = 0; i < hradiance.size(); i++) {
			hradiance.get(i).getStand().remove();
		}
		for (int i = 0; i < uhradiance.size(); i++) {
			uhradiance.get(i).getStand().remove();
		}
		for (int i = 0; i < catbolt.size(); i++) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if (Warlords.catbolt.get(i).getOcelot() != null) {
					PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(
							Warlords.catbolt.get(i).getOcelot().getBukkitEntity().getEntityId());
					((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
				}
			}
			catbolt.get(i).getArrow().remove();
		}
		for (int i = 0; i < lightningbolt.size(); i++) {
			lightningbolt.get(i).getStand().remove();
		}
		for (int i = 0; i < clightning.size(); i++) {
			for (ArmorStand a : clightning.get(i).getStand()) {
				a.remove();
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("weapons")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.weapons")) {
					Player ziel = p;
					if (args.length > 0) {
						for (Player sp : Bukkit.getOnlinePlayers()) {
							if (sp.getName().equals(args[0])) {
								ziel = sp;
							}
						}
					}
					p.openInventory(WeaponUtil.getWeaponInventory(ziel));
					return true;
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("generateWeapon")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.cheat")) {
					Weapon w;
					Player ziel = p;
					if (args.length > 0) {
						w = WeaponUtil.generateRandomWeapon(Integer.valueOf(args[0]));
					} else {
						w = WeaponUtil.generateRandomWeapon(100);
					}
					if (args.length > 1) {
						for (Player sp : Bukkit.getOnlinePlayers()) {
							if (sp.getName().equals(args[1])) {
								ziel = sp;
							}
						}
					}
					ArrayList<Weapon> list = FileUtilMethods.load(getDataFolder(), "/weapons/" + ziel.getUniqueId());
					if (list != null) {
						if (list.size() < 54) {
							list.add(w);
						} else {
							p.sendMessage("Weapon inventory already full");
							return false;
						}
					} else {
						list = new ArrayList<Weapon>();
						list.add(w);
					}
					FileUtilMethods.save(list, getDataFolder(), "/weapons/" + ziel.getUniqueId());
					return true;
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("ally")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.choose")) {
					if (args.length > 1) {
						String command = args[0];
						if (command.equals("add")) {
							Player ziel = null;
							for (Player sp : Bukkit.getOnlinePlayers()) {
								if (sp.getName().equals(args[1])) {
									ziel = sp;
								}
							}
							if (ziel != null) {
								if (ziel.getUniqueId().compareTo(p.getUniqueId()) != 0) {
									WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
									if (a.getAllys() != null) {
										if (!a.hasPlayer(ziel)) {
											a.addAlly(ziel.getUniqueId());
											a.saveAllys();
											p.sendMessage("You added " + ziel.getDisplayName() + " to your ally list");
											return true;
										} else {
											p.sendMessage(ziel.getDisplayName() + " is already your ally");
										}
									} else {
										ArrayList<UUID> list = new ArrayList<UUID>();
										list.add(ziel.getUniqueId());
										a.setAllys(list);
										a.saveAllys();
										p.sendMessage("You added " + ziel.getDisplayName() + " to your ally list");
										return true;
									}
								} else {
									p.sendMessage("You can not add yourself to your ally list");
								}
							} else {
								p.sendMessage("This player is not online/does not exist");
							}
						} else if (command.equals("remove")) {
							WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
							if (a.getAllys() != null) {
								for (UUID id : a.getAllys()) {
									OfflinePlayer p3 = Bukkit.getOfflinePlayer(id);
									if (p3 != null) {
										if (p3.getName().equals(args[1])) {
											a.removeAlly(id);
											a.saveAllys();
											p.sendMessage("You removed " + p3.getName() + " from your ally list");
											return true;
										}
									} else {
										p.sendMessage("This player does not exist");
									}
								}
							} else {
								p.sendMessage(args[1] + " is not on your ally list");

							}
						} else if (command.equals("list")) {
							int page = 1;
							page = Integer.parseInt(args[1]);
							if (page > 0) {
								int min = (page - 1) * 10;
								int max = page * 10;
								WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
								ArrayList<UUID> allies = a.getAllys();
								if (a.getAllys() != null) {
									if (allies.size() < max) {
										max = allies.size();
									}
									p.sendMessage("Allies (Page " + page + " of "
											+ ((int) (allies.size() / 10.0 + 0.5) + 1) + " )");
									for (int i = min; i < max; i++) {
										OfflinePlayer p3 = Bukkit.getOfflinePlayer(allies.get(i));
										p.sendMessage((i + 1) + ": " + p3.getName());
									}
								} else {
									p.sendMessage("You have no allies yet");

								}
							} else {
								p.sendMessage("Page number must be higher than 0");
							}
						} else {
							p.sendMessage("Usage: /ally add/remove/list Player/Page");
						}
					} else {
						p.sendMessage("Usage: /ally add/remove/list Player/Page");
					}
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("pyro")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.choose")) {
					p.sendMessage("Pyromancer choosen");
					Pyromancer py = new Pyromancer(90, p);
					UtilMethods.chooseClass(p, py);
					player.add(py);
					return true;
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("assassin")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.choose")) {
					p.sendMessage("Assassin choosen");
					Assassin py = new Assassin(90, p);
					UtilMethods.chooseClass(p, py);
					player.add(py);
					return true;
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("test")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.choose")) {
					p.sendMessage("Test Class choosen");
					Test py = new Test(90, p);
					UtilMethods.chooseClass(p, py);
					player.add(py);
					return true;
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("avenger")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.choose")) {
					p.sendMessage("Avenger choosen");
					Avenger py = new Avenger(90, p);
					UtilMethods.chooseClass(p, py);
					player.add(py);
					return true;
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("demon")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.getName().equals("NiconatorTM") || p.getName().equals("Zer0TM")) {
					if (p.hasPermission("warlords.choose")) {
						p.sendMessage("Demon choosen");
						Demon py = new Demon(90, p);
						UtilMethods.chooseClass(p, py);
						player.add(py);
						return true;
					}
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("hunter")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.choose")) {
					p.sendMessage("Hunter choosen");
					Hunter py = new Hunter(90, p);
					UtilMethods.chooseClass(p, py);
					player.add(py);
					return true;
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("crusader")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.choose")) {
					p.sendMessage("Crusader choosen");
					Crusader py = new Crusader(90, p);
					UtilMethods.chooseClass(p, py);
					player.add(py);
					return true;
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("thunderlord")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.choose")) {
					p.sendMessage("Thunderlord choosen");
					Thunderlord py = new Thunderlord(90, p);
					UtilMethods.chooseClass(p, py);
					player.add(py);
					return true;
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("removeclass")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.choose")) {
					p.sendMessage("You are a normal person now");
					SpielKlasse sk = getKlasse(p);
					if (sk != null) {
						WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
								"/players/" + p.getUniqueId());
						if (wp != null) {
							wp.setKlasse("");
							if (wp.getMode() == true) {
								for (int i = 0; i < 9; i++) {
									p.getInventory().setItem(i, null);
								}
							}
							wp.setMode(false);
							sk.removeHealthScale();
							sk.removeEnergy(sk.getEnergy());
							SkillUtil.removeAbilitys(p);
							p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
							p.setWalkSpeed(0.2f);
							player.remove(sk);
							PlayerUtil.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
									"/players/" + p.getUniqueId());
						}
					}
					return true;
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("togglemode")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.choose")) {
					SpielKlasse sk = getKlasse(p);
					if (sk != null) {
						WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
								"/players/" + p.getUniqueId());
						if (wp != null) {
							if (wp.getMode() == false) {
								wp.setMode(true);
								UtilMethods.giveItems(sk, true);
								p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(10);
								p.sendMessage("Enabled Abilities");
							} else {
								wp.setMode(false);
								for (int i = 0; i < 9; i++) {
									p.getInventory().setItem(i, null);
								}
								p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
								p.sendMessage("Disabled Abilities");
							}
							PlayerUtil.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
									"/players/" + p.getUniqueId());
						}
					}
					return true;
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("toggleplayerattack")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("warlords.choose")) {
					SpielKlasse sk = getKlasse(p);
					if (sk != null) {
						WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
								"/players/" + p.getUniqueId());
						if (wp != null) {
							if (wp.getPlayerAttackMode() == false) {
								wp.setPlayerAttackMode(true);
								p.sendMessage("Enabled attack of players");
							} else {
								wp.setPlayerAttackMode(false);
								p.sendMessage("Disabled attack of players");
							}
							PlayerUtil.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
									"/players/" + p.getUniqueId());
						}
					}
					return true;
				}
			} else {
				sender.sendMessage("You have to be a player");
			}
		}
		if (cmd.getName().equalsIgnoreCase("pontiff")) {
			if (sender instanceof BlockCommandSender) {
				Pontiff p = new Pontiff(((BlockCommandSender) sender).getBlock().getLocation());
				pontiff.add(p);
			} else {
				sender.sendMessage("You have to be a command block");
			}
		}
		return false;
	}

	private void loopsGeneral() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Elytra
				for (int i = 0; i < elytra.size(); i++) {
					Elytra e = elytra.get(i);
					Player p = e.getPlayer();
					SpielKlasse sk = getKlasse(p);
					if (sk != null) {
						double pitch = ((p.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
						double yaw = ((p.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
						double x = Math.sin(pitch) * Math.cos(yaw);
						double y = Math.sin(pitch) * Math.sin(yaw);
						double z = Math.cos(pitch);
						Vector vector = new Vector(x, z, y).normalize();
						if (sk.getEnergy() > 1) {
							if (vector.getY() > 0) {
								if (!p.isSneaking()) {
									p.setVelocity(vector.multiply(e.getSpeed()));
									if (!p.isGliding()) {
										p.setGliding(true);
									}
								} else {
									p.setVelocity(new Vector(0, 0.1, 0));
								}
							} else {
								if (p.isSneaking()) {
									p.setVelocity(new Vector(0, 0.1, 0));
								} else {
									if (!p.isGliding()) {
										p.getInventory().setChestplate(null);
										sk.doCooldown(8);
										Warlords.elytra.remove(i);
									}
								}
							}
							if (e.increaseBuf()) {
								sk.removeEnergy(1);
							}
						} else {
							if (!p.isGliding()) {
								p.getInventory().setChestplate(null);
								sk.doCooldown(8);
								Warlords.elytra.remove(i);
							}
						}
					}
				}
			}
		}, 0, 1);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// EPS and Health Display
				for (int i = 0; i < player.size(); i++) {
					SpielKlasse sp = player.get(i);
					if (sp.p.getHealthScale() != 40) {
						sp.setHealthScale();
					}
					boolean get = true;
					for (int j = 0; j < elytra.size(); j++) {
						if (elytra.get(j).getPlayer().equals(sp.p)) {
							get = false;
						}
					}
					if (isUsingVanish(sp.p)) {
						get = false;
					}
					if (get) {
						sp.addEnergy(sp.getEPS() / 10.0);
					}
					sp.showHealth();
				}
			}
		}, 0, 2);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Mob Health
				for (World w : Bukkit.getServer().getWorlds()) {
					for (LivingEntity e : w.getLivingEntities()) {
						if (e.getCustomName() == null) {
							e.setCustomName((int) (UtilMethods.healthtohp(e.getHealth())) + " " + ChatColor.RED + "❤");
						} else {
							if (e.getCustomName().endsWith("❤")) {
								e.setCustomName(
										(int) (UtilMethods.healthtohp(e.getHealth())) + " " + ChatColor.RED + "❤");
							}
						}
					}
				}
				// Player Health
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (obj == null) {
						if (Bukkit.getServer().getScoreboardManager().getMainScoreboard()
								.getObjective("leben") != null) {
							obj = Bukkit.getServer().getScoreboardManager().getMainScoreboard().getObjective("leben");
						} else {
							obj = Bukkit.getServer().getScoreboardManager().getMainScoreboard()
									.registerNewObjective("leben", "dummy");
						}
						obj.setDisplayName(ChatColor.RED + "❤");
						obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
					}
					SpielKlasse sk = getKlasse(p);
					if (sk != null) {
						obj.getScore(p.getName()).setScore((int) (sk.healthtohp()));
					} else {
						obj.getScore(p.getName()).setScore((int) (UtilMethods.healthtohp(p.getHealth())));
					}
				}
			}
		}, 0, 10);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Cooldown
				for (int i = 0; i < player.size(); i++) {
					SpielKlasse sp = player.get(i);
					Player p = sp.p;
					for (int j = 0; j < 9; j++) {
						WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
								"/players/" + p.getUniqueId());
						if (wp != null) {
							if (wp.getMode() == true) {
								ItemStack is = p.getInventory().getItem(j);
								if (is != null) {
									if (is.getType() == Material.INK_SACK) {
										if (is.getDurability() == 8) {
											if (is.getAmount() > 1) {
												is.setAmount(is.getAmount() - 1);
											} else {
												sp.addAbility(j);
											}
										} else if (is.getDurability() == 15) {
											if (is.getAmount() > 1) {
												is.setAmount(is.getAmount() - 1);
											} else {
												is.setDurability((short) 8);
												is.setAmount(59);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}, 0, 20);
	}

	private void loopsPyro() {
		// Projectile Gravitation disabling
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (int i = 0; i < fireball.size(); i++) {
					fireball.get(i).getArrow().setVelocity(fireball.get(i).getVector());
				}
				for (int i = 0; i < burst.size(); i++) {
					burst.get(i).getArrow().setVelocity(burst.get(i).getVector());
				}
			}
		}, 0, 1);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Fireball
				for (int i = 0; i < fireball.size(); i++) {
					Location l = fireball.get(i).getArrow().getLocation();
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						UtilMethods.sendParticlePacket(p, EnumParticle.SMOKE_NORMAL, l.getX(), l.getY(), l.getZ(), 0.2f,
								0.2f, 0.2f, 0f, 10);
						UtilMethods.sendParticlePacket(p, EnumParticle.FLAME, l.getX(), l.getY(), l.getZ(), 0.2f, 0.2f,
								0.2f, 0f, 5);
					}
					for (LivingEntity le : l.getWorld().getLivingEntities()) {
						if (!(le instanceof ArmorStand)) {
							if (le.getUniqueId().compareTo(fireball.get(i).getShooter().getUniqueId()) != 0) {
								if (fireball.get(i).getArrow().getLocation()
										.distance(le.getLocation().add(0, 1, 0)) <= 1.25) {
									fireball.get(i).directHit();
									SkillUtil.doFireballHit(fireball.get(i).getArrow());
									break;
								}
							}
						}
					}
				}
				// Flameburst
				for (int i = 0; i < burst.size(); i++) {
					Location l = burst.get(i).getArrow().getLocation();
					double angle = l.distance(burst.get(i).start()) * 0.2 * Math.PI;
					Vector v = new Vector(Math.cos(angle) * 0.6, 0, Math.sin(angle) * 0.6);
					v = UtilMethods.rotateAroundAxisX(v, -((l.getPitch() + 90) / 180.0 * Math.PI));
					v = UtilMethods.rotateAroundAxisY(v, (l.getYaw()) * Math.PI / 180);
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						UtilMethods.sendParticlePacket(p, EnumParticle.FLAME, l.getX() + v.getX(), l.getY() + v.getY(),
								l.getZ() + v.getZ(), 0f, 0f, 0f, 0f, 1);
						UtilMethods.sendParticlePacket(p, EnumParticle.FLAME, l.getX() - v.getX(), l.getY() - v.getY(),
								l.getZ() - v.getZ(), 0f, 0f, 0f, 0f, 1);
						UtilMethods.sendParticlePacket(p, EnumParticle.LAVA, l.getX(), l.getY(), l.getZ(), 0f, 0f, 0f,
								0f, 1);
						if (l.distance(burst.get(i).start()) > 2) {
							UtilMethods.sendParticlePacket(p, EnumParticle.DRIP_LAVA, l.getX(), l.getY(), l.getZ(), 0f,
									0f, 0f, 0f, 1);
						}
					}
					for (LivingEntity le : l.getWorld().getLivingEntities()) {
						if (!(le instanceof ArmorStand)) {
							if (le.getUniqueId().compareTo(burst.get(i).getShooter().getUniqueId()) != 0) {
								if (burst.get(i).getArrow().getLocation()
										.distance(le.getLocation().add(0, 1, 0)) <= 2.5) {
									SkillUtil.doFlameburstHit(burst.get(i).getArrow());
									break;
								}
							}
						}
					}
				}
				// Arcane Shield
				for (int i = 0; i < shield.size(); i++) {
					Location l = shield.get(i).getPlayer().getLocation();
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						UtilMethods.sendParticlePacket(p, EnumParticle.ENCHANTMENT_TABLE, l.getX(), l.getY(), l.getZ(),
								0.3f, 0.6f, 0.3f, 0f, 4);
						UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_WITCH, l.getX(), l.getY() + 1.0, l.getZ(),
								0.3f, 0.6f, 0.3f, 0f, 1);
						UtilMethods.sendParticlePacket(p, EnumParticle.FIREWORKS_SPARK, l.getX(), l.getY() + 1.0,
								l.getZ(), 0.3f, 0.6f, 0.3f, 0f, 2);
					}
				}
			}
		}, 0, 1);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Time Warp
				for (int i = 0; i < warp.size(); i++) {
					TimeWarp tw = warp.get(i);
					Location l = tw.getLocation();
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						UtilMethods.sendParticlePacket(p, EnumParticle.CLOUD, l.getX(), l.getY(), l.getZ(), 0.5f, 0f,
								0.5f, 0f, 100);
						for (int j = 0; j < warp.get(i).getPositions().size(); j++) {
							Location l1 = warp.get(i).getPositions().get(j);
							UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_WITCH, l1.getX(), l1.getY() - 0.1,
									l1.getZ(), 0.1f, 0.1f, 0.1f, 0f, 3);
						}
					}
					if (tw.getPositions().size() > 8) {
						tw.getPlayer().teleport(tw.getLocation());
						tw.getPlayer().getWorld().playSound(tw.getPlayer().getLocation(), "mage.timewarp.teleport", 1,
								1);
						SpielKlasse sk = getKlasse(tw.getPlayer());
						if (sk != null) {
							if (sk.healthtohp() < sk.getMaxHP()) {
								UtilMethods.heal("Time Warp", sk.getHeal() * sk.getMaxHP(),
										sk.getHeal() * sk.getMaxHP(), 0, 1, sk.p, sk);
								sk.addHealth(sk.hptohealth(sk.getHeal() * sk.getMaxHP()));
							}
						}
						warp.remove(tw);
					} else {
						tw.addLocation(tw.getPlayer().getLocation());
					}
				}
				// Arcane Shield
				for (int i = 0; i < shield.size(); i++) {
					ArcaneShield as = shield.get(i);
					as.decreaseDuration();
					if (as.getDuration() <= 0) {
						SpielKlasse sk = getKlasse(as.getPlayer());
						if (sk != null) {
							sk.shield = 0;
							shield.remove(as);
						}
					}
				}
				// Inferno
				for (int i = 0; i < inferno.size(); i++) {
					Inferno in = inferno.get(i);
					Location l = in.getPlayer().getLocation();
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						UtilMethods.sendParticlePacket(p, EnumParticle.FLAME, l.getX(), l.getY() + 1.0, l.getZ(), 0.35f,
								0.55f, 0.35f, 0f, 40);
					}
					in.decreaseDuration();
					if (in.getDuration() <= 0) {
						SpielKlasse sk = getKlasse(in.getPlayer());
						if (sk != null) {
							sk.cmulBoost = 0;
							sk.ccBoost = 0;
							inferno.remove(in);
						}
					}
				}
			}
		}, 0, 10);
	}

	private void loopsAssassin() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Vanish
				for (int i = 0; i < vanish.size(); i++) {
					Vanish in = vanish.get(i);
					in.decreaseDuration();
					if (in.getDuration() <= 0) {
						SpielKlasse sk = getKlasse(in.getPlayer());
						if (sk != null) {
							sk.speedBoost = sk.speedBoost - in.getBoost();
							sk.applySpeed();
							for (Player other : getServer().getOnlinePlayers()) {
								other.showPlayer(in.getPlayer());
							}
							vanish.remove(in);
						}

					}
				}
				// Stealth
				for (int i = 0; i < stealth.size(); i++) {
					Stealth as = stealth.get(i);
					as.decreaseDuration();
					if (as.getDuration() <= 0) {
						SpielKlasse sk = getKlasse(as.getPlayer());
						if (sk != null) {
							stealth.remove(as);
						}
					}
				}
			}
		}, 0, 10);
	}

	private void loopsPaladin() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Consecrate
				for (int i = 0; i < consecrate.size(); i++) {
					Consecrate c = consecrate.get(i);
					Location mid = c.getLocation();
					if (c.getDur() > 1) {
						for (int j = 0; j < 24 * c.getRad(); j++) {
							double x = Math.cos(j * Math.PI / 12 * c.getRad()) * c.getRad();
							double z = Math.sin(j * Math.PI / 12 * c.getRad()) * c.getRad();
							double y = 0;
							Location l = new Location(mid.getWorld(), mid.getX() + x, mid.getY() + 0.1, mid.getZ() + z);
							if (l.getBlock().isEmpty()) {
								if (l.add(0, -0.5, 0).getBlock().isEmpty()) {
									if (!l.add(0, -1.5, 0).getBlock().isEmpty()) {
										y = -1;
									} else {
										y = -mid.getBlockY();
									}
								}
							} else {
								if (l.add(0, 1, 0).getBlock().isEmpty()) {
									y = 1;
								}
							}
							for (Player p : Bukkit.getServer().getOnlinePlayers()) {
								if (p.equals(c.getP())) {
									UtilMethods.sendParticlePacket(p, EnumParticle.VILLAGER_HAPPY, mid.getX() + x,
											mid.getY() + y, mid.getZ() + z, 0, 0, 0, 0, 1);
								} else {
									UtilMethods.sendParticlePacket(p, EnumParticle.REDSTONE, mid.getX() + x,
											mid.getY() + y, mid.getZ() + z, 0, 0, 0, 0, 1);
								}
							}
						}
					}
					for (LivingEntity le : mid.getWorld().getLivingEntities()) {
						if (le.isDead() == false) {
							if (le instanceof Player) {
								if (PlayerUtil.isAttackingPlayers(c.getP())) {
									Player p2 = (Player) le;
									if (!p2.equals(c.getP())) {
										WarlordsPlayerAllys a = new WarlordsPlayerAllys(c.getP());
										if (!a.hasPlayer(p2)) {
											if (p2.getLocation().distance(mid) <= c.getRad()) {
												SpielKlasse sk = Warlords.getKlasse(p2);
												if (sk != null) {
													double dmg = UtilMethods.damage("Consecrate", c.getCc(),
															c.getCmul(), c.getDmin(), c.getDmax(), c.getP(), sk);
													if (dmg != 0) {
														sk.removeHealth(sk.hptohealth(dmg));
													}
													UtilMethods.sendSoundPacket(c.getP(), "entity.arrow.hit_player",
															c.getP().getLocation());
												}
											}
										}
									}
								}
							} else {
								if (!(le instanceof ArmorStand)) {
									if (le.getLocation().distance(mid) <= c.getRad()) {
										double dmg = UtilMethods.hptohealth(UtilMethods.damage(c.getCc(), c.getCmul(),
												c.getDmin(), c.getDmax(), c.getP(), "Consecrate"));
										double hp = le.getHealth();
										if (hp < dmg) {
											WeaponUtil.doWeapon(le, c.getP());
										}
										UtilMethods.sendSoundPacket(c.getP(), "entity.arrow.hit_player",
												c.getP().getLocation());
										SkillUtil.setHealth(le, dmg);
									}
								}
							}
						}
					}
					c.decreaseDuration();
					if (c.getDur() <= 0) {
						consecrate.remove(i);
					}
				}
			}
		}, 0, 20);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Consecrate
				for (int i = 0; i < consecrate.size(); i++) {
					Consecrate c = consecrate.get(i);
					Location mid = c.getLocation();
					for (int j = 0; j < 5; j++) {
						int second = (j + 2) % 5;
						int first = j;
						double xf = Math.cos(first * 0.4 * Math.PI) * c.getRad();
						double zf = Math.sin(first * 0.4 * Math.PI) * c.getRad();
						Location lf = new Location(mid.getWorld(), mid.getX() + xf, mid.getY(), mid.getZ() + zf);
						double xs = Math.cos(second * 0.4 * Math.PI) * c.getRad();
						double zs = Math.sin(second * 0.4 * Math.PI) * c.getRad();
						Location ls = new Location(mid.getWorld(), mid.getX() + xs, mid.getY(), mid.getZ() + zs);
						Vector v = new Vector(ls.getX() - lf.getX(), 0, ls.getZ() - lf.getZ());
						for (int k = 0; k < 30; k++) {
							double y = 0;
							Location l = new Location(mid.getWorld(), lf.getX() + v.getX() / 30 * k, mid.getY() + 0.1,
									lf.getZ() + v.getZ() / 30 * k);
							if (l.getBlock().isEmpty()) {
								if (l.add(0, -0.5, 0).getBlock().isEmpty()) {
									if (!l.add(0, -1.5, 0).getBlock().isEmpty()) {
										y = -1;
									} else {
										y = -mid.getBlockY();
									}
								}
							} else {
								if (l.add(0, 1, 0).getBlock().isEmpty()) {
									y = 1;
								}
							}
							for (Player p : Bukkit.getServer().getOnlinePlayers()) {
								UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, lf.getX() + v.getX() / 30 * k,
										mid.getY() + y, lf.getZ() + v.getZ() / 30 * k, 1f, 1f, 1f, 1f, 0);
							}
						}
					}
				}
			}
		}, 0, 10);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Light Infusion
				for (int i = 0; i < linfusion.size(); i++) {
					LightInfusion in = linfusion.get(i);
					if (in.getDuration() > in.getMaxDuration() - 50) {
						Location l = in.getPlayer().getLocation();
						for (int j = 0; j < 7; j++) {
							double x = Math.random() - 0.5;
							double z = Math.random() - 0.5;
							for (Player p : Bukkit.getServer().getOnlinePlayers()) {
								UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, l.getX() + x, l.getY() + 1.0,
										l.getZ() + z, 1f, 1f, 1f, 1f, 0);
							}
						}
					}
					in.decreaseDuration();
					if (in.getDuration() <= 0) {
						SpielKlasse sk = getKlasse(in.getPlayer());
						if (sk != null) {
							sk.speedBoost = sk.speedBoost - in.getBoost();
							sk.applySpeed();
							linfusion.remove(in);
						}

					}
				}
				// Holy Radiance
				for (int i = 0; i < hradiance.size(); i++) {
					HolyRadiance in = hradiance.get(i);
					ArmorStand a = in.getStand();
					Location l = a.getLocation();
					double distance = l.distance(in.start());
					double distance2 = in.getTarget().getLocation().distance(l);
					if (distance > 40 || distance2 > 50) {
						a.remove();
						hradiance.remove(i);
					} else {
						if (distance2 < 0.5) {
							SpielKlasse sk = getKlasse(in.getTarget());
							if (sk != null) {
								if (sk.healthtohp() < sk.getMaxHP()) {
									sk.addHealth(sk.hptohealth(UtilMethods.heal("Holy Radiance", in.hmin(), in.hmax(),
											in.critc(), in.critm(), in.getShooter(), sk)));
								}
								for (Player p : Bukkit.getServer().getOnlinePlayers()) {
									UtilMethods.sendParticlePacket(p, EnumParticle.HEART, l.getX(), l.getY() + 2.0,
											l.getZ(), 0f, 0f, 0f, 0f, 1);
								}
								UtilMethods.sendSoundPacket(in.getShooter(), "entity.arrow.hit_player",
										in.getShooter().getLocation());
							}
							a.remove();
							hradiance.remove(i);
						} else {
							Vector vel = in.getTarget().getLocation().toVector().subtract(l.toVector()).normalize()
									.multiply(0.4);
							a.setVelocity(vel);
							for (Player p : Bukkit.getServer().getOnlinePlayers()) {
								for (int j = 0; j < 10; j++) {
									double rand = Math.random() * 2.0;
									double randx = Math.random() * 0.6;
									double randz = Math.random() * 0.6;
									UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, l.getX() + randx - 0.3,
											l.getY() + rand, l.getZ() - 0.3 + randz, 1f, 1f, 1f, 1f, 0);
								}
							}
						}
					}
				}
				// Avenger's Wrath
				for (int i = 0; i < awrath.size(); i++) {
					Wrath in = awrath.get(i);
					Location l = in.getPlayer().getLocation();
					for (int j = 0; j < 7; j++) {
						double x = Math.random() - 0.5;
						double z = Math.random() - 0.5;
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, l.getX() + x, l.getY() + 1.0,
									l.getZ() + z, 1f, 1f, 1f, 1f, 0);
							UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, l.getX() + x, l.getY(),
									l.getZ() + z, 1f, 1f, 1f, 1f, 0);
						}
					}
					in.decreaseDuration();
					if (in.getDuration() <= 0) {
						SpielKlasse sk = getKlasse(in.getPlayer());
						if (sk != null) {
							sk.epsBoost = sk.epsBoost - in.getEPS();
							awrath.remove(in);
						}

					}
				}
				// Crusader's Presence
				for (int i = 0; i < presence.size(); i++) {
					Presence in = presence.get(i);
					Location l = in.getPlayer().getLocation();
					for (int j = 0; j < 7; j++) {
						double x = Math.random() - 0.5;
						double z = Math.random() - 0.5;
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, l.getX() + x, l.getY() + 1.0,
									l.getZ() + z, 1f, 1f, 1f, 1f, 0);
							UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, l.getX() + x, l.getY(),
									l.getZ() + z, 1f, 1f, 1f, 1f, 0);
						}
					}
					in.decreaseDuration();
					if (in.getDuration() <= 0) {
						for (Player p : in.getAllys()) {
							SpielKlasse sk = getKlasse(p);
							if (sk != null) {
								sk.speedBoost = sk.speedBoost - in.getSpeed();
								sk.epsBoost = sk.epsBoost - in.getEPS();
								sk.applySpeed();
							}
						}
						SpielKlasse sk = getKlasse(in.getPlayer());
						if (sk != null) {
							sk.speedBoost = sk.speedBoost - in.getSpeed();
							sk.epsBoost = sk.epsBoost - in.getEPS();
							sk.applySpeed();
						}
						presence.remove(in);
					}
				}
			}
		}, 0, 1);
	}

	private void loopsDemon() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Consecrate
				for (int i = 0; i < dconsecrate.size(); i++) {
					DConsecrate c = dconsecrate.get(i);
					Location mid = c.getLocation();
					if (c.getDur() > 1) {
						for (int j = 0; j < 24 * c.getRad(); j++) {
							double x = Math.cos(j * Math.PI / 12 * c.getRad()) * c.getRad();
							double z = Math.sin(j * Math.PI / 12 * c.getRad()) * c.getRad();
							double y = 0;
							Location l = new Location(mid.getWorld(), mid.getX() + x, mid.getY() + 0.1, mid.getZ() + z);
							if (l.getBlock().isEmpty()) {
								if (l.add(0, -0.5, 0).getBlock().isEmpty()) {
									if (!l.add(0, -1.5, 0).getBlock().isEmpty()) {
										y = -1;
									} else {
										y = -mid.getBlockY();
									}
								}
							} else {
								if (l.add(0, 1, 0).getBlock().isEmpty()) {
									y = 1;
								}
							}
							for (Player p : Bukkit.getServer().getOnlinePlayers()) {
								if (p.equals(c.getP())) {
									UtilMethods.sendParticlePacket(p, EnumParticle.VILLAGER_HAPPY, mid.getX() + x,
											mid.getY() + y, mid.getZ() + z, 0f, 0f, 0f, 0f, 0);
								} else {
									UtilMethods.sendParticlePacket(p, EnumParticle.REDSTONE, mid.getX() + x,
											mid.getY() + y, mid.getZ() + z, 0f, 0f, 0f, 0f, 1);
								}
							}
						}
					}
					for (LivingEntity le : mid.getWorld().getLivingEntities()) {
						if (le.isDead() == false) {
							if (le instanceof Player) {
								if (PlayerUtil.isAttackingPlayers(c.getP())) {
									Player p2 = (Player) le;
									if (!p2.equals(c.getP())) {
										WarlordsPlayerAllys a = new WarlordsPlayerAllys(c.getP());
										if (!a.hasPlayer(p2)) {
											if (p2.getLocation().distance(mid) <= c.getRad()) {
												SpielKlasse sk = Warlords.getKlasse(p2);
												if (sk != null) {
													double dmg = UtilMethods.damage("Cursed Ground", c.getCc(),
															c.getCmul(), c.getDmin(), c.getDmax(), c.getP(), sk);
													if (dmg != 0) {
														sk.removeHealth(sk.hptohealth(dmg));
													}
													UtilMethods.sendSoundPacket(c.getP(), "entity.arrow.hit_player",
															c.getP().getLocation());
												}
											}
										}
									}
								}
							} else {
								if (!(le instanceof ArmorStand)) {
									if (le.getLocation().distance(mid) <= c.getRad()) {
										double dmg = UtilMethods.hptohealth(UtilMethods.damage(c.getCc(), c.getCmul(),
												c.getDmin(), c.getDmax(), c.getP(), "Cursed Ground"));
										double hp = le.getHealth();
										if (hp < dmg) {
											WeaponUtil.doWeapon(le, c.getP());
										}
										UtilMethods.sendSoundPacket(c.getP(), "entity.arrow.hit_player",
												c.getP().getLocation());
										SkillUtil.setHealth(le, dmg);
									}
								}
							}
						}
					}
					c.decreaseDuration();
					if (c.getDur() <= 0) {
						dconsecrate.remove(i);
					}
				}
			}
		}, 0, 20);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Consecrate
				for (int i = 0; i < dconsecrate.size(); i++) {
					DConsecrate c = dconsecrate.get(i);
					Location mid = c.getLocation();
					for (int j = 0; j < 5; j++) {
						int second = (j + 2) % 5;
						int first = j;
						double xf = Math.cos(first * 0.4 * Math.PI) * c.getRad();
						double zf = Math.sin(first * 0.4 * Math.PI) * c.getRad();
						Location lf = new Location(mid.getWorld(), mid.getX() + xf, mid.getY(), mid.getZ() + zf);
						double xs = Math.cos(second * 0.4 * Math.PI) * c.getRad();
						double zs = Math.sin(second * 0.4 * Math.PI) * c.getRad();
						Location ls = new Location(mid.getWorld(), mid.getX() + xs, mid.getY(), mid.getZ() + zs);
						Vector v = new Vector(ls.getX() - lf.getX(), 0, ls.getZ() - lf.getZ());
						for (int k = 0; k < 30; k++) {
							double y = 0;
							Location l = new Location(mid.getWorld(), lf.getX() + v.getX() / 30 * k, mid.getY() + 0.1,
									lf.getZ() + v.getZ() / 30 * k);
							if (l.getBlock().isEmpty()) {
								if (l.add(0, -0.5, 0).getBlock().isEmpty()) {
									if (!l.add(0, -1.5, 0).getBlock().isEmpty()) {
										y = -1;
									} else {
										y = -mid.getBlockY();
									}
								}
							} else {
								if (l.add(0, 1, 0).getBlock().isEmpty()) {
									y = 1;
								}
							}
							for (Player p : Bukkit.getServer().getOnlinePlayers()) {
								UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, lf.getX() + v.getX() / 30 * k,
										mid.getY() + y, lf.getZ() + v.getZ() / 30 * k, 0f, 0f, 0f, 0f, 0);
							}
						}
					}
				}
			}
		}, 0, 10);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Light Infusion
				for (int i = 0; i < sinfusion.size(); i++) {
					ShadowInfusion in = sinfusion.get(i);
					if (in.getDuration() > in.getMaxDuration() - 50) {
						Location l = in.getPlayer().getLocation();
						for (int j = 0; j < 7; j++) {
							double x = Math.random() - 0.5;
							double z = Math.random() - 0.5;
							for (Player p : Bukkit.getServer().getOnlinePlayers()) {
								UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, l.getX() + x, l.getY() + 1.0,
										l.getZ() + z, 0f, 0f, 0f, 0f, 0);
							}
						}
					}
					in.decreaseDuration();
					if (in.getDuration() <= 0) {
						SpielKlasse sk = getKlasse(in.getPlayer());
						if (sk != null) {
							sk.speedBoost = sk.speedBoost - in.getBoost();
							sk.applySpeed();
							sinfusion.remove(in);
						}

					}
				}
				// Unholy Radiance
				for (int i = 0; i < uhradiance.size(); i++) {
					UnholyRadiance in = uhradiance.get(i);
					ArmorStand a = in.getStand();
					Location l = a.getLocation();
					double distance = l.distance(in.start());
					double distance2 = in.getTarget().getLocation().distance(l);
					if (distance > 40 || distance2 > 50) {
						a.remove();
						uhradiance.remove(i);
					} else {
						if (distance2 < 0.5) {
							SpielKlasse sk = getKlasse(in.getTarget());
							if (sk != null) {
								if (sk.healthtohp() < sk.getMaxHP()) {
									sk.addHealth(sk.hptohealth(UtilMethods.heal("Unholy Radiance", in.hmin(), in.hmax(),
											in.critc(), in.critm(), in.getShooter(), sk)));
								}
								for (Player p : Bukkit.getServer().getOnlinePlayers()) {
									UtilMethods.sendParticlePacket(p, EnumParticle.VILLAGER_ANGRY, l.getX(),
											l.getY() + 2.0, l.getZ(), 0f, 0f, 0f, 0f, 1);
								}
								UtilMethods.sendSoundPacket(in.getShooter(), "entity.arrow.hit_player",
										in.getShooter().getLocation());
							}
							a.remove();
							uhradiance.remove(i);
						} else {
							Vector vel = in.getTarget().getLocation().toVector().subtract(l.toVector()).normalize()
									.multiply(0.4);
							a.setVelocity(vel);
							for (Player p : Bukkit.getServer().getOnlinePlayers()) {
								for (int j = 0; j < 10; j++) {
									double rand = Math.random() * 2.0;
									double randx = Math.random() * 0.6;
									double randz = Math.random() * 0.6;
									UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, l.getX() + randx - 0.3,
											l.getY() + rand, l.getZ() - 0.3 + randz, 1f, 0f, 0f, 1f, 0);
								}
							}
						}
					}
				}
				// Demon's Wrath
				for (int i = 0; i < dwrath.size(); i++) {
					DWrath in = dwrath.get(i);
					Location l = in.getPlayer().getLocation();
					for (int j = 0; j < 7; j++) {
						double x = Math.random() - 0.5;
						double z = Math.random() - 0.5;
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, l.getX() + x, l.getY() + 1.0,
									l.getZ() + z, 0f, 0f, 0f, 0f, 0);
							UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, l.getX() + x, l.getY(),
									l.getZ() + z, 1f, 0f, 1f, 0f, 0);
						}
					}
					in.decreaseDuration();
					if (in.getDuration() <= 0) {
						SpielKlasse sk = getKlasse(in.getPlayer());
						if (sk != null) {
							sk.epsBoost = sk.epsBoost - in.getEPS();
							dwrath.remove(in);
						}

					}
				}
			}
		}, 0, 1);
	}

	private void loopsHunter() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Explosiv Arrow
				for (int i = 0; i < explosivarrow.size(); i++) {
					ExplosivArrow in = explosivarrow.get(i);
					in.decreaseDuration();
					if (in.getDur() <= 0) {
						SpielKlasse sk = getKlasse(in.getPlayer());
						if (sk != null) {
							if (sk instanceof Hunter) {
								Hunter h = (Hunter) sk;
								h.dmgb = 0;
							}
							sk.ccBoost = 0;
							sk.cmulBoost = 0;
							explosivarrow.remove(in);
						}
					}
				}
				// Companion
				for (int i = 0; i < companion.size(); i++) {
					Companion in = companion.get(i);
					in.decreaseDuration();
					if (in.getDur() <= 0 || in.getLive() <= 0) {
						SpielKlasse sk = getKlasse(in.getPlayer());
						if (sk != null) {
							if (sk instanceof Hunter) {
								Hunter h = (Hunter) sk;
								h.dmgb2 = 1.0;
							}
							in.getEndermite().remove();
							companion.remove(in);
						}
					}
				}
				// Blood Arrow
				for (int i = 0; i < bloodarrow.size(); i++) {
					BloodArrow in = bloodarrow.get(i);
					in.decreaseDuration();
					if (in.getDuration() <= 0) {
						SpielKlasse sk = getKlasse(in.getPlayer());
						if (sk != null) {
							bloodarrow.remove(in);
						}
					}
				}
				// Marking Arrow
				for (int i = 0; i < markingarrow.size(); i++) {
					MarkingArrow in = markingarrow.get(i);
					in.decreaseDuration();
					if (in.getDuration() <= 0) {
						SpielKlasse sk = getKlasse(in.getPlayer());
						if (sk != null) {
							if (sk instanceof Hunter) {
								Hunter h = (Hunter) sk;
								h.dmgb1 = 1.0;
							}
							markingarrow.remove(in);
						}
					}
				}
			}
		}, 0, 10);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Elemental Arrow
				for (int i = 0; i < elementalarrow.size(); i++) {
					ElementalArrow e = elementalarrow.get(i);
					Location l = e.getArrow().getLocation();
					if (e.isBlood()) {
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							UtilMethods.sendParticlePacket(p, EnumParticle.REDSTONE, l.getX(), l.getY(), l.getZ(), 0f,
									0f, 0f, 0f, 1);
						}
					}
					if (e.isMarking()) {
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							UtilMethods.sendParticlePacket(p, EnumParticle.CRIT_MAGIC, l.getX(), l.getY(), l.getZ(), 0f,
									0f, 0f, 0f, 1);
						}
					}
					if (!e.isMarking() && !e.isBlood()) {
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							UtilMethods.sendParticlePacket(p, EnumParticle.CRIT, l.getX(), l.getY(), l.getZ(), 0f, 0f,
									0f, 0f, 1);
						}
					}
				}
			}
		}, 0, 1);
	}

	private void loopsThunderlord() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Lightning Bolt
				for (int i = 0; i < lightningbolt.size(); i++) {
					Lightningbolt lb = lightningbolt.get(i);
					lb.getStand().setVelocity(lb.getVector());
					Location l = lb.getStand().getLocation();
					double distance = lb.start().distance(l);
					if (!l.add(0, 1.5, 0).getBlock().isEmpty()&&l.getBlock().getType().isSolid()) {
						l.getWorld().playSound(l, "shaman.lightningbolt.impact", 1, 1);
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							UtilMethods.sendParticlePacket(p, EnumParticle.EXPLOSION_LARGE, l.getX(), l.getY() + 1.5,
									l.getZ(), 0f, 0f, 0f, 0f, 1);
						}
						lb.getStand().remove();
						lightningbolt.remove(i);
					}
					else if (!l.add(lb.getVector()).getBlock().isEmpty()&&l.getBlock().getType().isSolid()) {
						l.getWorld().playSound(l, "shaman.lightningbolt.impact", 1, 1);
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							UtilMethods.sendParticlePacket(p, EnumParticle.EXPLOSION_LARGE, l.getX(), l.getY() + 1.5,
									l.getZ(), 0f, 0f, 0f, 0f, 1);
						}
						lb.getStand().remove();
						lightningbolt.remove(i);
					}
					if (distance >= lb.getRange()) {
						l.getWorld().playSound(l, "shaman.lightningbolt.impact", 1, 1);
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							UtilMethods.sendParticlePacket(p, EnumParticle.EXPLOSION_LARGE, l.getX(), l.getY() + 1.5,
									l.getZ(), 0f, 0f, 0f, 0f, 1);
						}
						lb.getStand().remove();
						lightningbolt.remove(i);
					}
					for (LivingEntity le : lb.getStand().getWorld().getLivingEntities()) {
						boolean already = false;
						for (UUID u : lb.getHited()) {
							if (u.compareTo(le.getUniqueId()) == 0) {
								already = true;
								break;
							}
						}
						if (already == false) {
							if (le.isDead() == false) {
								if (le instanceof Player) {
									if (PlayerUtil.isAttackingPlayers(lb.getShooter())) {
										Player p2 = (Player) le;
										if (!p2.equals(lb.getShooter())) {
											WarlordsPlayerAllys a = new WarlordsPlayerAllys(lb.getShooter());
											if (!a.hasPlayer(p2)) {
												Location m = lb.getStand().getLocation();
												double dy = m.getY() - le.getLocation().getY();
												if (SkillUtil.distance2D(m, le.getLocation()) <= 1.0 && dy < 0.5
														&& dy > -2.0) {
													SpielKlasse sk = Warlords.getKlasse(p2);
													if (sk != null) {
														sk.removeEnergy(3);
														double dmg = UtilMethods.damage("Lightning Bolt", lb.critc(),
																lb.critm(), lb.dmin(), lb.dmax(), lb.getShooter(), sk);
														if (dmg != 0) {
															sk.removeHealth(sk.hptohealth(dmg));
														}
														lb.addHited(le.getUniqueId());
														SpielKlasse skm = getKlasse(lb.getShooter());
														if (skm != null) {
															if (skm instanceof Thunderlord) {
																ItemStack is = lb.getShooter().getInventory()
																		.getItem(1);
																if (is != null) {
																	if (is.getType() == Material.INK_SACK) {
																		if (is.getDurability() == 8) {
																			if (is.getAmount() > 2) {
																				is.setAmount(is.getAmount() - 2);
																			} else {
																				skm.addAbility(1);
																			}
																		} else if (is.getDurability() == 15) {
																			if (is.getAmount() > 2) {
																				is.setAmount(is.getAmount() - 2);
																			} else {
																				is.setDurability((short) 8);
																				is.setAmount(59);
																			}
																		}
																	}
																}
															}
														}
														UtilMethods.sendSoundPacket(lb.getShooter(),
																"entity.arrow.hit_player",
																lb.getShooter().getLocation());
													}
												}
											}
										}
									}
								} else {
									if (!(le instanceof ArmorStand)) {
										Location m = lb.getStand().getLocation();
										double dy = m.getY() - le.getLocation().getY();
										CraftLivingEntity cle = (CraftLivingEntity) le;
										double height = cle.getHandle().getHeadHeight();
										if (SkillUtil.distance2D(m, le.getLocation()) <= 1.0 && dy < 0.5 * (height - 1)
												&& dy > -2.0) {
											double dmg = UtilMethods
													.hptohealth(UtilMethods.damage(lb.critc(), lb.critm(), lb.dmin(),
															lb.dmax(), lb.getShooter(), "Lightning Bolt"));
											double hp = le.getHealth();
											if (hp < dmg) {
												WeaponUtil.doWeapon(le, lb.getShooter());

											}
											lb.addHited(le.getUniqueId());
											SpielKlasse skm = getKlasse(lb.getShooter());
											if (skm != null) {
												if (skm instanceof Thunderlord) {
													ItemStack is = lb.getShooter().getInventory().getItem(1);
													if (is != null) {
														if (is.getType() == Material.INK_SACK) {
															if (is.getDurability() == 8) {
																if (is.getAmount() > 2) {
																	is.setAmount(is.getAmount() - 2);
																} else {
																	skm.addAbility(1);
																}
															} else if (is.getDurability() == 15) {
																if (is.getAmount() > 2) {
																	is.setAmount(is.getAmount() - 2);
																} else {
																	is.setDurability((short) 8);
																	is.setAmount(59);
																}
															}
														}
													}
												}
											}
											UtilMethods.sendSoundPacket(lb.getShooter(), "entity.arrow.hit_player",
													lb.getShooter().getLocation());
											UtilMethods.setHealth(le, dmg);
										}
									}
								}
							}
						}
					}
				}

				// Chain Lightning
				for (int i = 0; i < clightning.size(); i++) {
					ChainLightning cl = clightning.get(i);
					cl.decreaseduration();
					if (cl.getMaxDur() - cl.getDur() == 20) {
						for (ArmorStand a : cl.getStand()) {
							a.remove();
						}
					}
					if (cl.getDur() < 0) {
						Player p = cl.getPlayer();
						SpielKlasse sk = getKlasse(p);
						if (sk != null) {
							sk.damagemultiplier += cl.getReduction();
						}
						Warlords.clightning.remove(i);
					}
				}
			}
		}, 0, 1);

	}

	private void loopsPontiff() {
		// Attacks of Pontiff
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (int i = 0; i < pontiff.size(); i++) {
					Pontiff p = pontiff.get(i);
					if (!p.getSkel().isDead()) {
						p.reduceSkillCooldown();
						if (p.getSC() <= 0) {
							if (p.getSlamI() < 10) {
								Location neu = p.getSkel().getLocation();
								if (p.getSlamI() < 1) {
									p.setTarget(p.getSkel().getLocation());
									neu = p.getSkel().getLocation().add(0, 0.9, 0);
								}
								neu.setY(neu.getY() + 0.7);
								Location neu2 = new Location(neu.getWorld(), neu.getX(), neu.getY() + 1, neu.getZ());
								if (neu.getBlock().isEmpty() && neu2.getBlock().isEmpty()) {
									p.getSkel().teleport(neu);
									p.setSlamHeight(p.getTarget().getY() - p.getSkel().getLocation().getY());
								}
							} else if (p.getSlamI() < 30) {
								double distance = -1;
								Player target = null;
								for (Player pl : p.getSkel().getWorld().getPlayers()) {
									if (pl.isDead() == false && pl.getGameMode() != GameMode.CREATIVE
											&& pl.getGameMode() != GameMode.SPECTATOR) {
										if (pl.getLocation().distance(p.getSkel().getLocation()) <= 70.0) {
											if (distance < 0) {
												target = pl;
												distance = pl.getLocation().distance(p.getSkel().getLocation());
											} else {
												if (distance > pl.getLocation().distance(p.getSkel().getLocation())) {
													target = pl;
													distance = pl.getLocation().distance(p.getSkel().getLocation());
												}
											}
										}
									}
								}
								if (target != null && distance <= 70.0) {
									p.setTarget(target.getLocation());
									p.setSlamHeight(target.getLocation().getY() - p.getSkel().getLocation().getY());
									Vector dif = target.getLocation().toVector()
											.subtract(p.getSkel().getLocation().toVector());
									p.getSkel().teleport(p.getSkel().getLocation().setDirection(dif.normalize()));
								}
							} else if (p.getSlamI() < 35) {
								if (p.getTarget() != null) {
									Vector dif = new Vector(p.getTarget().getX() - p.getSkel().getLocation().getX(),
											p.getTarget().getY() - p.getSkel().getLocation().getY(),
											p.getTarget().getZ() - p.getSkel().getLocation().getZ()).normalize();
									Vector dif2D;
									if (dif.getX() == 0 || dif.getZ() == 0) {
										dif2D = new Vector(0, 0, 0);
									} else {
										dif2D = new Vector(dif.getX(), 0, dif.getZ()).normalize();
									}

									Vector difm = new Vector(dif2D.getX() * 1.2, p.getSlamHeight() / 6.0,
											dif2D.getZ() * 1.2);
									Location neu = p.getSkel().getLocation().add(difm);
									Location neu2 = new Location(neu.getWorld(), neu.getX(), neu.getY() + 1,
											neu.getZ());
									if (neu.getBlock().isEmpty() && neu2.getBlock().isEmpty()) {
										p.getSkel().teleport(neu);
									}
								}
							}
							if (p.getSlamI() < 35) {
								p.getSkel().setVelocity(new Vector(0, 0, 0));
							}
							p.increaseSlam();
							if (p.getSlamI() >= 35) {
								Location l = p.getSkel().getLocation();
								for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
									UtilMethods.sendParticlePacket(p2, EnumParticle.EXPLOSION_HUGE, l.getX(),
											l.getY() + 1, l.getZ(), 0f, 0f, 0f, 0f, 2);
									UtilMethods.sendParticlePacket(p2, EnumParticle.FLAME, l.getX(), l.getY() + 1,
											l.getZ(), 0f, 0f, 0f, 0.3f, 500);
								}
								l.getWorld().playSound(l, "entity.generic.explode", 1f, 1f);
								for (Player pl : p.getSkel().getWorld().getPlayers()) {
									if (SkillUtil.distance2D(l, pl.getLocation()) <= 3.0
											&& Math.abs(pl.getLocation().getY() - l.getY()) <= 2.0) {
										SpielKlasse sk = getKlasse(pl);
										if (pl.isDead() == false && pl.getGameMode() != GameMode.CREATIVE
												&& pl.getGameMode() != GameMode.SPECTATOR) {
											if (sk == null) {
												SkillUtil.setHealth(pl, 8);
											} else {
												UtilMethods.doDamageMessageENV(false, sk.healthtohp(4), "mob", sk.p);
												sk.removeHealth(4);
											}
											Vector kb = pl.getLocation().toVector().subtract(l.toVector());
											pl.setVelocity(pl.getVelocity().add(kb));
										}
									}
								}
								p.resetSlam();
							}
						}
					}
				}
			}
		}, 0, 1);
		// Boss bar
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (Pontiff p : pontiff) {
					BossBattle.BarStyle style = BossBattle.BarStyle.PROGRESS;
					BossBattle.BarColor color = BossBattle.BarColor.BLUE;
					BossBattleServer server = new BossBattleServer(
							IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + p.getHealth() + "\"}"), color, style);
					server.setProgress((float) (1f - p.getHealth() / p.getMax()));

					PacketPlayOutBoss add = new PacketPlayOutBoss(PacketPlayOutBoss.Action.ADD, server);
					PacketPlayOutBoss destroy = new PacketPlayOutBoss(PacketPlayOutBoss.Action.REMOVE,
							p.getBossServer());
					for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
						if (p2.getLocation().distance(p.getSkel().getLocation()) <= 70
								&& p.getSkel().isDead() == false) {
							((CraftPlayer) p2).getHandle().playerConnection.sendPacket(destroy);
							((CraftPlayer) p2).getHandle().playerConnection.sendPacket(add);
						} else {
							((CraftPlayer) p2).getHandle().playerConnection.sendPacket(destroy);
						}
					}
					p.setBossServer(server);
					if (p.getSkel().isDead()) {
						Warlords.pontiff.remove(p);
					}
				}
			}
		}, 0, 5);
	}

	private void loopsSpecial() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Demonic Sphere
				for (int i = 0; i < sphere.size(); i++) {
					Sphere s = sphere.get(i);
					Location l = s.getP().getLocation();
					for (int j = 1; j < 3.0 * s.getRad() + 1.0; j++) {
						double r = Math.sin((j / (3.0 * s.getRad() + 1.0)) * Math.PI) * s.getRad();
						for (int k = 0; k < (3.0 * s.getRad() + 1) * 2.0 * r; k++) {
							double x = Math.cos(k * Math.PI / (3.0 * s.getRad() + 1.0)) * r;
							double z = Math.sin(k * Math.PI / (3.0 * s.getRad() + 1.0)) * r;
							if ((k % 2) == 0) {
								for (Player p : Bukkit.getServer().getOnlinePlayers()) {
									UtilMethods.sendParticlePacket(p, EnumParticle.SPELL_MOB, l.getX() + x,
											l.getY() + 1.0 + (j - (3.0 * s.getRad() + 1.0) / 2.0)
													/ (3.0 * s.getRad() + 1.0) * 2.0 * s.getRad(),
											l.getZ() + z, 0f, 0f, 0f, 1f, 0);
								}
							} else {
								for (Player p : Bukkit.getServer().getOnlinePlayers()) {
									UtilMethods.sendParticlePacket(p, EnumParticle.REDSTONE, l.getX() + x,
											l.getY() + 1.0 + (j - (3.0 * s.getRad() + 1.0) / 2.0)
													/ (3.0 * s.getRad() + 1.0) * 2.0 * s.getRad(),
											l.getZ() + z, 0f, 0f, 0f, 1f, 0);// 0.05
								}
							}

						}
					}
					for (LivingEntity le : l.getWorld().getLivingEntities()) {
						if (le.isDead() == false) {
							if (le instanceof Player) {
								if (PlayerUtil.isAttackingPlayers(s.getP())) {
									Player p2 = (Player) le;
									if (!p2.equals(s.getP())) {
										WarlordsPlayerAllys a = new WarlordsPlayerAllys(s.getP());
										if (!a.hasPlayer(p2)) {
											if (p2.getLocation().distance(l) <= s.getRad()) {
												SpielKlasse sk = Warlords.getKlasse(p2);
												if (sk != null) {
													double dmg = UtilMethods.damage("Demonic Sphere", s.getCc(),
															s.getCmul(), s.getDmin(), s.getDmax(), s.getP(), sk);

													if (dmg != 0) {
														sk.removeHealth(sk.hptohealth(dmg));
													}
													UtilMethods.sendSoundPacket(s.getP(), "entity.arrow.hit_player",
															s.getP().getLocation());
												}
											}
										}
									}
								}
							} else {
								if (!(le instanceof ArmorStand)) {
									if (le.getLocation().distance(l) <= s.getRad()) {
										double dmg = UtilMethods.hptohealth(UtilMethods.damage(s.getCc(), s.getCmul(),
												s.getDmin(), s.getDmax(), s.getP(), "Demonic Sphere"));
										double hp = le.getHealth();
										if (hp < dmg) {
											WeaponUtil.doWeapon(le, s.getP());
										}
										UtilMethods.sendSoundPacket(s.getP(), "entity.arrow.hit_player",
												s.getP().getLocation());
										SkillUtil.setHealth(le, dmg);
									}
								}
							}
						}
					}
					s.setRad(s.getRad() - 6.0 / 30.0);
					s.decreaseDuration();
					if (s.getDur() <= 0) {
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							UtilMethods.sendParticlePacket(p, EnumParticle.EXPLOSION_LARGE, l.getX(), l.getY() + 1.0,
									l.getZ(), 0f, 0f, 0f, 0f, 2);
						}
						l.getWorld().playSound(l, "entity.generic.explode", 1f, 1f);
						for (LivingEntity le : l.getWorld().getLivingEntities()) {
							if (le.isDead() == false) {
								if (le instanceof Player) {
									if (PlayerUtil.isAttackingPlayers(s.getP())) {
										Player p2 = (Player) le;
										if (!p2.equals(s.getP())) {
											WarlordsPlayerAllys a = new WarlordsPlayerAllys(s.getP());
											if (!a.hasPlayer(p2)) {
												if (p2.getLocation().distance(l) <= s.getRadE()) {
													SpielKlasse sk = Warlords.getKlasse(p2);
													if (sk != null) {
														double dmg = UtilMethods.damage("Demonic Sphere", s.getCcE(),
																s.getCmulE(), s.getDminE(), s.getDmaxE(), s.getP(), sk);
														if (dmg != 0) {
															sk.removeHealth(sk.hptohealth(dmg));
														}
														UtilMethods.sendSoundPacket(s.getP(), "entity.arrow.hit_player",
																s.getP().getLocation());
														Vector kb = p2.getLocation().toVector().subtract(l.toVector());
														p2.setVelocity(p2.getVelocity().add(kb));
													}
												}
											}
										}
									}
								} else {
									if (!(le instanceof ArmorStand)) {
										if (le.getLocation().distance(l) <= s.getRadE()) {
											double dmg = UtilMethods
													.hptohealth(UtilMethods.damage(s.getCcE(), s.getCmulE(),
															s.getDminE(), s.getDmaxE(), s.getP(), "Demonic Sphere"));
											double hp = le.getHealth();
											if (hp < dmg) {
												WeaponUtil.doWeapon(le, s.getP());
											}
											UtilMethods.sendSoundPacket(s.getP(), "entity.arrow.hit_player",
													s.getP().getLocation());
											SkillUtil.setHealth(le, dmg);
											Vector kb = le.getLocation().toVector().subtract(l.toVector());
											le.setVelocity(le.getVelocity().add(kb));
										}
									}
								}
							}
						}
						sphere.remove(i);
					}
				}
			}
		}, 0, 10);
		// Projectile Gravitation disabling
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (int i = 0; i < catbolt.size(); i++) {
					catbolt.get(i).getArrow().setVelocity(catbolt.get(i).getVector());
				}
			}
		}, 0, 1);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				// Cat Bolt
				for (int i = 0; i < catbolt.size(); i++) {
					CatBolt c = catbolt.get(i);
					Location l = c.getArrow().getLocation();
					WorldServer s = ((CraftWorld) l.getWorld()).getHandle();
					if (c.getOcelot() != null) {
						EntityOcelot stand = c.getOcelot();
						stand.setLocation(l.getX(), l.getY(), l.getZ(), -c.getArrow().getLocation().getYaw(),
								c.getArrow().getLocation().getPitch());
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(stand);
							((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
						}
					} else {
						EntityOcelot stand = new EntityOcelot(s);
						stand.setLocation(l.getX(), l.getY(), l.getZ(), -c.getArrow().getLocation().getYaw(),
								c.getArrow().getLocation().getPitch());
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
							((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
						}
						c.setOcelot(stand);
					}

					for (LivingEntity le : l.getWorld().getLivingEntities()) {
						if (!(le instanceof ArmorStand)) {
							if (le.getUniqueId().compareTo(c.getShooter().getUniqueId()) != 0) {
								if (c.getArrow().getLocation().distance(le.getLocation().add(0, 1, 0)) <= 1.25) {
									c.directHit();
									SkillUtil.doCatboltHit(c.getArrow());
									break;
								}
							}
						}
					}
				}
			}
		}, 0, 1);
	}

	public static SpielKlasse getKlasse(Player p) {
		for (int i = 0; i < player.size(); i++) {
			SpielKlasse sp = player.get(i);
			if (sp.p.equals(p)) {
				return sp;
			}
		}
		return null;
	}

	public static boolean isUsingStealth(Player p) {
		for (int i = 0; i < stealth.size(); i++) {
			if (stealth.get(i).getPlayer().equals(p)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isUsingVanish(Player p) {
		for (int i = 0; i < vanish.size(); i++) {
			if (vanish.get(i).getPlayer().equals(p)) {
				return true;
			}
		}
		return false;
	}
}
