package club.quar.util.world;

import club.quar.plugin.QuarPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.concurrent.FutureTask;

/**
 * Basic Block utilities that allow handling blocks easier.
 */
public final class BlockUtil {

    /**
     * Make sure nobody can't instantiate the class.
     */
    private BlockUtil() {
        throw new RuntimeException("BlockUtil class is a utility class and should not be instantiated.");
    }

    /**
     * Attempts to get the block asynchronously.
     * If the block is in is not loaded will return null.
     *
     * @param location The location of the block requested.
     * @return The block in the given position.
     */
    public static Block getBlockAsync(Location location) {
        if (location.getWorld().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
            return location.getWorld().getBlockAt(location);
        } else {
            return null;
        }
    }

    /**
     * Attempts to get the block asynchronously.
     * If the block is in an unloaded chunk this method will force it to load and return the block position.
     *
     * Warning this method can lag out the thread your application is running on.
     *
     * @param location The location of the block requested.
     * @return The block in the given position.
     */
    @Deprecated
    public static   Block getBlockAsyncForced(Location location) {
        if (location.getWorld().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
            return location.getBlock();
        } else {
            final FutureTask<Block> futureTask = new FutureTask<>(() -> {
                location.getWorld().loadChunk(location.getBlockX() >> 4, location.getBlockZ() >> 4);
                return location.getBlock();
            });

            Bukkit.getScheduler().runTask(QuarPlugin.getInstance(), futureTask);

            try {
                return futureTask.get();
            } catch (final Exception exception) {
                exception.printStackTrace();
            }
            return null;
        }
    }
}
