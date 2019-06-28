package recreate.inventory;

import com.google.common.collect.Lists;
import net.minecraft.server.v1_14_R1.ChatMessageType;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import recreate.data.WarlordsPlayer;
import recreate.main.Warlords;
import recreate.util.ChatUtil;
import recreate.util.InventoryUtil;
import recreate.util.WeaponUtil;
import recreate.weapon.Weapon;
import recreate.weapon.WeaponSkin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryWeaponSkinInventory extends WarlordsInventory
{
    private final int page;

    public InventoryWeaponSkinInventory(final Player player,
        final int page)
    {
        super(generateInventory(player, page));
        this.page = page;
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
                final Weapon weapon = warlordsPlayer.getActiveWeapon();
                if (Objects.nonNull(weapon))
                {
                    final ArrayList<WeaponSkin> weaponSkins = Lists.newArrayList(WeaponSkin.values());
                    switch (slot)
                    {
                        case 48:
                            if (this.page > 0)
                            {
                                final WarlordsInventory skinBeforeInventory = InventoryUtil.getWeaponSkinInventory(player, this.page - 1);
                                Warlords.get().listWarlordsInventory.add(skinBeforeInventory);
                                player.openInventory(skinBeforeInventory.getMinecraftInventory());
                                break;
                            }
                            break;
                        case 49:
                            final WarlordsInventory smithInventory = InventoryUtil.getSmithInventory(player);
                            Warlords.get().listWarlordsInventory.add(smithInventory);
                            player.openInventory(smithInventory.getMinecraftInventory());
                            break;
                        case 50:
                            if (this.page < (weaponSkins.size() + 27) / 28)
                            {
                                final WarlordsInventory skinAfterInventory = InventoryUtil.getWeaponSkinInventory(player, this.page + 1);
                                Warlords.get().listWarlordsInventory.add(skinAfterInventory);
                                player.openInventory(skinAfterInventory.getMinecraftInventory());
                                break;
                            }
                            break;
                        default:

                            if (warlordsPlayer.getVoidShards() >= 5)
                            {
                                final List<WeaponSkin> weaponSkinsOfPage = weaponSkins.subList(this.page * 28, (this.page + 1) * 28 - 1);
                                final int skinIndex = slot - 10 * -(slot / 9 - 1) * 2;
                                final WeaponSkin weaponSkin = weaponSkinsOfPage.get(skinIndex);
                                weapon.setSkin(weaponSkin);
                                final String skinText = ChatColor.GRAY + "You received: ";
                                final PacketPlayOutChat packet =
                                    new PacketPlayOutChat(
                                        WeaponUtil.getChatBaseComponentForWeapon(weapon,
                                            WeaponUtil.generateWeaponItemStack(weapon, warlordsPlayer.getWarlordsClass()), skinText),
                                        ChatMessageType.CHAT);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                                warlordsPlayer.setVoidShards(warlordsPlayer.getVoidShards() - 5);
                            } else
                            {
                                ChatUtil.doNotEnoughVoidShardsMessage(player);
                            }
                            break;
                    }
                }
            } else
            {
                ChatUtil.doNoClassSelectedMessage(player);
            }
        }
    }

    private static Inventory generateInventory(final Player player,
        final int page)
    {
        final ArrayList<WeaponSkin> weaponSkins = Lists.newArrayList(WeaponSkin.values());
        final List<WeaponSkin> weaponSkinsOfPage = weaponSkins.subList(page * 28, (page + 1) * 28 - 1);
        final Inventory inventory = Bukkit.createInventory(player, 54, "WeaponSkin Selector");
        final WarlordsPlayer warlordsPlayer = Warlords.get().warlordsPlayerManager.getWarlordsPlayer(player.getUniqueId());
        for (int i = 0; i < weaponSkinsOfPage.size(); i++)
        {
            if (Objects.nonNull(warlordsPlayer))
            {
                final Weapon weapon = warlordsPlayer.getActiveWeapon();
                if (Objects.nonNull(weapon))
                {
                    final WeaponSkin weaponSkin = weaponSkinsOfPage.get(i);
                    final int slot = 10 + i % 7 + i / 7 * 9;
                    if (weapon.getWeaponType().ordinal() >= weaponSkin.getWarlordsWeaponType().ordinal())
                    {
                        final String price = ChatColor.LIGHT_PURPLE + "5 Void Shards";
                        final ItemStack itemStack = new ItemStack(weaponSkin.getMaterial());
                        final ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(weaponSkin.getWarlordsWeaponType().getChatColor() + weaponSkin.getTitle());
                        itemMeta.setUnbreakable(true);
                        itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
                        final ArrayList<String> lore = new ArrayList<>();
                        lore.add(ChatColor.GRAY + "Cost: " + price);
                        itemMeta.setLore(lore);
                        itemStack.setItemMeta(itemMeta);
                        inventory.setItem(slot, itemStack);
                    } else
                    {
                        final ItemStack itemStack = new ItemStack(weaponSkin.getWarlordsWeaponType().getGlassPane());
                        final ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(weaponSkin.getWarlordsWeaponType().getChatColor() + weaponSkin.getTitle());
                        itemMeta.setUnbreakable(true);
                        itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
                        final ArrayList<String> lore = new ArrayList<>();
                        lore.add(ChatColor.GRAY + "This skin is locked to a");
                        lore.add(ChatColor.GRAY + "weapon of " + weaponSkin.getWarlordsWeaponType().getChatColor() +
                            weaponSkin.getWarlordsWeaponType().name());
                        lore.add(ChatColor.GRAY + "category, or higher.");
                        itemMeta.setLore(lore);
                        itemStack.setItemMeta(itemMeta);
                        inventory.setItem(slot, itemStack);
                    }
                }
            }
        }
        if (page > 0)
        {
            final ItemStack itemStackBack = new ItemStack(Material.ARROW);
            final ItemMeta itemMetaBack = itemStackBack.getItemMeta();
            itemMetaBack.setDisplayName(ChatColor.GREEN + "Previous Page");
            itemStackBack.setItemMeta(itemMetaBack);
            inventory.setItem(48, itemStackBack);
        }
        if (page < (weaponSkins.size() + 27) / 28)
        {
            final ItemStack itemStackNext = new ItemStack(Material.ARROW);
            final ItemMeta itemMetaNext = itemStackNext.getItemMeta();
            itemMetaNext.setDisplayName(ChatColor.GREEN + "Next Page");
            itemStackNext.setItemMeta(itemMetaNext);
            inventory.setItem(50, itemStackNext);
        }
        inventory.setItem(49, InventoryUtil.getReturnArrow());
        return inventory;
    }
}
