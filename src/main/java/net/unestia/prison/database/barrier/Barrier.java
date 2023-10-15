package net.unestia.prison.database.barrier;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class Barrier {

    private final String name;
    private final Cuboid cuboid;

    public Barrier(String name, Cuboid cuboid) {
        this.name = name;
        this.cuboid = cuboid;
    }

    public Integer getVolume() {
        return cuboid.getMinimumX() + cuboid.getMaximumX() * cuboid.getMinimumZ() + cuboid.getMaximumZ() * cuboid.getMinimumY() + cuboid.getMaximumY();
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

        public Location getCenter() {
            return new Location(Bukkit.getWorld("world"), this.minimumX + ((this.maximumX + 1) - this.minimumX) / 2.0,
                    this.minimumY + ((this.maximumY + 1) - this.minimumY) / 2.0,
                    this.minimumZ + ((this.maximumZ + 1) - this.minimumZ) / 2.0);
        }

        public Boolean contains(Block block) {
            return this.contains(block.getLocation());
        }

        public Boolean contains(Location location) {
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

    public String getName() {
        return name;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }
}
