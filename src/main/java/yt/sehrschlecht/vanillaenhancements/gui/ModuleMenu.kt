package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import org.bukkit.Material
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.config.Config
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption
import yt.sehrschlecht.vanillaenhancements.modules.CustomItemModule
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.paginateItems
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.removeColorCodes

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleMenu(private val plugin: VanillaEnhancements, private val module: VEModule, private val sourceTag: ModuleTag) :
    UpdatingInventoryProvider(5) {

    companion object {
        fun getInventory(plugin: VanillaEnhancements, module: VEModule, sourceTag: ModuleTag): SmartInventory = SmartInventory.builder()
            .id("moduleMenu")
            .provider(ModuleMenu(plugin, module, sourceTag))
            .size(5, 9)
            .title("§lVE - ${module.name}")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        val enabled = module.isEnabled
        contents.fillBorders(ClickableItem.of(
            ItemCreator(if (enabled) Material.LIME_STAINED_GLASS_PANE else Material.RED_STAINED_GLASS_PANE) {
                displayName(if(enabled) "§a§lEnabled" else "§c§lDisabled")
                lore("Click to toggle!")
            }.build()
        ) { _ ->
            module.toggle()
        })
        contents.set(1, 4, ClickableItem.of(
            module.buildIcon().apply {
                displayName("§f§l${getDisplayName().removeColorCodes()}")
                module.description?.let { desc -> addLongLore(desc, lineStart = "§f§o") }
                addLore("§fCategory: ${module.category.displayName}")
            }.build()
        ) { _ -> })

        if (module is RecipeModule) {
            contents.set(1, 7, ClickableItem.of(
                ItemCreator(Material.CRAFTING_TABLE) {
                    displayName("§f§lCrafting Recipe")
                    lore("§fClick to view the crafting recipe!")
                }.build()
            ) { _ -> ModuleRecipesMenu.getInventory(plugin, module, sourceTag).open(player) })
        }

        if (module is CustomItemModule) {
            contents.set(1, 1, ClickableItem.of(
                ItemCreator(Material.CHEST) {
                    displayName("§f§lCustom Item")
                    lore("§fClick to view the custom item!")
                }.build()
            ) { _ -> ModuleCustomItemsMenu.getInventory(plugin, module, sourceTag).open(player) })
        }

        val options: MutableList<ConfigOption<*>> = Config.getInstance().getModuleOptions(module)
        options.removeIf { option -> option.key == "enabled" }
        val items = options.map { option ->
            option.buildClickableItem(
                ItemCreator(Material.PAPER) {
                    displayName("§f§l${ModuleUtils.beautifyLowerCamelCase(option.key)}")
                    addLongLore("§fDescription: ${option.description}", lineStart = "§f§o")
                    addLongLore("§fCurrent value: ${option.valueToDisplayString()}")
                    addLore("")
                }
            )
        }

        contents.paginateItems(items, row = 3, player, noneItem = {
            displayName("§c§lNo config options")
            addLore("§cThere are no configurable options for this module!")
        }, inventoryGetter = { getInventory(plugin, module, sourceTag) })

        contents.addBackButton { _ -> ModuleListMenu.getInventory(plugin, sourceTag) }
    }

}