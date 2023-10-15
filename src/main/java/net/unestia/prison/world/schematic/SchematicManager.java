package net.unestia.prison.world.schematic;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.util.io.Closer;
import org.bukkit.Bukkit;

import java.io.*;

public class SchematicManager {

    public Schematic loadSchematic(final String schematicName) {
        if (schematicName == null || schematicName.isEmpty()) {
            return null;
        }
        final File file = new File(new File(((WorldEditPlugin) WorldEditPlugin.getPlugin((Class) WorldEditPlugin.class)).getDataFolder(), "schematics"), schematicName + ".schem");
        if (!file.exists()) {
            Bukkit.getConsoleSender().sendMessage("§4[!] §cSchematic (§e" + schematicName + "§c) could not be found!");
            return null;
        }
        final ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(file);
        if (clipboardFormat == null) {
            Bukkit.getConsoleSender().sendMessage("§4[!] §cSchematic (§e" + schematicName + "§c) could not be loaded!");
            return null;
        }
        try {
            Bukkit.getConsoleSender().sendMessage("§2[!] §aSchematic (§e" + schematicName + "§a) was loaded successfully!");
            return new Schematic(this.loadSchematic(file, clipboardFormat));
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§4[!] §cSchematic (§e" + schematicName + "§c) could not be initialized!");
            return null;
        }
    }

    public Clipboard loadSchematic(File file, ClipboardFormat format) throws IOException {
        try (final Closer closer = Closer.create()) {
            final FileInputStream fileInputStream = (FileInputStream) closer.register((Closeable) new FileInputStream(file));
            final BufferedInputStream bufferedInputStream = (BufferedInputStream) closer.register((Closeable) new BufferedInputStream(fileInputStream));
            final ClipboardReader reader = (ClipboardReader) closer.register((Closeable) format.getReader(bufferedInputStream));
            return reader.read();
        }
    }

}
