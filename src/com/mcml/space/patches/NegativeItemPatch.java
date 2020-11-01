package com.mcml.space.patches;

import com.mcml.space.config.PatchesDupeFixes;
import com.mcml.space.core.PlayerList;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.Iterator;

public class NegativeItemPatch implements Listener {
    public static void init(Plugin plugin) {
        if (PatchesDupeFixes.enableNegativeItemDupeFixes || Bukkit.getPluginManager().isPluginEnabled("RPGItems") || Bukkit.getPluginManager().isPluginEnabled("RPG_Items")) {
            Bukkit.getPluginManager().registerEvents(new NegativeItemPatch(), plugin);
            AzureAPI.log(Locale.isNative() ? "子模块 - 正数物品 已启动" : "Submodule - NegativeItemPatch has been enabled");
        }
    }

    private static void fliterPlayersInventory(@Nullable Player sourcePlayer) {
        if (PatchesDupeFixes.negativeItemDupeFixes_fliterPlayersInv) {
            PlayerList.forEach((player) -> {
                boolean hasRemovedAny = false;
                ItemStack[] var3 = player.getInventory().getContents();
                int var4 = var3.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    ItemStack item = var3[var5];
                    if (item.getAmount() <= 0) {
                        hasRemovedAny = true;
                        player.getInventory().remove(item);
                    }
                }

                if (hasRemovedAny && sourcePlayer != null && !sourcePlayer.getName().equals(player.getName())) {
                    AzureAPI.bc(PatchesDupeFixes.negativeItemDupeFixes_notifyMesssage, "$player", player.getName());
                }

            });
        }
    }

    public static boolean removesNegativeDrops(World world) {
        if (!PatchesDupeFixes.negativeItemDupeFixes_removesItem) {
            return false;
        } else {
            if (PatchesDupeFixes.negativeItemDupeFixes_removesItem_fliterDrops) {
                Iterator<Entity> var1 = world.getEntities().iterator();

                while (var1.hasNext()) {
                    Entity entity = var1.next();
                    if (entity.getType() == EntityType.DROPPED_ITEM && ((Item) entity).getItemStack().getAmount() <= 0) {
                        entity.remove();
                    }
                }
            }

            return true;
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void DispenseCheck(BlockDispenseEvent evt) {
        if (evt.getItem().getAmount() <= 0) {
            BlockState state = evt.getBlock().getState();
            if (state instanceof InventoryHolder) {
                Inventory Inventory = ((InventoryHolder) state).getInventory();
                ItemStack[] var4 = Inventory.getContents();
                int var5 = var4.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    ItemStack item = var4[var6];
                    if (item.getAmount() <= 0) {
                        fliterPlayersInventory(null);
                        removesNegativeDrops(evt.getBlock().getWorld());
                        item.setAmount(1);
                        evt.setCancelled(true);
                    }
                }

            }
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void InteractCheck(PlayerInteractEvent evt) {
        if (evt.getItem() != null && evt.getItem().getAmount() <= 0) {
            fliterPlayersInventory(null);
            removesNegativeDrops(evt.getPlayer().getWorld());
            evt.setCancelled(true);
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onPickup(PlayerPickupItemEvent evt) {
        if (evt.getItem().getItemStack().getAmount() <= 0) {
            removesNegativeDrops(evt.getItem().getWorld());
            fliterPlayersInventory(evt.getPlayer());
            AzureAPI.bc(PatchesDupeFixes.negativeItemDupeFixes_notifyMesssage, "$player", evt.getPlayer().getName());
            evt.setCancelled(true);
        }
    }
}
