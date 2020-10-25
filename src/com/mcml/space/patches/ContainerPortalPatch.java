package com.mcml.space.patches;

import com.google.common.collect.Sets;
import com.mcml.space.config.PatchesDupeFixes;
import com.mcml.space.core.EscapeLag;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

public class ContainerPortalPatch implements Listener {
   private final Set<InventoryHolder> containerInv = Sets.newSetFromMap(new IdentityHashMap(4));

   public static void init(Plugin plugin) {
      if (PatchesDupeFixes.enablePortalContainerDupeFixes) {
         Bukkit.getPluginManager().registerEvents(new ContainerPortalPatch(), plugin);
         AzureAPI.log(Locale.isNative() ? "子模块 - 容器传送 已启动" : "Submodule - ContainerPortalPatch has been enabled");
      }
   }

   @EventHandler(
      priority = EventPriority.LOWEST,
      ignoreCancelled = true
   )
   public void onPortal(EntityPortalEvent evt) {
      EntityType type = evt.getEntityType();
      if (hasInventory(type)) {
         if (!this.handleContainer((InventoryHolder)evt.getEntity())) {
            return;
         }

         Bukkit.getScheduler().runTask(EscapeLag.plugin, () -> {
            this.containerInv.remove((InventoryHolder)evt.getEntity());
            evt.getEntity().teleport(evt.getTo(), TeleportCause.NETHER_PORTAL);
         });
         evt.setCancelled(true);
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST,
      ignoreCancelled = true
   )
   public void onPortal(InventoryOpenEvent evt) {
      if (this.containerInv.contains(evt.getInventory().getHolder())) {
         evt.setCancelled(true);
      }

   }

   private boolean handleContainer(InventoryHolder holder) {
      this.containerInv.add(holder);
      Iterator var2 = holder.getInventory().getViewers().iterator();

      while(var2.hasNext()) {
         HumanEntity player = (HumanEntity)var2.next();
         player.closeInventory();
      }

      return true;
   }

   private static boolean hasInventory(EntityType type) {
      switch(type) {
      case MINECART_CHEST:
      case MINECART_FURNACE:
      case MINECART_HOPPER:
      case VILLAGER:
      case HORSE:
         return true;
      default:
         return false;
      }
   }
}
