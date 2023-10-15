package net.unestia.prison.database.auctionhouse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import net.unestia.api.UnestiaAPI;
import org.bson.Document;

import java.util.*;
import java.util.function.Consumer;

public class AuctionManager {

    private final List<Auction> auctions;

    private final Gson gson;
    private final MongoCollection<Document> collection;


    public AuctionManager() {
        this.auctions = new ArrayList<>();

        this.gson = new GsonBuilder().create();
        this.collection = UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("auctions");
        this.collection.find().forEach((Consumer<Document>) document -> this.auctions.add(this.gson.fromJson(this.gson.toJson(document), Auction.class)));
    }

    public List<Auction> getAuctions() {
        return this.auctions;
    }

    public Map<UUID, Auction> getAuctionsMap() {
        Map<UUID, Auction> auctions = new HashMap<>();
        for (Auction auction : this.auctions) {
            auctions.put(auction.getUniqueId(), auction);
        }
        return auctions;
    }

    public void registerAuctions(Auction actions) {
        this.collection.insertOne(this.gson.fromJson(this.gson.toJson(actions), Document.class));
        this.auctions.add(actions);
    }

    public void removeAuctions(UUID uniqueId) {
        this.auctions.removeIf((barrier) -> barrier.getUniqueId().equals(uniqueId));
    }

}
