package com.mcml.space.patches;

import com.mcml.space.config.Patches;
import com.mcml.space.core.PlayerList;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.plugin.Plugin;

public class DupeLoginPatch implements Listener {
    public static void init(Plugin plugin) {
        if (Patches.fixDupeOnline) {
            Bukkit.getPluginManager().registerEvents(new DupeLoginPatch(), plugin);
            AzureAPI.log(Locale.isNative() ? "子模块 - 多重在线 已启动" : "Submodule - DupeLoginPatch has been enabled");
        }
    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onLogin(AsyncPlayerPreLoginEvent evt) {
        if (evt.getLoginResult() == Result.ALLOWED && PlayerList.contains(evt.getName())) {
            evt.setLoginResult(Result.KICK_OTHER);
            evt.setKickMessage(Patches.messageKickDupeOnline);
        }

    }
}
