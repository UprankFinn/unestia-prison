package net.unestia.prison.listener.block;

import net.unestia.prison.Prison;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private final Prison plugin;

    public BlockPlaceListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        if (event.getPlayer().getWorld().getName().equals("world")) {
            if (this.plugin.getMineManager().getMine(event.getBlock().getLocation()) == null && event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                event.setCancelled(true);
            }
        }
    }

}
