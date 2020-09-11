package ml.jadss.jadgens.listeners;

import ml.jadss.jadgens.JadGens;
import ml.jadss.jadgens.events.MachinePlaceEvent;
import ml.jadss.jadgens.nbt.NBTCompound;
import ml.jadss.jadgens.nbt.NBTItem;
import ml.jadss.jadgens.utils.Machine;
import ml.jadss.jadgens.utils.MachineLimiter;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerBuildListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack item = event.getItemInHand();
        NBTCompound nbtCompound = new NBTItem(item);
        MachineLimiter limiter = new MachineLimiter();

        if (nbtCompound.getBoolean("JadGens_machine")) {
            if (!limiter.canPlaceMachine(player)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("messages.machinesMessages.limitReached")));
                return;
            }
            int machineType = nbtCompound.getInteger("JadGens_machineType");
            MachinePlaceEvent newEvent = new MachinePlaceEvent(player, machineType);
            JadGens.getInstance().getServer().getPluginManager().callEvent(newEvent);
            if (newEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }
            Machine machine = new Machine(block.getLocation(), machineType, player.getUniqueId().toString());
            machine.addToConfig();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("messages.machinesMessages.placed")));
        }
    }
}
