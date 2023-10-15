package net.unestia.prison.listener.auctionhouse;

import net.unestia.api.UnestiaAPI;
import net.unestia.prison.Prison;
import net.unestia.prison.events.auctionhouse.AuctionItemBuyEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AuctionItemBuyListener implements Listener {

    private final Prison plugin;

    public AuctionItemBuyListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onAuctionItemBuyEvent(AuctionItemBuyEvent event) {
        if (event.getBuyer().isOnline()) {
            Player player = Bukkit.getPlayer(event.getBuyer().getUniqueId());
            player.getInventory().addItem(event.getItemStack());

            if (UnestiaAPI.getInstance().getPlayerManager().getPlayer(player.getUniqueId()) == null) return;


            if (UnestiaAPI.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getBalance("opprison") >= event.getPrice()) {
                player.sendMessage(Prison.PREFIX + "§cDu hast nicht genügend Geld um das Item zu kaufen!");
                return;
            }

            player.sendMessage(Prison.PREFIX + "§aDu hast Erfolgreich ein Item für " + event.getPrice() + " gekauft!");

        }

    }

}
