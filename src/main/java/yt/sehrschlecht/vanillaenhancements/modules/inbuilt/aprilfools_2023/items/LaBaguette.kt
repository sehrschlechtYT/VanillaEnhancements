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
class LaBaguette : VEItem(
    vanillaItem = Material.WOODEN_SWORD,
    texture = Texture("la_baguette.png", TextureUtils.readImageFromJar("/textures/la_baguette.png", VanillaEnhancements::class.java)),
    displayName = "La Baguette",
    modelDisplay = Display( // todo support inputting json for display (to copy from vanilla)
        rotations = mapOf(
            DisplayType.THIRDPERSON_RIGHTHAND to Rotation(0, -90, 55),
            DisplayType.THIRDPERSON_LEFTHAND to Rotation(0, -90, -55),
            DisplayType.FIRSTPERSON_RIGHTHAND to Rotation(0, -90, 25),
            DisplayType.FIRSTPERSON_LEFTHAND to Rotation(0, 90, -25),
            DisplayType.GUI to Rotation.zero(),
            DisplayType.FIXED to Rotation.zero(),
            DisplayType.GROUND to Rotation.zero() // ToDo these zero rotations may be unnecessary; check whether the default values are zero
        ),
        translations = mapOf(
            DisplayType.THIRDPERSON_RIGHTHAND to Translation(0.0, 4.0, 0.5),
            DisplayType.THIRDPERSON_LEFTHAND to Translation(0.0, 4.0, 0.5),
            DisplayType.FIRSTPERSON_RIGHTHAND to Translation(1.13, 3.2, 1.13),
            DisplayType.FIRSTPERSON_LEFTHAND to Translation(1.13, 3.2, 1.13),
            DisplayType.GUI to Translation(3.0, 4.0, 0.0),
            DisplayType.FIXED to Translation.zero(),
            DisplayType.GROUND to Translation(4.0, 4.0, 2.0)
        ),
        scales = mapOf(
            DisplayType.THIRDPERSON_RIGHTHAND to Scale(1.7, 1.7, 1.7),
            DisplayType.THIRDPERSON_LEFTHAND to Scale(1.7, 1.7, 1.7),
            DisplayType.FIRSTPERSON_RIGHTHAND to Scale(1.36, 1.36, 1.36),
            DisplayType.FIRSTPERSON_LEFTHAND to Scale(1.36, 1.36, 1.36),
            DisplayType.GUI to Scale(2.0, 2.0, 1.0),
            DisplayType.FIXED to Scale(2.0, 2.0, 1.0),
            DisplayType.GROUND to Scale(1.0, 1.0, 1.0)
        )
    )
)