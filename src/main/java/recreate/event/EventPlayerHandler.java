package recreate.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import recreate.classes.WarlordsClass;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.util.ItemUtil;
import recreate.util.StatUtil;

import java.util.Objects;

public class EventPlayerHandler implements Listener
{
    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event)
    {
        final Player player = event.getEntity();
        if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
        {
            player.getWorld().playSound(player.getLocation(), "defeat", 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        StatUtil.giveStats(player.getUniqueId());
        ItemUtil.giveItems(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event)
    {
        final Player player = event.getPlayer();
        StatUtil.giveStats(player.getUniqueId());
        ItemUtil.giveItems(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event)
    {
        final Player player = event.getPlayer();
        StatUtil.resetStats(player.getUniqueId());
        ItemUtil.removeItems(player.getUniqueId());
        Warlords.get().warlordsSpellHandler.cancelSpellsForPlayer(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerDropItem(final PlayerDropItemEvent event)
    {
        final Player player = event.getPlayer();
        final ItemStack itemStack = event.getItemDrop().getItemStack();
        if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
        {
            final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
            final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
            for (int i = 0; i < 9; i++)
            {
                final ItemStack itemStackForSlot = warlordsClass.getItemForSlot(warlordsPlayer.getActiveWeapon(), i);
                if (Objects.nonNull(itemStackForSlot) &&
                    (itemStack.equals(itemStackForSlot) || itemStack.equals(warlordsClass.getWeaponSlot(warlordsPlayer.getActiveWeapon(),
                        ClickType.LEFT))))
                {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(final FoodLevelChangeEvent event)
    {
        final Entity entity = event.getEntity();
        if (entity instanceof Player)
        {
            final Player player = (Player) entity;
            if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event)
    {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            final Player player = event.getPlayer();
            if (event.getItem() != null)
            {
                if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
                {
                    final int slot = player.getInventory().getHeldItemSlot();
                    final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
                    final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
                    warlordsClass.castAbility(player, warlordsPlayer.getActiveWeapon(), slot);
                    event.setCancelled(true);
                    return;
                }
            }
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK)
        {
            final Player player = event.getPlayer();
            if (event.getItem() != null)
            {
                if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
                {
                    final int slot = player.getInventory().getHeldItemSlot();
                    if (slot == 0)
                    {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(final PlayerInteractAtEntityEvent event)
    {
        final Player player = event.getPlayer();
        if (player.getInventory().getItem(player.getInventory().getHeldItemSlot()) != null)
        {
            if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
            {
                player.getInventory().setContents(player.getInventory().getContents());
                if (event.getRightClicked() instanceof Player)
                {
                    final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
                    final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
                    warlordsClass.castAbility(player, warlordsPlayer.getActiveWeapon(), player.getInventory().getHeldItemSlot());
                }
                event.setCancelled(true);
                return;
            }
        }
    }
}
