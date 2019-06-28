package recreate.data;

import recreate.classes.WarlordsClass;
import recreate.weapon.Weapon;

import java.util.ArrayList;

public class WarlordsPlayer
{

    private transient WarlordsClass warlordsClass;
    private transient ArrayList<Weapon> weaponInventory = new ArrayList<>();
    private int weaponSlot = 0;
    private int voidShards = 0;

    public WarlordsPlayer(final WarlordsClass warlordsClass)
    {
        this.warlordsClass = warlordsClass;
    }

    public WarlordsPlayer(final WarlordsClass warlordsClass,
        final ArrayList<Weapon> weaponInventory,
        final int weaponSlot,
        final int voidShards)
    {
        this.warlordsClass = warlordsClass;
        this.weaponInventory = weaponInventory;
        this.weaponSlot = weaponSlot;
        this.voidShards = voidShards;
    }

    public WarlordsClass getWarlordsClass()
    {
        return this.warlordsClass;
    }

    public ArrayList<Weapon> getWeaponInventory()
    {
        return this.weaponInventory;
    }

    public Weapon getActiveWeapon()
    {
        if (this.weaponInventory.size() > this.weaponSlot)
        {
            return this.weaponInventory.get(this.weaponSlot);
        }
        if (this.weaponInventory.size() > 0)
        {
            this.weaponSlot = 0;
            return this.weaponInventory.get((0));
        }
        return null;
    }

    public int getWeaponSlot()
    {
        return this.weaponSlot;
    }

    public int getVoidShards()
    {
        return this.voidShards;
    }

    public void setWarlordsClass(final WarlordsClass warlordsClass)
    {
        this.warlordsClass = warlordsClass;
    }

    public void setWeaponInventory(final ArrayList<Weapon> weaponInventory)
    {
        this.weaponInventory = weaponInventory;
    }

    public void setWeaponSlot(final int weaponSlot)
    {
        this.weaponSlot = weaponSlot;
    }

    public void setVoidShards(final int voidShards)
    {
        this.voidShards = voidShards;
    }
}
