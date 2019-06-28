package recreate.util;

import org.bukkit.ChatColor;

public class ColorUtil
{
    public static ChatColor getCompareColor(final int x,
        final int y)
    {
        if (x == y)
        {
            return ChatColor.GRAY;
        } else if (x > y)
        {
            return ChatColor.RED;
        }
        return ChatColor.GREEN;
    }

    public static ChatColor getCompareColor(final double x,
        final double y)
    {
        if (Math.abs(x - y) < 0.1)
        {
            return ChatColor.GRAY;
        } else if (x - y > 0.1)
        {
            return ChatColor.RED;
        }
        return ChatColor.GREEN;
    }
}
