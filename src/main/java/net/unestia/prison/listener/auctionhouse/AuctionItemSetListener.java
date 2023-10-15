package net.unestia.prison.listener.auctionhouse;

import net.unestia.prison.Prison;
import net.unestia.prison.database.auctionhouse.Auction;
import net.unestia.prison.database.auctionhouse.item.AuctionItem;
import net.unestia.prison.events.auctionhouse.AuctionItemSetEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AuctionItemSetListener implements Listener {

    private final Prison plugin;

    public AuctionItemSetListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onAuctionItemSetEvent(AuctionItemSetEvent event) {

        if (event.getPlayer().isOnline()) {
            Player player = Bukkit.getPlayer(event.getPlayer().getUniqueId());

            player.sendMessage(Prison.PREFIX + "§aDu hast Erfolgreich eine Aktion Erstellt!");

            this.plugin.getActionManager().registerAuctions(new Auction(event.getPlayer().getUniqueId(), event.getPlayer().getPlayer().getName(),
                    new AuctionItem(player.getItemInHand().getType(), player.getItemInHand().getAmount(), player.getItemInHand().getItemMeta().getDisplayName(),
                            player.getItemInHand().getDurability(), player.getItemInHand().getEnchantments(), player.getItemInHand().getItemMeta().getItemFlags()), event.getPrice(), event.getDuration()));
        }


    }

}
