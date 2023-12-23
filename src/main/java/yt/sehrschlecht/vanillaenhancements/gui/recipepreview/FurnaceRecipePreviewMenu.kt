package yt.sehrschlecht.vanillaenhancements.gui.recipepreview

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.SlotPos
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.FurnaceRecipe
import org.bukkit.inventory.ItemStack
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.VERecipe
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils

class FurnaceRecipePreviewMenu(plugin: VanillaEnhancements, recipe: VERecipe, origin: SmartInventory) :
    AbstractRecipePreviewMenu(
        plugin,
        recipe,
        origin,
        ClickableItem.empty(ItemCreator(Material.FURNACE) {
            displayName("§f§l§oUse this recipe in a furnace.")
        }.build())
    ) {

    companion object {
        fun getInventory(plugin: VanillaEnhancements, recipe: VERecipe, origin: SmartInventory): SmartInventory =
            SmartInventory.builder()
                .id("furnaceRecipePreview")
                .provider(FurnaceRecipePreviewMenu(plugin, recipe, origin))
                .size(5, 9)
                .title("§lRecipe preview: ${ModuleUtils.getNameFromKey(recipe.key)}")
                .manager(plugin.inventoryManager)
                .build()
    }

    override fun fillOutlines(player: Player, contents: InventoryContents) {
        // input outline
        contents.fillRect(SlotPos.of(1, 1), SlotPos.of(3, 3), whiteGlassPane)
        // fill input slot with air
        contents.set(2, 2, ClickableItem.empty(ItemStack(Material.AIR)))
        // result outline
        contents.fillRect(SlotPos.of(1, 6), SlotPos.of(3, 8), whiteGlassPane)
    }

    override fun addArrow(player: Player, contents: InventoryContents) {
        contents.set(2, 5, ClickableItem.empty(ItemCreator(Material.ARROW) {
            displayName("§f§lSmelting in furnace")
            addLore("§f<-- Input")
            addLore("§fOutput -->")
        }.build()))
    }

    override fun fillIngredients(player: Player, contents: InventoryContents) {
        val furnaceRecipe = recipe.recipe as FurnaceRecipe
        val input = furnaceRecipe.input
        contents.set(2, 2, ClickableItem.empty(input))
    }
}