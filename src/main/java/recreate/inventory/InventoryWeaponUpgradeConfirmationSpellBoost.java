package recreate.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.util.ChatUtil;
import recreate.util.InventoryUtil;
import recreate.util.ItemUtil;
import recreate.util.WeaponUtil;
import recreate.weapon.WarlordsWeaponType;
import recreate.weapon.Weapon;
import recreate.weapon.WeaponSpell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryWeaponUpgradeConfirmationSpellBoost extends WarlordsInventory
{
    public InventoryWeaponUpgradeConfirmationSpellBoost(final Player player)
    {
        super(generateInventory(player));
    }

    @Override
    public void handleSlotClick(final int slot,
        final ClickType click)
    {
        final Inventory inventory = this.getMinecraftInventory();
        if (Objects.nonNull(inventory.getItem(slot)))
        {
            final Player player = (Player) inventory.getHolder();
            if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
            {
                final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
                final Weapon weapon = warlordsPlayer.getActiveWeapon();
                if (Objects.nonNull(weapon))
                {
                    if (weapon.isSkillBoosted())
                    {
                        switch (slot)
                        {
                            case 49:
                                final WarlordsInventory craftingInventory = InventoryUtil.getCraftingInventory(player);
                                Warlords.get().listWarlordsInventory.add(craftingInventory);
                                player.openInventory(craftingInventory.getMinecraftInventory());
                                break;
                            default:
                                if (weapon.getWeaponType() == WarlordsWeaponType.LEGENDARY)
                                {
                                    final ArrayList<WeaponSpell> weaponSpells = WeaponSpell.getSpellsByClass(weapon.getWarlordsClassType());
                                    final int listIndex = slot - 22 + (weaponSpells.size() / 2);
                                    weapon.setSkill(weaponSpells.get(listIndex));
                                    ItemUtil.giveItems(player.getUniqueId());
                                    player.closeInventory();
                                } else
                                {
                                    ChatUtil.doNotCorrectWeaponTypeEquippedMessage(player, WarlordsWeaponType.LEGENDARY);
                                }
                                break;
                        }
                    } else
                    {
                        switch (slot)
                        {
                            case 11:
                                if (weapon.getWeaponType() == WarlordsWeaponType.LEGENDARY)
                                {
                                    if (warlordsPlayer.getVoidShards() >= 100)
                                    {
                                        warlordsPlayer.setVoidShards(warlordsPlayer.getVoidShards() - 100);
                                        weapon.setSkillBoosted(true);
                                    } else
                                    {
                                        ChatUtil.doNotEnoughVoidShardsMessage(player);
                                    }
                                } else
                                {
                                    ChatUtil.doNotCorrectWeaponTypeEquippedMessage(player, WarlordsWeaponType.LEGENDARY);
                                }
                                break;
                            case 15:
                                final WarlordsInventory craftingInventory = InventoryUtil.getCraftingInventory(player);
                                Warlords.get().listWarlordsInventory.add(craftingInventory);
                                player.openInventory(craftingInventory.getMinecraftInventory());
                                break;
                            default:
                                break;
                        }
                    }
                }
            } else
            {
                ChatUtil.doNoClassSelectedMessage(player);
            }
        }
    }

    private static Inventory generateInventory(final Player player)
    {
        final Inventory inventory = Bukkit.createInventory(player, 27, "Upgrade Weapon?");
        final ItemStack itemStackYes = new ItemStack(Material.GREEN_CONCRETE);
        final ItemMeta itemMetaYes = itemStackYes.getItemMeta();
        itemMetaYes.setDisplayName(ChatColor.GREEN + "Confirm");
        final List<String> loreYes = new ArrayList<>();
        final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
        if (Objects.nonNull(warlordsPlayer))
        {
            final Weapon weapon = warlordsPlayer.getActiveWeapon();
            if (Objects.nonNull(weapon))
            {
                if (weapon.getWeaponType() == WarlordsWeaponType.LEGENDARY)
                {
                    if (weapon.isSkillBoosted())
                    {
                        return getWeaponSkillBoostSelectionInventory(player);
                    } else
                    {
                        loreYes.add(ChatColor.RED + "Warning! " + ChatColor.GRAY + "This purchase");
                        loreYes.add(ChatColor.GRAY + "is required for each weapon");
                        loreYes.add(ChatColor.GRAY + "you want to customize. The specialization can not be");
                        loreYes.add(ChatColor.GRAY + "changed; you can only swap");
                        loreYes.add(ChatColor.GRAY + "between skill boosts that");
                        loreYes.add(ChatColor.GRAY + "are available for your");
                        loreYes.add(ChatColor.GRAY + "current weapon's");
                        loreYes.add(ChatColor.GRAY + "specialization.");
                        loreYes.add("");
                        loreYes.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "100 Void Shards");
                    }
                } else
                {
                    loreYes.add(ChatColor.GRAY + "You don't have a " + ChatColor.GOLD + "LEGENDARY " + ChatColor.GRAY
                        + "weapon equipped");
                }
            }
        }
        itemMetaYes.setLore(loreYes);
        itemStackYes.setItemMeta(itemMetaYes);
        final ItemStack itemStackNo = new ItemStack(Material.RED_CONCRETE);
        final ItemMeta itemMetaNo = itemStackNo.getItemMeta();
        itemMetaNo.setDisplayName(ChatColor.RED + "Deny");
        final List<String> loreNo = new ArrayList<>();
        loreNo.add(ChatColor.GRAY + "Go back to the previous");
        loreNo.add(ChatColor.GRAY + "menu.");
        itemMetaNo.setLore(loreNo);
        itemStackNo.setItemMeta(itemMetaNo);
        inventory.setItem(15, itemStackNo);
        inventory.setItem(11, itemStackYes);
        return inventory;
    }

    private static Inventory getWeaponSkillBoostSelectionInventory(final Player player)
    {
        final Inventory inventory = Bukkit.createInventory(player, 54, "Skill Boost");
        final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
        if (Objects.nonNull(warlordsPlayer))
        {
            final Weapon weapon = warlordsPlayer.getActiveWeapon();
            if (Objects.nonNull(weapon))
            {
                final ArrayList<WeaponSpell> weaponSpells = WeaponSpell.getSpellsByClass(weapon.getWarlordsClassType());
                for (int i = 0; i < weaponSpells.size(); i++)
                {
                    final WeaponSpell weaponSpell = weaponSpells.get(i);
                    ItemStack itemStack = ItemUtil.getSpellItemStack(weaponSpells.get(i).getSlot());
                    if (i == 0)
                    {
                        itemStack = WeaponUtil.generateWeaponItemStack(weapon, warlordsPlayer.getWarlordsClass());
                    }
                    final ItemMeta itemMeta = itemStack.getItemMeta();
                    final ChatColor color = weapon.getWeaponBoostSpell() == weaponSpells.get(i) ? ChatColor.GREEN : ChatColor.RED;
                    final ChatColor color2 = weapon.getWeaponBoostSpell() == weaponSpells.get(i) ? ChatColor.GREEN : ChatColor.GRAY;
                    itemMeta.setDisplayName(color + weaponSpell.getTitle() + " (" + weapon.getWarlordsClassType().getTitle() + ")");
                    final List<String> lore = new ArrayList<>();
                    lore.add(color2 + "Increase the damage you");
                    lore.add(color2 + "deal with " + weaponSpell.getTitle());
                    lore.add(String.format(color2 + "by " + ChatColor.RED + "%s%", String.format("#.##", weapon.getWeaponBoost() * 100.0)));
                    lore.add("");
                    if (weapon.getWeaponBoostSpell() == weaponSpell)
                    {
                        lore.add(color2 + "Currently selected!");
                    } else
                    {
                        lore.add(ChatColor.YELLOW + "Click to select!");
                    }
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                    final int slot = 22 - (weaponSpells.size() / 2) + i;
                    inventory.setItem(slot, itemStack);
                }
            }
        }
        inventory.setItem(49, InventoryUtil.getReturnArrow());
        return inventory;
    }
}
