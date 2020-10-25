package com.mcml.space.optimizations;

import com.google.common.collect.Sets;
import com.mcml.space.config.OptimizesChunk;
import com.mcml.space.core.EscapeLag;
import com.mcml.space.core.Ticker;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import com.mcml.space.util.VersionLevel;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;

public class DelayedChunkKeeper implements Listener {
   @Nullable
   public static final Set<AzureAPI.ChunkCoord> DEALYED_CHUNKS = VersionLevel.isPaper() ? null : Sets.newHashSet();
   private int unloadedChunks;
   private long currentTick = -2147483648L;

   public static void init(Plugin plugin) {
      if (OptimizesChunk.enableDelayedChunkKeeper || OptimizesChunk.delayedChunkKeeper_maxUnloadChunksPerTick > 0 && !VersionLevel.isPaper()) {
         Bukkit.getPluginManager().registerEvents(new DelayedChunkKeeper(), plugin);
         AzureAPI.log(Locale.isNative() ? "子模块 - 区块延时 已启动" : "Submodule - DelayedChunkKeeper has been enabled");
      }
   }

   @EventHandler(
      priority = EventPriority.LOWEST,
      ignoreCancelled = true
   )
   public void onChunkUnload(ChunkUnloadEvent evt) {
      World world = evt.getWorld();
      Chunk chunk = evt.getChunk();
      AzureAPI.ChunkCoord coord = AzureAPI.wrapCoord(chunk.getX(), chunk.getZ());
      if (OptimizesChunk.delayedChunkKeeper_maxUnloadChunksPerTick <= 0) {
         if (this.currentTick == (long)Ticker.currentTick) {
            if (++this.unloadedChunks > OptimizesChunk.delayedChunkKeeper_maxUnloadChunksPerTick && VersionLevel.isPaper() && !DEALYED_CHUNKS.contains(coord)) {
               int skipTicks = OptimizesChunk.delayedChunkKeeper_postSkipTicks - 1;
               Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, () -> {
                  if (world.isChunkLoaded(chunk) && !world.isChunkInUse(chunk.getX(), chunk.getZ())) {
                     chunk.unload();
                  }

               }, skipTicks < 0 ? 0L : (long)skipTicks);
               evt.setCancelled(true);
               return;
            }
         } else {
            this.unloadedChunks = 1;
            this.currentTick = (long)Ticker.currentTick;
         }
      }

      if (!VersionLevel.isPaper() && !DEALYED_CHUNKS.contains(coord)) {
         DEALYED_CHUNKS.add(coord);
         Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, () -> {
            if (world.isChunkLoaded(chunk) && !world.isChunkInUse(chunk.getX(), chunk.getZ())) {
               chunk.unload();
            }

            DEALYED_CHUNKS.remove(coord);
         }, AzureAPI.toTicks(TimeUnit.SECONDS, (long)OptimizesChunk.delayedChunkKeeper_delayInSeconds));
      }

   }
}
