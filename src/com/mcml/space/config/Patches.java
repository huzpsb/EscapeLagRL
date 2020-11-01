package com.mcml.space.config;

import com.mcml.space.util.Configurable;
import com.mcml.space.util.Locale;
import com.mcml.space.util.VersionLevel;
import org.bukkit.Bukkit;

public abstract class Patches extends Configurable {
    @Configurable.Node("AntiWEcalc.WarnMessage")
    public static String AntiWEcalcWarnMessage = Locale.isNative() ? "§c禁止利用WE bug炸服! =.=" : "§cSorry but abuse //calc is forbidden! =.=";
    @Configurable.Node("AntiLongStringCrash.enable")
    public static boolean AntiLongStringCrashenable = true;
    @Configurable.Node("AntiCrashOP.enable")
    public static boolean AntiCrashOPenable = true;
    @Configurable.Node("AntiCrashOP.WarnMessage")
    public static String AntiCrashOPWarnMessage = "§c你貌似卡OP了,已将OP去除!";
    @Configurable.Node("AntiLongStringCrash.WarnMessage")
    public static String AntiLongStringCrashWarnMessage = Locale.isNative() ? "§c警告！严禁利用长字符串来导致服务器崩溃！" : "§cSorry but abuse long string to trigger a crash is forbidden!";
    @Configurable.Node("AntiFakeDeath.KickMessage")
    public static String messageFakedeath = "§c严禁卡假死BUG！";
    @Configurable.Node("AntiFakeDeath.enable")
    public static boolean noFakedeath = true;
    @Configurable.Node("NoDoubleOnline.enable")
    public static boolean fixDupeOnline;
    @Configurable.Node("NoDoubleOnline.KickMessage")
    public static String messageKickDupeOnline;
    @Configurable.Node("AntiBedExplode.enable")
    public static boolean noBedExplore;
    @Configurable.Node("AntiBreakUseingChest.enable")
    public static boolean protectUsingChest;
    @Configurable.Node("AntiCheatBook.enable")
    public static boolean noCheatBook;
    @Configurable.Node("AntiCrashSign.enable")
    public static boolean fixCrashSign;
    @Configurable.Node("AntiDupeDropItem.enable")
    public static boolean fixDupeDropItem;
    @Configurable.Node("patches.vaildate-actions.enable")
    public static boolean enableVaildateActions;
    @Configurable.Node("AntiSkullCrash.enable")
    public static boolean noSkullCrash;
    @Configurable.Node("AntiCrashChat.enable")
    public static boolean noCrashChat;
    @Configurable.Node("AntiCrashChat.SpecialStringWarnMessage")
    public static String AntiCrashChatSpecialStringWarnMessage;
    @Configurable.Node("AntiCrashChat.ColorChatWarnMessage")
    public static String AntiCrashChatColorChatWarnMessage;
    @Configurable.Node("AntiBreakUseingChest.WarnMessage")
    public static String AntiBreakUsingChestWarnMessage;
    @Configurable.Node("AntiBedExplode.TipMessage")
    public static String AntiBedExplodeTipMessage;
    @Configurable.Node("AntiCrashSign.WarnMessage")
    public static String AntiCrashSignWarnMessage;

    static {
        fixDupeOnline = !Bukkit.getOnlineMode() && (!VersionLevel.isHigherEquals(VersionLevel.Version.MINECRAFT_1_7_R4) || !VersionLevel.isSpigot() || !Bukkit.getServer().spigot().getConfig().getBoolean("settings.bungeecord"));
        messageKickDupeOnline = Locale.isNative() ? "抱歉，服务器中您已经在线了。ԅ(¯ㅂ¯ԅ)" : "Sorry but you are already online ԅ(¯ㅂ¯ԅ)";
        noBedExplore = true;
        protectUsingChest = true;
        noCheatBook = true;
        fixCrashSign = true;
        fixDupeDropItem = true;
        enableVaildateActions = true;
        noSkullCrash = true;
        noCrashChat = true;
        AntiCrashChatSpecialStringWarnMessage = Locale.isNative() ? "§c严禁使用崩服代码炸服！" : "§cSorry but abuse special character to trigger a crash is forbidden!";
        AntiCrashChatColorChatWarnMessage = Locale.isNative() ? "§c抱歉！为了防止服务器被破坏，服务器禁止使用颜色代码." : "§cSorry but color code is not allowed here on account of server safety";
        AntiBreakUsingChestWarnMessage = Locale.isNative() ? "§c抱歉！您不可以破坏一个正在被使用的容器" : "§cSorry but you can't break a container that is using by others";
        AntiBedExplodeTipMessage = Locale.isNative() ? "§r你不能在这里睡觉" : "§rSorry but here is too dangerous to sleep";
        AntiCrashSignWarnMessage = Locale.isNative() ? "§c您输入的内容太长了！" : "§rSorry but you typed too long!";
    }
}
