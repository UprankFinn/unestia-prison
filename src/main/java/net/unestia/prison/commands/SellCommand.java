package net.unestia.prison.commands;

import net.unestia.prison.Prison;
import net.unestia.prison.events.auctionhouse.AuctionItemSetEvent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class SellCommand implements CommandExecutor {

    private final Prison plugin;

    public SellCommand(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("sell").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        /*if (!(args.length == 1)) {
            player.sendMessage(Prison.PREFIX + "§7usage: sell <price>");
            return false;
        }

        if (!(StringUtils.isNumeric(args[0]))) {
            player.sendMessage(Prison.PREFIX + "§cDer angegebene Paramenter ist keine Gültige Zahl");
            return false;
        }

        this.plugin.getServer().getPluginManager().callEvent(new AuctionItemSetEvent(player, player.getItemInHand(), Integer.valueOf(args[0]), (System.currentTimeMillis() + TimeUnit.DAYS.toMillis(5))));
*/
        return false;
    }
}
