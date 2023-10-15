package net.unestia.prison.builder.kits;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;

public abstract class Kit {

    public abstract String getName();
    public abstract Timestamp getRemainingTime();

    public abstract ItemStack getItemStack();

    public abstract Inventory getContent();
    public abstract ItemStack[] getArmorContent();

}
