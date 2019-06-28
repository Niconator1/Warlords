package recreate.util;

import net.minecraft.server.v1_14_R1.ChatMessageType;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import recreate.classes.WarlordsClass;
import recreate.classes.WarlordsClassType;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.weapon.WarlordsForgeType;
import recreate.weapon.WarlordsWeaponType;
import recreate.weapon.Weapon;
import recreate.weapon.WeaponSkin;
import recreate.weapon.WeaponSpell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class WeaponUtil
{
    public static Weapon generateRandomWeapon()
    {
        final Random random = new Random();
        final int weaponTypeInt = random.nextInt(10000);
        final WarlordsClassType warlordsClassType = WarlordsClassType.values()[random.nextInt(WarlordsClassType.values().length - 1)];
        if (weaponTypeInt <= 37)
        {
            return generateRandomWeapon(WarlordsWeaponType.LEGENDARY, warlordsClassType, WarlordsForgeType.NORMAL);
        } else if (weaponTypeInt <= 287)
        {
            return generateRandomWeapon(WarlordsWeaponType.EPIC, warlordsClassType, WarlordsForgeType.NORMAL);
        } else if (weaponTypeInt <= 2787)
        {
            return generateRandomWeapon(WarlordsWeaponType.RARE, warlordsClassType, WarlordsForgeType.NORMAL);
        } else
        {
            return generateRandomWeapon(WarlordsWeaponType.COMMON, warlordsClassType, WarlordsForgeType.NORMAL);
        }
    }

    public static Weapon generateRandomWeapon(final WarlordsWeaponType warlordsWeaponType,
        final WarlordsClassType warlordsClassType,
        final WarlordsForgeType warlordsForgeType)
    {
        final Random random = new Random();
        final WeaponSkin weaponSkin =
            WeaponSkin.getSkinsByType(warlordsWeaponType).get(random.nextInt(WeaponSkin.getSkinsByType(warlordsWeaponType).size()));
        double damageMin = 0.0;
        double damageMax = 0.0;
        double critChance = 0.0;
        double critMultiplier = 0.0;
        double weaponBoost = 0.0;
        final WeaponSpell weaponBoostSpell =
            WeaponSpell.getSpellsByClass(warlordsClassType).get(random.nextInt(WeaponSpell.getSpellsByClass(warlordsClassType).size()));
        double health = 0.0;
        double energy = 0.0;
        double cooldown = 0.0;
        double speed = 0.0;
        final int upgradesCurrent = 0;
        int upgradesMax = 0;

        final boolean skillBoost = false;

        switch (warlordsWeaponType)
        {
            case LEGENDARY:
                damageMin = random.nextInt(91) / 10.0 + 93.0;
                damageMax = random.nextInt(121) / 10.0 + 126.0;
                critChance = (random.nextInt(11) + 15) / 100.0;
                critMultiplier = (random.nextInt(21) + 180) / 100.0;
                weaponBoost = (random.nextInt(6) + 10) / 100.0;
                health = random.nextInt(151) + 250;
                energy = (random.nextInt(6) + 20);
                cooldown = (random.nextInt(6) + 5) / 100.0;
                speed = (random.nextInt(6) + 5) / 100.0;
                upgradesMax = random.nextInt(3);
                break;
            case EPIC:
                damageMin = random.nextInt(21) + 70;
                damageMax = random.nextInt(21) + 100;
                critChance = (random.nextInt(6) + 15) / 100.0;
                critMultiplier = (random.nextInt(26) + 150) / 100.0;
                weaponBoost = (random.nextInt(3) + 7) / 100.0;
                health = random.nextInt(101) + 200;
                energy = (random.nextInt(10) + 11);
                cooldown = (random.nextInt(4) + 1) / 100.0;
                upgradesMax = random.nextInt(2);
                break;
            case RARE:
                damageMin = random.nextInt(21) + 60;
                damageMax = random.nextInt(21) + 90;
                critChance = (random.nextInt(6) + 10) / 100.0;
                critMultiplier = (random.nextInt(26) + 125) / 100.0;
                weaponBoost = (random.nextInt(3) + 4) / 100.0;
                health = random.nextInt(101) + 100;
                energy = (random.nextInt(10) + 1);
                break;
            default:
                damageMin = random.nextInt(21) + 50;
                damageMax = random.nextInt(21) + 80;
                critChance = (random.nextInt(10) + 1) / 100.0;
                critMultiplier = (random.nextInt(26) + 100) / 100.0;
                weaponBoost = (random.nextInt(3) + 1) / 100.0;
                health = random.nextInt(21) + 60;
                break;
        }
        return new Weapon(warlordsWeaponType, weaponSkin, warlordsClassType, damageMin, damageMax, critChance, critMultiplier, weaponBoost,
            weaponBoostSpell, health, energy, cooldown, speed, upgradesCurrent, upgradesMax, warlordsForgeType, skillBoost);
    }

    public static ItemStack generateWeaponItemStack(final Weapon weapon,
        final WarlordsClass warlordsClass)
    {
        if (Objects.isNull(weapon))
        {
            return null;
        }
        final ItemStack weaponItemStack = new ItemStack(weapon.getWeaponSkin().getMaterial());
        final ItemMeta itemMetaWeaponItemStack = weaponItemStack.getItemMeta();
        itemMetaWeaponItemStack.setUnbreakable(true);
        itemMetaWeaponItemStack.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        itemMetaWeaponItemStack.setDisplayName(weapon.getTitle());
        final List<String> lore = new ArrayList<>();

        lore.add(
            String.format(ChatColor.GRAY + "Damage: " + ChatColor.RED + "%s" + ChatColor.GRAY + " - " + ChatColor.RED + "%s",
                String.format("#.##", weapon.getDamageMin()), String.format("#.##", weapon.getDamageMax())));
        lore.add(String.format(ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + "%s%", String.format("#", weapon.getCritChance() * 100.0)));
        lore.add(String.format(ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + "%s%", String.format("#", weapon.getCritMultiplier() * 100.0)));
        lore.add("");
        ChatColor weaponBonusColor = ChatColor.GRAY;
        if (Objects.nonNull(warlordsClass) && weapon.getWarlordsClassType() == warlordsClass.getType())
        {
            weaponBonusColor = ChatColor.GREEN;
        }
        lore.add(weaponBonusColor + weapon.getWeaponBoostSpell().getTitle() + " (" + weapon.getWarlordsClassType().getTitle() + ")" + ":");
        lore.add(weaponBonusColor + "Increase the damage you");
        lore.add(weaponBonusColor + "deal with " + weapon.getWeaponBoostSpell().getTitle());
        lore.add(String.format(weaponBonusColor + "by " + ChatColor.RED + "%s%", String.format("#", weapon.getWeaponBoost() * 100.0)));
        lore.add("");
        lore.add(ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+" + Math.round(weapon.getHealth()));
        if (weapon.getEnergy() > 0)
        {
            lore.add(ChatColor.GRAY + "Max Energy: " + ChatColor.GREEN + "+" + weapon.getEnergy());
        }
        if (weapon.getCooldown() > 0)
        {
            lore.add(
                String.format(ChatColor.GRAY + "Cooldown Reduction: " + ChatColor.GREEN + "+%s%", String.format("#", weapon.getCooldown() * 100.0)));
        }
        if (weapon.getSpeed() > 0)
        {
            lore.add(String.format(ChatColor.GRAY + "Speed: " + ChatColor.GREEN + "+%s%", String.format("#", weapon.getSpeed() * 100.0)));
        }
        if (weapon.getWeaponForgeType() == WarlordsForgeType.CRAFTED || weapon.getUpgradesCurrent() > 0 || weapon.getUpgradesMax() > 0 ||
            weapon.isSkillBoosted())
        {
            lore.add("");
        }
        if (weapon.isSkillBoosted())
        {
            lore.add(ChatColor.GOLD + "Skill Boost Unlocked");
        }
        if (weapon.getWeaponForgeType() == WarlordsForgeType.CRAFTED)
        {
            lore.add(ChatColor.AQUA + "Crafted");
        }
        if (weapon.getUpgradesMax() > 0)
        {
            if (weapon.getWeaponType() == WarlordsWeaponType.LEGENDARY)
            {
                if (weapon.getUpgradesCurrent() > 0)
                {
                    lore.add(ChatColor.LIGHT_PURPLE + "Void Forged [" + weapon.getUpgradesCurrent() + "/" + weapon.getUpgradesMax() + "]");
                } else
                {
                    lore.add(ChatColor.GRAY + "Void Forged [" + weapon.getUpgradesCurrent() + "/" + weapon.getUpgradesMax() + "]");
                }
            } else if (weapon.getWeaponType() == WarlordsWeaponType.EPIC)
            {
                if (weapon.getUpgradesCurrent() > 0)
                {
                    lore.add(ChatColor.AQUA + "Magic Forged [" + weapon.getUpgradesCurrent() + "/" + weapon.getUpgradesMax() + "]");
                } else
                {
                    lore.add(ChatColor.GRAY + "Magic Forged [" + weapon.getUpgradesCurrent() + "/" + weapon.getUpgradesMax() + "]");
                }
            }
        }
        itemMetaWeaponItemStack.setLore(lore);
        weaponItemStack.setItemMeta(itemMetaWeaponItemStack);
        return weaponItemStack;
    }

    public static IChatBaseComponent getChatBaseComponentForWeapon(final Weapon weapon,
        final ItemStack itemStackWeapon,
        final String title)
    {
        String lore = "";
        for (int i = 0; i < itemStackWeapon.getItemMeta().getLore().size(); i++)
        {
            final String s = itemStackWeapon.getItemMeta().getLore().get(i);
            if (i < itemStackWeapon.getItemMeta().getLore().size() - 1)
            {
                lore += "\\\"" + s + "\\\",";
            } else
            {
                lore += "\\\"" + s + "\\\"";
            }
        }
        final IChatBaseComponent chatBaseComponent = ChatSerializer
            .a("{\"text\":\"" + title + "\", \"extra\":[{\"text\":\"" +
                itemStackWeapon.getItemMeta().getDisplayName()
                + "\",\"color\":\"" + weapon.getWeaponType().getChatColor().name().toLowerCase() +
                "\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,tag:{display:{Name:\\\""
                + itemStackWeapon.getItemMeta().getDisplayName() + "\\\",Lore:[" + lore + "]}}}\"}}]}");
        return chatBaseComponent;
    }

    public static void handleWeaponDropPossibility(final Player player,
        final LivingEntity victim)
    {
        final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
        if (Objects.nonNull(warlordsPlayer))
        {
            if (victim.getType() == EntityType.ENDER_DRAGON || victim.getType() == EntityType.WITHER)
            {
                final Random random = new Random();
                final WarlordsClassType warlordsClassType = WarlordsClassType.values()[random.nextInt(WarlordsClassType.values().length - 1)];
                final Weapon weapon = generateRandomWeapon(WarlordsWeaponType.LEGENDARY, warlordsClassType, WarlordsForgeType.NORMAL);
                final PacketPlayOutChat packet =
                    new PacketPlayOutChat(getChatBaseComponentForWeapon(weapon, generateWeaponItemStack(weapon, warlordsPlayer.getWarlordsClass()),
                        player.getDisplayName() + " was lucky and found a "), ChatMessageType.CHAT);
                for (final Player p : Bukkit.getOnlinePlayers())
                {
                    player.playSound(player.getLocation(), "legendaryfind", 1f, 1f);
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                }
                player.sendMessage("You earned a weapon");
                final ArrayList<Weapon> weaponInventory = warlordsPlayer.getWeaponInventory();
                if (weaponInventory.size() < 45)
                {
                    weaponInventory.add(weapon);
                } else
                {
                    player.sendMessage("Weapon inventory already full");
                }
            } else
            {
                if (Math.random() < 0.2)
                {
                    final Weapon weapon = WeaponUtil.generateRandomWeapon();
                    final PacketPlayOutChat packet =
                        new PacketPlayOutChat(
                            getChatBaseComponentForWeapon(weapon, generateWeaponItemStack(weapon, warlordsPlayer.getWarlordsClass()),
                                player.getDisplayName() + " was lucky and found a "),
                            ChatMessageType.CHAT);
                    switch (weapon.getWeaponType())
                    {
                        case LEGENDARY:
                            for (final Player p : Bukkit.getOnlinePlayers())
                            {
                                player.playSound(player.getLocation(), "legendaryfind", 1f, 1f);
                                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                            }
                            break;
                        case EPIC:
                            for (final Player p : Bukkit.getOnlinePlayers())
                            {
                                player.playSound(player.getLocation(), "epicfind", 1f, 1f);
                                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                            }
                            break;
                        default:
                            break;
                    }
                    player.sendMessage("You earned a weapon");
                    final ArrayList<Weapon> weaponInventory = warlordsPlayer.getWeaponInventory();
                    if (weaponInventory.size() < 45)
                    {
                        weaponInventory.add(weapon);
                    } else
                    {
                        player.sendMessage("Weapon inventory already full");
                    }
                }
            }
        }
    }

    public static ItemStack generateChangeItemStack(final Weapon weaponFirst,
        final Weapon weaponSecond)
    {
        if (Objects.isNull(weaponFirst) || Objects.isNull(weaponSecond))
        {
            return null;
        }
        final ItemStack weaponItemStack = new ItemStack(weaponFirst.getWeaponSkin().getMaterial());
        final ItemMeta itemMetaWeaponItemStack = weaponItemStack.getItemMeta();
        itemMetaWeaponItemStack.setUnbreakable(true);
        itemMetaWeaponItemStack.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        itemMetaWeaponItemStack.setDisplayName(weaponFirst.getTitle());
        final List<String> lore = new ArrayList<>();
        ChatColor color = ColorUtil
            .getCompareColor(weaponFirst.getDamageMin() + weaponFirst.getDamageMax(), weaponSecond.getDamageMin() + weaponSecond.getDamageMax());
        lore.add(String.format(ChatColor.GRAY + "Damage: " + ChatColor.RED + ChatColor.RED
                + "%s" + ChatColor.GRAY + " - " + ChatColor.RED
                + ChatColor.RED + "%s" + color + " ➠ " + ChatColor.RED
                + ChatColor.RED + "%s" + ChatColor.GRAY + " - "
                + ChatColor.RED + "%s", String.format("#.#", weaponFirst.getDamageMin()), String.format("#.#", weaponFirst.getDamageMax()),
            String.format("#.#", weaponSecond.getDamageMin()),
            String.format("#.#", weaponSecond.getDamageMax())));
        color = ColorUtil.getCompareColor(weaponFirst.getCritChance(), weaponSecond.getCritChance());
        lore.add(String.format(ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + "%s%" + color + " ➠ "
                + ChatColor.RED + "%s%", String.format("#", weaponFirst.getCritChance() * 100.0),
            String.format("#", weaponSecond.getCritChance() * 100.0)));
        color = ColorUtil.getCompareColor(weaponFirst.getCritMultiplier(), weaponSecond.getCritMultiplier());
        lore.add(String.format(ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + "%s%" + color
                + " ➠ " + ChatColor.RED + "%s%", String.format("#", weaponFirst.getCritMultiplier() * 100.0),
            String.format("#", weaponSecond.getCritMultiplier() * 100.0)));
        lore.add("");
        lore.add(ChatColor.GREEN + weaponFirst.getWeaponBoostSpell().getTitle() + " (" + weaponFirst.getWarlordsClassType().getTitle() + ")" + ":");
        lore.add(ChatColor.GREEN + "Increase the damage you");
        lore.add(ChatColor.GREEN + "deal with " + weaponFirst.getWeaponBoostSpell().getTitle());
        color = ColorUtil.getCompareColor(weaponFirst.getWeaponBoost(), weaponSecond.getWeaponBoost());
        lore.add(String.format(ChatColor.GREEN + "by " + ChatColor.RED + "%s%"
                + color + " ➠ " + ChatColor.RED + "%s%", String.format("#.##", weaponFirst.getWeaponBoost() * 100.0),
            String.format("#.##", weaponFirst.getWeaponBoost() * 100.0)));
        lore.add("");
        color = ColorUtil.getCompareColor(Math.round(weaponFirst.getHealth()), Math.round(weaponSecond.getHealth()));
        lore.add(String.format(ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+%s" + color + " ➠ "
            + ChatColor.GREEN + "+%s", String.format("#", weaponFirst.getHealth()), String.format("#", weaponSecond.getHealth())));
        if (weaponFirst.getEnergy() > 0)
        {
            color = ColorUtil.getCompareColor(Math.round(weaponFirst.getEnergy()), Math.round(weaponSecond.getEnergy()));
            lore.add(String.format(ChatColor.GRAY + "Max Energy: " + ChatColor.GREEN + "+%s" + color + " ➠ "
                + ChatColor.GREEN + "+%s", weaponFirst.getEnergy(), weaponSecond.getEnergy()));
        }
        if (weaponFirst.getCooldown() > 0)
        {
            color = ColorUtil.getCompareColor(weaponFirst.getCooldown(), weaponSecond.getCooldown());
            lore.add(String.format(ChatColor.GRAY + "Cooldown Reduction: " + ChatColor.GREEN + "+%s%" + color + " ➠ " + ChatColor.GREEN + "+%s%",
                String.format("#", weaponFirst.getCooldown() * 100.0), String.format("#", weaponSecond.getCooldown() * 100.0)));
        }
        if (weaponFirst.getSpeed() > 0)
        {
            color = ColorUtil.getCompareColor(weaponFirst.getSpeed(), weaponSecond.getSpeed());
            lore.add(String.format(ChatColor.GRAY + "Speed: " + ChatColor.GREEN + "+%s%" + color + " ➠ " + ChatColor.GREEN + "+%s%",
                String.format("#", weaponFirst.getSpeed() * 100.0), String.format("#", weaponSecond.getSpeed() * 100.0)));
        }
        if (weaponFirst.getWeaponForgeType() == WarlordsForgeType.CRAFTED || weaponFirst.getUpgradesMax() > 0 || weaponFirst.isSkillBoosted())
        {
            lore.add("");
        }
        if (weaponFirst.isSkillBoosted())
        {
            lore.add(ChatColor.GOLD + "Skill Boost Unlocked");
        }
        if (weaponFirst.getWeaponForgeType() == WarlordsForgeType.CRAFTED)
        {
            lore.add(ChatColor.AQUA + "Crafted");
        }
        if (weaponFirst.getUpgradesMax() > 0)
        {
            if (weaponFirst.getWeaponType() == WarlordsWeaponType.LEGENDARY)
            {
                if (weaponFirst.getUpgradesCurrent() > 0)
                {
                    if (weaponFirst.getUpgradesCurrent() < weaponSecond.getUpgradesCurrent())
                    {
                        lore.add(
                            ChatColor.LIGHT_PURPLE + "Void Forged [" + weaponFirst.getUpgradesCurrent() + "/" + weaponFirst.getUpgradesMax() + "]" +
                                ChatColor.GREEN
                                + " ➠ " + ChatColor.LIGHT_PURPLE + "Void Forged [" + weaponFirst.getUpgradesCurrent() + "/" +
                                weaponSecond.getUpgradesMax() + "]");
                    } else
                    {
                        lore.add(
                            ChatColor.LIGHT_PURPLE + "Void Forged [" + weaponFirst.getUpgradesCurrent() + "/" + weaponFirst.getUpgradesMax() + "]");
                    }
                } else
                {
                    if (weaponFirst.getUpgradesCurrent() < weaponSecond.getUpgradesCurrent())
                    {

                    } else
                    {
                        lore.add(ChatColor.GRAY + "Void Forged [" + weaponFirst.getUpgradesCurrent() + "/" + weaponFirst.getUpgradesMax() + "]");
                    }
                }
            } else if (weaponFirst.getWeaponType() == WarlordsWeaponType.EPIC)
            {
                if (weaponFirst.getUpgradesCurrent() > 0)
                {
                    if (weaponFirst.getUpgradesCurrent() < weaponSecond.getUpgradesCurrent())
                    {
                        lore.add(
                            ChatColor.AQUA + "Magic Forged [" + weaponFirst.getUpgradesCurrent() + "/" + weaponFirst.getUpgradesMax() + "]" +
                                ChatColor.GREEN
                                + " ➠ " + ChatColor.LIGHT_PURPLE + "Magic Forged [" + weaponFirst.getUpgradesCurrent() + "/" +
                                weaponSecond.getUpgradesMax() + "]");
                    } else
                    {
                        lore.add(ChatColor.AQUA + "Magic Forged [" + weaponFirst.getUpgradesCurrent() + "/" + weaponFirst.getUpgradesMax() + "]");
                    }
                } else
                {
                    if (weaponFirst.getUpgradesCurrent() < weaponSecond.getUpgradesCurrent())
                    {

                    } else
                    {
                        lore.add(ChatColor.GRAY + "Magic Forged [" + weaponFirst.getUpgradesCurrent() + "/" + weaponFirst.getUpgradesMax() + "]");
                    }
                }
            }
        }
        itemMetaWeaponItemStack.setLore(lore);
        weaponItemStack.setItemMeta(itemMetaWeaponItemStack);
        return weaponItemStack;
    }

    public static Weapon craftWeapon(final WarlordsWeaponType warlordsWeaponType,
        final WarlordsClassType warlordsClassType)
    {
        return generateRandomWeapon(warlordsWeaponType, warlordsClassType, WarlordsForgeType.CRAFTED);
    }

    public static Weapon upgradeWeapon(final Weapon weapon)
    {
        if (Objects.isNull(weapon))
        {
            return null;
        }
        final double upgradeMultiplier = 1.0 / (1.0 + weapon.getUpgradesCurrent() * 0.075) * (1.0 + (weapon.getUpgradesCurrent() + 1) * 0.075);
        final double upgradeMultiplierHealth = 1.0 / (1.0 + weapon.getUpgradesCurrent() * 0.25) * (1.0 + (weapon.getUpgradesCurrent() + 1) * 0.25);
        final double upgradeMultiplierEnergy = 1.0 / (1.0 + weapon.getUpgradesCurrent() * 0.1) * (1.0 + (weapon.getUpgradesCurrent() + 1) * 0.1);
        return new Weapon(weapon.getWeaponType(), weapon.getWeaponSkin(), weapon.getWarlordsClassType(), weapon.getDamageMin() * upgradeMultiplier,
            weapon.getDamageMax() * upgradeMultiplier, weapon.getCritChance(), weapon.getCritMultiplier(),
            weapon.getWeaponBoost() * upgradeMultiplier, weapon.getWeaponBoostSpell(), weapon.getHealth() * upgradeMultiplierHealth,
            weapon.getEnergy() * upgradeMultiplierEnergy, weapon.getCooldown() * upgradeMultiplier, weapon.getSpeed() * upgradeMultiplier,
            weapon.getUpgradesCurrent(), weapon.getUpgradesMax(), weapon.getWeaponForgeType(), weapon.isSkillBoosted());
    }

    public static Weapon rerollWeapon(final Weapon weapon)
    {
        Weapon rerolledWeapon = generateRandomWeapon(weapon.getWeaponType(), weapon.getWarlordsClassType(), weapon.getWeaponForgeType());
        for (int i = 0; i < weapon.getUpgradesCurrent(); i++)
        {
            rerolledWeapon = upgradeWeapon(rerolledWeapon);
        }
        return rerolledWeapon;
    }
}
