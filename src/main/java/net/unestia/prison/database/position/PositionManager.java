package net.unestia.prison.database.position;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import net.unestia.api.UnestiaAPI;
import org.bson.Document;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class PositionManager {

    private final Gson gson;

    private final Map<String, Position> positions = new ConcurrentHashMap<>();

    private final MongoCollection<Document> collection;

    public PositionManager() {
        this.gson = new GsonBuilder().create();
        this.collection = UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("locations");

        this.collection.find().forEach((Consumer<Document>) document -> this.positions.put("spawn", this.gson.fromJson(this.gson.toJson(document), Position.class)));
    }

    public void createPosition(Position position) {
        if (this.collection.find(Filters.eq("name", position.getName())).first() == null)
            this.collection.insertOne(new Gson().fromJson(new Gson().toJson(position), Document.class));
    }

    public Position getPositionFromDatabase(String position) {
        if (this.collection.find(Filters.eq("name", position)).first() == null) {
            return this.gson.fromJson(this.gson.toJson(this.collection.find(Filters.eq("name", position)).first()), Position.class);
        }
        return null;
    }

    public Position getPosition(String position) {
        if (this.positions.containsKey(position)) return this.positions.get(position);
        return null;
    }

    public void addPositionToCach(String name, Position position) {
        this.positions.put(name, position);
    }

    public void removePositionToCach(String position) {
        this.positions.remove(position);
    }

    public Map<String, Position> getPositions() {
        return positions;
    }
}
