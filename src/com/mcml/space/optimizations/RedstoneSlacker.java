package com.mcml.space.optimizations;

import com.google.common.collect.Maps;
import com.mcml.space.config.Optimizes;
import com.mcml.space.core.EscapeLag;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class RedstoneSlacker implements Listener {
   private static final HashMap<Location, Integer> CHECKED_TIMES = Maps.newHashMap();
   private static int maxCounts;
   private Location notifyLocation;

   public static void init(Plugin plugin) {
      if (Optimizes.AntiRedstoneenable) {
         BukkitScheduler var10000 = Bukkit.getScheduler();
         HashMap var10002 = CHECKED_TIMES;
         var10000.runTaskTimer(plugin, var10002::clear, 0L, AzureAPI.toTicks(TimeUnit.SECONDS, (long)Optimizes.AntiRedstoneTimes));
         maxCounts = Optimizes.AntiRedstoneTimes * 4;
         Bukkit.getPluginManager().registerEvents(new RedstoneSlacker(), plugin);
         AzureAPI.log(Locale.isNative() ? "子模块 - 中频红石 已启动" : "Submodule - RedstoneSlacker has been enabled");
      }
   }

   @EventHandler(
      priority = EventPriority.LOW,
      ignoreCancelled = true
   )
   public void onRedstone(BlockRedstoneEvent evt) {
      if (evt.getOldCurrent() <= evt.getNewCurrent()) {
         Block block = evt.getBlock();
         Location location = block.getLocation();
         Integer times = (Integer)CHECKED_TIMES.get(location);
         CHECKED_TIMES.put(location, times == null ? (times = 0) : (times = times + 1));
         if (times > maxCounts) {
            if (Optimizes.AntiRedstoneRemoveBlockList.contains(block.getType().name())) {
               Bukkit.getScheduler().runTask(EscapeLag.plugin, () -> {
                  if (Optimizes.dropRedstone) {
                     block.breakNaturally();
                  } else {
                     block.setType(Material.AIR);
                  }

               });
               if (this.notifyLocation != null && this.notifyLocation.getWorld().equals(location.getWorld()) && this.notifyLocation.distance(location) <= 16.0D) {
                  this.notifyLocation = location;
                  return;
               }

               String message = Optimizes.AntiRedstoneMessage;
               message = StringUtils.replace(message, "%location%", "(" + location.getWorld().getName() + ": " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + ")");
               AzureAPI.bc(message);
            }

         }
      }
   }
}
