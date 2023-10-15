package net.unestia.prison.listener.player;

import net.unestia.prison.Prison;
import net.unestia.prison.database.barrier.BarrierManager;
import net.unestia.prison.database.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerMoveListener implements Listener {

    private final Prison plugin;

    public PlayerMoveListener(Prison plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {

        Player player = this.plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId());
        BarrierManager barrierManager = this.plugin.getBarrierManager();

        if (this.plugin.getBarrierManager().getBarrier(event.getPlayer().getLocation()) == null) return;

        if (!(player.getMines().containsKey(barrierManager.getBarrier(player.toPlayer().getLocation()).getName()))) {

            Location center = this.plugin.getBarrierManager().getBarrier(event.getPlayer()).getCuboid().getCenter();
            Vector vector = new Vector(center.getX(), center.getY(), center.getZ());
            vector.subtract(event.getPlayer().getLocation().toVector());
            event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().normalize().setY(0).multiply(-1));

            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
                player.toPlayer().teleport(this.plugin.getPositionManager().getPosition("spawn").getBukkitLocation());
            }, 15);
        }

        /*this.plugin.getBarrierManager().getBarriers().forEach((barrier -> {

            if (this.plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId()).getMines().get(Character.toString((char) (player.getMine().charAt(0) - player.getMines().size()))) == null) {
                Location center = this.plugin.getBarrierManager().getBarrier(event.getPlayer()).getCuboid().getCenter();
                Vector vector = new Vector(center.getX(), center.getY(), center.getZ());
                vector.subtract(event.getPlayer().getLocation().toVector());
                event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().normalize().setY(0).multiply(-1));

                Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> player.toPlayer().teleport(this.plugin.getPositionManager().getPosition("spawn").getBukkitLocation()), 15);

            }
        }));*/

    }

}
