package recreate.classes.mage.pyromancer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import recreate.data.TimeSpell;
import recreate.util.DamageUtil;
import recreate.util.ItemUtil;
import recreate.util.StatUtil;
import recreate.util.WeaponUtil;

import java.util.UUID;

public class Fireball extends TimeSpell
{
    private final double damageMin;
    private final double damageMax;
    private final double critChance;
    private final double critMultiplier;
    private Vector vector;
    private Location startLocation;
    private Location location;

    public Fireball(final UUID uuid,
        final double damageMin,
        final double damageMax,
        final double critChance,
        final double critMultiplier)
    {
        super(uuid);
        this.damageMin = damageMin;
        this.damageMax = damageMax;
        this.critChance = critChance;
        this.critMultiplier = critMultiplier;
    }

    @Override
    public void cancel()
    {

    }

    @Override
    public void doStart()
    {
        final Player player = Bukkit.getPlayer(getOwner());
        final double pitch = ((player.getLocation().getPitch() + 90.0) * Math.PI) / 180.0;
        final double yaw = ((player.getLocation().getYaw() + 90.0) * Math.PI) / 180.0;
        final double x = Math.sin(pitch) * Math.cos(yaw);
        final double y = Math.sin(pitch) * Math.sin(yaw);
        final double z = Math.cos(pitch);
        this.vector = new Vector(x, z, y).multiply(1.25);
        this.startLocation = player.getEyeLocation().clone();
        this.location = player.getEyeLocation().clone();
        player.getWorld().playSound(player.getLocation(), "mage.fireball.activation", 1f, 1f);
    }

    @Override
    public void doTick()
    {
        this.location.getWorld().spawnParticle(Particle.SMOKE_NORMAL, this.location, 10, 0.2f, 0.2f, 0.2f, 0f);
        this.location.getWorld().spawnParticle(Particle.FLAME, this.location, 5, 0.2f, 0.2f, 0.2f, 0f);
        for (final LivingEntity livingEntity : this.location.getWorld().getLivingEntities())
        {
            if (!(livingEntity instanceof ArmorStand))
            {
                if (livingEntity.getUniqueId().compareTo(getOwner()) != 0)
                {
                    if (this.location.distance(livingEntity.getLocation().add(0, 1, 0)) <= 1.25)
                    {
                        doHit(true);
                    }
                }
            }
        }
        if (this.location.getBlock().isEmpty())
        {
            this.location.add(this.vector);
        } else
        {
            this.setFinished();
        }
    }

    private void doHit(final boolean isDirectHit)
    {
        this.location.getWorld().playSound(this.location, "mage.fireball.impact", 1f, 1f);
        this.location.getWorld().spawnParticle(Particle.LAVA, this.location.add(0, 1.0f, 0), 8, 0f, 0f, 0f, 0f);
        this.location.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, this.location.add(0, 1.0f, 0), 1, 0f, 0f, 0f, 0f);
        for (final LivingEntity livingEntity : this.location.getWorld().getLivingEntities())
        {
            if (!(livingEntity instanceof ArmorStand))
            {
                if (livingEntity.getUniqueId().compareTo(getOwner()) != 0 && !livingEntity.isDead())
                {
                    if (this.location.distance(livingEntity.getLocation().add(0, 1, 0)) <= 4.5)
                    {
                        final Player shooter = Bukkit.getPlayer(getOwner());
                        final double damage =
                            DamageUtil.calculateSpellDamage(shooter, livingEntity, "Fireball", isDirectHit ? this.damageMin * 1.15 : this.damageMin,
                                isDirectHit ? this.damageMax * 1.15 : this.damageMax, this.critChance, this.critMultiplier);
                        if (livingEntity instanceof Player)
                        {
                            final Player victim = (Player) livingEntity;
                            final double warlordsHealthPoints = StatUtil.getWarlordsClassHealth(victim.getUniqueId());
                            if (damage > warlordsHealthPoints)
                            {
                                StatUtil.resetStats(victim.getUniqueId());
                                ItemUtil.removeItems(victim.getUniqueId());
                                victim.setHealth(0.0);
                                WeaponUtil.handleWeaponDropPossibility(shooter, livingEntity);
                            } else
                            {
                                victim.setHealth(StatUtil.warlordsHealthPointsToHealth(victim.getUniqueId(), warlordsHealthPoints - damage));
                            }
                        } else
                        {
                            final double warlordsHealthPoints = livingEntity.getHealth() * 200.0;
                            if (damage > warlordsHealthPoints)
                            {
                                livingEntity.setHealth(0.0);
                                WeaponUtil.handleWeaponDropPossibility(shooter, livingEntity);
                            } else
                            {
                                livingEntity.setHealth((warlordsHealthPoints - damage) / 200.0);
                            }
                        }
                        shooter.playSound(shooter.getLocation(), "entity.arrow.hit_player", 1f, 1f);
                    }
                }
            }
        }
    }

    @Override
    public void doFinish()
    {

    }
}
