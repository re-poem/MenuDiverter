package org.repoem.menudiverter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class MenuCommand extends Command {

    FloodgateApi fa;

    protected MenuCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
        fa = FloodgateApi.getInstance();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String command, @NotNull String[] args) {
        if (command.equalsIgnoreCase(getName()) && (sender instanceof Player)) {
            Player player = (Player) sender;
            if (fa.isFloodgatePlayer(player.getUniqueId())) {
                getServer().dispatchCommand(getServer().getConsoleSender(), "gmenu open " + player.getName() + " " + getName() + ".yml");
            } else {
                getServer().dispatchCommand(getServer().getConsoleSender(), "dm open " + getName() + " " + player.getName());
            }
            return true;
        }
        return false;
    }
}
