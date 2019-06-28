package recreate.classes;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import recreate.util.WeaponUtil;
import recreate.weapon.Weapon;

import java.util.Objects;

public abstract class WarlordsClass
{
    protected final WarlordsClassType type;

    public WarlordsClass(final WarlordsClassType type)
    {
        this.type = type;
    }

    public WarlordsClassType getType()
    {
        return this.type;
    }

    public abstract ItemStack getItemForSlot(Weapon weapon,
        int slot);

    public abstract void castAbility(Player player,
        Weapon weapon,
        int slot);

    public ItemStack getWeaponSlot(final Weapon weapon,
        final ClickType clickType)
    {
        if (Objects.isNull(weapon))
        {
            return null;
        }
        switch (clickType)
        {
            case LEFT:
                return WeaponUtil.generateWeaponItemStack(weapon, this);
            case RIGHT:
                return getItemForSlot(weapon, 0);
            default:
                return null;
        }
    }
}
