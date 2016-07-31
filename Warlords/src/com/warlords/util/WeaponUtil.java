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

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;

public class WeaponUtil {
	public static final String[] KLASSEN = { "Pyromancer", "Assassin", "Avenger", "Hunter", "Crusader" };
	public static final String[][] SKILLS = { new String[] { "Fireball", "Flameburst" },
			new String[] { "Shadow Step", "Back Stab", "Deadly Poison" },
			new String[] { "Avenger's Strike", "Consecrate" }, new String[] { "Elemental Arrow", "Explosive Arrow" },
			new String[] { "Crusader's Strike", "Consecrate" } };
	public static final String[] COMMONNAMES = { "Hammer", "Scimitar", "Orc Axe", "Steel Sword", "Bludgeon",
			"Training Sword", "Walking Stick", "Claws", "Hatchet", "Pike" };
	public static final String[] RARENAMES = { "World Tree Branch", "Mandibles", "Gem Axe", "Demonblade", "Cludgel",
			"Golden Gladius", "Doubleaxe", "Stone Mallet", "Venomstrike", "Halbert" };
	public static final String[] EPICNAMES = { "Diamondspark", "Flameweaver", "Elven Greatsword", "Gemcrusher",
			"Runic Axe", "Divine Reach", "Zweireaper", "Tenderizer", "Magmasword", "Runeblade", "Hammer of Light",
			"Katar", "Nomegusta", "Nethersteel Katana", "Lunar Relic" };
	public static final String[] LEGENDNAMES = { "Felflame Blade", "Void Edge", "Amaranth", "Armblade", "Gemini",
			"Drakefang", "Abbadon", "Void Twig", "Frostbite", "Enderfist", "Ruby Thorn", "Broccomace" };
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
		int klass = r.nextInt(KLASSEN.length);
		int skill = r.nextInt(SKILLS[klass].length);
		String klasse = KLASSEN[klass];
		if (i == 1) {
			int rand = r.nextInt(LEGENDS.length);
			dmin = r.nextInt(9) + 94;
			dmax = r.nextInt(12) + 127;
			hp = r.nextInt(101) + 300;
			boost = (r.nextInt(6) + 10) / 100.0;
			cm = (r.nextInt(26) + 175) / 100.0;
			cc = (r.nextInt(11) + 15) / 100.0;
			cooldown = (r.nextInt(6) + 5) / 100.0;
			speed = (r.nextInt(6) + 5) / 100.0;
			um = r.nextInt(4);
			energy = (r.nextInt(10) + 21);
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
			w = new Weapon(klasse, klass, 0, rand, dmin, dmax, cc, cm, skill, boost, hp, energy, cooldown, speed, ua,
					um);
		}
		return w;
	}

	public static Weapon generateRandomWeapon() {
		Weapon w = null;
		Random r = new Random();
		int t = r.nextInt(10000);
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
		int klass = r.nextInt(KLASSEN.length);
		int skill = r.nextInt(SKILLS[klass].length);
		String klasse = KLASSEN[klass];
		if (t <= 74) {
			int rand = r.nextInt(LEGENDS.length);
			dmin = r.nextInt(9) + 94;
			dmax = r.nextInt(12) + 127;
			hp = r.nextInt(101) + 300;
			boost = (r.nextInt(6) + 10) / 100.0;
			cm = (r.nextInt(26) + 175) / 100.0;
			cc = (r.nextInt(11) + 15) / 100.0;
			cooldown = (r.nextInt(6) + 5) / 100.0;
			speed = (r.nextInt(6) + 5) / 100.0;
			um = r.nextInt(4);
			energy = (r.nextInt(10) + 21);
			w = new Weapon(klasse, klass, 1, rand, dmin, dmax, cc, cm, skill, boost, hp, energy, cooldown, speed, ua,
					um);
		} else if (t <= 374) {
			int rand = r.nextInt(EPICS.length);
			dmin = r.nextInt(21) + 70;
			dmax = r.nextInt(21) + 100;
			hp = r.nextInt(101) + 200;
			boost = (r.nextInt(3) + 7) / 100.0;
			cm = (r.nextInt(26) + 150) / 100.0;
			cc = (r.nextInt(6) + 15) / 100.0;
			cooldown = (r.nextInt(4) + 1) / 100.0;
			energy = (r.nextInt(10) + 11);
			um = r.nextInt(2);
			w = new Weapon(klasse, klass, 2, rand, dmin, dmax, cc, cm, skill, boost, hp, energy, cooldown, speed, ua,
					um);
		} else if (t <= 2624) {
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
			hp = r.nextInt(41) + 60;
			boost = (r.nextInt(3) + 1) / 100.0;
			cm = (r.nextInt(26) + 100) / 100.0;
			cc = (r.nextInt(10) + 1) / 100.0;
			w = new Weapon(klasse, klass, 0, rand, dmin, dmax, cc, cm, skill, boost, hp, energy, cooldown, speed, ua,
					um);
		}
		return w;
	}

	public static Inventory getWeaponInventory(Player p) {
		Inventory wi = Bukkit.createInventory(p, 54, "Weapons");
		ArrayList<Weapon> list = FileUtilMethods.load(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/weapons/" + p.getUniqueId());
		SpielKlasse sk = Warlords.getKlasse(p);
		String klasse = "";
		if (sk != null) {
			klasse = sk.getName();
		}
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (i >= 54) {
					return wi;
				}
				if (list.get(i) == null) {
					Weapon w = WeaponUtil.generateRandomWeapon(1);
					list.set(i, w);
					FileUtilMethods.save(list, Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/weapons/" + p.getUniqueId());
				}
				wi.setItem(i, generateItemStack(list.get(i), klasse));
			}
		}
		return wi;
	}

	public static ItemStack generateItemStack(Weapon weapon, String klasse) {
		ItemStack is = new ItemStack(Material.BARRIER);
		if (weapon.getKat() == 1) {
			is = LEGENDS[weapon.getType()];
			net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(is);
			NBTTagCompound tag2 = new NBTTagCompound(); // Create the NMS
														// Stack's NBT (item
														// data)
			tag2.setBoolean("Unbreakable", true); // Set unbreakable value to
													// true
			stack2.setTag(tag2); // Apply the tag to the item
			is = CraftItemStack.asCraftMirror(stack2); // Get the bukkit version
														// of the stack
			ItemMeta im2 = is.getItemMeta();
			im2.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
			im2.setDisplayName(
					ChatColor.GOLD + LEGENDNAMES[weapon.getType()] + " of the " + KLASSEN[weapon.getKlass()]);
			List<String> lore2 = new ArrayList<String>();
			lore2.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + weapon.getDmin() + ChatColor.GRAY + " - "
					+ ChatColor.RED + weapon.getDmax());
			lore2.add(ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + Math.round(weapon.getCc() * 100) + "%");
			lore2.add(ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + Math.round(weapon.getCm() * 100) + "%");
			lore2.add("");
			if (klasse.equals(weapon.getTitle())) {
				lore2.add(ChatColor.GREEN + weapon.getTitle() + ":");
				lore2.add(ChatColor.GREEN + "Increase the damage you");
				lore2.add(ChatColor.GREEN + "deal with " + SKILLS[weapon.getKlass()][weapon.getSkill()]);
				lore2.add(ChatColor.GREEN + "by " + ChatColor.RED
						+ ((double) (int) (weapon.getBoost() * 10000.0)) / 100.0 + "%");
			} else {
				lore2.add(ChatColor.GRAY + weapon.getTitle() + ":");
				lore2.add(ChatColor.GRAY + "Increase the damage you");
				lore2.add(ChatColor.GRAY + "deal with " + SKILLS[weapon.getKlass()][weapon.getSkill()]);
				lore2.add(ChatColor.GRAY + "by " + ((double) (int) (weapon.getBoost() * 10000.0)) / 100.0 + "%");
			}
			lore2.add("");
			lore2.add(ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+" + Math.round(weapon.getHp()));
			lore2.add(ChatColor.GRAY + "Max Energy: " + ChatColor.GREEN + "+" + weapon.getEnergy());
			lore2.add(ChatColor.GRAY + "Cooldown Reduction: " + ChatColor.GREEN + "+"
					+ Math.round(weapon.getCooldown() * 100.0) + "%");
			lore2.add(ChatColor.GRAY + "Speed: " + ChatColor.GREEN + "+" + Math.round(weapon.getSpeed() * 100.0) + "%");
			im2.setLore(lore2);
			is.setItemMeta(im2);
		} else if (weapon.getKat() == 2) {
			is = EPICS[weapon.getType()];
			net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(is);
			NBTTagCompound tag2 = new NBTTagCompound(); // Create the NMS
														// Stack's NBT (item
														// data)
			tag2.setBoolean("Unbreakable", true); // Set unbreakable value to
													// true
			stack2.setTag(tag2); // Apply the tag to the item
			is = CraftItemStack.asCraftMirror(stack2); // Get the bukkit version
														// of the stack
			ItemMeta im2 = is.getItemMeta();
			im2.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
			im2.setDisplayName(
					ChatColor.DARK_PURPLE + EPICNAMES[weapon.getType()] + " of the " + KLASSEN[weapon.getKlass()]);
			List<String> lore2 = new ArrayList<String>();
			lore2.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + weapon.getDmin() + ChatColor.GRAY + " - "
					+ ChatColor.RED + weapon.getDmax());
			lore2.add(ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + Math.round(weapon.getCc() * 100) + "%");
			lore2.add(ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + Math.round(weapon.getCm() * 100) + "%");
			lore2.add("");
			if (klasse.equals(weapon.getTitle())) {
				lore2.add(ChatColor.GREEN + weapon.getTitle() + ":");
				lore2.add(ChatColor.GREEN + "Increase the damage you");
				lore2.add(ChatColor.GREEN + "deal with " + SKILLS[weapon.getKlass()][weapon.getSkill()]);
				lore2.add(ChatColor.GREEN + "by " + ChatColor.RED
						+ ((double) (int) (weapon.getBoost() * 10000.0)) / 100.0 + "%");
			} else {
				lore2.add(ChatColor.GRAY + weapon.getTitle() + ":");
				lore2.add(ChatColor.GRAY + "Increase the damage you");
				lore2.add(ChatColor.GRAY + "deal with " + SKILLS[weapon.getKlass()][weapon.getSkill()]);
				lore2.add(ChatColor.GRAY + "by " + ((double) (int) (weapon.getBoost() * 10000.0)) / 100.0 + "%");
			}
			lore2.add("");
			lore2.add(ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+" + Math.round(weapon.getHp()));
			lore2.add(ChatColor.GRAY + "Max Energy: " + ChatColor.GREEN + "+" + weapon.getEnergy());
			lore2.add(ChatColor.GRAY + "Cooldown Reduction: " + ChatColor.GREEN + "+"
					+ Math.round(weapon.getCooldown() * 100.0) + "%");
			im2.setLore(lore2);
			is.setItemMeta(im2);
		} else if (weapon.getKat() == 3) {
			is = RARES[weapon.getType()];
			net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(is);
			NBTTagCompound tag2 = new NBTTagCompound(); // Create the NMS
														// Stack's NBT (item
														// data)
			tag2.setBoolean("Unbreakable", true); // Set unbreakable value to
													// true
			stack2.setTag(tag2); // Apply the tag to the item
			is = CraftItemStack.asCraftMirror(stack2); // Get the bukkit version
														// of the stack
			ItemMeta im2 = is.getItemMeta();
			im2.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
			im2.setDisplayName(ChatColor.BLUE + RARENAMES[weapon.getType()] + " of the " + KLASSEN[weapon.getKlass()]);
			List<String> lore2 = new ArrayList<String>();
			lore2.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + weapon.getDmin() + ChatColor.GRAY + " - "
					+ ChatColor.RED + weapon.getDmax());
			lore2.add(ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + Math.round(weapon.getCc() * 100) + "%");
			lore2.add(ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + Math.round(weapon.getCm() * 100) + "%");
			lore2.add("");
			if (klasse.equals(weapon.getTitle())) {
				lore2.add(ChatColor.GREEN + weapon.getTitle() + ":");
				lore2.add(ChatColor.GREEN + "Increase the damage you");
				lore2.add(ChatColor.GREEN + "deal with " + SKILLS[weapon.getKlass()][weapon.getSkill()]);
				lore2.add(ChatColor.GREEN + "by " + ChatColor.RED
						+ ((double) (int) (weapon.getBoost() * 10000.0)) / 100.0 + "%");
			} else {
				lore2.add(ChatColor.GRAY + weapon.getTitle() + ":");
				lore2.add(ChatColor.GRAY + "Increase the damage you");
				lore2.add(ChatColor.GRAY + "deal with " + SKILLS[weapon.getKlass()][weapon.getSkill()]);
				lore2.add(ChatColor.GRAY + "by " + ((double) (int) (weapon.getBoost() * 10000.0)) / 100.0 + "%");
			}
			lore2.add("");
			lore2.add(ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+" + Math.round(weapon.getHp()));
			lore2.add(ChatColor.GRAY + "Max Energy: " + ChatColor.GREEN + "+" + weapon.getEnergy());
			im2.setLore(lore2);
			is.setItemMeta(im2);
		} else {
			is = COMMONS[weapon.getType()];
			net.minecraft.server.v1_10_R1.ItemStack stack = CraftItemStack.asNMSCopy(is);
			NBTTagCompound tag = new NBTTagCompound(); // Create the NMS Stack's
														// NBT (item data)
			tag.setBoolean("Unbreakable", true); // Set unbreakable value to
													// true
			stack.setTag(tag); // Apply the tag to the item
			is = CraftItemStack.asCraftMirror(stack); // Get the bukkit version
														// of the stack
			ItemMeta im = is.getItemMeta();
			im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
			im.setDisplayName(
					ChatColor.GREEN + COMMONNAMES[weapon.getType()] + " of the " + KLASSEN[weapon.getKlass()]);
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + weapon.getDmin() + ChatColor.GRAY + " - "
					+ ChatColor.RED + weapon.getDmax());
			lore.add(ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + Math.round(weapon.getCc() * 100) + "%");
			lore.add(ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + Math.round(weapon.getCm() * 100) + "%");
			lore.add("");
			if (klasse.equals(weapon.getTitle())) {
				lore.add(ChatColor.GREEN + weapon.getTitle() + ":");
				lore.add(ChatColor.GREEN + "Increase the damage you");
				lore.add(ChatColor.GREEN + "deal with " + SKILLS[weapon.getKlass()][weapon.getSkill()]);
				lore.add(ChatColor.GREEN + "by " + ChatColor.RED
						+ ((double) (int) (weapon.getBoost() * 10000.0)) / 100.0 + "%");
			} else {
				lore.add(ChatColor.GRAY + weapon.getTitle() + ":");
				lore.add(ChatColor.GRAY + "Increase the damage you");
				lore.add(ChatColor.GRAY + "deal with " + SKILLS[weapon.getKlass()][weapon.getSkill()]);
				lore.add(ChatColor.GRAY + "by " + ((double) (int) (weapon.getBoost() * 10000.0)) / 100.0 + "%");
			}
			lore.add("");
			lore.add(ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+" + Math.round(weapon.getHp()));
			im.setLore(lore);
			is.setItemMeta(im);
		}
		return is;
	}

	public static void doWeapon(LivingEntity le, Player shooter) {
		if (le.getType() == EntityType.ENDER_DRAGON || le.getType() == EntityType.WITHER) {
			if (le.isDead() != true) {
				Weapon w;
				int x3 = (int) (Math.random() * 100.0);
				if (x3 < 12.5) {
					w = WeaponUtil.generateRandomWeapon(1);
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.getWorld().playSound(p.getLocation(), "legendaryfind", 1, 1);
						ItemStack is = WeaponUtil.generateItemStack(w, WeaponUtil.KLASSEN[w.getKlass()]);
						String maintext = shooter.getDisplayName() + " was lucky and found a ";
						String weaponname = WeaponUtil.LEGENDNAMES[w.getType()] + " of the "
								+ WeaponUtil.KLASSEN[w.getKlass()];
						String cweaponname = ChatColor.GOLD + WeaponUtil.LEGENDNAMES[w.getType()] + " of the "
								+ WeaponUtil.KLASSEN[w.getKlass()];
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
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
					}
				} else if (x3 < 37.5) {
					w = WeaponUtil.generateRandomWeapon(2);
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.getWorld().playSound(p.getLocation(), "epicfind", 1, 1);
						ItemStack is = WeaponUtil.generateItemStack(w, WeaponUtil.KLASSEN[w.getKlass()]);
						String maintext = shooter.getDisplayName() + " was lucky and found a ";
						String weaponname = WeaponUtil.EPICNAMES[w.getType()] + " of the "
								+ WeaponUtil.KLASSEN[w.getKlass()];
						String cweaponname = ChatColor.DARK_PURPLE + WeaponUtil.EPICNAMES[w.getType()] + " of the "
								+ WeaponUtil.KLASSEN[w.getKlass()];
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
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
					}
				} else {
					w = WeaponUtil.generateRandomWeapon(3);
				}
				ArrayList<Weapon> list2 = FileUtilMethods.load(Warlords.getPlugin(Warlords.class).getDataFolder(),
						"/weapons/" + shooter.getUniqueId());
				if (list2 != null) {
					shooter.sendMessage("You earned a weapon");
					if (list2.size() < 54) {
						list2.add(w);
					} else {
						shooter.sendMessage("Weapon inventory already full");
					}
				} else {
					list2 = new ArrayList<Weapon>();
					shooter.sendMessage("You earned a weapon");
					list2.add(w);
				}
				FileUtilMethods.save(list2, Warlords.getPlugin(Warlords.class).getDataFolder(),
						"/weapons/" + shooter.getUniqueId());
			}
		} else {
			if (le.isDead() != true) {
				double rand = Math.random() * 100.0;
				shooter.sendMessage(rand + "");
				if (rand < 0.2 * 100.0) {
					Weapon w = WeaponUtil.generateRandomWeapon();
					if (w.getKat() == 1) {
						for (Player p : Bukkit.getOnlinePlayers()) {
							p.getWorld().playSound(p.getLocation(), "legendaryfind", 1, 1);
							p.sendMessage(shooter.getDisplayName() + " got lucky and found a " + ChatColor.GOLD
									+ LEGENDNAMES[w.getType()] + " of the " + KLASSEN[w.getKlass()]);
						}
					} else if (w.getKat() == 2) {
						for (Player p : Bukkit.getOnlinePlayers()) {
							p.getWorld().playSound(p.getLocation(), "epicfind", 1, 1);
							p.sendMessage(shooter.getDisplayName() + " got lucky and found a " + ChatColor.DARK_PURPLE
									+ EPICNAMES[w.getType()] + " of the " + KLASSEN[w.getKlass()]);
						}
					}
					ArrayList<Weapon> list2 = FileUtilMethods.load(Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/weapons/" + shooter.getUniqueId());
					if (list2 != null) {
						shooter.sendMessage("You earned a weapon");
						if (list2.size() < 54) {
							list2.add(w);
						} else {
							shooter.sendMessage("Weapon inventory already full");
						}
					} else {
						list2 = new ArrayList<Weapon>();
						shooter.sendMessage("You earned a weapon");
						list2.add(w);
					}
					FileUtilMethods.save(list2, Warlords.getPlugin(Warlords.class).getDataFolder(),
							"/weapons/" + shooter.getUniqueId());
				}
			}
		}
	}

	public static Confirmation getWeaponConfirmationInventory(Player p, int rawSlot) {
		Inventory ci = Bukkit.createInventory(p, 27, "Confirmation");
		ItemStack isy = new ItemStack(Material.STAINED_CLAY, 1, (short) 13);
		ItemMeta imy = isy.getItemMeta();
		imy.setDisplayName(ChatColor.RESET + "Confirm");
		isy.setItemMeta(imy);
		ItemStack isn = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
		ItemMeta imn = isn.getItemMeta();
		imn.setDisplayName(ChatColor.RESET + "Cancel");
		isn.setItemMeta(imn);
		ci.setItem(11, isy);
		ci.setItem(15, isn);
		Confirmation c = new Confirmation(rawSlot, ci);
		return c;
	}
}
