package net.unestia.prison.events.auctionhouse;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class AuctionItemBuyEvent extends Event {

    public static HandlerList handlerList = new HandlerList();

    private final OfflinePlayer player;
    private final OfflinePlayer buyer;

    private final ItemStack itemStack;
    private final Integer price;

    public AuctionItemBuyEvent(OfflinePlayer player, OfflinePlayer buyer, ItemStack itemStack, Integer price) {
        this.player = player;
        this.buyer = buyer;
        this.itemStack = itemStack;
        this.price = price;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public OfflinePlayer getBuyer() {
        return buyer;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
