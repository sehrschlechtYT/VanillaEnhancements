package yt.sehrschlecht.vanillaenhancements.modules

import org.bukkit.Material
import yt.sehrschlecht.schlechteutils.items.ItemBuilder
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.Display
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.ResourcePackBuilder
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.Texture

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
abstract class CustomItemModule(description: String?, since: String?) : RecipeModule(description, since), CustomTextureProvider {

    abstract fun vanillaItem(): Material
    abstract fun texture(): Texture
    open fun modelDisplay(): Display? {
        return null
    }
    abstract fun customModelData(): Int
    abstract fun displayName(): String // ToDo replace these methods with properties

    open fun createItemBuilder(): ItemBuilder {
        return ItemBuilder(vanillaItem()).setCustomModelData(customModelData()).setDisplayName("§r" + displayName())
    }

    override fun createResourcePack(): ResourcePackBuilder {
        return packBuilder {
            addCustomModelData(vanillaItem(), customModelData(), texture(), modelDisplay())
        }
    }

    // ToDo add open methods for events like interact, craft, drop, inv click etc.
    // ToDo support item attributes

}