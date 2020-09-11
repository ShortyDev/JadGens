package ml.jadss.jadgens.listeners;

import ml.jadss.jadgens.JadGens;
import ml.jadss.jadgens.utils.MachineLookup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class PistonMoveListener implements Listener {

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        if (!JadGens.getInstance().getConfig().getBoolean("machinesConfig.preventPistonsMoveMachines"))
            return;
        MachineLookup checker = new MachineLookup();
        for (Block block : event.getBlocks()) {
            if (checker.isMachine(block)) {
                event.setCancelled(true);
                for (Player nearPlayer : Bukkit.getOnlinePlayers()) {
                    if (nearPlayer.getLocation().distance(block.getLocation()) <= 5) {
                        nearPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("messages.noPistonMoving")));
                    }
                }
                return;
            }
        }
    }

    @EventHandler
    public void onPistonRetrace(BlockPistonRetractEvent event) {
        if (!JadGens.getInstance().getConfig().getBoolean("machinesConfig.preventPistonsMoveMachines"))
            return;
        MachineLookup checker = new MachineLookup();
        for (Block block : event.getBlocks()) {
            if (checker.isMachine(block)) {
                event.setCancelled(true);
                for (Player nearPlayer : Bukkit.getOnlinePlayers()) {
                    if (nearPlayer.getLocation().distance(block.getLocation()) <= 5) {
                        nearPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("messages.noPistonMoving")));
                    }
                }
                return;
            }
        }
    }
}
