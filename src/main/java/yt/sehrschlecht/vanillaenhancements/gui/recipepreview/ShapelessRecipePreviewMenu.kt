package yt.sehrschlecht.vanillaenhancements.gui.recipepreview

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.SlotPos
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ShapelessRecipe
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.VERecipe
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils

class ShapelessRecipePreviewMenu(plugin: VanillaEnhancements, recipe: VERecipe, origin: SmartInventory) :
    AbstractRecipePreviewMenu(
        plugin,
        recipe,
        origin,
        ClickableItem.empty(ItemCreator(Material.CRAFTING_TABLE) {
            displayName("§f§l§oUse this recipe in a crafting table.")
            addLore("§7§oThis is a shapeless recipe.")
        }.build()),
        resultPos = SlotPos.of(2, 7),
        arrowPos = SlotPos.of(2, 5)
    ) {

    companion object {
        fun getInventory(plugin: VanillaEnhancements, recipe: VERecipe, origin: SmartInventory): SmartInventory =
            SmartInventory.builder()
                .id("shapelessRecipePreview")
                .provider(ShapelessRecipePreviewMenu(plugin, recipe, origin))
                .size(5, 9)
                .title("§lRecipe preview: ${ModuleUtils.getNameFromKey(recipe.key)}")
                .manager(plugin.inventoryManager)
                .build()
    }

    override fun fillOutlines(player: Player, contents: InventoryContents) {
        fill3x3InputOutlines(contents)
    }

    override fun buildArrow(player: Player): ItemCreator.() -> Unit {
        return {
            displayName("§f§lCrafting in crafting table")
            addLore("§f<-- Ingredients")
            addLore("§fOutput -->")
        }
    }

    override fun fillIngredients(player: Player, contents: InventoryContents) {
        val shapelessRecipe = recipe.recipe as ShapelessRecipe
        val ingredients = shapelessRecipe.choiceList
        repeat(9) {
            val column: Int = (it % 3) + 1
            val row: Int = (it / 3) + 1
            val recipeChoice = if (it >= ingredients.size) {
                null
            } else {
                ingredients[it]
            }
            setIngredient(contents, SlotPos.of(row, column), recipeChoice)
        }
    }
}