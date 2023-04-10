package yt.sehrschlecht.vanillaenhancements.utils

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class SpigotExtensions {

    companion object {
        fun InventoryContents.addBackButton(onClick: (Player) -> SmartInventory? = { null }) {
            set(2, 8, ClickableItem.of(
                ItemCreator(Material.IRON_DOOR) {
                    displayName("§cClose")
                }.build()
            ) { event ->
                val player = event.whoClicked as Player
                val inventory = onClick(player)
                if (inventory == null) {
                    player.closeInventory()
                } else {
                    inventory.open(player)
                }
            })
        }
    }

}