package recreate.weapon;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum WeaponSkin
{
    HAMMER(WarlordsWeaponType.COMMON, "Hammer", Material.IRON_SHOVEL),
    SCIMITAR(WarlordsWeaponType.COMMON, "Scimitar", Material.SALMON),
    ORC_AXE(WarlordsWeaponType.COMMON, "Orc Axe", Material.PUMPKIN_PIE),
    STEEL_SWORD(WarlordsWeaponType.COMMON, "Steel Sword", Material.WOODEN_AXE),
    BLUDGEON(WarlordsWeaponType.COMMON, "Bludgeon", Material.RABBIT_STEW),
    TRAINING_SWORD(WarlordsWeaponType.COMMON, "Training Sword", Material.STONE_AXE),
    WALKING_STICK(WarlordsWeaponType.COMMON, "Walking Stick", Material.STONE_PICKAXE),
    CLAWS(WarlordsWeaponType.COMMON, "Claws", Material.MUTTON),
    HATCHET(WarlordsWeaponType.COMMON, "Hatchet", Material.GOLDEN_HOE),
    PIKE(WarlordsWeaponType.COMMON, "Pike", Material.ROTTEN_FLESH),

    WORLD_TREE_BRANCH(WarlordsWeaponType.RARE, "World Tree Branch", Material.IRON_PICKAXE),
    MANDIBLES(WarlordsWeaponType.RARE, "Mandibles", Material.PORKCHOP),
    GEM_AXE(WarlordsWeaponType.RARE, "Gem Axe", Material.DIAMOND_HOE),
    DEMON_BLADE(WarlordsWeaponType.RARE, "Demonblade", Material.IRON_AXE),
    CLUDGEL(WarlordsWeaponType.RARE, "Cludgel", Material.COOKED_RABBIT),
    GOLDEN_GLADIUS(WarlordsWeaponType.RARE, "Golden Gladius", Material.PUFFERFISH),
    DOUBLEAXE(WarlordsWeaponType.RARE, "Doubleaxe", Material.COOKED_COD),
    STONE_MALLET(WarlordsWeaponType.RARE, "Stone Mallet", Material.GOLDEN_SHOVEL),
    VENOMSTRIKE(WarlordsWeaponType.RARE, "Venomstrike", Material.GOLDEN_AXE),
    HALBERT(WarlordsWeaponType.RARE, "Halbert", Material.POTATO),

    DIAMONDSPARK(WarlordsWeaponType.EPIC, "Diamondspark", Material.DIAMOND_AXE),
    FLAMEWEAVER(WarlordsWeaponType.EPIC, "Flameweaver", Material.GOLDEN_PICKAXE),
    ELVEN_GREATSWORD(WarlordsWeaponType.EPIC, "Elven Greatsword", Material.IRON_HOE),
    GEMCRUSHER(WarlordsWeaponType.EPIC, "Gemcrusher", Material.DIAMOND_SHOVEL),
    RUNIC_AXE(WarlordsWeaponType.EPIC, "Runic Axe", Material.BREAD),
    DIVINE_REACH(WarlordsWeaponType.EPIC, "Divine Reach", Material.MELON),
    ZWEIREAPER(WarlordsWeaponType.EPIC, "Zweireaper", Material.WOODEN_HOE),
    TENDERIZER(WarlordsWeaponType.EPIC, "Tenderizer", Material.COOKED_CHICKEN),
    MAGMASWORD(WarlordsWeaponType.EPIC, "Magmasword", Material.TROPICAL_FISH),
    RUNEBLADE(WarlordsWeaponType.EPIC, "Runeblade", Material.STONE_HOE),
    HAMMER_OF_LIGHT(WarlordsWeaponType.EPIC, "Hammer of Light", Material.STRING),
    KATAR(WarlordsWeaponType.EPIC, "Katar", Material.BEEF),
    NOMEGUSTA(WarlordsWeaponType.EPIC, "Nomegusta", Material.WOODEN_SHOVEL),
    NETHERSTEEL_KATANA(WarlordsWeaponType.EPIC, "Nethersteel Katana", Material.CHICKEN),
    LUNAR_RELIC(WarlordsWeaponType.EPIC, "Lunar Relic", Material.MUSHROOM_STEW),

    FELFLAME_BLADE(WarlordsWeaponType.LEGENDARY, "Felflame Blade", Material.COOKED_SALMON),
    VOID_EDGE(WarlordsWeaponType.LEGENDARY, "Void Edge", Material.GOLDEN_CARROT),
    AMARANTH(WarlordsWeaponType.LEGENDARY, "Amaranth", Material.COOKED_MUTTON),
    ARMBLADE(WarlordsWeaponType.LEGENDARY, "Armblade", Material.COOKED_BEEF),
    GEMINI(WarlordsWeaponType.LEGENDARY, "Gemini", Material.COOKED_PORKCHOP),
    DRAKEFANG(WarlordsWeaponType.LEGENDARY, "Drakefang", Material.STONE_SHOVEL),
    ABBADON(WarlordsWeaponType.LEGENDARY, "Abbadon", Material.WOODEN_PICKAXE),
    VOID_TWIG(WarlordsWeaponType.LEGENDARY, "Void Twig", Material.DIAMOND_PICKAXE),
    FROSTBITE(WarlordsWeaponType.LEGENDARY, "Frostbite", Material.COD),
    ENDERFIST(WarlordsWeaponType.LEGENDARY, "Enderfist", Material.APPLE),
    RUBY_THORN(WarlordsWeaponType.LEGENDARY, "Ruby Thorn", Material.POISONOUS_POTATO),
    BROCCOMACE(WarlordsWeaponType.LEGENDARY, "Broccomace", Material.BAKED_POTATO);

    private final WarlordsWeaponType warlordsWeaponType;
    private final String title;
    private final Material material;

    WeaponSkin(final WarlordsWeaponType warlordsWeaponType,
        final String title,
        final Material material)
    {
        this.warlordsWeaponType = warlordsWeaponType;
        this.title = title;
        this.material = material;
    }

    public WarlordsWeaponType getWarlordsWeaponType()
    {
        return this.warlordsWeaponType;
    }

    public String getTitle()
    {
        return this.title;
    }

    public Material getMaterial()
    {
        return this.material;
    }

    public static ArrayList<WeaponSkin> getSkinsByType(final WarlordsWeaponType warlordsWeaponType)
    {
        return Arrays.stream(WeaponSkin.values()).filter(weaponSkin -> weaponSkin.warlordsWeaponType == warlordsWeaponType)
            .collect(Collectors.toCollection(ArrayList::new));
    }
}

