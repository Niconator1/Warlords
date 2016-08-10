package com.warlords.util.data.types;

public class Weapon {

	private int type;
	private int kat;
	private double dmin;
	private double dmax;
	private double cc;
	private double cm;
	private double boost;
	private double hp;
	private int energy;
	private double cooldown;
	private double speed;
	private int skill;
	private int ua;
	private int um;
	private int klass;
	private boolean crafted;
	private boolean skillboost;
	private int skin;

	private String title;

	public Weapon(String title, int klass, int kat, int type, double dmin, double dmax, double cc, double cm, int skill,
			double boost, double hp, int energy, double cooldown, double speed, int ua, int um) {
		this.title = title;
		this.type = type;
		this.dmin = dmin;
		this.kat = kat;
		this.dmax = dmax;
		this.cc = cc;
		this.cm = cm;
		this.boost = boost;
		this.hp = hp;
		this.energy = energy;
		this.cooldown = cooldown;
		this.speed = speed;
		this.ua = ua;
		this.um = um;
		this.skill = skill;
		this.klass = klass;
		this.crafted = false;
		this.skillboost = false;
		this.skin=kat;
	}

	public Weapon(String title, int klass, int kat, int type, double dmin, double dmax, double cc, double cm, int skill,
			double boost, double hp, int energy, double cooldown, double speed, int ua, int um, boolean crafted) {
		this.title = title;
		this.type = type;
		this.dmin = dmin;
		this.kat = kat;
		this.dmax = dmax;
		this.cc = cc;
		this.cm = cm;
		this.boost = boost;
		this.hp = hp;
		this.energy = energy;
		this.cooldown = cooldown;
		this.speed = speed;
		this.ua = ua;
		this.um = um;
		this.skill = skill;
		this.klass = klass;
		this.crafted = crafted;
		this.skillboost = false;
		this.skin=kat;
	}

	public Weapon(String title, int klass, int kat, int type, double dmin, double dmax, double cc, double cm, int skill,
			double boost, double hp, int energy, double cooldown, double speed, int ua, int um, boolean crafted,
			boolean skillboost) {
		this.title = title;
		this.type = type;
		this.dmin = dmin;
		this.kat = kat;
		this.dmax = dmax;
		this.cc = cc;
		this.cm = cm;
		this.boost = boost;
		this.hp = hp;
		this.energy = energy;
		this.cooldown = cooldown;
		this.speed = speed;
		this.ua = ua;
		this.um = um;
		this.skill = skill;
		this.klass = klass;
		this.crafted = crafted;
		this.skillboost = skillboost;
		this.skin=kat;
	}

	public Weapon(String title, int klass, int kat, int type, double dmin, double dmax, double cc, double cm, int skill,
			double boost, double hp, int energy, double cooldown, double speed, int ua, int um, boolean crafted,
			boolean skillboost, int skin) {
		this.title = title;
		this.type = type;
		this.dmin = dmin;
		this.kat = kat;
		this.dmax = dmax;
		this.cc = cc;
		this.cm = cm;
		this.boost = boost;
		this.hp = hp;
		this.energy = energy;
		this.cooldown = cooldown;
		this.speed = speed;
		this.ua = ua;
		this.um = um;
		this.skill = skill;
		this.klass = klass;
		this.crafted = crafted;
		this.skillboost = skillboost;
		this.skin = skin;
	}

	public String getTitle() {
		return title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getDmin() {
		return dmin;
	}

	public void setDmin(double dmin) {
		this.dmin = dmin;
	}

	public double getDmax() {
		return dmax;
	}

	public void setDmax(double dmax) {
		this.dmax = dmax;
	}

	public double getCc() {
		return cc;
	}

	public void setCc(double cc) {
		this.cc = cc;
	}

	public double getCm() {
		return cm;
	}

	public void setCm(double cm) {
		this.cm = cm;
	}

	public double getBoost() {
		return boost;
	}

	public void setBoost(double boost) {
		this.boost = boost;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public double getCooldown() {
		return cooldown;
	}

	public void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getUa() {
		return ua;
	}

	public void setUa(int ua) {
		this.ua = ua;
	}

	public int getUm() {
		return um;
	}

	public void setUm(int um) {
		this.um = um;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getKat() {
		return kat;
	}

	public void setKat(int kat) {
		this.kat = kat;
	}

	public int getSkill() {
		return skill;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}

	public int getKlass() {
		return klass;
	}

	public void setKlass(int klass) {
		this.klass = klass;
	}

	public void setCrafted(boolean crafted) {
		this.crafted = crafted;
	}

	public boolean getCrafted() {
		return crafted;
	}

	public void setSkillboost(boolean skillboost) {
		this.skillboost = skillboost;
	}

	public boolean getSkillboost() {
		return skillboost;
	}

	public int getSkin() {
		return skin;
	}

	public void setSkin(int skin) {
		this.skin = skin;
		;
	}
}
