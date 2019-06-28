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
import recreate.util.MathUtil;
import recreate.util.StatUtil;
import recreate.util.WeaponUtil;

import java.util.UUID;

public class FlameBurst extends TimeSpell
{
    private final double damageMin;
    private final double damageMax;
    private final double critChance;
    private final double critMultiplier;
    private Vector vector;
    private Location startLocation;
    private Location location;

    public FlameBurst(final UUID uuid,
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
        this.vector = new Vector(x, z, y).multiply(0.95);
        this.startLocation = player.getEyeLocation();
        this.location = player.getEyeLocation();
        player.getWorld().playSound(player.getLocation(), "mage.fireball.activation", 1, 1);
    }

    @Override
    public void doTick()
    {
        final double angle = this.location.distance(this.startLocation) * 0.2 * Math.PI;
        Vector vector = new Vector(Math.cos(angle) * 0.6, 0, Math.sin(angle) * 0.6);
        vector = MathUtil.rotateAroundAxisX(vector, -((this.location.getPitch() + 90) / 180.0 * Math.PI));
        vector = MathUtil.rotateAroundAxisY(vector, (this.location.getYaw()) * Math.PI / 180);
        this.location.getWorld().spawnParticle(Particle.FLAME, this.location.add(vector), 1, 0.0f, 0.0f, 0.0f, 0f);
        this.location.getWorld().spawnParticle(Particle.FLAME, this.location.subtract(vector), 1, 0.0f, 0.0f, 0.0f, 0f);
        this.location.getWorld().spawnParticle(Particle.LAVA, this.location, 1, 0.0f, 0.0f, 0.0f, 0f);
        if (this.location.distance(this.startLocation) > 2)
        {
            this.location.getWorld().spawnParticle(Particle.DRIP_LAVA, this.location, 1, 0.0f, 0.0f, 0.0f, 0f);
        }
        for (final LivingEntity livingEntity : this.location.getWorld().getLivingEntities())
        {
            if (!(livingEntity instanceof ArmorStand))
            {
                if (livingEntity.getUniqueId().compareTo(getOwner()) != 0)
                {
                    if (this.location.distance(livingEntity.getLocation().add(0, 1, 0)) <= 2.5)
                    {
                        doHit();
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

    private void doHit()
    {
        this.location.getWorld().playSound(this.location, "mage.flameburst.impact", 1f, 1f);
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
                            DamageUtil.calculateSpellDamage(shooter, livingEntity, "Flameburst", this.damageMin, this.damageMax, this.critChance,
                                this.critMultiplier);
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
