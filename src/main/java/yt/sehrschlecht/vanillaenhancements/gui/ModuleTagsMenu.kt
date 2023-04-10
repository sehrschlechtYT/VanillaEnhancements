package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.removeColorCodes

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleTagsMenu(private val plugin: VanillaEnhancements) : UpdatingInventoryProvider(20) {

    companion object {
        fun getInventory(plugin: VanillaEnhancements): SmartInventory = SmartInventory.builder()
            .id("moduleTags")
            .provider(ModuleTagsMenu(plugin))
            .size(3, 9)
            .title("§lVE - Modules")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        val tags = plugin.moduleRegistry.collectTags()
        tags.forEach { // TODO: Add pagination
            val modules = plugin.moduleRegistry.getModulesByTag(it)
            val total = modules.size
            val enabled = modules.count(VEModule::isEnabled)
            contents.add(ClickableItem.of(it.buildIcon {
                displayName("§f§l${getDisplayName().removeColorCodes()}")
                addLore("§f$enabled/$total enabled")
            }.build()) { _ -> ModuleListMenu.getInventory(plugin, it).open(player) })
        }
        contents.addBackButton { _ -> MainMenu.getInventory(plugin) }
        contents.fillBackground()
    }

}