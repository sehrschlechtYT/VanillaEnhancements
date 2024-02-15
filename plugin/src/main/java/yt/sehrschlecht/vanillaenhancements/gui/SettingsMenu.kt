package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground

class SettingsMenu(private val plugin: VanillaEnhancements) : RecurrentInventoryInitializer(tickInterval = 20) {
    companion object {
        fun getInventory(plugin: VanillaEnhancements): SmartInventory = SmartInventory.builder()
            .id("settings")
            .provider(SettingsMenu(plugin))
            .size(3, 9)
            .title("§lVE Settings")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBackground()

        contents.set(1, 4, ClickableItem.of(
            ItemCreator(Material.BARRIER) {
                displayName("§c§lDisable all modules")
                addLongLore("Disables every single module that is currently active. This cannot be undone.")
            }.build()
        ) {
            plugin.moduleRegistry.enabledModules.forEach { it.toggle() }
            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
        })

        contents.addBackButton { _ -> MainMenu.getInventory(plugin) }
    }
}