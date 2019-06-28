package recreate.data;

import org.bukkit.Bukkit;
import recreate.main.Warlords;

import java.util.UUID;

public abstract class TimeLimitedSpell extends Spell
{
    private int duration;

    public TimeLimitedSpell(final UUID uuid,
        final int duration)
    {
        super(uuid);
        this.duration = duration;
    }

    @Override
    public void start()
    {
        if (Warlords.get().warlordsPlayerManager.doValidChecks(getOwner()))
        {
            doStart();
        }
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Warlords.get(), () -> {
            if (this.duration > 0)
            {
                if (Warlords.get().warlordsPlayerManager.doValidChecks(getOwner()))
                {
                    doTick();
                }

            } else
            {
                if (Warlords.get().warlordsPlayerManager.doValidChecks(getOwner()))
                {
                    doFinish();
                }
                this.cancel();
            }
            this.duration--;
        }, 0, 1);
    }

    public abstract void doStart();

    public abstract void doTick();

    public abstract void doFinish();

}
