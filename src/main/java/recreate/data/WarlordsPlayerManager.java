package recreate.data;

import recreate.classes.WarlordsClass;
import recreate.classes.WarlordsClassManager;
import recreate.classes.WarlordsClassType;
import recreate.main.Warlords;
import recreate.util.ItemUtil;
import recreate.util.StatUtil;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class WarlordsPlayerManager
{
    private final HashMap<UUID, WarlordsPlayer> mapPlayerToWarlordsPlayer = new HashMap<>();

    public WarlordsClassType getWarlordsClass(final UUID uuid)
    {
        final WarlordsPlayer warlordsPlayer = getWarlordsPlayer(uuid);
        if (Objects.nonNull(warlordsPlayer))
        {
            final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
            return Objects.nonNull(warlordsClass) ? warlordsClass.getType() : WarlordsClassType.NONE;
        }
        return WarlordsClassType.NONE;
    }

    public void chooseClass(final UUID uuid,
        final WarlordsClassType warlordsClassType)
    {
        final WarlordsPlayer warlordsPlayer = getWarlordsPlayer(uuid);
        if (Objects.nonNull(warlordsPlayer))
        {
            warlordsPlayer.setWarlordsClass(WarlordsClassManager.getWarlordsClass(warlordsClassType));
        } else
        {
            this.mapPlayerToWarlordsPlayer.put(uuid, new WarlordsPlayer(WarlordsClassManager.getWarlordsClass(warlordsClassType)));
        }
    }

    public WarlordsPlayer getWarlordsPlayer(final UUID uuid)
    {
        WarlordsPlayer warlordsPlayer = this.mapPlayerToWarlordsPlayer.get(uuid);
        if (Objects.isNull(warlordsPlayer))
        {
            warlordsPlayer = Warlords.get().warlordsDataManager.readData(uuid);
            if (Objects.nonNull(warlordsPlayer))
            {
                this.mapPlayerToWarlordsPlayer.put(uuid, warlordsPlayer);
            }
        }
        return warlordsPlayer;
    }

    public void onEnable()
    {
        this.mapPlayerToWarlordsPlayer.forEach((uuid, warlordsPlayer) -> ItemUtil.giveItems(uuid));
        this.mapPlayerToWarlordsPlayer.forEach((uuid, warlordsPlayer) -> StatUtil.giveStats(uuid));
    }

    public void onDisable()
    {
        this.mapPlayerToWarlordsPlayer.forEach((uuid, warlordsPlayer) -> ItemUtil.removeItems(uuid));
        this.mapPlayerToWarlordsPlayer.forEach((uuid, warlordsPlayer) -> StatUtil.resetStats(uuid));
        this.mapPlayerToWarlordsPlayer.forEach(Warlords.get().warlordsDataManager::savePlayerConfig);
    }

    public boolean doValidChecks(final UUID uuid)
    {
        final WarlordsPlayer warlordsPlayer = getWarlordsPlayer(uuid);
        if (Objects.nonNull(warlordsPlayer))
        {
            final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
            if (Objects.nonNull(warlordsClass) && warlordsClass.getType() != WarlordsClassType.NONE)
            {
                return true;
            }
        }
        return false;
    }
}
