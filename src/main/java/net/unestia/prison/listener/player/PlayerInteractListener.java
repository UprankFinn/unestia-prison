package net.unestia.prison.listener.player;

import net.unestia.api.UnestiaAPI;
import net.unestia.prison.Prison;
import net.unestia.prison.builder.ItemBuilder;
import net.unestia.prison.crates.Crate;
import net.unestia.prison.crates.type.CrateType;
import net.unestia.prison.database.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PlayerInteractListener implements Listener {

    private final Prison plugin;

    public PlayerInteractListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {

        Player player = this.plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId());

        Location location = new Location(Bukkit.getWorld("world"), -12, 65, -6);

        Block block = event.getClickedBlock();
        if (block == null) return;
        if (block.getType() == null) return;
        if (block.getLocation() == null) return;

        if (block.getType().equals(Material.CHEST) && block.getLocation().equals(location)) {
            ItemStack itemStack = event.getItem();
            event.setCancelled(true);

            if (itemStack == null) return;

            if (itemStack.getItemMeta().getLocalizedName() == null) {
                player.toPlayer().sendMessage(Prison.PREFIX + "§cDu hast kein Gültigen Key in der Hand!");
                return;
            }

            Crate openedCrate = this.plugin.getCrateManager().getCrate(Objects.requireNonNull(event.getPlayer().getItemInHand().getItemMeta()).getLocalizedName());

            if (openedCrate == null) {
                player.toPlayer().sendMessage(Prison.PREFIX + "§cDu hast kein Gültigen Key in der Hand!");
                return;
            }

            if (openedCrate.getLocalizedName().equals(itemStack.getItemMeta().getLocalizedName())) {
                if (itemStack.getItemMeta().getLocalizedName().equals("MINE_KEY")) {

                    if (this.plugin.getInventoryUtil().isInventoryFull(player.toPlayer())) {
                        player.toPlayer().sendMessage(Prison.PREFIX + "§cDein Inventar ist voll!");
                        player.toPlayer().playSound(player.toPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                        return;
                    }

                    Integer tokens = this.plugin.random(5, 15);

                    this.plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).addTokens(tokens);
                    this.plugin.getInventoryUtil().remove(event.getPlayer().getInventory(), itemStack, "MINE_KEY", 1);

                    event.getPlayer().sendMessage(Prison.PREFIX + "§aDu hast ein Mine Key geöffnet und " + tokens + " Tokens bekommen!");

                    UnestiaAPI.getInstance().getScoreboardUtil().updateScoreboard(event.getPlayer(), 8, "§e» §7Tokens: §f" + player.getTokens());

                } else {

                    if (this.plugin.getInventoryUtil().isInventoryFull(player.toPlayer())) {
                        player.toPlayer().sendMessage(Prison.PREFIX + "§cDein Inventar ist voll!");
                        player.toPlayer().playSound(player.toPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                        return;
                    }

                    List<ItemStack> allWins = new ArrayList<>();

                    if (!(openedCrate.getCrateType().getNormalWins().isEmpty()))
                        allWins.addAll(openedCrate.getCrateType().getNormalWins());

                    if (!(openedCrate.getCrateType().getEpicWins().isEmpty()))
                        allWins.addAll(openedCrate.getCrateType().getEpicWins());

                    if (allWins.isEmpty()) {
                        player.toPlayer().sendMessage(Prison.PREFIX + "§cEs sind derzeit keine Items für diese Crate verfügbar!");
                        player.toPlayer().playSound(player.toPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                        return;
                    }

                    Random random = new Random();

                    ItemStack win = allWins.get(random.nextInt(allWins.size()));

                    player.toPlayer().getInventory().addItem(new ItemBuilder(win).build());

                    this.plugin.getInventoryUtil().remove(event.getPlayer().getInventory(), itemStack, openedCrate.getLocalizedName(), 1);
                    event.getPlayer().sendMessage(Prison.PREFIX + "§aDu hast ein " + openedCrate.getCrateType().getKey() + " Key geöffnet und " + win.getType().name().replace("_", " ") + " bekommen!");

                }
            }

        }

    }

}
