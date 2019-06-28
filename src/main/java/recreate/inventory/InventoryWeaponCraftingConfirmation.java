package recreate.inventory;

import net.minecraft.server.v1_14_R1.ChatMessageType;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import recreate.classes.WarlordsClass;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.util.ChatUtil;
import recreate.util.InventoryUtil;
import recreate.util.WeaponUtil;
import recreate.weapon.WarlordsWeaponType;
import recreate.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryWeaponCraftingConfirmation extends WarlordsInventory
{
    private final WarlordsWeaponType warlordsWeaponType;

    public InventoryWeaponCraftingConfirmation(final Player player,
        final WarlordsWeaponType warlordsWeaponType)
    {
        super(generateInventory(player, warlordsWeaponType));
        this.warlordsWeaponType = warlordsWeaponType;
    }

    @Override
    public void handleSlotClick(final int slot,
        final ClickType click)
    {
        final Inventory inventory = this.getMinecraftInventory();
        if (Objects.nonNull(inventory.getItem(slot)))
        {
            final Player player = (Player) inventory.getHolder();
            if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
            {
                final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
                switch (slot)
                {
                    case 11:
                        int cost = 0;
                        switch (this.warlordsWeaponType)
                        {
                            case LEGENDARY:
                                cost = 100;
                                break;
                            case EPIC:
                                cost = 25;
                                break;
                            case RARE:
                                cost = 5;
                                break;
                            default:
                                break;
                        }
                        if (warlordsPlayer.getVoidShards() >= cost)
                        {
                            warlordsPlayer.setVoidShards(warlordsPlayer.getVoidShards() - cost);
                            final Weapon weapon =
                                WeaponUtil.craftWeapon(this.warlordsWeaponType, warlordsPlayer.getWarlordsClass().getType());
                            final ItemStack itemStack = WeaponUtil.generateWeaponItemStack(weapon, warlordsPlayer.getWarlordsClass());
                            final String resultText = ChatColor.GRAY + player.getDisplayName() + " crafted a";
                            final PacketPlayOutChat packet =
                                new PacketPlayOutChat(
                                    WeaponUtil
                                        .getChatBaseComponentForWeapon(weapon, itemStack, resultText),
                                    ChatMessageType.CHAT);
                            switch (weapon.getWeaponType())
                            {
                                case LEGENDARY:
                                    for (final Player p : Bukkit.getOnlinePlayers())
                                    {
                                        player.playSound(player.getLocation(), "legendaryfind", 1f, 1f);
                                        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                                    }
                                    break;
                                case EPIC:
                                    for (final Player p : Bukkit.getOnlinePlayers())
                                    {
                                        player.playSound(player.getLocation(), "epicfind", 1f, 1f);
                                        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                                    }
                                    break;
                                default:
                                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                                    break;
                            }
                            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                            final ArrayList<Weapon> weaponList = warlordsPlayer.getWeaponInventory();
                            weaponList.add(weapon);
                        } else
                        {
                            ChatUtil.doNotEnoughVoidShardsMessage(player);
                        }
                        break;
                    case 15:
                        final WarlordsInventory craftingInventory = InventoryUtil.getCraftingInventory(player);
                        Warlords.get().listWarlordsInventory.add(craftingInventory);
                        player.openInventory(craftingInventory.getMinecraftInventory());
                        break;
                    default:
                        break;
                }
            } else
            {
                ChatUtil.doNoClassSelectedMessage(player);
            }
        }

    }

    private static Inventory generateInventory(final Player player,
        final WarlordsWeaponType warlordsWeaponType)
    {
        final Inventory inventory = Bukkit.createInventory(player, 27, "Craft Weapon?");
        final ItemStack itemStackYes = new ItemStack(Material.GREEN_CONCRETE);
        final ItemMeta itemMetaYes = itemStackYes.getItemMeta();
        itemMetaYes.setDisplayName(ChatColor.GREEN + "Confirm");
        final List<String> loreYes = new ArrayList<>();
        if (Warlords.get().warlordsPlayerManager.doValidChecks(player.getUniqueId()))
        {
            final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
            final WarlordsClass warlordsClass = warlordsPlayer.getWarlordsClass();
            switch (warlordsWeaponType)
            {
                case LEGENDARY:
                    loreYes.add(ChatColor.GRAY + "Creates a " + ChatColor.GOLD + "Legendary " + ChatColor.GRAY
                        + "weapon with a");
                    loreYes.add(ChatColor.GRAY + "skill bonus for " + warlordsClass.getType().getTitle() + ChatColor.GRAY + ".");
                    loreYes.add(ChatColor.GRAY + "Crafted Legendary Weapons can be");
                    loreYes.add(ChatColor.GRAY + "upgraded " + ChatColor.GREEN + "4 " + ChatColor.GRAY
                        + "times using the Void");
                    loreYes.add(ChatColor.GRAY + "");
                    loreYes.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "100 Void Shards");
                    break;
                case EPIC:
                    loreYes.add(ChatColor.GRAY + "Creates a " + ChatColor.DARK_PURPLE + "Epic " + ChatColor.GRAY
                        + "weapon with a");
                    loreYes.add(ChatColor.GRAY + "skill bonus for " + warlordsClass.getType().getTitle() + ChatColor.GRAY + ".");
                    loreYes.add(ChatColor.GRAY + "Crafted Epic Weapons can be");
                    loreYes.add(ChatColor.GRAY + "upgraded " + ChatColor.GREEN + "2 " + ChatColor.GRAY
                        + "times using the Magic");
                    loreYes.add(ChatColor.GRAY + "Forging feature.");
                    loreYes.add(ChatColor.GRAY + "");
                    loreYes.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "25 Void Shards");
                    break;
                case RARE:
                    loreYes.add(ChatColor.GRAY + "Creates a " + ChatColor.BLUE + "Rare " + ChatColor.GRAY + "weapon with a");
                    loreYes.add(ChatColor.GRAY + "skill bonus for " + warlordsClass.getType().getTitle() + ChatColor.GRAY + ".");
                    loreYes.add(ChatColor.GRAY + "");
                    loreYes.add(ChatColor.GRAY + "Cost: " + ChatColor.LIGHT_PURPLE + "5 Void Shards");
                    break;
                default:
                    break;
            }

        }
        itemMetaYes.setLore(loreYes);
        itemStackYes.setItemMeta(itemMetaYes);
        final ItemStack itemStackNo = new ItemStack(Material.RED_CONCRETE);
        final ItemMeta itemMetaNo = itemStackNo.getItemMeta();
        itemMetaNo.setDisplayName(ChatColor.RED + "Deny");
        final List<String> loreNo = new ArrayList<>();
        loreNo.add(ChatColor.GRAY + "Go back to the previous");
        loreNo.add(ChatColor.GRAY + "menu.");
        itemMetaNo.setLore(loreNo);
        itemStackNo.setItemMeta(itemMetaNo);
        inventory.setItem(11, itemStackYes);
        inventory.setItem(15, itemStackNo);
        return inventory;
    }
}
