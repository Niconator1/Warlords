package recreate.event;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import recreate.classes.WarlordsClass;
import recreate.classes.WarlordsClassType;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.util.ChatUtil;
import recreate.util.DamageUtil;
import recreate.util.ItemUtil;
import recreate.util.StatUtil;
import recreate.util.WeaponUtil;
import recreate.weapon.Weapon;

import java.util.Objects;

public class EventEntityHandler implements Listener
{
    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Player)
        {
            final Player player = (Player) event.getDamager();
            if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
            {
                final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
                final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
                final WarlordsClassType warlordsClassType = warlordsClass.getType();
                if (event.getEntity() instanceof LivingEntity)
                {
                    final LivingEntity livingEntity = (LivingEntity) event.getEntity();
                    if (event.getEntity() instanceof Player)
                    {
                        final Player victim = (Player) livingEntity;
                        if (Warlords.get().warlordsPlayerManager.doValidChecks(victim.getUniqueId()))
                        {
                            StatUtil.addEnergy(player.getUniqueId(), warlordsClassType.getEnergyPerHit());
                            if (player.getInventory().getHeldItemSlot() == 0)
                            {
                                final Weapon weapon = warlordsPlayer.getActiveWeapon();
                                if (Objects.nonNull(weapon))
                                {
                                    final double damage = DamageUtil.calculateMeleeDamage(player, victim, weapon);
                                    final double warlordsHealthPoints = StatUtil.getWarlordsClassHealth(victim.getUniqueId());
                                    if (damage > warlordsHealthPoints)
                                    {
                                        StatUtil.resetStats(victim.getUniqueId());
                                        ItemUtil.removeItems(victim.getUniqueId());
                                        victim.setHealth(0.0);
                                        WeaponUtil.handleWeaponDropPossibility(player, livingEntity);
                                        event.setCancelled(true);
                                        return;
                                    } else
                                    {
                                        victim.setHealth(StatUtil.warlordsHealthPointsToHealth(victim.getUniqueId(), warlordsHealthPoints - damage));
                                    }
                                }
                            }
                        }
                    } else if (!(event.getEntity() instanceof ArmorStand))
                    {
                        StatUtil.addEnergy(player.getUniqueId(), warlordsClassType.getEnergyPerHit());
                        if (player.getInventory().getHeldItemSlot() == 0)
                        {
                            final Weapon weapon = warlordsPlayer.getActiveWeapon();
                            if (Objects.nonNull(weapon))
                            {
                                final double damage = DamageUtil.calculateMeleeDamage(player, livingEntity, weapon);
                                final double warlordsHealthPoints = ((LivingEntity) event.getEntity()).getHealth() * 200.0;
                                if (damage > warlordsHealthPoints)
                                {
                                    livingEntity.setHealth(0.0);
                                    WeaponUtil.handleWeaponDropPossibility(player, livingEntity);
                                    event.setCancelled(true);
                                    return;
                                } else
                                {
                                    livingEntity.setHealth((warlordsHealthPoints - damage) / 200.0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageEvent(final EntityDamageEvent event)
    {
        if (event instanceof EntityDamageByEntityEvent)
        {
            final EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
            if (entityDamageByEntityEvent.getDamager() instanceof Player)
            {
                if (Warlords.get().warlordsPlayerManager.doValidChecks(entityDamageByEntityEvent.getDamager().getUniqueId()))
                {
                    event.setCancelled(true);
                    return;
                }
            } else if (entityDamageByEntityEvent.getDamager() instanceof Arrow)
            {
                final Arrow arrow = (Arrow) entityDamageByEntityEvent.getDamager();
                if (arrow.getShooter() instanceof Player)
                {
                    final Player player = (Player) arrow.getShooter();
                    if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
                    {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
        if (event.getEntity() instanceof Player)
        {
            final Player player = (Player) event.getEntity();
            if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
            {
                final String causeText = ChatUtil.mapDamageCauseToChatName(event.getCause());
                double damage =
                    DamageUtil.calculateDamage(player, StatUtil.healthToWarlordsHealthPoints(player.getUniqueId(), event.getDamage()));
                final double shieldValue = DamageUtil.getShield(player.getUniqueId());
                DamageUtil.damageShield(player.getUniqueId(), Math.min(damage, shieldValue));
                damage = Math.max(0, damage - shieldValue);
                ChatUtil.doDamageMessage(player, damage, damage <= 0.0, causeText);
                final double warlordsHealthPoints = StatUtil.getWarlordsClassHealth(player.getUniqueId());
                if (damage > warlordsHealthPoints)
                {
                    StatUtil.resetStats(player.getUniqueId());
                    ItemUtil.removeItems(player.getUniqueId());
                    player.setHealth(0.0);
                    event.setCancelled(true);
                    return;
                } else
                {
                    player.setHealth(StatUtil.warlordsHealthPointsToHealth(player.getUniqueId(), warlordsHealthPoints - damage));
                }
            }
        }
    }

    @EventHandler
    public void onPickupItem(final EntityPickupItemEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            final Player player = (Player) event.getEntity();
            if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
            {
                final ItemStack itemStack = event.getItem().getItemStack();
                for (int i = 9; i < 36; i++)
                {
                    final ItemStack itemStackInventory = player.getInventory().getItem(i);
                    if (itemStackInventory != null)
                    {
                        if (itemStackInventory.isSimilar(itemStack))
                        {
                            final int remainingItems = itemStackInventory.getMaxStackSize() - itemStackInventory.getAmount();
                            if (remainingItems > 0)
                            {
                                if (remainingItems > itemStack.getAmount())
                                {
                                    itemStackInventory.setAmount(itemStackInventory.getAmount() + itemStack.getAmount());
                                    event.getItem().remove();
                                    event.setCancelled(true);
                                    return;
                                } else
                                {
                                    itemStackInventory.setAmount(itemStackInventory.getAmount() + remainingItems);
                                    itemStack.setAmount(itemStack.getAmount() - remainingItems);
                                }
                            }
                        }
                    }
                }
                for (int i = 9; i < 36; i++)
                {
                    final ItemStack itemStackInventory = player.getInventory().getItem(i);
                    if (itemStackInventory == null)
                    {
                        player.getInventory().setItem(i, itemStack);
                        event.getItem().remove();
                        event.setCancelled(true);
                        return;
                    }
                }
                event.setCancelled(true);
                return;
            }
        }
    }
}
