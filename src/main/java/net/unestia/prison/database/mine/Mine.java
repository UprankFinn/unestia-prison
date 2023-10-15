package net.unestia.prison.database.mine;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.unestia.api.UnestiaAPI;
import net.unestia.prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.*;

public class Mine {

    private final String name;
    private final Location location;

    private final Material material;
    private final List<Material> materials;

    private final Map<Material, Integer> prices;

    private final Cuboid cuboid;

    public Mine(String name, Location location, Material material, Map<String, Integer> prices, List<Material> materials, Cuboid cuboid) {
        this.name = name;
        this.location = location;

        this.material = material;
        this.materials = materials;

        this.prices = new HashMap<>();
        prices.forEach((deinemudda, price) -> {
            this.prices.put(Material.valueOf(deinemudda), price);
        });

        this.cuboid = cuboid;
    }

    public void fill() {

        for (int x = cuboid.minimumX; x < cuboid.maximumX + 1; x++) {
            for (int y = cuboid.minimumY; y < cuboid.maximumY + 1; y++) {
                for (int z = cuboid.minimumZ; z < cuboid.maximumZ + 1; z++) {
                    Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(x, y, z).setType(this.material);
                }
            }
        }

        this.materials.forEach((material) -> {
            for (int i = 0; i < Prison.staticRandom(1000, 1000); i++) {
                Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(Prison.staticRandom(cuboid.getMinimumX(), cuboid.getMaximumX()), Prison.staticRandom(cuboid.getMinimumY(), cuboid.getMaximumY()), Prison.staticRandom(cuboid.getMinimumZ(), cuboid.getMaximumZ())).setType(material);
            }
        });
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return location;
    }

    public List<Material> getMaterials() {
        return this.materials;
    }

    public void addPrice(Material material, Integer price) {
        Map<String, Integer> prices = new HashMap<>();
        this.prices.put(material, price);
        this.prices.forEach((material1, price1) -> prices.put(material1.name(), price1));

        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("mines").updateOne(
                Filters.eq("name", name), Updates.set("prices", prices));
    }

    public void removePrice(Material material) {
        Map<String, Integer> prices = new HashMap<>();
        this.prices.remove(material);
        this.prices.forEach((material1, price1) -> prices.put(material1.name(), price1));

        UnestiaAPI.getInstance().getMongoClient().getDatabase("opprison").getCollection("mines").updateOne(
                Filters.eq("name", name), Updates.set("prices", prices));
    }

    public Map<Material, Integer> getPrices() {
        return prices;
    }

    public Cuboid getCuboid() {
        return this.cuboid;
    }

    public Integer getVolume() {
        return cuboid.getMinimumX() + cuboid.getMaximumX() * cuboid.getMinimumZ() + cuboid.getMaximumZ() * cuboid.getMinimumY() + cuboid.getMaximumY();
    }

    public static class Location {

        private final String world;
        private final double x, y, z;
        private final float yaw, pitch;

        public Location(String world, double x, double y, double z, float yaw, float pitch) {
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public String getWorld() {
            return world;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public float getYaw() {
            return yaw;
        }

        public float getPitch() {
            return pitch;
        }

        public org.bukkit.Location toLocation() {
            return new org.bukkit.Location(Bukkit.getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch);
        }

    }

    public static class Cuboid {

        private final Integer minimumX;
        private final Integer maximumX;

        private final Integer minimumY;
        private final Integer maximumY;

        private final Integer minimumZ;
        private final Integer maximumZ;

        public Cuboid(Integer minimumX, Integer maximumX, Integer minimumY, Integer maximumY, Integer minimumZ, Integer maximumZ) {
            this.minimumX = minimumX;
            this.maximumX = maximumX;

            this.minimumY = minimumY;
            this.maximumY = maximumY;

            this.minimumZ = minimumZ;
            this.maximumZ = maximumZ;
        }

        public org.bukkit.Location getCenter() {
            return new org.bukkit.Location(Bukkit.getWorld("world"), this.minimumX + ((this.maximumX + 1) - this.minimumX) / 2.0,
                    this.minimumY + ((this.maximumY + 1) - this.minimumY) / 2.0,
                    this.minimumZ + ((this.maximumZ + 1) - this.minimumZ) / 2.0);
        }

        public Boolean contains(Block block) {
            return this.contains(block.getLocation());
        }

        public Boolean contains(org.bukkit.Location location) {
            return this.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }

        public Boolean contains(Integer x, Integer y, Integer z) {
            return x >= this.minimumX && x <= this.maximumX && y >= this.minimumY && y <= this.maximumY && z >= this.minimumZ && z <= this.maximumZ;
        }

        public Integer getMinimumX() {
            return this.minimumX;
        }

        public Integer getMaximumX() {
            return this.maximumX;
        }

        public Integer getMinimumY() {
            return this.minimumY;
        }

        public Integer getMaximumY() {
            return this.maximumY;
        }

        public Integer getMinimumZ() {
            return this.minimumZ;
        }

        public Integer getMaximumZ() {
            return this.maximumZ;
        }

        @Override
        public String toString() {
            return "x1=" + this.getMinimumX() + ",x2=" + this.getMaximumX() + ",y1=" + this.getMinimumY() + ",y2=" + this.getMaximumY() + ",z1=" + this.getMinimumZ() + ",z2=" + this.getMaximumZ();
        }
    }

}
