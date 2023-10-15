package net.unestia.prison.world.generator;

import com.sk89q.worldedit.math.BlockVector3;
import net.unestia.prison.Prison;
import net.unestia.prison.database.plot.Plot;
import net.unestia.prison.world.schematic.Schematic;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class WorldGenerator extends ChunkGenerator {

    private final Prison plugin;

    private final Schematic schematic;

    public WorldGenerator(Prison plugin, final String name) {
        this.plugin = plugin;
        this.schematic = this.plugin.getSchematicManager().loadSchematic(name);
    }

    public ChunkGenerator.ChunkData generateChunkData( World world,  Random random,  int x,  int z, ChunkGenerator.BiomeGrid biome) {
        ChunkGenerator.ChunkData data = this.createChunkData(world);
        if (this.schematic != null && !BlockVector3.ZERO.equals((Object)this.schematic.getSize())) {
            BlockVector3 blockVector3 = BlockVector3.at(0, 0, 0);
            int width = this.schematic.getWidth();
            int length = this.schematic.getLength();
            int startX;
            for (startX = (x * 16 - blockVector3.getBlockX()) % width; startX < 0; startX += this.schematic.getWidth()) {}
            int startZ;
            for (startZ = (z * 16 - blockVector3.getBlockZ()) % length; startZ < 0; startZ += this.schematic.getLength()) {}
            for (int chunkX = 0; chunkX < 16; ++chunkX) {
                int schemX = (startX + chunkX) % width;
                for (int chunkZ = 0; chunkZ < 16; ++chunkZ) {
                    int schemZ = (startZ + chunkZ) % length;
                    for (int chunkY = 0; chunkY < this.schematic.getHeight(); ++chunkY) {
                        BlockData block = this.schematic.getBlock(schemX, chunkY, schemZ);
                        data.setBlock(chunkX, chunkY, chunkZ, block);
                    }
                }
            }
            int minimumX = x * 16 - startX;
            int minimumZ = z * 16 - startZ;
            this.plugin.getPlotManager().registerPlot(Plot.Type.PLAYER, new Plot.Cuboid(minimumX, minimumX + 39, 0, 256, minimumZ, minimumZ + 39));
            this.plugin.getPlotManager().registerPlot(Plot.Type.PLAYER, new Plot.Cuboid(minimumX + 41, minimumX + 80, 0, 256, minimumZ, minimumZ + 39));
            this.plugin.getPlotManager().registerPlot(Plot.Type.PLAYER, new Plot.Cuboid(minimumX + 82, minimumX + 121, 0, 256, minimumZ, minimumZ + 39));
            this.plugin.getPlotManager().registerPlot(Plot.Type.PLAYER, new Plot.Cuboid(minimumX, minimumX + 39, 0, 256, minimumZ + 41, minimumZ + 80));
            this.plugin.getPlotManager().registerPlot(Plot.Type.PLAYER, new Plot.Cuboid(minimumX + 82, 121, 0, 256, minimumZ + 41, minimumZ + 80));
            this.plugin.getPlotManager().registerPlot(Plot.Type.PLAYER, new Plot.Cuboid(minimumX, minimumX + 39, 0, 256, minimumZ + 82, minimumZ + 121));
            this.plugin.getPlotManager().registerPlot(Plot.Type.PLAYER, new Plot.Cuboid(minimumX + 41, minimumX + 80, 0, 256, minimumZ + 82, minimumZ + 121));
            this.plugin.getPlotManager().registerPlot(Plot.Type.PLAYER, new Plot.Cuboid(minimumX + 82, minimumX + 121, 0, 256, minimumZ + 82, minimumZ + 121));
            this.plugin.getPlotManager().registerPlot(Plot.Type.GUILD, new Plot.Cuboid(minimumX + 41, minimumX + 80, 0, 256, minimumZ + 41, minimumZ + 80));
        }
        return data;
    }

    public Location getFixedSpawnLocation( World world,  Random random) {
        return super.getFixedSpawnLocation(world, random);
    }



}
