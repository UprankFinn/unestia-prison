package net.unestia.prison.commands;

import net.unestia.prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class AhCommand implements CommandExecutor {

    private final Prison plugin;

    public AhCommand(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("ah").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player) commandSender;

        Inventory inventory = Bukkit.createInventory(null, 6*9, "§cAuktionshaus");

        this.plugin.getActionManager().getAuctions().forEach((auction -> inventory.addItem(auction.getAuctionItem().toItemStack())));

        player.openInventory(inventory);

        return false;
    }
}
