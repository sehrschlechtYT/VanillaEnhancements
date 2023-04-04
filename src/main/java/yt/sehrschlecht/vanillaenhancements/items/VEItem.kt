package yt.sehrschlecht.vanillaenhancements.items

import org.bukkit.inventory.ItemStack
import java.awt.image.BufferedImage

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
abstract class VEItem {
    abstract fun create(): ItemStack
    abstract fun getTexture(): BufferedImage
}