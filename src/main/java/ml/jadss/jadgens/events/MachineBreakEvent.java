package ml.jadss.jadgens.events;

import lombok.Getter;
import lombok.Setter;
import ml.jadss.jadgens.utils.Machine;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@SuppressWarnings("unused")
@Getter
@Setter
public class MachineBreakEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private boolean isMachineOwner;
    private int type;
    private Block block;
    private boolean cancelled;

    public MachineBreakEvent(Player player, int machine_type, boolean is_Machine_Owner, Block blocka) {
        this.player = player;
        this.type = machine_type;
        this.isMachineOwner = is_Machine_Owner;
        this.block = blocka;
    }

    public void removeMachine() {
        Machine machine = new Machine(block);
        machine.removeFromConfig();
        block.setType(Material.AIR);
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}