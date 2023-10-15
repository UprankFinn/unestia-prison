package net.unestia.prison.database.auctionhouse.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.Set;

public class AuctionItem {

    private final Material material;
    private final Integer items;

    private final String name;
    private final short durability;

    private final Map<Enchantment, Integer> enchantments;
    private final Set<ItemFlag> itemFlags;


    public AuctionItem(Material material, Integer items, String name, short durability, Map<Enchantment, Integer> enchantments, Set<ItemFlag> itemFlags) {
        this.material = material;
        this.items = items;
        this.name = name;
        this.durability = durability;
        this.enchantments = enchantments;
        this.itemFlags = itemFlags;
    }

    public Material getMaterial() {
        return material;
    }

    public Integer getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public short getDurability() {
        return durability;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public Set<ItemFlag> getItemFlags() {
        return itemFlags;
    }

    public ItemStack toItemStack(){
        ItemStack itemStack = new ItemStack(this.material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(this.name);

        itemStack.setDurability(this.durability);

        this.enchantments.forEach(((enchantment, integer) -> itemMeta.addEnchant(enchantment, integer, true)));

        this.itemFlags.forEach((itemFlag -> itemMeta.addItemFlags(itemFlag)));



        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }



}
