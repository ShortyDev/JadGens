package ml.jadss.jadgens.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@SuppressWarnings("unused")
@Getter
@Setter
public class CustomJadGensCommandArg extends Event {

    private static final HandlerList handlers = new HandlerList();

    private CommandSender sender;
    private String command;
    private String[] arguments;
    private boolean used;

    public CustomJadGensCommandArg(CommandSender sender, String argCommand, String[] args) {
        this.sender = sender;
        this.command = argCommand;
        this.arguments = args;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}