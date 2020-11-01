package com.mcml.space.optimizations;

import com.mcml.space.config.Optimizes;
import com.mcml.space.core.EscapeLag;
import com.mcml.space.core.PlayerList;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class EmptyRestart implements Listener {
    private BukkitTask RestartTask = null;

    public static void init(Plugin plugin) {
        if (Optimizes.emptyRestart) {
            Bukkit.getPluginManager().registerEvents(new EmptyRestart(), plugin);
            AzureAPI.log(Locale.isNative() ? "子模块 - 闲置重启 已启动" : "Submodule - EmptyRestart has been enabled");
        }
    }

    @EventHandler
    public void stopRestartTask(PlayerJoinEvent event) {
        if (this.RestartTask != null) {
            this.RestartTask.cancel();
            this.RestartTask = null;
        }

    }

    @EventHandler
    public void preparRestart(PlayerQuitEvent evt) {
        if (PlayerList.isEmpty()) {
            this.RestartTask = Bukkit.getScheduler().runTaskLater(EscapeLag.plugin, new Runnable() {
                public void run() {
                    AzureAPI.restartServer("服务器无人,正在重启服务器...");
                }
            }, 6000L);
        }

    }
}
