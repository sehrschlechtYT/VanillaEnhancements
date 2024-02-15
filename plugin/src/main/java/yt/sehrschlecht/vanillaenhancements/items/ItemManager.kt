package yt.sehrschlecht.vanillaenhancements.items

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.CustomItemModule

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ItemManager(val plugin: VanillaEnhancements) {

    val items = mutableListOf<VEItem>()
    val key = NamespacedKey(plugin, "ve_custom_item")

    fun initialize() {
        plugin.moduleRegistry.registeredModules.forEach {
            if (it !is CustomItemModule) return@forEach
            items.addAll(it.getItems())
        }
    }

    fun findItem(stack: ItemStack) : VEItem? {
        val persistentDataContainer = stack.itemMeta?.persistentDataContainer ?: return null
        val key = persistentDataContainer.get(key, PersistentDataType.STRING) ?: return null
        return findItemByKey(key)
    }

    fun findItemByKey(key: String) : VEItem? {
        return items.firstOrNull { it.key.equals(key, true) }
    }

}