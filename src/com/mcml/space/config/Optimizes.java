package com.mcml.space.config;

import com.mcml.space.util.Configurable;
import com.mcml.space.util.Locale;
import java.util.Set;

public abstract class Optimizes extends Configurable {
   @Configurable.Node("TPSSleep.NoOneFreeze.enable")
   public static boolean TPSSleepNoOneFreezeenable = true;
   @Configurable.Node("TPSSleep.SleepMode")
   public static String TPSSleepSleepMode = "NoUse";
   @Configurable.Node("EntityClear.enable")
   public static boolean EntityClearenable = true;
   @Configurable.Node("EntityClear.CheckInterval")
   public static int EntityClearCheckInterval = 600;
   @Configurable.Node("EntityClear.LimitCount")
   public static int EntityClearLimitCount = 1200;
   @Configurable.Node("EntityClear.ClearEntityType")
   public static Set<String> EntityClearEntityType = DefaultOptions.EntityClearEntityTypes();
   @Configurable.Node("EntityClear.ClearMessage")
   public static String EntityClearClearMessage = Locale.isNative() ? "§a成功清除了过多的实体 ~~(@^_^@)~" : "§aSuccessfully cleared overflow entities ~~(@^_^@)~";
   @Configurable.Node("NooneRestart.enable")
   public static boolean emptyRestart = false;
   @Configurable.Node("NooneRestart.TimeLong")
   public static long emptyRestartDelay = 1200L;
   @Configurable.Node("OverLoadMemoryRestart.enable")
   public static boolean OverLoadMemoryRestartenable = false;
   @Configurable.Node("WaterFlowLimitor.enable")
   public static boolean WaterFlowLimitorenable = true;
   @Configurable.Node("WaterFlowLimitor.PerChunkTimes")
   public static long WaterFlowLimitorPerChunkTimes = 2L;
   @Configurable.Node("AntiRedstone.enable")
   public static boolean AntiRedstoneenable = true;
   @Configurable.Node("AntiRedstone.drop-item")
   public static boolean dropRedstone = true;
   @Configurable.Node("AntiRedstone.Times")
   public static int AntiRedstoneTimes = 5;
   @Configurable.Node("FireLimitor.enable")
   public static boolean FireLimitorenable = true;
   @Configurable.Node("FireLimitor.Period")
   public static long FireLimitorPeriod = 3000L;
   @Configurable.Node("TeleportPreLoader.enable")
   public static boolean TeleportPreLoaderenable = false;
   @Configurable.Node("UnloadClear.DROPPED_ITEM.NoCleatDeath")
   public static boolean UnloadClearDROPPED_ITEMNoCleatDeath = true;
   @Configurable.Node("UnloadClear.DROPPED_ITEM.NoClearTeleport")
   public static boolean UnloadClearDROPPED_ITEMNoClearTeleport = false;
   @Configurable.Node("NoCrowdedEntity.enable")
   public static boolean NoCrowdedEntityenable = true;
   @Configurable.Node("NoCrowdedEntity.TypeList")
   public static Set<String> NoCrowdedEntityTypeList = DefaultOptions.slackEntityTypes();
   @Configurable.Node("NoCrowdedEntity.PerChunkLimit")
   public static int NoCrowdedEntityPerChunkLimit = 30;
   @Configurable.Node("AntiRedstone.Message")
   public static String AntiRedstoneMessage = Locale.isNative() ? "§c检测到高频红石在 %location% 附近，插件已经将其清除，不许玩了！ (╰_╯)#" : "§cDetected there is an overclock redstone around %location%, we just cleared it, please stop! (╰_╯)#";
   @Configurable.Node("OverLoadMemoryRestart.HeapMBLefted")
   public static int OverLoadMemoryRestartHeapMBLefted = 130;
   @Configurable.Node("OverLoadMemoryRestart.KickMessage")
   public static String OverLoadMemoryRestartKickMessage = Locale.isNative() ? "抱歉！由于服务器内存过载，需要重启服务器！" : "Sorry but we must restart due to memory overflow";
   @Configurable.Node("AntiRedstone.RemoveBlockList")
   public static Set<String> AntiRedstoneRemoveBlockList = DefaultOptions.redstoneRemovalMaterialTypes();
   @Configurable.Node("AutoSave.Interval")
   public static long AutoSaveInterval = 15L;
   @Configurable.Node("OverLoadMemoryRestart.WarnMessage")
   public static String OverLoadMemoryRestartWarnMessage = Locale.isNative() ? "服务器会在15秒后重启，请玩家不要游戏，耐心等待！ ╮(╯_╰)╭" : "Server will restart in 15s, please save your current work and await! ╮(╯_╰)╭";
   @Configurable.Node("OverLoadMemoryRestart.DelayTime")
   public static int OverLoadMemoryRestartDelayTime = 15;
   @Configurable.Node("AutoSet.enable")
   public static boolean AutoSetenable = true;
   @Configurable.Node("AutoSave.enable")
   public static boolean AutoSaveenable = true;
   @Configurable.Node("UnloadClear.DROPPED_ITEM.enable")
   public static boolean UnloadClearDROPPED_ITEMenable = true;
   @Configurable.Node("UnloadClear.enable")
   public static boolean UnloadClearenable = true;
   @Configurable.Node("UnloadClear.type")
   public static Set<String> UnloadCleartype = DefaultOptions.unloadClearEntityTypes();
}
