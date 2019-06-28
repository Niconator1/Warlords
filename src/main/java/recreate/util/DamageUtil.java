package recreate.util;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import recreate.classes.WarlordsClass;
import recreate.classes.WarlordsClassType;
import recreate.classes.mage.ArcaneShield;
import recreate.data.Spell;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.weapon.Weapon;

import java.util.Objects;
import java.util.UUID;

public class DamageUtil
{
    public static double getShield(final UUID uuid)
    {
        double shield = 0.0;
        for (final Spell spell : Warlords.get().warlordsSpellHandler.getSpellsByClassType(ArcaneShield.class))
        {
            if (spell.getOwner().compareTo(uuid) == 0)
            {
                final ArcaneShield arcaneShield = (ArcaneShield) spell;
                shield += arcaneShield.getValue();
            }
        }
        return shield;
    }

    public static void damageShield(final UUID uuid,
        double value)
    {
        for (final Spell spell : Warlords.get().warlordsSpellHandler.getSpellsByClassType(ArcaneShield.class))
        {
            if (value > 0)
            {
                if (spell.getOwner().compareTo(uuid) == 0)
                {
                    final ArcaneShield arcaneShield = (ArcaneShield) spell;
                    final double damage = Math.min(arcaneShield.getValue(), value);
                    arcaneShield.setValue(arcaneShield.getValue() - damage);
                    value -= damage;
                }
            }
        }
    }

    public static double calculateHeal(final Player healer,
        final Player victim,
        final Weapon weapon,
        double heal,
        final String type)
    {
        if (Warlords.get().warlordsPlayerManager.doValidChecks(victim.getUniqueId()) && Objects.nonNull(weapon))
        {
            final double critChance = Math.random();
            final boolean isCrit = critChance < weapon.getCritChance();
            heal *= isCrit ? 2.0 : 1.0;
            final double missingWarlordsHealthPoints =
                StatUtil.getWarlordsClassHealthMaxHealth(victim.getUniqueId()) - StatUtil.getWarlordsClassHealth(victim.getUniqueId());
            heal = Math.min(missingWarlordsHealthPoints, heal);
            ChatUtil.doHealMessage(healer, victim, heal, isCrit, type);
            return heal;
        }
        return 0.0;
    }

    public static double calculateDamage(final Player victim,
        double damage)
    {
        if (Warlords.get().warlordsPlayerManager.doValidChecks(victim.getUniqueId()))
        {
            final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(victim.getUniqueId());
            final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
            final WarlordsClassType warlordsClassType = warlordsClass.getType();
            damage *= warlordsClassType.getDamageTaken();
            damage *= 0.5;

            return damage;
        }
        return damage;
    }

    public static double calculateMeleeDamage(final Player attacker,
        final LivingEntity victim,
        final Weapon weapon)
    {
        if (Warlords.get().warlordsPlayerManager.doValidChecks(attacker.getUniqueId()) && Objects.nonNull(weapon))
        {
            double weaponDamage = Math.random() * (weapon.getDamageMax() - weapon.getDamageMin()) + weapon.getDamageMin();
            final double critChance = Math.random();
            final boolean isCrit = critChance < weapon.getCritChance();
            weaponDamage *= isCrit ? 2.0 : 1.0;
            if (Warlords.get().warlordsPlayerManager.doValidChecks(victim.getUniqueId()))
            {
                final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(victim.getUniqueId());
                final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
                final WarlordsClassType warlordsClassType = warlordsClass.getType();
                weaponDamage *= isCrit ? warlordsClassType.getDamageTaken() : warlordsClassType.getDamageTaken();
                final double shieldValue = DamageUtil.getShield(victim.getUniqueId());
                DamageUtil.damageShield(victim.getUniqueId(), Math.min(weaponDamage, shieldValue));
                weaponDamage = Math.max(0, weaponDamage - shieldValue);
                ChatUtil.doMeleeDamageMessage(attacker, (Player) victim, weaponDamage, weaponDamage <= 0.0, isCrit);
                return weaponDamage;
            }
            return weaponDamage;
        }
        return 0.0;
    }

    public static double calculateSpellDamage(final Player attacker,
        final LivingEntity victim,
        final String type,
        final double damageMin,
        final double damageMax,
        final double critChance,
        final double critMultiplier)
    {
        double spellDamage = Math.random() * (damageMax - damageMin) + damageMin;
        final boolean isCrit = critChance < critChance;
        spellDamage *= isCrit ? critMultiplier : 1.0;
        if (Warlords.get().warlordsPlayerManager.doValidChecks(victim.getUniqueId()))
        {
            final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(victim.getUniqueId());
            final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
            final WarlordsClassType warlordsClassType = warlordsClass.getType();
            spellDamage *= isCrit ? warlordsClassType.getDamageTaken() : warlordsClassType.getDamageTaken();
            final double shieldValue = DamageUtil.getShield(victim.getUniqueId());
            DamageUtil.damageShield(victim.getUniqueId(), Math.min(spellDamage, shieldValue));
            spellDamage = Math.max(0, spellDamage - shieldValue);
            ChatUtil.doSpellDamageMessage(attacker, victim, spellDamage, spellDamage <= 0.0, isCrit, type);
            return spellDamage;
        }
        return spellDamage;
    }
}
