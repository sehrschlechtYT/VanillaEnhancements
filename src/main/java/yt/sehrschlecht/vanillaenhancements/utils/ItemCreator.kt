package yt.sehrschlecht.vanillaenhancements.utils

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

/**
 * ItemBuilder using Kotlin DSLs
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ItemCreator {
    private val stack: ItemStack
    private val meta: ItemMeta

    constructor(stack: ItemStack, init: ItemCreator.() -> Unit) {
        this.stack = stack;
        this.meta = stack.itemMeta ?: throw IllegalArgumentException("ItemStack has no ItemMeta!")
        init(this)
    }

    constructor(material: Material, init: ItemCreator.() -> Unit) {
        this.stack = ItemStack(material);
        this.meta = stack.itemMeta ?: throw IllegalArgumentException("ItemStack has no ItemMeta!")
        init(this)
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
        meta.owner = owner
    }

    fun skullOwner(owner: OfflinePlayer) {
        if (meta !is SkullMeta) throw IllegalArgumentException("ItemMeta is not a SkullMeta!")
        meta.owningPlayer = owner
    }

    fun displayName(name: String) {
        meta.setDisplayName(name)
    }

    fun lore(vararg lore: String) {
        meta.lore = lore.toList()
    }

    fun lore(lore: List<String>) {
        meta.lore = lore
    }

    fun addLore(vararg lore: String) {
        val currentLore = meta.lore ?: return lore(*lore)
        currentLore.addAll(lore)
        lore(currentLore)
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

    fun removeLoreLine(line: Int) {
        val currentLore = meta.lore ?: return
        currentLore.removeAt(line)
        lore(currentLore)
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