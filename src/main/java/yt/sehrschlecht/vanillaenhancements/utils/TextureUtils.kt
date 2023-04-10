package yt.sehrschlecht.vanillaenhancements.utils

import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class TextureUtils {
    companion object {
        fun readImageFromJar(path: String, pluginClass: Class<*>): BufferedImage {
            val imageResource = pluginClass.getResourceAsStream(path);

            Debug.RESOURCE_PACKS.log("Read image resource from $path")

            return ImageIO.read(imageResource)
        }
    }
}