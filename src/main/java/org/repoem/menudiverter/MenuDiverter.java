package org.repoem.menudiverter;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("menudiverter") || command.getName().equalsIgnoreCase("menud")) {
            if (args.length == 0) {
                sender.sendMessage("使用/menud change来切换使用箱子菜单或者基岩菜单");
                return true;
            }
            LuckPerms lp;
            try {
                lp = LuckPermsProvider.get();
            } catch (IllegalStateException e) {
                sender.sendMessage("未找到LuckPerms！");
                return true;
            }
            if (args[0].equalsIgnoreCase("change") && (sender instanceof Player)) {
                User user = lp.getPlayerAdapter(Player.class).getUser((Player) sender);
                Node forcejava = Node.builder("menudiverter.forcejava").build();
                if (sender.hasPermission("menudiverter.change") || fa.isFloodgatePlayer(((Player) sender).getUniqueId())) {
                    if (sender.hasPermission("menudiverter.forcejava")) {
                        user.data().remove(forcejava);
                        sender.sendMessage("切换完成！你现在的使用的是：" + "基岩菜单");
                    } else {
                        user.data().add(forcejava);
                        sender.sendMessage("切换完成！你现在的使用的是：" + "箱子菜单");
                    }
                    lp.getUserManager().saveUser(user);
                } else {
                    sender.sendMessage("您无权切换！");
                }
            } else {
                sender.sendMessage("无效的参数！");
            }
            return true;
        }
        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
