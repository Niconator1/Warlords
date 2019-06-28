package recreate.weapon;

import recreate.classes.WarlordsClassType;

public class Weapon
{
    private final WarlordsWeaponType weaponType;
    private WeaponSkin weaponSkin;
    private final WarlordsClassType warlordsClassType;

    private final double damageMin;
    private final double damageMax;
    private final double critChance;
    private final double critMultiplier;
    private final double weaponBoost;
    private WeaponSpell weaponBoostSpell;
    private final double health;
    private final double energy;
    private final double cooldown;
    private final double speed;
    private final int upgradesCurrent;
    private final int upgradesMax;

    private final WarlordsForgeType weaponForgeType;
    private boolean skillBoost;

    public Weapon(final WarlordsWeaponType weaponType,
        final WeaponSkin weaponSkin,
        final WarlordsClassType warlordsClassType,
        final double damageMin,
        final double damageMax,
        final double critChance,
        final double critMultiplier,
        final double weaponBoost,
        final WeaponSpell weaponBoostSpell,
        final double health,
        final double energy,
        final double cooldown,
        final double speed,
        final int upgradesCurrent,
        final int upgradesMax,
        final WarlordsForgeType weaponForgeType,
        final boolean skillBoost)
    {
        this.weaponType = weaponType;
        this.weaponSkin = weaponSkin;
        this.warlordsClassType = warlordsClassType;
        this.damageMin = damageMin;
        this.damageMax = damageMax;
        this.critChance = critChance;
        this.critMultiplier = critMultiplier;
        this.weaponBoost = weaponBoost;
        this.weaponBoostSpell = weaponBoostSpell;
        this.health = health;
        this.energy = energy;
        this.cooldown = cooldown;
        this.speed = speed;
        this.upgradesCurrent = upgradesCurrent;
        this.upgradesMax = upgradesMax;
        this.weaponForgeType = weaponForgeType;
        this.skillBoost = skillBoost;
    }

    public String getTitle()
    {
        //TODO: [Prefix](Warlords) [Waffenname](Frostbite) of the {Class](Pyromancer)
        return this.weaponSkin.getWarlordsWeaponType().getChatColor() + "" + this.weaponSkin.getTitle() + " of the " +
            this.warlordsClassType.getTitle();
    }

    public WarlordsWeaponType getWeaponType()
    {
        return this.weaponType;
    }

    public WeaponSkin getWeaponSkin()
    {
        return this.weaponSkin;
    }

    public WarlordsClassType getWarlordsClassType()
    {
        return this.warlordsClassType;
    }

    public double getDamageMin()
    {
        return this.damageMin;
    }

    public double getDamageMax()
    {
        return this.damageMax;
    }

    public double getCritChance()
    {
        return this.critChance;
    }

    public double getCritMultiplier()
    {
        return this.critMultiplier;
    }

    public double getWeaponBoost()
    {
        return this.weaponBoost;
    }

    public WeaponSpell getWeaponBoostSpell()
    {
        return this.weaponBoostSpell;
    }

    public double getHealth()
    {
        return this.health;
    }

    public double getEnergy()
    {
        return this.energy;
    }

    public double getCooldown()
    {
        return this.cooldown;
    }

    public double getSpeed()
    {
        return this.speed;
    }

    public int getUpgradesCurrent()
    {
        return this.upgradesCurrent;
    }

    public int getUpgradesMax()
    {
        return this.upgradesMax;
    }

    public WarlordsForgeType getWeaponForgeType()
    {
        return this.weaponForgeType;
    }

    public boolean isSkillBoosted()
    {
        return this.skillBoost;
    }

    public void setSkill(final WeaponSpell weaponBoostSpell)
    {
        this.weaponBoostSpell = weaponBoostSpell;
    }

    public void setSkillBoosted(final boolean skillBoosted)
    {
        this.skillBoost = skillBoosted;
    }

    public void setSkin(final WeaponSkin weaponSkin)
    {
        this.weaponSkin = weaponSkin;
    }
}
