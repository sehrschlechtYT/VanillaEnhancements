package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.entity.Player

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
abstract class InventoryTickUpdater(private val tickInterval: Int) : InventoryProvider {
    override fun update(player: Player, contents: InventoryContents) {
        val state = contents.property("state", 0)
        contents.setProperty("state", state + 1)

        if (state % tickInterval == 0) {
            tick(player, contents)
        }
    }

    abstract fun tick(player: Player, contents: InventoryContents)
}