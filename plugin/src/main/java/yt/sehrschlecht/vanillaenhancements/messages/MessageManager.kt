package yt.sehrschlecht.vanillaenhancements.messages

import dev.dejvokep.boostedyaml.YamlDocument
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class MessageManager(private val document: YamlDocument, private val plugin: VanillaEnhancements) {

    fun init() {
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
        val component = asComponent(message, *args)
        if (plugin.isUsingPaper) {
            receiver.sendMessage(component)
        } else {
            plugin.adventure().sender(receiver).sendMessage(component)
        }
    }

    /**
     * Converts a message with args to a component.
     * @param args The args for the message. Tags will be escaped for all objects that are not a [Component].
     */
    private fun asComponent(message: Message, vararg args: Any): Component {
        return plugin.miniMessage().deserialize(
            format(get(message), args.map { arg ->
                if (arg is Component) {
                    return@map plugin.miniMessage().serialize(arg)
                }
                return@map plugin.miniMessage().escapeTags(arg.toString())
            }.toTypedArray())
        )
    }

    private fun format(string: String, args: Array<String>): String {
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