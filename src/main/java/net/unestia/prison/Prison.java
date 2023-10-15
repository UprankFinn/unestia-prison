package net.unestia.prison;

import net.unestia.api.UnestiaAPI;
import net.unestia.prison.builder.InventoryUtil;
import net.unestia.prison.builder.house.HouseBuilder;
import net.unestia.prison.builder.kits.KitBuilder;
import net.unestia.prison.builder.kits.type.DefaultKit;
import net.unestia.prison.builder.sell.SellBuilder;
import net.unestia.prison.builder.upgrade.UpgradeBuilder;
import net.unestia.prison.commands.*;
import net.unestia.prison.crates.CrateManager;
import net.unestia.prison.database.auctionhouse.AuctionManager;
import net.unestia.prison.database.barrier.BarrierManager;
import net.unestia.prison.database.npc.NPCManager;
import net.unestia.prison.database.plot.PlotManager;
import net.unestia.prison.listener.auctionhouse.AuctionItemBuyListener;
import net.unestia.prison.listener.auctionhouse.AuctionItemSetListener;
import net.unestia.prison.listener.block.BlockBreakListener;
import net.unestia.prison.listener.block.BlockPhysicsListener;
import net.unestia.prison.listener.block.BlockPlaceListener;
import net.unestia.prison.listener.craft.CraftItemListener;
import net.unestia.prison.listener.enchant.EnchantItemListener;
import net.unestia.prison.listener.entity.EntityDamageByEntityListener;
import net.unestia.prison.listener.entity.EntityDamageListener;
import net.unestia.prison.listener.food.FoodLevelChangeListener;
import net.unestia.prison.listener.inventory.InventoryClickListener;
import net.unestia.prison.listener.npc.NPCInteractListener;
import net.unestia.prison.listener.player.*;
import net.unestia.prison.database.mine.MineManager;
import net.unestia.prison.database.player.PlayerManager;
import net.unestia.prison.database.position.PositionManager;
import net.unestia.prison.builder.rankup.RankManager;
import net.unestia.prison.world.generator.WorldGenerator;
import net.unestia.prison.world.schematic.SchematicManager;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Prison extends JavaPlugin {

    private final List<String> alphabetic = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

    private static Prison instance;

    public static final String PREFIX = "§6§lUNESTIA §8» §r";

    private HouseBuilder houseBuilder;
    private KitBuilder kitBuilder;
    private SellBuilder sellBuilder;
    private final CrateManager crateManager;
    private final InventoryUtil inventoryUtil;
    private final UpgradeBuilder upgradeBuilder;
    private final RankManager rankUpManager;

    private AuctionManager auctionManager;
    private BarrierManager barrierManager;
    private final PlayerManager playerManager;
    private PositionManager positionManager;
    private NPCManager npcManager;
    private MineManager mineManager;

    private final SchematicManager schematicManager;
    private PlotManager plotManager;


    public Prison() {
        this.schematicManager = new SchematicManager();
        this.plotManager = new PlotManager();
        this.crateManager = new CrateManager();
        this.inventoryUtil = new InventoryUtil();

        this.upgradeBuilder = new UpgradeBuilder(this);
        this.upgradeBuilder.put();

        this.rankUpManager = new RankManager();
        this.playerManager = new PlayerManager();
    }

    @Override
    public void onEnable() {

        new WorldCreator("arena").createWorld();
        this.createWorld("plots", "template");

        this.npcManager = new NPCManager();
        this.sellBuilder = new SellBuilder(this);
        this.kitBuilder = new KitBuilder(this);

        this.houseBuilder = new HouseBuilder();
        this.positionManager = new PositionManager();
        this.mineManager = new MineManager();
        this.barrierManager = new BarrierManager();
        this.auctionManager = new AuctionManager();

        instance = this;

        new AhCommand(this);
        new CrateCommand(this);
        new HouseCommand(this);
        new KitCommand(this);
        new PriceCommand(this);
        new PvPCommand(this);
        new RankupCommand(this);
        new SellCommand(this);
        new SetCommand(this);
        new SetupCommand(this);
        new SpawnCommand(this);
        new TokensCommand(this);
        new UpgradeCommand(this);

        new AuctionItemBuyListener(this);
        new AuctionItemSetListener(this);

        new BlockBreakListener(this);
        new BlockPhysicsListener(this);
        new BlockPlaceListener(this);

        new CraftItemListener(this);

        new EnchantItemListener(this);

        new EntityDamageByEntityListener(this);
        new EntityDamageListener(this);

        new FoodLevelChangeListener(this);

        new InventoryClickListener(this);

        new NPCInteractListener(this);

        new PlayerChangedWorldListener(this);
        new PlayerDeathListener(this);
        new PlayerInteractListener(this);
        new PlayerJoinListener(this);
        new PlayerMoveListener(this);
        new PlayerQuitListener(this);
        new PlayerRespawnListener(this);
        new PlayerTeleportListener(this);


        this.npcManager.getNPCs().forEach(npc -> {
            Villager villager = (Villager) Bukkit.getWorld(npc.getLocation().getWorld()).spawnEntity(npc.getLocation().toLocation(), EntityType.VILLAGER);

            villager.setCustomNameVisible(true);
            villager.setCustomName("§a§lVerkaufe deine Items hier!");

            villager.setVillagerType(Villager.Type.PLAINS);
            villager.setAI(true);
            villager.setAdult();
            villager.setCollidable(true);
            villager.setGravity(true);
            villager.setInvisible(false);
            villager.setProfession(Villager.Profession.FARMER);
            villager.setCanPickupItems(false);
        });
        this.mineManager.getMines().forEach(mine -> {

            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (this.mineManager.getMine(player.getLocation()) != null) {
                        player.sendMessage(PREFIX + "§cDie Mine hat sich zurückgesetzt!");
                        player.teleport(mine.getLocation().toLocation());
                    }
                });
                mine.fill();
            }, 6000L, 6000L);

        });

        this.kitBuilder.registerKit(new DefaultKit());

    }

    @Override
    public void onDisable() {
        Bukkit.getWorld("world").getEntities().forEach((entity -> {
            if (entity.getType().equals(EntityType.VILLAGER)) {
                Bukkit.getWorld("world").getEntities().remove(entity);
            }
        }));
    }

    public List<String> getAlphabetic() {
        return alphabetic;
    }

    public Integer random(Integer minimum, Integer maximum) {
        Random random = new Random();
        return random.nextInt(maximum - minimum + 1) + minimum;
    }

    public static Integer staticRandom(Integer minimum, Integer maximum) {
        Random random = new Random();
        return random.nextInt(maximum - minimum + 1) + minimum;
    }

    public String getTime(int count) {
        int min = 0;
        int sec = 0;
        String minStr = "";
        String secStr = "";
        min = (int) Math.floor(count / 60);
        sec = count % 60;
        minStr = min < 10 ? "0" + (min) : String.valueOf(min);
        secStr = sec < 10 ? "0" + (sec) : String.valueOf(sec);
        return minStr + ":" + secStr;
    }

    public static Prison getInstance() {
        return instance;
    }

    public final NPCManager getNpcManager() {
        return npcManager;
    }

    public final KitBuilder getKitBuilder() {
        return kitBuilder;
    }

    public final MineManager getMineManager() {
        return mineManager;
    }

    public final SellBuilder getSellBuilder() {
        return sellBuilder;
    }

    public final HouseBuilder getHouseBuilder() {
        return houseBuilder;
    }

    public final CrateManager getCrateManager() {
        return crateManager;
    }

    public final BarrierManager getBarrierManager() {
        return barrierManager;
    }

    public final PlayerManager getPlayerManager() {
        return playerManager;
    }

    public final InventoryUtil getInventoryUtil() {
        return inventoryUtil;
    }

    public final UpgradeBuilder getUpgradeBuilder() {
        return upgradeBuilder;
    }

    public final PositionManager getPositionManager() {
        return positionManager;
    }

    public final RankManager getRankUpManager() {
        return rankUpManager;
    }

    public final AuctionManager getActionManager() {
        return auctionManager;
    }

    public PlotManager getPlotManager() {
        return plotManager;
    }

    public SchematicManager getSchematicManager() {
        return schematicManager;
    }

    public void createWorld(String name, String template) {
        if (Bukkit.getWorld(name) != null) return;
        new WorldCreator(name).generator((ChunkGenerator) new WorldGenerator(this, template)).createWorld();
    }
}
