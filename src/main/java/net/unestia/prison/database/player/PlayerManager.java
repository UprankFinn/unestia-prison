package net.unestia.prison.database.player;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import net.unestia.api.UnestiaAPI;
import org.bson.Document;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private final Map<UUID, Player> players = new ConcurrentHashMap<>();

    public void createPlayer(Player player) {
        MongoCollection<Document> document = UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("players");
        if (document.find(Filters.eq("uniqueId", player.getUniqueId().toString())).first() == null)
            document.insertOne(new Gson().fromJson(new Gson().toJson(player), Document.class));
    }

    public Player getPlayer(UUID uniqueId){
        if(this.players.containsKey(uniqueId)) return this.players.get(uniqueId);
        return null;
    }

    public void addPlayerToCach(Player player) {
        this.players.put(player.getUniqueId(), player);
    }

    public void removePlayerFromCach(UUID uniqueId){
        this.players.remove(uniqueId);
    }

    public Map<UUID, Player> getPlayers() {
        return players;
    }

}
