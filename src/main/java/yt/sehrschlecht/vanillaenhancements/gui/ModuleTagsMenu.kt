package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.utils.PaginationType
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.paginateItems
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.removeColorCodes

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleTagsMenu(private val plugin: VanillaEnhancements) : RecurrentInventoryInitializer(20) {

    companion object {
        fun getInventory(plugin: VanillaEnhancements): SmartInventory = SmartInventory.builder()
            .id("moduleTags")
            .provider(ModuleTagsMenu(plugin))
            .size(3, 9)
            .title("§lVE - Module tags")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        val tags = plugin.moduleRegistry.collectTags()
        val items = tags.map {
            val modules = plugin.moduleRegistry.getModulesByTag(it)
            val total = modules.size
            val enabled = modules.count(VEModule::isEnabled)
            ClickableItem.of(it.buildIcon {
                displayName("§f§l${getDisplayName().removeColorCodes()}")
                addLore("§f$enabled/$total enabled")
            }.build()) { _ -> ModuleListMenu.getInventory(plugin, it).open(player) }
        }

        contents.paginateItems(items, player = player, paginationType = PaginationType.HORIZONTAL_7, noneItem = {
            displayName("§c§lNo modules found")
            addLongLore("This should not be the case. Please report this bug to the discord server (see SpigotMC page of the VanillaEnhancements plugin)!", lineStart = "§c")
        }, inventoryGetter = { getInventory(plugin) })

        contents.addBackButton { _ -> MainMenu.getInventory(plugin) }
        contents.fillBackground()
    }

}