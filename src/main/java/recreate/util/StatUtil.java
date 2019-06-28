package recreate.util;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import recreate.classes.WarlordsClass;
import recreate.classes.WarlordsClassType;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.weapon.Weapon;

import java.util.Objects;
import java.util.UUID;

public class StatUtil
{

    public static void resetStats(final UUID uuid)
    {
        final Player player = Bukkit.getPlayer(uuid);
        player.setHealthScale(40.0);
        player.setFoodLevel(20);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
        player.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(32.0);
        player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.0);
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.7);
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2.0);
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.0);
        player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0.0);
        player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(0.0);
        player.getAttribute(Attribute.GENERIC_LUCK).setBaseValue(0.0);
    }

    public static void giveStats(final UUID uuid)
    {
        final Player player = Bukkit.getPlayer(uuid);
        player.setHealthScale(20.0);
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(20);
        double speedMultiplier = 1.0;
        if (Warlords.get().warlordsPlayerManager.doValidChecks(uuid))
        {
            final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(uuid);
            final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
            final Weapon weapon = warlordsPlayer.getActiveWeapon();
            if (Objects.nonNull(weapon))
            {
                speedMultiplier += weapon.getSpeed();
                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.7 * speedMultiplier);
                if (warlordsClass.getType() != WarlordsClassType.NONE)
                {
                    player.setLevel(warlordsClass.getType().getEnergyMax());
                }
            }
        }
    }

    public static double getWarlordsClassHealth(final UUID uuid)
    {
        final Player player = Bukkit.getPlayer(uuid);
        return player.getHealth() / player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() *
            getWarlordsClassHealthMaxHealth(uuid);
    }

    public static double getWarlordsClassHealthMaxHealth(final UUID uuid)
    {
        if (Warlords.get().warlordsPlayerManager.doValidChecks(uuid))
        {
            final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(uuid);
            final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
            double maxHealth = warlordsClass.getType().getHealthMax();
            final Weapon weapon = warlordsPlayer.getActiveWeapon();
            if (Objects.nonNull(weapon))
            {
                maxHealth += weapon.getHealth();
            }
            return maxHealth;
        }
        return 0.0;
    }

    public static double getWarlordsClassEnergy(final UUID uuid)
    {
        final Player player = Bukkit.getPlayer(uuid);
        return player.getLevel();
    }

    public static double getWarlordsClassMaxEnergy(final UUID uuid)
    {
        if (Warlords.get().warlordsPlayerManager.doValidChecks(uuid))
        {
            final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(uuid);
            final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
            double maxEnergy = warlordsClass.getType().getEnergyMax();
            final Weapon weapon = warlordsPlayer.getActiveWeapon();
            if (Objects.nonNull(weapon))
            {
                maxEnergy += weapon.getEnergy();
            }
            return maxEnergy;
        }
        return 0.0;
    }

    public static double healthToWarlordsHealthPoints(final UUID uuid,
        final double health)
    {
        return health / 20.0 * getWarlordsClassHealthMaxHealth(uuid);
    }

    public static double warlordsHealthPointsToHealth(final UUID uuid,
        final double warlordsHealthPoints)
    {
        return warlordsHealthPoints / getWarlordsClassHealthMaxHealth(uuid) * 20.0;
    }

    public static void addEnergy(final UUID uuid,
        final int energy)
    {
        final Player player = Bukkit.getPlayer(uuid);
        if (getWarlordsClassEnergy(uuid) + energy <= getWarlordsClassMaxEnergy(uuid))
        {
            player.setLevel(player.getLevel() + energy);
        }
    }

    public static boolean removeEnergy(final UUID uuid,
        final int energy)
    {
        final Player player = Bukkit.getPlayer(uuid);
        if (getWarlordsClassEnergy(uuid) - energy >= 0)
        {
            player.setLevel(player.getLevel() - energy);
            return true;
        }
        return false;
    }
}
