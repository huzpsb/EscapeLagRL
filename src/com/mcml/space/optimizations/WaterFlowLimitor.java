package com.mcml.space.optimizations;

import com.mcml.space.config.Optimizes;
import com.mcml.space.core.EscapeLag;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class WaterFlowLimitor implements Listener {
   private static final HashMap<Chunk, Long> ChunkLastTime = new HashMap();
   private static final HashMap<Chunk, Integer> CheckedTimes = new HashMap();

   public WaterFlowLimitor() {
      Bukkit.getScheduler().runTaskTimer(EscapeLag.plugin, CheckedTimes::clear, 140L, 140L);
   }

   @EventHandler
   public void WaterFowLimitor(BlockFromToEvent event) {
      if (Optimizes.WaterFlowLimitorenable) {
         Block block = event.getBlock();
         Chunk chunk = block.getChunk();
         if (block.getType() == Material.STATIONARY_WATER || block.getType() == Material.STATIONARY_LAVA) {
            if (CheckFast(block.getChunk())) {
               if (CheckedTimes.get(chunk) == null) {
                  CheckedTimes.put(chunk, 0);
               }

               CheckedTimes.put(chunk, (Integer)CheckedTimes.get(chunk) + 1);
               if ((long)(Integer)CheckedTimes.get(chunk) > Optimizes.WaterFlowLimitorPerChunkTimes) {
                  event.setCancelled(true);
               }
            } else {
               ChunkLastTime.put(block.getChunk(), System.currentTimeMillis());
            }
         }
      }

   }

   private static boolean CheckFast(Chunk chunk) {
      if (ChunkLastTime.containsKey(chunk)) {
         return (Long)ChunkLastTime.get(chunk) + 50L > System.currentTimeMillis();
      } else {
         return false;
      }
   }
}
