package net.unestia.prison.commands;

import net.unestia.prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvPCommand implements CommandExecutor {

    private final Prison plugin;

    public PvPCommand(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("pvp").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if (Bukkit.getWorld("arena") == null) {
            player.sendMessage(Prison.PREFIX + "§cDie Arena ist derzeit nicht verfügbar!");
            return false;
        }

        player.teleport(new Location(Bukkit.getWorld("arena"), 0.5, 65, 0.5, 0, 0));
        player.sendMessage(Prison.PREFIX + "§aDu hast dich in die PvP Arena teleportiert!");

        return false;
    }
}
