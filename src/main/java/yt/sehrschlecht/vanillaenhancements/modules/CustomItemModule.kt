package yt.sehrschlecht.vanillaenhancements.modules

import yt.sehrschlecht.vanillaenhancements.items.VEItem
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.ResourcePackBuilder

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
abstract class CustomItemModule(
    description: String?,
    since: String?,
    category: ModuleCategory,
    vararg tags: ModuleTag
) : RecipeModule(
    description, since, category, *tags, ModuleTag.CUSTOM_ITEMS
), CustomTextureProvider {

    abstract fun getItems(): List<VEItem>

    override fun createResourcePack(): ResourcePackBuilder {
        return packBuilder {
            for (item in getItems()) {
                addCustomModelData(item.vanillaItem, item.customModelData, item.texture, item.modelDisplay)
            }
        }
    }

}