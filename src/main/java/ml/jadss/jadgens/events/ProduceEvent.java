package ml.jadss.jadgens.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@SuppressWarnings("unused")
@Getter
@Setter
public class ProduceEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;

    public ProduceEvent() {
    }

    public HandlerList getHandlers() {
        return handlers;
    }

}