package com.warlords.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.warlords.boss.Pontiff;
import com.warlords.util.PlayerUtil;
import com.warlords.util.SkillUtil;
import com.warlords.util.SpielKlasse;
import com.warlords.util.UtilMethods;
import com.warlords.util.WarlordsPlayer;
import com.warlords.util.WarlordsPlayerAllys;
import com.warlords.util.Weapon;
import com.warlords.util.WeaponUtil;
import com.warlords.util.skills.hunter.Companion;
import com.warlords.util.type.Assassin;

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
						WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
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
					WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
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
		WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
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
			WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
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
	public void onInventoryClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(WeaponUtil.getWeaponInventory(p).getName())) {
			if (inventory.getHolder().equals(p)) {
				if (event.getClick() == ClickType.RIGHT) {
					WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/players/" + p.getUniqueId());
					if (wp != null) {
						ArrayList<Weapon> wlist = wp.getWeaponlist();
						if (wlist != null) {
							if (clicked != null && event.getRawSlot() < wlist.size()
									&& event.getSlot() != wp.getWeaponSlot()) {
								Weapon w = wlist.get(event.getRawSlot());
								if (w.getKat() == 1) {
									p.sendMessage("You salvaged " + ChatColor.GOLD + WeaponUtil.LEGENDNAMES[w.getType()]
											+ " of the " + WeaponUtil.KLASSEN[w.getKlass()]);
								} else if (w.getKat() == 2) {
									p.sendMessage(
											"You salvaged " + ChatColor.DARK_PURPLE + WeaponUtil.EPICNAMES[w.getType()]
													+ " of the " + WeaponUtil.KLASSEN[w.getKlass()]);
								} else if (w.getKat() == 3) {
									p.sendMessage("You salvaged " + ChatColor.BLUE + WeaponUtil.RARENAMES[w.getType()]
											+ " of the " + WeaponUtil.KLASSEN[w.getKlass()]);
								} else {
									p.sendMessage(
											"You salvaged " + ChatColor.GREEN + WeaponUtil.COMMONNAMES[w.getType()]
													+ " of the " + WeaponUtil.KLASSEN[w.getKlass()]);
								}
								if (event.getSlot() < wp.getWeaponSlot()) {
									wp.setWeaponSlot(wp.getWeaponSlot() - 1);
								}
								wlist.remove(event.getSlot());
								wp.saveWeaponlist(wlist);
								PlayerUtil.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
										"/players/" + p.getUniqueId());
								inventory.setContents(WeaponUtil.getWeaponInventory(p).getContents());
							}
						}
					}
				} else {
					WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/players/" + p.getUniqueId());
					if (wp != null) {
						ArrayList<Weapon> wlist = wp.getWeaponlist();
						if (wlist != null) {
							if (clicked != null && event.getRawSlot() < wlist.size()) {
								wp.setWeaponSlot(event.getSlot());
								PlayerUtil.save(wp, Warlords.getPlugin(Warlords.class).getDataFolder(),
										"/players/" + p.getUniqueId());
								SpielKlasse sk = wp.getSk();
								if (sk != null) {
									UtilMethods.giveItems(sk, false);
								}
							}
						}
					}
				}
			}
			event.setCancelled(true);
		} else {
			SpielKlasse sk = Warlords.getKlasse(p);
			WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
					"/players/" + p.getUniqueId());
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
		WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
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
		WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
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
			WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
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
												sk2.removeHealth(
														sk2.hptohealth(UtilMethods.melee(sk.getWeapon(), p, sk2)));
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
	public void onPigClick(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (p.getInventory().getItem(p.getInventory().getHeldItemSlot()) != null) {
			SpielKlasse sp = Warlords.getKlasse(p);
			if (sp != null) {
				WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
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
												as.doCooldown(4);
											}
										}
									}
								}
							}
						}
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK
				|| event.getAction() == Action.PHYSICAL) {
			Player p = event.getPlayer();
			if (event.getItem() != null) {
				int slot = event.getPlayer().getInventory().getHeldItemSlot();
				SpielKlasse sp = Warlords.getKlasse(p);
				if (sp != null) {
					WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/players/" + p.getUniqueId());
					if (wp != null) {
						if (wp.getMode() == true) {
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
					WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
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
				WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
						"/players/" + p.getUniqueId());
				if (wp != null) {
					// event.setCancelled(true);
				}
			}
		}
		if (event.getEntity() instanceof Wither) {
			event.setAmount(event.getAmount() / 2.0);
		}
	}
}
