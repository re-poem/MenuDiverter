package org.repoem.menudiverter;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class MenuDiverter extends JavaPlugin implements Listener {

    FloodgateApi fa;
    @NotNull List<Map<?, ?>> cmdsList = List.of();
    List<MenuCommand> cmdsIList = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        fa = FloodgateApi.getInstance();
        cmdsList = getConfig().getMapList("commands");
        getLogger().info("插件初始化完成！");

        for (Map<?, ?> item : cmdsList) {
            cmdsIList.add(new MenuCommand(item.get("command").toString(),
                    item.get("description").toString(),
                    item.get("usage").toString(),
                    List.of()));
            getLogger().info(item.get("command") + "命令加载！");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
