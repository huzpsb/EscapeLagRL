package com.mcml.space.core;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager {
    public static YamlConfiguration data;
    private static File DataFile;

    public static void init() {
        DataFile = new File(EscapeLag.plugin.getDataFolder(), "SaveData");
        if (!DataFile.exists()) {
            try {
                DataFile.getParentFile().mkdirs();
                DataFile.createNewFile();
            } catch (IOException var1) {
                Logger.getLogger(EscapeLag.class.getName()).log(Level.SEVERE, null, var1);
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
            Logger.getLogger(EscapeLag.class.getName()).log(Level.SEVERE, null, var1);
        }

    }
}
