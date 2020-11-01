package com.mcml.space.config;

import com.google.common.collect.Sets;
import com.mcml.space.util.Configurable;
import com.mcml.space.util.Locale;

import java.util.Map;
import java.util.Set;

public abstract class PatchesDupeFixes extends Configurable {
    @Configurable.Node("config-version")
    public static long configVersion = 1L;
    @Configurable.Node("settings.rails-machine.enable-fixes")
    public static boolean enableRailsMachineFixes = true;
    @Configurable.Node("settings.nether-hoppers.enable-fixes")
    public static boolean enableNetherHoppersDupeFixes = true;
    @Configurable.Node("settings.nether-hoppers.worlds.restrict-environment")
    public static boolean netherHoppersDupeFixes_restrictEnv = true;
    @Configurable.Node("settings.nether-hoppers.worlds.enable-worlds")
    public static Set<String> netherHoppersDupeFixes_worlds = Sets.newHashSet();
    @Configurable.Node("settings.negative-amount-item.enable-fixes")
    public static boolean enableNegativeItemDupeFixes = false;
    @Configurable.Node("settings.negative-amount-item.notify-message")
    public static String negativeItemDupeFixes_notifyMesssage = Locale.isNative() ? "&b发现玩家 &f$player &b获取到负数物品, 已执行清理!" : "&bFound that player &f$player &bhas negative/infinite item(s), have been cleared!";
    @Configurable.Node("settings.negative-amount-item.actions.removes-item.enable")
    public static boolean negativeItemDupeFixes_removesItem = false;
    @Configurable.Node("settings.negative-amount-item.actions.removes-item.fliter-drops")
    public static boolean negativeItemDupeFixes_removesItem_fliterDrops = true;
    @Configurable.Node("settings.negative-amount-item.actions.fliter-players-inv")
    public static boolean negativeItemDupeFixes_fliterPlayersInv = true;
    @Configurable.Node("settings.portal-container.enable-fixes")
    public static boolean enablePortalContainerDupeFixes = true;
    @Configurable.Node("settings.cancelled-placement.enable-fixes")
    public static boolean enableCancelledPlacementDupeFixes = true;
    @Configurable.Node("settings.cancelled-placement.clears-radius")
    public static Map<String, String> cancelledPlacementDupeFixes_clearsRadius = DefaultOptions.droppedItemClearsRadius();
}
