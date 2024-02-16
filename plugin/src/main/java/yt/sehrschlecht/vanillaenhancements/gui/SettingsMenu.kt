package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import org.bukkit.Material
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.messages.Message
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.asComponent
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground
import yt.sehrschlecht.vanillaenhancements.utils.VESound

class SettingsMenu(private val plugin: VanillaEnhancements, private val origin: SmartInventory?) : RecurrentInventoryInitializer(tickInterval = 20) {
    companion object {
        fun getInventory(plugin: VanillaEnhancements, origin: SmartInventory? = null): SmartInventory = SmartInventory.builder()
            .id("settings")
            .provider(SettingsMenu(plugin, origin))
            .size(3, 9)
            .title("§lVE Settings")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBackground()

        contents.set(1, 4, ClickableItem.of(
            ItemCreator(Material.LEVER) {
                displayName(Message.MENU_SETTINGS_DISABLE_ALL_DISPLAYNAME, plugin)
                appendLongLore(Message.MENU_SETTINGS_DISABLE_ALL_LORE.asComponent(plugin))
            }.build()
        ) {
            plugin.moduleRegistry.enabledModules.toList().forEach { it.toggle() }
            VESound.SUCCESS.play(player)
        })

        contents.addBackButton { origin ?: MainMenu.getInventory(plugin) }
    }
}