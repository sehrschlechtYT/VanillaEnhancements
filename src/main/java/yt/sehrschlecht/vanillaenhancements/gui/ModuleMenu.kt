package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.SlotIterator
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
            .title("§lVE - Module \"${module.name}\"")
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

        val options: List<ConfigOption<*>> = Config.getInstance().getModuleOptions(module)
        val items = options.map { option ->
            ClickableItem.of(
                ItemCreator(Material.PAPER) {
                    displayName("§f§l${ModuleUtils.getNameFromKey(option.key)}}")
                    addLongLore("§fDescription: ${option.description}", lineStart = "§f§o")
                    addLongLore("§fCurrent value: ${option.valueToDisplayString()}")
                    lore("§fClick to change!")
                }.build()
            ) { player.sendMessage("You clicked on option ${option.key}. TODO make option changeable") }
        }
        if (options.isEmpty()) {
            contents.set(3, 4, ClickableItem.empty(
                ItemCreator(Material.BARRIER) {
                    displayName("§c§lNo config options available!")
                }.build()
            ))
        } else if (options.size <= 5) {
            getSlots(items).forEach { (slot, item) -> contents.set(3, slot, item) }
        } else {
            val pagination = contents.pagination()
            pagination.setItems(*items.toTypedArray())
            pagination.setItemsPerPage(5)
            pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 3, 2))

            if (!pagination.isLast) {
                contents.set(3, 8, ClickableItem.of(
                    ItemCreator(Material.PLAYER_HEAD) {
                        displayName("§fNext Page")
                    }.build()
                ) { _ -> getInventory(plugin, module, sourceTag).open(player, pagination.next().page) })
            }

            if (!pagination.isFirst) {
                contents.set(3, 0, ClickableItem.of(
                    ItemCreator(Material.PLAYER_HEAD) {
                        displayName("§fPrevious Page")
                    }.build()
                ) { _ -> getInventory(plugin, module, sourceTag).open(player, pagination.previous().page) })
            }
        }

        contents.addBackButton { _ -> ModuleListMenu.getInventory(plugin, sourceTag) }
    }

    private fun getSlots(items: List<ClickableItem>): Map<Int, ClickableItem> {
        return when (items.size) {
            1 -> mapOf(4 to items[0])
            2 -> mapOf(3 to items[0], 5 to items[1])
            3 -> mapOf(3 to items[0], 4 to items[1], 5 to items[2])
            4 -> mapOf(2 to items[0], 3 to items[1], 5 to items[2], 6 to items[3])
            5 -> mapOf(2 to items[0], 3 to items[1], 4 to items[2], 5 to items[3], 6 to items[4])
            else -> mapOf()
        }
    }

}