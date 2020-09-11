package ml.jadss.jadgens.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@SuppressWarnings("unused")
@Getter
@Setter
public class MachinePlaceEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private int type;
    private boolean cancelled;

    public MachinePlaceEvent(Player player, int machine_type) {
        this.player = player;
        this.type = machine_type;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

}