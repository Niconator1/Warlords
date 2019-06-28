package recreate.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Objects;

public class WarlordsCommandManager
{
    private final HashMap<String, WarlordsCommand> mapCommandToWarlordsCommand = new HashMap<>();

    public boolean handleWarlordsCommand(final CommandSender commandSender,
        final Command command,
        final String[] args)
    {
        final WarlordsCommand warlordsCommand = this.mapCommandToWarlordsCommand.get(command.getName());
        if (Objects.nonNull(warlordsCommand))
        {
            if (warlordsCommand.isValidSender(commandSender))
            {
                return warlordsCommand.handle(commandSender, args);
            }
        }
        return false;
    }

    public void registerCommands()
    {
        this.mapCommandToWarlordsCommand.put("choose", new CommandChooseClass(WarlordsCommandSender.PLAYER));
        this.mapCommandToWarlordsCommand.put("generateWeapon", new CommandGenerateWeapon(WarlordsCommandSender.PLAYER));
    }
}
