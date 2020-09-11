package ml.jadss.jadgens.listeners;

import ml.jadss.jadgens.JadGens;
import ml.jadss.jadgens.utils.Fuel;
import ml.jadss.jadgens.utils.Machine;
import ml.jadss.jadgens.utils.MachineLookup;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        if (new Fuel().isFuel(item)) {
            event.setCancelled(true);
            if (player.isSneaking()) {
                Fuel fuel = new Fuel(item);
                if (new MachineLookup().isMachine(block)) {
                    String id = block.getLocation().getWorld().getName() + "_" + block.getLocation().getBlockX() + "_" + block.getLocation().getBlockY() + "_" + block.getLocation().getBlockZ();
                    Machine machine = new Machine(id);
                    if (!(JadGens.getInstance().getConfig().getBoolean("machines." + machine.getType() + ".needsFuelToProduce"))) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("messages.fuelMessages.machineNotAcceptingFuel")));
                    } else {
                        Integer drops = machine.getDropsRemaining();
                        if (drops + fuel.getDrops() <= JadGens.getInstance().getConfig().getInt("machines." + machine.getType() + ".maxFuel")) {
                            ItemStack inHand = player.getItemInHand();
                            inHand.setAmount(inHand.getAmount() - 1);
                            player.setItemInHand(inHand);
                            machine.setDropsRemaining(machine.getDropsRemaining() + fuel.getDrops());
                            machine.addToConfig();
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("messages.fuelMessages.used")));
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("messages.fuelMessages.doesntAcceptMoreFuel")));
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("messages.fuelMessages.notAMachine")));
                }
            }
        }
    }
}
