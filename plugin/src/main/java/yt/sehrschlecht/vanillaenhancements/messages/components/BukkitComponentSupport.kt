package yt.sehrschlecht.vanillaenhancements.messages.components

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.command.CommandSender
import org.bukkit.inventory.meta.ItemMeta
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class BukkitComponentSupport(plugin: VanillaEnhancements) : AbstractComponentSupport(plugin) {
    val legacyComponentSerializer = LegacyComponentSerializer.legacySection()

    private fun componentToString(component: Component) : String {
        return legacyComponentSerializer.serialize(component)
    }

    private fun componentsToStrings(components: List<Component>) : List<String> {
        return components.map(::componentToString)
    }

    private fun componentFromString(string: String) : Component {
        return legacyComponentSerializer.deserialize(string)
    }

    private fun componentsFromStrings(strings: List<String>) : List<Component> {
        return strings.map(::componentFromString)
    }

    override fun send(component: Component, receiver: CommandSender) {
        plugin.adventure().sender(receiver).sendMessage(component)
    }

    override fun setDisplayName(itemMeta: ItemMeta, component: Component) {
        itemMeta.setDisplayName(componentToString(component))
    }

    override fun getDisplayName(itemMeta: ItemMeta): Component {
        return componentFromString(itemMeta.displayName)
    }

    override fun setLore(itemMeta: ItemMeta, lore: List<Component>) {
        itemMeta.lore = componentsToStrings(lore)
    }

    override fun addLore(itemMeta: ItemMeta, loreToAdd: List<Component>) {
        val stringLore = itemMeta.lore
        stringLore ?: return setLore(itemMeta, loreToAdd)
        val lore = componentsFromStrings(stringLore).toMutableList()
        lore.addAll(loreToAdd)
        setLore(itemMeta, lore)
    }

    override fun getLore(itemMeta: ItemMeta): List<Component> {
        val stringLore = itemMeta.lore
        stringLore ?: return emptyList()
        return componentsFromStrings(stringLore)
    }

}