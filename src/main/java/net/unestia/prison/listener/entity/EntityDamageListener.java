package net.unestia.prison.listener.entity;

import net.unestia.prison.Prison;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    private final Prison plugin;

    public EntityDamageListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {

        if (event.getEntity().equals(EntityType.ITEM_FRAME)) {
            event.setCancelled(true);
            return;
        }

        if (!(event.getEntity().getWorld().getName().equals("arena"))) {
            event.setCancelled(true);
            return;
        }

    }

}
