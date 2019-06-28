package recreate.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.util.WeaponUtil;
import recreate.weapon.Weapon;

import java.util.Objects;

public class CommandGenerateWeapon extends WarlordsCommand
{
    public CommandGenerateWeapon(final WarlordsCommandSender sender)
    {
        super(sender);
    }

    @Override
    public boolean handle(final CommandSender commandSender,
        final String[] args)
    {
        if (commandSender instanceof Player)
        {
            final Player player = (Player) commandSender;
            final Weapon weapon = WeaponUtil.generateRandomWeapon();
            final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
            if (Objects.nonNull(warlordsPlayer))
            {
                warlordsPlayer.getWeaponInventory().add(weapon);
                commandSender.sendMessage("Successfully generated a random Weapon");
                return true;
            } else
            {
                commandSender.sendMessage("Playerdata not found");
            }
        }
        return false;
    }

}
