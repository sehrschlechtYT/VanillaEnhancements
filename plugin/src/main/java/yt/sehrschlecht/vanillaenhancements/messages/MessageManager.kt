package yt.sehrschlecht.vanillaenhancements.messages

import dev.dejvokep.boostedyaml.YamlDocument
import org.bukkit.command.CommandSender
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.messages.components.AbstractComponentSupport
import yt.sehrschlecht.vanillaenhancements.messages.components.BukkitComponentSupport
import yt.sehrschlecht.vanillaenhancements.messages.components.PaperComponentSupport
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.asComponent
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class MessageManager(private val document: YamlDocument, private val plugin: VanillaEnhancements) {
    lateinit var componentSupport: AbstractComponentSupport

    fun init() {
        componentSupport = if (plugin.isUsingPaper) {
            Debug.MESSAGES.log("Initializing component support for Paper.")
            PaperComponentSupport(plugin)
        } else {
            Debug.MESSAGES.log("Initializing component support for Bukkit.")
            BukkitComponentSupport(plugin)
        }

        Debug.MESSAGES.log("Initializing messages...")
        Debug.MESSAGES.log("Found {} default messages.", Message.values().size)

        Message.values().forEach { message ->
            Debug.MESSAGES.log("Found message {} with default value \"{}\"", message.key, message.defaultValue)
            if (!document.contains(message.key)) {
                Debug.MESSAGES.log("Creating key msg.{} with default value {}", message.key, message.defaultValue)
                document.set(message.key, message.defaultValue)
            } else if (message.defaultValue == null) {
                Debug.MESSAGES.log("Removing message {} because its default value is null.", message.key)
                document.remove(message.key)
            }
        }

        document.save()
    }

    fun send(message: Message, receiver: CommandSender, vararg args: Any) {
        val component = message.asComponent(plugin, *args)
        componentSupport.send(component, receiver)
    }

    fun format(string: String, args: Array<String>): String {
        var message = string
        for (arg in args) {
            message = message.replaceFirst("\\{}".toRegex(), arg)
        }
        return message
    }

    fun get(message: Message): String {
        if (!document.contains(message.key)) {
            Debug.CONFIG.log("Tried accessing non-existent message {}!", message.key)
            return "Missing translation!"
        }
        return document.getString(message.key)
    }

}