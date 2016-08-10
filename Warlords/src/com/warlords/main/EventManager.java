package com.warlords.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.warlords.boss.Pontiff;
import com.warlords.util.SkillUtil;
import com.warlords.util.UtilMethods;
import com.warlords.util.WeaponUtil;
import com.warlords.util.data.PlayerUtilMethods;
import com.warlords.util.data.inventory.Confirmation;
import com.warlords.util.data.inventory.CraftConfirmation;
import com.warlords.util.data.inventory.SkinGUI;
import com.warlords.util.data.inventory.UpgradeConfirmation;
import com.warlords.util.data.types.SpielKlasse;
import com.warlords.util.data.types.WarlordsPlayer;
import com.warlords.util.data.types.WarlordsPlayerAllys;
import com.warlords.util.data.types.Weapon;
import com.warlords.util.skills.hunter.Companion;
import com.warlords.util.skills.shaman.WindfuryWeapon;
import com.warlords.util.type.Assassin;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;

public class EventManager implements Listener {
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			p.getWorld().playSound(p.getLocation(), "defeat", 1.0f, 1.0f);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onSkillPlayer(EntityDamageEvent e) {
		if (e instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent en = (EntityDamageByEntityEvent) e;
			if (en.getDamager() instanceof Player) {
				SpielKlasse sk = Warlords.getKlasse((Player) en.getDamager());
				if (sk != null) {
					e.setDamage(0);
				}
			} else if (en.getDamager() instanceof Arrow) {
				if (((Arrow) en.getDamager()).getShooter() instanceof Player) {
					Player p = (Player) ((Arrow) en.getDamager()).getShooter();
					SpielKlasse sk = Warlords.getKlasse(p);
					if (sk != null) {
						WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
								"/players/" + p.getUniqueId());
						if (wp != null) {
							if (wp.getMode() == true) {
								e.setDamage(0);
							}
						}
					}

				}
			}
		}
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			SpielKlasse sk = Warlords.getKlasse(p);
			double dmg = 0.0;
			if (sk != null) {
				if (e.getFinalDamage() / 2.0 > 0) {
					String source = "";
					if (e.getCause() == DamageCause.ENTITY_ATTACK) {
						source = "mob";
					} else if (e.getCause() == DamageCause.FALL) {
						source = "fall";
					} else if (e.getCause() == DamageCause.FLY_INTO_WALL) {
						for (int i = 0; i < Warlords.elytra.size(); i++) {
							if (Warlords.elytra.get(i).getPlayer().equals(p)) {
								e.setDamage(0);
								e.setCancelled(true);
								return;
							}
						}
					} else if (e.getCause() == DamageCause.THORNS) {
						source = "thorn";
					} else {
						source = "special";
					}
					dmg = UtilMethods.envdamage(source, UtilMethods.healthtohp(e.getFinalDamage() / 2.0), sk);
				}
				if (dmg > sk.healthtohp()) {
					SkillUtil.removeAbilitys(p);
					WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/players/" + p.getUniqueId());
					if (wp != null) {
						if (wp.getMode() == true) {
							for (int i = 0; i < 9; i++) {
								p.getInventory().setItem(i, null);
							}
						}
					}
				}
			}
			if (dmg > 0) {
				UtilMethods.setHealth(p, sk.hptohealth(dmg));
				e.setDamage(0);
			} else {
				e.setDamage(0);
			}
		}
		for (Pontiff p : Warlords.pontiff) {
			if (p.getSkel().getUniqueId().compareTo(e.getEntity().getUniqueId()) == 0) {
				if (!p.removeHealth((int) Math.round(UtilMethods.healthtohp(e.getDamage())))) {
					e.setDamage(0);
				}
				return;
			}
		}
	}

	@EventHandler
	public void skillhit(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		UtilMethods.doRegiveClass(p);
		for (int i = 0; i < Warlords.vanish.size(); i++) {
			for (Player sp : Bukkit.getServer().getOnlinePlayers()) {
				sp.hidePlayer(Warlords.vanish.get(i).getPlayer());
			}
		}
	}

	@EventHandler
	public void respawn(PlayerRespawnEvent event) {
		Player p = event.getPlayer();
		WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/players/" + p.getUniqueId());
		if (wp != null) {
			SpielKlasse py = Warlords.getKlasse(p);
			if (py != null) {
				if (wp.getMode() == true) {
					p.getInventory().setItem(0, WeaponUtil.generateItemStack(py.getWeapon(), py.getName()));
					for (int i = 1; i < 9; i++) {
						py.addAbility(i);
						py.removeEnergy(py.getEnergy());
					}
				}
			}
		}
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			SpielKlasse sk = Warlords.getKlasse(p);
			WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
					"/players/" + p.getUniqueId());
			if (sk != null && wp != null) {
				if (wp.getMode() == true) {
					for (Integer s : e.getRawSlots()) {
						if (s < e.getInventory().getSize()) {
						} else {
							if (s > p.getInventory().getSize()) {
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Inventory in = event.getInventory();
		for (int i = 0; i < Warlords.confirmation.size(); i++) {
			if (in.equals(Warlords.confirmation.get(i).getInventory())) {
				Warlords.confirmation.remove(i);
			}
		}
		for (int i = 0; i < Warlords.uconfirmation.size(); i++) {
			if (in.equals(Warlords.uconfirmation.get(i).getInventory())) {
				Warlords.uconfirmation.remove(i);
			}
		}
		for (int i = 0; i < Warlords.cconfirmation.size(); i++) {
			if (in.equals(Warlords.cconfirmation.get(i).getInventory())) {
				Warlords.cconfirmation.remove(i);
			}
		}
		for (int i = 0; i < Warlords.skingui.size(); i++) {
			if (in.equals(Warlords.skingui.get(i).getInventory())) {
				Warlords.skingui.remove(i);
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(WeaponUtil.getWeaponInventory(p).getName())) {
			if (inventory.getHolder().equals(p)) {
				if (event.getClick() == ClickType.RIGHT) {
					WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/players/" + p.getUniqueId());
					if (wp != null) {
						ArrayList<Weapon> wlist = wp.getWeaponlist();
						if (wlist != null) {
							if (clicked != null && event.getRawSlot() < wlist.size()
									&& event.getSlot() != wp.getWeaponSlot()) {
								Weapon w = wlist.get(event.getRawSlot());
								if (w.getKat() == 1) {
									Confirmation c = WeaponUtil.getWeaponConfirmationInventory(p, event.getRawSlot());
									Warlords.confirmation.add(c);
									p.openInventory(c.getInventory());
									return;
								} else if (w.getKat() == 2) {
									Confirmation c = WeaponUtil.getWeaponConfirmationInventory(p, event.getRawSlot());
									Warlords.confirmation.add(c);
									p.openInventory(c.getInventory());
									return;
								} else if (w.getKat() == 3) {
									ItemStack is = WeaponUtil.generateItemStack(w, WeaponUtil.KLASSEN[w.getKlass()]);
									double rand = Math.random();
									String maintext = ChatColor.GRAY + "You salvaged ";
									if (rand < 0.05) {
										maintext = ChatColor.GRAY + "You received " + ChatColor.LIGHT_PURPLE
												+ "1 Void Shard " + ChatColor.GRAY + "for salvaging your ";
										wp.setVoidShards(wp.getVoidShards() + 1);
									}
									String weaponname = is.getItemMeta().getDisplayName();
									String cweaponname = is.getItemMeta().getDisplayName();
									String lore = "";
									for (int i = 0; i < is.getItemMeta().getLore().size(); i++) {
										String s = is.getItemMeta().getLore().get(i);
										if (i < is.getItemMeta().getLore().size() - 1) {
											lore += "\\\"" + s + "\\\",";
										} else {
											lore += "\\\"" + s + "\\\"";
										}
									}
									IChatBaseComponent al = ChatSerializer
											.a("{\"text\":\"" + maintext + "\", \"extra\":[{\"text\":\"" + weaponname
													+ "\",\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
													+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
									PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
									((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
								} else {
									ItemStack is = WeaponUtil.generateItemStack(w, WeaponUtil.KLASSEN[w.getKlass()]);
									String maintext = ChatColor.GRAY + "You salvaged ";
									String weaponname = is.getItemMeta().getDisplayName();
									String cweaponname = is.getItemMeta().getDisplayName();
									String lore = "";
									for (int i = 0; i < is.getItemMeta().getLore().size(); i++) {
										String s = is.getItemMeta().getLore().get(i);
										if (i < is.getItemMeta().getLore().size() - 1) {
											lore += "\\\"" + s + "\\\",";
										} else {
											lore += "\\\"" + s + "\\\"";
										}
									}
									IChatBaseComponent al = ChatSerializer
											.a("{\"text\":\"" + maintext + "\", \"extra\":[{\"text\":\"" + weaponname
													+ "\",\"color\":\"green\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
													+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
									PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
									((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
								}
								UtilMethods.sendSoundPacket(p, "block.anvil.use", p.getLocation());
								if (event.getSlot() < wp.getWeaponSlot()) {
									wp.setWeaponSlot(wp.getWeaponSlot() - 1);
								}
								wlist.remove(event.getSlot());
								wp.saveWeaponlist(wlist);
								PlayerUtilMethods.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
										"/players/" + p.getUniqueId());
								inventory.setContents(WeaponUtil.getWeaponInventory(p).getContents());
							}
						}
					}
				} else {
					WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/players/" + p.getUniqueId());
					if (wp != null) {
						ArrayList<Weapon> wlist = wp.getWeaponlist();
						if (wlist != null) {
							if (clicked != null && event.getRawSlot() < wlist.size()) {
								wp.setWeaponSlot(event.getSlot());
								PlayerUtilMethods.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
										"/players/" + p.getUniqueId());
								SpielKlasse sk = wp.getSk();
								if (sk != null) {
									UtilMethods.giveItems(sk, false);
								}
							}
						}
					}
				}
				if (event.getRawSlot() == 49) {
					p.openInventory(WeaponUtil.getSmithInventory(p));
				}
			}
			event.setCancelled(true);
		} else if (inventory.getName().equals(WeaponUtil.getSmithInventory(p).getName())) {
			if (event.getRawSlot() == 15) {
				p.openInventory(WeaponUtil.getWeaponInventory(p));
			} else if (event.getRawSlot() == 29) {
				WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
						"/players/" + p.getUniqueId());
				if (wp != null) {
					ArrayList<Weapon> wlist = wp.getWeaponlist();
					if (wlist != null) {
						Weapon w = wlist.get(wp.getWeaponSlot());
						if (w.getKat() < 3 && w.getKat() > 0) {
							if (wp.getVoidShards() >= 1.0 / w.getKat() * 10) {
								p.openInventory(WeaponUtil.getWeaponRerollConfirmationInventory(p, w.getKat()));
							} else {
								p.sendMessage(ChatColor.RED + "You don't have enough " + ChatColor.LIGHT_PURPLE
										+ "Void Shards");
							}
						} else {
							p.sendMessage(ChatColor.RED
									+ "Your equipped weapon has to be at least a epic in order to perform this action.");
						}
					}
				}
			} else if (event.getRawSlot() == 11) {
				p.openInventory(WeaponUtil.getCraftingInventory(p));
			} else if (event.getRawSlot() == 33) {
				int kat = 5;
				WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
						"/players/" + p.getUniqueId());
				if (wp != null) {
					ArrayList<Weapon> wlist = wp.getWeaponlist();
					if (wlist != null) {
						Weapon w = wlist.get(wp.getWeaponSlot());
						kat = w.getKat();
					}
				}
				SkinGUI c = WeaponUtil.getSkinInventory(p, 4, kat);
				Warlords.skingui.add(c);
				p.openInventory(c.getInventory());
			}
			event.setCancelled(true);
		} else if (inventory.getName().equals(WeaponUtil.getCraftingInventory(p).getName())) {
			if (event.getRawSlot() == 33) {
				UpgradeConfirmation c = WeaponUtil.getWeaponUpgradeConfirmationInventory(p, 2);
				Warlords.uconfirmation.add(c);
				p.openInventory(c.getInventory());
			} else if (event.getRawSlot() == 31) {
				UpgradeConfirmation c = WeaponUtil.getWeaponUpgradeConfirmationInventory(p, 1);
				Warlords.uconfirmation.add(c);
				p.openInventory(c.getInventory());
			} else if (event.getRawSlot() == 29) {
				UpgradeConfirmation c = WeaponUtil.getWeaponUpgradeConfirmationInventory(p, 0);
				Warlords.uconfirmation.add(c);
				p.openInventory(c.getInventory());
			} else if (event.getRawSlot() == 15) {
				CraftConfirmation c = WeaponUtil.getWeaponCraftConfirmationInventory(p, 2);
				Warlords.cconfirmation.add(c);
				p.openInventory(c.getInventory());
			} else if (event.getRawSlot() == 13) {
				CraftConfirmation c = WeaponUtil.getWeaponCraftConfirmationInventory(p, 1);
				Warlords.cconfirmation.add(c);
				p.openInventory(c.getInventory());
			} else if (event.getRawSlot() == 11) {
				CraftConfirmation c = WeaponUtil.getWeaponCraftConfirmationInventory(p, 0);
				Warlords.cconfirmation.add(c);
				p.openInventory(c.getInventory());
			} else if (event.getRawSlot() == 49) {
				p.openInventory(WeaponUtil.getSmithInventory(p));
			}
			event.setCancelled(true);
		} else {
			for (int i = 0; i < Warlords.uconfirmation.size(); i++) {
				if (inventory.equals(Warlords.uconfirmation.get(i).getInventory())) {
					UpgradeConfirmation uc = Warlords.uconfirmation.get(i);
					if (uc.getType() == 3) {
						if (event.getRawSlot() == 49) {
							Warlords.uconfirmation.remove(i);
							p.openInventory(WeaponUtil.getCraftingInventory(p));
							return;
						} else {
							if (event.getInventory().getItem(event.getRawSlot()) != null) {
								WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(
										Warlords.getPlugin(Warlords.class).getDataFolder(),
										"/players/" + p.getUniqueId());
								if (wp != null) {
									ArrayList<Weapon> wlist = wp.getWeaponlist();
									if (wlist != null) {
										Weapon w = wlist.get(wp.getWeaponSlot());
										int anz = WeaponUtil.SKILLS[w.getKlass()].length;
										int slota = 18 + 5 - anz;
										int s = (event.getRawSlot() - slota) / 2;
										w.setSkill(s);
										wp.saveWeaponlist(wlist);
										PlayerUtilMethods.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
												"/players/" + p.getUniqueId());
										Warlords.uconfirmation.remove(i);
										SpielKlasse sk = wp.getSk();
										if (sk != null) {
											UtilMethods.giveItems(sk, false);
										}
										p.closeInventory();
									}
								}
							} else {
								event.setCancelled(true);
							}
						}
					} else {
						if (event.getRawSlot() == 11) {
							WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(
									Warlords.getPlugin(Warlords.class).getDataFolder(), "/players/" + p.getUniqueId());
							if (wp != null) {
								ArrayList<Weapon> wlist = wp.getWeaponlist();
								if (wlist != null) {
									Weapon w = wlist.get(wp.getWeaponSlot());
									if (uc.getType() == 0) {
										if (w.getKat() == 1) {
											if (wp.getVoidShards() >= 100) {
												wp.setVoidShards(wp.getVoidShards() - 100);
												w.setSkillboost(true);
											} else {
												p.sendMessage(ChatColor.RED + "You don't have enough "
														+ ChatColor.LIGHT_PURPLE + "Void Shards");
											}
										} else {
											p.sendMessage(ChatColor.GRAY + "You don't have a " + ChatColor.GOLD
													+ "LEGENDARY " + ChatColor.GRAY + "weapon equipped");
										}
									} else if (uc.getType() == 1) {
										if (w.getKat() == 2) {
											if (w.getUa() < w.getUm()) {
												int count = (int) (25 + (w.getUa() * 0.5 + 0.5) * w.getUa() * 12.5);
												if (wp.getVoidShards() >= count) {
													wp.setVoidShards(wp.getVoidShards() - count);
													Weapon neu = WeaponUtil.upgradeWeapon(w);
													ItemStack is = WeaponUtil.generateUpgradeItemStack(w, neu);
													String maintext = ChatColor.GRAY + "Result: ";
													String weaponname = is.getItemMeta().getDisplayName();
													String cweaponname = is.getItemMeta().getDisplayName();
													String lore = "";
													for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
														String s = is.getItemMeta().getLore().get(j);
														if (j < is.getItemMeta().getLore().size() - 1) {
															lore += "\\\"" + s + "\\\",";
														} else {
															lore += "\\\"" + s + "\\\"";
														}
													}
													IChatBaseComponent al = ChatSerializer.a("{\"text\":\"" + maintext
															+ "\", \"extra\":[{\"text\":\"" + weaponname
															+ "\",\"color\":\"dark_purple\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
															+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
													PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
													((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
													w.setDmin(neu.getDmin());
													w.setDmax(neu.getDmax());
													w.setCc(neu.getCc());
													w.setCm(neu.getCm());
													w.setBoost(neu.getBoost());
													w.setHp(neu.getHp());
													w.setEnergy(neu.getEnergy());
													w.setCooldown(neu.getCooldown());
													w.setUa(neu.getUa());
												} else {
													p.sendMessage(ChatColor.RED + "You don't have enough "
															+ ChatColor.LIGHT_PURPLE + "Void Shards");
												}
											} else {
												p.sendMessage(
														ChatColor.RED + "You can't upgrade this weapon any more!");
											}
										} else {
											p.sendMessage(ChatColor.GRAY + "You don't have an " + ChatColor.DARK_PURPLE
													+ "EPIC " + ChatColor.GRAY + "weapon equipped");
										}
									} else if (uc.getType() == 2) {
										if (w.getKat() == 1) {
											if (w.getUa() < w.getUm()) {
												int count = (int) (100 + (w.getUa() * 0.5 + 0.5) * w.getUa() * 50);
												if (wp.getVoidShards() >= count) {
													wp.setVoidShards(wp.getVoidShards() - count);
													Weapon neu = WeaponUtil.upgradeWeapon(w);
													ItemStack is = WeaponUtil.generateUpgradeItemStack(w, neu);
													String maintext = ChatColor.GRAY + "Result: ";
													String weaponname = is.getItemMeta().getDisplayName();
													String cweaponname = is.getItemMeta().getDisplayName();
													String lore = "";
													for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
														String s = is.getItemMeta().getLore().get(j);
														if (j < is.getItemMeta().getLore().size() - 1) {
															lore += "\\\"" + s + "\\\",";
														} else {
															lore += "\\\"" + s + "\\\"";
														}
													}
													IChatBaseComponent al = ChatSerializer.a("{\"text\":\"" + maintext
															+ "\", \"extra\":[{\"text\":\"" + weaponname
															+ "\",\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
															+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
													PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
													((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
													w.setDmin(neu.getDmin());
													w.setDmax(neu.getDmax());
													w.setCc(neu.getCc());
													w.setCm(neu.getCm());
													w.setBoost(neu.getBoost());
													w.setHp(neu.getHp());
													w.setEnergy(neu.getEnergy());
													w.setCooldown(neu.getCooldown());
													w.setSpeed(neu.getSpeed());
													w.setUa(neu.getUa());
												} else {
													p.sendMessage(ChatColor.RED + "You don't have enough "
															+ ChatColor.LIGHT_PURPLE + "Void Shards");
												}
											} else {
												p.sendMessage(
														ChatColor.RED + "You can't upgrade this weapon any more!");
											}
										} else {
											p.sendMessage(ChatColor.GRAY + "You don't have a " + ChatColor.GOLD
													+ "LEGENDARY " + ChatColor.GRAY + "weapon equipped");
										}
									}
									wp.saveWeaponlist(wlist);
									PlayerUtilMethods.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
											"/players/" + p.getUniqueId());
									Warlords.uconfirmation.remove(i);
									SpielKlasse sk = wp.getSk();
									if (sk != null) {
										UtilMethods.giveItems(sk, false);
									}
									p.closeInventory();
								}
							}
						} else if (event.getRawSlot() == 15) {
							Warlords.uconfirmation.remove(i);
							p.openInventory(WeaponUtil.getCraftingInventory(p));
							return;
						} else {
							event.setCancelled(true);
						}
					}
				}
			}
			for (int i = 0; i < Warlords.cconfirmation.size(); i++) {
				if (inventory.equals(Warlords.cconfirmation.get(i).getInventory())) {
					if (event.getRawSlot() == 11) {
						CraftConfirmation uc = Warlords.cconfirmation.get(i);
						WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
								"/players/" + p.getUniqueId());
						if (wp != null) {
							ArrayList<Weapon> wlist = wp.getWeaponlist();
							if (wlist != null) {
								if (uc.getType() == 0) {
									if (wp.getVoidShards() >= 5) {
										int k = WeaponUtil.getKlass(wp.getKlasse());
										if (k >= 0) {
											wp.setVoidShards(wp.getVoidShards() - 5);
											Weapon w = WeaponUtil.craftWeapon(3, k);
											ItemStack is = WeaponUtil.generateItemStack(w, w.getTitle());
											String maintext = ChatColor.GRAY + "You crafted: ";
											String weaponname = is.getItemMeta().getDisplayName();
											String cweaponname = is.getItemMeta().getDisplayName();
											String lore = "";
											for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
												String s = is.getItemMeta().getLore().get(j);
												if (j < is.getItemMeta().getLore().size() - 1) {
													lore += "\\\"" + s + "\\\",";
												} else {
													lore += "\\\"" + s + "\\\"";
												}
											}
											IChatBaseComponent al = ChatSerializer.a("{\"text\":\"" + maintext
													+ "\", \"extra\":[{\"text\":\"" + weaponname
													+ "\",\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
													+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
											PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
											((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
											wlist.add(w);
										} else {
											p.sendMessage(ChatColor.RED + "You don't have a class selected");
										}
									} else {
										p.sendMessage(ChatColor.RED + "You don't have enough " + ChatColor.LIGHT_PURPLE
												+ "Void Shards");
									}
								} else if (uc.getType() == 1) {
									if (wp.getVoidShards() >= 25) {
										int k = WeaponUtil.getKlass(wp.getKlasse());
										if (k >= 0) {
											wp.setVoidShards(wp.getVoidShards() - 25);
											Weapon w = WeaponUtil.craftWeapon(2, k);
											ItemStack is = WeaponUtil.generateItemStack(w, w.getTitle());
											String maintext = p.getDisplayName() + " crafted a ";
											String weaponname = is.getItemMeta().getDisplayName();
											String cweaponname = is.getItemMeta().getDisplayName();
											String lore = "";
											for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
												String s = is.getItemMeta().getLore().get(j);
												if (j < is.getItemMeta().getLore().size() - 1) {
													lore += "\\\"" + s + "\\\",";
												} else {
													lore += "\\\"" + s + "\\\"";
												}
											}
											IChatBaseComponent al = ChatSerializer.a("{\"text\":\"" + maintext
													+ "\", \"extra\":[{\"text\":\"" + weaponname
													+ "\",\"color\":\"dark_purple\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
													+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
											PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
											for (Player p2 : Bukkit.getOnlinePlayers()) {
												UtilMethods.sendSoundPacket(p2, "epicfind", p2.getLocation());
												((CraftPlayer) p2).getHandle().playerConnection.sendPacket(packet);
											}
											wlist.add(w);
										} else {
											p.sendMessage(ChatColor.RED + "You don't have a class selected");
										}
									} else {
										p.sendMessage(ChatColor.RED + "You don't have enough " + ChatColor.LIGHT_PURPLE
												+ "Void Shards");
									}
								} else if (uc.getType() == 2) {
									if (wp.getVoidShards() >= 100) {
										int k = WeaponUtil.getKlass(wp.getKlasse());
										if (k >= 0) {
											wp.setVoidShards(wp.getVoidShards() - 100);
											Weapon w = WeaponUtil.craftWeapon(1, k);
											ItemStack is = WeaponUtil.generateItemStack(w, w.getTitle());
											String maintext = p.getDisplayName() + " crafted a ";
											String weaponname = is.getItemMeta().getDisplayName();
											String cweaponname = is.getItemMeta().getDisplayName();
											String lore = "";
											for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
												String s = is.getItemMeta().getLore().get(j);
												if (j < is.getItemMeta().getLore().size() - 1) {
													lore += "\\\"" + s + "\\\",";
												} else {
													lore += "\\\"" + s + "\\\"";
												}
											}
											IChatBaseComponent al = ChatSerializer.a("{\"text\":\"" + maintext
													+ "\", \"extra\":[{\"text\":\"" + weaponname
													+ "\",\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
													+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
											PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
											for (Player p2 : Bukkit.getOnlinePlayers()) {
												UtilMethods.sendSoundPacket(p2, "legendaryfind", p2.getLocation());
												((CraftPlayer) p2).getHandle().playerConnection.sendPacket(packet);
											}
											wlist.add(w);
										} else {
											p.sendMessage(ChatColor.RED + "You don't have a class selected");
										}
									} else {
										p.sendMessage(ChatColor.RED + "You don't have enough " + ChatColor.LIGHT_PURPLE
												+ "Void Shards");
									}
								}
								wp.saveWeaponlist(wlist);
								PlayerUtilMethods.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
										"/players/" + p.getUniqueId());
								Warlords.cconfirmation.remove(i);
								SpielKlasse sk = wp.getSk();
								if (sk != null) {
									UtilMethods.giveItems(sk, false);
								}
								p.closeInventory();
							}
						}
					} else if (event.getRawSlot() == 15) {
						Warlords.cconfirmation.remove(i);
						p.openInventory(WeaponUtil.getCraftingInventory(p));
					} else {
						event.setCancelled(true);
					}
				}
			}
			for (int i = 0; i < Warlords.skingui.size(); i++) {
				if (inventory.equals(Warlords.skingui.get(i).getInventory())) {
					SkinGUI sk = Warlords.skingui.get(i);
					if (event.getRawSlot() == 49) {
						p.openInventory(WeaponUtil.getSmithInventory(p));
					} else if (event.getRawSlot() == 50 && sk.getInventory().getItem(50) != null) {
						Warlords.skingui.remove(i);
						int kat = 5;
						WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
								"/players/" + p.getUniqueId());
						if (wp != null) {
							ArrayList<Weapon> wlist = wp.getWeaponlist();
							if (wlist != null) {
								Weapon w = wlist.get(wp.getWeaponSlot());
								kat = w.getKat();
							}
						}
						SkinGUI sk2 = WeaponUtil.getSkinInventory(p, sk.getType() - 1, kat);
						Warlords.skingui.add(sk2);
						p.openInventory(sk2.getInventory());
					} else if (event.getRawSlot() == 48 && sk.getInventory().getItem(48) != null) {
						Warlords.skingui.remove(i);
						int kat = 5;
						WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
								"/players/" + p.getUniqueId());
						if (wp != null) {
							ArrayList<Weapon> wlist = wp.getWeaponlist();
							if (wlist != null) {
								Weapon w = wlist.get(wp.getWeaponSlot());
								kat = w.getKat();
							}
						}
						SkinGUI sk2 = WeaponUtil.getSkinInventory(p, sk.getType() + 1, kat);
						Warlords.skingui.add(sk2);
						p.openInventory(sk2.getInventory());
					} else if (event.getInventory().getItem(event.getRawSlot()) != null) {
						int kat = 5;
						WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
								"/players/" + p.getUniqueId());
						if (wp != null) {
							ArrayList<Weapon> wlist = wp.getWeaponlist();
							if (wlist != null) {
								Weapon w = wlist.get(wp.getWeaponSlot());
								kat = w.getKat();
							}
						}
						if (kat <= sk.getType()) {
							if (wp != null) {
								ArrayList<Weapon> wlist = wp.getWeaponlist();
								if (wlist != null) {
									Weapon w = wlist.get(wp.getWeaponSlot());
									int slot = (event.getRawSlot() - 10) - ((int) ((event.getRawSlot() - 9) / 9)) * 2;
									if (sk.getType() == w.getSkin() && slot == w.getType()) {
										ItemStack is = WeaponUtil.generateItemStack(w,
												WeaponUtil.KLASSEN[w.getKlass()]);
										String maintext = ChatColor.GRAY + "You received: ";
										String weaponname = is.getItemMeta().getDisplayName();
										String cweaponname = is.getItemMeta().getDisplayName();
										String lore = "";
										for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
											String s = is.getItemMeta().getLore().get(j);
											if (j < is.getItemMeta().getLore().size() - 1) {
												lore += "\\\"" + s + "\\\",";
											} else {
												lore += "\\\"" + s + "\\\"";
											}
										}
										IChatBaseComponent al = ChatSerializer.a(
												"{\"text\":\"" + maintext + "\", \"extra\":[{\"text\":\"" + weaponname
														+ "\",\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
														+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
										PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
										((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
									} else {
										if (wp.getVoidShards() >= 5) {
											w.setSkin(sk.getType());
											w.setType(slot);
											wp.setVoidShards(wp.getVoidShards() - 5);
											wp.saveWeaponlist(wlist);
											PlayerUtilMethods.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
													"/players/" + p.getUniqueId());
											ItemStack is = WeaponUtil.generateItemStack(w,
													WeaponUtil.KLASSEN[w.getKlass()]);
											String maintext = ChatColor.GRAY + "You received: ";
											String weaponname = is.getItemMeta().getDisplayName();
											String cweaponname = is.getItemMeta().getDisplayName();
											String lore = "";
											for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
												String s = is.getItemMeta().getLore().get(j);
												if (j < is.getItemMeta().getLore().size() - 1) {
													lore += "\\\"" + s + "\\\",";
												} else {
													lore += "\\\"" + s + "\\\"";
												}
											}
											IChatBaseComponent al = ChatSerializer.a("{\"text\":\"" + maintext
													+ "\", \"extra\":[{\"text\":\"" + weaponname
													+ "\",\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
													+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
											PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
											((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
										} else {
											p.sendMessage(ChatColor.RED + "You don't have enough "
													+ ChatColor.LIGHT_PURPLE + "Void Shards");
										}
									}
								}
							}

							Warlords.skingui.remove(i);
							SpielKlasse sp = wp.getSk();
							if (sp != null) {
								UtilMethods.giveItems(sp, false);
							}
							p.closeInventory();
						} else {
							if (sk.getType() == 4) {
								p.sendMessage("");
							} else if (sk.getType() == 3) {
								p.sendMessage("");
							} else if (sk.getType() == 2) {
								p.sendMessage("");
							} else if (sk.getType() == 1) {
								p.sendMessage("");
							}
						}
					}
					event.setCancelled(true);
				}
			}
			for (int i = 0; i < Warlords.confirmation.size(); i++) {
				if (inventory.equals(Warlords.confirmation.get(i).getInventory())) {
					if (event.getRawSlot() == 11) {
						Confirmation c = Warlords.confirmation.get(i);
						WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
								"/players/" + p.getUniqueId());
						if (wp != null) {
							ArrayList<Weapon> wlist = wp.getWeaponlist();
							if (wlist != null) {
								Weapon w = wlist.get(c.getSlot());
								if (w.getKat() == 1) {
									ItemStack is = WeaponUtil.generateItemStack(w, WeaponUtil.KLASSEN[w.getKlass()]);
									double rand = Math.random();
									int count = (int) (30 + Math.round(10.0 * rand));
									wp.setVoidShards(wp.getVoidShards() + count);
									String maintext = ChatColor.GRAY + "You received " + ChatColor.LIGHT_PURPLE + count
											+ " Void Shards " + ChatColor.GRAY + "for salvaging your ";
									String weaponname = is.getItemMeta().getDisplayName();
									String cweaponname = is.getItemMeta().getDisplayName();
									String lore = "";
									for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
										String s = is.getItemMeta().getLore().get(j);
										if (j < is.getItemMeta().getLore().size() - 1) {
											lore += "\\\"" + s + "\\\",";
										} else {
											lore += "\\\"" + s + "\\\"";
										}
									}
									IChatBaseComponent al = ChatSerializer
											.a("{\"text\":\"" + maintext + "\", \"extra\":[{\"text\":\"" + weaponname
													+ "\",\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
													+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
									PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
									((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
								} else if (w.getKat() == 2) {
									ItemStack is = WeaponUtil.generateItemStack(w, WeaponUtil.KLASSEN[w.getKlass()]);
									double rand = Math.random();
									int count = 3;
									if (rand < 0.5) {
										count += 1;
									}
									wp.setVoidShards(wp.getVoidShards() + count);
									String maintext = ChatColor.GRAY + "You received " + ChatColor.LIGHT_PURPLE + count
											+ " Void Shards " + ChatColor.GRAY + "for salvaging your ";
									String weaponname = is.getItemMeta().getDisplayName();
									String cweaponname = is.getItemMeta().getDisplayName();
									String lore = "";
									for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
										String s = is.getItemMeta().getLore().get(j);
										if (j < is.getItemMeta().getLore().size() - 1) {
											lore += "\\\"" + s + "\\\",";
										} else {
											lore += "\\\"" + s + "\\\"";
										}
									}
									IChatBaseComponent al = ChatSerializer
											.a("{\"text\":\"" + maintext + "\", \"extra\":[{\"text\":\"" + weaponname
													+ "\",\"color\":\"dark_purple\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
													+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
									PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
									((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
								}
								UtilMethods.sendSoundPacket(p, "block.anvil.use", p.getLocation());
								if (c.getSlot() < wp.getWeaponSlot()) {
									wp.setWeaponSlot(wp.getWeaponSlot() - 1);
								}
								wlist.remove(c.getSlot());
								wp.saveWeaponlist(wlist);
								PlayerUtilMethods.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
										"/players/" + p.getUniqueId());
								Warlords.confirmation.remove(i);
								p.openInventory(WeaponUtil.getWeaponInventory(p));
								return;
							}
						}
					} else if (event.getRawSlot() == 15) {
						Warlords.confirmation.remove(i);
						p.openInventory(WeaponUtil.getWeaponInventory(p));
						return;
					} else {
						event.setCancelled(true);
					}
				}
			}
			WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
					"/players/" + p.getUniqueId());
			if (wp != null) {
				ArrayList<Weapon> wlist = wp.getWeaponlist();
				if (wlist != null) {
					Weapon w = wlist.get(wp.getWeaponSlot());
					if (inventory.getName()
							.equals(WeaponUtil.getWeaponRerollConfirmationInventory(p, w.getKat()).getName())) {
						if (event.getRawSlot() == 11) {
							Weapon neu = WeaponUtil.generateRandomWeapon(w.getKat());
							for (int j = 0; j < w.getUa(); j++) {
								Weapon up = WeaponUtil.upgradeWeapon(neu);
								neu.setDmin(up.getDmin());
								neu.setDmax(up.getDmax());
								neu.setCc(up.getCc());
								neu.setCm(up.getCm());
								neu.setBoost(up.getBoost());
								neu.setHp(up.getHp());
								neu.setEnergy(up.getEnergy());
								neu.setCooldown(up.getCooldown());
								neu.setSpeed(up.getSpeed());
								neu.setUa(up.getUa());
							}
							Weapon alt = w;
							if (w.getKat() == 1) {
								ItemStack is = WeaponUtil.generateChangeItemStack(alt, neu);
								String maintext = ChatColor.GRAY + "Result: ";
								String weaponname = is.getItemMeta().getDisplayName();
								String cweaponname = is.getItemMeta().getDisplayName();
								String lore = "";
								for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
									String s = is.getItemMeta().getLore().get(j);
									if (j < is.getItemMeta().getLore().size() - 1) {
										lore += "\\\"" + s + "\\\",";
									} else {
										lore += "\\\"" + s + "\\\"";
									}
								}
								IChatBaseComponent al = ChatSerializer
										.a("{\"text\":\"" + maintext + "\", \"extra\":[{\"text\":\"" + weaponname
												+ "\",\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
												+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
								PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
								((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
								w.setDmin(neu.getDmin());
								w.setDmax(neu.getDmax());
								w.setCc(neu.getCc());
								w.setCm(neu.getCm());
								w.setBoost(neu.getBoost());
								w.setHp(neu.getHp());
								w.setEnergy(neu.getEnergy());
								w.setCooldown(neu.getCooldown());
								w.setSpeed(neu.getSpeed());
								wp.setVoidShards(wp.getVoidShards() - 10);
							} else if (w.getKat() == 2) {
								ItemStack is = WeaponUtil.generateChangeItemStack(alt, neu);
								String maintext = ChatColor.GRAY + "Result: ";
								String weaponname = WeaponUtil.EPICNAMES[w.getType()] + " of the "
										+ WeaponUtil.KLASSEN[w.getKlass()];
								String cweaponname = ChatColor.DARK_PURPLE + WeaponUtil.EPICNAMES[w.getType()]
										+ " of the " + WeaponUtil.KLASSEN[w.getKlass()];
								String lore = "";
								for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
									String s = is.getItemMeta().getLore().get(j);
									if (j < is.getItemMeta().getLore().size() - 1) {
										lore += "\\\"" + s + "\\\",";
									} else {
										lore += "\\\"" + s + "\\\"";
									}
								}
								IChatBaseComponent al = ChatSerializer
										.a("{\"text\":\"" + maintext + "\", \"extra\":[{\"text\":\"" + weaponname
												+ "\",\"color\":\"dark_purple\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
												+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
								PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
								((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
								w.setDmin(neu.getDmin());
								w.setDmax(neu.getDmax());
								w.setCc(neu.getCc());
								w.setCm(neu.getCm());
								w.setBoost(neu.getBoost());
								w.setHp(neu.getHp());
								w.setEnergy(neu.getEnergy());
								w.setCooldown(neu.getCooldown());
								wp.setVoidShards(wp.getVoidShards() - 5);
							}
							wp.saveWeaponlist(wlist);
							PlayerUtilMethods.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
									"/players/" + p.getUniqueId());
							SpielKlasse sk = wp.getSk();
							if (sk != null) {
								UtilMethods.giveItems(sk, false);
							}
							p.closeInventory();
						} else if (event.getRawSlot() == 15) {
							p.openInventory(WeaponUtil.getSmithInventory(p));
						} else {
							event.setCancelled(true);
						}
					}
				}
			}
			SpielKlasse sk = Warlords.getKlasse(p);
			if (sk != null && wp != null) {
				if (wp.getMode() == true) {
					Integer slotClicked = event.getRawSlot();
					if (slotClicked < inventory.getSize()) {
					} else {
						if (event.getSlot() < 9) {
							if (event.getSlot() == 0) {
								if (event.getClick() == ClickType.RIGHT) {
									sk.setMainAbility();
								} else if (event.getClick() == ClickType.LEFT) {
									sk.p.getInventory().setItem(0,
											WeaponUtil.generateItemStack(sk.getWeapon(), sk.getName()));
								}
							}
							event.setCancelled(true);
						}
					}
					if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT
							|| event.getClick() == ClickType.DOUBLE_CLICK) {
						event.setCancelled(true);
					}
					if (event.getAction() == InventoryAction.HOTBAR_SWAP
							|| event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) {
						event.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void logOut(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		SpielKlasse sk = Warlords.getKlasse(p);
		if (sk != null) {
			Warlords.player.remove(sk);
			SkillUtil.removeAbilitys(p);
			sk.removeHealthScale();
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void skillhit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Arrow) {
			if (SkillUtil.isFireball(event.getEntity())) {
				SkillUtil.doFireballHit(event.getEntity());
			} else if (SkillUtil.isFlameburst(event.getEntity())) {
				SkillUtil.doFlameburstHit(event.getEntity());
			} else if (SkillUtil.isElementelarrow(event.getEntity())) {
				SkillUtil.doElementelarrowHit(event.getEntity());
			} else if (SkillUtil.isCatBolt(event.getEntity())) {
				SkillUtil.doCatboltHit(event.getEntity());
			}
		}
	}

	@EventHandler
	public void livingEntityEvent(EntityTargetLivingEntityEvent event) {
		if (event.getTarget() != null) {
			for (Companion e : Warlords.companion) {
				if (e.getEndermite().getUniqueId().compareTo(event.getEntity().getUniqueId()) == 0
						&& e.getPlayer().getUniqueId().compareTo(event.getTarget().getUniqueId()) == 0) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		SpielKlasse sk = Warlords.getKlasse(p);
		WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/players/" + p.getUniqueId());
		if (sk != null && wp != null) {
			if (wp.getMode() == true) {
				ItemStack is = e.getItemDrop().getItemStack();
				for (int i = 0; i < 9; i++) {
					if (is != null && sk.getAbility(i) != null) {
						if (sk.getAbility(i).equals(is) || (is.getType() == Material.INK_SACK
								&& (is.getDurability() == 8 || is.getDurability() == 15))) {
							e.setCancelled(true);
							return;
						}

					}
					if (is != null && sk.getMainAbility() != null) {
						if (sk.getMainAbility().equals(is) || (is.getType() == Material.INK_SACK
								&& (is.getDurability() == 8 || is.getDurability() == 15))) {
							e.setCancelled(true);
							return;
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void PickupItem(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		SpielKlasse sk = Warlords.getKlasse(p);
		WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/players/" + p.getUniqueId());
		if (sk != null && wp != null) {
			if (wp.getMode() == true) {
				ItemStack is = e.getItem().getItemStack();
				for (int i = 9; i < 36; i++) {
					ItemStack in = p.getInventory().getItem(i);
					if (in != null) {
						if (in.getType().equals(is.getType()) && in.getItemMeta().equals(is.getItemMeta())
								&& in.getDurability() == is.getDurability()
								&& in.getEnchantments().equals(is.getEnchantments())) {
							int x = in.getMaxStackSize() - in.getAmount();
							if (x > 0) {
								if (x > is.getAmount()) {
									in.setAmount(in.getAmount() + is.getAmount());
									e.getItem().remove();
									e.setCancelled(true);
									return;
								} else {
									in.setAmount(in.getAmount() + x);
									is.setAmount(is.getAmount() - x);
								}
							}
						}
					}
				}
				for (int i = 9; i < 36; i++) {
					ItemStack in = p.getInventory().getItem(i);
					if (in == null) {
						p.getInventory().setItem(i, is);
						e.getItem().remove();
						e.setCancelled(true);
						return;
					}
				}
				e.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSkillPlayerHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player p = (Player) event.getDamager();
			SpielKlasse sk = Warlords.getKlasse(p);
			WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
					"/players/" + p.getUniqueId());
			if (wp != null) {
				if (wp.getMode() == true) {
					if (sk != null) {
						if (!Warlords.isUsingVanish(sk.p)) {
							if (event.getEntity() instanceof Player) {
								Player victim = (Player) event.getEntity();
								WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
								if (!a.hasPlayer(victim)) {
									sk.addEnergy(sk.getEPH());
								}
							} else if (event.getEntity() instanceof LivingEntity
									&& !(event.getEntity() instanceof ArmorStand)) {
								sk.addEnergy(sk.getEPH());
							}
						}
						if (Warlords.isUsingStealth(p)) {
							if (sk instanceof Assassin) {
								for (int j = 0; j < 9; j++) {
									if (j != 3) {
										ItemStack is = p.getInventory().getItem(j);
										if (is != null) {
											if (is.getType() == Material.INK_SACK) {
												if (is.getDurability() == 8) {
													if (is.getAmount() > 2) {
														is.setAmount(is.getAmount() - 2);
													} else {
														sk.addAbility(j);
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
							}
						}
						if (event.getEntity() instanceof Player) {
							Player victim = (Player) event.getEntity();
							SpielKlasse sk2 = Warlords.getKlasse(victim);
							if (sk2 != null) {
								if (p.getInventory().getHeldItemSlot() == 0) {
									if (sk.getWeapon() != null) {
										if (!Warlords.isUsingVanish(sk.p)) {
											WarlordsPlayerAllys a = new WarlordsPlayerAllys(p);
											if (!a.hasPlayer(victim)) {
												double dmg = UtilMethods.melee(sk.getWeapon(), p, sk2);
												sk2.removeHealth(sk2.hptohealth(dmg));
												for (int i = 0; i < Warlords.windfury.size(); i++) {
													WindfuryWeapon ww = Warlords.windfury.get(i);
													if (ww.getPlayer().getUniqueId().compareTo(p.getUniqueId()) == 0) {
														double rand = Math.random();
														if (rand < ww.chance()) {
															for (int j = 0; j < ww.count(); j++) {
																double dmg2 = UtilMethods.damage("Windfury Weapon", 0.0,
																		1.0, dmg * ww.mul(), dmg * ww.mul(), p, sk2);
																sk2.removeHealth(sk2.hptohealth(dmg2));
																Bukkit.getServer().getScheduler()
																		.scheduleSyncDelayedTask(
																				JavaPlugin.getPlugin(Warlords.class),
																				new Runnable() {
																					public void run() {
																						victim.getWorld().playSound(
																								victim.getLocation(),
																								"shaman.windfuryweapon.impact",
																								1, 1);
																					}
																				}, 7L * j);
															}
														}
													}
												}
											}
										}
									}
								}
							}
						} else {
							if (event.getEntity() instanceof LivingEntity
									&& !(event.getEntity() instanceof ArmorStand)) {
								if (!Warlords.isUsingVanish(sk.p)) {
									LivingEntity e = (LivingEntity) event.getEntity();
									if (p.getInventory().getHeldItemSlot() == 0) {
										double dmg = UtilMethods.hptohealth(UtilMethods.melee(sk.getWeapon(), p));
										double hp = e.getHealth();
										if (hp < dmg) {
											WeaponUtil.doWeapon(e, p);
										}
										UtilMethods.setHealth(e, dmg);
										for (int i = 0; i < Warlords.windfury.size(); i++) {
											WindfuryWeapon ww = Warlords.windfury.get(i);
											if (ww.getPlayer().getUniqueId().compareTo(p.getUniqueId()) == 0) {
												double rand = Math.random();
												if (rand < ww.chance()) {
													for (int j = 0; j < ww.count(); j++) {
														double dmg2 = UtilMethods.hptohealth(UtilMethods.damage(0.0,
																1.0, UtilMethods.healthtohp(dmg) * ww.mul(),
																UtilMethods.healthtohp(dmg) * ww.mul(), p,
																"Windfury Weapon"));
														if (hp < dmg2) {
															WeaponUtil.doWeapon(e, p);
														}
														UtilMethods.setHealth(e, dmg2);
														Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(
																JavaPlugin.getPlugin(Warlords.class), new Runnable() {
																	public void run() {
																		e.getWorld().playSound(e.getLocation(),
																				"shaman.windfuryweapon.impact", 1, 1);
																	}
																}, 7L * j);
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
		}
	}

	@EventHandler
	public void blockEvents(FoodLevelChangeEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			Player p = (Player) entity;
			SpielKlasse sk = Warlords.getKlasse(p);
			if (sk != null) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPEntitylick(PlayerInteractAtEntityEvent e) {
		Player p = e.getPlayer();
		if (p.getInventory().getItem(p.getInventory().getHeldItemSlot()) != null) {
			SpielKlasse sp = Warlords.getKlasse(p);
			if (sp != null) {
				WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
						"/players/" + p.getUniqueId());
				if (wp != null) {
					if (wp.getMode() == true) {
						if (sp instanceof Assassin) {
							Assassin as = (Assassin) sp;
							if (p.getInventory().getHeldItemSlot() == 4 && p.getInventory().getItem(4) != null) {
								ItemStack is = p.getInventory().getItem(4);
								if (!(is.getDurability() == 8) && !(is.getDurability() == 15)) {
									if (e.getRightClicked() instanceof LivingEntity) {
										LivingEntity le = (LivingEntity) e.getRightClicked();
										if (!(le instanceof ArmorStand)) {
											if (le.isDead() == false) {
												SkillUtil.doDeadlyPoison(as.getPoisonMax(), as.getPoisonOwn(), p, le);
												sp.doCooldown(4);
											}
										}
									}
								}
							}
						}
						p.getInventory().setContents(p.getInventory().getContents());
						// sp.doAbility(p.getInventory().getHeldItemSlot());
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = event.getPlayer();
			if (event.getItem() != null) {
				SpielKlasse sp = Warlords.getKlasse(p);
				if (sp != null) {
					WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/players/" + p.getUniqueId());
					if (wp != null) {
						if (wp.getMode() == true) {
							int slot = event.getPlayer().getInventory().getHeldItemSlot();
							sp.doAbility(slot);
							event.setCancelled(true);
						}
					}
				}
			}
		}
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Player p = event.getPlayer();
			if (event.getItem() != null) {
				int slot = event.getPlayer().getInventory().getHeldItemSlot();
				SpielKlasse sp = Warlords.getKlasse(p);
				if (sp != null) {
					WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/players/" + p.getUniqueId());
					if (wp != null) {
						if (wp.getMode() == true) {
							if (slot == 0) {
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}

	}

	@EventHandler
	public void onRegeneration(EntityRegainHealthEvent event) {
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			SpielKlasse sp = Warlords.getKlasse(p);
			if (sp != null) {
				WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
						"/players/" + p.getUniqueId());
				if (wp != null) {
					if (wp.getMode()) {
					}
					// event.setCancelled(true);
				}
			}
		}
		if (event.getEntity() instanceof Wither) {
			event.setAmount(event.getAmount() / 2.0);
		}
	}
}
