MenuDiverter
---
一个用于DeluxeMenus和GeyserMenu插件之间菜单命令分流的插件，检测到的玩家是java版就会弹出DeluxeMenus对应的箱子菜单，是基岩版就会弹出GeyserMenu的对应表单

以下是一个配置文件的示例
```
commands:
  - command: menu #指令名
    description: "菜单命令" #指令的注解
    usage: /menu #指令的用法提示信息
  - command: menu1 #指令名
    description: "菜单命令1" #指令的注解
    usage: /menu1 #指令的用法提示信息
  ......
```
菜单指令直接绑定菜单名，在DeluxeMenus中体现为config配置文件内的配置，在GeyserMenu中体现为菜单的文件名（不带`.yml`后缀的那部分）。实质上，这就是使用这两个插件的命令时所使用的菜单参数。
以下是一个例子：

在DeluxeMenus中，有一个这样的菜单：
```
gui_menus:
  cd: 
    file: cd.yml
```
同时在GeyserMenu中，有一个菜单，文件名是`cd.yml`。

此时插件配置文件应该是这样：
```
commands:
  - command: cd #指令名
    description: "主菜单" #指令的注解
    usage: /cd #指令的用法提示信息
```

***由于热重载需要玩家重进服务器才能获得最好效果（不重进只能够被执行，没有自动提示），并且实现比较麻烦，所以此插件不支持热重载，plugman不起效果。***

推荐与[DM2GM](https://github.com/re-poem/DM2GM)搭配使用。