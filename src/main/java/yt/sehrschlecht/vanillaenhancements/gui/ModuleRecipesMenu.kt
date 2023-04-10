package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.paginateItems

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleRecipesMenu(private val plugin: VanillaEnhancements, private val module: RecipeModule, private val sourceTag: ModuleTag) : UpdatingInventoryProvider(20) {

    companion object {
        fun getInventory(plugin: VanillaEnhancements, module: RecipeModule, sourceTag: ModuleTag): SmartInventory =
            SmartInventory.builder()
                .id("moduleRecipes")
                .provider(ModuleRecipesMenu(plugin, module, sourceTag))
                .size(3, 9)
                .title("§lVE - Recipes of \"${module.name}\"")
                .manager(plugin.inventoryManager)
                .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        val recipes = plugin.recipeManager.getRecipes(module)
        val items = recipes.map {
            ClickableItem.of(ItemCreator(it.recipe.result) {
                displayName("§f§l${ModuleUtils.getNameFromKey(it.key)}")
                addLore("§fItem required for discovery: ${ModuleUtils.getNameFromKey(it.discoverItem?.name ?: "§cNone")}")
                addLore(if (it.isRegistered) "§aRegistered" else "§cNot registered")
            }.build()) { _ -> player.sendMessage("Clicked on recipe ${it.key}. TODO") }
        }

        contents.paginateItems(items, player = player, noneItem = {
            displayName("§c§lNo recipes found")
            addLore("§cThis module has no recipes")
        }, inventoryGetter = { getInventory(plugin, module, sourceTag) })

        contents.addBackButton{_ -> ModuleMenu.getInventory(plugin, module, sourceTag) }
        contents.fillBackground()
    }

}