package ml.jadss.jadgens.management;

import ml.jadss.jadgens.JadGens;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataFile {

    private File dataFile;
    private FileConfiguration data;

    public FileConfiguration getData() {
        return data;
    }

    public void setupDataFile() {
        dataFile = new File(JadGens.getInstance().getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &3Data&e.&ayml &aCreated&e! If it &cwasn't &acreated &eplease be sure to &3&krestart &ethe &b&lserver&e!"));
            }
        }
        data = YamlConfiguration.loadConfiguration(dataFile);

        data.set("machines.setup", true);
        data.set("machines.setup", null);
        saveData();
    }

    public void saveData() {
        try {
            data.save(dataFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &c&lCannot &esave &3Data&e.&ayml&e!"));
        }
    }

    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dataFile);
    }
}
