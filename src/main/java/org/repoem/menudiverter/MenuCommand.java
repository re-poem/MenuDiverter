package org.repoem.menudiverter;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class MenuCommand extends BukkitCommand {

    FloodgateApi fa;
    CommandMap commandMap;

    protected MenuCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
        fa = FloodgateApi.getInstance();
        registerCommand();
    }

    private void registerCommand() {
        try {
            final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            commandMap.register("Menus", this);
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String command, @NotNull String[] args) {
        if (command.equalsIgnoreCase(getName()) && (sender instanceof Player)) {
            Player player = (Player) sender;
            if (fa.isFloodgatePlayer(player.getUniqueId())) {
                if (player.hasPermission("menudiverter.forcejava")) {
                    getServer().dispatchCommand(getServer().getConsoleSender(), "dm open " + getName() + " " + player.getName());
                } else {
                    getServer().dispatchCommand(getServer().getConsoleSender(), "gmenu open " + player.getName() + " " + getName() + ".yml");
                }
            } else {
                getServer().dispatchCommand(getServer().getConsoleSender(), "dm open " + getName() + " " + player.getName());
            }
            return true;
        }
        return false;
    }
}
