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
        fun InventoryContents.addBackButton(onClick: ((Player) -> SmartInventory?)? = null) {
            set(inventory().rows - 1, inventory().columns - 1, ClickableItem.of(
                ItemCreator(Material.IRON_DOOR) {
                    if (onClick == null) {
                        displayName("§cClose")
                    } else {
                        displayName("§cBack")
                    }
                }.build()
            ) click@{ event ->
                val player = event.whoClicked as Player
                onClick ?: return@click player.closeInventory()
                val inventory = onClick(player)
                inventory ?: return@click player.closeInventory()
                inventory.open(player)
            })
        }

        fun InventoryContents.fillBackground() {
            while (true) {
                val firstEmpty = firstEmpty() ?: break
                if (firstEmpty.isEmpty) return
                set(firstEmpty.get(), ClickableItem.empty(ItemCreator(Material.GRAY_STAINED_GLASS_PANE) {
                    displayName("§0")
                }.build()))
            }
        }

        fun String.removeColorCodes(): String {
            return this.replace("§[0-9a-fk-or]".toRegex(), "")
        }
    }

}