package yt.sehrschlecht.vanillaenhancements.modules

import yt.sehrschlecht.vanillaenhancements.items.resourcepack.ResourcePackBuilder
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
// WARNING: You have to implement this in the module class!
interface CustomTextureProvider {

    fun createResourcePack() : ResourcePackBuilder

    fun packBuilder(builder: ResourcePackBuilder.() -> Unit): ResourcePackBuilder {
        val packBuilder = ResourcePackBuilder()
        builder.invoke(packBuilder)
        return packBuilder
    }

    // for pluginClass: use this::class.java in Kotlin and Class.class in Java instead of object.getClass()
    fun readImageFromJar(path: String, pluginClass: Class<*>): BufferedImage {
        val imageResource = pluginClass.getResourceAsStream(path);

        Debug.RESOURCE_PACKS.log("Read image resource from $path")

        return ImageIO.read(imageResource)
    }

}