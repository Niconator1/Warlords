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
import recreate.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryWeaponDestroyConfirmation extends WarlordsInventory
{
    private final Weapon weapon;

    public InventoryWeaponDestroyConfirmation(final Player player,
        final Weapon weapon)
    {
        super(generateInventory(player));
        this.weapon = weapon;
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
                switch (slot)
                {
                    case 11:
                        int result = 0;
                        final double rand = Math.random();
                        switch (this.weapon.getWeaponType())
                        {
                            case LEGENDARY:
                                result = (int) (30 + Math.round(10.0 * rand));
                                break;
                            case EPIC:
                                result = 3 + (int) (rand + 0.5);
                                break;
                            default:
                                break;
                        }
                        warlordsPlayer.setVoidShards(warlordsPlayer.getVoidShards() + result);
                        final String salvageText = ChatColor.GRAY + "You received " + ChatColor.LIGHT_PURPLE + result
                            + " Void Shards " + ChatColor.GRAY + "for salvaging your ";
                        final PacketPlayOutChat packet =
                            new PacketPlayOutChat(
                                WeaponUtil.getChatBaseComponentForWeapon(this.weapon,
                                    WeaponUtil.generateWeaponItemStack(this.weapon, warlordsPlayer.getWarlordsClass()), salvageText),
                                ChatMessageType.CHAT);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                        final ArrayList<Weapon> weaponList = warlordsPlayer.getWeaponInventory();
                        player.playSound(player.getLocation(), "block.anvil.use", 1f, 1f);
                        if (slot < warlordsPlayer.getWeaponSlot())
                        {
                            warlordsPlayer.setWeaponSlot(warlordsPlayer.getWeaponSlot() - 1);
                        }
                        weaponList.remove(this.weapon);
                        final WarlordsInventory weaponInventorySalvage = InventoryUtil.getWeaponInventory(player);
                        Warlords.get().listWarlordsInventory.add(weaponInventorySalvage);
                        player.openInventory(weaponInventorySalvage.getMinecraftInventory());
                        break;
                    case 15:
                        final WarlordsInventory weaponInventory = InventoryUtil.getWeaponInventory(player);
                        Warlords.get().listWarlordsInventory.add(weaponInventory);
                        player.openInventory(weaponInventory.getMinecraftInventory());
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
        final Inventory inventory = Bukkit.createInventory(player, 27, "Are you sure?");
        final ItemStack itemStackYes = new ItemStack(Material.GREEN_CONCRETE);
        final ItemMeta itemMetaItemStackYes = itemStackYes.getItemMeta();
        itemMetaItemStackYes.setDisplayName(ChatColor.GREEN + "Confirm");
        final List<String> loreYes = new ArrayList<>();
        loreYes.add(ChatColor.GRAY + "Destroy the weapon and");
        loreYes.add(ChatColor.GRAY + "claim the crafting");
        loreYes.add(ChatColor.GRAY + "materials. This is");
        loreYes.add(ChatColor.GRAY + "irreversible.");
        itemMetaItemStackYes.setLore(loreYes);
        itemStackYes.setItemMeta(itemMetaItemStackYes);
        final ItemStack itemStackNo = new ItemStack(Material.RED_CONCRETE);
        final ItemMeta itemMetaItemStackNo = itemStackNo.getItemMeta();
        itemMetaItemStackNo.setDisplayName(ChatColor.RED + "Deny");
        final List<String> loreNo = new ArrayList<>();
        loreNo.add(ChatColor.GRAY + "Go back to the previous");
        loreNo.add(ChatColor.GRAY + "menu.");
        itemMetaItemStackNo.setLore(loreNo);
        itemStackNo.setItemMeta(itemMetaItemStackNo);
        inventory.setItem(11, itemStackYes);
        inventory.setItem(15, itemStackNo);
        return inventory;
    }
}
