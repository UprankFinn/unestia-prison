package net.unestia.prison.commands;

import net.unestia.prison.Prison;
import net.unestia.prison.builder.ItemBuilder;
import net.unestia.prison.crates.type.CrateType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrateCommand implements CommandExecutor {

    private final Prison plugin;

    public CrateCommand(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("crate").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) return false;


        final Player player = (Player) commandSender;

        if (args.length == 0) {
            //TODO: MESSAGE
            player.sendMessage(Prison.PREFIX + "§7usage: give <name> <type> <amount>");
            player.sendMessage(Prison.PREFIX + "§7usage: list (list of types of crates)");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                this.plugin.getCrateManager().getCrates().forEach((name, crate) -> player.sendMessage(Prison.PREFIX + name));
            }
        } else if (args.length == 4) {
            if (this.plugin.getCrateManager().getCrate(args[2]) == null) return false;
            if (args[0].equalsIgnoreCase("give")) {
                if (args[2].equalsIgnoreCase("MINE_KEY") || args[2].equalsIgnoreCase("VOTE_KEY")
                        || args[2].equalsIgnoreCase("PRESTIGE_KEY") || args[2].equalsIgnoreCase("BEAST_KEY")
                        || args[2].equalsIgnoreCase("GNOME_KEY") || args[2].equalsIgnoreCase("DRAGON_KEY")
                        || args[2].equalsIgnoreCase("ZOMBIE_KEY") || args[2].equalsIgnoreCase("VAMPIRE_KEY")
                        || args[2].equalsIgnoreCase("CURRENT_KEY") || args[2].equalsIgnoreCase("OP_KEY")
                        || args[2].equalsIgnoreCase("GOLD_KEY")) {

                    Player target = Bukkit.getPlayer(args[1]);

                    if (target != null) {
                        for (int i = 0; i < Integer.parseInt(args[3]); i++) {
                            target.getInventory().addItem(new ItemBuilder(this.plugin.getCrateManager().getCrate(args[2]).getCrateType().getMaterial()).setCrate(args[2], args[3]).setMetadata(args[2]).build());
                        }
                        if (args[3].equals("1")) {
                            //TODO: MESSAGE
                            player.sendMessage(Prison.PREFIX + "§aDu hast dem Spieler " + target.getName() + " ein " + args[2].replace("_KEY", "") + " Key gegeben!");
                        } else {
                            //TODO: MESSAGE
                            player.sendMessage(Prison.PREFIX + "§aDu hast dem Spieler " + target.getName() + " " + args[3] + " " + args[2].replace("_KEY", "") + " Keys gegeben!");
                        }
                    } else {
                        //TODO: MESSAGE
                        player.sendMessage(Prison.PREFIX + "§cDer Spieler ist nicht online!");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                    }

                } else {
                    //TODO: MESSAGE
                    player.sendMessage(Prison.PREFIX + "§7usage: give <name> <type> <amount>");
                    player.sendMessage(Prison.PREFIX + "§7usage: list (list of types of crates)");
                }
            }
        } else {
            //TODO: MESSAGE
            player.sendMessage(Prison.PREFIX + "§7usage: give <name> <type> <amount>");
            player.sendMessage(Prison.PREFIX + "§7usage: list (list of types of crates)");
        }

        return false;
    }
}
