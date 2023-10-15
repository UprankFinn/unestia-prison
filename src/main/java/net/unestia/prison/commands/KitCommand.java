package net.unestia.prison.commands;

import net.unestia.prison.Prison;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

    private final Prison plugin;

    public KitCommand(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("kit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player) commandSender;

        this.plugin.getKitBuilder().createInventory(player);

        return false;
    }
}
