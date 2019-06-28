package recreate.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import recreate.classes.WarlordsClass;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;

import java.util.Objects;
import java.util.UUID;

public class ItemUtil
{

    public static void removeItems(final UUID uuid)
    {
        if (Warlords.get().warlordsPlayerManager.doValidChecks(uuid))
        {
            final Player player = Bukkit.getPlayer(uuid);
            final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(uuid);
            final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
            for (int i = 0; i < 9; i++)
            {
                if (Objects.nonNull(warlordsClass.getItemForSlot(warlordsPlayer.getActiveWeapon(), i)))
                {
                    player.getInventory().setItem(i, null);
                }
            }
        }
    }

    public static void giveItems(final UUID uuid)
    {
        if (Warlords.get().warlordsPlayerManager.doValidChecks(uuid))
        {
            final Player player = Bukkit.getPlayer(uuid);
            final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(uuid);
            final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
            for (int i = 0; i < 9; i++)
            {
                if (Objects.nonNull(warlordsClass.getItemForSlot(warlordsPlayer.getActiveWeapon(), i)))
                {
                    player.getInventory().setItem(i, warlordsClass.getItemForSlot(warlordsPlayer.getActiveWeapon(), i));
                }
            }
        }
    }

    public static ItemStack getSpellItemStack(final int ability)
    {
        switch (ability)
        {
            case 1:
                return new ItemStack(Material.RED_DYE);
            case 2:
                return new ItemStack(Material.GLOWSTONE_DUST);
            case 3:
                return new ItemStack(Material.LIME_DYE);
            case 4:
                return new ItemStack(Material.ORANGE_DYE);
            default:
                return new ItemStack(Material.BARRIER);
        }
    }
}
