package org.repoem.menudiverter;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public final class MenuDiverter extends JavaPlugin {

    FloodgateApi fa;
    @NotNull List<Map<?, ?>> commandsList = List.of();

    @Override
    public void onEnable() {
        // Plugin startup logic
        fa = FloodgateApi.getInstance();
        commandsList = getConfig().getMapList("commands");
        saveDefaultConfig();
        getLogger().info("插件初始化完成！");

        try {
            final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            for (Map<?, ?> item : commandsList) {
                commandMap.register("Menus",
                        new MenuCommand(item.get("command").toString(),
                                item.get("description").toString(),
                                item.get("usage").toString(),
                                List.of()));
                getLogger().info(item.get("command") + "命令成功加载！");
            }
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            getLogger().info(exception.getMessage());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
