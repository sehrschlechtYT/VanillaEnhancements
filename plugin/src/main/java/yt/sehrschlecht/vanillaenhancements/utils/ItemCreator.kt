package yt.sehrschlecht.vanillaenhancements.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.OfflinePlayer
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.messages.components.AbstractComponentSupport

/**
 * ItemBuilder using Kotlin DSLs
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ItemCreator {
    private val stack: ItemStack
    private val meta: ItemMeta
    private val componentSupport: AbstractComponentSupport

    constructor(stack: ItemStack, init: ItemCreator.() -> Unit) {
        componentSupport = VanillaEnhancements.getPlugin().messageManager.componentSupport
        this.stack = stack
        this.meta = stack.itemMeta ?: throw IllegalArgumentException("ItemStack has no ItemMeta!")
        init(this)
    }

    constructor(material: Material, init: ItemCreator.() -> Unit) {
        componentSupport = VanillaEnhancements.getPlugin().messageManager.componentSupport
        this.stack = ItemStack(material)
        this.meta = stack.itemMeta ?: throw IllegalArgumentException("ItemStack has no ItemMeta!")
        init(this)
    }

    fun type(material: Material) {
        stack.type = material
    }

    fun color(color: Color?) {
        if (meta !is LeatherArmorMeta) throw IllegalArgumentException("ItemMeta is not a LeatherArmorMeta!")
        meta.setColor(color)
    }

    fun resetColor() {
        color(null)
    }

    fun glow(glow: Boolean = true, enchant: Enchantment = Enchantment.DURABILITY, level: Int = 1) {
        if (glow) {
            meta.addEnchant(enchant, level, true)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        } else {
            meta.removeEnchant(enchant)
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
    }

    fun unbreakable(unbreakable: Boolean = true) {
        meta.isUnbreakable = unbreakable
    }

    fun amount(amount: Int) {
        stack.amount = amount
    }

    fun fullStack() {
        val maxStackSize = stack.maxStackSize
        if (maxStackSize != -1) {
            amount(maxStackSize)
        }
    }

    fun skullOwner(owner: String) {
        if (meta !is SkullMeta) throw IllegalArgumentException("ItemMeta is not a SkullMeta!")
        @Suppress("DEPRECATION")
        meta.owner = owner
    }

    fun skullOwner(owner: OfflinePlayer) {
        if (meta !is SkullMeta) throw IllegalArgumentException("ItemMeta is not a SkullMeta!")
        meta.owningPlayer = owner
    }

    @Deprecated(message = "Use #displayName(Component)")
    fun displayName(name: String) {
        meta.setDisplayName(name)
    }

    fun displayName(name: Component) = componentSupport.setDisplayName(meta, name)

    @Deprecated(message = "Use #getDisplayNameComponent")
    fun getDisplayName(): String {
        return meta.displayName
    }

    fun getDisplayNameComponent(): Component? = componentSupport.getDisplayName(meta)

    @Deprecated(message = "Use #setLore")
    fun lore(vararg lore: String) {
        meta.lore = lore.map { if (!it.startsWith("§")) "§f$it" else it }
    }

    @Deprecated(message = "Use #setLore")
    fun lore(lore: List<String>) {
        meta.lore = lore.map { if (!it.startsWith("§")) "§f$it" else it }
    }

    private fun fixLoreColors(components: List<Component>): List<Component> {
        return components.map {
            it.colorIfAbsent(NamedTextColor.WHITE)
            it.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
        }
    }

    fun setLore(vararg lore: Component) = setLore(lore.toList())
    fun setLore(lore: List<Component>) = componentSupport.setLore(meta, fixLoreColors(lore))

    @Deprecated(message = "Use #appendLore")
    fun addLore(vararg lore: String) {
        val currentLore = meta.lore ?: return lore(*lore)
        currentLore.addAll(lore)
        lore(currentLore)
    }

    fun appendLore(vararg lore: Component) = appendLore(lore.toList())
    fun appendLore(lore: List<Component>) = componentSupport.addLore(meta, fixLoreColors(lore))

    @Deprecated(message = "Use #appendLongLore")
    fun addLongLore(lore: String, lineStart: String = "§f", limit: Int = 45) {
        val currentLore: MutableList<String> = meta.lore ?: mutableListOf()
        val lines = lore.split(" ")
        var currentLine = ""
        for (line in lines) {
            if (currentLine.length + line.length > limit) {
                currentLore.add(lineStart + currentLine)
                currentLine = ""
            }
            currentLine += "$line "
        }
        currentLore.add(lineStart + currentLine.trim())
        lore(currentLore)
    }

    // ToDo this may break components when splitting tags, so this should only be used on components without any special stuff
    fun appendLongLore(lore: Component, lineStart: Component = Component.empty().color(NamedTextColor.WHITE), limit: Int = 45) {
        val miniMessage = VanillaEnhancements.getPlugin().miniMessage()
        val string = miniMessage.serialize(lore)
        val lines = string.split(" ")
        var currentLine = ""
        for (line in lines) {
            if (currentLine.length + line.length > limit) {
                appendLore(lineStart.append(miniMessage.deserialize(currentLine)))
                currentLine = ""
            }
            currentLine += "$line "
        }
    }

    fun enchantment(enchantment: Enchantment, level: Int = 1) {
        meta.addEnchant(enchantment, level, true)
    }

    fun itemFlag(vararg flags: ItemFlag) {
        meta.addItemFlags(*flags)
    }

    fun customModelData(data: Int) {
        meta.setCustomModelData(data)
    }

    fun removeEnchantment(enchantment: Enchantment) {
        meta.removeEnchant(enchantment)
    }

    fun removeItemFlag(vararg flags: ItemFlag) {
        meta.removeItemFlags(*flags)
    }

    @Deprecated(message = "use #removeLoreAtIndex")
    fun removeLoreLine(line: Int) {
        val currentLore = meta.lore ?: return
        currentLore.removeAt(line)
        lore(currentLore)
    }

    fun removeLoreAtIndex(index: Int) {
        val currentLore = getLore().toMutableList()
        currentLore.removeAt(index)
        setLore(currentLore)
    }

    fun getLore(): List<Component> {
        return componentSupport.getLore(meta)
    }

    fun attributeModifier(attribute: Attribute, modifier: AttributeModifier) {
        meta.addAttributeModifier(attribute, modifier)
    }

    fun stringData(plugin: JavaPlugin, key: String, value: String) {
        meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.STRING, value)
    }

    fun intData(plugin: JavaPlugin, key: String, value: Int) {
        meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.INTEGER, value)
    }

    fun doubleData(plugin: JavaPlugin, key: String, value: Double) {
        meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.DOUBLE, value)
    }

    fun booleanData(plugin: JavaPlugin, key: String, value: Boolean) {
        meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.BYTE, if (value) 1 else 0)
    }

    fun build(): ItemStack {
        stack.itemMeta = meta
        return stack
    }

}