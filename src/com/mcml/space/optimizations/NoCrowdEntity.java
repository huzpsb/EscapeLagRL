package com.mcml.space.optimizations;

import com.mcml.space.config.Optimizes;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class NoCrowdEntity implements Listener {
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent evt) {
        if (Optimizes.NoCrowdedEntityenable) {
            Chunk chunk = evt.getChunk();
            Entity[] entities = chunk.getEntities();
            Entity[] var4 = entities;
            int var5 = entities.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                Entity e = var4[var6];
                EntityType type = e.getType();
                int count = 0;
                if (Optimizes.NoCrowdedEntityTypeList.contains("*") || Optimizes.NoCrowdedEntityTypeList.contains(type.name())) {
                    count = count + 1;
                    if (count > Optimizes.NoCrowdedEntityPerChunkLimit && e.getType() != EntityType.PLAYER) {
                        e.remove();
                    }
                }
            }
        }

    }
}
