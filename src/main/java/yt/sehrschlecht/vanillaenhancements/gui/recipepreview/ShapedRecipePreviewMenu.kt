package yt.sehrschlecht.vanillaenhancements.gui.recipepreview

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.SlotPos
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ShapedRecipe
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.VERecipe
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils

class ShapedRecipePreviewMenu(plugin: VanillaEnhancements, recipe: VERecipe, origin: SmartInventory) :
    AbstractRecipePreviewMenu(
        plugin,
        recipe,
        origin,
        ClickableItem.empty(ItemCreator(Material.CRAFTING_TABLE) {
            displayName("§f§l§oUse this recipe in a crafting table.")
            addLore("§7§oThis is a shaped recipe.")
        }.build()),
        resultPos = SlotPos.of(2, 7),
        arrowPos = SlotPos.of(2, 5)
    ) {

    companion object {
        fun getInventory(plugin: VanillaEnhancements, recipe: VERecipe, origin: SmartInventory): SmartInventory =
            SmartInventory.builder()
                .id("shapedRecipePreview")
                .provider(ShapedRecipePreviewMenu(plugin, recipe, origin))
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
        val shapedRecipe = recipe.recipe as ShapedRecipe
        val ingredientMap = shapedRecipe.choiceMap
        val shape = shapedRecipe.shape
        shape.forEachIndexed { rowIndex, row ->
            row.toCharArray().forEachIndexed { column, char ->
                val pos = SlotPos.of(rowIndex + 1, column + 1)
                val recipeChoice = ingredientMap[char]
                setIngredient(contents, pos, recipeChoice)
            }
        }
    }
}