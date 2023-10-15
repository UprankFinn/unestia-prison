package net.unestia.prison.builder.kits.type;

import net.unestia.prison.builder.ItemBuilder;
import net.unestia.prison.builder.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class DefaultKit extends Kit {

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public Timestamp getRemainingTime() {
        return new Timestamp(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
    }

    @Override
    public ItemStack getItemStack() {
        return new ItemStack(Material.IRON_SWORD, 1);
    }

    @Override
    public Inventory getContent() {

        Inventory inventory = Bukkit.createInventory(null, 36);

        inventory.setItem(0, new ItemBuilder(Material.IRON_SWORD, 1, (byte) 0).setDisplayName("§8[§b§lStarter Sword§8]").build());
        inventory.setItem(1, new ItemBuilder(Material.IRON_PICKAXE, 1, (byte) 0).setDisplayName("§8[§b§lStarter Pickaxe§8]").build());
        inventory.setItem(2, new ItemBuilder(Material.IRON_AXE, 1, (byte) 0).setDisplayName("§8[§b§lStarter Axe§8]").build());
        inventory.setItem(3, new ItemBuilder(Material.IRON_SHOVEL, 1, (byte) 0).setDisplayName("§8[§b§lStarter Shovel§8]").build());

        return inventory;
    }

    @Override
    public ItemStack[] getArmorContent() {
        return null;
    }

}
