package net.unestia.prison.builder.kits;

import net.unestia.prison.Prison;
import net.unestia.prison.builder.ItemBuilder;
import net.unestia.prison.builder.kits.type.DefaultKit;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class KitBuilder {

    private final Prison plugin;

    private Inventory inventory;

    private final Map<String, Kit> kits;

    public KitBuilder(Prison plugin) {
        this.plugin = plugin;

        this.kits = new HashMap<>();
    }

    public void createInventory(Player player) {
        this.inventory = Bukkit.createInventory(null, 9 * 3, "§6Kits");

        this.inventory.setItem(13, new ItemBuilder(this.getKit("default").getItemStack()).setDisplayName("§8» §edefault").build());

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        player.openInventory(this.inventory);
    }

    public void registerKit(Kit kit) {
        this.kits.put(kit.getName(), kit);
    }

    public Kit getKit(String kit) {
        if (kits.containsKey(kit)) return this.kits.get(kit);
        return null;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<String, Kit> getKits() {
        return kits;
    }
}
