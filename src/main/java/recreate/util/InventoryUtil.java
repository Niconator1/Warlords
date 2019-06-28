package recreate.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import recreate.inventory.InventoryWeaponCraftingConfirmation;
import recreate.inventory.InventoryWeaponCraftingInventory;
import recreate.inventory.InventoryWeaponDestroyConfirmation;
import recreate.inventory.InventoryWeaponInventory;
import recreate.inventory.InventoryWeaponSkinInventory;
import recreate.inventory.InventoryWeaponSmithInventory;
import recreate.inventory.InventoryWeaponUpgradeConfirmation;
import recreate.inventory.InventoryWeaponUpgradeConfirmationReroll;
import recreate.inventory.InventoryWeaponUpgradeConfirmationSpellBoost;
import recreate.inventory.WarlordsInventory;
import recreate.weapon.WarlordsWeaponType;
import recreate.weapon.Weapon;

public class InventoryUtil
{
    public static WarlordsInventory getWeaponUpgradeConfirmation(final Player player)
    {

        return new InventoryWeaponUpgradeConfirmation(player);
    }

    public static WarlordsInventory getWeaponUpgradeSpellBoostConfirmation(final Player player)
    {

        return new InventoryWeaponUpgradeConfirmationSpellBoost(player);
    }

    public static WarlordsInventory getCraftingInventory(final Player player)
    {
        return new InventoryWeaponCraftingInventory(player);
    }

    public static WarlordsInventory getWeaponRerollConfirmation(final Player player)
    {
        return new InventoryWeaponUpgradeConfirmationReroll(player);
    }

    public static WarlordsInventory getSmithInventory(final Player player)
    {
        return new InventoryWeaponSmithInventory(player);
    }

    public static WarlordsInventory getWeaponDestroyConfirmation(final Player player,
        final Weapon weapon)
    {

        return new InventoryWeaponDestroyConfirmation(player, weapon);
    }

    public static WarlordsInventory getWeaponInventory(final Player player)
    {

        return new InventoryWeaponInventory(player);
    }

    public static WarlordsInventory getWeaponCraftConfirmation(final Player player,
        final WarlordsWeaponType warlordsWeaponType)
    {
        return new InventoryWeaponCraftingConfirmation(player, warlordsWeaponType);
    }

    public static WarlordsInventory getWeaponSkinInventory(final Player player,
        final int page)
    {
        return new InventoryWeaponSkinInventory(player, page);
    }

    public static ItemStack getReturnArrow()
    {
        final ItemStack itemStackReturnArrow = new ItemStack(Material.ARROW);
        final ItemMeta itemMetaReturnArrow = itemStackReturnArrow.getItemMeta();
        itemMetaReturnArrow.setDisplayName(ChatColor.GREEN + "Go Back");
        itemStackReturnArrow.setItemMeta(itemMetaReturnArrow);
        return itemStackReturnArrow;
    }
}
