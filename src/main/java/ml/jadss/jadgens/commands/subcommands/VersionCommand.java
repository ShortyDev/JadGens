package ml.jadss.jadgens.commands.subcommands;

import ml.jadss.jadgens.JadGens;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class VersionCommand {

    public VersionCommand(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3&lJadGens &7>> &eThis &bserver &eis &a&lrunning &3&lJadGens &bv" + JadGens.getInstance().getDescription().getVersion() + " &eby &3Jadss_pt"));
        if (JadGens.getInstance().isHookedVault())
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eHooked into &3Vault&e!"));
        if (JadGens.getInstance().isHookedPlaceHolderAPI())
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eHooked into &3PlaceHolderAPI&e!"));
        if (JadGens.getInstance().isHookedPlayerPoints())
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3JadGens &7>> &eHooked into &3PlayerPoints&e!"));
    }
}
