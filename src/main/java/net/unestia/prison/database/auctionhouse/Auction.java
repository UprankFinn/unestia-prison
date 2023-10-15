package net.unestia.prison.database.auctionhouse;

import net.unestia.prison.database.auctionhouse.item.AuctionItem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Auction {

    private final UUID uniqueId;
    private final String displayName;

    private final AuctionItem auctionItem;
    private final Integer price;

    private final Long duration;

    public Auction(UUID uniqueId, String displayName, AuctionItem auctionItem, Integer price, Long duration) {
        this.uniqueId = uniqueId;
        this.displayName = displayName;
        this.auctionItem = auctionItem;
        this.price = price;
        this.duration = duration;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public AuctionItem getAuctionItem() {
        return auctionItem;
    }

    public Integer getPrice() {
        return price;
    }

    public Long getDuration() {
        return duration;
    }

    public OfflinePlayer getOfflinePlayer() {
        UUID uniqueId = UUID.fromString(this.uniqueId.toString());
        return Bukkit.getOfflinePlayer(uniqueId);
    }

}
