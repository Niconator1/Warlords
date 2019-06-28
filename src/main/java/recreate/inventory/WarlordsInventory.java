package recreate.inventory;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

public abstract class WarlordsInventory
{
    private final Inventory inventory;

    public WarlordsInventory(final Inventory inventory)
    {
        this.inventory = inventory;
    }

    public abstract void handleSlotClick(final int slot,
        final ClickType click);

    public Inventory getMinecraftInventory()
    {
        return this.inventory;
    }
}
