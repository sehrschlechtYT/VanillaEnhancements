package yt.sehrschlecht.vanillaenhancements.gui.recipepreview

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.StonecuttingRecipe
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.VERecipe
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils

class StonecuttingRecipePreviewMenu(plugin: VanillaEnhancements, recipe: VERecipe, origin: SmartInventory) :
    AbstractRecipePreviewMenu(
        plugin,
        recipe,
        origin,
        ClickableItem.empty(ItemCreator(Material.STONECUTTER) {
            displayName("§f§l§oUse this recipe in a stonecutter.")
        }.build())
    ) {

    companion object {
        fun getInventory(plugin: VanillaEnhancements, recipe: VERecipe, origin: SmartInventory): SmartInventory =
            SmartInventory.builder()
                .id("stonecuttingRecipePreview")
                .provider(StonecuttingRecipePreviewMenu(plugin, recipe, origin))
                .size(5, 9)
                .title("§lRecipe preview: ${ModuleUtils.getNameFromKey(recipe.key)}")
                .manager(plugin.inventoryManager)
                .build()
    }

    override fun fillOutlines(player: Player, contents: InventoryContents) {
        fillSingleInputOutlines(contents)
    }

    override fun buildArrow(player: Player): ItemCreator.() -> Unit {
        return {
            displayName("§f§lCutting in stonecutter")
            addLore("§f<-- Input")
            addLore("§fOutput -->")
        }
    }

    override fun fillIngredients(player: Player, contents: InventoryContents) {
        setSingleInput(contents, (recipe.recipe as StonecuttingRecipe).inputChoice)
    }
}