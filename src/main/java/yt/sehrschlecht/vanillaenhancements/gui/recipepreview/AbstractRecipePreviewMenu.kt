package yt.sehrschlecht.vanillaenhancements.gui.recipepreview

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import fr.minuskube.inv.content.SlotPos
import org.bukkit.Material
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.VERecipe
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground

abstract class AbstractRecipePreviewMenu(
    protected val plugin: VanillaEnhancements,
    protected val recipe: VERecipe,
    protected val origin: SmartInventory,
    private val recipeTypeIndicator: ClickableItem,
    private val resultPos: SlotPos = SlotPos.of(2, 7),
) : InventoryProvider {
    protected val whiteGlassPane: ClickableItem = ClickableItem.empty(ItemCreator(Material.WHITE_STAINED_GLASS_PANE) {
        displayName("§0")
    }.build())

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBackground()

        postInit(player, contents)
        fillOutlines(player, contents)
        addArrow(player, contents)
        fillIngredients(player, contents)

        // set recipe type indicator
        contents.set(0, 8, recipeTypeIndicator)

        // set result
        contents.set(resultPos, ClickableItem.empty(recipe.recipe.result))

        // back button
        contents.addBackButton { origin }
    }

    private fun postInit(player: Player, contents: InventoryContents) { }
    abstract fun fillOutlines(player: Player, contents: InventoryContents)
    abstract fun addArrow(player: Player, contents: InventoryContents)
    abstract fun fillIngredients(player: Player, contents: InventoryContents)
}