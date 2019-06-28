package recreate.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class WarlordsCommand
{
    private final WarlordsCommandSender validSender;

    public WarlordsCommand(final WarlordsCommandSender sender)
    {
        this.validSender = sender;
    }

    public abstract boolean handle(CommandSender sender,
        String[] args);

    public boolean isValidSender(final CommandSender commandSender)
    {
        switch (this.validSender)
        {
            case NONPLAYER:
                if (!(commandSender instanceof Player))
                {
                    return true;
                } else
                {
                    commandSender.sendMessage("Command cannot be executed by players");
                }
            case PLAYER:
                if (commandSender instanceof Player)
                {
                    return true;
                } else
                {
                    commandSender.sendMessage("Command can only be executed by players");
                }
            default:
                return true;
        }
    }
}
