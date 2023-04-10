package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import yt.sehrschlecht.vanillaenhancements.modules.GenReplaceModule
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class CobblestoneGenReplace : GenReplaceModule(
    blockToReplace = Material.COBBLESTONE,
    description = "Replaces generated cobblestone (water + lava) with the set block",
    since = "1.0",
    category = INBUILT
) {

    override fun getKey(): String {
        return "cobblestone_gen_replace"
    }

    override fun getName(): String {
        return "Replace generated cobblestone"
    }

    override fun getPlugin(): JavaPlugin {
        return veInstance
    }

}