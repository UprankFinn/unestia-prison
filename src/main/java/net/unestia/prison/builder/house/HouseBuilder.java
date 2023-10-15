package net.unestia.prison.builder.house;

import net.unestia.api.UnestiaAPI;
import net.unestia.prison.builder.ItemBuilder;
import net.unestia.prison.database.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class HouseBuilder {

    private final Inventory inventory = Bukkit.createInventory(null, 9 * 3, "§6Verwaltung");

    private final Inventory housesInventory = Bukkit.createInventory(null, 9 * 3, "§6Deine Häuser");

    public HouseBuilder() {
        this.inventory.setItem(9, new ItemBuilder(Material.EMERALD, 1, (byte) 0).setDisplayName("§6Deine Häuser").build());
        this.inventory.setItem(10, new ItemBuilder(Material.ENCHANTED_BOOK, 1, (byte) 0).setDisplayName("§eWerte eines deiner Häuser auf...").build());
    }


    public void openFirstInventory(Player player) {
        org.bukkit.entity.Player bukkitPlayer = player.toPlayer();
        bukkitPlayer.openInventory(this.inventory);
    }

    public void openHousesInventory(Player player) {
        org.bukkit.entity.Player bukkitPlayer = player.toPlayer();

        UnestiaAPI.getInstance().getPlotManager().getPlots(player.getUniqueId()).forEach(plot -> {
            this.housesInventory.addItem(new ItemBuilder(Material.EMERALD, 1, (byte) 0).setDisplayName(plot.getId().toString()).build());
        });

        this.housesInventory.setItem(26, new ItemBuilder(Material.BARRIER, 1, (byte) 0).setDisplayName("§cZurück").build());

        bukkitPlayer.openInventory(this.housesInventory);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Inventory getHousesInventory() {
        return this.housesInventory;
    }
}
