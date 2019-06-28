package recreate.util;

import org.bukkit.util.Vector;

public class MathUtil
{
    public static final Vector rotateAroundAxisX(final Vector v,
        final double angle)
    {
        final double y;
        final double z;
        final double cos;
        final double sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static final Vector rotateAroundAxisY(final Vector v,
        final double angle)
    {
        final double x;
        final double z;
        final double cos;
        final double sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }
}
