package yt.sehrschlecht.vanillaenhancements.gui

import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import yt.sehrschlecht.schlechteutils.commands.CommandUtils
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.messages.Message
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.send

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class VECommand(val plugin: VanillaEnhancements) : CommandExecutor, TabExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            Message.COMMAND_NOT_A_PLAYER.send(sender, plugin)
            return true
        }
        if (args?.size == 1 || args?.size == 2) {
            val moduleTagArg = args[0]
            val tag = plugin.moduleRegistry.collectTags().find { t -> t.key.equals(moduleTagArg, true) }
            if (tag == null) {
                Message.COMMAND_VE_MODULE_TAG_NOT_FOUND.send(sender, plugin)
                return true
            }
            if (args.size == 1) {
                ModuleListMenu.getInventory(plugin, tag, ModuleTagsMenu.getInventory(plugin)).open(sender)
                return true
            }
            val moduleKeyArg = args[1]
            val namespacedKey = NamespacedKey.fromString(moduleKeyArg)
            if (namespacedKey == null) {
                Message.COMMAND_VE_MODULE_TAG_MALFORMATTED.send(sender, plugin)
                return true
            }
            val module = plugin.moduleRegistry.getModule(namespacedKey)
            if (module == null) {
                Message.COMMAND_VE_MODULE_NOT_FOUND.send(sender, plugin)
                return true
            }
            ModuleMenu.getInventory(plugin, module, ModuleListMenu.getInventory(plugin, tag)).open(sender)
            return true
        }
        if (args?.size != 0) {
            Message.COMMAND_VE_USAGE.send(sender, plugin)
            return true
        }
        MainMenu.getInventory(plugin).open(sender)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        args?: return mutableListOf()
        if (args.isEmpty() || args.size > 2) return mutableListOf()
        if (args.size == 1) {
            val tags = plugin.moduleRegistry.collectTags()
            return CommandUtils.tabComplete(tags.map(ModuleTag::key), args[0])
        } else {
            val tag = plugin.moduleRegistry.collectTags().find { t -> t.key.equals(args[0], true) } ?: return mutableListOf()
            val moduleKeysForTag = plugin.moduleRegistry.getModulesByTag(tag).map { module -> module.moduleKey.toString() }
            return CommandUtils.tabComplete(moduleKeysForTag, args[1])
        }
    }

}