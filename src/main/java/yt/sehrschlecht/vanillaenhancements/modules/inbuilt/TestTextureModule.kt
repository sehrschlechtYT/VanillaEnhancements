package yt.sehrschlecht.vanillaenhancements.modules.inbuilt

import org.bukkit.Material
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.ResourcePackBuilder
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.Texture
import yt.sehrschlecht.vanillaenhancements.modules.CustomTextureModule

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class TestTextureModule : CustomTextureModule() {

    override fun createResourcePack(): ResourcePackBuilder {
        val image = readImageFromJar("/textures/la_baguette.png", VanillaEnhancements::class.java)
        return packBuilder {
            addCustomModelData(Material.WOODEN_SWORD, 123, Texture("la_baguette", image))
        }
    }

    override fun getKey(): String {
        return "test_texture_module"
    }

}