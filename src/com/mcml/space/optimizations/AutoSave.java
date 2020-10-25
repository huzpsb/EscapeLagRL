package com.mcml.space.optimizations;

import com.mcml.space.config.Optimizes;
import com.mcml.space.core.EscapeLag;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AutoSave implements Listener {
   private HashMap<Player, Integer> TaskId = new HashMap();
   private static HashMap<Player, Chunk> PlayerInChunkMap = new HashMap();
   private static HashMap<Player, Chunk> PlayerClickedMap = new HashMap();

   @EventHandler
   public void JoinTaskGiver(PlayerJoinEvent e) {
      if (Optimizes.AutoSaveenable) {
         Player p = e.getPlayer();
         this.TaskId.put(p, Bukkit.getScheduler().scheduleSyncRepeatingTask(EscapeLag.plugin, () -> {
            Chunk chunk = p.getLocation().getChunk();
            Chunk LastChunk = (Chunk)PlayerInChunkMap.get(p);
            if (LastChunk != chunk) {
               if (LastChunk == null) {
                  PlayerInChunkMap.put(p, chunk);
                  return;
               }

               if (PlayerClickedMap.containsValue(LastChunk)) {
                  return;
               }

               World world = LastChunk.getWorld();
               if (LastChunk.isLoaded()) {
                  world.unloadChunk(LastChunk.getX(), LastChunk.getZ(), true);
                  LastChunk.load();
               } else {
                  LastChunk.load();
                  world.unloadChunk(LastChunk.getX(), LastChunk.getZ(), true);
               }
            }

            PlayerInChunkMap.put(p, chunk);
            p.saveData();
         }, Optimizes.AutoSaveInterval * 20L, Optimizes.AutoSaveInterval * 20L));
      }
   }

   @EventHandler
   public void ClickBypassList(PlayerInteractEvent e) {
      if (e.getClickedBlock() != null) {
         if (Optimizes.AutoSaveenable) {
            Player p = e.getPlayer();
            PlayerClickedMap.put(p, e.getClickedBlock().getChunk());
         }
      }
   }

   @EventHandler
   public void QuitCancelled(PlayerQuitEvent e) {
      if (Optimizes.AutoSaveenable) {
         Player p = e.getPlayer();
         if (this.TaskId.get(p) != null) {
            Bukkit.getScheduler().cancelTask((Integer)this.TaskId.get(p));
            this.TaskId.remove(p);
         }

      }
   }
}
