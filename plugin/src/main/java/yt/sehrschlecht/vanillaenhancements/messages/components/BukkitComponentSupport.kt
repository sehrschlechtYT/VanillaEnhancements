package yt.sehrschlecht.vanillaenhancements.messages.components

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class BukkitComponentSupport(plugin: VanillaEnhancements) : AbstractComponentSupport(plugin) {
    override fun send(component: Component, receiver: CommandSender) {
        plugin.adventure().sender(receiver).sendMessage(component)
    }
}