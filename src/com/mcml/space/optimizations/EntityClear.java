package com.mcml.space.optimizations;

import com.mcml.space.config.Optimizes;
import com.mcml.space.core.PlayerList;
import com.mcml.space.util.AzureAPI;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntityClear implements Runnable {
   public void run() {
      if (Optimizes.EntityClearenable) {
         int count = 0;
         List<World> worlds = Bukkit.getWorlds();
         List<LivingEntity> allents = new ArrayList();
         int ws = worlds.size();

         int aes;
         for(aes = 0; aes < ws; ++aes) {
            World world = (World)worlds.get(aes);
            List<LivingEntity> lents = world.getLivingEntities();
            int ls = lents.size();

            for(int ii = 0; ii < ls; ++ii) {
               LivingEntity le = (LivingEntity)lents.get(ii);
               if (Optimizes.EntityClearEntityType.contains("*") || Optimizes.EntityClearEntityType.contains(le.getType().name())) {
                  allents.add(le);
                  count += lents.size();
               }
            }
         }

         if (count > Optimizes.EntityClearLimitCount) {
            PlayerList.forEach((player) -> {
               List<Entity> nents = player.getNearbyEntities(10.0D, 10.0D, 10.0D);
               int ls = nents.size();

               for(int ii = 0; ii < ls; ++ii) {
                  Entity le = (Entity)nents.get(ii);
                  if (allents.contains(le)) {
                     allents.remove(le);
                  }
               }

            });
            aes = allents.size();
            AzureAPI.bc(Optimizes.EntityClearClearMessage);

            for(int ii = 0; ii < aes; ++ii) {
               ((LivingEntity)allents.get(ii)).remove();
            }
         }
      }

   }
}
