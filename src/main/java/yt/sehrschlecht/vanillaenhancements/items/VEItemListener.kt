package yt.sehrschlecht.vanillaenhancements.items

import org.bukkit.Keyed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
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
        // check if the recipe is a vanilla recipe
        if (recipe !is Keyed) return
        if (!recipe.key.namespace.equals("minecraft", true)) return
        // check if one of the ingredients is a custom item
        for (ingredient in event.inventory.matrix) {
            ingredient ?: continue
            val customItem = itemManager.findItem(ingredient) ?: continue
            if (!customItem.disableVanillaCrafting) continue
            event.inventory.result = null
            return
        }
    }

}