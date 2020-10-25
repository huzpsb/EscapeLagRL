package com.mcml.space.patches;

import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import com.mcml.space.util.VersionLevel;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.Plugin;

public class BonemealDupePatch implements Listener {
   public static void init(Plugin plugin) {
      if (!VersionLevel.isLowerThan(VersionLevel.Version.MINECRAFT_1_6_R3) && !VersionLevel.isHigherThan(VersionLevel.Version.MINECRAFT_1_7_R4)) {
         Bukkit.getPluginManager().registerEvents(new BonemealDupePatch(), plugin);
         AzureAPI.log(Locale.isNative() ? "子模块 - 骨粉修复 已启动" : "Submodule - BonemealDupePatch has been enabled");
      }
   }

   @EventHandler(
      priority = EventPriority.LOWEST,
      ignoreCancelled = true
   )
   public void onGrow(StructureGrowEvent evt) {
      if (evt.isFromBonemeal()) {
         Iterator var2 = evt.getBlocks().iterator();

         while(var2.hasNext()) {
            BlockState state = (BlockState)var2.next();
            Material material = state.getBlock().getType();
            if (material != Material.AIR && material != Material.SAPLING) {
               evt.setCancelled(true);
            }
         }

      }
   }
}
