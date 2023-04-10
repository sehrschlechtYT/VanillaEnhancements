package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.Material
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.config.options.StringListOption
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.PaginationType
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.paginateItems

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModifyStringListMenu(private val plugin: VanillaEnhancements, private val option: StringListOption, private val origin: SmartInventory) : InventoryProvider {

    companion object {
        fun getInventory(plugin: VanillaEnhancements, option: StringListOption, origin: SmartInventory): SmartInventory = SmartInventory.builder()
            .id("chooseDyeColor")
            .provider(ModifyStringListMenu(plugin, option, origin))
            .size(3, 9)
            .title("§lModify option values")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBackground()
        val items = option.get().map {
            ClickableItem.of(
                ItemCreator(Material.PAPER) {
                    displayName("§f§l${it}")
                    addLore("§9Right click to remove")
                }.build()
            ) { event ->
                if (!event.isRightClick) return@of
                val list = option.get().toMutableList()
                list.remove(it)
                option.set(list)
                init(player, contents)
            }
        }
        contents.paginateItems(items, player = player, paginationType = PaginationType.HORIZONTAL_7, noneItem = {
            displayName("§c§lNo values")
            addLongLore("There are no values yet for this option.", lineStart = "§c")
        }, inventoryGetter = { getInventory(plugin, option, origin) })
        contents.set(2, 0, ClickableItem.of(
            ItemCreator(Material.EMERALD) {
                displayName("§a§lAdd value")
                addLongLore("§fClick to add a new value to this option.")
            }.build()
        ) { AddToStringListMenu.getInventory(plugin, option, origin).open(player) })
        contents.addBackButton { origin }
    }

    override fun update(player: Player, contents: InventoryContents) {
        val state = contents.property("state", 0)
        contents.setProperty("state", state + 1)

        if (state % 5 == 0) {
            init(player, contents)
        }
    }

}