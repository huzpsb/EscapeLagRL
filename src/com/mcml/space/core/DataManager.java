package com.mcml.space.core;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataManager {
   private static File DataFile;
   public static YamlConfiguration data;

   public static void init() {
      DataFile = new File(EscapeLag.plugin.getDataFolder(), "SaveData");
      if (!DataFile.exists()) {
         try {
            DataFile.getParentFile().mkdirs();
            DataFile.createNewFile();
         } catch (IOException var1) {
            Logger.getLogger(EscapeLag.class.getName()).log(Level.SEVERE, (String)null, var1);
         }

         data = YamlConfiguration.loadConfiguration(DataFile);
         save();
      }

      data = YamlConfiguration.loadConfiguration(DataFile);
   }

   public static void save() {
      try {
         data.save(DataFile);
      } catch (IOException var1) {
         Logger.getLogger(EscapeLag.class.getName()).log(Level.SEVERE, (String)null, var1);
      }

   }
}
