package com.mcml.space.features;

import com.mcml.space.config.Features;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import com.mcml.space.util.VersionLevel;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ExplosionController {
    public static void init(Plugin plugin) {
        if (Features.controlExplode) {
            Bukkit.getPluginManager().registerEvents(new ExplosionController.EntityDetector(), plugin);
            if (VersionLevel.isHigherEquals(VersionLevel.Version.MINECRAFT_1_8_R2)) {
                Bukkit.getPluginManager().registerEvents(new ExplosionController.BlockDetector(), plugin);
            }

            AzureAPI.log(Locale.isNative() ? "子模块 - 可控爆炸 已启动" : "Submodule - ExplosionController has been enabled");
        }
    }

    private static void handleExplode(Cancellable evt, List<Block> blocks) {
        if (Features.explodeControlType.equalsIgnoreCase("NoBlockBreak")) {
            blocks.clear();
        }

        if (Features.explodeControlType.equalsIgnoreCase("NoExplode")) {
            evt.setCancelled(true);
        }

    }

    private static class EntityDetector implements Listener {
        private EntityDetector() {
        }

        // $FF: synthetic method
        EntityDetector(Object x0) {
            this();
        }

        @EventHandler(
                priority = EventPriority.LOW,
                ignoreCancelled = true
        )
        public void onExplode(EntityExplodeEvent evt) {
            ExplosionController.handleExplode(evt, evt.blockList());
        }
    }

    private static class BlockDetector implements Listener {
        private BlockDetector() {
        }

        // $FF: synthetic method
        BlockDetector(Object x0) {
            this();
        }

        @EventHandler(
                priority = EventPriority.LOW,
                ignoreCancelled = true
        )
        public void onExplode(BlockExplodeEvent evt) {
            ExplosionController.handleExplode(evt, evt.blockList());
        }
    }
}
