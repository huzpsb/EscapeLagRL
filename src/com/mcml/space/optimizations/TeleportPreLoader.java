package com.mcml.space.optimizations;

import com.mcml.space.config.Optimizes;
import com.mcml.space.core.EscapeLag;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Utils;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

public class TeleportPreLoader implements Listener {
   private boolean isPreLoading;
   private int nowteleportid = 0;
   private HashMap<Integer, Integer> nowint = new HashMap();

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void TeleportLoader(PlayerTeleportEvent event) {
      if (Optimizes.TeleportPreLoaderenable) {
         Player player = event.getPlayer();
         if (!player.isOnline()) {
            return;
         }

         if (!this.shouldReload(event.getFrom(), event.getTo(), player)) {
            return;
         }

         if (player.getVehicle() != null) {
            return;
         }

         if (event.getFrom().getBlock().getType() == Material.ENDER_PORTAL) {
            return;
         }

         ++this.nowteleportid;
         if (!this.isPreLoading) {
            event.setCancelled(true);
            int thistpid = this.nowteleportid;
            List<AzureAPI.ChunkCoord> chunks = Utils.getShouldUseChunks(event.getTo());
            int cs = chunks.size();
            if (this.nowint.get(thistpid) == null) {
               this.nowint.put(thistpid, 0);
            }

            World world = event.getTo().getWorld();
            Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, () -> {
               this.nowint.put(thistpid, (Integer)this.nowint.get(thistpid) + cs / 10);

               for(int i = (Integer)this.nowint.get(thistpid) - cs / 10; i < (Integer)this.nowint.get(thistpid); ++i) {
                  AzureAPI.ChunkCoord coord = (AzureAPI.ChunkCoord)chunks.get(i);
                  world.loadChunk(coord.getChunkX(), coord.getChunkZ());
               }

            }, 1L);
            Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, () -> {
               this.nowint.put(thistpid, (Integer)this.nowint.get(thistpid) + cs / 10);

               for(int i = (Integer)this.nowint.get(thistpid) - cs / 10; i < (Integer)this.nowint.get(thistpid); ++i) {
                  AzureAPI.ChunkCoord coord = (AzureAPI.ChunkCoord)chunks.get(i);
                  world.loadChunk(coord.getChunkX(), coord.getChunkZ());
               }

            }, 2L);
            Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, () -> {
               this.nowint.put(thistpid, (Integer)this.nowint.get(thistpid) + cs / 10);

               for(int i = (Integer)this.nowint.get(thistpid) - cs / 10; i < (Integer)this.nowint.get(thistpid); ++i) {
                  AzureAPI.ChunkCoord coord = (AzureAPI.ChunkCoord)chunks.get(i);
                  world.loadChunk(coord.getChunkX(), coord.getChunkZ());
               }

            }, 3L);
            Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, () -> {
               this.nowint.put(thistpid, (Integer)this.nowint.get(thistpid) + cs / 10);

               for(int i = (Integer)this.nowint.get(thistpid) - cs / 10; i < (Integer)this.nowint.get(thistpid); ++i) {
                  AzureAPI.ChunkCoord coord = (AzureAPI.ChunkCoord)chunks.get(i);
                  world.loadChunk(coord.getChunkX(), coord.getChunkZ());
               }

            }, 4L);
            Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, () -> {
               this.nowint.put(thistpid, (Integer)this.nowint.get(thistpid) + cs / 10);

               for(int i = (Integer)this.nowint.get(thistpid) - cs / 10; i < (Integer)this.nowint.get(thistpid); ++i) {
                  AzureAPI.ChunkCoord coord = (AzureAPI.ChunkCoord)chunks.get(i);
                  world.loadChunk(coord.getChunkX(), coord.getChunkZ());
               }

            }, 5L);
            Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, () -> {
               this.nowint.put(thistpid, (Integer)this.nowint.get(thistpid) + cs / 10);

               for(int i = (Integer)this.nowint.get(thistpid) - cs / 10; i < (Integer)this.nowint.get(thistpid); ++i) {
                  AzureAPI.ChunkCoord coord = (AzureAPI.ChunkCoord)chunks.get(i);
                  world.loadChunk(coord.getChunkX(), coord.getChunkZ());
               }

            }, 6L);
            Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, () -> {
               this.nowint.put(thistpid, (Integer)this.nowint.get(thistpid) + cs / 10);

               for(int i = (Integer)this.nowint.get(thistpid) - cs / 10; i < (Integer)this.nowint.get(thistpid); ++i) {
                  AzureAPI.ChunkCoord coord = (AzureAPI.ChunkCoord)chunks.get(i);
                  world.loadChunk(coord.getChunkX(), coord.getChunkZ());
               }

            }, 7L);
            Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, () -> {
               this.nowint.put(thistpid, (Integer)this.nowint.get(thistpid) + cs / 10);

               for(int i = (Integer)this.nowint.get(thistpid) - cs / 10; i < (Integer)this.nowint.get(thistpid); ++i) {
                  AzureAPI.ChunkCoord coord = (AzureAPI.ChunkCoord)chunks.get(i);
                  world.loadChunk(coord.getChunkX(), coord.getChunkZ());
               }

            }, 8L);
            Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, () -> {
               this.nowint.put(thistpid, (Integer)this.nowint.get(thistpid) + cs / 10);

               for(int i = (Integer)this.nowint.get(thistpid) - cs / 10; i < (Integer)this.nowint.get(thistpid); ++i) {
                  AzureAPI.ChunkCoord coord = (AzureAPI.ChunkCoord)chunks.get(i);
                  world.loadChunk(coord.getChunkX(), coord.getChunkZ());
               }

            }, 9L);
            Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, () -> {
               this.isPreLoading = true;
               player.teleport(event.getTo());
               this.isPreLoading = false;
               this.nowint.remove(thistpid);
            }, 10L);
         }
      }

   }

   private boolean shouldReload(Location from, Location to, Player player) {
      if (from.getWorld() != to.getWorld()) {
         return true;
      } else {
         Vector fvec = from.toVector();
         Vector tvec = to.toVector();
         double distance = fvec.distance(tvec);
         return distance >= 6.0D;
      }
   }
}
