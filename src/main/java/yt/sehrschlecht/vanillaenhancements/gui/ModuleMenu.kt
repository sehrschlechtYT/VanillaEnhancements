package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.config.Config
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption
import yt.sehrschlecht.vanillaenhancements.modules.*
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
    RecurrentInventoryInitializer(5) {

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
            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, if (module.isEnabled) 0f else 2f)
            module.toggle()
        })
        contents.set(1, 4, ClickableItem.of(
            module.buildIcon().apply {
                displayName("§f§l${getDisplayName().removeColorCodes()}")
                module.description?.let { desc -> addLongLore(desc, lineStart = "§f§o") }
                addLore("§fCategory: ${module.category.displayName}")
                if (module.category == ModuleCategory.EXTERNAL) {
                    addLore("§fProvided by §o${module.plugin.name}")
                }
            }.build()
        ) { _ -> })

        if (module is RecipeModule) {
            contents.set(1, 7, ClickableItem.of(
                ItemCreator(Material.CRAFTING_TABLE) {
                    displayName("§f§lCrafting Recipes")
                    lore("§fClick to view the crafting recipes of this module!")
                }.build()
            ) { _ -> ModuleRecipesMenu.getInventory(plugin, module, sourceTag).open(player) })
        }

        if (module is CustomItemModule) {
            contents.set(1, 1, ClickableItem.of(
                ItemCreator(Material.CHEST) {
                    displayName("§f§lCustom Items")
                    lore("§fClick to view the custom items of this module!")
                }.build()
            ) { _ -> ModuleCustomItemsMenu.getInventory(plugin, module, sourceTag).open(player) })
        }

        val options: MutableList<ConfigOption<*>> = Config.getInstance().getModuleOptions(module)
        options.removeIf { option -> option.key == "enabled" }
        val items = options.map { option ->
            option.buildClickableItem(
                ItemCreator(Material.PAPER) {
                    displayName("§f§l${ModuleUtils.beautifyLowerCamelCase(option.key)}")
                    addLongLore("§fDescription: §f§o${option.description}", lineStart = "§f§o")
                    addLongLore("§fCurrent value: ${option.valueToDisplayString()}")
                    addLore("")
                },
                getInventory(plugin, module, sourceTag)
            )
        }

        contents.set(4, 0, ClickableItem.of(
            ItemCreator(Material.BARRIER) {
                displayName("§c§lReset module settings")
                addLongLore("Warning: This will reset all config settings for this module!", lineStart = "§c")
            }.build()
        ){
            ResetConfirmationPromptMenu.getInventory(plugin, { if (this) module.resetSettings() }, contents.inventory(), "Reset settings of module").open(player)
        })

        contents.paginateItems(items, row = 3, player, noneItem = {
            displayName("§c§lNo config options")
            addLore("§cThere are no configurable options for this module!")
        }, inventoryGetter = { getInventory(plugin, module, sourceTag) })

        contents.addBackButton { _ -> ModuleListMenu.getInventory(plugin, sourceTag) }
    }

}