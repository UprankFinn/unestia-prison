package net.unestia.prison.commands;

import net.unestia.prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class UpgradeCommand implements CommandExecutor {

    private final Prison plugin;

    public UpgradeCommand(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("upgrade").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;


        if (player.getItemInHand() != null && player.getItemInHand().getItemMeta() != null) {

            this.plugin.getUpgradeBuilder().genInventory(player);

        } else {
            //TODO: MESSAGE
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            player.sendMessage(Prison.PREFIX + "§cDu hast kein Aufwertbares Item in deiner Hand!");
        }

        return false;
    }
}
