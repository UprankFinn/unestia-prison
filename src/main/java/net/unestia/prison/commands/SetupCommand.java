package net.unestia.prison.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import net.unestia.prison.Prison;
import net.unestia.prison.database.mine.Mine;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetupCommand implements CommandExecutor {

    private final Prison plugin;

    public SetupCommand(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("setup").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;
        if (args.length < 3) {
            player.sendMessage("§cDU KEK");
            return false;
        }

        BlockVector3 minimumPoint;
        BlockVector3 maximumPoint;

        WorldEditPlugin worldEditPlugin = WorldEditPlugin.getPlugin(WorldEditPlugin.class);
        try {
            Region region = worldEditPlugin.getSession(player).getSelection(worldEditPlugin.getSession(player).getSelectionWorld());
            minimumPoint = region.getMinimumPoint();
            maximumPoint = region.getMaximumPoint();
        } catch (IncompleteRegionException exception) {
            exception.printStackTrace();
            return false;
        }

        if (minimumPoint == null || maximumPoint == null) {
            return false;
        }

        if (!(this.exists(args[1]))) {
            return false;
        }

        List<Material> materials = new ArrayList<>();
        for (int i = 2; i < args.length; i++) {
            if (!(this.exists(args[i]))) return false;
            materials.add(Material.valueOf(args[i].toUpperCase()));
        }

        this.plugin.getMineManager().registerMine(new Mine(args[0], new Mine.Location(
                player.getLocation().getWorld().getName(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()
        ), Material.valueOf(args[1].toUpperCase()), new HashMap<>(), materials, new Mine.Cuboid(minimumPoint.getBlockX(), maximumPoint.getBlockX(), minimumPoint.getBlockY(), maximumPoint.getBlockY(), minimumPoint.getBlockZ(), maximumPoint.getBlockZ())));

        player.sendMessage(Prison.PREFIX + "§aEine Blockmine wurde erstellt!");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);

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
