package com.warlords.util;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.warlords.main.Warlords;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.PlayerConnection;

public abstract class SpielKlasse {
	private String name;
	public int level;
	public String description;
	public Player p;
	private int eps;
	private int eph;
	private int epmax;
	private int hpmax;
	private double be = 0;
	public int epsBoost = 0;
	public double shield = 0;
	public double ccBoost = 0;
	public double cmulBoost = 0;
	public double speedBoost = 0;

	public SpielKlasse(int level, String name, String description, int eps, int eph, int epmax, int hpmax, Player p) {
		this.level = level;
		this.name = name;
		this.description = description;
		this.p = p;
		this.eps = eps;
		this.eph = eph;
		this.epmax = epmax;
		this.hpmax = hpmax;
		p.setFoodLevel(20);
	}

	public abstract void addAbility(int i);

	public abstract void doAbility(int i);

	public abstract void doCooldown(int i);

	public abstract double getHeal();

	public int getEnergy() {
		return p.getLevel();
	}

	public double getHealth() {
		return p.getHealth();
	}

	public String getName() {
		return name;
	}

	public void addEnergy(double d) {
		int todo = (int) (d);
		be += d - todo;
		int todobe = (int) (be);
		be -= todobe;
		if (getEnergy() + todobe + todo <= epmax + getWeapon().getEnergy()) {
			p.giveExpLevels(todobe + todo);
		} else {
			int diff = epmax + getWeapon().getEnergy() - getEnergy();
			p.giveExpLevels(diff);
		}
		float pro = p.getLevel() / (epmax + getWeapon().getEnergy() + 0.000001f);
		p.setExp(pro);
	}

	public void removeEnergy(int i) {
		if (getEnergy() - i >= 0) {
			p.setLevel(getEnergy() - i);
		} else {
			p.setLevel(0);
		}
		float pro = p.getLevel() / (epmax + getWeapon().getEnergy() + 0.000001f);
		p.setExp(pro);
	}

	public void addHealth(double i) {
		if (getHealth() + i <= 20.0) {
			p.setHealth(getHealth() + i);
		} else {
			p.setHealth(20.0);
		}
	}

	public double healthtohp() {
		return getHealth() / 20.0 * (hpmax + Math.round(getWeapon().getHp()));
	}
	
	public double healthtohp(double i) {
		return i / 20.0 * (hpmax + Math.round(getWeapon().getHp()));
	}

	public void removeHealth(double i) {
		CraftLivingEntity cle = ((CraftLivingEntity) p);
		cle.getHandle().world.broadcastEntityEffect(cle.getHandle(), (byte) 2);
		if (getHealth() - i >= 0) {
			p.setHealth(getHealth() - i);
		} else {
			WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
					"/players/" + p.getUniqueId());
			if (wp != null) {
				if (wp.getMode() == true) {
					for (int j = 0; j < 9; j++) {
						p.getInventory().clear(j);
					}
				}
			}
			p.setHealth(0);
		}
	}

	public double hptohealth(double i) {
		return i / (hpmax + Math.round(getWeapon().getHp())) * 20.0;
	}

	public int getEPS() {
		return eps+epsBoost;
	}

	public void showHealth() {
		PlayerConnection con = ((CraftPlayer) p).getHandle().playerConnection;
		IChatBaseComponent chat = ChatSerializer.a("{\"text\": \"" + ChatColor.GOLD + ChatColor.BOLD + "HP: "
				+ ChatColor.GREEN + ChatColor.BOLD + (int) (healthtohp()) + ChatColor.GOLD + ChatColor.BOLD + "/"
				+ (hpmax + Math.round(getWeapon().getHp())) + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(chat, (byte) 2);
		con.sendPacket(packet);
	}

	public double getMaxHP() {
		return hpmax + getWeapon().getHp();
	}

	public int getEPH() {
		return eph;
	}

	public Weapon getWeapon() {
		WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/players/" + p.getUniqueId());
		if (wp != null) {
			ArrayList<Weapon> wlist = FileUtilMethods.load(Warlords.getPlugin(Warlords.class).getDataFolder(),
					"/weapons/" + p.getUniqueId());
			if (wlist != null) {
				if (wlist.size() > 0) {
					if (wp.getWeaponSlot() < wlist.size()) {
						return wlist.get(wp.getWeaponSlot());
					} else {
						wp.setWeaponSlot(0);
						return wlist.get(wp.getWeaponSlot());
					}
				}
			}
		}
		return null;
	}

	public void setHealthScale() {
		p.setHealthScale(40);
	}

	public void removeHealthScale() {
		p.setHealthScale(20);
	}

	public abstract void setMainAbility();

	public abstract ItemStack getAbility(int i);

	public void damageShield(double dmg) {
		CraftLivingEntity cle = ((CraftLivingEntity) p);
		cle.getHandle().world.broadcastEntityEffect(cle.getHandle(), (byte) 2);
		shield = shield - dmg;
	}

	public double getShield() {
		return shield;
	}
	public void applySpeed(){
		p.setWalkSpeed((float)(0.2f*(1.0+speedBoost+getWeapon().getSpeed())));
	}

	public abstract ItemStack getMainAbility();
}
