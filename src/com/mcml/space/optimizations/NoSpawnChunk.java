package com.mcml.space.optimizations;

import com.mcml.space.util.AzureAPI;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class NoSpawnChunk {
   public static void init() {
      Iterator var0 = Bukkit.getWorlds().iterator();

      while(var0.hasNext()) {
         World world = (World)var0.next();
         world.setKeepSpawnInMemory(false);
      }

      AzureAPI.log("子模块 - 区块释放 已启动");
   }
}
