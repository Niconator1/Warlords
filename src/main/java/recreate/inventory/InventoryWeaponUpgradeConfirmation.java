package recreate.inventory;

import net.minecraft.server.v1_14_R1.ChatMessageType;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.util.ChatUtil;
import recreate.util.InventoryUtil;
import recreate.util.WeaponUtil;
import recreate.weapon.WarlordsWeaponType;
import recreate.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryWeaponUpgradeConfirmation extends WarlordsInventory
{

    public InventoryWeaponUpgradeConfirmation(final Player player)
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
                    if (weapon.getWeaponType().ordinal() >= WarlordsWeaponType.EPIC.ordinal())
                    {
                        switch (slot)
                        {
                            case 11:
                                int cost = 0;
                                switch (weapon.getWeaponType())
                                {
                                    case LEGENDARY:
                                        cost = (int) (100 + (weapon.getUpgradesCurrent() * 0.5 + 0.5) * weapon.getUpgradesCurrent() * 50);
                                        break;
                                    case EPIC:
                                        cost = (int) (25 + (weapon.getUpgradesCurrent() * 0.5 + 0.5) * weapon.getUpgradesCurrent() * 12.5);
                                        break;
                                    default:
                                        break;
                                }
                                if (warlordsPlayer.getVoidShards() >= cost)
                                {
                                    if (weapon.getUpgradesCurrent() < weapon.getUpgradesMax())
                                    {
                                        warlordsPlayer.setVoidShards(warlordsPlayer.getVoidShards() - cost);
                                        final Weapon upgradedWeapon = WeaponUtil.upgradeWeapon(weapon);
                                        final ItemStack itemStack = WeaponUtil.generateChangeItemStack(weapon, upgradedWeapon);
                                        final String resultText = ChatColor.GRAY + "Result: ";
                                        final PacketPlayOutChat packet =
                                            new PacketPlayOutChat(WeaponUtil.getChatBaseComponentForWeapon(weapon, itemStack, resultText),
                                                ChatMessageType.CHAT);
                                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                                        final ArrayList<Weapon> weaponList = warlordsPlayer.getWeaponInventory();
                                        weaponList.remove(weapon);
                                        weaponList.add(upgradedWeapon);
                                    } else
                                    {
                                        ChatUtil.doWeaponAlreadyMaxedMessage(player);
                                    }
                                } else
                                {
                                    ChatUtil.doNotEnoughVoidShardsMessage(player);
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
                    } else
                    {
                        ChatUtil.doTooBadWeaponMessage(player, WarlordsWeaponType.EPIC);
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
                switch (weapon.getWeaponType())
                {
                    case LEGENDARY:
                        if (weapon.getUpgradesCurrent() < weapon.getUpgradesMax())
                        {
                            final Weapon neu = WeaponUtil.upgradeWeapon(weapon);
                            final ItemStack itemStack = WeaponUtil.generateChangeItemStack(weapon, neu);
                            itemStack.getItemMeta().getLore().forEach(loreYes::add);
                        } else
                        {
                            loreYes.add(ChatColor.RED + "You can't upgrade this weapon any more!");
                        }
                        break;
                    case EPIC:
                        if (weapon.getUpgradesCurrent() < weapon.getUpgradesMax())
                        {
                            final Weapon neu = WeaponUtil.upgradeWeapon(weapon);
                            final ItemStack itemStack = WeaponUtil.generateChangeItemStack(weapon, neu);
                            itemStack.getItemMeta().getLore().forEach(loreYes::add);
                        } else
                        {
                            loreYes.add(ChatColor.RED + "You can't upgrade this weapon any more!");
                        }
                        break;
                    default:
                        break;
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
}
