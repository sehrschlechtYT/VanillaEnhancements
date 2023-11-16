package yt.sehrschlecht.vanillaenhancements.gui

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import yt.sehrschlecht.schlechteutils.commands.CommandUtils
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class VECommand(val plugin: VanillaEnhancements) : CommandExecutor, TabExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cYou must be a player to execute this command!")
            return true
        }
        if (args?.size == 1) {
            val arg = args[0]
            val tag = plugin.moduleRegistry.collectTags().find { t -> t.key.equals(arg, true) }
            if (tag == null) {
                sender.sendMessage("§cThe given module tag was not found!")
                return true
            }
            ModuleListMenu.getInventory(plugin, tag).open(sender)
            return true
        }
        if (args?.size != 0) {
            sender.sendMessage("§cCommand usage: /ve [module tag]")
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
        if (args.size != 1) return mutableListOf()
        val tags = plugin.moduleRegistry.collectTags()
        return CommandUtils.tabComplete(tags.map(ModuleTag::key), args[0])
    }

}