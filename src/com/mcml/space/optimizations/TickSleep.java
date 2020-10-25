package com.mcml.space.optimizations;

import com.mcml.space.config.Optimizes;
import com.mcml.space.core.PlayerList;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TickSleep {
   public static void init(Plugin plugin) {
      if (Optimizes.TPSSleepNoOneFreezeenable) {
         if (Bukkit.getPluginManager().getPlugin("Yum") != null) {
            AzureAPI.log("您的服务器安装有Yum,此插件与【TPSSleep】功能冲突，此功能被禁用...");
            return;
         }

         Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            public void run() {
               if (PlayerList.isEmpty()) {
                  try {
                     Thread.sleep(TickSleep.getTargetSleepTime(5L));
                  } catch (InterruptedException var3) {
                     Logger.getLogger(TickSleep.class.getName()).log(Level.SEVERE, (String)null, var3);
                  }
               } else if (!Optimizes.TPSSleepSleepMode.equalsIgnoreCase("NoUse")) {
                  try {
                     Thread.sleep(TickSleep.getTargetSleepTime((long)Integer.parseInt(Optimizes.TPSSleepSleepMode)));
                  } catch (InterruptedException var2) {
                     Logger.getLogger(TickSleep.class.getName()).log(Level.SEVERE, (String)null, var2);
                  }
               }

            }
         }, 1L, 1L);
         AzureAPI.log(Locale.isNative() ? "子模块 - 线程休眠 已启动" : "Submodule - TickSleep has been enabled");
      }

   }

   static long getTargetSleepTime(long raw) {
      return 50L - 5L * raw / 2L;
   }
}
