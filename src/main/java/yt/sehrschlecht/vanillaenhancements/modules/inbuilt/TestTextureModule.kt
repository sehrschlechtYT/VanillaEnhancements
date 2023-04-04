package yt.sehrschlecht.vanillaenhancements.modules.inbuilt

import org.bukkit.Material
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.ResourcePackBuilder
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.Texture
import yt.sehrschlecht.vanillaenhancements.modules.CustomTextureModule
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class TestTextureModule : CustomTextureModule() {

    override fun createResourcePack(): ResourcePackBuilder {
        val imageResource = javaClass.getResourceAsStream("/la_baguette.png")
        val image: BufferedImage = ImageIO.read(imageResource)
        return packBuilder {
            addCustomModelData(Material.WOODEN_SWORD, 123, Texture("la_baguette", image))
        }
    }

    override fun getKey(): String {
        return "test_texture_module"
    }

}