package com.mcml.space.patches;

import com.mcml.space.config.Patches;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CheatBookBlocker implements Listener {
    public static void init(Plugin plugin) {
        if (Patches.noCheatBook) {
            Bukkit.getPluginManager().registerEvents(new CheatBookBlocker(), plugin);
            AzureAPI.log(Locale.isNative() ? "子模块 - 书本修复 已启动" : "Submodule - CheatBookBlocker has been enabled");
        }
    }

    public static BookMeta clearEnchant(BookMeta meta) {
        Iterator var1 = meta.getEnchants().keySet().iterator();

        while (var1.hasNext()) {
            Enchantment e = (Enchantment) var1.next();
            meta.removeEnchant(e);
        }

        return meta;
    }

    public static BookMeta addEnchantFrom(BookMeta source, BookMeta meta) {
        Iterator var2 = source.getEnchants().entrySet().iterator();

        while (var2.hasNext()) {
            Entry<Enchantment, Integer> e = (Entry) var2.next();
            meta.addEnchant(e.getKey(), e.getValue(), true);
        }

        return meta;
    }

    @EventHandler(
            priority = EventPriority.LOWEST,
            ignoreCancelled = true
    )
    public void onBookEdit(PlayerEditBookEvent evt) {
        BookMeta prev = evt.getPreviousBookMeta();
        BookMeta meta = evt.getNewBookMeta();
        if (!prev.equals(meta)) {
            List<String> lore = prev.getLore();
            if (lore != null && !lore.isEmpty()) {
                if (!lore.equals(meta.getLore())) {
                    meta.setLore(lore);
                }
            } else {
                meta.setLore(null);
            }

            Map<Enchantment, Integer> enchants = prev.getEnchants();
            if (enchants != null && !enchants.isEmpty()) {
                if (!enchants.equals(meta.getEnchants())) {
                    clearEnchant(meta);
                    addEnchantFrom(prev, meta);
                }
            } else {
                clearEnchant(meta);
            }

            Set<ItemFlag> itemFlags = prev.getItemFlags();
            if (itemFlags != null && !itemFlags.isEmpty()) {
                if (!itemFlags.equals(meta.getItemFlags())) {
                    meta.removeItemFlags(ItemFlag.values());
                    meta.addItemFlags((ItemFlag[]) itemFlags.toArray(new ItemFlag[0]));
                }
            } else {
                meta.removeItemFlags(ItemFlag.values());
            }

            evt.setNewBookMeta(meta);
        }
    }
}
