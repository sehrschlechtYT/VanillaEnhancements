package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.InventoryListener
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.config.options.StringOption

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ChooseStringMenu : InventoryProvider {

    companion object { // ToDo doesnt work
        fun getInventory(plugin: VanillaEnhancements, option: StringOption, origin: SmartInventory): SmartInventory = SmartInventory.builder()
            .id("chooseString")
            .type(InventoryType.ANVIL)
            .provider(ChooseStringMenu())
            .title("§lEnter a value")
            .manager(plugin.inventoryManager)
            .parent(origin)
            .listener(InventoryListener(InventoryClickEvent::class.java) { event ->
                Bukkit.broadcastMessage("Click!!")
                if (event.slot == 2) {
                    Bukkit.broadcastMessage("slot 2!!")
                    val item = event.currentItem
                    if (item != null && item.type == Material.PAPER) {
                        Bukkit.broadcastMessage("is paper & item != null!!")
                        val meta = item.itemMeta
                        if (meta != null && meta.hasDisplayName()) {
                            Bukkit.broadcastMessage("has display name and meta != null!!")
                            option.set(meta.displayName)
                            origin.open(event.whoClicked as Player)
                        }
                    }
                }
            })
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.add(ClickableItem.empty(ItemStack(Material.PAPER)))
    }

    override fun update(player: Player, contents: InventoryContents) {

    }

}