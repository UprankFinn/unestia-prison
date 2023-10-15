package net.unestia.prison.listener.entity;

import net.unestia.prison.Prison;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    private final Prison plugin;

    public EntityDamageByEntityListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {

        if (event.getEntity().equals(EntityType.ITEM_FRAME)) {
            event.setCancelled(true);
            return;

        }

    }

}
