package recreate.classes.mage;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import recreate.data.TimeLimitedSpell;

import java.util.UUID;

public class ArcaneShield extends TimeLimitedSpell
{
    private double value;

    public ArcaneShield(final UUID uuid,
        final double value,
        final int duration)
    {
        super(uuid, duration);
        this.value = value;
    }

    public double getValue()
    {
        return this.value;
    }

    public void setValue(final double value)
    {
        this.value = value;
    }

    @Override
    public void doStart()
    {
        final Player player = Bukkit.getPlayer(this.getOwner());
        player.getWorld().playSound(player.getLocation(), "mage.arcaneshield.activation", 1.5f, 1f);
    }

    @Override
    public void doTick()
    {
        final Player player = Bukkit.getPlayer(this.getOwner());
        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation(), 4, 0.3f, 0.6f, 0.3f, 0f);
        player.getWorld().spawnParticle(Particle.SPELL_WITCH, player.getLocation(), 1, 0.3f, 0.6f, 0.3f, 0f);
        player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 2, 0.3f, 0.6f, 0.3f, 0f);
    }

    @Override
    public void doFinish()
    {

    }

    @Override
    public void cancel()
    {

    }
}
