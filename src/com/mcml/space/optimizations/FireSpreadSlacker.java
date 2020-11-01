package com.mcml.space.optimizations;

import com.google.common.collect.Maps;
import com.mcml.space.config.Optimizes;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class FireSpreadSlacker implements Listener {
    private static final HashMap<AzureAPI.ChunkCoord, Long> CHECKED_CHUNKS = Maps.newHashMap();

    public static void init(Plugin plugin) {
        if (Optimizes.FireLimitorenable) {
            Bukkit.getPluginManager().registerEvents(new FireSpreadSlacker(), plugin);
            BukkitScheduler var10000 = Bukkit.getScheduler();
            HashMap var10002 = CHECKED_CHUNKS;
            var10000.runTaskTimer(plugin, var10002::clear, 0L, AzureAPI.toTicks(TimeUnit.SECONDS, 90L));
            AzureAPI.log(Locale.isNative() ? "子模块 - 火蔓延控制 已启动" : "Submodule - FireSpreadSlacker has been enabled");
        }
    }

    public static boolean isFireOverclock(AzureAPI.ChunkCoord coord) {
        Long checked = CHECKED_CHUNKS.get(coord);
        if (checked == null) {
            CHECKED_CHUNKS.put(coord, System.currentTimeMillis());
            return false;
        } else {
            return checked + Optimizes.FireLimitorPeriod >= System.currentTimeMillis();
        }
    }

    @EventHandler(
            priority = EventPriority.LOW,
            ignoreCancelled = true
    )
    public void onSpread(BlockIgniteEvent event) {
        if (event.getCause() == IgniteCause.SPREAD) {
            Chunk chunk = event.getBlock().getChunk();
            if (isFireOverclock(AzureAPI.wrapCoord(chunk.getX(), chunk.getZ()))) {
                event.setCancelled(true);
            }

        }
    }
}
