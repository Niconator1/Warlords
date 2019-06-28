package recreate.weapon;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum WarlordsWeaponType
{
    COMMON(ChatColor.GREEN, Material.GREEN_STAINED_GLASS_PANE), RARE(ChatColor.BLUE, Material.BLUE_STAINED_GLASS_PANE), EPIC(ChatColor.DARK_PURPLE,
    Material.PURPLE_STAINED_GLASS_PANE), LEGENDARY(ChatColor.GOLD, Material.YELLOW_STAINED_GLASS_PANE);

    private final ChatColor chatColor;
    private final Material material;

    WarlordsWeaponType(final ChatColor chatColor,
        final Material material)
    {
        this.chatColor = chatColor;
        this.material = material;
    }

    public ChatColor getChatColor()
    {
        return this.chatColor;
    }

    public Material getGlassPane()
    {
        return this.material;
    }
}
