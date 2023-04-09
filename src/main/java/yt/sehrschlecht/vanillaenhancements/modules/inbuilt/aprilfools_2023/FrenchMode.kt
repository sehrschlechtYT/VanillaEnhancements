package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import yt.sehrschlecht.vanillaenhancements.items.VEItem
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.*
import yt.sehrschlecht.vanillaenhancements.modules.CustomItemModule
import yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023.items.LaBaguette
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class FrenchMode : CustomItemModule(
    "Adds the joke weapon \"La Baguette\" from the april fools snapshot 2023.",
    "1.0"
) { // ToDo add tricolore item
    private val laBaguette = LaBaguette()
    override fun getItems(): List<VEItem> {
        return listOf(laBaguette)
    }

    override fun getKey(): String {
        return "french_mode"
    }

    override fun registerRecipes() {
        val recipe = ShapedRecipe(moduleKey, laBaguette.createItemBuilder().build())
        recipe.shape("  S", " S ", "S  ")
        recipe.setIngredient('S', Material.BREAD)
        addRecipe(
            NamespacedKey(plugin, "la_baguette"),
            recipe,
            Material.BREAD
        )
    }

}