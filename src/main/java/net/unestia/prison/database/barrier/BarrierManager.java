package net.unestia.prison.database.barrier;

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

public class BarrierManager {

    private final List<Barrier> barriers;

    private final Gson gson;
    private final MongoCollection<Document> collection;


    public BarrierManager() {
        this.barriers = new ArrayList<>();

        this.gson = new GsonBuilder().create();
        this.collection = UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("barriers");
        this.collection.find().forEach((Consumer<Document>) document -> this.barriers.add(this.gson.fromJson(this.gson.toJson(document), Barrier.class)));
    }

    public Barrier getBarrier(String name) {
        for (Barrier barrier : this.barriers) {
            if (barrier.getName().equals(name)) {
                return barrier;
            }
        }
        return null;
    }

    public Barrier getBarrier(Player player) {
        return this.getBarrier(player.getLocation());
    }

    public Barrier getBarrier(Location location) {
        for (Barrier barrier : this.barriers) {
            if (barrier.getCuboid().contains(location)) {
                return barrier;
            }
        }
        return null;
    }

    public List<Barrier> getBarriers() {
        return this.barriers;
    }

    public void registerBarrier(Barrier barrier) {
        this.collection.insertOne(this.gson.fromJson(this.gson.toJson(barrier), Document.class));
        this.barriers.add(barrier);
    }

    public void unregisterBarrier(String name) {
        this.barriers.removeIf((barrier) -> barrier.getName().equals(name));
    }

}
