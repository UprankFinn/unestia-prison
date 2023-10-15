package net.unestia.prison.listener.enchant;

import net.unestia.prison.Prison;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemFlag;

public class EnchantItemListener implements Listener {

    private final Prison plugin;

    public EnchantItemListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onEnchantItemEvent(EnchantItemEvent event) {
        event.getItem().getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

}
