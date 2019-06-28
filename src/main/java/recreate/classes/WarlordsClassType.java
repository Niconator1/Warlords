package recreate.classes;

public enum WarlordsClassType
{
    NONE("", 0, 0, 0, 0, 0, 0),
    PYROMANCER("Pyromancer", 7, 10, 200, 5500, 1.2, 1.0);

    int energyPerSecond, energyPerHit, energyMax, healthMax;
    double damageDone, damageTaken;
    String title;

    WarlordsClassType(final String title,
        final int energyPerSecond,
        final int energyPerHit,
        final int energyMax,
        final int healthMax,
        final double damageDone,
        final double damageTaken)
    {
        this.title = title;
        this.energyPerSecond = energyPerSecond;
        this.energyPerHit = energyPerHit;
        this.energyMax = energyMax;
        this.healthMax = healthMax;
        this.damageDone = damageDone;
        this.damageTaken = damageTaken;
    }

    public static WarlordsClassType fromString(final String name)
    {
        for (final WarlordsClassType warlordsClassType : WarlordsClassType.values())
        {
            if (warlordsClassType.name().equalsIgnoreCase(name))
            {
                return warlordsClassType;
            }
        }
        return null;
    }

    public int getEnergyPerSecond()
    {
        return this.energyPerSecond;
    }

    public int getEnergyPerHit()
    {
        return this.energyPerHit;
    }

    public int getEnergyMax()
    {
        return this.energyMax;
    }

    public int getHealthMax()
    {
        return this.healthMax;
    }

    public double getDamageDone()
    {
        return this.damageDone;
    }

    public double getDamageTaken()
    {
        return this.damageTaken;
    }

    public String getTitle()
    {
        return this.title;
    }

}
