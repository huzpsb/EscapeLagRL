package com.mcml.space.optimizations;

import com.mcml.space.config.Optimizes;
import com.mcml.space.core.EscapeLag;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SmartConfigs {
   public static void init(Plugin plugin, boolean force) {
      if (force || Optimizes.AutoSetenable) {
         File backupFolder = new File("backup_configurations");
         if (backupFolder.isDirectory()) {
            backupFolder.delete();
         }

         backupFolder.mkdir();
         File bukkitFile = new File("bukkit.yml");
         if (bukkitFile.exists()) {
            configsBukkit(bukkitFile);
         }

         File spigotFile = new File("spigot.yml");
         if (spigotFile.exists()) {
            configsSpigot(spigotFile);
         }

         Optimizes.AutoSaveenable = false;
         EscapeLag.plugin.setupConfig("OptimizeConfig.yml", Optimizes.class);
         AzureAPI.log(Locale.isNative() ? "子模块 - 智能配置 已启动" : "Submodule - SmartConfigs has been enabled");
      }
   }

   private static FileConfiguration loadAndBackup(File file) {
      try {
         File backup = new File("backup_configurations/", file.getName());
         backup.createNewFile();
         FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
         configuration.save(backup);
         return configuration;
      } catch (Throwable var3) {
         throw var3;
      }
   }

   private static void configsBukkit(File bukkitFile) {
      FileConfiguration bukkitConfig = loadAndBackup(bukkitFile);
      long heapMb = Runtime.getRuntime().maxMemory() / 1024L / 1024L;
      long targetAutoSaveTicks;
      if (heapMb <= 6144L) {
         if (bukkitConfig.getInt("chunk-gc.period-in-ticks") == 600) {
            targetAutoSaveTicks = heapMb / 6144L * 36000L;
            bukkitConfig.set("chunk-gc.period-in-ticks", targetAutoSaveTicks < 6000L ? 6000L : targetAutoSaveTicks);
         }

         if (bukkitConfig.getInt("chunk-gc.load-threshold") == 0) {
            targetAutoSaveTicks = heapMb / 10L;
            bukkitConfig.set("chunk-gc.load-threshold", targetAutoSaveTicks < 200L ? 200L : targetAutoSaveTicks);
         }

         if (bukkitConfig.getInt("ticks-per.autosave") == 6000) {
            targetAutoSaveTicks = 6144L / heapMb * 6000L;
            bukkitConfig.set("ticks-per.animal-spawns", targetAutoSaveTicks > 18000L ? 18000L : targetAutoSaveTicks);
         }
      }

      if (heapMb <= 4096L && bukkitConfig.getInt("ticks-per.animal-spawns") == 400) {
         targetAutoSaveTicks = heapMb / 20L;
         bukkitConfig.set("ticks-per.animal-spawns", targetAutoSaveTicks < 200L ? 200L : targetAutoSaveTicks);
      }

   }

   private static void configsSpigot(File spigotFile) {
      FileConfiguration spigotConfig = loadAndBackup(spigotFile);
   }
}
