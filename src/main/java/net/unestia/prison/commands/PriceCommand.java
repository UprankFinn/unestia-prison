package net.unestia.prison.commands;

import net.unestia.prison.Prison;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PriceCommand implements CommandExecutor {

    private final Prison plugin;

    public PriceCommand(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("price").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player) commandSender;

        if (args.length != 3) {
            return false;
        }

        if (this.plugin.getMineManager().getMine(args[0]) == null) {
            player.sendMessage("Du stinkst!");
            return false;
        }

        if (!(this.exists(args[1]))) {
            player.sendMessage("Du stinkst! #2");
            return false;
        }

        if (!(StringUtils.isNumeric(args[2]))) {
            player.sendMessage("Du stinkst! #3");
            return false;
        }

        this.plugin.getMineManager().getMine(args[0]).addPrice(Material.valueOf(args[1].toUpperCase()), Integer.valueOf(args[2]));

        return false;
    }

    private boolean exists(String name) {
        for (Material material : Material.values()) {
            if (material.name().equals(name.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

}
