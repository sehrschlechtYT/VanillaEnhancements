package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.items.VEItem
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground
import kotlin.math.max

class CustomItemMenu(private val plugin: VanillaEnhancements, private val item: VEItem, private val origin: SmartInventory) : InventoryProvider {
    companion object {
        fun getInventory(plugin: VanillaEnhancements, item: VEItem, origin: SmartInventory): SmartInventory = SmartInventory.builder()
            .id("customItemInfo")
            .provider(CustomItemMenu(plugin, item, origin))
            .size(3, 9)
            .title("§lItem info: ${item.displayName}")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBackground()
        contents.set(0, 4, ClickableItem.empty(item.createItem {
            addLore("§f§oBased off of ${ModuleUtils.getNameFromKey(item.vanillaItem.name)}")
        }.build()) )

        contents.set(2, 1, ClickableItem.of(
            ItemCreator(Material.CHEST) {
                displayName("§f§lGet item")
                lore("§fClick to receive the item.")
            }.build()
        ) { _ -> player.inventory.addItem(item.createItem().build()) })

        contents.set(2, 3, ClickableItem.of(
            ItemCreator(Material.HOPPER) {
                displayName("§f§lRemove item from your inventory")
                lore("§fClick to remove the item from your inventory.")
            }.build()
        ) { _ -> player.inventory.removeItem(*player.inventory.filterNotNull().filter { itemStack -> item.isItem(itemStack) }.toTypedArray()) })

        contents.set(2, 5, ClickableItem.of(
            ItemCreator(Material.HOPPER) {
                displayName("§f§lRemove item §c§lfrom all players")
                lore("§fClick to remove this item from all players.")
                lore("§cWarning: This can not be undone!")
            }.build()
        ) { _ -> plugin.server.onlinePlayers.forEach { p -> p.inventory.removeItem(*p.inventory.filterNotNull().filter { itemStack -> item.isItem(itemStack) }.toTypedArray()) } })

        val playersWithItem = Bukkit.getOnlinePlayers().filter { p -> p.inventory.contents.filterNotNull().any { itemStack -> item.isItem(itemStack) } }
        val itemCreator = ItemCreator(Material.PLAYER_HEAD) {
            displayName("§f§lPlayers with item:")
            amount(max(1, playersWithItem.size))
        }
        if (playersWithItem.isEmpty()) {
            itemCreator.addLore("§cThere are no players who have this item.")
        } else {
            itemCreator.addLore(*playersWithItem.map { p -> "§7- §f${p.name}" }.toTypedArray())
        }
        contents.set(2, 7, ClickableItem.empty(itemCreator.build()))

        contents.addBackButton{ origin }
    }

}