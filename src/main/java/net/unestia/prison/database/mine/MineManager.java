package net.unestia.prison.database.mine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import net.unestia.api.UnestiaAPI;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MineManager {

    private final List<Mine> mines;

    private final Gson gson;
    private final MongoCollection<Document> collection;

    public MineManager() {
        this.mines = new ArrayList<>();

        this.gson = new GsonBuilder().create();
        this.collection = UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("mines");
        this.collection.find().forEach((Consumer<Document>) document -> this.mines.add(this.gson.fromJson(this.gson.toJson(document), Mine.class)));
    }

    public Mine getMine(String name) {
        for (Mine mine : this.mines) {
            if (mine.getName().equals(name)) {
                return mine;
            }
        }
        return null;
    }

    public Mine getMine(Player player) {
        return this.getMine(player.getLocation());
    }

    public Mine getMine(Location location) {
        for (Mine mine : this.mines) {
            if (mine.getCuboid().contains(location)) {
                return mine;
            }
        }
        return null;
    }

    public List<Mine> getMines() {
        return this.mines;
    }

    public void registerMine(Mine mine) {
        this.collection.insertOne(this.gson.fromJson(this.gson.toJson(mine), Document.class));
        this.mines.add(mine);
    }

    public void unregisterMine(String name) {
        this.mines.removeIf((mine) -> mine.getName().equals(name));
    }
}
