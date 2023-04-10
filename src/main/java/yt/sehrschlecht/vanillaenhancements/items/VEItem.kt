package yt.sehrschlecht.vanillaenhancements.items

import org.bukkit.Material
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.event.inventory.PrepareSmithingEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MerchantRecipe
import org.bukkit.inventory.Recipe
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.Display
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.Texture
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
abstract class VEItem(
    val vanillaItem: Material,
    val texture: Texture,
    val modelDisplay: Display? = null,
    val customModelData: Int = VanillaEnhancements.getPlugin().resourcePackManager.getNextCustomModelData(vanillaItem),
    val displayName: String,
    val key: String,
    val blockUsageInCraftingRecipe: (Recipe) -> Boolean = {true}, // whether to disable the usage of this item in crafting recipes
    val blockUsageInAnvilRecipe: (PrepareAnvilEvent) -> Boolean = {true}, // whether to disable the usage of this item in repairing recipes
    val blockUsageInSmithingRecipe: (PrepareSmithingEvent) -> Boolean = {true}, // whether to disable the usage of this item in smithing recipes
    val blockUsageForTrading: (MerchantRecipe) -> Boolean = {true} // whether to disable the usage of this item in trading
) {

    open fun createItem(): ItemCreator {
        return ItemCreator(vanillaItem) {
            customModelData(customModelData)
            displayName("§r$displayName")
            stringData(VanillaEnhancements.getPlugin(), VanillaEnhancements.getPlugin().itemManager.key.key, key)
        }
    }

    open fun createItem(modify: ItemCreator.() -> Unit): ItemCreator {
        return createItem().apply(modify)
    }

    open fun isItem(item: ItemStack) : Boolean {
        return item.type == vanillaItem && VanillaEnhancements.getPlugin().itemManager.findItem(item) == this
    }

    open fun onInteract(event: PlayerInteractEvent) {}
    open fun onCraft(event: CraftItemEvent) {}
    open fun onDrop(event: PlayerDropItemEvent) {}
    open fun onClick(event: InventoryClickEvent) {}

}