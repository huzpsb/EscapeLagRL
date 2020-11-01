package com.mcml.space.patches;

import com.mcml.space.config.PatchesDupeFixes;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

import java.util.Iterator;
import java.util.Map;

public class CancelledPlacementPatch implements Listener {
    public static void init(Plugin plugin) {
        if (PatchesDupeFixes.enableCancelledPlacementDupeFixes) {
            Bukkit.getPluginManager().registerEvents(new CancelledPlacementPatch(), plugin);
            AzureAPI.log(Locale.isNative() ? "子模块 - 取消放置 已启动" : "Submodule - CancelledPlacementPatch has been enabled");
        }
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = false
    )
    public void place(BlockPlaceEvent evt) {
        if (evt.isCancelled()) {
            Player player = evt.getPlayer();
            Map<String, String> radius = PatchesDupeFixes.cancelledPlacementDupeFixes_clearsRadius;
            Iterator var4 = player.getNearbyEntities((double) Integer.valueOf(radius.get("x")), (double) Integer.valueOf(radius.get("y")), (double) Integer.valueOf(radius.get("z"))).iterator();

            while (true) {
                Entity drop;
                Material material;
                do {
                    do {
                        if (!var4.hasNext()) {
                            return;
                        }

                        drop = (Entity) var4.next();
                    } while (drop.getType() != EntityType.DROPPED_ITEM);

                    Item item = (Item) drop;
                    material = item.getItemStack().getType();
                } while (material != Material.SUGAR_CANE && material != Material.CACTUS);

                drop.remove();
            }
        }
    }
}
