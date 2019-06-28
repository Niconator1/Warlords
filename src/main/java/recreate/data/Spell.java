package recreate.data;

import java.util.UUID;

public abstract class Spell
{
    private final UUID uuid;

    public Spell(final UUID uuid)
    {
        this.uuid = uuid;
    }

    public UUID getOwner()
    {
        return this.uuid;
    }

    public abstract void start();

    public abstract void cancel();

}
