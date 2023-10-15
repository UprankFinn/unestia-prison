package net.unestia.prison.database.npc;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class NPC {

    private final String name;
    private final Location location;

    public NPC(String name, Location location) {
        this.name = name;
        this.location = location;

        Villager villager = (Villager) Bukkit.getWorld(location.getWorld()).spawnEntity(location.toLocation(), EntityType.VILLAGER);

        villager.setCustomNameVisible(true);
        villager.setCustomName("§a§lVerkaufe deine Items hier!");

        villager.setVillagerType(Villager.Type.PLAINS);
        villager.setAI(true);
        villager.setAdult();
        villager.setCollidable(true);
        villager.setGravity(true);
        villager.setInvisible(false);
        villager.setProfession(Villager.Profession.FARMER);
        villager.setCanPickupItems(false);

    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
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
            return new org.bukkit.Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        }
    }

}
