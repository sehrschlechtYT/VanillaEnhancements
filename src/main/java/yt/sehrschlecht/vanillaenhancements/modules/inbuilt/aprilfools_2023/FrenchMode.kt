package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import yt.sehrschlecht.vanillaenhancements.items.VEItem
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.*
import yt.sehrschlecht.vanillaenhancements.modules.CustomItemModule
import yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023.items.LaBaguette
import yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023.items.LeTricolore
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class FrenchMode : CustomItemModule(
    "Adds the joke items \"La Baguette\" and \"Le Tricolore\" from the april fools snapshot 2023.",
    "1.0"
) {
    private val laBaguette = LaBaguette()
    private val leTricolore = LeTricolore()
    // ToDo add config option for each item to enable/disable it

    override fun getItems(): List<VEItem> {
        return listOf(laBaguette, leTricolore)
    }

    override fun getKey(): String {
        return "french_mode"
    }

    override fun registerRecipes() {
        registerLaBaguetteRecipe()
        registerLeTricoloreRecipe()
    }

    private fun registerLaBaguetteRecipe() {
        val recipe = ShapedRecipe(moduleKey, laBaguette.createItem().build())
        recipe.shape("  S", " S ", "S  ")
        recipe.setIngredient('S', Material.BREAD)
        addRecipe(
            NamespacedKey(plugin, "la_baguette"),
            recipe,
            Material.BREAD
        )
    }

    private fun registerLeTricoloreRecipe() {
        val recipe = ShapedRecipe(moduleKey, leTricolore.createItem().build())
        recipe.shape("BWR", "BWR", "BWR")
        recipe.setIngredient('B', Material.BLUE_WOOL)
        recipe.setIngredient('W', Material.WHITE_WOOL)
        recipe.setIngredient('R', Material.RED_WOOL)
        addRecipe(
            NamespacedKey(plugin, "le_tricolore"),
            recipe,
            Material.WHITE_WOOL
        )
    }

}