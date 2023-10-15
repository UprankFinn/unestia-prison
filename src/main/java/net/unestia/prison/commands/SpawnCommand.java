package net.unestia.prison.commands;

import net.unestia.prison.Prison;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final Prison plugin;

    public SpawnCommand(Prison prison) {
        this.plugin = prison;
        this.plugin.getCommand("spawn").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        player.teleport(this.plugin.getPositionManager().getPosition("spawn").getBukkitLocation());
        player.sendMessage(Prison.PREFIX + "§cDu hast dich erfolgreich zum Spawn teleportiert!");

        return false;
    }
}
