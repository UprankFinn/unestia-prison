package net.unestia.prison.commands;

import net.unestia.prison.Prison;
import net.unestia.prison.database.player.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TokensCommand implements CommandExecutor {

    private final Prison plugin;

    private final PlayerManager playerManager;

    public TokensCommand(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("tokens").setExecutor(this);

        this.playerManager = this.plugin.getPlayerManager();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        //TODO: MESSAGE
        commandSender.sendMessage(Prison.PREFIX + "§aDu hast aktuell " + this.playerManager.getPlayer(player.getUniqueId()).getTokens() + " Tokens!");

        return false;
    }
}
