package recreate.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import recreate.commands.WarlordsCommandManager;
import recreate.data.WarlordsDataManager;
import recreate.data.WarlordsPlayerManager;
import recreate.data.WarlordsSpellHandler;
import recreate.event.EventEntityHandler;
import recreate.event.EventInventoryHandler;
import recreate.event.EventPlayerHandler;
import recreate.inventory.WarlordsInventory;

import java.util.ArrayList;

public class Warlords extends JavaPlugin
{
    public WarlordsDataManager warlordsDataManager = new WarlordsDataManager(this.getDataFolder());
    public WarlordsCommandManager warlordsCommandManager = new WarlordsCommandManager();
    public WarlordsPlayerManager warlordsPlayerManager = new WarlordsPlayerManager();
    public WarlordsSpellHandler warlordsSpellHandler = new WarlordsSpellHandler();
    public ArrayList<WarlordsInventory> listWarlordsInventory = new ArrayList<>();

    @Override
    public void onEnable()
    {
        this.warlordsCommandManager.registerCommands();
        this.warlordsPlayerManager.onEnable();
        this.getServer().getPluginManager().registerEvents(new EventPlayerHandler(), this);
        this.getServer().getPluginManager().registerEvents(new EventEntityHandler(), this);
        this.getServer().getPluginManager().registerEvents(new EventInventoryHandler(), this);
        this.warlordsSpellHandler.onEnable();
        this.getLogger().info("Warlords enabled");
    }

    @Override
    public void onDisable()
    {
        this.warlordsPlayerManager.onDisable();
        this.warlordsSpellHandler.onDisable();
        this.getLogger().info("Warlords disabled");
    }

    @Override
    public boolean onCommand(final CommandSender commandSender,
        final Command command,
        final String commandLabel,
        final String[] args)
    {
        return this.warlordsCommandManager.handleWarlordsCommand(commandSender, command, args);
    }

    public static Warlords get()
    {
        return Warlords.getPlugin(Warlords.class);
    }
}
