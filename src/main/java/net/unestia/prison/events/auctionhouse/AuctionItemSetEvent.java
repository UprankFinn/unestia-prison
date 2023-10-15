package net.unestia.prison.events.auctionhouse;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class AuctionItemSetEvent extends Event {

    public static HandlerList handlerList = new HandlerList();

    private final OfflinePlayer player;
    private final ItemStack itemStack;
    private final Integer price;
    private final Long duration;

    public AuctionItemSetEvent(OfflinePlayer player, ItemStack itemStack, Integer price, Long duration) {
        this.player = player;
        this.itemStack = itemStack;
        this.price = price;
        this.duration = duration;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Integer getPrice() {
        return price;
    }

    public Long getDuration() {
        return duration;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
