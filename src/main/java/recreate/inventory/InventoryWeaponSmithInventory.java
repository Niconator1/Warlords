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
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.util.ChatUtil;
import recreate.util.InventoryUtil;
import recreate.weapon.WarlordsWeaponType;
import recreate.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryWeaponSmithInventory extends WarlordsInventory
{
    public InventoryWeaponSmithInventory(final Player player)
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
                final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
                switch (slot)
                {
                    case 11:
                        final WarlordsInventory craftingInventory = InventoryUtil.getCraftingInventory(player);
                        Warlords.get().listWarlordsInventory.add(craftingInventory);
                        player.openInventory(craftingInventory.getMinecraftInventory());
                        break;
                    case 15:
                        final WarlordsInventory weaponInventory = InventoryUtil.getWeaponInventory(player);
                        Warlords.get().listWarlordsInventory.add(weaponInventory);
                        player.openInventory(weaponInventory.getMinecraftInventory());
                        break;
                    case 29:
                        final Weapon weapon = warlordsPlayer.getActiveWeapon();
                        if (Objects.nonNull(weapon))
                        {
                            if (weapon.getWeaponType().ordinal() >= WarlordsWeaponType.EPIC.ordinal())
                            {
                                if (warlordsPlayer.getVoidShards() >= 1.0 / weapon.getWeaponType().ordinal() * 10)
                                {
                                    final WarlordsInventory rerollInventory = InventoryUtil.getWeaponRerollConfirmation(player);
                                    Warlords.get().listWarlordsInventory.add(rerollInventory);
                                    player.openInventory(rerollInventory.getMinecraftInventory());
                                } else
                                {
                                    ChatUtil.doNotEnoughVoidShardsMessage(player);
                                }
                            } else
                            {
                                ChatUtil.doNotCorrectWeaponTypeEquippedMessage(player, WarlordsWeaponType.EPIC);
                            }
                        }
                        break;
                    case 33:
                        final WarlordsInventory skinInventory = InventoryUtil.getWeaponSkinInventory(player, 1);
                        Warlords.get().listWarlordsInventory.add(skinInventory);
                        player.openInventory(skinInventory.getMinecraftInventory());
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
        final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
        String countVoidShards = ChatColor.DARK_RED + "ERROR";
        if (Objects.nonNull(warlordsPlayer))
        {
            countVoidShards = "" + warlordsPlayer.getVoidShards();
        }
        final Inventory inventorySmith = Bukkit.createInventory(player, 45, "The Weaponsmith");
        final ItemStack forge = new ItemStack(Material.WOODEN_PICKAXE);
        final ItemMeta itemMetaForge = forge.getItemMeta();
        itemMetaForge.setDisplayName(ChatColor.GREEN + "Weapon Crafting & Forging");
        final List<String> loreForge = new ArrayList<>();
        loreForge.add(ChatColor.GRAY + "Use the " + ChatColor.LIGHT_PURPLE + "Void Shards");
        loreForge.add(ChatColor.GRAY + "you gathered to craft weapons");
        loreForge.add(ChatColor.GRAY + "for your currently selected class");
        loreForge.add(ChatColor.GRAY + "specialization or to upgrade your equipped weapon.");
        itemMetaForge.setLore(loreForge);
        itemMetaForge.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
        forge.setItemMeta(itemMetaForge);
        inventorySmith.setItem(11, forge);
        final ItemStack reroll = new ItemStack(Material.CRAFTING_TABLE);
        final ItemMeta itemMetaReroll = reroll.getItemMeta();
        itemMetaReroll.setDisplayName(ChatColor.GREEN + "Weapon Stats Reroll");
        final List<String> loreReroll = new ArrayList<>();
        loreReroll.add(ChatColor.GRAY + "Click here to reroll the");
        loreReroll.add(ChatColor.GRAY + "numerical stats of your");
        loreReroll.add(ChatColor.GRAY + "currently " + ChatColor.GREEN + "EQUIPPED");
        loreReroll.add(ChatColor.GRAY + "weapon for a chance at");
        loreReroll.add(ChatColor.GRAY + "making it better than it");
        loreReroll.add(ChatColor.GRAY + "previously was.");
        loreReroll.add(ChatColor.GRAY + "");
        loreReroll.add(ChatColor.RED + "WARNING: " + ChatColor.GRAY + "Weapon stat");
        loreReroll.add(ChatColor.GRAY + "rerolls are totally random.");
        loreReroll.add(ChatColor.GRAY + "This means you can also end");
        loreReroll.add(ChatColor.GRAY + "up with worse stats!");
        itemMetaReroll.setLore(loreReroll);
        itemMetaReroll.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
        reroll.setItemMeta(itemMetaReroll);
        inventorySmith.setItem(29, reroll);
        final ItemStack inventory = new ItemStack(Material.GOLDEN_HOE, 1);
        final ItemMeta itemMetaInventory = inventory.getItemMeta();
        itemMetaInventory.setDisplayName(ChatColor.GREEN + "Weapons Inventory");
        final List<String> loreInventory = new ArrayList<>();
        loreInventory.add(ChatColor.GRAY + "Click here to equip your");
        loreInventory.add(ChatColor.GRAY + "repaired weapons.");
        loreInventory.add(ChatColor.GRAY + "");
        loreInventory.add(ChatColor.AQUA + "Weapons: " + ChatColor.GOLD + countVoidShards);
        itemMetaInventory.setLore(loreInventory);
        itemMetaInventory.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
        inventory.setItemMeta(itemMetaInventory);
        inventorySmith.setItem(15, inventory);
        final ItemStack skin = new ItemStack(Material.PAINTING, 1);
        final ItemMeta itemMetaSkin = skin.getItemMeta();
        itemMetaSkin.setDisplayName(ChatColor.GREEN + "Weapon WeaponSkin Selector");
        final List<String> loreSkin = new ArrayList<>();
        loreSkin.add(ChatColor.GRAY + "Change the cosmetic");
        loreSkin.add(ChatColor.GRAY + "appearance of your weapon");
        loreSkin.add(ChatColor.GRAY + "to better suit your tastes.");
        loreSkin.add(ChatColor.GRAY + "");
        loreSkin.add(ChatColor.GRAY + "This costs " + ChatColor.LIGHT_PURPLE + "Void Shards");
        itemMetaSkin.setLore(loreSkin);
        itemMetaSkin.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
        skin.setItemMeta(itemMetaSkin);
        inventorySmith.setItem(33, skin);
        final ItemStack resourcen = new ItemStack(Material.EMERALD, 1);
        final ItemMeta itemMetaResources = resourcen.getItemMeta();
        itemMetaResources.setDisplayName(ChatColor.GREEN + "Resources");
        final List<String> loreResources = new ArrayList<>();
        loreResources.add(ChatColor.LIGHT_PURPLE + "Void Shards: " + ChatColor.WHITE + countVoidShards);
        itemMetaResources.setLore(loreResources);
        itemMetaResources.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
        resourcen.setItemMeta(itemMetaResources);
        inventorySmith.setItem(22, resourcen);
        return inventorySmith;
    }
}
