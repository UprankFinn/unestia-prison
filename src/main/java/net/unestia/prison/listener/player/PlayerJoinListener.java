package net.unestia.prison.listener.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import net.unestia.api.UnestiaAPI;
import net.unestia.prison.Prison;
import net.unestia.prison.builder.ItemBuilder;
import net.unestia.prison.database.player.Player;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PlayerJoinListener implements Listener {

    private final Prison plugin;

    private final List<ItemStack> items = new ArrayList<>();

    private final Gson gson;
    private final MongoCollection<Document> collection;

    public PlayerJoinListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        this.gson = new GsonBuilder().create();
        this.collection = UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players");

        this.items.add(new ItemBuilder(Material.STONE_SWORD, 1, (byte) 0).build());
        this.items.add(new ItemBuilder(Material.STONE_PICKAXE, 1, (byte) 0).build());
        this.items.add(new ItemBuilder(Material.STONE_AXE, 1, (byte) 0).build());
        this.items.add(new ItemBuilder(Material.STONE_SHOVEL, 1, (byte) 0).build());
        this.items.add(new ItemBuilder(Material.COOKED_BEEF, 32, (byte) 0).build());

    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        //TODO: MESSAGE
        event.setJoinMessage(Prison.PREFIX + "§6" + event.getPlayer().getName() + " §7hat das Spiel betreten!");

        if (this.collection.find(Filters.eq("uniqueId", event.getPlayer().getUniqueId().toString())).first() == null) {
            this.plugin.getPlayerManager().createPlayer(new Player(event.getPlayer().getUniqueId(), event.getPlayer().getName(), 0, 0, "A", 0, 0, Arrays.asList("A")));
        }

        Player player = this.gson.fromJson(this.gson.toJson(this.collection.find(Filters.eq("uniqueId", event.getPlayer().getUniqueId().toString())).first()), Player.class);
        //if(player.getDisplayName() != event.getPlayer().getName()) player.setDisplayName(event.getPlayer().getName());
        this.plugin.getPlayerManager().addPlayerToCach(player);

        Map<Integer, String> scoreboard = new HashMap<>();

        scoreboard.put(11, "§e");
        scoreboard.put(10, "§eInformation:");
        scoreboard.put(9, "§e» §7Money: §f" + UnestiaAPI.getInstance().getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).getBalance("opprison"));
        scoreboard.put(8, "§e» §7Tokens: §f" + player.getTokens());
        scoreboard.put(7, "§e» §7Blöcke: §f" + player.getMinedBlocks());
        scoreboard.put(6, "§f");
        scoreboard.put(5, "§eKlasse:");
        scoreboard.put(4, "§e» §7Mine: §f" + player.getMine());
        scoreboard.put(3, "§e» §7Prestige: §f" + player.getPrestige());
        scoreboard.put(2, "§e» §7Rebirth: §f" + player.getRebirth());
        scoreboard.put(1, "§a");
        scoreboard.put(0, "§6mc.unestia.net");

        UnestiaAPI.getInstance().getScoreboardUtil().createScoreboard(event.getPlayer(), scoreboard);

        this.plugin.getNpcManager().getNPCs().forEach(npc -> {
            UnestiaAPI.getInstance().getNPCManager().getNPC(npc.getName()).spawn(event.getPlayer());
            UnestiaAPI.getInstance().getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).sendHologram(UnestiaAPI.getInstance().getNPCManager().getNPC(npc.getName()).getLocation(), Arrays.asList("§a§lVerkäufer", "§7Verkaufe hier deine Items aus der Mine"));
        });

        event.getPlayer().setHealth(20);
        event.getPlayer().setHealthScale(20);
        event.getPlayer().setFoodLevel(20);
        event.getPlayer().setGameMode(GameMode.SURVIVAL);

        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> event.getPlayer().teleport(this.plugin.getPositionManager().getPosition("spawn").getBukkitLocation()), 5L);

    }
}
