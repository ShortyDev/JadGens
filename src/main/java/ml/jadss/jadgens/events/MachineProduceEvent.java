package ml.jadss.jadgens.events;

import lombok.Getter;
import lombok.Setter;
import ml.jadss.jadgens.JadGens;
import ml.jadss.jadgens.utils.Machine;
import ml.jadss.jadgens.utils.PurgeMachines;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

import static ml.jadss.jadgens.JadGensAPI.validatePlugin;

@SuppressWarnings("unused")
@Getter
@Setter
public class MachineProduceEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Machine machine;
    private int dropsRemaining;
    private String id;
    private Location location;
    private UUID owner;
    private int machineType;

    private boolean cancelled;

    public MachineProduceEvent(Machine machine) {
        this.machine = machine;
        this.dropsRemaining = machine.getDropsRemaining();
        this.id = machine.getId();
        this.location = machine.getLocation();
        this.owner = UUID.fromString(machine.getUuid());
        this.machineType = machine.getType();
    }

    public void setDropsRemaining(int dropsRemaining) {
        machine.setDropsRemaining(dropsRemaining);
    }

    public void removeMachine(Plugin plugin) {
        if (validatePlugin(plugin)) {
            if (JadGens.getInstance().isAPIDebugEnabled())
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&3JadGens &7>> &eThe &3&lMachine &ewith &b&lID &a\"" + id + "\" &ewas &c&lRemoved &eby "));
            new PurgeMachines().removeMachine(this.getId());
        }
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}