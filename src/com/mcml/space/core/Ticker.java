package com.mcml.space.core;

import com.mcml.space.config.Features;
import com.mcml.space.util.AzureAPI;
import java.lang.management.ManagementFactory;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Ticker {
   private static int GameTick;
   public static int TPS;
   private static BukkitTask GameTask;
   private static Timer TPSTimer;
   private static Timer ThreadMonitorTimer;
   private static long ThreadUseTime;
   private static boolean ThreadLagWarned;
   public static int currentTick;

   public static void init() {
      AzureAPI.log("TPS系统模块已加载...");
      TPSTimer = new Timer();
      TPSTimer.schedule(new TimerTask() {
         public void run() {
            Ticker.TPS = Ticker.GameTick;
            Ticker.GameTick = 0;
         }
      }, 1000L, 1000L);
      if (Features.Monitorenable && Features.MonitorThreadLagWarning) {
         ThreadMonitorTimer = new Timer();
         ThreadMonitorTimer.schedule(new TimerTask() {
            public void run() {
               Ticker.ThreadUseTime++;
               if (Ticker.ThreadUseTime >= Features.MonitorThreadLagPeriod && !Ticker.ThreadLagWarned) {
                  if (Features.MonitorThreadLagDumpStack) {
                     Bukkit.getLogger().log(Level.WARNING, "------------------------------");
                     Bukkit.getLogger().log(Level.WARNING, "[EL侦测系统]服务器主线程已陷入停顿" + Ticker.ThreadUseTime + "ms! 你的服务器卡顿了!");
                     Bukkit.getLogger().log(Level.WARNING, "当前主线程堆栈追踪 (这并非EscapeLag引起的卡顿,EL只负责报告卡顿情况):");
                     AzureAPI.dumpThread(ManagementFactory.getThreadMXBean().getThreadInfo(AzureAPI.serverThread().getId(), Integer.MAX_VALUE), Bukkit.getLogger());
                     Bukkit.getLogger().log(Level.WARNING, "------------------------------");
                  } else {
                     AzureAPI.log("服务器主线程已陷入停顿! 你的服务器卡顿了!");
                     AzureAPI.log("这并非EscapeLag引起的卡顿,EL只负责报告卡顿情况!");
                  }

                  Ticker.ThreadLagWarned = true;
               }

            }
         }, 1L, 1L);
      }

      GameTask = Bukkit.getScheduler().runTaskTimer(EscapeLag.plugin, new Runnable() {
         public void run() {
            Ticker.GameTick++;
            ++Ticker.currentTick;
            if (Ticker.ThreadLagWarned) {
               Ticker.ThreadLagWarned = false;
               Bukkit.getLogger().log(Level.WARNING, "[EL侦测系统]服务器总计停顿" + Ticker.ThreadUseTime + "ms!");
            }

            Ticker.ThreadUseTime = 0L;
         }
      }, 1L, 1L);
   }

   public static void close() {
      TPSTimer.cancel();
      GameTask.cancel();
      if (Features.Monitorenable && Features.MonitorThreadLagWarning) {
         ThreadMonitorTimer.cancel();
      }

      AzureAPI.log("TPS系统模块已卸载...");
   }
}
