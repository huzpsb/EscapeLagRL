package com.mcml.space.patches;

import com.mcml.space.config.Patches;
import com.mcml.space.util.AzureAPI;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AntiBedExplode implements Listener {
   @EventHandler
   public void CheckInterackBed(PlayerInteractEvent e) {
      if (Patches.noBedExplore) {
         Player p = e.getPlayer();
         Block block = e.getClickedBlock();
         if (e.getAction() == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.BED_BLOCK && (p.getWorld().getEnvironment() == Environment.NETHER || p.getWorld().getEnvironment() == Environment.THE_END)) {
            e.setCancelled(true);
            AzureAPI.log((CommandSender)p, Patches.AntiBedExplodeTipMessage);
         }
      }

   }
}
