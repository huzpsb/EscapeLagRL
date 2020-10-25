package com.mcml.space.core;

import com.mcml.space.config.Core;
import com.mcml.space.util.Perms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class UpgradeHelper implements Listener {
   private static boolean isKnown;

   public static void init(Plugin plugin) {
      if (!Core.AutoUpdate) {
         Bukkit.getPluginManager().registerEvents(new UpgradeHelper(), plugin);
      }
   }

   @EventHandler
   public void react(PlayerJoinEvent evt) {
      if (!isKnown) {
         Player player = evt.getPlayer();
         if (Perms.has(player)) {
            player.sendMessage("§3§lE§b§lL §b> §f输入 §3/el updateon §f开启自动更新, 永远保持高效运作! ");
         }

      }
   }

   public static void setKnown(boolean isKnown) {
      UpgradeHelper.isKnown = isKnown;
   }
}
