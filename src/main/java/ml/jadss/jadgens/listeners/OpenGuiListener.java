package ml.jadss.jadgens.listeners;

import ml.jadss.jadgens.JadGens;
import ml.jadss.jadgens.utils.Machine;
import ml.jadss.jadgens.utils.MachineLookup;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class OpenGuiListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        Player player = event.getPlayer();
        if (player.isSneaking())
            return;
        if (!player.getItemInHand().getType().equals(Material.AIR))
            return;
        MachineLookup machineCaller = new MachineLookup();
        if (machineCaller.isMachine(event.getClickedBlock())) {
            Location location = event.getClickedBlock().getLocation();
            String id = location.getWorld().getName() + "_" + location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();
            Machine machineBlock = new Machine(id);
            if (player.getUniqueId().equals(UUID.fromString(machineBlock.getUuid())) || player.hasPermission(JadGens.getInstance().getConfig().getString("messages.bypassPermission"))) {
                player.openInventory(machineBlock.createGUI());
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("messages.machinesMessages.notTheOwner")));
            }
        }
    }
}
