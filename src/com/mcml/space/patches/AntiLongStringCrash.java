package com.mcml.space.patches;

import com.mcml.space.config.Patches;
import com.mcml.space.util.AzureAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AntiLongStringCrash implements Listener {
    @EventHandler
    public void InteractCheck(PlayerInteractEvent evt) {
        if (Patches.AntiLongStringCrashenable) {
            ItemStack item = evt.getItem();
            Player player = evt.getPlayer();
            if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().length() >= 127) {
                evt.setCancelled(true);
                player.setItemInHand(null);
                AzureAPI.log(player, Patches.AntiLongStringCrashWarnMessage);
            }
        }

    }

    @EventHandler
    public void ClickCheckItem(InventoryClickEvent evt) {
        if (Patches.AntiLongStringCrashenable) {
            if (evt.getWhoClicked().getType() != EntityType.PLAYER) {
                return;
            }

            Player player = (Player) evt.getWhoClicked();
            ItemStack item = evt.getCursor();
            if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().length() >= 127) {
                evt.setCancelled(true);
                evt.setCurrentItem(null);
                AzureAPI.log(player, Patches.AntiLongStringCrashWarnMessage);
            }
        }

    }
}
