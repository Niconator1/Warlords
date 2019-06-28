package recreate.classes.mage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import recreate.data.TimeLimitedSpell;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.util.DamageUtil;
import recreate.util.StatUtil;

import java.util.ArrayList;
import java.util.UUID;

public class TimeWarp extends TimeLimitedSpell
{
    private final double value;
    private final Location location;
    private final ArrayList<Location> particleLocations = new ArrayList<>();

    public TimeWarp(final UUID uuid,
        final Location location,
        final double value,
        final int duration)
    {
        super(uuid, duration);
        this.location = location;
        this.value = value;
    }

    @Override
    public void cancel()
    {

    }

    int i = 0;

    @Override
    public void doStart()
    {
        final Player player = Bukkit.getPlayer(this.getOwner());
        player.getWorld().playSound(player.getLocation(), "mage.timewarp.activation", 1f, 1f);
    }

    @Override
    public void doTick()
    {
        if (this.i % 10 == 0)
        {
            final Player player = Bukkit.getPlayer(this.getOwner());
            player.getWorld().spawnParticle(Particle.SPELL_WITCH, player.getLocation(), 3, 0.1f, 0.1f, 0.1f, 0f);
            this.particleLocations
                .stream().forEach(listLocation -> player.getWorld().spawnParticle(Particle.CLOUD, listLocation, 100, 0.5f, 0.5f, 0.5f, 0));
            this.particleLocations.add(player.getLocation());
        }
        this.i++;
    }

    @Override
    public void doFinish()
    {
        final Player player = Bukkit.getPlayer(this.getOwner());
        player.teleport(this.location);
        final double warlordsHealthPoints = StatUtil.getWarlordsClassHealth(player.getUniqueId());
        final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(getOwner());
        final double heal = DamageUtil.calculateHeal(player, player, warlordsPlayer.getActiveWeapon(), this.value, "Time Warp");
        player.setHealth(StatUtil.warlordsHealthPointsToHealth(player.getUniqueId(), warlordsHealthPoints + heal));
        player.getWorld().playSound(this.location, "mage.timewarp.teleport", 1f, 1f);
    }
}
