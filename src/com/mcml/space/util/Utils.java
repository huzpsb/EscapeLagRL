package com.mcml.space.util;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class Utils {
   public static ArrayList<Entry<Plugin, Long>> sortMap(Map<Plugin, Long> map) {
      List<Entry<Plugin, Long>> entries = new ArrayList(map.entrySet());
      Collections.sort(entries, new Comparator<Entry<Plugin, Long>>() {
         public int compare(Entry<Plugin, Long> obj1, Entry<Plugin, Long> obj2) {
            return (int)((Long)obj2.getValue() - (Long)obj1.getValue());
         }
      });
      return (ArrayList)entries;
   }

   public static List<AzureAPI.ChunkCoord> getShouldUseChunks(Location loc) {
      List<AzureAPI.ChunkCoord> chunks = Lists.newArrayList();
      int vd = Bukkit.getViewDistance();
      int hvd = vd / 2;

      for(int i = loc.getChunk().getX() - hvd; i <= loc.getChunk().getX() + hvd; ++i) {
         for(int ii = loc.getChunk().getZ() - hvd; ii <= loc.getChunk().getZ() + hvd; ++ii) {
            chunks.add(AzureAPI.wrapCoord(i, ii));
         }
      }

      return chunks;
   }

   public static List<AzureAPI.Coord3<Integer, Integer, World>> getNearbyChunks(Chunk chunk) {
      return getNearbyChunks(chunk.getWorld(), chunk.getX(), chunk.getZ());
   }

   public static List<AzureAPI.Coord3<Integer, Integer, World>> getNearbyChunks(World world, int x, int z) {
      List<AzureAPI.Coord3<Integer, Integer, World>> chunks = Lists.newArrayListWithExpectedSize(9);

      for(int cx = x - 1; cx < x + 1; ++cx) {
         for(int cz = z - 1; cz < z + 1; ++cz) {
            chunks.add(AzureAPI.wrapCoord(cx, cz, world));
         }
      }

      return chunks;
   }

   public static boolean isSameChunk(Chunk chunk1, Chunk chunk2) {
      String c1w = chunk1.getWorld().getName();
      String c2w = chunk2.getWorld().getName();
      int c1x = chunk1.getX();
      int c2x = chunk2.getX();
      int c1z = chunk1.getZ();
      int c2z = chunk2.getZ();
      return c1w.equals(c2w) && c1x == c2x && c1z == c2z;
   }

   public static String readTxtFile(File file) {
      String readResult = "";

      try {
         FileReader fileread = new FileReader(file);
         BufferedReader br = new BufferedReader(fileread);

         String read;
         try {
            while((read = br.readLine()) != null) {
               readResult = readResult + read + "/r/n";
            }
         } catch (IOException var6) {
         }
      } catch (FileNotFoundException var7) {
      }

      return readResult;
   }

   public static void ChangeTxtFileAndSave(String LastFileString, String newStringToWriteIn, File file) {
      String NewResult = newStringToWriteIn + "\r\n" + LastFileString;
      RandomAccessFile raf = null;

      try {
         raf = new RandomAccessFile(file, "rw");
         raf.writeBytes(NewResult);
      } catch (IOException var7) {
      }

      if (raf != null) {
         try {
            raf.close();
         } catch (IOException var6) {
         }
      }

   }
}
