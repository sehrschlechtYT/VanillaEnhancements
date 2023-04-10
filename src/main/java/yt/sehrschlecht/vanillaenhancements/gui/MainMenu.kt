package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.Material
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class MainMenu(private val plugin: VanillaEnhancements) : InventoryProvider {

    companion object {
        fun getInventory(plugin: VanillaEnhancements): SmartInventory = SmartInventory.builder()
            .id("mainMenu")
            .provider(MainMenu(plugin))
            .size(3, 9)
            .title("§lVanillaEnhancements")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBackground()
        contents.set(1, 4, ClickableItem.of(
            ItemCreator(Material.COMMAND_BLOCK_MINECART) {
                displayName("§f§lManage Modules")
            }.build()
        ) { _ -> ModuleTagsMenu.getInventory(plugin).open(player) })
        contents.addBackButton()
    }

    override fun update(player: Player, contents: InventoryContents) {

    }

}