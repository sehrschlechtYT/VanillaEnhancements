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
        val items = module.getItems()
        items.forEach { // ToDo: Add pagination
            contents.add(ClickableItem.of(it.createItem {
                displayName("§f§l${it.displayName}")
                addLore("§fBased off of ${ModuleUtils.getNameFromKey(it.vanillaItem.name)}")
                addLore("§9Right click to get")
            }.build()) { event ->
                if (event.isRightClick) {
                    player.inventory.addItem(it.createItem().build())
                    player.playSound(player.location, Sound.ENTITY_ITEM_PICKUP, 1f, 1f)
                    return@of
                }
                player.sendMessage("Clicked on item ${it.key}. TODO")
            })
        }
        contents.addBackButton{_ -> ModuleMenu.getInventory(plugin, module, sourceTag) }
        contents.fillBackground()
    }

}