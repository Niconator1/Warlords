package recreate.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import recreate.classes.WarlordsClassType;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.util.ChatUtil;
import recreate.util.InventoryUtil;
import recreate.weapon.WarlordsWeaponType;
import recreate.weapon.Weapon;
import recreate.weapon.WeaponSkin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class InventoryWeaponCraftingInventory extends WarlordsInventory
{
    public InventoryWeaponCraftingInventory(final Player player)
    {
        super(generateInventory(player));
    }

    @Override
    public void handleSlotClick(final int slot,
        final ClickType clickType)
    {
        final Inventory inventory = this.getMinecraftInventory();
        if (Objects.nonNull(inventory.getItem(slot)))
        {
            final Player player = (Player) inventory.getHolder();
            if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
            {
                switch (slot)
                {
                    case 11:
                        final WarlordsInventory craftConfirmationRareInventory =
                            InventoryUtil.getWeaponCraftConfirmation(player, WarlordsWeaponType.RARE);
                        Warlords.get().listWarlordsInventory.add(craftConfirmationRareInventory);
                        player.openInventory(craftConfirmationRareInventory.getMinecraftInventory());
                        break;
                    case 13:
                        final WarlordsInventory craftConfirmationEpicInventory =
                            InventoryUtil.getWeaponCraftConfirmation(player, WarlordsWeaponType.EPIC);
                        Warlords.get().listWarlordsInventory.add(craftConfirmationEpicInventory);
                        player.openInventory(craftConfirmationEpicInventory.getMinecraftInventory());
                        break;
                    case 15:
                        final WarlordsInventory craftConfirmationLegendaryInventory =
                            InventoryUtil.getWeaponCraftConfirmation(player, WarlordsWeaponType.LEGENDARY);
                        Warlords.get().listWarlordsInventory.add(craftConfirmationLegendaryInventory);
                        player.openInventory(craftConfirmationLegendaryInventory.getMinecraftInventory());
                        break;
                    case 29:
                        final WarlordsInventory upgradeConfirmationSpellBoostInventory = InventoryUtil.getWeaponUpgradeSpellBoostConfirmation(player);
                        Warlords.get().listWarlordsInventory.add(upgradeConfirmationSpellBoostInventory);
                        player.openInventory(upgradeConfirmationSpellBoostInventory.getMinecraftInventory());
                        break;
                    case 31:
                        final WarlordsInventory upgradeConfirmationEpicInventory = InventoryUtil.getWeaponUpgradeConfirmation(player);
                        Warlords.get().listWarlordsInventory.add(upgradeConfirmationEpicInventory);
                        player.openInventory(upgradeConfirmationEpicInventory.getMinecraftInventory());
                        break;
                    case 33:
                        final WarlordsInventory upgradeConfirmationLegendaryInventory = InventoryUtil.getWeaponUpgradeConfirmation(player);
                        Warlords.get().listWarlordsInventory.add(upgradeConfirmationLegendaryInventory);
                        player.openInventory(upgradeConfirmationLegendaryInventory.getMinecraftInventory());
                        break;
                    case 49:
                        final WarlordsInventory smithInventory = InventoryUtil.getSmithInventory(player);
                        Warlords.get().listWarlordsInventory.add(smithInventory);
                        player.openInventory(smithInventory.getMinecraftInventory());
                        break;
                    default:
                        break;
                }
            } else
            {
                ChatUtil.doNoClassSelectedMessage(player);
            }
        }
    }

    private static Inventory generateInventory(final Player player)
    {
        String costMagicForge = ChatColor.DARK_RED + "Error";
        String costVoidForge = ChatColor.DARK_RED + "Error";
        String weaponBonus = ChatColor.DARK_RED + "Error";
        final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
        if (Objects.nonNull(warlordsPlayer))
        {
            final Weapon weapon = warlordsPlayer.getActiveWeapon();
            if (Objects.nonNull(weapon))
            {
                if (weapon.getWeaponType() == WarlordsWeaponType.LEGENDARY)
                {
                    costVoidForge = "" + (int) (100 + (weapon.getUpgradesCurrent() * 0.5 + 0.5) * weapon.getUpgradesCurrent() * 50);
                } else if (weapon.getWeaponType() == WarlordsWeaponType.EPIC)
                {
                    costMagicForge = "" + (int) (25 + (weapon.getUpgradesCurrent() * 0.5 + 0.5) * weapon.getUpgradesCurrent() * 12.5);
                    costVoidForge = "100";
                } else
                {
                    costMagicForge = "25";
                    costVoidForge = "100";
                }
            }
            if (Objects.nonNull(warlordsPlayer.getWarlordsClass()) && warlordsPlayer.getWarlordsClass().getType() != WarlordsClassType.NONE)
            {
                weaponBonus = ChatColor.GREEN + "" + warlordsPlayer.getWarlordsClass().getType();
            }
        }
        final Random random = new Random();
        final Inventory inventory = Bukkit.createInventory(player, 54, "Crafting & Forging");
        final ItemStack voidForge = new ItemStack(Material.ENCHANTING_TABLE);
        final ItemMeta itemMetaVoidForge = voidForge.getItemMeta();
        itemMetaVoidForge.setDisplayName(ChatColor.GREEN + "Void Forging");
        final List<String> loreVoidForge = new ArrayList<>();
        loreVoidForge.add(ChatColor.GRAY + "Upgrade your currently equipped");
        loreVoidForge.add(ChatColor.GOLD + "legendary " + ChatColor.GRAY + "weapon using " + ChatColor.LIGHT_PURPLE
            + "Void Shards" + ChatColor.GRAY + ",");
        loreVoidForge.add(ChatColor.GRAY + "increasing some of its stats");
        loreVoidForge.add(ChatColor.GRAY + "permanently.");
        loreVoidForge.add(ChatColor.GRAY + "");
        loreVoidForge.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + costVoidForge + " Void Shards");
        itemMetaVoidForge.setLore(loreVoidForge);
        itemMetaVoidForge.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
        voidForge.setItemMeta(itemMetaVoidForge);
        inventory.setItem(33, voidForge);
        final ItemStack magicForge = new ItemStack(Material.BREWING_STAND);
        final ItemMeta itemMetaMagicForge = magicForge.getItemMeta();
        itemMetaMagicForge.setDisplayName(ChatColor.GREEN + "Magic Forging");
        final List<String> loreMagicForge = new ArrayList<>();
        loreMagicForge.add(ChatColor.GRAY + "Upgrade your currently equipped");
        loreMagicForge.add(ChatColor.DARK_PURPLE + "epic " + ChatColor.GRAY + "weapon using " + ChatColor.LIGHT_PURPLE
            + "Void Shards" + ChatColor.GRAY + ",");
        loreMagicForge.add(ChatColor.GRAY + "increasing some of its stats");
        loreMagicForge.add(ChatColor.GRAY + "permanently.");
        loreMagicForge.add(ChatColor.GRAY + "");
        loreMagicForge.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + costMagicForge + " Void Shards");
        itemMetaMagicForge.setLore(loreMagicForge);
        itemMetaMagicForge.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
        magicForge.setItemMeta(itemMetaMagicForge);
        inventory.setItem(31, magicForge);
        final ItemStack skillBoost = new ItemStack(Material.BOOKSHELF);
        final ItemMeta itemMetaSkillBoost = skillBoost.getItemMeta();
        itemMetaSkillBoost.setDisplayName(ChatColor.GREEN + "Weapon Skill Boost");
        final List<String> loreSkillBoost = new ArrayList<>();
        loreSkillBoost.add(ChatColor.GRAY + "Change the skill boost of your currently");
        loreSkillBoost.add(ChatColor.GRAY + "equipped " + ChatColor.GOLD + "Legendary" + ChatColor.GRAY
            + " weapon! Once purchased,");
        loreSkillBoost.add(ChatColor.GRAY + "this feature will allow you to swap freely");
        loreSkillBoost.add(ChatColor.GRAY + "between the different skill boosts available");
        loreSkillBoost.add(ChatColor.GRAY + "for this weapon, with no additional cost.");
        loreSkillBoost.add(ChatColor.GRAY + "");
        loreSkillBoost.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "100 Void Shards");
        itemMetaSkillBoost.setLore(loreSkillBoost);
        itemMetaSkillBoost.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
        skillBoost.setItemMeta(itemMetaSkillBoost);
        inventory.setItem(29, skillBoost);
        final WeaponSkin weaponSkinLegendary =
            WeaponSkin.getSkinsByType(WarlordsWeaponType.LEGENDARY)
                .get(random.nextInt(WeaponSkin.getSkinsByType(WarlordsWeaponType.LEGENDARY).size()));
        final ItemStack craftLegendary = new ItemStack(weaponSkinLegendary.getMaterial());
        final ItemMeta itemMetaCraftLegendary = craftLegendary.getItemMeta();
        itemMetaCraftLegendary.setDisplayName(ChatColor.GREEN + "Craft a Legendary Weapon");
        final List<String> loreCraftLegendary = new ArrayList<>();
        loreCraftLegendary.add(ChatColor.GRAY + "Creates a " + ChatColor.GOLD + "Legendary " + ChatColor.GRAY + "weapon with a");
        loreCraftLegendary.add(ChatColor.GRAY + "skill bonus for " + weaponBonus + ChatColor.GRAY + ".");
        loreCraftLegendary.add(ChatColor.GRAY + "Crafted Legendary Weapons can be");
        loreCraftLegendary.add(ChatColor.GRAY + "upgraded " + ChatColor.GREEN + "4 " + ChatColor.GRAY + "times using the Void");
        loreCraftLegendary.add(ChatColor.GRAY + "Forging feature.");
        loreCraftLegendary.add(ChatColor.GRAY + "");
        loreCraftLegendary.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "100 Void Shards");
        itemMetaCraftLegendary.setLore(loreCraftLegendary);
        itemMetaCraftLegendary.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
        craftLegendary.setItemMeta(itemMetaCraftLegendary);
        inventory.setItem(15, craftLegendary);
        final WeaponSkin weaponSkinEpic =
            WeaponSkin.getSkinsByType(WarlordsWeaponType.EPIC).get(random.nextInt(WeaponSkin.getSkinsByType(WarlordsWeaponType.EPIC).size()));
        final ItemStack craftEpic = new ItemStack(weaponSkinEpic.getMaterial());
        final ItemMeta itemMetaCraftEpic = craftEpic.getItemMeta();
        itemMetaCraftEpic.setDisplayName(ChatColor.GREEN + "Craft an Epic Weapon");
        final List<String> loreCraftEpic = new ArrayList<>();
        loreCraftEpic.add(ChatColor.GRAY + "Creates a " + ChatColor.DARK_PURPLE + "Epic " + ChatColor.GRAY + "weapon with a");
        loreCraftEpic.add(ChatColor.GRAY + "skill bonus for " + weaponBonus + ChatColor.GRAY + ".");
        loreCraftEpic.add(ChatColor.GRAY + "Crafted Epic Weapons can be");
        loreCraftEpic.add(ChatColor.GRAY + "upgraded " + ChatColor.GREEN + "2 " + ChatColor.GRAY + "times using the Magic");
        loreCraftEpic.add(ChatColor.GRAY + "Forging feature.");
        loreCraftEpic.add(ChatColor.GRAY + "");
        loreCraftEpic.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "25 Void Shards");
        itemMetaCraftEpic.setLore(loreCraftEpic);
        itemMetaCraftEpic.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
        craftEpic.setItemMeta(itemMetaCraftEpic);
        inventory.setItem(13, craftEpic);
        final WeaponSkin weaponSkinRare =
            WeaponSkin.getSkinsByType(WarlordsWeaponType.RARE).get(random.nextInt(WeaponSkin.getSkinsByType(WarlordsWeaponType.RARE).size()));
        final ItemStack craftRare = new ItemStack(weaponSkinRare.getMaterial());
        final ItemMeta itemMetaCraftRare = craftRare.getItemMeta();
        itemMetaCraftRare.setDisplayName(ChatColor.GREEN + "Craft a Rare Weapon");
        final List<String> loreCraftRare = new ArrayList<>();
        loreCraftRare.add(ChatColor.GRAY + "Creates a " + ChatColor.BLUE + "Rare " + ChatColor.GRAY + "weapon with a");
        loreCraftRare.add(ChatColor.GRAY + "skill bonus for " + weaponBonus + ChatColor.GRAY + ".");
        loreCraftRare.add(ChatColor.GRAY + "");
        loreCraftRare.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "5 Void Shards");
        itemMetaCraftRare.setLore(loreCraftRare);
        itemMetaCraftRare.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
        craftRare.setItemMeta(itemMetaCraftRare);
        inventory.setItem(11, craftRare);
        final ItemStack isb = new ItemStack(Material.ARROW, 1);
        final ItemMeta imb = isb.getItemMeta();
        imb.setDisplayName(ChatColor.GREEN + "Go Back");
        isb.setItemMeta(imb);
        inventory.setItem(49, isb);
        return inventory;
    }
}
