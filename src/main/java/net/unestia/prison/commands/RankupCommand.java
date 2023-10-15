package net.unestia.prison.commands;

import net.unestia.prison.Prison;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankupCommand implements CommandExecutor {

    private final Prison plugin;

    public RankupCommand(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("rankup").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;
        net.unestia.prison.database.player.Player prisonPlayer = this.plugin.getPlayerManager().getPlayer(player.getUniqueId());

        this.plugin.getRankUpManager().rankUp(prisonPlayer);

        return false;
    }
}
