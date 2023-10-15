package net.unestia.prison.builder.sell;

import net.unestia.api.UnestiaAPI;
import net.unestia.api.npc.NPC;
import net.unestia.prison.Prison;
import net.unestia.prison.database.mine.Mine;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class SellBuilder {

    private final Prison plugin;

    public SellBuilder(Prison plugin) {
        this.plugin = plugin;
    }

    public void sellItems(NPC npc, Player player) {
        AtomicInteger price = new AtomicInteger(0);

        Arrays.stream(player.getInventory().getStorageContents()).forEach((itemStack) -> {
            if (itemStack == null) return;

            Mine mine = this.plugin.getMineManager().getMine(npc.getGameProfile().getName().replace("npc_", "").toUpperCase());
            if (mine.getPrices().containsKey(itemStack.getType())) {
                for (int i = 0; i < itemStack.getAmount(); i++) {
                    price.getAndAdd(mine.getPrices().get(itemStack.getType()));
                }
                player.getInventory().remove(itemStack);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            }
        });

        UnestiaAPI.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).addBalance("opprison", price.get());
        UnestiaAPI.getInstance().getScoreboardUtil().updateScoreboard(player, 9, "§e» §7Money: §f" + UnestiaAPI.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getBalance("opprison"));

    }

}
