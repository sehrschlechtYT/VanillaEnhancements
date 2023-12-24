package yt.sehrschlecht.vanillaenhancements.modules

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockFormEvent
import yt.sehrschlecht.vanillaenhancements.config.options.MaterialOption

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
abstract class GenReplaceModule(
    private val blockToReplace: Material,
    description: String? = null,
    since: String? = null,
    category: ModuleCategory,
    vararg tags: ModuleTag
) : VEModule(description, since, category, *tags, ModuleTag.GEN_REPLACE) {

    val block = MaterialOption(Material.BASALT, "Block to replace generated basalt with")

    @EventHandler(ignoreCancelled = true)
    fun onBlockForm(event: BlockFormEvent) {
        if (event.newState.type == blockToReplace) {
            event.newState.type = block.get()
        }
    }

    override fun getDisplayItem(): Material {
        return blockToReplace
    }

}
