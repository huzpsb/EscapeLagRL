package com.mcml.space.config;

import com.mcml.space.util.Configurable;
import com.mcml.space.util.Locale;

import java.util.Map;
import java.util.Set;

public abstract class Features extends Configurable {
    @Configurable.Node("Monitor.enable")
    public static volatile boolean Monitorenable = false;
    @Configurable.Node("Monitor.ThreadLag.Warning.enable")
    public static volatile boolean MonitorThreadLagWarning = false;
    @Configurable.Node("Monitor.ThreadLag.Period")
    public static long MonitorThreadLagPeriod = 2000L;
    @Configurable.Node("Monitor.ThreadLag.DumpStack")
    public static volatile boolean MonitorThreadLagDumpStack = false;
    @Configurable.Node("AntiSpam.enable")
    public static boolean AntiSpamenable = false;
    @Configurable.Node("AntiSpam.Period.Period")
    public static double AntiSpamPeriodPeriod = 1.5D;
    @Configurable.Node("AntiSpam.Period.Period-Command")
    public static double AntiCommandSpamPeriodPeriod = 0.5D;
    @Configurable.Node("AntiSpam.Period.WarnMessage")
    public static String AntiSpamPeriodWarnMessage = Locale.isNative() ? "§c请慢一点，别激动嘛！ _(:з」∠)_" : "§cBe slow, have an coffee! _(:з」∠)_";
    @Configurable.Node("AntiSpam.Dirty.enable")
    public static boolean enableAntiDirty = false;
    @Configurable.Node("AntiSpam.Dirty.List")
    public static Map<String, Boolean> AntiSpamDirtyList = DefaultOptions.spamMessages();
    @Configurable.Node("AntiSpam.Dirty.white-list")
    public static Set<String> AntiSpamDirtyWhitelist = DefaultOptions.spamWhitelist();
    @Configurable.Node("NoEggChangeSpawner.enable")
    public static boolean preventSpawnerModify = true;
    @Configurable.Node("ProtectFarm.enable")
    public static boolean ProtectFarmenable = false;
    @Configurable.Node("AntiSpam.Dirty.WarnMessage")
    public static String AntiSpamDirtyWarnMessage = Locale.isNative() ? "§c什么事情激动得你都想骂人啦？" : "§cWhat makes you so angry?";
    @Configurable.Node("NoExplode.enable")
    public static boolean controlExplode = false;
    @Configurable.Node("NoExplode.Type")
    public static String explodeControlType = "NoBlockBreak";
}
