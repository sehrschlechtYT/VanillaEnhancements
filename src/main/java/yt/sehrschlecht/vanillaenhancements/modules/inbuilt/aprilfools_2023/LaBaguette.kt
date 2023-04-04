package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.Material
import org.bukkit.inventory.ShapedRecipe
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.*
import yt.sehrschlecht.vanillaenhancements.modules.CustomItemModule
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class LaBaguette : CustomItemModule(
    "Adds the joke weapon \"La Baguette\" from the april fools snapshot 2023.",
    "1.0"
) { // ToDo make custom items objects and not modules and make this a FrenchMode module which also contains the tricolore
    private val image = readImageFromJar("/textures/la_baguette.png", VanillaEnhancements::class.java)

    override fun vanillaItem(): Material {
        return Material.WOODEN_SWORD
    }

    override fun texture(): Texture {
        return Texture("la_baguette.png", image)
    }

    override fun customModelData(): Int {
        return 10
    }

    override fun displayName(): String {
        return "La Baguette"
    }

    override fun modelDisplay(): Display {
        return Display( // todo support inputting json for display (to copy from vanilla)
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
    }

    override fun getKey(): String {
        return "la_baguette"
    }

    override fun registerRecipes() {
        val recipe = ShapedRecipe(moduleKey, createItemBuilder().build())
        recipe.shape("  S", " S ", "S  ")
        recipe.setIngredient('S', Material.BREAD)
        addRecipe(
            moduleKey,
            recipe,
            Material.BREAD
        )
    }

}