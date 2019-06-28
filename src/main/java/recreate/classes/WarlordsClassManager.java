package recreate.classes;

import recreate.classes.mage.pyromancer.Pyromancer;

public class WarlordsClassManager
{

    public static WarlordsClass getWarlordsClass(final WarlordsClassType warlordsClassType)
    {
        switch (warlordsClassType)
        {
            case PYROMANCER:
                return new Pyromancer();
            default:
                return null;
        }
    }

}
