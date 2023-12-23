package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import fr.minuskube.inv.content.SlotPos
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.FurnaceRecipe
import org.bukkit.inventory.ItemStack
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.VERecipe
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground

class FurnaceRecipePreviewMenu(private val plugin: VanillaEnhancements, private val recipe: VERecipe, private val origin: SmartInventory) : InventoryProvider {
    companion object {
        fun getInventory(plugin: VanillaEnhancements, recipe: VERecipe, origin: SmartInventory): SmartInventory = SmartInventory.builder()
            .id("furnaceRecipePreview")
            .provider(FurnaceRecipePreviewMenu(plugin, recipe, origin))
            .size(5, 9)
            .title("§lRecipe preview: ${ModuleUtils.getNameFromKey(recipe.key)}")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBackground()

        // outlines
        val whiteGlassPane = ClickableItem.empty(ItemCreator(Material.WHITE_STAINED_GLASS_PANE) {
            displayName("§0")
        }.build())
        // input outline
        contents.fillRect(SlotPos.of(1, 1), SlotPos.of(3, 3), whiteGlassPane)
        // fill input slot with air
        contents.set(2, 2, ClickableItem.empty(ItemStack(Material.AIR)))
        // result outline
        contents.fillRect(SlotPos.of(1, 6), SlotPos.of(3, 8), whiteGlassPane)

        // arrow
        contents.set(2, 5, ClickableItem.empty(ItemCreator(Material.ARROW) {
            displayName("§f§lSmelting in furnace")
            addLore("§f<-- Input")
            addLore("§fOutput -->")
        }.build() ))

        // recipe type indicator
        contents.set(0, 8, ClickableItem.empty(ItemCreator(Material.FURNACE) {
            displayName("§f§l§oUse this recipe in a furnace.")
        }.build()))

        // fill ingredients
        val furnaceRecipe = recipe.recipe as FurnaceRecipe
        val input = furnaceRecipe.input
        contents.set(2, 2, ClickableItem.empty(input))

        // set result
        val result = furnaceRecipe.result
        contents.set(2, 7, ClickableItem.empty(result))

        // back button
        contents.addBackButton { origin }
    }
}