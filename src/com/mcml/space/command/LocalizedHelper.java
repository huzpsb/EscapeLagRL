package com.mcml.space.command;

import com.mcml.space.config.Core;
import com.mcml.space.core.EscapeLag;

public class LocalizedHelper {
    public static String PleaseEnterelToHelp;
    public static String HelpLine1;
    public static String HelpLine2;
    public static String HelpLine3;
    public static String HelpLine4;
    public static String HelpLine5;
    public static String HelpLine6;
    public static String HelpLine7;
    public static String PluginPrefixLine;

    public static void init() {
        if (Core.lang.equalsIgnoreCase("zh_cn")) {
            PleaseEnterelToHelp = "§c请输入/el help 来获取帮助";
            HelpLine1 = "§e/el reload 重载插件";
            HelpLine2 = "§e/el chunkkeeper 查看关于区块保持者的帮助";
            HelpLine3 = "§e/el heap 查阅关于内存清理和分配的内容";
            HelpLine4 = "§e/el autosave 查阅关于自动储存的内容";
            HelpLine5 = "§e/el tps 查阅关于TPS和主线程";
            HelpLine6 = "§e/el autoset 查阅关于自动配端";
            PluginPrefixLine = "§b------§a§lEscapeLag - §e版本 " + EscapeLag.plugin.getDescription().getVersion() + "§b------";
        }

        if (Core.lang.equalsIgnoreCase("en_GB")) {
            PleaseEnterelToHelp = "§cPlease enter /el help to get help";
            HelpLine1 = "§e/el reload ReloadPlugin";
            HelpLine2 = "§e/el chunkkeeper ask help for ChunkKeeper";
            HelpLine3 = "§e/el heap ask help for Heap";
            HelpLine4 = "§e/el autosave ask help for AutoSave";
            HelpLine5 = "§e/el tps ask help for tps";
            HelpLine6 = "§e/el autoset ask help for AutoSet model";
            PluginPrefixLine = "§b------§a§lEscapeLag - §eVer " + EscapeLag.plugin.getDescription().getVersion() + "§b------";
        }

    }
}
