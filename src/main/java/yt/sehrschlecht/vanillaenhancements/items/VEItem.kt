package yt.sehrschlecht.vanillaenhancements.items

import org.bukkit.Material
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.Display
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.Texture
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
abstract class VEItem(
    val vanillaItem: Material,
    val texture: Texture,
    val modelDisplay: Display? = null,
    val customModelData: Int = VanillaEnhancements.getPlugin().resourcePackManager.getNextCustomModelData(vanillaItem),
    val displayName: String,
    val key: String,
    val disableVanillaCrafting: Boolean = true // whether to disable the usage of this item in vanilla crafting recipes,
) {

    open fun createItem(): ItemCreator {
        return ItemCreator(vanillaItem) {
            customModelData(customModelData)
            displayName("§r$displayName")
            stringData(VanillaEnhancements.getPlugin(), VanillaEnhancements.getPlugin().itemManager.key.key, key)
        }
    }

    open fun createItem(modify: ItemCreator.() -> Unit): ItemCreator {
        return createItem().apply(modify)
    }

    // ToDo add open functions for events like interact, craft, drop, inv click etc.

}