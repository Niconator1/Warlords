package recreate.data;

import org.bukkit.Bukkit;
import recreate.main.Warlords;

import java.util.UUID;

public abstract class TimeSpell extends Spell
{
    private boolean cancelled = false;

    public TimeSpell(final UUID uuid)
    {
        super(uuid);
    }

    @Override
    public void start()
    {
        if (Warlords.get().warlordsPlayerManager.doValidChecks(getOwner()))
        {
            doStart();
        }
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Warlords.get(), () -> {
            if (!this.cancelled)
            {
                if (Warlords.get().warlordsPlayerManager.doValidChecks(getOwner()))
                {
                    doTick();
                }

            }
            if (this.cancelled)
            {
                if (Warlords.get().warlordsPlayerManager.doValidChecks(getOwner()))
                {
                    doFinish();
                }
                this.cancel();
            }
        }, 0, 1);
    }

    public abstract void doStart();

    public abstract void doTick();

    public abstract void doFinish();

    public void setFinished()
    {
        this.cancelled = true;
    }

}
