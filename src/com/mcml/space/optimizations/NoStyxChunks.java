package com.mcml.space.optimizations;

import com.mcml.space.core.EscapeLag;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.VersionLevel;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

public class NoStyxChunks {
   public static long totalUnloadedChunks;

   public static void init(Plugin plugin) {
   }

   private static void handleChunksAt(Location from, Player player) {
      Bukkit.getScheduler().runTask(EscapeLag.plugin, () -> {
         World world = from.getWorld();
         if (world.getPlayers().isEmpty()) {
            Chunk[] var3 = world.getLoadedChunks();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Chunk each = var3[var5];
               each.unload();
               ++totalUnloadedChunks;
            }
         } else {
            unloadChunksAt(from, player);
         }

      });
   }

   public static void unloadChunksAt(Location from, Player player) {
      World world = from.getWorld();
      int view = AzureAPI.viewDistanceBlock(player);
      int edgeX = from.getBlockX() + view;
      int edgeZ = from.getBlockZ() + view;

      for(int x = from.getBlockX() - view; x <= edgeX; x += 16) {
         for(int z = from.getBlockZ() - view; z <= edgeZ; z += 16) {
            int chunkX = x >> 4;
            int chunkZ = z >> 4;
            if (VersionLevel.isPaper() || !DelayedChunkKeeper.DEALYED_CHUNKS.contains(AzureAPI.wrapCoord(chunkX, chunkZ))) {
               Chunk chunk = world.getChunkAt(chunkX, chunkZ);
               if (!world.isChunkInUse(chunkX, chunkZ)) {
                  chunk.unload();
                  ++totalUnloadedChunks;
               }
            }
         }
      }

   }

   private static class ExitDetector implements Listener {
      @EventHandler(
         priority = EventPriority.HIGHEST
      )
      public void onQuit(PlayerQuitEvent evt) {
         NoStyxChunks.handleChunksAt(evt.getPlayer().getLocation(), evt.getPlayer());
      }
   }

   private static class TeleportDetector implements Listener {
      @EventHandler(
         priority = EventPriority.HIGHEST,
         ignoreCancelled = true
      )
      public void onTeleport(PlayerTeleportEvent evt) {
         NoStyxChunks.handleChunksAt(evt.getFrom(), evt.getPlayer());
      }
   }
}
