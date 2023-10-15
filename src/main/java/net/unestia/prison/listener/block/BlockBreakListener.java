package net.unestia.prison.listener.block;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import net.unestia.api.UnestiaAPI;
import net.unestia.prison.Prison;
import net.unestia.prison.builder.ItemBuilder;
import net.unestia.prison.crates.type.CrateType;
import net.unestia.prison.database.player.Player;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockBreakListener implements Listener {

    private final Prison plugin;

    private final Gson gson;
    private final MongoCollection<Document> collection;

    public BlockBreakListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        this.gson = new GsonBuilder().create();
        this.collection = UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players");
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {

        Player player = this.gson.fromJson(this.gson.toJson(this.collection.find(Filters.eq("uniqueId", event.getPlayer().getUniqueId().toString())).first()), Player.class);

        if (event.getPlayer().getWorld().getName().equals("world")) {

            if (this.plugin.getMineManager().getMine(event.getBlock().getLocation()) == null && event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                event.setCancelled(true);
            } else if (this.plugin.getMineManager().getMine(event.getBlock().getLocation()) != null && event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                player.addMinedBlock(1);
                if (player.getMinedBlocks() % 1000 == 0) {
                    player.toPlayer().getInventory().addItem(new ItemBuilder(this.plugin.getCrateManager().getCrate("MINE_KEY").getCrateType().getMaterial()).setCrate("MINE_KEY", "MINE_KEY").setMetadata("MINE_KEY").build());
                    player.toPlayer().sendMessage(Prison.PREFIX + "§aDu hast ein Mine Key erhalten!");
                }

                if (player.getMinedBlocks() % 10000 == 0) {
                    player.toPlayer().getInventory().addItem(new ItemBuilder(this.plugin.getCrateManager().getCrate("CURRENT_KEY").getCrateType().getMaterial()).setCrate("CURRENT_KEY", "CURRENT_KEY").setMetadata("CURRENT_KEY").build());

                    player.toPlayer().sendMessage(Prison.PREFIX + "§aDu hast ein Mine Key erhalten!");
                }

                if (player.getMinedBlocks() % 50000 == 0) {

                }

                if (player.getMinedBlocks() % 100000 == 0) {

                }

                if (player.getMinedBlocks() % 500000 == 0) {
                    player.toPlayer().getInventory().addItem(new ItemBuilder(this.plugin.getCrateManager().getCrate("OP_KEY").getCrateType().getMaterial()).setCrate("OP_KEY", "OP_KEY").setMetadata("OP_KEY").build());

                    player.toPlayer().sendMessage(Prison.PREFIX + "§aDu hast ein OP Key erhalten!");
                }

                UnestiaAPI.getInstance().getScoreboardUtil().updateScoreboard(event.getPlayer(), 7, "§e» §7Blöcke: §f" + player.getMinedBlocks());

                ItemStack[] items = event.getBlock().getDrops().toArray(new ItemStack[0]);

                event.setExpToDrop(0);
                event.setDropItems(false);

                event.getPlayer().getInventory().addItem(items);

            }
        }

    }

}
