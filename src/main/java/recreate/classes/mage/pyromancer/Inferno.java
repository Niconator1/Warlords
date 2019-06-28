package recreate.classes.mage.pyromancer;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import recreate.data.TimeLimitedSpell;

import java.util.UUID;

public class Inferno extends TimeLimitedSpell
{
    private final double critChance;
    private final double critMultiplier;

    public Inferno(final UUID uuid,
        final double critChance,
        final double CritMultiplier,
        final int duration)
    {
        super(uuid, duration);
        this.critChance = critChance;
        this.critMultiplier = CritMultiplier;
    }

    int i = 0;

    @Override
    public void doStart()
    {
        final Player player = Bukkit.getPlayer(this.getOwner());
        player.getWorld().playSound(player.getLocation(), "mage.inferno.activation", 1, 1);
    }

    @Override
    public void doTick()
    {
        if (this.i % 10 == 0)
        {
            final Player player = Bukkit.getPlayer(this.getOwner());
            player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 40, 0.35f, 0.55f, 0.35f, 0f);
        }
        this.i++;
    }

    @Override
    public void doFinish()
    {

    }

    @Override
    public void cancel()
    {

    }

    public double getCritChance()
    {
        return this.critChance;
    }

    public double getCritMultiplier()
    {
        return this.critMultiplier;
    }
}
