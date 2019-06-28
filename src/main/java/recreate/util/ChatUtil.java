package recreate.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import recreate.weapon.WarlordsWeaponType;

public class ChatUtil
{
    public static String mapDamageCauseToChatName(final EntityDamageEvent.DamageCause cause)
    {
        switch (cause)
        {
            case ENTITY_ATTACK:
                return "mob";
            case FALL:
                return "fall";
            case THORNS:
                return "thorn";
            default:
                return "special";
        }
    }

    public static void doDamageMessage(final Player victim,
        final double damage,
        final boolean absorbed,
        final String type)
    {
        if (absorbed == true)
        {
            victim.sendMessage(
                ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + "You absorbed " + type + " damage.");
        } else
        {
            victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + "You took " + ChatColor.RED
                + Math.round(damage) + ChatColor.GRAY + " " + type + " damage.");
        }
    }

    public static void doMeleeDamageMessage(final Player attacker,
        final Player victim,
        final double damage,
        final boolean isAbsorbed,
        final boolean isCrit)
    {
        if (isAbsorbed == true)
        {
            if (attacker.isOnline())
            {
                attacker.sendMessage(
                    ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your melee hit was absorbed by " + victim.getName() + ".");
            }
            victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + "You absorbed "
                + attacker.getName() + "'s melee hit hit.");
        } else
        {
            if (isCrit == true)
            {
                if (attacker.isOnline())
                {
                    attacker.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "You hit "
                        + victim.getName() + " for critical " + ChatColor.RED + ChatColor.BOLD + Math.round(damage)
                        + ChatColor.GRAY + " melee damage.");
                }
                victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + attacker.getName()
                    + " hit you for critical " + ChatColor.RED + ChatColor.BOLD + Math.round(damage) + ChatColor.GRAY
                    + " melee damage.");
            } else
            {
                if (attacker.isOnline())
                {
                    attacker.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "You hit "
                        + victim.getName() + " for " + ChatColor.RED + Math.round(damage) + ChatColor.GRAY
                        + " melee damage.");
                }
                victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + attacker.getName()
                    + " hit you for " + ChatColor.RED + Math.round(damage) + ChatColor.GRAY + " melee damage.");
            }
        }
    }

    public static void doNotCorrectWeaponTypeEquippedMessage(final Player player,
        final WarlordsWeaponType warlordsWeaponType)
    {
        player.sendMessage(ChatColor.GRAY + "You don't have a " + warlordsWeaponType.getChatColor()
            + warlordsWeaponType.name() + ChatColor.GRAY + " weapon equipped");
    }

    public static void doNotEnoughVoidShardsMessage(final Player player)
    {
        player.sendMessage(ChatColor.RED + "You don't have enough "
            + ChatColor.LIGHT_PURPLE + "Void Shards");
    }

    public static void doNoClassSelectedMessage(final Player player)
    {
        player.sendMessage("You do not have a class selected");
    }

    public static void doTooBadWeaponMessage(final Player player,
        final WarlordsWeaponType warlordsWeaponType)
    {
        player.sendMessage(ChatColor.GRAY + "You don't have at least a " + warlordsWeaponType.getChatColor()
            + warlordsWeaponType.name() + ChatColor.GRAY + " weapon equipped");
    }

    public static void doWeaponAlreadyMaxedMessage(final Player player)
    {
        player.sendMessage(ChatColor.RED + "You can't upgrade this weapon any more!");
    }

    public static void doNotEnoughEnergyMessage(final Player player)
    {
    }

    public static void doHealMessage(final Player healer,
        final Player victim,
        final double heal,
        final boolean isCrit,
        final String type)
    {
        if (healer.equals(victim))
        {
            if (healer.isOnline())
            {
                if (isCrit == true)
                {
                    healer.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
                        + " healed you for critical " + ChatColor.GREEN + ChatColor.BOLD + Math.round(heal)
                        + ChatColor.GRAY + " health.");
                } else
                {
                    healer.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
                        + " healed you for " + ChatColor.GREEN + Math.round(heal) + ChatColor.GRAY + " health.");
                }
            }
        } else
        {
            if (isCrit == true)
            {
                if (healer.isOnline())
                {
                    healer.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
                        + " healed " + victim.getName() + " for critical " + ChatColor.GREEN + ChatColor.BOLD
                        + Math.round(heal) + ChatColor.GRAY + " health.");
                }
                victim.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + healer.getName()
                    + "'s " + type + " healed you for critical " + ChatColor.GREEN + ChatColor.BOLD
                    + Math.round(heal) + ChatColor.GRAY + " health.");
            } else
            {
                if (healer.isOnline())
                {
                    healer.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
                        + " healed " + victim.getName() + " for " + ChatColor.GREEN + Math.round(heal)
                        + ChatColor.GRAY + " health.");
                }
                victim.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫" + ChatColor.GRAY + healer.getName()
                    + "'s " + type + " healed you for " + ChatColor.GREEN + Math.round(heal) + ChatColor.GRAY
                    + " health.");
            }
        }
    }

    public static void doSpellDamageMessage(final Player attacker,
        final LivingEntity victim,
        final double damage,
        final boolean absorbed,
        final boolean isCrit,
        final String type)
    {
        if (absorbed == true)
        {
            if (attacker.isOnline())
            {
                attacker.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
                    + " was absorbed by " + victim.getName() + ".");
            }
            victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + "You absorbed "
                + attacker.getName() + "'s " + type + " hit.");
        } else
        {
            if (isCrit == true)
            {
                if (attacker.isOnline())
                {
                    attacker.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
                        + " hit " + victim.getName() + " for critical " + ChatColor.RED + ChatColor.BOLD
                        + Math.round(damage) + ChatColor.GRAY + " damage.");
                }
                victim.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + attacker.getName()
                    + "'s " + type + " hit you for critical " + ChatColor.RED + ChatColor.BOLD + Math.round(damage)
                    + ChatColor.GRAY + " damage.");
            } else
            {
                if (attacker.isOnline())
                {
                    attacker.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "≫ " + ChatColor.GRAY + "Your " + type
                        + " hit " + victim.getName() + " for " + ChatColor.RED + Math.round(damage) + ChatColor.GRAY
                        + " damage.");
                }
                victim.sendMessage(
                    ChatColor.RED + "" + ChatColor.BOLD + "≪ " + ChatColor.GRAY + attacker.getName() + "'s " + type
                        + " hit you for " + ChatColor.RED + Math.round(damage) + ChatColor.GRAY + " damage.");
            }
        }
    }
}
