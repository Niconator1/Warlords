package recreate.inventory;

import net.minecraft.server.v1_14_R1.ChatMessageType;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.util.ChatUtil;
import recreate.util.InventoryUtil;
import recreate.util.ItemUtil;
import recreate.util.WeaponUtil;
import recreate.weapon.WarlordsWeaponType;
import recreate.weapon.Weapon;

import java.util.ArrayList;
import java.util.Objects;

public class InventoryWeaponInventory extends WarlordsInventory
{
    public InventoryWeaponInventory(final Player player)
    {
        super(generateInventory(player));
    }

    @Override
    public void handleSlotClick(final int slot,
        final ClickType clickType)
    {
        Inventory inventory = this.getMinecraftInventory();
        if (Objects.nonNull(inventory.getItem(slot)))
        {
            final Player player = (Player) inventory.getHolder();
            if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
            {
                final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
                final ArrayList<Weapon> weaponList = warlordsPlayer.getWeaponInventory();
                switch (slot)
                {
                    case 49:
                        final WarlordsInventory smithInventory = InventoryUtil.getSmithInventory(player);
                        Warlords.get().listWarlordsInventory.add(smithInventory);
                        player.openInventory(smithInventory.getMinecraftInventory());
                        break;
                    default:
                        if (clickType == ClickType.RIGHT)
                        {
                            if (slot < weaponList.size() && slot != warlordsPlayer.getWeaponSlot())
                            {
                                final Weapon weapon = weaponList.get(slot);
                                if (weapon.getWeaponType() == WarlordsWeaponType.LEGENDARY || weapon.getWeaponType() == WarlordsWeaponType.EPIC)
                                {
                                    final WarlordsInventory destroyInventory = InventoryUtil.getWeaponDestroyConfirmation(player, weapon);
                                    Warlords.get().listWarlordsInventory.add(destroyInventory);
                                    player.openInventory(destroyInventory.getMinecraftInventory());
                                } else
                                {
                                    final double rand = Math.random();
                                    String salvageText = ChatColor.GRAY + "You salvaged ";
                                    if (rand < 0.05 && weapon.getWeaponType() == WarlordsWeaponType.RARE)
                                    {
                                        salvageText = ChatColor.GRAY + "You received " + ChatColor.LIGHT_PURPLE
                                            + "1 Void Shard " + ChatColor.GRAY + "for salvaging your ";
                                        warlordsPlayer.setVoidShards(warlordsPlayer.getVoidShards() + 1);
                                    }
                                    final PacketPlayOutChat packet =
                                        new PacketPlayOutChat(
                                            WeaponUtil.getChatBaseComponentForWeapon(weapon,
                                                WeaponUtil.generateWeaponItemStack(weapon, warlordsPlayer.getWarlordsClass()), salvageText),
                                            ChatMessageType.CHAT);
                                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                                    player.playSound(player.getLocation(), "block.anvil.use", 1f, 1f);
                                    if (slot < warlordsPlayer.getWeaponSlot())
                                    {
                                        warlordsPlayer.setWeaponSlot(warlordsPlayer.getWeaponSlot() - 1);
                                    }
                                    weaponList.remove(slot);
                                    inventory = generateInventory(player);
                                    player.openInventory(inventory);
                                }
                            }
                        } else
                        {
                            if (Objects.nonNull(inventory.getItem(slot)) && slot < weaponList.size())
                            {
                                warlordsPlayer.setWeaponSlot(slot);
                                ItemUtil.giveItems(player.getUniqueId());
                            }
                        }
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
        final Inventory inventory = Bukkit.createInventory(player, 54, "Weapons");
        if (Objects.nonNull(warlordsPlayer))
        {
            final ArrayList<Weapon> weaponList = warlordsPlayer.getWeaponInventory();
            weaponList.stream().limit(45).sequential()
                .forEach(weapon -> inventory.addItem(WeaponUtil.generateWeaponItemStack(weapon, warlordsPlayer.getWarlordsClass())));
        }
        inventory.setItem(49, InventoryUtil.getReturnArrow());
        return inventory;
    }
}
