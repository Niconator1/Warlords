package recreate.weapon;

import recreate.classes.WarlordsClassType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum WeaponSpell
{
    FIREBALL(WarlordsClassType.PYROMANCER, "Fireball", 0), FLAMEBURST(WarlordsClassType.PYROMANCER, "Flameburst", 1);

    private final WarlordsClassType warlordsClassType;
    private final String title;
    private final int slot;

    WeaponSpell(final WarlordsClassType warlordsClassType,
        final String title,
        final int slot)
    {
        this.warlordsClassType = warlordsClassType;
        this.title = title;
        this.slot = slot;
    }

    public String getTitle()
    {
        return this.title;
    }

    public int getSlot()
    {
        return this.slot;
    }

    public static ArrayList<WeaponSpell> getSpellsByClass(final WarlordsClassType warlordsClassType)
    {
        return Arrays.stream(WeaponSpell.values()).sequential().filter(weaponSpell -> weaponSpell.warlordsClassType == warlordsClassType)
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
