package com.warlords.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.warlords.main.Warlords;
import com.warlords.util.data.WeaponUtilMethods;
import com.warlords.util.data.inventory.Confirmation;
import com.warlords.util.data.inventory.CraftConfirmation;
import com.warlords.util.data.inventory.SkinGUI;
import com.warlords.util.data.inventory.UpgradeConfirmation;
import com.warlords.util.data.types.SpielKlasse;
import com.warlords.util.data.types.WarlordsPlayer;
import com.warlords.util.data.types.Weapon;
import com.warlords.util.data.PlayerUtilMethods;
import com.warlords.util.itemlist.ItemListGenerel;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;

public class WeaponUtil {
	public static final String[] KLASSEN = { "Pyromancer", "Assassin", "Avenger", "Hunter", "Crusader" };
	public static final String[][] SKILLS = { new String[] { "Fireball", "Flameburst" },
			new String[] { "Shadow Step", "Back Stab", "Deadly Poison" },
			new String[] { "Avenger's Strike", "Consecrate" }, new String[] { "Elemental Arrow", "Explosive Arrow" },
			new String[] { "Crusader's Strike", "Consecrate" } };
	public static final String[] COMMONNAMES = { "Hammer", "Scimitar", "Orc Axe", "Steel Sword", "Bludgeon",
			"Training Sword", "Walking Stick", "Claws", "Hatchet", "Pike" }; // 11
	public static final String[] RARENAMES = { "World Tree Branch", "Mandibles", "Gem Axe", "Demonblade", "Cludgel",
			"Golden Gladius", "Doubleaxe", "Stone Mallet", "Venomstrike", "Halbert" }; // 10
	public static final String[] EPICNAMES = { "Diamondspark", "Flameweaver", "Elven Greatsword", "Gemcrusher",
			"Runic Axe", "Divine Reach", "Zweireaper", "Tenderizer", "Magmasword", "Runeblade", "Hammer of Light",
			"Katar", "Nomegusta", "Nethersteel Katana", "Lunar Relic" }; // 15
	public static final String[] LEGENDNAMES = { "Felflame Blade", "Void Edge", "Amaranth", "Armblade", "Gemini",
			"Drakefang", "Abbadon", "Void Twig", "Frostbite", "Enderfist", "Ruby Thorn", "Broccomace" }; // 12
	public static final ItemStack[] COMMONS = { new ItemStack(Material.IRON_SPADE),
			new ItemStack(Material.RAW_FISH, 1, (short) 1), new ItemStack(Material.PUMPKIN_PIE),
			new ItemStack(Material.WOOD_AXE), new ItemStack(Material.RABBIT_STEW), new ItemStack(Material.STONE_AXE),
			new ItemStack(Material.STONE_PICKAXE), new ItemStack(Material.MUTTON), new ItemStack(Material.GOLD_HOE),
			new ItemStack(Material.ROTTEN_FLESH) };
	public static final ItemStack[] RARES = { new ItemStack(Material.IRON_PICKAXE), new ItemStack(Material.PORK),
			new ItemStack(Material.DIAMOND_HOE), new ItemStack(Material.IRON_AXE),
			new ItemStack(Material.COOKED_RABBIT), new ItemStack(Material.RAW_FISH, 1, (short) 3),
			new ItemStack(Material.COOKED_FISH), new ItemStack(Material.GOLD_SPADE), new ItemStack(Material.GOLD_AXE),
			new ItemStack(Material.POTATO_ITEM) };
	public static final ItemStack[] EPICS = { new ItemStack(Material.DIAMOND_AXE), new ItemStack(Material.GOLD_PICKAXE),
			new ItemStack(Material.IRON_HOE), new ItemStack(Material.DIAMOND_SPADE), new ItemStack(Material.BREAD),
			new ItemStack(Material.MELON), new ItemStack(Material.WOOD_HOE), new ItemStack(Material.COOKED_CHICKEN),
			new ItemStack(Material.RAW_FISH, 1, (short) 2), new ItemStack(Material.STONE_HOE),
			new ItemStack(Material.STRING), new ItemStack(Material.RAW_BEEF), new ItemStack(Material.WOOD_SPADE),
			new ItemStack(Material.RAW_CHICKEN), new ItemStack(Material.MUSHROOM_SOUP) };
	public static final ItemStack[] LEGENDS = { new ItemStack(Material.COOKED_FISH, 1, (short) 1),
			new ItemStack(Material.GOLDEN_CARROT), new ItemStack(Material.COOKED_MUTTON),
			new ItemStack(Material.COOKED_BEEF), new ItemStack(Material.GRILLED_PORK),
			new ItemStack(Material.STONE_SPADE), new ItemStack(Material.WOOD_PICKAXE),
			new ItemStack(Material.DIAMOND_PICKAXE), new ItemStack(Material.RAW_FISH), new ItemStack(Material.APPLE),
			new ItemStack(Material.POISONOUS_POTATO), new ItemStack(Material.BAKED_POTATO) };

	public static Weapon generateRandomWeapon(int i) {
		Weapon w = null;
		Random r = new Random();
		int klass = r.nextInt(KLASSEN.length);
		if (i == 1) {
			w = generateRandomWeapon(i, klass);
		} else if (i == 2) {
			w = generateRandomWeapon(i, klass);
		} else if (i == 3) {
			w = generateRandomWeapon(i, klass);
		} else {
			w = generateRandomWeapon(i, klass);
		}
		return w;
	}

	public static Weapon generateRandomWeapon(int i, int klass) {
		Weapon w = null;
		Random r = new Random();
		double dmin = 0;
		double dmax = 0;
		double cc = 0;
		double cm = 0;
		double boost = 0;
		double hp = 0;
		int energy = 0;
		double cooldown = 0;
		double speed = 0;
		int ua = 0;
		int um = 0;
		int skill = r.nextInt(SKILLS[klass].length);
		String klasse = KLASSEN[klass];
		if (i == 1) {
			int rand = r.nextInt(LEGENDS.length);
			dmin = r.nextInt(91) / 10.0 + 93.0;
			dmax = r.nextInt(121) / 10.0 + 126.0;
			hp = r.nextInt(151) + 250;
			boost = (r.nextInt(6) + 10) / 100.0;
			cm = (r.nextInt(21) + 180) / 100.0;
			cc = (r.nextInt(11) + 15) / 100.0;
			cooldown = (r.nextInt(6) + 5) / 100.0;
			speed = (r.nextInt(6) + 5) / 100.0;
			um = r.nextInt(3);
			energy = (r.nextInt(6) + 20);
			w = new Weapon(klasse, klass, 1, rand, dmin, dmax, cc, cm, skill, boost, hp, energy, cooldown, speed, ua,
					um);
		} else if (i == 2) {
			int rand = r.nextInt(EPICS.length);
			dmin = r.nextInt(21) + 70;
			dmax = r.nextInt(21) + 100;
			hp = r.nextInt(101) + 200;
			boost = (r.nextInt(3) + 7) / 100.0;
			cm = (r.nextInt(26) + 150) / 100.0;
			cc = (r.nextInt(6) + 15) / 100.0;
			cooldown = (r.nextInt(4) + 1) / 100.0;
			um = r.nextInt(2);
			energy = (r.nextInt(10) + 11);
			w = new Weapon(klasse, klass, 2, rand, dmin, dmax, cc, cm, skill, boost, hp, energy, cooldown, speed, ua,
					um);
		} else if (i == 3) {
			int rand = r.nextInt(RARES.length);
			dmin = r.nextInt(21) + 60;
			dmax = r.nextInt(21) + 90;
			hp = r.nextInt(101) + 100;
			boost = (r.nextInt(3) + 4) / 100.0;
			cm = (r.nextInt(26) + 125) / 100.0;
			cc = (r.nextInt(6) + 10) / 100.0;
			energy = (r.nextInt(10) + 1);
			w = new Weapon(klasse, klass, 3, rand, dmin, dmax, cc, cm, skill, boost, hp, energy, cooldown, speed, ua,
					um);
		} else {
			int rand = r.nextInt(COMMONS.length);
			dmin = r.nextInt(21) + 50;
			dmax = r.nextInt(21) + 80;
			hp = r.nextInt(21) + 60;
			boost = (r.nextInt(3) + 1) / 100.0;
			cm = (r.nextInt(26) + 100) / 100.0;
			cc = (r.nextInt(10) + 1) / 100.0;
			w = new Weapon(klasse, klass, 4, rand, dmin, dmax, cc, cm, skill, boost, hp, energy, cooldown, speed, ua,
					um);
		}
		return w;
	}

	public static Weapon generateRandomWeapon() {
		Weapon w = null;
		Random r = new Random();
		int t = r.nextInt(10000);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equals("KatanaTM")) {
				p.sendMessage(t + " L:<=37 E:<=287 R<=2787");
			}
		}
		if (t <= 37) {
			w = generateRandomWeapon(1);
		} else if (t <= 287) {
			w = generateRandomWeapon(2);
		} else if (t <= 2787) {
			w = generateRandomWeapon(3);
		} else {
			w = generateRandomWeapon(4);
		}
		return w;
	}

	public static Inventory getWeaponInventory(Player p) {
		Inventory wi = Bukkit.createInventory(p, 54, "Weapons");
		ArrayList<Weapon> list = WeaponUtilMethods.load(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/weapons/" + p.getUniqueId());
		SpielKlasse sk = Warlords.getKlasse(p);
		String klasse = "";
		if (sk != null) {
			klasse = sk.getName();
		}
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (i >= 45) {
					ItemStack isb = new ItemStack(Material.ARROW, 1);
					ItemMeta imb = isb.getItemMeta();
					imb.setDisplayName(ChatColor.GREEN + "Go Back");
					isb.setItemMeta(imb);
					wi.setItem(49, isb);
					return wi;
				}
				if (list.get(i) == null) {
					Weapon w = WeaponUtil.generateRandomWeapon(1);
					list.set(i, w);
					WeaponUtilMethods.save(list, Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/weapons/" + p.getUniqueId());
				}
				wi.setItem(i, generateItemStack(list.get(i), klasse));
			}
			ItemStack isb = new ItemStack(Material.ARROW, 1);
			ItemMeta imb = isb.getItemMeta();
			imb.setDisplayName(ChatColor.GREEN + "Go Back");
			isb.setItemMeta(imb);
			wi.setItem(49, isb);
		}
		return wi;
	}

	public static ItemStack generateItemStack(Weapon weapon, String klasse) {
		ItemStack is = new ItemStack(Material.BARRIER);
		String prefix = ChatColor.DARK_RED + "Error";
		if (weapon.getSkin() == 1) {
			is = LEGENDS[weapon.getType()];
		} else if (weapon.getSkin() == 2) {
			is = EPICS[weapon.getType()];
		} else if (weapon.getSkin() == 3) {
			is = RARES[weapon.getType()];
		} else {
			is = COMMONS[weapon.getType()];
		}
		if (weapon.getKat() == 1) {
			prefix = ChatColor.GOLD + "";
		} else if (weapon.getKat() == 2) {
			prefix = ChatColor.DARK_PURPLE + "";
		} else if (weapon.getKat() == 3) {
			prefix = ChatColor.BLUE + "";
		} else {
			prefix = ChatColor.GREEN + "";
		}
		net.minecraft.server.v1_10_R1.ItemStack stack = CraftItemStack.asNMSCopy(is);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setBoolean("Unbreakable", true);
		stack.setTag(tag);
		is = CraftItemStack.asCraftMirror(stack);
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		if (weapon.getSkin() == 1) {
			prefix += LEGENDNAMES[weapon.getType()] + " of the " + KLASSEN[weapon.getKlass()];
		} else if (weapon.getSkin() == 2) {
			prefix += EPICNAMES[weapon.getType()] + " of the " + KLASSEN[weapon.getKlass()];
		} else if (weapon.getSkin() == 3) {
			prefix += RARENAMES[weapon.getType()] + " of the " + KLASSEN[weapon.getKlass()];
		} else {
			prefix += COMMONNAMES[weapon.getType()] + " of the " + KLASSEN[weapon.getKlass()];
		}
		im.setDisplayName(prefix);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + ((double) (int) (weapon.getDmin() * 10.0)) / 10.0
				+ ChatColor.GRAY + " - " + ChatColor.RED + ((double) (int) (weapon.getDmax() * 10.0)) / 10.0);
		lore.add(ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + Math.round(weapon.getCc() * 100) + "%");
		lore.add(ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + Math.round(weapon.getCm() * 100) + "%");
		lore.add("");
		if (klasse.equals(weapon.getTitle())) {
			lore.add(ChatColor.GREEN + weapon.getTitle() + ":");
			lore.add(ChatColor.GREEN + "Increase the damage you");
			lore.add(ChatColor.GREEN + "deal with " + SKILLS[weapon.getKlass()][weapon.getSkill()]);
			lore.add(ChatColor.GREEN + "by " + ChatColor.RED + ((double) (int) (weapon.getBoost() * 10000.0)) / 100.0
					+ "%");
		} else {
			lore.add(ChatColor.GRAY + weapon.getTitle() + ":");
			lore.add(ChatColor.GRAY + "Increase the damage you");
			lore.add(ChatColor.GRAY + "deal with " + SKILLS[weapon.getKlass()][weapon.getSkill()]);
			lore.add(ChatColor.GRAY + "by " + ((double) (int) (weapon.getBoost() * 10000.0)) / 100.0 + "%");
		}
		lore.add("");
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+" + Math.round(weapon.getHp()));
		if (weapon.getEnergy() > 0) {
			lore.add(ChatColor.GRAY + "Max Energy: " + ChatColor.GREEN + "+" + weapon.getEnergy());
		}
		if (weapon.getCooldown() > 0) {
			lore.add(ChatColor.GRAY + "Cooldown Reduction: " + ChatColor.GREEN + "+"
					+ Math.round(weapon.getCooldown() * 100.0) + "%");
		}
		if (weapon.getSpeed() > 0) {
			lore.add(ChatColor.GRAY + "Speed: " + ChatColor.GREEN + "+" + Math.round(weapon.getSpeed() * 100.0) + "%");
		}
		if (weapon.getCrafted() || weapon.getUm() > 0 || weapon.getSkillboost()) {
			lore.add("");
		}
		if (weapon.getSkillboost()) {
			lore.add(ChatColor.GOLD + "Skill Boost Unlocked");
		}
		if (weapon.getCrafted()) {
			lore.add(ChatColor.AQUA + "Crafted");
		}
		if (weapon.getKat() == 1) {
			if (weapon.getUm() > 0) {
				if (weapon.getUa() > 0) {
					lore.add(ChatColor.LIGHT_PURPLE + "Void Forged [" + weapon.getUa() + "/" + weapon.getUm() + "]");
				} else {
					lore.add(ChatColor.GRAY + "Void Forged [" + weapon.getUa() + "/" + weapon.getUm() + "]");
				}
			}
		} else if (weapon.getKat() == 2) {
			if (weapon.getUm() > 0) {
				if (weapon.getUa() > 0) {
					lore.add(ChatColor.AQUA + "Magic Forged [" + weapon.getUa() + "/" + weapon.getUm() + "]");
				} else {
					lore.add(ChatColor.GRAY + "Magic Forged [" + weapon.getUa() + "/" + weapon.getUm() + "]");
				}
			}
		}
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static void doWeapon(LivingEntity le, Player shooter) {
		if (le.getType() == EntityType.ENDER_DRAGON || le.getType() == EntityType.WITHER) {
			if (le.isDead() != true) {
				Weapon w;
				int x3 = (int) (Math.random() * 100.0);
				if (x3 < 12.5) {
					w = WeaponUtil.generateRandomWeapon(1);
					ItemStack is = WeaponUtil.generateItemStack(w, WeaponUtil.KLASSEN[w.getKlass()]);
					String maintext = shooter.getDisplayName() + " was lucky and found a ";
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
									+ "\",\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
									+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
					PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
					for (Player p : Bukkit.getOnlinePlayers()) {
						UtilMethods.sendSoundPacket(p, "legendaryfind", p.getLocation());
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
					}
				} else if (x3 < 37.5) {
					w = WeaponUtil.generateRandomWeapon(2);
					ItemStack is = WeaponUtil.generateItemStack(w, WeaponUtil.KLASSEN[w.getKlass()]);
					String maintext = shooter.getDisplayName() + " was lucky and found a ";
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
									+ "\",\"color\":\"dark_purple\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
									+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
					PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
					for (Player p : Bukkit.getOnlinePlayers()) {
						UtilMethods.sendSoundPacket(p, "epicfind", p.getLocation());
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
					}
				} else {
					w = WeaponUtil.generateRandomWeapon(3);
				}
				ArrayList<Weapon> list2 = WeaponUtilMethods.load(Warlords.getPlugin(Warlords.class).getDataFolder(),
						"/weapons/" + shooter.getUniqueId());
				if (list2 != null) {
					shooter.sendMessage("You earned a weapon");
					if (list2.size() < 45) {
						list2.add(w);
					} else {
						shooter.sendMessage("Weapon inventory already full");
					}
				} else {
					list2 = new ArrayList<Weapon>();
					shooter.sendMessage("You earned a weapon");
					list2.add(w);
				}
				WeaponUtilMethods.save(list2, Warlords.getPlugin(Warlords.class).getDataFolder(),
						"/weapons/" + shooter.getUniqueId());
			}
		} else {
			if (le.isDead() != true) {
				double rand = Math.random() * 100.0;
				shooter.sendMessage(rand + "");
				if (rand < 0.2 * 100.0) {
					Weapon w = WeaponUtil.generateRandomWeapon();
					if (w.getKat() == 1) {
						ItemStack is = WeaponUtil.generateItemStack(w, WeaponUtil.KLASSEN[w.getKlass()]);
						String maintext = shooter.getDisplayName() + " was lucky and found a ";
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
										+ "\",\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
										+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
						PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
						for (Player p : Bukkit.getOnlinePlayers()) {
							UtilMethods.sendSoundPacket(p, "legendaryfind", p.getLocation());
							((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
						}
					} else if (w.getKat() == 2) {
						ItemStack is = WeaponUtil.generateItemStack(w, WeaponUtil.KLASSEN[w.getKlass()]);
						String maintext = shooter.getDisplayName() + " was lucky and found a ";
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
										+ "\",\"color\":\"dark_purple\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
										+ cweaponname + "\\\",Lore:[" + lore + "]}}}\"}}]}");
						PacketPlayOutChat packet = new PacketPlayOutChat(al, (byte) 0);
						for (Player p : Bukkit.getOnlinePlayers()) {
							UtilMethods.sendSoundPacket(p, "epicfind", p.getLocation());
							((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
						}
					}
					ArrayList<Weapon> list2 = WeaponUtilMethods.load(Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/weapons/" + shooter.getUniqueId());
					if (list2 != null) {
						shooter.sendMessage("You earned a weapon");
						if (list2.size() < 45) {
							list2.add(w);
						} else {
							shooter.sendMessage("Weapon inventory already full");
						}
					} else {
						list2 = new ArrayList<Weapon>();
						shooter.sendMessage("You earned a weapon");
						list2.add(w);
					}
					WeaponUtilMethods.save(list2, Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/weapons/" + shooter.getUniqueId());
				}
			}
		}
	}

	public static Confirmation getWeaponConfirmationInventory(Player p, int rawSlot) {
		Inventory ci = Bukkit.createInventory(p, 27, "Are you sure?");
		ItemStack isy = new ItemStack(Material.STAINED_CLAY, 1, (short) 13);
		ItemMeta imy = isy.getItemMeta();
		imy.setDisplayName(ChatColor.GREEN + "Confirm");
		List<String> lorey = new ArrayList<String>();
		lorey.add(ChatColor.GRAY + "Destroy the weapon and");
		lorey.add(ChatColor.GRAY + "claim the crafting");
		lorey.add(ChatColor.GRAY + "materials. This is");
		lorey.add(ChatColor.GRAY + "irreversible.");
		imy.setLore(lorey);
		isy.setItemMeta(imy);
		ItemStack isn = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
		ItemMeta imn = isn.getItemMeta();
		imn.setDisplayName(ChatColor.RED + "Deny");
		List<String> loren = new ArrayList<String>();
		loren.add(ChatColor.GRAY + "Go back to the previous");
		loren.add(ChatColor.GRAY + "menu.");
		imn.setLore(loren);
		isn.setItemMeta(imn);
		ci.setItem(11, isy);
		ci.setItem(15, isn);
		Confirmation c = new Confirmation(rawSlot, ci);
		return c;
	}

	public static Inventory getSmithInventory(Player p) {
		Inventory smithi = Bukkit.createInventory(p, 45, "The Weaponsmith");
		ItemStack forge = new ItemStack(Material.WOOD_PICKAXE, 1);
		ItemMeta imf = forge.getItemMeta();
		imf.setDisplayName(ChatColor.GREEN + "Weapon Crafting & Forging");
		List<String> loref = new ArrayList<String>();
		loref.add(ChatColor.GRAY + "Use the " + ChatColor.LIGHT_PURPLE + "Void Shards");
		loref.add(ChatColor.GRAY + "you gathered to craft weapons");
		loref.add(ChatColor.GRAY + "for your currently selected class");
		loref.add(ChatColor.GRAY + "specialization or to upgrade your equipped weapon.");
		imf.setLore(loref);
		imf.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		forge.setItemMeta(imf);
		smithi.setItem(11, forge);
		ItemStack reroll = new ItemStack(Material.WORKBENCH, 1);
		ItemMeta imr = reroll.getItemMeta();
		imr.setDisplayName(ChatColor.GREEN + "Weapon Stats Reroll");
		List<String> lorer = new ArrayList<String>();
		lorer.add(ChatColor.GRAY + "Click here to reroll the");
		lorer.add(ChatColor.GRAY + "numerical stats of your");
		lorer.add(ChatColor.GRAY + "currently " + ChatColor.GREEN + "EQUIPPED");
		lorer.add(ChatColor.GRAY + "weapon for a chance at");
		lorer.add(ChatColor.GRAY + "making it better than it");
		lorer.add(ChatColor.GRAY + "previously was.");
		lorer.add(ChatColor.GRAY + "");
		lorer.add(ChatColor.RED + "WARNING: " + ChatColor.GRAY + "Weapon stat");
		lorer.add(ChatColor.GRAY + "rerolls are totally random.");
		lorer.add(ChatColor.GRAY + "This means you can also end");
		lorer.add(ChatColor.GRAY + "up with worse stats!");
		imr.setLore(lorer);
		imr.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		reroll.setItemMeta(imr);
		smithi.setItem(29, reroll);
		String count = ChatColor.DARK_RED + "Error";
		String countv = ChatColor.DARK_RED + "Error";
		WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/players/" + p.getUniqueId());
		if (wp != null) {
			if (wp.getWeaponlist() != null) {
				count = "" + wp.getWeaponlist().size();
			}
			countv = "" + wp.getVoidShards();
		}
		ItemStack inv = new ItemStack(Material.GOLD_HOE, 1);
		ItemMeta imi = inv.getItemMeta();
		imi.setDisplayName(ChatColor.GREEN + "Weapons Inventory");
		List<String> lorei = new ArrayList<String>();
		lorei.add(ChatColor.GRAY + "Click here to equip your");
		lorei.add(ChatColor.GRAY + "repaired weapons.");
		lorei.add(ChatColor.GRAY + "");
		lorei.add(ChatColor.AQUA + "Weapons: " + ChatColor.GOLD + count);
		imi.setLore(lorei);
		imi.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		inv.setItemMeta(imi);
		smithi.setItem(15, inv);
		ItemStack skin = new ItemStack(Material.PAINTING, 1);
		ItemMeta ims = skin.getItemMeta();
		ims.setDisplayName(ChatColor.GREEN + "Weapon Skin Selector");
		List<String> lores = new ArrayList<String>();
		lores.add(ChatColor.GRAY + "Change the cosmetic");
		lores.add(ChatColor.GRAY + "appearance of your weapon");
		lores.add(ChatColor.GRAY + "to better suit your tastes.");
		lores.add(ChatColor.GRAY + "");
		lores.add(ChatColor.GRAY + "This costs " + ChatColor.LIGHT_PURPLE + "Void Shards");
		ims.setLore(lores);
		ims.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		skin.setItemMeta(ims);
		smithi.setItem(33, skin);
		ItemStack voids = new ItemStack(Material.EMERALD, 1);
		ItemMeta imv = voids.getItemMeta();
		imv.setDisplayName(ChatColor.GREEN + "Resources");
		List<String> lorev = new ArrayList<String>();
		lorev.add(ChatColor.LIGHT_PURPLE + "Void Shards: " + ChatColor.WHITE + countv);
		imv.setLore(lorev);
		imv.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		voids.setItemMeta(imv);
		smithi.setItem(22, voids);
		return smithi;
	}

	public static Inventory getWeaponRerollConfirmationInventory(Player p, int kat) {
		String price = ChatColor.DARK_RED + "Error";
		if (kat == 1) {
			price = ChatColor.LIGHT_PURPLE + "10 Void Shards";
		} else if (kat == 2) {
			price = ChatColor.LIGHT_PURPLE + "5 Void Shards";
		}
		Inventory ci = Bukkit.createInventory(p, 27, "Are you sure?");
		ItemStack isy = new ItemStack(Material.STAINED_CLAY, 1, (short) 13);
		ItemMeta imy = isy.getItemMeta();
		imy.setDisplayName(ChatColor.GREEN + "Confirm");
		List<String> lorey = new ArrayList<String>();
		lorey.add(ChatColor.GRAY + "I understand the risks!");
		lorey.add(ChatColor.GRAY + "Just do it!");
		lorey.add(ChatColor.GRAY + "");
		lorey.add(ChatColor.GRAY + "Requires: " + price);
		imy.setLore(lorey);
		isy.setItemMeta(imy);
		ItemStack isn = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
		ItemMeta imn = isn.getItemMeta();
		imn.setDisplayName(ChatColor.RED + "Deny");
		List<String> loren = new ArrayList<String>();
		loren.add(ChatColor.GRAY + "Go back to the previous");
		loren.add(ChatColor.GRAY + "menu.");
		imn.setLore(loren);
		isn.setItemMeta(imn);
		ci.setItem(11, isy);
		ci.setItem(15, isn);
		return ci;
	}

	public static ItemStack generateChangeItemStack(Weapon alt, Weapon neu) {
		ItemStack is = new ItemStack(Material.BARRIER);
		String prefix = ChatColor.DARK_RED + "Error";
		if (alt.getSkin() == 1) {
			is = LEGENDS[alt.getType()];
		} else if (alt.getSkin() == 2) {
			is = EPICS[alt.getType()];
		} else if (alt.getSkin() == 3) {
			is = RARES[alt.getType()];
		} else {
			is = COMMONS[alt.getType()];
		}
		if (alt.getKat() == 1) {
			prefix = ChatColor.GOLD + "";
		} else if (alt.getKat() == 2) {
			prefix = ChatColor.DARK_PURPLE + "";
		} else if (alt.getKat() == 3) {
			prefix = ChatColor.BLUE + "";
		} else {
			prefix = ChatColor.GREEN + "";
		}
		net.minecraft.server.v1_10_R1.ItemStack stack = CraftItemStack.asNMSCopy(is);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setBoolean("Unbreakable", true);
		stack.setTag(tag);
		is = CraftItemStack.asCraftMirror(stack);
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		if (alt.getSkin() == 1) {
			prefix += LEGENDNAMES[alt.getType()] + " of the " + KLASSEN[alt.getKlass()];
		} else if (alt.getSkin() == 2) {
			prefix += EPICNAMES[alt.getType()] + " of the " + KLASSEN[alt.getKlass()];
		} else if (alt.getSkin() == 3) {
			prefix += RARENAMES[alt.getType()] + " of the " + KLASSEN[alt.getKlass()];
		} else {
			prefix += COMMONNAMES[alt.getType()] + " of the " + KLASSEN[alt.getKlass()];
		}
		im.setDisplayName(prefix);
		List<String> lore = new ArrayList<String>();
		ChatColor color = ChatColor.GREEN;
		if (alt.getDmax() + alt.getDmin() > neu.getDmax() + neu.getDmin()) {
			color = ChatColor.RED;
		} else if (alt.getDmax() + alt.getDmin() == neu.getDmax() + neu.getDmin()) {
			color = ChatColor.GRAY;
		}
		lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + ChatColor.RED
				+ ((double) (int) (alt.getDmin() * 10.0)) / 10.0 + ChatColor.GRAY + " - " + ChatColor.RED
				+ ChatColor.RED + ((double) (int) (alt.getDmax() * 10.0)) / 10.0 + color + " ➠ " + ChatColor.RED
				+ ChatColor.RED + ((double) (int) (neu.getDmin() * 10.0)) / 10.0 + ChatColor.GRAY + " - "
				+ ChatColor.RED + ChatColor.RED + ((double) (int) (neu.getDmax() * 10.0)) / 10.0);
		if (Math.round(alt.getCc() * 100) > Math.round(neu.getCc() * 100)) {
			color = ChatColor.RED;
		} else if (Math.round(alt.getCc() * 100) == Math.round(neu.getCc() * 100)) {
			color = ChatColor.GRAY;
		} else {
			color = ChatColor.GREEN;
		}
		lore.add(ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + Math.round(alt.getCc() * 100) + "%" + color + " ➠ "
				+ ChatColor.RED + Math.round(neu.getCc() * 100) + "%");
		if (Math.round(alt.getCm() * 100) > Math.round(neu.getCm() * 100)) {
			color = ChatColor.RED;
		} else if (Math.round(alt.getCm() * 100) == Math.round(neu.getCm() * 100)) {
			color = ChatColor.GRAY;
		} else {
			color = ChatColor.GREEN;
		}
		lore.add(ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + Math.round(alt.getCm() * 100) + "%" + color
				+ " ➠ " + ChatColor.RED + Math.round(neu.getCm() * 100) + "%");
		lore.add("");
		lore.add(ChatColor.GREEN + alt.getTitle() + ":");
		lore.add(ChatColor.GREEN + "Increase the damage you");
		lore.add(ChatColor.GREEN + "deal with " + SKILLS[alt.getKlass()][alt.getSkill()]);
		if (((double) (int) (alt.getBoost() * 10000.0)) / 100.0 > ((double) (int) (neu.getBoost() * 10000.0)) / 100.0) {
			color = ChatColor.RED;
		} else if (((double) (int) (alt.getBoost() * 10000.0)) / 100.0 == ((double) (int) (neu.getBoost() * 10000.0))
				/ 100.0) {
			color = ChatColor.GRAY;
		} else {
			color = ChatColor.GREEN;
		}
		lore.add(ChatColor.GREEN + "by " + ChatColor.RED + ((double) (int) (alt.getBoost() * 10000.0)) / 100.0 + "%"
				+ color + " ➠ " + ChatColor.RED + ((double) (int) (neu.getBoost() * 10000.0)) / 100.0 + "%");
		lore.add("");
		if (Math.round(alt.getHp()) > Math.round(neu.getHp())) {
			color = ChatColor.RED;
		} else if (Math.round(alt.getHp()) == Math.round(neu.getHp())) {
			color = ChatColor.GRAY;
		} else {
			color = ChatColor.GREEN;
		}
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+" + Math.round(alt.getHp()) + color + " ➠ "
				+ ChatColor.GREEN + "+" + Math.round(neu.getHp()));
		if (alt.getEnergy() > 0) {
			if (alt.getEnergy() > neu.getEnergy()) {
				color = ChatColor.RED;
			} else if (alt.getEnergy() == neu.getEnergy()) {
				color = ChatColor.GRAY;
			} else {
				color = ChatColor.GREEN;
			}
			lore.add(ChatColor.GRAY + "Max Energy: " + ChatColor.GREEN + "+" + alt.getEnergy() + color + " ➠ "
					+ ChatColor.GREEN + "+" + neu.getEnergy());
		}
		if (alt.getCooldown() > 0) {
			if (Math.round(alt.getCooldown() * 100.0) > Math.round(neu.getCooldown() * 100.0)) {
				color = ChatColor.RED;
			} else if (Math.round(alt.getCooldown() * 100.0) == Math.round(neu.getCooldown() * 100.0)) {
				color = ChatColor.GRAY;
			} else {
				color = ChatColor.GREEN;
			}
			lore.add(ChatColor.GRAY + "Cooldown Reduction: " + ChatColor.GREEN + "+"
					+ Math.round(alt.getCooldown() * 100.0) + "%" + color + " ➠ " + ChatColor.GREEN + "+"
					+ Math.round(neu.getCooldown() * 100.0) + "%");
		}
		if (alt.getSpeed() > 0) {
			if (Math.round(alt.getSpeed() * 100.0) > Math.round(neu.getSpeed() * 100.0)) {
				color = ChatColor.RED;
			} else if (Math.round(alt.getSpeed() * 100.0) == Math.round(neu.getSpeed() * 100.0)) {
				color = ChatColor.GRAY;
			} else {
				color = ChatColor.GREEN;
			}
			lore.add(ChatColor.GRAY + "Speed: " + ChatColor.GREEN + "+" + Math.round(alt.getSpeed() * 100.0) + "%"
					+ color + " ➠ " + ChatColor.GREEN + "+" + Math.round(neu.getSpeed() * 100.0) + "%");
		}
		if (alt.getCrafted() || alt.getUm() > 0 || alt.getSkillboost()) {
			lore.add("");
		}
		if (alt.getSkillboost()) {
			lore.add(ChatColor.GOLD + "Skill Boost Unlocked");
		}
		if (alt.getCrafted()) {
			lore.add(ChatColor.AQUA + "Crafted");
		}
		if (alt.getKat() == 1) {
			if (alt.getUm() > 0) {
				if (alt.getUa() > 0) {
					lore.add(ChatColor.LIGHT_PURPLE + "Void Forged [" + alt.getUa() + "/" + alt.getUm() + "]");
				} else {
					lore.add(ChatColor.GRAY + "Void Forged [" + alt.getUa() + "/" + alt.getUm() + "]");
				}
			}
		} else if (alt.getKat() == 2) {
			if (alt.getUm() > 0) {
				if (alt.getUa() > 0) {
					lore.add(ChatColor.AQUA + "Magic Forged [" + alt.getUa() + "/" + alt.getUm() + "]");
				} else {
					lore.add(ChatColor.GRAY + "Magic Forged [" + alt.getUa() + "/" + alt.getUm() + "]");
				}
			}
		}
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static Inventory getCraftingInventory(Player p) {
		Inventory crafting = Bukkit.createInventory(p, 54, "Crafting & Forging");
		ItemStack forge = new ItemStack(Material.ENCHANTMENT_TABLE, 1);
		ItemMeta imf = forge.getItemMeta();
		imf.setDisplayName(ChatColor.GREEN + "Void Forging");
		List<String> loref = new ArrayList<String>();
		loref.add(ChatColor.GRAY + "Upgrade your currently equipped");
		loref.add(ChatColor.GOLD + "legendary " + ChatColor.GRAY + "weapon using " + ChatColor.LIGHT_PURPLE
				+ "Void Shards" + ChatColor.GRAY + ",");
		loref.add(ChatColor.GRAY + "increasing some of its stats");
		loref.add(ChatColor.GRAY + "permanently.");
		loref.add(ChatColor.GRAY + "");
		String cost = ChatColor.DARK_RED + "Error";
		WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/players/" + p.getUniqueId());
		if (wp != null) {
			ArrayList<Weapon> list = wp.getWeaponlist();
			if (list != null) {
				Weapon w = list.get(wp.getWeaponSlot());
				if (w.getKat() == 1) {
					cost = "" + (int) (100 + (w.getUa() * 0.5 + 0.5) * w.getUa() * 50);
				} else {
					cost = "100";
				}
			}
		}
		loref.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + cost + " Void Shards");
		imf.setLore(loref);
		imf.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		forge.setItemMeta(imf);
		crafting.setItem(33, forge);
		ItemStack mforge = new ItemStack(Material.BREWING_STAND_ITEM, 1);
		ItemMeta immf = mforge.getItemMeta();
		immf.setDisplayName(ChatColor.GREEN + "Magic Forging");
		List<String> loremf = new ArrayList<String>();
		loremf.add(ChatColor.GRAY + "Upgrade your currently equipped");
		loremf.add(ChatColor.DARK_PURPLE + "epic " + ChatColor.GRAY + "weapon using " + ChatColor.LIGHT_PURPLE
				+ "Void Shards" + ChatColor.GRAY + ",");
		loremf.add(ChatColor.GRAY + "increasing some of its stats");
		loremf.add(ChatColor.GRAY + "permanently.");
		loremf.add(ChatColor.GRAY + "");
		String costm = ChatColor.DARK_RED + "Error";
		if (wp != null) {
			ArrayList<Weapon> list = wp.getWeaponlist();
			if (list != null) {
				Weapon w = list.get(wp.getWeaponSlot());
				if (w.getKat() == 2) {
					costm = "" + (int) (25 + (w.getUa() * 0.5 + 0.5) * w.getUa() * 12.5);
				} else {
					costm = "25";
				}
			}
		}
		loremf.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + costm + " Void Shards");
		immf.setLore(loremf);
		immf.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		mforge.setItemMeta(immf);
		crafting.setItem(31, mforge);
		ItemStack skill = new ItemStack(Material.BOOKSHELF, 1);
		ItemMeta ims = skill.getItemMeta();
		ims.setDisplayName(ChatColor.GREEN + "Weapon Skill Boost");
		List<String> lores = new ArrayList<String>();
		lores.add(ChatColor.GRAY + "Change the skill boost of your currently");
		lores.add(ChatColor.GRAY + "equipped " + ChatColor.GOLD + "Legendary" + ChatColor.GRAY
				+ " weapon! Once purchased,");
		lores.add(ChatColor.GRAY + "this feature will allow you to swap freely");
		lores.add(ChatColor.GRAY + "between the different skill boosts available");
		lores.add(ChatColor.GRAY + "for this weapon, with no additional cost.");
		lores.add(ChatColor.GRAY + "");
		lores.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "100 Void Shards");
		ims.setLore(lores);
		ims.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		skill.setItemMeta(ims);
		crafting.setItem(29, skill);
		Random r = new Random();
		int rand = r.nextInt(LEGENDS.length);
		ItemStack craftl = LEGENDS[rand];
		ItemMeta imcl = craftl.getItemMeta();
		imcl.setDisplayName(ChatColor.GREEN + "Craft a Legendary Weapon");
		String bonus = ChatColor.DARK_RED + "Error";
		if (wp != null) {
			bonus = ChatColor.GREEN + "" + wp.getKlasse();
		}
		List<String> lorecl = new ArrayList<String>();
		lorecl.add(ChatColor.GRAY + "Creates a " + ChatColor.GOLD + "Legendary " + ChatColor.GRAY + "weapon with a");
		lorecl.add(ChatColor.GRAY + "skill bonus for " + bonus + ChatColor.GRAY + ".");
		lorecl.add(ChatColor.GRAY + "Crafted Legendary Weapons can be");
		lorecl.add(ChatColor.GRAY + "upgraded " + ChatColor.GREEN + "4 " + ChatColor.GRAY + "times using the Void");
		lorecl.add(ChatColor.GRAY + "Forging feature.");
		lorecl.add(ChatColor.GRAY + "");
		lorecl.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "100 Void Shards");
		imcl.setLore(lorecl);
		imcl.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		craftl.setItemMeta(imcl);
		crafting.setItem(15, craftl);
		int rand2 = r.nextInt(EPICS.length);
		ItemStack crafte = EPICS[rand2];
		ItemMeta imce = crafte.getItemMeta();
		imce.setDisplayName(ChatColor.GREEN + "Craft an Epic Weapon");
		List<String> lorece = new ArrayList<String>();
		lorece.add(ChatColor.GRAY + "Creates a " + ChatColor.DARK_PURPLE + "Epic " + ChatColor.GRAY + "weapon with a");
		lorece.add(ChatColor.GRAY + "skill bonus for " + bonus + ChatColor.GRAY + ".");
		lorece.add(ChatColor.GRAY + "Crafted Epic Weapons can be");
		lorece.add(ChatColor.GRAY + "upgraded " + ChatColor.GREEN + "2 " + ChatColor.GRAY + "times using the Magic");
		lorece.add(ChatColor.GRAY + "Forging feature.");
		lorece.add(ChatColor.GRAY + "");
		lorece.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "25 Void Shards");
		imce.setLore(lorece);
		imce.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		crafte.setItemMeta(imce);
		crafting.setItem(13, crafte);
		int rand3 = r.nextInt(RARES.length);
		ItemStack craftr = RARES[rand3];
		ItemMeta imcr = craftr.getItemMeta();
		imcr.setDisplayName(ChatColor.GREEN + "Craft a Rare Weapon");
		List<String> lorecr = new ArrayList<String>();
		lorecr.add(ChatColor.GRAY + "Creates a " + ChatColor.BLUE + "Rare " + ChatColor.GRAY + "weapon with a");
		lorecr.add(ChatColor.GRAY + "skill bonus for " + bonus + ChatColor.GRAY + ".");
		lorecr.add(ChatColor.GRAY + "");
		lorecr.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "5 Void Shards");
		imcr.setLore(lorecr);
		imcr.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		craftr.setItemMeta(imcr);
		crafting.setItem(11, craftr);
		ItemStack isb = new ItemStack(Material.ARROW, 1);
		ItemMeta imb = isb.getItemMeta();
		imb.setDisplayName(ChatColor.GREEN + "Go Back");
		isb.setItemMeta(imb);
		crafting.setItem(49, isb);
		return crafting;
	}

	public static UpgradeConfirmation getWeaponUpgradeConfirmationInventory(Player p, int type) {
		Inventory ci = Bukkit.createInventory(p, 27, "Upgrade Weapon?");
		WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/players/" + p.getUniqueId());
		ItemStack isy = new ItemStack(Material.STAINED_CLAY, 1, (short) 13);
		ItemMeta imy = isy.getItemMeta();
		imy.setDisplayName(ChatColor.GREEN + "Confirm");
		List<String> lorey = new ArrayList<String>();
		if (wp != null) {
			ArrayList<Weapon> list = wp.getWeaponlist();
			if (list != null) {
				Weapon w = list.get(wp.getWeaponSlot());
				if (type == 0) {
					if (w.getKat() == 1) {
						if (w.getSkillboost()) {
							return getWeaponUpgradeConfirmationInventory(p, 3);
						} else {
							lorey.add(ChatColor.RED + "Warning! " + ChatColor.GRAY + "This purchase");
							lorey.add(ChatColor.GRAY + "is required for each weapon");
							lorey.add(ChatColor.GRAY + "you want to customize. The specialization can not be");
							lorey.add(ChatColor.GRAY + "changed; you can only swap");
							lorey.add(ChatColor.GRAY + "between skill boosts that");
							lorey.add(ChatColor.GRAY + "are available for your");
							lorey.add(ChatColor.GRAY + "current weapon's");
							lorey.add(ChatColor.GRAY + "specialization.");
							lorey.add("");
							lorey.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "100 Void Shards");
						}
					} else {
						lorey.add(ChatColor.GRAY + "You don't have a " + ChatColor.GOLD + "LEGENDARY " + ChatColor.GRAY
								+ "weapon equipped");
					}
				} else if (type == 1) {
					if (w.getKat() == 2) {
						if (w.getUa() < w.getUm()) {
							Weapon neu = upgradeWeapon(w);
							ItemStack is = generateUpgradeItemStack(w, neu);
							for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
								String s = is.getItemMeta().getLore().get(j);
								lorey.add(s);
							}
						} else {
							lorey.add(ChatColor.RED + "You can't upgrade this weapon any more!");
						}
					} else {
						lorey.add(ChatColor.GRAY + "You don't have an " + ChatColor.DARK_PURPLE + "EPIC "
								+ ChatColor.GRAY + "weapon equipped");
					}
				} else if (type == 2) {
					if (w.getKat() == 1) {
						if (w.getUa() < w.getUm()) {
							Weapon neu = upgradeWeapon(w);
							ItemStack is = generateUpgradeItemStack(w, neu);
							for (int j = 0; j < is.getItemMeta().getLore().size(); j++) {
								String s = is.getItemMeta().getLore().get(j);
								lorey.add(s);
							}
						} else {
							lorey.add(ChatColor.RED + "You can't upgrade this weapon any more!");
						}
					} else {
						lorey.add(ChatColor.GRAY + "You don't have a " + ChatColor.GOLD + "LEGENDARY " + ChatColor.GRAY
								+ "weapon equipped");
					}
				} else if (type == 3) {
					ci = Bukkit.createInventory(p, 54, "Skill Boost");
					int anz = SKILLS[w.getKlass()].length;
					int slota = 18 + 5 - anz;
					for (int i = 0; i < anz; i++) {
						int slot = slota + 2 * i;
						ItemStack is = ItemListGenerel.getItemStack(i);
						if (i == 0) {
							is = generateItemStack(w, "");
						}
						ItemMeta im = is.getItemMeta();
						ChatColor color = ChatColor.RED;
						ChatColor color2 = ChatColor.GRAY;
						if (w.getSkill() == i) {
							color = ChatColor.GREEN;
							color2 = ChatColor.GREEN;
						}
						im.setDisplayName(color + SKILLS[w.getKlass()][i] + " (" + w.getTitle() + ")");
						List<String> lore = new ArrayList<String>();
						lore.add(color2 + "Increase the damage you");
						lore.add(color2 + "deal with " + SKILLS[w.getKlass()][i]);
						lore.add(color2 + "by " + ChatColor.RED + ((double) (int) (w.getBoost() * 10000.0)) / 100.0
								+ "%");
						lore.add("");
						if (w.getSkill() == i) {
							lore.add(color2 + "Currently selected!");
						} else {
							lore.add(ChatColor.YELLOW + "Click to select!");
						}
						im.setLore(lore);
						is.setItemMeta(im);
						ci.setItem(slot, is);
					}
					ItemStack isb = new ItemStack(Material.ARROW, 1);
					ItemMeta imb = isb.getItemMeta();
					imb.setDisplayName(ChatColor.GREEN + "Go Back");
					isb.setItemMeta(imb);
					ci.setItem(49, isb);
				}
			} else {
				lorey.add(ChatColor.DARK_RED + "Error");
			}
		}
		imy.setLore(lorey);
		isy.setItemMeta(imy);
		if (type != 3) {
			ItemStack isn = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
			ItemMeta imn = isn.getItemMeta();
			imn.setDisplayName(ChatColor.RED + "Deny");
			List<String> loren = new ArrayList<String>();
			loren.add(ChatColor.GRAY + "Go back to the previous");
			loren.add(ChatColor.GRAY + "menu.");
			imn.setLore(loren);
			isn.setItemMeta(imn);
			ci.setItem(15, isn);
			ci.setItem(11, isy);
		}
		UpgradeConfirmation c = new UpgradeConfirmation(type, ci);
		return c;
	}

	public static ItemStack generateUpgradeItemStack(Weapon alt, Weapon neu) {
		ItemStack is = new ItemStack(Material.BARRIER);
		String prefix = ChatColor.DARK_RED + "Error";
		if (alt.getSkin() == 1) {
			is = LEGENDS[alt.getType()];
		} else if (alt.getSkin() == 2) {
			is = EPICS[alt.getType()];
		} else if (alt.getSkin() == 3) {
			is = RARES[alt.getType()];
		} else {
			is = COMMONS[alt.getType()];
		}
		if (alt.getKat() == 1) {
			prefix = ChatColor.GOLD + "";
		} else if (alt.getKat() == 2) {
			prefix = ChatColor.DARK_PURPLE + "";
		} else if (alt.getKat() == 3) {
			prefix = ChatColor.BLUE + "";
		} else {
			prefix = ChatColor.GREEN + "";
		}
		net.minecraft.server.v1_10_R1.ItemStack stack = CraftItemStack.asNMSCopy(is);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setBoolean("Unbreakable", true);
		stack.setTag(tag);
		is = CraftItemStack.asCraftMirror(stack);
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
		if (alt.getSkin() == 1) {
			prefix += LEGENDNAMES[alt.getType()] + " of the " + KLASSEN[alt.getKlass()];
		} else if (alt.getSkin() == 2) {
			prefix += EPICNAMES[alt.getType()] + " of the " + KLASSEN[alt.getKlass()];
		} else if (alt.getSkin() == 3) {
			prefix += RARENAMES[alt.getType()] + " of the " + KLASSEN[alt.getKlass()];
		} else {
			prefix += COMMONNAMES[alt.getType()] + " of the " + KLASSEN[alt.getKlass()];
		}
		im.setDisplayName(prefix);
		List<String> lore = new ArrayList<String>();
		ChatColor color = ChatColor.GREEN;
		if (alt.getDmax() + alt.getDmin() > neu.getDmax() + neu.getDmin()) {
			color = ChatColor.RED;
		} else if (alt.getDmax() + alt.getDmin() == neu.getDmax() + neu.getDmin()) {
			color = ChatColor.GRAY;
		}
		lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + ChatColor.RED
				+ ((double) (int) (alt.getDmin() * 10.0)) / 10.0 + ChatColor.GRAY + " - " + ChatColor.RED
				+ ChatColor.RED + ((double) (int) (alt.getDmax() * 10.0)) / 10.0 + color + " ➠ " + ChatColor.RED
				+ ChatColor.RED + ((double) (int) (neu.getDmin() * 10.0)) / 10.0 + ChatColor.GRAY + " - "
				+ ChatColor.RED + ChatColor.RED + ((double) (int) (neu.getDmax() * 10.0)) / 10.0);
		if (Math.round(alt.getCc() * 100) > Math.round(neu.getCc() * 100)) {
			color = ChatColor.RED;
		} else if (Math.round(alt.getCc() * 100) == Math.round(neu.getCc() * 100)) {
			color = ChatColor.GRAY;
		} else {
			color = ChatColor.GREEN;
		}
		lore.add(ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + Math.round(alt.getCc() * 100) + "%" + color + " ➠ "
				+ ChatColor.RED + Math.round(neu.getCc() * 100) + "%");
		if (Math.round(alt.getCm() * 100) > Math.round(neu.getCm() * 100)) {
			color = ChatColor.RED;
		} else if (Math.round(alt.getCm() * 100) == Math.round(neu.getCm() * 100)) {
			color = ChatColor.GRAY;
		} else {
			color = ChatColor.GREEN;
		}
		lore.add(ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + Math.round(alt.getCm() * 100) + "%" + color
				+ " ➠ " + ChatColor.RED + Math.round(neu.getCm() * 100) + "%");
		lore.add("");
		lore.add(ChatColor.GREEN + alt.getTitle() + ":");
		lore.add(ChatColor.GREEN + "Increase the damage you");
		lore.add(ChatColor.GREEN + "deal with " + SKILLS[alt.getKlass()][alt.getSkill()]);
		if (((double) (int) (alt.getBoost() * 10000.0)) / 100.0 > ((double) (int) (neu.getBoost() * 10000.0)) / 100.0) {
			color = ChatColor.RED;
		} else if (((double) (int) (alt.getBoost() * 10000.0)) / 100.0 == ((double) (int) (neu.getBoost() * 10000.0))
				/ 100.0) {
			color = ChatColor.GRAY;
		} else {
			color = ChatColor.GREEN;
		}
		lore.add(ChatColor.GREEN + "by " + ChatColor.RED + ((double) (int) (alt.getBoost() * 10000.0)) / 100.0 + "%"
				+ color + " ➠ " + ChatColor.RED + ((double) (int) (neu.getBoost() * 10000.0)) / 100.0 + "%");
		lore.add("");
		if (Math.round(alt.getHp()) > Math.round(neu.getHp())) {
			color = ChatColor.RED;
		} else if (Math.round(alt.getHp()) == Math.round(neu.getHp())) {
			color = ChatColor.GRAY;
		} else {
			color = ChatColor.GREEN;
		}
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+" + Math.round(alt.getHp()) + color + " ➠ "
				+ ChatColor.GREEN + "+" + Math.round(neu.getHp()));
		if (alt.getEnergy() > 0) {
			if (alt.getEnergy() > neu.getEnergy()) {
				color = ChatColor.RED;
			} else if (alt.getEnergy() == neu.getEnergy()) {
				color = ChatColor.GRAY;
			} else {
				color = ChatColor.GREEN;
			}
			lore.add(ChatColor.GRAY + "Max Energy: " + ChatColor.GREEN + "+" + alt.getEnergy() + color + " ➠ "
					+ ChatColor.GREEN + "+" + neu.getEnergy());
		}
		if (alt.getCooldown() > 0) {
			if (Math.round(alt.getCooldown() * 100.0) > Math.round(neu.getCooldown() * 100.0)) {
				color = ChatColor.RED;
			} else if (Math.round(alt.getCooldown() * 100.0) == Math.round(neu.getCooldown() * 100.0)) {
				color = ChatColor.GRAY;
			} else {
				color = ChatColor.GREEN;
			}
			lore.add(ChatColor.GRAY + "Cooldown Reduction: " + ChatColor.GREEN + "+"
					+ Math.round(alt.getCooldown() * 100.0) + "%" + color + " ➠ " + ChatColor.GREEN + "+"
					+ Math.round(neu.getCooldown() * 100.0) + "%");
		}
		if (alt.getSpeed() > 0) {
			if (Math.round(alt.getSpeed() * 100.0) > Math.round(neu.getSpeed() * 100.0)) {
				color = ChatColor.RED;
			} else if (Math.round(alt.getSpeed() * 100.0) == Math.round(neu.getSpeed() * 100.0)) {
				color = ChatColor.GRAY;
			} else {
				color = ChatColor.GREEN;
			}
			lore.add(ChatColor.GRAY + "Speed: " + ChatColor.GREEN + "+" + Math.round(alt.getSpeed() * 100.0) + "%"
					+ color + " ➠ " + ChatColor.GREEN + "+" + Math.round(neu.getSpeed() * 100.0) + "%");
		}
		lore.add("");
		if (alt.getSkillboost()) {
			lore.add(ChatColor.GOLD + "Skill Boost Unlocked");
		}
		if (alt.getCrafted()) {
			lore.add(ChatColor.AQUA + "Crafted");
		}
		if (alt.getKat() == 1) {
			lore.add(ChatColor.LIGHT_PURPLE + "Void Forged [" + alt.getUa() + "/" + alt.getUm() + "]" + ChatColor.GREEN
					+ " ➠ " + ChatColor.LIGHT_PURPLE + "Void Forged [" + neu.getUa() + "/" + alt.getUm() + "]");
		} else if (alt.getKat() == 2) {
			lore.add(ChatColor.AQUA + "Magic Forged [" + alt.getUa() + "/" + alt.getUm() + "]" + ChatColor.GREEN + " ➠ "
					+ ChatColor.AQUA + "Magic Forged [" + neu.getUa() + "/" + alt.getUm() + "]");
		}
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static Weapon upgradeWeapon(Weapon w) {
		double amul = 1.0 / (1.0 + w.getUa() * 0.075) * (1.0 + (w.getUa() + 1) * 0.075);
		double amul2 = 1.0 / (1.0 + w.getUa() * 0.25) * (1.0 + (w.getUa() + 1) * 0.25);
		double amul3 = 1.0 / (1.0 + w.getUa() * 0.1) * (1.0 + (w.getUa() + 1) * 0.1);
		Weapon neu = new Weapon(w.getTitle(), w.getKlass(), w.getKat(), w.getType(), w.getDmin() * amul,
				w.getDmax() * amul, w.getCc(), w.getCm(), w.getSkill(), w.getBoost() * amul, w.getHp() * amul2,
				(int) (w.getEnergy() * amul3), w.getCooldown() * amul, w.getSpeed() * amul, w.getUa() + 1, w.getUm());
		return neu;
	}

	public static CraftConfirmation getWeaponCraftConfirmationInventory(Player p, int type) {
		Inventory ci = Bukkit.createInventory(p, 27, "Craft Weapon?");
		WarlordsPlayer wp = PlayerUtilMethods.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/players/" + p.getUniqueId());
		ItemStack isy = new ItemStack(Material.STAINED_CLAY, 1, (short) 13);
		ItemMeta imy = isy.getItemMeta();
		imy.setDisplayName(ChatColor.GREEN + "Confirm");
		List<String> lorey = new ArrayList<String>();
		if (wp != null) {
			String bonus = "" + ChatColor.GREEN + wp.getKlasse();
			if (type == 0) {
				lorey.add(ChatColor.GRAY + "Creates a " + ChatColor.BLUE + "Rare " + ChatColor.GRAY + "weapon with a");
				lorey.add(ChatColor.GRAY + "skill bonus for " + bonus + ChatColor.GRAY + ".");
				lorey.add(ChatColor.GRAY + "");
				lorey.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "5 Void Shards");
			} else if (type == 1) {
				lorey.add(ChatColor.GRAY + "Creates a " + ChatColor.DARK_PURPLE + "Epic " + ChatColor.GRAY
						+ "weapon with a");
				lorey.add(ChatColor.GRAY + "skill bonus for " + bonus + ChatColor.GRAY + ".");
				lorey.add(ChatColor.GRAY + "Crafted Epic Weapons can be");
				lorey.add(ChatColor.GRAY + "upgraded " + ChatColor.GREEN + "2 " + ChatColor.GRAY
						+ "times using the Magic");
				lorey.add(ChatColor.GRAY + "Forging feature.");
				lorey.add(ChatColor.GRAY + "");
				lorey.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "25 Void Shards");
			} else if (type == 2) {
				lorey.add(ChatColor.GRAY + "Creates a " + ChatColor.GOLD + "Legendary " + ChatColor.GRAY
						+ "weapon with a");
				lorey.add(ChatColor.GRAY + "skill bonus for " + bonus + ChatColor.GRAY + ".");
				lorey.add(ChatColor.GRAY + "Crafted Legendary Weapons can be");
				lorey.add(ChatColor.GRAY + "upgraded " + ChatColor.GREEN + "4 " + ChatColor.GRAY
						+ "times using the Void");
				lorey.add(ChatColor.GRAY + "");
				lorey.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "100 Void Shards");
			}
		}
		imy.setLore(lorey);
		isy.setItemMeta(imy);
		ItemStack isn = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
		ItemMeta imn = isn.getItemMeta();
		imn.setDisplayName(ChatColor.RED + "Deny");
		List<String> loren = new ArrayList<String>();
		loren.add(ChatColor.GRAY + "Go back to the previous");
		loren.add(ChatColor.GRAY + "menu.");
		imn.setLore(loren);
		isn.setItemMeta(imn);
		ci.setItem(11, isy);
		ci.setItem(15, isn);
		CraftConfirmation c = new CraftConfirmation(type, ci);
		return c;
	}

	public static int getKlass(String klasse) {
		for (int i = 0; i < KLASSEN.length; i++) {
			if (KLASSEN[i].equals(klasse)) {
				return i;
			}
		}
		return -1;
	}

	public static Weapon craftWeapon(int i, int klass) {
		Weapon w = generateRandomWeapon(i, klass);
		if (i == 1) {
			w.setUm(4);
		} else if (i == 2) {
			w.setUm(2);
		}
		w.setCrafted(true);
		return w;
	}

	public static SkinGUI getSkinInventory(Player p, int page, int kat) {
		Inventory ci = Bukkit.createInventory(p, 54, "Weapon Skin Selector");
		int slota = 10;
		if (page == 4) {
			for (int j = 0; j < COMMONS.length; j++) {
				int slot = j % 7 + slota + (int) (j / 7.0) * 9;
				if (kat <= page) {
					ItemStack is = COMMONS[j];
					net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(is);
					NBTTagCompound tag2 = new NBTTagCompound();
					tag2.setBoolean("Unbreakable", true);
					stack2.setTag(tag2);
					is = CraftItemStack.asCraftMirror(stack2);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(ChatColor.GREEN + COMMONNAMES[j]);
					im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "5 Void Shards");
					im.setLore(lore);
					is.setItemMeta(im);
					ci.setItem(slot, is);
				} else {
					ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
					net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(is);
					NBTTagCompound tag2 = new NBTTagCompound();
					tag2.setBoolean("Unbreakable", true);
					stack2.setTag(tag2);
					is = CraftItemStack.asCraftMirror(stack2);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(ChatColor.GREEN + "???");
					im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "This skin is locked to a");
					lore.add(ChatColor.GRAY + "weapon of " + ChatColor.GREEN + "COMMON");
					lore.add(ChatColor.GRAY + "category, or higher.");
					im.setLore(lore);
					is.setItemMeta(im);
					ci.setItem(slot, is);
				}
			}
			ItemStack isn = new ItemStack(Material.ARROW, 1);
			ItemMeta imn = isn.getItemMeta();
			imn.setDisplayName(ChatColor.GREEN + "Next Page");
			isn.setItemMeta(imn);
			ci.setItem(50, isn);
		} else if (page == 3) {
			for (int j = 0; j < RARES.length; j++) {
				int slot = j % 7 + slota + (int) (j / 7.0) * 9;
				if (kat <= page) {
					ItemStack is = RARES[j];
					net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(is);
					NBTTagCompound tag2 = new NBTTagCompound();
					tag2.setBoolean("Unbreakable", true);
					stack2.setTag(tag2);
					is = CraftItemStack.asCraftMirror(stack2);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(ChatColor.BLUE + RARENAMES[j]);
					im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "5 Void Shards");
					im.setLore(lore);
					is.setItemMeta(im);
					ci.setItem(slot, is);
				} else {
					ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
					net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(is);
					NBTTagCompound tag2 = new NBTTagCompound();
					tag2.setBoolean("Unbreakable", true);
					stack2.setTag(tag2);
					is = CraftItemStack.asCraftMirror(stack2);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(ChatColor.BLUE + "???");
					im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "This skin is locked to a");
					lore.add(ChatColor.GRAY + "weapon of " + ChatColor.BLUE + "RARE");
					lore.add(ChatColor.GRAY + "category, or higher.");
					im.setLore(lore);
					is.setItemMeta(im);
					ci.setItem(slot, is);
				}
			}
			ItemStack isb = new ItemStack(Material.ARROW, 1);
			ItemMeta imb = isb.getItemMeta();
			imb.setDisplayName(ChatColor.GREEN + "Previous Page");
			isb.setItemMeta(imb);
			ci.setItem(48, isb);
			ItemStack isn = new ItemStack(Material.ARROW, 1);
			ItemMeta imn = isn.getItemMeta();
			imn.setDisplayName(ChatColor.GREEN + "Next Page");
			isn.setItemMeta(imn);
			ci.setItem(50, isn);
		} else if (page == 2) {
			for (int j = 0; j < EPICS.length; j++) {
				int slot = j % 7 + slota + (int) (j / 7.0) * 9;
				if (kat <= page) {
					ItemStack is = EPICS[j];
					net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(is);
					NBTTagCompound tag2 = new NBTTagCompound();
					tag2.setBoolean("Unbreakable", true);
					stack2.setTag(tag2);
					is = CraftItemStack.asCraftMirror(stack2);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(ChatColor.DARK_PURPLE + EPICNAMES[j]);
					im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "5 Void Shards");
					im.setLore(lore);
					is.setItemMeta(im);
					ci.setItem(slot, is);
				} else {
					ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10);
					net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(is);
					NBTTagCompound tag2 = new NBTTagCompound();
					tag2.setBoolean("Unbreakable", true);
					stack2.setTag(tag2);
					is = CraftItemStack.asCraftMirror(stack2);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(ChatColor.DARK_PURPLE + "???");
					im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "This skin is locked to a");
					lore.add(ChatColor.GRAY + "weapon of " + ChatColor.DARK_PURPLE + "EPIC");
					lore.add(ChatColor.GRAY + "category, or higher.");
					im.setLore(lore);
					is.setItemMeta(im);
					ci.setItem(slot, is);
				}
			}
			ItemStack isb = new ItemStack(Material.ARROW, 1);
			ItemMeta imb = isb.getItemMeta();
			imb.setDisplayName(ChatColor.GREEN + "Previous Page");
			isb.setItemMeta(imb);
			ci.setItem(48, isb);
			ItemStack isn = new ItemStack(Material.ARROW, 1);
			ItemMeta imn = isn.getItemMeta();
			imn.setDisplayName(ChatColor.GREEN + "Next Page");
			isn.setItemMeta(imn);
			ci.setItem(50, isn);
		} else if (page == 1) {
			for (int j = 0; j < LEGENDS.length; j++) {
				int slot = j % 7 + slota + (int) (j / 7.0) * 9;
				if (kat <= page) {
					ItemStack is = LEGENDS[j];
					net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(is);
					NBTTagCompound tag2 = new NBTTagCompound();
					tag2.setBoolean("Unbreakable", true);
					stack2.setTag(tag2);
					is = CraftItemStack.asCraftMirror(stack2);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(ChatColor.GOLD + LEGENDNAMES[j]);
					im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "5 Void Shards");
					im.setLore(lore);
					is.setItemMeta(im);
					ci.setItem(slot, is);
				} else {
					ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
					net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(is);
					NBTTagCompound tag2 = new NBTTagCompound();
					tag2.setBoolean("Unbreakable", true);
					stack2.setTag(tag2);
					is = CraftItemStack.asCraftMirror(stack2);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(ChatColor.GOLD + "???");
					im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "This skin is locked to a");
					lore.add(ChatColor.GRAY + "weapon of " + ChatColor.GOLD + "LEGENDARY");
					lore.add(ChatColor.GRAY + "category, or higher.");
					im.setLore(lore);
					is.setItemMeta(im);
					ci.setItem(slot, is);
				}
			}
			ItemStack isb = new ItemStack(Material.ARROW, 1);
			ItemMeta imb = isb.getItemMeta();
			imb.setDisplayName(ChatColor.GREEN + "Previous Page");
			isb.setItemMeta(imb);
			ci.setItem(48, isb);
		}
		ItemStack isb = new ItemStack(Material.ARROW, 1);
		ItemMeta imb = isb.getItemMeta();
		imb.setDisplayName(ChatColor.GREEN + "Go Back");
		isb.setItemMeta(imb);
		ci.setItem(49, isb);
		SkinGUI sk = new SkinGUI(page, ci);
		return sk;
	}
}
