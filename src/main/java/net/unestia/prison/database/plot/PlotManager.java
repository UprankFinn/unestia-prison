package net.unestia.prison.database.plot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import net.unestia.api.UnestiaAPI;

import javax.swing.text.Document;
import java.lang.reflect.Type;
import java.util.*;

public class PlotManager {

    private final Gson gson;
    private final Map<Plot.Cuboid, Plot> plots;

    //private final MongoCollection<org.bson.Document> collection;

    public PlotManager() {
        this.gson = new GsonBuilder().create();
        this.plots = new HashMap<>();

        //this.collection = UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("plots");
        //this.collection.find().forEach((Block<? super org.bson.Document>) document -> this.plots.put(this.gson.fromJson(this.gson.toJson(document), Plot.Cuboid.class), this.gson.fromJson(this.gson.toJson(document), Plot.class)));
    }

    public List<Plot> getPlots(UUID uniqueId) {
        List<Plot> plots = new ArrayList<>();
        this.plots.values().forEach(plot -> {
            if (plot.getOwner() != null && plot.getOwner().equals(uniqueId)) {
                plots.add(plot);
            }
            return;
        });
        return plots;
    }

    public List<Plot> getPlots() {
        return new ArrayList<Plot>(this.plots.values());
    }

    public void registerPlot( Plot.Type type, Plot.Cuboid cuboid) {
        if (this.plots.containsKey(cuboid)) {
            return;
        }
        for (final Plot.Cuboid plot : this.plots.keySet()) {
            if (plot.toString().equalsIgnoreCase(cuboid.toString())) {
                return;
            }
        }
        Map<String, Boolean> flags = new HashMap<>();
        flags.put("pvp", false);
        flags.put("fly", false);

        this.plots.put(cuboid, new Plot(UUID.randomUUID(), type, flags, null, new ArrayList<UUID>(), new ArrayList<UUID>(), cuboid));
        //this.collection.insertOne(this.gson.fromJson(this.gson.toJson(new Plot(UUID.randomUUID(), type, flags, null, new ArrayList<>(), new ArrayList<>(), cuboid)), (Type) Plot.class));
    }

}
