package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleListMenu(private val plugin: VanillaEnhancements, private val tag: ModuleTag) : InventoryProvider {
    companion object {
        fun getInventory(plugin: VanillaEnhancements, tag: ModuleTag): SmartInventory = SmartInventory.builder()
            .id("moduleList")
            .provider(ModuleListMenu(plugin, tag))
            .size(3, 9)
            .title("§lVE - Modules for ${tag.displayName}")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fill(null)
        val modules = plugin.moduleRegistry.getModulesByTag(tag)
        modules.forEach { // ToDo: Add pagination
            contents.add(ClickableItem.of(it.buildIcon().apply {
                displayName("§l${getDisplayName()}")
                it.description?.let { desc -> addLore(desc) }
                addLore("Category: ${it.category.displayName}")
            }.build()) { _ -> player.sendMessage("You clicked on module ${it.name}!") })
        }
        contents.addBackButton { _ -> ModuleTagsMenu.getInventory(plugin) }
    }

    override fun update(player: Player, contents: InventoryContents) {
        val state = contents.property("state", 0)
        contents.setProperty("state", state + 1)

        if (state % 20 == 0) {
            init(player, contents)
        }
    }
}