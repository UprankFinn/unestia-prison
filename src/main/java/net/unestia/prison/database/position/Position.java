package net.unestia.prison.database.position;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.unestia.api.UnestiaAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Position {

    private final String name;

    private String world;

    private double x;
    private double y;
    private double z;

    private float yaw;
    private float pitch;

    public Position(String name, String world, double x, double y, double z, float yaw, float pitch) {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public String getName() {
        return name;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("locations").updateOne(
                Filters.eq("name", name), Updates.set("world", world));
        this.world = world;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("locations").updateOne(
                Filters.eq("name", name), Updates.set("x", x));
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("locations").updateOne(
                Filters.eq("name", name), Updates.set("y", y));
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("locations").updateOne(
                Filters.eq("name", name), Updates.set("z", z));
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("locations").updateOne(
                Filters.eq("name", name), Updates.set("yaw", yaw));
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("locations").updateOne(
                Filters.eq("name", name), Updates.set("pitch", pitch));
        this.pitch = pitch;
    }

    public Location getBukkitLocation() {
        return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch);
    }

}
