package ml.jadss.jadgens.listeners;

import ml.jadss.jadgens.JadGens;
import ml.jadss.jadgens.events.MachineBreakEvent;
import ml.jadss.jadgens.utils.Machine;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class PlayerBreakListener implements Listener {

    @EventHandler
    public void PlayerBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        String id = block.getLocation().getWorld().getName() + "_" + block.getLocation().getBlockX() + "_" + block.getLocation().getBlockY() + "_" + block.getLocation().getBlockZ();
        Machine machine = new Machine(id);
        if (machine.getId() == null)
            return;
        event.setCancelled(true);
        MachineBreakEvent newEvent = new MachineBreakEvent(player, machine.getType(), UUID.fromString(machine.getUuid()).equals(player.getUniqueId()), event.getBlock());
        JadGens.getInstance().getServer().getPluginManager().callEvent(newEvent);
        if (newEvent.isCancelled())
            event.setCancelled(true);
        if (UUID.fromString(machine.getUuid()).equals(player.getUniqueId()) || player.hasPermission(JadGens.getInstance().getConfig().getString("messages.bypassPermission"))) {
            if (player.getInventory().firstEmpty() != -1) {
                machine.removeFromConfig();
                player.getInventory().addItem(machine.createItem(machine.getType()));
                block.setType(Material.AIR);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("messages.machinesMessages.broken")));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("messages.noInventorySpace")));
            }
        } else {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("messages.machinesMessages.notTheOwner")));
        }
    }
}
