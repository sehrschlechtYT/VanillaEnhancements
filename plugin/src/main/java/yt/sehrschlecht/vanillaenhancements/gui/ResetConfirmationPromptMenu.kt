package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.toEmptyClickableItem

class ResetConfirmationPromptMenu(private val plugin: VanillaEnhancements, private val answer: Boolean.() -> Unit, private val origin: SmartInventory) : InventoryProvider {
    companion object {
        fun getInventory(plugin: VanillaEnhancements, answer: Boolean.() -> Unit, origin: SmartInventory, title: String): SmartInventory = SmartInventory.builder()
            .id("resetConfirmationPrompt")
            .provider(ResetConfirmationPromptMenu(plugin, answer, origin))
            .size(3, 9)
            .title("§c§l$title")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f)

        contents.fillBorders(ItemCreator(Material.RED_STAINED_GLASS_PANE){ displayName("§0") }.build().toEmptyClickableItem())

        contents.set(1, 4, ClickableItem.empty(ItemCreator(Material.BARRIER) {
            displayName("§c§lConfirm action")
            addLore("§fClick the §aGREEN WOOL §fto §aCONFIRM §fthe action.")
            addLore("§fClick the §cRED WOOL §fto §cCANCEL §fthe action.")
        }.build()))

        contents.set(1, 2, ClickableItem.of(ItemCreator(Material.LIME_WOOL) {
            displayName("§a§lCONFIRM the action")
        }.build()) {
            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
            answer(true)
            origin.open(player)
        })
        contents.set(1, 6, ClickableItem.of(ItemCreator(Material.RED_WOOL) {
            displayName("§c§lCANCEL the action")
        }.build()) {
            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f)
            answer(false)
            origin.open(player)
        })
    }
}