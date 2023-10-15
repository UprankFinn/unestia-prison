package net.unestia.prison.listener.npc;

import net.unestia.event.npc.NPCInteractEvent;
import net.unestia.prison.Prison;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCInteractListener implements Listener {

    private final Prison plugin;

    public NPCInteractListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onNPCInteractEvent(NPCInteractEvent event) {

        if (event.getNPC().getGameProfile().getName().equals("npc_H")) {
            this.plugin.getSellBuilder().sellItems(event.getNPC(), event.getPlayer().toPlayer());
        }
    }

}
