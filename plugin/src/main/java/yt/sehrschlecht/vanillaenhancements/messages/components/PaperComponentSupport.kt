package yt.sehrschlecht.vanillaenhancements.messages.components

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.inventory.meta.ItemMeta
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class PaperComponentSupport(plugin: VanillaEnhancements) : AbstractComponentSupport(plugin) {

    override fun send(component: Component, receiver: CommandSender) {
        receiver.sendMessage(component)
    }

    override fun setDisplayName(itemMeta: ItemMeta, component: Component) {
        itemMeta.displayName(component)
    }

    override fun getDisplayName(itemMeta: ItemMeta): Component? {
        return itemMeta.displayName()
    }

    override fun setLore(itemMeta: ItemMeta, lore: List<Component>) {
        itemMeta.lore(lore)
    }

    override fun addLore(itemMeta: ItemMeta, loreToAdd: List<Component>) {
        val lore = itemMeta.lore() ?: emptyList()
        val loreMutable = lore.toMutableList()
        loreMutable.addAll(loreToAdd)
        itemMeta.lore(loreMutable)
    }

    override fun getLore(itemMeta: ItemMeta): List<Component> {
        return itemMeta.lore() ?: emptyList()
    }


}