package com.mcml.space.patches;

import com.mcml.space.config.Patches;
import com.mcml.space.util.AzureAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class AntiCrashSign implements Listener {
    @EventHandler
    public void SignCheckChange(SignChangeEvent event) {
        if (Patches.fixCrashSign) {
            Player player = event.getPlayer();
            String[] lines = event.getLines();
            int ll = lines.length;

            for (int i = 0; i < ll; ++i) {
                String line = lines[i];
                if (line.length() >= 127) {
                    event.setCancelled(true);
                    AzureAPI.log(player, Patches.AntiCrashSignWarnMessage);
                }
            }
        }

    }
}
