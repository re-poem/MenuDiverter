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

    FloodgateApi fa;  //floodgate api
    @NotNull List<Map<?, ?>> cmdsList = List.of();  //从文件中读取的命令列表
    List<MenuCommand> cmdsIList = new ArrayList<>();  //命令实例

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        fa = FloodgateApi.getInstance();
        cmdsList = getConfig().getMapList("commands");
        getLogger().info("[§h§lMenuDiverter§r] 插件初始化完成！");

        for (Map<?, ?> item : cmdsList) {
            cmdsIList.add(new MenuCommand(item.get("command").toString(),
                    item.get("description").toString(),
                    item.get("usage").toString(),
                    List.of()));  //实例创建时自动注册
            getLogger().info(item.get("command") + "命令加载！");
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("menudiverter") || command.getName().equalsIgnoreCase("menud")) {
            if (args.length == 0) {  //空传参提示
                sender.sendMessage("[§h§lMenuDiverter§r] 使用/menud change来切换使用箱子菜单或者基岩菜单");
                return true;
            }

            LuckPerms lp;
            try {
                lp = LuckPermsProvider.get();
            } catch (IllegalStateException e) {
                sender.sendMessage("[§h§lMenuDiverter§r] 未找到LuckPerms！");
                return true;
            }  //luckperms api取得

            if (args.length == 1 && args[0].equalsIgnoreCase("change") && (sender instanceof Player)) {
                User user = lp.getPlayerAdapter(Player.class).getUser((Player) sender);
                Node forcejava = Node.builder("menudiverter.forcejava").build();
                if (sender.hasPermission("menudiverter.change") || fa.isFloodgatePlayer(((Player) sender).getUniqueId())) {
                    if (sender.hasPermission("menudiverter.forcejava")) {
                        user.data().remove(forcejava);
                        sender.sendMessage("[§h§lMenuDiverter§r] 切换完成！你现在的使用的是：" + "基岩菜单");
                    } else {
                        user.data().add(forcejava);
                        sender.sendMessage("[§h§lMenuDiverter§r] 切换完成！你现在的使用的是：" + "箱子菜单");
                    }
                    lp.getUserManager().saveUser(user);
                } else {
                    sender.sendMessage("[§h§lMenuDiverter§r] 您无权切换！");
                }
            } else {
                sender.sendMessage("[§h§lMenuDiverter§r] 无效的参数！");
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
