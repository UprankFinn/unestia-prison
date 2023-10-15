package net.unestia.prison.listener.player;

import net.unestia.api.UnestiaAPI;
import net.unestia.prison.Prison;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.Arrays;

public class PlayerChangedWorldListener implements Listener {

    private final Prison plugin;

    public PlayerChangedWorldListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
        this.plugin.getNpcManager().getNPCs().forEach(npc -> {
            UnestiaAPI.getInstance().getNPCManager().getNPC(npc.getName()).spawn(event.getPlayer());
            UnestiaAPI.getInstance().getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).sendHologram(UnestiaAPI.getInstance().getNPCManager().getNPC(npc.getName()).getLocation(), Arrays.asList("§a§lVerkäufer", "§7Verkaufe hier deine Items aus der Mine"));
        });
    }

}
