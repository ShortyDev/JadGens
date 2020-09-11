package ml.jadss.jadgens.listeners;

import ml.jadss.jadgens.JadGens;
import ml.jadss.jadgens.utils.MachineLookup;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockExplodeListener implements Listener {

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (!JadGens.getInstance().getConfig().getBoolean("machinesConfig.preventMachineExplosion"))
            return;
        MachineLookup checker = new MachineLookup();
        for (Block block : event.blockList()) {
            if (checker.isMachine(block)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!JadGens.getInstance().getConfig().getBoolean("machinesConfig.preventMachineExplosion"))
            return;
        MachineLookup checker = new MachineLookup();
        for (Block block : event.blockList()) {
            if (checker.isMachine(block)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
