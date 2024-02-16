package yt.sehrschlecht.vanillaenhancements.messages.components

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
abstract class AbstractComponentSupport(protected val plugin: VanillaEnhancements) {
    abstract fun send(component: Component, receiver: CommandSender)
}