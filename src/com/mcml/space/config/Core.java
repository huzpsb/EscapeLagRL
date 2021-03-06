package com.mcml.space.config;

import com.mcml.space.util.Configurable;

public abstract class Core extends Configurable {
    @Configurable.Node("PluginPrefix")
    public static String PluginPrefix = "&bEscapeLag";
    @Configurable.View
    @Configurable.Node("internal-version")
    public static String internalVersion = "557";
    @Configurable.Node("language")
    public static String lang = "zh_cn";
}
