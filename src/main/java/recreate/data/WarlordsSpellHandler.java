package recreate.data;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class WarlordsSpellHandler
{
    private final ArrayList<Spell> listSpell = new ArrayList<>();

    public void onEnable()
    {

    }

    public void onDisable()
    {
        this.listSpell.forEach(Spell::cancel);
        this.listSpell.forEach(this.listSpell::remove);
    }

    public void registerSpell(final Spell spell)
    {
        this.listSpell.add(spell);
        spell.start();
    }

    public void cancelSpellsForPlayer(final UUID uuid)
    {
        this.listSpell.stream().forEach(spell -> {
            if (spell.getOwner().compareTo(uuid) == 0)
            {
                spell.cancel();
                this.listSpell.remove(spell);
            }
        });
    }

    public ArrayList<Spell> getSpellsByClassType(final Class classType)
    {
        return this.listSpell.stream().filter(spell -> classType.isInstance(spell)
        ).collect(Collectors.toCollection(ArrayList::new));
    }
}
