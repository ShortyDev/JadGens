package ml.jadss.jadgens.listeners;

import ml.jadss.jadgens.JadGens;
import ml.jadss.jadgens.utils.Compatibility;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (new Compatibility().getTitle(e.getClickedInventory(), e.getView()).equals(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("machineGui.title")))) {
            e.setCancelled(true);
        }
    }
}
