package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.content.InventoryContents
import org.bukkit.entity.Player

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
abstract class RecurrentInventoryInitializer(tickInterval: Int) : InventoryTickUpdater(tickInterval) {
    override fun tick(player: Player, contents: InventoryContents) {
        init(player, contents)
    }
}