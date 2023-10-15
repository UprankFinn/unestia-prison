package net.unestia.prison.builder.upgrade;

import net.unestia.api.UnestiaAPI;
import net.unestia.prison.Prison;
import net.unestia.prison.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class UpgradeBuilder {

    private final Prison plugin;

    private Inventory inventory;

    private final Map<Enchantment, Integer> enchantmentListOfSwords = new HashMap<>();
    private final Map<Enchantment, Integer> enchantmentListOfPickaxe = new HashMap<>();

    public UpgradeBuilder(Prison plugin) {
        this.plugin = plugin;
    }

    public void put() {
        this.enchantmentListOfSwords.put(Enchantment.DAMAGE_ALL, 50);
        this.enchantmentListOfSwords.put(Enchantment.FIRE_ASPECT, 50);
        this.enchantmentListOfSwords.put(Enchantment.SWEEPING_EDGE, 50);
        this.enchantmentListOfSwords.put(Enchantment.LOOT_BONUS_BLOCKS, 50);
        this.enchantmentListOfSwords.put(Enchantment.KNOCKBACK, 50);
        this.enchantmentListOfSwords.put(Enchantment.DAMAGE_ARTHROPODS, 50);
        this.enchantmentListOfSwords.put(Enchantment.DEPTH_STRIDER, 50);

        this.enchantmentListOfPickaxe.put(Enchantment.MENDING, 150);
        this.enchantmentListOfPickaxe.put(Enchantment.SILK_TOUCH, 50);
        this.enchantmentListOfPickaxe.put(Enchantment.LOOT_BONUS_BLOCKS, 50);
    }

    public void genInventory(Player player) {

        if (player.getItemInHand().getType().equals(Material.IRON_PICKAXE) || player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) || player.getItemInHand().getType().equals(Material.NETHERITE_PICKAXE)
                || player.getItemInHand().getType().equals(Material.GOLDEN_PICKAXE) || player.getItemInHand().getType().equals(Material.STONE_PICKAXE) || player.getItemInHand().getType().equals(Material.WOODEN_PICKAXE)
                || player.getItemInHand().getType().equals(Material.STONE_PICKAXE)) {

            this.inventory = Bukkit.createInventory(null, 9 * 6, "§6Pickaxe Upgraden");

            this.addDefault(player, this.inventory);

            this.enchantmentListOfPickaxe.forEach((enchantment, integer) -> this.inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK, 1, (byte) 0).setDisplayName("§8» " + enchantment.getName()).build()));

            player.openInventory(this.inventory);
        } else if (player.getItemInHand().getType().equals(Material.IRON_SWORD) || player.getItemInHand().getType().equals(Material.DIAMOND_SWORD) || player.getItemInHand().getType().equals(Material.NETHERITE_SWORD)
                || player.getItemInHand().getType().equals(Material.GOLDEN_SWORD) || player.getItemInHand().getType().equals(Material.STONE_SWORD) || player.getItemInHand().getType().equals(Material.WOODEN_SWORD)
                || player.getItemInHand().getType().equals(Material.STONE_SWORD)) {

            this.inventory = Bukkit.createInventory(null, 9 * 6, "§6Schwerter Upgraden");

            this.addDefault(player, this.inventory);

            this.enchantmentListOfSwords.forEach((enchantment, integer) -> this.inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK, 1, (byte) 0).setDisplayName("§8» " + enchantment.getName()).build()));

            player.openInventory(this.inventory);
        }


    }

    public void upgrade(Player player, ItemStack itemStack, String enchantment, Integer value) {

        if (player.getItemInHand().getType().equals(Material.IRON_PICKAXE) || player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) || player.getItemInHand().getType().equals(Material.NETHERITE_PICKAXE)
                || player.getItemInHand().getType().equals(Material.GOLDEN_PICKAXE) || player.getItemInHand().getType().equals(Material.STONE_PICKAXE) || player.getItemInHand().getType().equals(Material.WOODEN_PICKAXE)
                || player.getItemInHand().getType().equals(Material.STONE_PICKAXE)) {

            if (this.plugin.getPlayerManager().getPlayer(player.getUniqueId()).getTokens() >= this.enchantmentListOfPickaxe.get(Enchantment.getByName(enchantment))) {
                if (Enchantment.getByName(enchantment) == null) {
                    player.sendMessage(Prison.PREFIX + "§cDas Enchantment existiert nicht!");
                    return;
                }

                Objects.requireNonNull(itemStack.getItemMeta()).addItemFlags(ItemFlag.HIDE_ENCHANTS);

                itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchantment)), value);

                this.plugin.getPlayerManager().getPlayer(player.getUniqueId()).removeTokens(this.enchantmentListOfPickaxe.get(Enchantment.getByName(enchantment)));

                player.closeInventory();

            } else {
                player.sendMessage(Prison.PREFIX + "§cDafür hast du nicht genügend Tokens!");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            }

        } else if (player.getItemInHand().getType().equals(Material.IRON_SWORD) || player.getItemInHand().getType().equals(Material.DIAMOND_SWORD) || player.getItemInHand().getType().equals(Material.NETHERITE_SWORD)
                || player.getItemInHand().getType().equals(Material.GOLDEN_SWORD) || player.getItemInHand().getType().equals(Material.STONE_SWORD) || player.getItemInHand().getType().equals(Material.WOODEN_SWORD)
                || player.getItemInHand().getType().equals(Material.STONE_SWORD)) {

            if (this.plugin.getPlayerManager().getPlayer(player.getUniqueId()).getTokens() >= this.enchantmentListOfSwords.get(Enchantment.getByName(enchantment))) {
                if (Enchantment.getByName(enchantment) == null) {
                    player.sendMessage(Prison.PREFIX + "§cDas Enchantment existiert nicht!");
                    return;
                }

                Objects.requireNonNull(itemStack.getItemMeta()).addItemFlags(ItemFlag.HIDE_ENCHANTS);

                itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchantment)), value);

                this.plugin.getPlayerManager().getPlayer(player.getUniqueId()).removeTokens(this.enchantmentListOfSwords.get(Enchantment.getByName(enchantment)));

                player.closeInventory();

            } else {
                player.sendMessage(Prison.PREFIX + "§cDafür hast du nicht genügend Tokens!");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            }

        }

        UnestiaAPI.getInstance().getScoreboardUtil().updateScoreboard(player, 8, "§e» §7Tokens: §f" + this.plugin.getPlayerManager().getPlayer(player.getUniqueId()).getTokens());

    }

    public void addDefault(Player player, Inventory inventory){
        inventory.setItem(0, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(1, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(2, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(3, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(4, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(5, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(6, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(7, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(8, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());

        inventory.setItem(9, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(10, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(11, new ItemBuilder(Material.ANVIL, 1, (byte) 0).setDisplayName("§8» §eRepair").build());
        inventory.setItem(12, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());

        inventory.setItem(13, player.getItemInHand());

        inventory.setItem(14, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(15, new ItemBuilder(Material.BARRIER, 1, (byte) 0).setDisplayName("§8» §cSoon").build());
        inventory.setItem(16, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(17, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());

        inventory.setItem(18, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(19, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(20, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(21, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(22, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(23, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(24, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(25, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
        inventory.setItem(26, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (byte) 0).build());
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<Enchantment, Integer> getEnchantmentListOfSwords() {
        return enchantmentListOfSwords;
    }

    public Map<Enchantment, Integer> getEnchantmentListOfPickaxe() {
        return enchantmentListOfPickaxe;
    }

}
