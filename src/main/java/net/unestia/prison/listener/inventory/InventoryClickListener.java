package net.unestia.prison.listener.inventory;

import net.unestia.api.UnestiaAPI;
import net.unestia.prison.Prison;
import net.unestia.prison.builder.kits.Kit;
import net.unestia.prison.database.player.Player;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Objects;

public class InventoryClickListener implements Listener {

    private final Prison plugin;

    public InventoryClickListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {

        org.bukkit.entity.Player player = (org.bukkit.entity.Player) event.getWhoClicked();

        if (event.getCurrentItem() == null) return;
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().equals(event.getWhoClicked().getInventory())) return;

        //<----[House Administration Inventory]---->
        if (event.getClickedInventory().equals(this.plugin.getHouseBuilder().getInventory())) {
            event.setCancelled(true);

            if (event.getCurrentItem().getType().equals(Material.EMERALD)) {
                Player player1 = this.plugin.getPlayerManager().getPlayer(event.getWhoClicked().getUniqueId());
                this.plugin.getHouseBuilder().openHousesInventory(player1);
            }

            //<----[House Inventory]---->
        } else if (event.getClickedInventory().equals(this.plugin.getHouseBuilder().getHousesInventory())) {
            event.setCancelled(true);
            if (event.getSlot() == 26) {
                Player player1 = this.plugin.getPlayerManager().getPlayer(event.getWhoClicked().getUniqueId());
                this.plugin.getHouseBuilder().openFirstInventory(player1);
            }
            //<----[Upgrade Inventory]---->
        } else if (event.getClickedInventory().equals(this.plugin.getUpgradeBuilder().getInventory())) {
            event.setCancelled(true);

            if (event.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK)) {

                String currentItem = Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().replace("§8» ", "");

                if (player.getItemInHand().getEnchantmentLevel(Objects.requireNonNull(Enchantment.getByName(currentItem))) == 0) {
                    this.plugin.getUpgradeBuilder().upgrade(player, player.getItemInHand(), currentItem, 1);

                } else {
                    this.plugin.getUpgradeBuilder().upgrade(player, player.getItemInHand(), currentItem, (
                            player.getItemInHand().getEnchantmentLevel(Objects.requireNonNull(Enchantment.getByName(currentItem))) + 1));
                }

                player.sendMessage(Prison.PREFIX + "§aDu hast ein Item erfolgreich Verzaubert!");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);

            }

        } else if (event.getClickedInventory().equals(this.plugin.getKitBuilder().getInventory())) {
            event.setCancelled(true);

            String currentItem = Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().replace("§8» §e", "");

            Kit kit = this.plugin.getKitBuilder().getKit(currentItem);

            //TODO: add Contents to inventory

            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            player.sendMessage(Prison.PREFIX + "§aDu hast das Kit " + currentItem + " ausgewählt!");

        }

    }

}
