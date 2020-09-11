package ml.jadss.jadgens.utils;

import ml.jadss.jadgens.JadGens;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;

public class PurgeMachines {

    public PurgeMachines() {
    }

    public void purgeMachines() {
        Set<String> keys = getData().getConfigurationSection("machines").getKeys(false);
        for (String key : keys) {
            int x = getData().getInt("machines." + key + ".x");
            int y = getData().getInt("machines." + key + ".y");
            int z = getData().getInt("machines." + key + ".z");
            World world = Bukkit.getServer().getWorld(getData().getString("machines." + key + ".world"));
            if (world == null) {
                getData().set("machines." + key, null);
            } else {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.AIR);
                getData().set("machines." + key, null);
            }
        }
        getData().set("machines", null);
        getData().set("machines.setup", true);
        getData().set("machines.setup", null);
        JadGens.getInstance().getDataFile().saveData();
        JadGens.getInstance().getDataFile().reloadData();
    }

    public boolean removeIfAir(String id) {
        if (id == null)
            return false;
        if (!JadGens.getInstance().getConfig().getBoolean("machinesConfig.autoDestroy"))
            return false;
        World world = Bukkit.getServer().getWorld(getData().getString("machines." + id + ".world"));
        if (world == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eThe &3&lMachine &ewith &b&lID &a\"" + id + "\" &ewas &c&lRemoved&e!"));
            getData().set("machines." + id, null);
        }
        Location loc = new Location(world, getData().getInt("machines." + id + ".x"),
                getData().getInt("machines." + id + ".y"),
                getData().getInt("machines." + id + ".z"));

        if (new MachineLookup().isMachine(loc.getBlock())) {
            if (loc.getBlock().getType().equals(Material.AIR)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eThe &3&lMachine &ewith &b&lID &a\"" + id + "\" &ewas &c&lRemoved&e!"));
                getData().set("machines." + id, null);
            }
        }
        return false;
    }

    public void removeMachine(String id) {
        if (id == null)
            return;
        Machine mac = new Machine(id);
        if (mac.getId() == null)
            return;
        if (mac.getLocation().getWorld() == null) {
            getData().set("machines." + id, null);
        }
        mac.getLocation().getBlock().setType(Material.AIR);
        getData().set("machines." + id, null);
    }

    public FileConfiguration getData() {
        return JadGens.getInstance().getDataFile().getData();
    }

}
