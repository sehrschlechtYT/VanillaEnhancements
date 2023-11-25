package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import org.bukkit.Sound
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.CustomItemModule
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.paginateItems

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleCustomItemsMenu(private val plugin: VanillaEnhancements, private val module: CustomItemModule, private val sourceTag: ModuleTag) : UpdatingInventoryProvider(20) {

    companion object {
        fun getInventory(plugin: VanillaEnhancements, module: CustomItemModule, sourceTag: ModuleTag): SmartInventory =
            SmartInventory.builder()
                .id("moduleCustomItems")
                .provider(ModuleCustomItemsMenu(plugin, module, sourceTag))
                .size(3, 9)
                .title("§lVE - Items of \"${module.name}\"")
                .manager(plugin.inventoryManager)
                .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        val customItems = module.getItems()
        val items = customItems.map {
            ClickableItem.of(it.createItem {
                displayName("§f§l${it.displayName}")
                addLore("§f§oBased off of ${ModuleUtils.getNameFromKey(it.vanillaItem.name)}")
                addLore("§9§oLeft click for more information")
                addLore("§9§oRight click to get")
            }.build()) { event ->
                if (event.isRightClick) {
                    player.inventory.addItem(it.createItem().build())
                    player.playSound(player.location, Sound.ENTITY_ITEM_PICKUP, 1f, 1f)
                    return@of
                }
                CustomItemMenu.getInventory(plugin, it, contents.inventory()).open(player)
            }
        }

        contents.paginateItems(items, player = player, noneItem = {
            displayName("§c§lNo items found")
            addLore("§cThis module has no items")
        }, inventoryGetter = { getInventory(plugin, module, sourceTag) })

        contents.addBackButton{_ -> ModuleMenu.getInventory(plugin, module, sourceTag) }
        contents.fillBackground()
    }

}