package net.unestia.prison.listener.block;

import net.unestia.prison.Prison;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class BlockPhysicsListener implements Listener {

    private final Prison plugin;

    public BlockPhysicsListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onBlockPhysicEvent(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }

}
