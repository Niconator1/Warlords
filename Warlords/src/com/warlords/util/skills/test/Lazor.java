package com.warlords.util.skills.test;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.warlords.main.Warlords;
import com.warlords.util.PlayerUtil;
import com.warlords.util.SpielKlasse;
import com.warlords.util.UtilMethods;
import com.warlords.util.WeaponUtil;

public class Lazor {
	private Player shooter;
	private double dmin;
	private double dmax;
	private double critc;
	private double critm;
	private double duration;
	private int range;
	private ArrayList<ArmorStand> list = new ArrayList<ArmorStand>();

	public Lazor(Player p, double min, double max, double cc, double cm, double dur, int range) {
		this.shooter = p;
		this.dmin = min;
		this.dmax = max;
		this.critc = cc;
		this.critm = cm;
		this.duration = dur * 5;
		this.range = range;
		for (int i = 0; i < range; i++) {
			ArmorStand a = (ArmorStand) p.getWorld().spawnEntity(new Location(p.getWorld(), 0, 0, 0),
					EntityType.ARMOR_STAND);
			a.setInvulnerable(true);
			a.setGravity(false);
			a.setVisible(false);
			a.setHelmet(new ItemStack(Material.RED_MUSHROOM));
			list.add(a);
		}
	}

	public Player getShooter() {
		return shooter;
	}

	public double dmin() {
		return dmin;
	}

	public double dmax() {
		return dmax;
	}

	public double critc() {
		return critc;
	}

	public double critm() {
		return critm;
	}

	public double getDuration() {
		return duration;
	}

	public int getRange() {
		return range;
	}

	public void clearList() {
		for (int i = 0; i < list.size(); i++) {
			ArmorStand a = list.get(i);
			a.remove();
		}
		list.clear();
	}

	public void addArmorStand(ArmorStand a) {
		list.add(a);
	}

	public void moveArmorStand() {
		int x = 30;
		for (int i = 0; i < list.size(); i++) {
			ArmorStand a = list.get(i);
			Location l = shooter.getLocation().add(shooter.getLocation().getDirection().normalize().multiply(i + 2));
			l.setY(l.getY()-0.05);
			a.teleport(l);	
			a.setHeadPose(a.getHeadPose()
					.setX(shooter.getLocation().getPitch()/90 * 0.5 * Math.PI));
			if(a.getLocation().add(0, 1, 0).getBlock().getType()!=Material.AIR){
				a.setHelmet(null);
				x=i;
			}
			else{
				if(x<i){
					a.setHelmet(null);
				}
				else{
					a.setHelmet(new ItemStack(Material.RED_MUSHROOM));
					for(Entity e :a.getNearbyEntities(0.5, 0.5, 0.5)){
						if(e instanceof LivingEntity){
							if(e.isDead()==false){
								LivingEntity le = (LivingEntity) e;
								if(!le.equals(shooter)){
									if (le instanceof Player) {
										if(PlayerUtil.isAttackingPlayers(shooter)){
											Player p2 = (Player) le;
											SpielKlasse sk = Warlords.getKlasse(p2);
											if (sk != null) {
												double dmg = UtilMethods.damage("FIRIN' MAH LAZOR",critc, critm, dmin, dmax, shooter, sk);
												if (dmg != 0) {
													sk.removeHealth(sk.hptohealth(dmg));
													Vector kb = a.getLocation().getDirection().normalize().multiply(0.5);
													kb.setY(kb.getY()+(1-Math.abs(kb.getY()))*0.5);
													le.setVelocity(le.getVelocity().add(kb));
												}
												UtilMethods.sendSoundPacket(shooter, "entity.arrow.hit_player", shooter.getLocation());
											}
										}
									} else {
										if(!(le instanceof ArmorStand)){
											double dmg = UtilMethods.hptohealth(UtilMethods.damage(critc, critm, dmin, dmax, shooter, "FIRIN' MAH LAZOR"));
											double hp = le.getHealth();
											if (hp < dmg) {
												WeaponUtil.doWeapon(le, shooter);
												le.setHealth(0);
											} else {
												le.setHealth(le.getHealth()-dmg);
											}
											Vector kb = a.getLocation().getDirection().normalize().multiply(0.2);
											kb.setY(kb.getY()+(1-Math.abs(kb.getY()))*0.2);
											le.setVelocity(le.getVelocity().add(kb));
											UtilMethods.sendSoundPacket(shooter, "entity.arrow.hit_player", shooter.getLocation());
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

	public void decreaseDuration() {
		duration--;
	}
}
