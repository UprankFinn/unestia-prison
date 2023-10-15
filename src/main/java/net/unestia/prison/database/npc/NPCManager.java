package net.unestia.prison.database.npc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import net.unestia.api.UnestiaAPI;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NPCManager {

    private final Gson gson;

    private final List<NPC> NPCs = new ArrayList<>();

    private final MongoCollection<Document> collection;

    public NPCManager() {
        this.gson = new GsonBuilder().create();
        this.collection = UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("npcs");

        this.collection.find().forEach((Consumer<Document>) document -> this.NPCs.add(this.gson.fromJson(this.gson.toJson(document), NPC.class)));
    }

    public void createNPC(String name, NPC.Location location) {
        if (this.collection.find(Filters.eq("name", name)).first() == null) {
            this.collection.insertOne(this.gson.fromJson(this.gson.toJson(new NPC(name, location)), Document.class));
        }
    }

    public NPC getNPC(String name) {
        if (this.collection.find(Filters.eq("name", name)).first() == null) {
            return this.gson.fromJson(this.gson.toJson(this.collection.find(Filters.eq("name", name)).first()), NPC.class);
        }
        return null;
    }

    public List<NPC> getNPCs() {
        return this.NPCs;
    }
}
