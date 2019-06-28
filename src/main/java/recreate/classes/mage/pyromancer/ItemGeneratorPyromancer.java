package recreate.classes.mage.pyromancer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemGeneratorPyromancer
{

    public static ItemStack getRedRune(final double cooldownFlameBurst,
        final int energyFlameBurst,
        final double critChanceFlameBurst,
        final double critMultiplierFlameBurst,
        final double damageMinFlameBurst,
        final double damageMaxFlameBurst)
    {
        final ItemStack itemStack = new ItemStack(Material.RED_DYE);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "Flameburst");
        final List<String> lore = new ArrayList<>();
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + "%s seconds", cooldownFlameBurst));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + "%s", energyFlameBurst));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + "%s%",
            String.format("#", critChanceFlameBurst * 100.0)));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + "%s%",
            String.format("#", critMultiplierFlameBurst * 100.0)));
        lore.add("");
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Launch a flame burst that will"));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "explode for " + ChatColor.RED + "%s "
            + ChatColor.GRAY + "-" + ChatColor.RED + " %s", damageMinFlameBurst, damageMaxFlameBurst));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "damage. The critical chance"));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "increases by " + ChatColor.RED + "1.0% " + ChatColor.GRAY
            + "for each"));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "travelled block. Up to 100%."));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getPurpleRune(final double cooldownTimeWarp,
        final int energyTimeWarp,
        final double delayTimeWarp,
        final double restoreHealthTimeWarp)
    {
        final ItemStack itemStack = new ItemStack(Material.GLOWSTONE_DUST);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "Time Warp");
        final List<String> lore = new ArrayList<>();
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + "%s seconds", cooldownTimeWarp));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + "%s", energyTimeWarp));
        lore.add("");
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Activate to place a time rune on the"));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "ground. After " + ChatColor.GOLD + "%s "
            + ChatColor.GRAY + "seconds, you will warp", delayTimeWarp));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "back to that location and restore " + ChatColor.GREEN
            + "%s%", String.format("#", restoreHealthTimeWarp * 100.0)));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "of your health."));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getBlueRune(final double cooldownArcaneShield,
        final int energyArcaneShield,
        final double valueArcaneShield,
        final double restoreArcaneShield,
        final double durationShield)
    {
        final ItemStack itemStack = new ItemStack(Material.LIME_DYE);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "Arcane Shield");
        final List<String> lore = new ArrayList<>();
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + "%s seconds", cooldownArcaneShield));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + "%s", energyArcaneShield));
        lore.add("");
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Surround yourself with arcane energy,"));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "creating a shield that will absorb up to"));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.YELLOW + "%s " + ChatColor.GRAY + "(" + ChatColor.YELLOW
            + "%s% " + ChatColor.GRAY + "of your maximum", valueArcaneShield, String.format("#", restoreArcaneShield * 100.0)));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "health) incoming damage. Lasts " + ChatColor.GOLD + "%s", durationShield));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "seconds."));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getYellowRune(final double cooldownInferno,
        final double critChanceInferno,
        final double critMultiplierInferno,
        final double durationInferno)
    {
        final ItemStack itemStack = new ItemStack(Material.ORANGE_DYE);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "Inferno");
        final List<String> lore = new ArrayList<>();
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + "%s seconds", cooldownInferno));
        lore.add("");
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Combust into a molten inferno,"));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "increasing your Crit Chance by " + ChatColor.RED
            + "%s%", String.format("#", critChanceInferno * 100.0)));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "and your Crit Multiplier by " + ChatColor.RED
            + "%s%", String.format("#", critMultiplierInferno * 100.0)));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "for " + ChatColor.GOLD + "%s " + ChatColor.GRAY
            + "seconds.", durationInferno));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static List<String> getMain(final int energyFireball,
        final double critChanceFireball,
        final double critMultiplierFireball,
        final double damageMinFireball,
        final double damageMaxFireball)
    {
        final List<String> lore = new ArrayList<>();
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Energy Cost: " + ChatColor.YELLOW + "%s", energyFireball));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Chance: " + ChatColor.RED + "%s%",
            String.format("#", critChanceFireball * 100.0)));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Crit Multiplier: " + ChatColor.RED + "%s%",
            String.format("#", critMultiplierFireball * 100.0)));
        lore.add("");
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "Shoots a fireball that will"));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "explode for " + ChatColor.RED + "%s "
            + ChatColor.GRAY + "-" + ChatColor.RED + " %s", damageMinFireball, damageMaxFireball));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "damage. A direct hit will cause"));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.GRAY + "the enemy to take an additional"));
        lore.add(String.format(ChatColor.RESET + "" + ChatColor.RED + "15%" + ChatColor.GRAY + " extra damage. "));
        return lore;
    }
}
