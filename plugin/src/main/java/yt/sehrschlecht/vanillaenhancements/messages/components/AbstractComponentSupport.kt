package yt.sehrschlecht.vanillaenhancements.messages.components

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.inventory.meta.ItemMeta
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
abstract class AbstractComponentSupport(protected val plugin: VanillaEnhancements) {

    abstract fun send(component: Component, receiver: CommandSender)

    abstract fun setDisplayName(itemMeta: ItemMeta, component: Component)
    abstract fun getDisplayName(itemMeta: ItemMeta) : Component?

    abstract fun setLore(itemMeta: ItemMeta, lore: List<Component>)
    abstract fun addLore(itemMeta: ItemMeta, loreToAdd: List<Component>)
    abstract fun getLore(itemMeta: ItemMeta): List<Component>

}