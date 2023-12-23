package yt.sehrschlecht.vanillaenhancements.gui.recipepreview

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.SlotPos
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
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
        }.build())
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
        // ingredients outline
        contents.fillRect(SlotPos.of(0, 0), SlotPos.of(4, 4), whiteGlassPane)
        // fill ingredients 3x3 with air
        contents.fillRect(SlotPos.of(1, 1), SlotPos.of(3, 3), ClickableItem.empty(ItemStack(Material.AIR)))
        // result outline
        contents.fillRect(SlotPos.of(1, 6), SlotPos.of(3, 8), whiteGlassPane)
    }

    override fun addArrow(player: Player, contents: InventoryContents) {
        contents.set(2, 5, ClickableItem.empty(ItemCreator(Material.ARROW) {
            displayName("§f§lCrafting in crafting table")
            addLore("§f<-- Ingredients")
            addLore("§fOutput -->")
        }.build()))
    }

    override fun fillIngredients(player: Player, contents: InventoryContents) {
        val shapedRecipe = recipe.recipe as ShapedRecipe
        val ingredientMap = shapedRecipe.ingredientMap
        val shape = shapedRecipe.shape
        shape.forEachIndexed { rowIndex, row ->
            row.toCharArray().forEachIndexed { column, char ->
                contents.set(
                    rowIndex + 1, column + 1, ClickableItem.empty(
                        ingredientMap[char] ?: ItemStack(Material.AIR)
                    )
                )
            }
        }
    }
}