package recreate.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import recreate.classes.WarlordsClassType;
import recreate.main.Warlords;

import java.util.Objects;

public class CommandChooseClass extends WarlordsCommand
{
    public CommandChooseClass(final WarlordsCommandSender sender)
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
            if (Warlords.get().warlordsPlayerManager.getWarlordsClass(player.getUniqueId()) == WarlordsClassType.NONE)
            {
                if (args.length == 1)
                {
                    final String input = args[0];
                    final WarlordsClassType warlordsClassType = WarlordsClassType.fromString(input);
                    if (Objects.nonNull(warlordsClassType))
                    {
                        Warlords.get().warlordsPlayerManager.chooseClass(player.getUniqueId(), warlordsClassType);
                        commandSender.sendMessage("You choose " + input + "as your class");
                        return true;
                    }
                    commandSender.sendMessage("Incorrect class name");
                } else
                {
                    commandSender.sendMessage("Syntax: choose name");
                }
            } else
            {
                player.sendMessage("You choose already a class");
            }
        }
        return false;
    }

}
