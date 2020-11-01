package com.mcml.space.patches;

import com.mcml.space.config.Patches;
import com.mcml.space.core.EscapeLag;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.VersionLevel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class AntiCrashChat implements Listener {
    private static AntiCrashChat Instance;
    private static boolean ShouldAntiColorChar;

    public static void init() {
        Instance = new AntiCrashChat();
        Plugin ess = Bukkit.getPluginManager().getPlugin("Essentials");
        if (ess != null && VersionLevel.isLowerThan(VersionLevel.Version.MINECRAFT_1_7_R2)) {
            ShouldAntiColorChar = true;
            AzureAPI.log("反崩溃颜色字符模块开启!");
        }

        if (VersionLevel.equals(VersionLevel.Version.MINECRAFT_1_7_R2)) {
            Bukkit.getPluginManager().registerEvents(Instance, EscapeLag.plugin);
            AzureAPI.log("反崩溃字符模块开启!");
        }

    }

    @EventHandler(
            priority = EventPriority.HIGH,
            ignoreCancelled = true
    )
    public void ChatCheckCrash(AsyncPlayerChatEvent event) {
        if (Patches.noCrashChat) {
            Player player = event.getPlayer();
            String message = event.getMessage();
            if (message.contains("İ")) {
                event.setCancelled(true);
                AzureAPI.log(player, Patches.AntiCrashChatSpecialStringWarnMessage);
            }

            if (ShouldAntiColorChar && message.contains("&l")) {
                event.setCancelled(true);
                AzureAPI.log(player, Patches.AntiCrashChatSpecialStringWarnMessage);
            }

        }
    }
}
