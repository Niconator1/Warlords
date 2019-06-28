package recreate.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import recreate.classes.WarlordsClass;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.weapon.Weapon;

import java.util.Objects;

public class EventInventoryHandler implements Listener
{
    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent event)
    {
        if (event.getWhoClicked() instanceof Player)
        {
            final Player player = (Player) event.getWhoClicked();
            if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
            {
                for (final Integer slot : event.getRawSlots())
                {
                    if (slot >= event.getInventory().getSize())
                    {
                        if (slot > player.getInventory().getSize())
                        {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event)
    {
        final Inventory inventory = event.getInventory();
        Warlords.get().listWarlordsInventory.stream().forEach(warlordsInventory -> {
            if (warlordsInventory.getMinecraftInventory().equals(inventory))
            {
                Warlords.get().listWarlordsInventory.remove(warlordsInventory);
            }
        });
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event)
    {
        final Player player = (Player) event.getWhoClicked();

        final Inventory inventory = event.getInventory();
        Warlords.get().listWarlordsInventory.stream().forEach(warlordsInventory -> {
            if (warlordsInventory.getMinecraftInventory().equals(inventory))
            {
                warlordsInventory.handleSlotClick(event.getRawSlot(), event.getClick());
                event.setCancelled(true);
            }
        });
        if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
        {
            final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
            final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
            final Integer slotClicked = event.getRawSlot();
            if (slotClicked >= inventory.getSize())
            {
                if (event.getSlot() < 9)
                {
                    if (event.getSlot() == 0)
                    {
                        final Weapon weapon = warlordsPlayer.getActiveWeapon();
                        if (Objects.nonNull(weapon))
                        {
                            final ItemStack itemStack = warlordsClass.getWeaponSlot(weapon, event.getClick());
                            if (itemStack != null)
                            {
                                player.getInventory().setItem(0, itemStack);
                            }
                        }
                    }
                    event.setCancelled(true);
                    return;
                }
            }
            if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT
                || event.getClick() == ClickType.DOUBLE_CLICK)
            {
                event.setCancelled(true);
                return;
            }
            if (event.getAction() == InventoryAction.HOTBAR_SWAP
                || event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD)
            {
                event.setCancelled(true);
                return;
            }
        }
    }
}
