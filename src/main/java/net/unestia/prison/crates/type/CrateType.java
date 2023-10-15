package net.unestia.prison.crates.type;

import net.unestia.prison.builder.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum CrateType {

    MINE_KEY("MINE_KEY", "§8§l[§c§lMine Key§8§l]", ChatColor.RED, Material.TRIPWIRE_HOOK, Enchantment.LUCK, Collections.emptyList(), Collections.emptyList()),
    VOTE_KEY("VOTE_KEY", "§8§l[§a§lVote Key§8§l]", ChatColor.GREEN, Material.TRIPWIRE_HOOK, Enchantment.LUCK, Arrays.asList(
            new ItemBuilder(Material.IRON_PICKAXE, 1, (byte) 0).setDisplayName("§a§lVoter Spitzhacke").addUnsafeEnchantment(Enchantment.DIG_SPEED, 3)
                    .addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3)
                    .addUnsafeEnchantment(Enchantment.DURABILITY, 3).build(),
            new ItemBuilder(Material.IRON_AXE, 1, (byte) 0).setDisplayName("§a§lVoter Axt").addUnsafeEnchantment(Enchantment.DIG_SPEED, 3)
                    .addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3)
                    .addUnsafeEnchantment(Enchantment.DURABILITY, 3).build(),
            new ItemBuilder(Material.IRON_SWORD, 1, (byte) 0).setDisplayName("§a§lVoter Schaufel").addUnsafeEnchantment(Enchantment.DIG_SPEED, 3)
                    .addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3)
                    .addUnsafeEnchantment(Enchantment.DURABILITY, 3).build(),
            new ItemBuilder(Material.IRON_SWORD, 1, (byte) 0).setDisplayName("§a§lVoter Schwert").addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3)
                    .addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 3)
                    .addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 3)
                    .addUnsafeEnchantment(Enchantment.KNOCKBACK, 2).build()
    ), Collections.emptyList()),
    PRESTIGE_KEY("PRESTIGE_KEY", "§8§l[§c§lPrestige Key§8§l]", ChatColor.RED, Material.TRIPWIRE_HOOK, Enchantment.LUCK, Collections.emptyList(), Collections.emptyList()),
    DRAGON_KEY("DRAGON_KEY", "§8§l[§3§lDragon Key§8§l]", ChatColor.DARK_AQUA, Material.TRIPWIRE_HOOK, Enchantment.LUCK, Collections.emptyList(), Collections.emptyList()),
    ZOMBIE_KEY("ZOMBIE_KEY", "§8§l[§c§lZombie Key§8§l]", ChatColor.RED, Material.TRIPWIRE_HOOK, Enchantment.LUCK, Collections.emptyList(), Collections.emptyList()),
    VAMPIRE_KEY("VAMPIRE_KEY", "§8§l[§6§lVampire Key§8§l]", ChatColor.GOLD, Material.TRIPWIRE_HOOK, Enchantment.LUCK, Collections.emptyList(), Collections.emptyList()),
    CURRENT_KEY("CURRENT_KEY", "§8§l[§9§lCurrent Key§8§l]", ChatColor.DARK_BLUE, Material.TRIPWIRE_HOOK, Enchantment.LUCK, Collections.emptyList(), Collections.emptyList()),
    OP_KEY("OP_KEY", "§8§l[§6§lOP Key§8§l]", ChatColor.GOLD, Material.TRIPWIRE_HOOK, Enchantment.LUCK, Collections.emptyList(), Collections.emptyList()),
    GOLD_KEY("GOLD_KEY", "§8§l[§6§lGold Key§8§l]", ChatColor.GOLD, Material.TRIPWIRE_HOOK, Enchantment.LUCK, Collections.emptyList(), Collections.emptyList());

    private final String key;
    private final String displayName;
    private final ChatColor chatColor;
    private final Material material;
    private final Enchantment enchantmentList;
    private final List<ItemStack> normalWins;
    private final List<ItemStack> epicWins;

    CrateType(String key, String displayName, ChatColor chatColor, Material material, Enchantment enchantmentList, List<ItemStack> normalWins, List<ItemStack> epicWins) {
        this.key = key;
        this.displayName = displayName;
        this.chatColor = chatColor;
        this.material = material;
        this.enchantmentList = enchantmentList;
        this.normalWins = normalWins;
        this.epicWins = epicWins;
    }

    public String getKey() {
        return key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ChatColor getColor() {
        return chatColor;
    }

    public Material getMaterial() {
        return material;
    }

    public Enchantment getEnchantmentList() {
        return enchantmentList;
    }

    public List<ItemStack> getNormalWins() {
        return normalWins;
    }

    public List<ItemStack> getEpicWins() {
        return epicWins;
    }
}
