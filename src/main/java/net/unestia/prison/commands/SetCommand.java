package net.unestia.prison.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import net.unestia.api.UnestiaAPI;
import net.unestia.prison.Prison;
import net.unestia.prison.database.barrier.Barrier;
import net.unestia.prison.database.npc.NPC;
import net.unestia.prison.database.position.Position;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class SetCommand implements CommandExecutor {

    private final Prison plugin;

    private final Gson gson;
    private final MongoCollection<Document> collection;

    public SetCommand(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("set").setExecutor(this);

        this.gson = new GsonBuilder().create();
        this.collection = UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("locations");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("spawn")) {
                if (this.collection.find(Filters.eq("name", "spawn")).first() == null) {
                    this.plugin.getPositionManager().createPosition(new Position("spawn", player.getLocation().getWorld().getName(),
                            player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(),
                            player.getLocation().getYaw(), player.getLocation().getPitch()));
                }

                Position position = this.gson.fromJson(this.gson.toJson(this.collection.find(Filters.eq("name", "spawn")).first()), Position.class);
                this.plugin.getPositionManager().addPositionToCach("spawn", position);
                player.sendMessage(Prison.PREFIX + "§aDu hast den Spawn erfolgreich gesetzt!");
            }
        } else if (args.length == 2) {

            if (args[0].equalsIgnoreCase("barrier")) {

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

                this.plugin.getBarrierManager().registerBarrier(new Barrier(args[1], new Barrier.Cuboid(minimumPoint.getBlockX(), maximumPoint.getBlockX(), minimumPoint.getBlockY(), maximumPoint.getBlockY(), minimumPoint.getBlockZ(), maximumPoint.getBlockZ())));

                player.sendMessage(Prison.PREFIX + "§aDu hast erfolgreich eine Barrier für eine Mine erstellt!");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);

            }

            if (args[0].equalsIgnoreCase("npc")) {
                this.plugin.getNpcManager().createNPC(args[0],
                        new NPC.Location(player.getLocation().getWorld().getName(),
                        player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
                Villager villager = (Villager) Bukkit.getWorld(player.getLocation().getWorld().getName()).spawnEntity(player.getLocation(), EntityType.VILLAGER);

                villager.setCustomNameVisible(true);
                villager.setCustomName("§a§lVerkaufe deine Items hier!");

                villager.setVillagerType(Villager.Type.PLAINS);
                villager.setAI(false);
                villager.setAdult();
                villager.setCollidable(false);
                villager.setGravity(false);
                villager.setInvisible(false);
                villager.setProfession(Villager.Profession.FARMER);
                player.sendMessage(Prison.PREFIX + "§aDu hast erfolgreich ein NPC gesetzt!");
            }
        }

        return false;
    }
}
