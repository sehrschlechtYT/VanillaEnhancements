package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleTagsMenu(private val plugin: VanillaEnhancements) : InventoryProvider {

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
        contents.fill(null)
        val tags = plugin.moduleRegistry.collectTags()
        tags.forEach { // TODO: Add pagination
            val modules = plugin.moduleRegistry.getModulesByTag(it)
            val total = modules.size
            val enabled = modules.count(VEModule::isEnabled)
            contents.add(ClickableItem.of(it.buildIcon {
                displayName("§l${getDisplayName()}")
                addLore("$enabled/$total enabled")
            }.build()) { _ -> ModuleListMenu.getInventory(plugin, it).open(player) })
        }
        contents.addBackButton { _ -> MainMenu.getInventory(plugin) }
    }

    override fun update(player: Player, contents: InventoryContents) {
        val state = contents.property("state", 0)
        contents.setProperty("state", state + 1)

        if (state % 20 == 0) {
            init(player, contents)
        }
    }

}