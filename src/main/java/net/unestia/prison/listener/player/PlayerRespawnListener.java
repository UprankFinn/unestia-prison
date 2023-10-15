package net.unestia.prison.listener.player;

import net.unestia.prison.Prison;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    private final Prison plugin;

    public PlayerRespawnListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {

        event.setRespawnLocation(this.plugin.getPositionManager().getPosition("spawn").getBukkitLocation());

    }

}
