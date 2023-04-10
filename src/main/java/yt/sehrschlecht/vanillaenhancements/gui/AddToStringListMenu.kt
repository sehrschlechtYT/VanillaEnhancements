package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.InventoryListener
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.config.options.StringListOption

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class AddToStringListMenu : InventoryProvider {

    companion object {
        fun getInventory(plugin: VanillaEnhancements, option: StringListOption, origin: SmartInventory): SmartInventory = SmartInventory.builder()
            .id("addToStringList")
            .type(InventoryType.ANVIL)
            .provider(AddToStringListMenu())
            .title("§lEnter a value to add")
            .manager(plugin.inventoryManager)
            .parent(origin) // ToDo set this for all menus
            .listener(InventoryListener(InventoryClickEvent::class.java) { event ->
                if (event.slot == 2) {
                    val item = event.currentItem
                    if (item != null && item.type == Material.PAPER) {
                        val meta = item.itemMeta
                        if (meta != null && meta.hasDisplayName()) {
                            val list = option.get().toMutableList()
                            list.add(meta.displayName)
                            option.set(list)
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