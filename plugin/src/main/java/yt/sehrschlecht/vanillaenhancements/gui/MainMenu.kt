package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.messages.Message
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.asComponent
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
        contents.set(1, 3, ClickableItem.of(
            ItemCreator(Material.COMMAND_BLOCK_MINECART) {
                displayName(Message.MENU_MAIN_MANAGE_MODULES_DISPLAYNAME, plugin)
                appendLongLore(Message.MENU_MAIN_MANAGE_MODULES_LORE.asComponent(plugin), lineStart = NamedTextColor.GRAY)
            }.build()
        ) { _ -> ModuleTagsMenu.getInventory(plugin, contents.inventory()).open(player) })
        contents.set(1, 5, ClickableItem.of(
            ItemCreator(Material.REPEATER) {
                displayName(Message.MENU_MAIN_SETTINGS_DISPLAYNAME, plugin)
                appendLongLore(Message.MENU_MAIN_SETTINGS_LORE.asComponent(plugin), lineStart = NamedTextColor.GRAY)
            }.build()
        ) { _ -> SettingsMenu.getInventory(plugin, contents.inventory()).open(player) })
        contents.addBackButton()
    }

    override fun update(player: Player, contents: InventoryContents) {

    }

}