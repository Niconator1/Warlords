package com.warlords.boss;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import net.minecraft.server.v1_10_R1.BossBattle;
import net.minecraft.server.v1_10_R1.BossBattleServer;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

public class Pontiff {
	private Skeleton s;
	private int healthmax = 2000000;
	private int[] ha = new int[100];
	private int iSlam = 0;
	private int cSlam = 15 * 20;
	private int bSlam = cSlam;
	private BossBattleServer server;
	private Location target;
	private double slamHeight;

	public Pontiff(Location l) {
		s = (Skeleton) l.getWorld().spawnEntity(l.add(0, 1, 0), EntityType.SKELETON);
		server = new BossBattleServer(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + healthmax + "\"}"),
				BossBattle.BarColor.BLUE, BossBattle.BarStyle.PROGRESS);
		server.setProgress(0.01f);
		s.getEquipment().setItemInMainHand(generateSword());
		s.getEquipment().setItemInOffHand(generateLapis());
		s.getEquipment().setItemInMainHandDropChance(0);
		s.getEquipment().setItemInOffHandDropChance(1);
		s.getEquipment().setBootsDropChance(0);
		s.getEquipment().setLeggingsDropChance(0);
		s.getEquipment().setChestplateDropChance(0);
		s.getEquipment().setHelmetDropChance(0);
		s.setCustomName("Pontiff Sulyvahn");
		s.getEquipment().setBoots(setColor(new ItemStack(Material.LEATHER_BOOTS)));
		s.getEquipment().setChestplate(setColor(new ItemStack(Material.LEATHER_CHESTPLATE)));
		s.getEquipment().setLeggings(setColor(new ItemStack(Material.LEATHER_LEGGINGS)));
		s.getEquipment().setHelmet(setColor(new ItemStack(Material.LEATHER_HELMET)));
		s.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25);
		s.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(15);
		s.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(80);
		s.setRemoveWhenFarAway(false);
		for (int i = 0; i < ha.length; i++) {
			ha[i] = healthmax / ha.length;
		}
	}

	private ItemStack generateLapis() {
		ItemStack is = new ItemStack(Material.INK_SACK, 1, (short) 4);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("Soul of Pontiff Sulyvahn");
		is.setItemMeta(im);
		return is;
	}

	private ItemStack generateSword() {
		ItemStack is = new ItemStack(Material.GOLD_SWORD);
		net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(is);
		NBTTagCompound tag2 = new NBTTagCompound();
		tag2.setBoolean("Unbreakable", true);
		stack2.setTag(tag2);
		is = CraftItemStack.asCraftMirror(stack2);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("Profaned Greatsword");
		is.setItemMeta(im);
		is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 4);
		is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 60);
		is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		is.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 5);
		return is;
	}

	public Skeleton getSkel() {
		return s;
	}

	public int getMax() {
		return healthmax;
	}

	public ItemStack setColor(ItemStack item) {
		net.minecraft.server.v1_10_R1.ItemStack stack2 = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag2 = new NBTTagCompound();
		tag2.setBoolean("Unbreakable", true);
		stack2.setTag(tag2);
		item = CraftItemStack.asCraftMirror(stack2);
		LeatherArmorMeta lch = (LeatherArmorMeta) item.getItemMeta();
		lch.setColor(Color.BLACK);
		item.setItemMeta(lch);
		return item;
	}

	public BossBattleServer getBossServer() {
		return server;
	}

	public void setBossServer(BossBattleServer server) {
		this.server = server;
	}

	public boolean removeHealth(int hp) {
		for (int i = 0; i < ha.length; i++) {
			if (ha[i] >= hp) {
				ha[i] = ha[i] - hp;
				s.setHealth(getHealth() / ((double) (healthmax)) * s.getMaxHealth());
				return true;
			} else {
				hp = hp - ha[i];
				ha[i] = 0;
			}
		}
		if (hp > 0) {
			s.setHealth(0);
			return false;
		}
		return true;
	}

	public int getHealth() {
		int sum = 0;
		for (int i = 0; i < ha.length; i++) {
			sum += ha[i];
		}
		return sum;
	}

	public void reduceSkillCooldown() {
		bSlam--;
	}

	public void increaseSlam() {
		iSlam++;
	}

	public int getSlamI() {
		return iSlam;
	}

	public void resetSlam() {
		iSlam = 0;
		bSlam = cSlam;
	}

	public int getSC() {
		return bSlam;
	}

	public void setTarget(Location location) {
		this.target = location;
	}

	public Location getTarget() {
		return target;
	}

	public double getSlamHeight() {
		return slamHeight;
	}

	public void setSlamHeight(double d) {
		this.slamHeight = d;
	}
}
