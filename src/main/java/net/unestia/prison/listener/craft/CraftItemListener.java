package net.unestia.prison.listener.craft;

import net.unestia.prison.Prison;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemFlag;

public class CraftItemListener implements Listener {

    private final Prison plugin;

    public CraftItemListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onCraftItemEvent(CraftItemEvent event) {
        event.getCurrentItem().getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

}
