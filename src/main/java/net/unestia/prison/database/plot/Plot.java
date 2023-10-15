package net.unestia.prison.database.plot;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Plot {

    private UUID id;
    private Type type;
    private Map<String, Boolean> flags;
    private UUID owner;
    private List<UUID> trustedMembers;
    private List<UUID> deniedMembers;
    private Cuboid cuboid;

    public Plot(UUID id, Type type, Map<String, Boolean> flags, UUID owner, List<UUID> trustedMembers, List<UUID> deniedMembers, Cuboid cuboid) {
        this.id = id;
        this.type = type;
        this.flags = flags;
        this.owner = owner;
        this.trustedMembers = trustedMembers;
        this.deniedMembers = deniedMembers;
        this.cuboid = cuboid;
    }

    public UUID getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public Map<String, Boolean> getFlags() {
        return flags;
    }

    public UUID getOwner() {
        return owner;
    }

    public List<UUID> getTrustedMembers() {
        return trustedMembers;
    }

    public List<UUID> getDeniedMembers() {
        return deniedMembers;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setFlags(Map<String, Boolean> flags) {
        this.flags = flags;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public void setTrustedMembers(List<UUID> trustedMembers) {
        this.trustedMembers = trustedMembers;
    }

    public void setDeniedMembers(List<UUID> deniedMembers) {
        this.deniedMembers = deniedMembers;
    }

    public void setCuboid(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    public enum Type {
        PLAYER,
        GUILD;
    }

    public static class Cuboid {
        private final Integer minimumX;
        private final Integer maximumX;
        private final Integer minimumY;
        private final Integer maximumY;
        private final Integer minimumZ;
        private final Integer maximumZ;

        public Cuboid(final Integer minimumX, final Integer maximumX, final Integer minimumY, final Integer maximumY, final Integer minimumZ, final Integer maximumZ) {
            this.minimumX = minimumX;
            this.maximumX = maximumX;
            this.minimumY = minimumY;
            this.maximumY = maximumY;
            this.minimumZ = minimumZ;
            this.maximumZ = maximumZ;
        }

        public Location getCenter() {
            return new Location(Bukkit.getWorld("plot"), this.minimumX + (this.maximumX + 1 - this.minimumX) / 2.0, this.minimumY + (this.maximumY + 1 - this.minimumY) / 2.0, this.minimumZ + (this.maximumZ + 1 - this.minimumZ) / 2.0);
        }

        public Boolean contains(final Block block) {
            return this.contains(block.getLocation());
        }

        public Boolean contains(final Location location) {
            return this.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }

        public Boolean contains(final Integer x, final Integer y, final Integer z) {
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
