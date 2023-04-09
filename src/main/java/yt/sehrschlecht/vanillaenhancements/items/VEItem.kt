package yt.sehrschlecht.vanillaenhancements.items

import org.bukkit.Material
import yt.sehrschlecht.schlechteutils.items.ItemBuilder
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.Display
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.Texture

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
abstract class VEItem(
    val vanillaItem: Material,
    val texture: Texture,
    val modelDisplay: Display?,
    val customModelData: Int = VanillaEnhancements.getPlugin().resourcePackManager.getNextCustomModelData(vanillaItem),
    val displayName: String
) {

    open fun createItemBuilder(): ItemBuilder {
        return ItemBuilder(vanillaItem).setCustomModelData(customModelData).setDisplayName("§r" + displayName)
    }

    // ToDo add open functions for events like interact, craft, drop, inv click etc.
    // ToDo support item attributes

}