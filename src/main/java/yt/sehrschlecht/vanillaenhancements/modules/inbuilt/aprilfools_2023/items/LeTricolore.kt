package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023.items

import org.bukkit.Material
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.items.VEItem
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.*
import yt.sehrschlecht.vanillaenhancements.utils.TextureUtils

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class LeTricolore : VEItem(
    vanillaItem = Material.SCUTE,
    texture = Texture("le_tricolore.png", TextureUtils.readImageFromJar("/textures/le_tricolore.png", VanillaEnhancements::class.java)),
    displayName = "Le Tricolore",
    key = "le_tricolore",
    modelDisplay = Display(
        rotations = mapOf(DisplayType.THIRDPERSON_RIGHTHAND to Rotation(0, 180, 0)),
        translations = mapOf(DisplayType.THIRDPERSON_RIGHTHAND to Translation(0.0, 3.0, 1.0)),
        scales = mapOf(DisplayType.THIRDPERSON_RIGHTHAND to Scale(0.55, 0.55, 0.55))
    )
)