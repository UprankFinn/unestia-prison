package net.unestia.prison.listener.player;

import net.unestia.api.UnestiaAPI;
import net.unestia.api.player.PlayerManager;
import net.unestia.api.rank.RankManager;
import net.unestia.prison.Prison;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final Prison plugin;

    public PlayerQuitListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerQuitEvent event) {
        event.setQuitMessage(Prison.PREFIX + "§6" + event.getPlayer().getName() + " §7hat den Server verlassen!");
        this.plugin.getPlayerManager().removePlayerFromCach(event.getPlayer().getUniqueId());
    }

}
