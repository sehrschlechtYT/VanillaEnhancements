package yt.sehrschlecht.vanillaenhancements.items

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class VEItemListener : Listener {
    private val itemManager: ItemManager = VanillaEnhancements.getPlugin().itemManager

    @EventHandler
    fun onPrepareItemCraft(event: PrepareItemCraftEvent) {
        val recipe = event.recipe ?: return
        if (handleEvent(event.inventory.matrix, shouldBlock = { it.blockUsageInCraftingRecipe(recipe) })) {
            event.inventory.result = null
        }
    }

    @EventHandler
    fun onPrepareAnvil(event: PrepareAnvilEvent) {
        if (handleEvent(event.inventory.contents, shouldBlock = { it.blockUsageInAnvilRecipe(event) })) {
            event.inventory.setItem(2, null)
        }
    }

    @EventHandler
    fun onPrepareSmithing(event: PrepareSmithingEvent) {
        if (handleEvent(event.inventory.contents, shouldBlock = { it.blockUsageInSmithingRecipe(event) })) {
            event.result = null
        }
    }

    @EventHandler
    fun onSelectTrade(event: TradeSelectEvent) {
        val recipe = event.merchant.getRecipe(event.index)
        if (handleEvent(event.inventory.contents, shouldBlock = { it.blockUsageForTrading(recipe) })) {
            event.inventory.setItem(2, null)
        }
    }

    private fun handleEvent(items: Array<ItemStack?>, shouldBlock: (VEItem) -> Boolean) : Boolean {
        // check if one of the ingredients is a custom item
        for (ingredient in items) {
            ingredient ?: continue
            val customItem = itemManager.findItem(ingredient) ?: continue
            if (!shouldBlock(customItem)) continue
            return true
        }
        return false
    }

    /* Events handled by the item classes */

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val item = event.item ?: return
        val customItem = itemManager.findItem(item) ?: return
        customItem.onInteract(event)
    }

    @EventHandler
    fun onCraft(event: CraftItemEvent) {
        val item = event.currentItem ?: return
        val customItem = itemManager.findItem(item) ?: return
        customItem.onCraft(event)
    }

    @EventHandler
    fun onDrop(event: PlayerDropItemEvent) {
        val item = event.itemDrop.itemStack
        val customItem = itemManager.findItem(item) ?: return
        customItem.onDrop(event)
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val item = event.currentItem ?: return
        val customItem = itemManager.findItem(item) ?: return
        customItem.onClick(event)
    }

}