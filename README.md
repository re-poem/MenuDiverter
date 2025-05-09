# MenuDiverter - 跨版本菜单分流插件

## 📌 核心功能
- **智能分流**：自动检测玩家客户端版本
    - Java版 → 触发 DeluxeMenus 的箱子菜单
    - 基岩版 → 触发 GeyserMenu 的表单菜单（也可自己选择是否使用）
- **无缝兼容**：统一管理两种菜单系统的命令入口
- **依赖**：DeluxeMenus, GeyserMenu, LuckPerms
## ⚙️ 配置指南
```yaml
commands:
  - command: "指令名称"        # 必须与菜单ID一致
    description: "功能描述"    # 指令提示文本
    usage: "用法示例"         # 输入错误时的提示
```
| 系统类型        | 对应关系              | 示例                      |
|-------------|-------------------|-------------------------|
| DeluxeMenus | `gui_menus`下的配置键名 | `gui_menus: {shop:...}` |
| GeyserMenu  | 菜单文件名(无扩展名)       | `shop.yml` → "shop"     |

## ⚠️ 重要说明

###  重载限制

- 🔴 **必须重启服务器生效**
- 🔴 **不支持plugman热重载**

### 最佳实践

- ✅ **推荐配合 [DM2GM](https://github.com/re-poem/DM2GM) 使用**
- ✅ **为每个命令添加清晰的usage说明**  

## 🔗 命令

基岩版可使用 `menudiverter change` 或 `menud change` 来自主决定是否使用基岩版菜单

（使用此命令需要 `menudiverter.change` 权限，您可能需要提前在 `LuckPerms`的默认用户组添加这个权限）

（原理实质上是玩家的 `menudiverter.forcejava` 权限，当基岩版玩家存在这个权限时，玩家打开的菜单被强制使用箱子菜单）
