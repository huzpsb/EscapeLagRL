package com.mcml.space.patches;

import com.mcml.space.config.Patches;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

public class CalculationAbusePatch implements Listener {
    private final String calcLabel = "//calc ";

    public static void init(Plugin plugin) {
        Plugin worldedit = Bukkit.getPluginManager().getPlugin("WorldEdit");
        if (worldedit != null && !hasCalculationPerms(worldedit.getDescription().getVersion())) {
            Bukkit.getPluginManager().registerEvents(new CalculationAbusePatch(), plugin);
            AzureAPI.log(Locale.isNative() ? "子模块 - 计算滥用修复 已启动" : "Submodule - CalculationAbusePatch has been enabled");
        }
    }

    public static boolean hasCalculationPerms(String worldeditVersion) {
        try {
            if (Integer.valueOf(worldeditVersion.charAt(0)) > 6) {
                return true;
            }

            if (Integer.valueOf(worldeditVersion.charAt(2)) > 1) {
                return true;
            }

            if (Integer.valueOf(worldeditVersion.charAt(4)) > 1) {
                return true;
            }
        } catch (Throwable var2) {
        }

        return false;
    }

    @EventHandler(
            priority = EventPriority.LOW,
            ignoreCancelled = true
    )
    public void onCommand(PlayerCommandPreprocessEvent evt) {
        String command = evt.getMessage();
        if (command.startsWith("//calc ") && !evt.getPlayer().isOp() && !AzureAPI.hasPerm(evt.getPlayer(), "worldedit.calc")) {
            AzureAPI.log(evt.getPlayer(), Patches.AntiWEcalcWarnMessage);
            evt.setCancelled(true);
        }
    }
}
