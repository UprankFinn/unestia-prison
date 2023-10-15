package net.unestia.prison.world.schematic;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import org.bukkit.block.data.BlockData;

public class Schematic {

    private final BlockVector3 size;
    private final BlockVector3 origin;
    private final BlockData[][][] blocks;

    public Schematic(Clipboard clipboard) {
        this.size = clipboard.getDimensions();
        this.origin = clipboard.getOrigin();
        this.blocks = new BlockData[(int)this.getWidth()][(int)this.getHeight()][(int)this.getLength()];
        for (int x = 0; x < this.getWidth(); ++x) {
            for (int z = 0; z < this.getLength(); ++z) {
                for (int y = 0; y < this.getHeight(); ++y) {
                    this.setBlock(x, y, z, BukkitAdapter.adapt((BlockStateHolder)clipboard.getBlock(BlockVector3.at(x, y, z))));
                }
            }
        }
    }

    public BlockData getBlock(final int x, final int y, final int z) throws IndexOutOfBoundsException {
        return this.blocks[x][y][z];
    }

    public void setBlock(final int x, final int y, final int z, final BlockData blockData) throws IndexOutOfBoundsException {
        this.blocks[x][y][z] = blockData;
    }

    public BlockVector3 getSize() {
        return this.size;
    }

    public BlockVector3 getOrigin() {
        return this.origin;
    }

    public Integer getWidth() {
        return this.size.getBlockX();
    }

    public Integer getLength() {
        return this.size.getBlockZ();
    }

    public Integer getHeight() {
        return this.size.getBlockY();
    }

}
