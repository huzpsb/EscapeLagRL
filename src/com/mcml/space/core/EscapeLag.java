package com.mcml.space.core;

import com.google.common.collect.Maps;
import com.mcml.space.command.EscapeLagCommand;
import com.mcml.space.command.LocalizedHelper;
import com.mcml.space.config.Core;
import com.mcml.space.config.Features;
import com.mcml.space.config.Optimizes;
import com.mcml.space.config.OptimizesChunk;
import com.mcml.space.config.Patches;
import com.mcml.space.config.PatchesDupeFixes;
import com.mcml.space.features.CensoredChat;
import com.mcml.space.features.ExplosionController;
import com.mcml.space.features.FarmProtect;
import com.mcml.space.features.SpawnerGuard;
import com.mcml.space.optimizations.AutoSave;
import com.mcml.space.optimizations.DelayedChunkKeeper;
import com.mcml.space.optimizations.EmptyRestart;
import com.mcml.space.optimizations.FireSpreadSlacker;
import com.mcml.space.optimizations.NoCrowdEntity;
import com.mcml.space.optimizations.NoSpawnChunk;
import com.mcml.space.optimizations.NoStyxChunks;
import com.mcml.space.optimizations.OverloadResume;
import com.mcml.space.optimizations.RedstoneSlacker;
import com.mcml.space.optimizations.TeleportPreLoader;
import com.mcml.space.optimizations.TickSleep;
import com.mcml.space.optimizations.UnloadClear;
import com.mcml.space.optimizations.WaterFlowLimitor;
import com.mcml.space.patches.AntiBedExplode;
import com.mcml.space.patches.AntiCrashChat;
import com.mcml.space.patches.AntiCrashOP;
import com.mcml.space.patches.AntiCrashSign;
import com.mcml.space.patches.AntiDeadDrop;
import com.mcml.space.patches.AntiDestoryUsingChest;
import com.mcml.space.patches.AntiLongStringCrash;
import com.mcml.space.patches.BonemealDupePatch;
import com.mcml.space.patches.CalculationAbusePatch;
import com.mcml.space.patches.CancelledPlacementPatch;
import com.mcml.space.patches.CheatBookBlocker;
import com.mcml.space.patches.ContainerPortalPatch;
import com.mcml.space.patches.DupeLoginPatch;
import com.mcml.space.patches.NegativeItemPatch;
import com.mcml.space.patches.NetherHopperDupePatch;
import com.mcml.space.patches.RailsMachine;
import com.mcml.space.patches.ValidateActions;
import com.mcml.space.patches.ZeroHealthPatch;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Configurable;
import com.mcml.space.util.Locale;
import com.mcml.space.util.Perms;
import com.mcml.space.util.VersionLevel;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class EscapeLag extends JavaPlugin {
   public static EscapeLag plugin;
   public static final String CONFIG_CORE = "CoreConfig.yml";
   public static final String CONFIG_FEATURES = "FeaturesConfig.yml";
   public static final String CONFIG_PATCHES = "BugPatchesConfig.yml";
   public static final String CONFIG_PATCH_DUPE_FIXES = "patches/dupe_fixes.yml";
   public static final String CONFIG_OPTIMIZES = "OptimizeConfig.yml";
   public static final String CONFIG_OPTIMIZE_DUPE_FIXES = "optimizes/chunks.yml";
   public static final Map<String, AzureAPI.Coord<File, FileConfiguration>> configurations = Maps.newHashMap();
   public static final String GLOBAL_PERMS = "escapelag.admin";

   public void onEnable() {
      plugin = this;
      this.setupConfigs();
      Locale.checkLang(Core.lang);
      DataManager.init();
      Perms.bind("escapelag.admin");
      AutoSetServer(false);
      AzureAPI.log("EscapeLag —— 新一代的优化/稳定插件");
      AzureAPI.log("~(@^_^@)~ 玩的开心！~");
      AzureAPI.log("Version " + this.getDescription().getVersion() + " is ready for installation \n");
      AzureAPI.log("Server: " + Bukkit.getServer().getVersion());
      AzureAPI.log("Bukkit: " + Bukkit.getServer().getBukkitVersion());
      AzureAPI.log("Level: " + VersionLevel.get() + "\n");
      this.bindModules();
      AzureAPI.log("EscapeLag has been installed successfully!");
      AzureAPI.log("乐乐感谢您的使用——有建议务必反馈，QQ1207223090");
      AzureAPI.log("您可以在插件文件夹内的documents文件夹找到本插件的说明文档 说明文档.txt");
      List<String> devs = this.getDescription().getAuthors();
      AzureAPI.log("|||" + (String)devs.get(0) + "/EscapeLag 合作作品.|||");
      AzureAPI.log("|||" + AzureAPI.concatsBetween(devs, 1, ", ") + " 合作开发.|||");
      AzureAPI.log("§a您正在使用EscapeLag构建号 " + Core.internalVersion);
   }

   public void onDisable() {
      this.clearModules();
      AntiCrashOP.save();
      DataManager.save();
      AzureAPI.log("Plugin has been disabled.");
      AzureAPI.log("Thanks for using!");
   }

   public void clearModules() {
      AzureAPI.log("Uninstall modules..");
      HandlerList.unregisterAll(this);
      Bukkit.getScheduler().cancelTasks(this);
      Ticker.close();
      AzureAPI.log("All modules have been cleared!");
   }

   public void bindModules() {
      AzureAPI.log("Setup modules..");
      Ticker.init();
      AutoUpgrade.init(this);
      PlayerList.init(this);
      UpgradeHelper.init(this);
      CensoredChat.init(this);
      ExplosionController.init(this);
      SpawnerGuard.init(this);
      FarmProtect.init(this);
      ContainerPortalPatch.init(this);
      NetherHopperDupePatch.init(this);
      NegativeItemPatch.init(this);
      RailsMachine.init(this);
      DupeLoginPatch.init(this);
      AntiDeadDrop.init();
      BonemealDupePatch.init(this);
      CheatBookBlocker.init(this);
      CalculationAbusePatch.init(this);
      ZeroHealthPatch.init(this);
      ValidateActions.init(this);
      AntiDestoryUsingChest.init();
      AntiCrashOP.init();
      AntiCrashChat.init();
      Bukkit.getPluginManager().registerEvents(new AntiCrashSign(), this);
      Bukkit.getPluginManager().registerEvents(new CancelledPlacementPatch(), this);
      Bukkit.getPluginManager().registerEvents(new AntiBedExplode(), this);
      Bukkit.getPluginManager().registerEvents(new AntiLongStringCrash(), this);
      TickSleep.init(this);
      EmptyRestart.init(this);
      OverloadResume.init(this);
      FireSpreadSlacker.init(this);
      RedstoneSlacker.init(this);
      DelayedChunkKeeper.init(this);
      NoStyxChunks.init(this);
      NoSpawnChunk.init();
      Bukkit.getPluginManager().registerEvents(new UnloadClear(), this);
      Bukkit.getPluginManager().registerEvents(new AutoSave(), this);
      Bukkit.getPluginManager().registerEvents(new WaterFlowLimitor(), this);
      Bukkit.getPluginManager().registerEvents(new TeleportPreLoader(), this);
      Bukkit.getPluginManager().registerEvents(new NoCrowdEntity(), this);
      AzureAPI.log("All modules have been installed!");
   }

   public static boolean canAccessPackets() {
      return Bukkit.getPluginManager().isPluginEnabled("ProtocolLib");
   }

   public static File getPluginFile() {
      return plugin.getFile();
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      return EscapeLagCommand.processCommand(sender, cmd, label, args);
   }

   private AzureAPI.Coord<File, FileConfiguration> configsFile(String name) {
      File configFile = new File(this.getDataFolder(), name);
      AzureAPI.Coord<File, FileConfiguration> coord = AzureAPI.wrapCoord(configFile, AzureAPI.loadOrCreateConfiguration(configFile));
      configurations.put(name, coord);
      return coord;
   }

   public void setupConfigs() {
      String locale = "english";
      if (StringUtils.startsWithIgnoreCase(Core.lang, "zh_")) {
         locale = "中文";
      }

      plugin.saveResource("documents" + File.separator + "Guide-english.txt", true);
      plugin.saveResource("documents" + File.separator + "Guide-chinese.txt", true);
      this.setupConfig("CoreConfig.yml", Core.class);
      this.setupConfig("FeaturesConfig.yml", Features.class);
      this.setupConfig("BugPatchesConfig.yml", Patches.class);
      this.setupConfig("patches/dupe_fixes.yml", PatchesDupeFixes.class);
      this.setupConfig("OptimizeConfig.yml", Optimizes.class);
      this.setupConfig("optimizes/chunks.yml", OptimizesChunk.class);
   }

   public boolean setupConfig(String configIdentifier, Class<? extends Configurable> provider) {
      configurations.remove(configIdentifier);
      AzureAPI.Coord configCoord = this.configsFile(configIdentifier);

      try {
         Configurable.restoreNodes(configCoord, provider);
      } catch (IllegalAccessException | IllegalArgumentException var5) {
         AzureAPI.fatal("Cannot setup configuration '" + ((File)configCoord.getKey()).getName() + "', wrong format?", this);
         var5.printStackTrace();
         return false;
      } catch (IOException var6) {
         AzureAPI.fatal("Cannot load configuration '" + ((File)configCoord.getKey()).getName() + "', file blocked?", this);
         var6.printStackTrace();
         return false;
      }

      if (configIdentifier.equals("CoreConfig.yml")) {
         AzureAPI.setPrefix(Core.PluginPrefix + ChatColor.RESET + " > ");
         LocalizedHelper.init();
      }

      return true;
   }

   public static void AutoSetServer(boolean force) {
      try {
         if (force || Optimizes.AutoSetenable) {
            long heapmb = Runtime.getRuntime().maxMemory() / 1024L / 1024L;
            File BukkitFile = new File("bukkit.yml");
            File PaperFile;
            if (BukkitFile.exists()) {
               FileConfiguration bukkit = AzureAPI.loadOrCreateConfiguration(BukkitFile);
               PaperFile = new File("backup_bukkit.yml");
               if (!PaperFile.exists()) {
                  PaperFile.createNewFile();
                  bukkit.save(PaperFile);
                  if (heapmb <= 6000L) {
                     bukkit.set("chunk-gc.period-in-ticks", 300);
                  } else {
                     bukkit.set("chunk-gc.period-in-ticks", 500);
                  }

                  bukkit.set("chunk-gc.load-threshold", 400);
                  if (heapmb <= 4000L) {
                     bukkit.set("ticks-per.monster-spawns", 2);
                  }

                  bukkit.set("EscapeLag.Changed", "如果Config的AutoSet开启，该参数会被改变。");
                  bukkit.save(BukkitFile);
               }
            }

            File SpigotFile = new File("spigot.yml");
            if (SpigotFile.exists()) {
               FileConfiguration spigot = AzureAPI.loadOrCreateConfiguration(SpigotFile);
               File backupSpigotFile = new File("backup_spigot.yml");
               if (!backupSpigotFile.exists()) {
                  backupSpigotFile.createNewFile();
                  spigot.save(backupSpigotFile);
                  if (heapmb <= 2000L) {
                     spigot.set("settings.save-user-cache-on-stop-only", true);
                  }

                  if (heapmb >= 6000L) {
                     spigot.set("settings.user-cache-size", 5000);
                  }

                  if (heapmb >= 4000L) {
                     spigot.set("world-settings.default.view-distance", 4);
                  } else {
                     spigot.set("world-settings.default.view-distance", 3);
                  }

                  if (heapmb <= 4000L) {
                     spigot.set("world-settings.default.chunks-per-tick", 150);
                  } else {
                     spigot.set("world-settings.default.chunks-per-tick", 350);
                  }

                  if (heapmb <= 4000L) {
                     spigot.set("world-settings.default.max-tick-time.tile", 10);
                     spigot.set("world-settings.default.max-tick-time.entity", 20);
                  } else {
                     spigot.set("world-settings.default.max-tick-time.tile", 20);
                     spigot.set("world-settings.default.max-tick-time.entity", 30);
                  }

                  spigot.set("world-settings.default.entity-activation-range.animals", 12);
                  spigot.set("world-settings.default.entity-activation-range.monsters", 32);
                  spigot.set("world-settings.default.entity-activation-range.misc", 12);
                  spigot.set("world-settings.default.entity-tracking-range.other", 48);
                  spigot.set("world-settings.default.random-light-updates", false);
                  if (heapmb <= 4000L) {
                     spigot.set("world-settings.default.save-structure-info", false);
                  }

                  spigot.set("world-settings.default.max-entity-collisions", 2);
                  spigot.set("world-settings.default.max-tnt-per-tick", 20);
                  spigot.set("EscapeLag.Changed", "如果Config的AutoSet开启，该参数会被改变。");
                  spigot.save(SpigotFile);
               }
            }

            PaperFile = new File("paper.yml");
            FileConfiguration bukkit;
            if (PaperFile.exists()) {
               bukkit = AzureAPI.loadOrCreateConfiguration(PaperFile);
               File backupPaperFile = new File("backup_paper.yml");
               if (!backupPaperFile.exists()) {
                  backupPaperFile.createNewFile();
                  bukkit.save(backupPaperFile);
                  bukkit.set("world-settings.default.keep-spawn-loaded", false);
                  bukkit.set("world-settings.default.optimize-explosions", true);
                  bukkit.set("world-settings.default.fast-drain.lava", true);
                  bukkit.set("world-settings.default.fast-drain.water", true);
                  bukkit.set("world-settings.default.use-async-lighting", true);
                  if (heapmb <= 6000L) {
                     bukkit.set("world-settings.default.tick-next-tick-list-cap", 8000);
                  }

                  bukkit.set("world-settings.default.tick-next-tick-list-cap-ignores-redstone", true);
                  bukkit.save(PaperFile);
               }
            }

            if (BukkitFile.exists()) {
               bukkit = AzureAPI.loadOrCreateConfiguration(BukkitFile);
               if (bukkit.getInt("EscapeLag.SetStep") == 1) {
                  bukkit.set("EscapeLag.SetStep", 2);

                  try {
                     bukkit.save(BukkitFile);
                  } catch (IOException var8) {
                  }
               }

               if (bukkit.getInt("EscapeLag.SetStep") == 0) {
                  bukkit.set("EscapeLag.SetStep", 1);
                  bukkit.save(BukkitFile);
                  Bukkit.getScheduler().runTaskLater(plugin, () -> {
                     AzureAPI.log("成功改动服务器配端，正在重启来启用它.");
                     AzureAPI.restartServer("配端完成，正在重启中！");
                  }, 1L);
               }
            }

         }
      } catch (Throwable var9) {
         throw var9;
      }
   }
}
