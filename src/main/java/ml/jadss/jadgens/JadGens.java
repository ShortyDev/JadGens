package ml.jadss.jadgens;

import lombok.Getter;
import lombok.Setter;
import ml.jadss.jadgens.commands.JadGensCommand;
import ml.jadss.jadgens.commands.TabCompleter;
import ml.jadss.jadgens.listeners.*;
import ml.jadss.jadgens.management.DataFile;
import ml.jadss.jadgens.management.MetricsLite;
import ml.jadss.jadgens.tasks.ProduceMachineDelayTask;
import ml.jadss.jadgens.utils.PlaceHolders;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

@Getter
public class JadGens extends JavaPlugin {

    @Getter
    private static JadGens instance;
    private DataFile dataFile;
    //hook booleans
    private boolean hookedVault = false;
    private boolean hookedPlaceHolderAPI = false;
    private boolean hookedPlayerPoints = false;
    //hooks
    private Economy eco;
    private PlayerPointsAPI pointsAPI;
    private MetricsLite metrics;
    //tasks
    @Setter
    private BukkitTask task;
    //API STUFF
    private boolean apiDebug;

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();

        hookVault();
        hookPlaceHolderAPI();
        hookPlayerPoints();

        if (hookedVault) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eHooked into &bVault&e!"));
        }
        if (hookedPlaceHolderAPI) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eHooked into &bPlaceHolderAPI&e!"));
        }
        if (hookedPlayerPoints) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eHooked into &bPlayerPoints&e!"));
        }

        if (!setupShop()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        dataFile = new DataFile();
        dataFile.setupDataFile();

        task = new ProduceMachineDelayTask().runTaskTimer(this, 0L, getConfig().getLong("machinesConfig.machinesDelay") * 20);

        setupAPIDebug();
        registerStuff();

        metrics = new MetricsLite(this, 8789);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &3Plugin &bEnabled&7!"));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &bDisabling &3Plugin&e!"));
    }

    public void setupAPIDebug() {
        apiDebug = getConfig().getBoolean("messages.debugAPI");
    }

    public boolean isAPIDebugEnabled() {
        if (this.getConfig().getBoolean("messages.debugAPI") != apiDebug) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eA plugin tried to disable API Debug, but it was reverted."));
            apiDebug = getConfig().getBoolean("messages.debugAPI");
        }
        return apiDebug;
    }
    //END OF API STUFF


    //Other stuff
    private void registerStuff() {
        //Register Commands
        getCommand("JadGens").setExecutor(new JadGensCommand());
        getCommand("JadGens").setTabCompleter(new TabCompleter());

        //Register Listeners
        getServer().getPluginManager().registerEvents(new PlayerBuildListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new OpenGuiListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new BlockExplodeListener(), this);
        getServer().getPluginManager().registerEvents(new PistonMoveListener(), this);
        getServer().getPluginManager().registerEvents(new ShopListeners(), this);
    }

    //SETUP SHOP STUFF
    private boolean setupShop() {
        if (getConfig().getBoolean("shop.enabled")) {
            for (String key : getConfig().getConfigurationSection("machines").getKeys(false)) {
                if (getConfig().getString("machines." + key + ".shop.currency").equalsIgnoreCase("ECO")) {
                    if (!hookedVault) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eCouldn't &b&lstartup &ebecause, a &3machine currency &eis &a\"ECO\"&e, but &3&lVault &cwasn't &afound&e!"));
                        return false;
                    }
                }
                if (getConfig().getString("machines." + key + ".shop.currency").equalsIgnoreCase("POINTS")) {
                    if (!hookedPlayerPoints) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eCouldn't &b&lstartup &ebecause, a &3machine currency &eis &a\"POINTS\"&e, but &3&lPlayerPoints &cwasn't &afound&e!"));
                        return false;
                    }
                }
            }
            for (String key : getConfig().getConfigurationSection("fuels").getKeys(false)) {
                if (getConfig().getString("fuels." + key + ".shop.currency").equalsIgnoreCase("ECO")) {
                    if (!hookedVault) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eCouldn't &b&lstartup &ebecause, a &3machine currency &eis &a\"ECO\"&e, but &3&lVault &cwasn't &afound&e!"));
                        return false;
                    }
                }
                if (getConfig().getString("machines." + key + ".shop.currency").equalsIgnoreCase("POINTS")) {
                    if (!hookedPlayerPoints) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eCouldn't &b&lstartup &ebecause, a &3machine currency &eis &a\"POINTS\"&e, but &3&lPlayerPoints &cwasn't &afound&e!"));
                        return false;
                    }
                }
            }
        } else if (!getConfig().getBoolean("shop.enabled")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &3Shop &eis &cDisabled&e!"));
            return true;
        }
        return true;
    }


    //Hooks
    private void hookVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            hookedVault = false;
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            hookedVault = false;
            return;
        }
        eco = rsp.getProvider();
        hookedVault = true;
    }

    private void hookPlayerPoints() {
        if (getServer().getPluginManager().getPlugin("PlayerPoints") == null) {
            hookedPlayerPoints = false;
            return;
        }
        pointsAPI = new PlayerPointsAPI((PlayerPoints) getServer().getPluginManager().getPlugin("PlayerPoints"));
        hookedPlayerPoints = true;
    }

    private void hookPlaceHolderAPI() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return;
        }
        hookedPlaceHolderAPI = true;
        new PlaceHolders().register();
    }
}