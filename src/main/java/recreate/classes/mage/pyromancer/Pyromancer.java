package recreate.classes.mage.pyromancer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import recreate.classes.WarlordsClass;
import recreate.classes.WarlordsClassType;
import recreate.classes.mage.ArcaneShield;
import recreate.classes.mage.TimeWarp;
import recreate.data.Spell;
import recreate.main.Warlords;
import recreate.util.ChatUtil;
import recreate.util.StatUtil;
import recreate.util.WeaponUtil;
import recreate.weapon.Weapon;
import recreate.weapon.WeaponSpell;

import java.util.ArrayList;
import java.util.Objects;

public class Pyromancer extends WarlordsClass
{
    public int energyFireball = 35;
    public double damageMinFireball = 334.8;
    public double damageMaxFireball = 433.2;
    public double critChanceFireball = 0.2;
    public double critMultiplierFireball = 1.75;

    public double cooldownFlameBurst = 10.8;
    public int energyFlameBurst = 30;
    public double damageMinFlameBurst = 557;
    public double damageMaxFlameBurst = 753;
    public double critChanceFlameBurst = 0.2;
    public double critMultiplierFlameBurst = 2.1;

    public double cooldownTimeWarp = 33.75;
    public int energyTimeWarp = 15;
    public double restoreHealthTimeWarp = 0.3;
    public int delayTimeWarp = 5;

    public double cooldownArcaneShield = 36.0;
    public int energyArcaneShield = 20;
    public double strengthArcaneShield = 0.5;
    public int durationArcaneShield = 6;

    public double cooldownInferno = 54.0;
    public double critChanceInferno = 0.2;
    public double critMultiplierInferno = 0.5;
    public int durationInferno = 18;

    public Pyromancer()
    {
        super(WarlordsClassType.PYROMANCER);
    }

    @Override
    public ItemStack getItemForSlot(final Weapon weapon,
        final int slot)
    {
        double cooldownMultiplier = 1.0;
        if (Objects.nonNull(weapon))
        {
            cooldownMultiplier += weapon.getCooldown();
        }
        switch (slot)
        {
            case 0:
                final ItemStack itemStack = WeaponUtil.generateWeaponItemStack(weapon, this);
                if (itemStack != null)
                {
                    final ItemMeta itemMeta = itemStack.getItemMeta();
                    final WeaponSpell weaponSpell = weapon.getWeaponBoostSpell();
                    if (weaponSpell == WeaponSpell.FIREBALL)
                    {
                        itemMeta.setLore(ItemGeneratorPyromancer.getMain(this.energyFireball, this.critChanceFireball, this.critMultiplierFireball,
                            this.damageMinFireball * (1.0 + weapon.getWeaponBoost()), this.damageMaxFireball * (1.0 + weapon.getWeaponBoost())));
                    } else
                    {
                        itemMeta.setLore(ItemGeneratorPyromancer
                            .getMain(this.energyFireball, this.critChanceFireball, this.critMultiplierFireball, this.damageMinFireball,
                                this.damageMaxFireball));
                    }
                    itemMeta.setDisplayName(ChatColor.GREEN + "Fireball");
                    itemStack.setItemMeta(itemMeta);
                    return itemStack;
                }
                return null;
            case 1:
                if (Objects.nonNull(weapon))
                {
                    final WeaponSpell weaponSpell = weapon.getWeaponBoostSpell();
                    if (weaponSpell == WeaponSpell.FLAMEBURST)
                    {
                        return ItemGeneratorPyromancer
                            .getRedRune(this.cooldownFlameBurst * cooldownMultiplier, this.energyFlameBurst, this.critChanceFlameBurst,
                                this.critMultiplierFlameBurst,
                                this.damageMinFlameBurst * (1.0 + weapon.getWeaponBoost()),
                                this.damageMaxFlameBurst * (1.0 + weapon.getWeaponBoost()));
                    }
                }
                return ItemGeneratorPyromancer
                    .getRedRune(this.cooldownFlameBurst * cooldownMultiplier, this.energyFlameBurst, this.critChanceFlameBurst,
                        this.critMultiplierFlameBurst,
                        this.damageMinFlameBurst,
                        this.damageMaxFlameBurst);
            case 2:
                return ItemGeneratorPyromancer
                    .getPurpleRune(this.cooldownTimeWarp * cooldownMultiplier, this.energyTimeWarp, this.delayTimeWarp, this.restoreHealthTimeWarp);
            case 3:
                return ItemGeneratorPyromancer
                    .getBlueRune(this.cooldownArcaneShield * cooldownMultiplier, this.energyArcaneShield,
                        this.strengthArcaneShield * this.type.getHealthMax(),
                        this.strengthArcaneShield,
                        this.durationArcaneShield);
            case 4:
                return ItemGeneratorPyromancer
                    .getYellowRune(this.cooldownInferno * cooldownMultiplier, this.critChanceInferno, this.critMultiplierInferno,
                        this.durationInferno);
            default:
                return null;
        }

    }

    @Override
    public void castAbility(final Player player,
        final Weapon weapon,
        final int slot)
    {
        double critChanceBonus = 0.0;
        double critMultiplierBonus = 0.0;
        final ArrayList<Spell> spells = Warlords.get().warlordsSpellHandler.getSpellsByClassType(Inferno.class);
        for (final Spell spell : spells)
        {
            final Inferno inferno = (Inferno) spell;
            critChanceBonus += inferno.getCritChance();
            critMultiplierBonus += inferno.getCritMultiplier();
        }
        switch (slot)
        {
            case 0:
                if (StatUtil.removeEnergy(player.getUniqueId(), this.energyFireball))
                {
                    final Fireball fireball =
                        new Fireball(player.getUniqueId(), this.damageMinFireball, this.damageMaxFireball, this.critChanceFireball + critChanceBonus,
                            this.critMultiplierFireball + critMultiplierBonus);
                    Warlords.get().warlordsSpellHandler.registerSpell(fireball);
                    return;
                }
                break;
            case 1:
                if (StatUtil.removeEnergy(player.getUniqueId(), this.energyFlameBurst))
                {
                    final FlameBurst flameBurst =
                        new FlameBurst(player.getUniqueId(), this.damageMinFlameBurst, this.damageMaxFlameBurst,
                            this.critChanceFlameBurst + critChanceBonus,
                            this.critMultiplierFlameBurst + critMultiplierBonus);
                    Warlords.get().warlordsSpellHandler.registerSpell(flameBurst);
                }
                break;
            case 2:
                if (StatUtil.removeEnergy(player.getUniqueId(), this.energyArcaneShield))
                {
                    final ArcaneShield arcaneShield =
                        new ArcaneShield(player.getUniqueId(), this.strengthArcaneShield * StatUtil.getWarlordsClassMaxEnergy(player.getUniqueId()),
                            this.durationArcaneShield);
                    Warlords.get().warlordsSpellHandler.registerSpell(arcaneShield);
                    return;
                }
                break;
            case 3:
                if (StatUtil.removeEnergy(player.getUniqueId(), this.energyTimeWarp))
                {
                    final TimeWarp timeWarp = new TimeWarp(player.getUniqueId(), player.getLocation(),
                        this.restoreHealthTimeWarp * StatUtil.getWarlordsClassHealthMaxHealth(player.getUniqueId()), this.delayTimeWarp);
                    Warlords.get().warlordsSpellHandler.registerSpell(timeWarp);
                    return;
                }
                break;
            case 4:
                final Inferno inferno =
                    new Inferno(player.getUniqueId(), this.critChanceInferno, this.critMultiplierInferno, this.durationInferno);
                Warlords.get().warlordsSpellHandler.registerSpell(inferno);
                return;
            default:
                break;
        }
        ChatUtil.doNotEnoughEnergyMessage(player);
    }
}
