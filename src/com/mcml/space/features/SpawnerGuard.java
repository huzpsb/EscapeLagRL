package com.mcml.space.features;

import com.mcml.space.config.Features;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import com.mcml.space.util.Perms;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class SpawnerGuard implements Listener {
   public static void init(Plugin plugin) {
      if (Features.preventSpawnerModify) {
         Bukkit.getPluginManager().registerEvents(new SpawnerGuard(), plugin);
         AzureAPI.log(Locale.isNative() ? "子模块 - 刷怪保护 已启动" : "Submodule - SpawnerGuard has been enabled");
      }
   }

   @EventHandler(
      priority = EventPriority.LOW,
      ignoreCancelled = true
   )
   public void onModify(PlayerInteractEvent evt) {
      if (evt.getItem() != null && evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
         if (!Perms.has(evt.getPlayer())) {
            if (evt.getClickedBlock().getType() == Material.MOB_SPAWNER) {
               Material type = evt.getItem().getType();
               if (type == Material.MONSTER_EGG || type == Material.MONSTER_EGGS) {
                  evt.setCancelled(true);
               }
            }

         }
      }
   }
}
