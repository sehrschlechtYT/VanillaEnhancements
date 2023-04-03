package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.Material
import yt.sehrschlecht.vanillaenhancements.modules.GenReplaceModule
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class StoneGenReplace : GenReplaceModule(
    blockToReplace = Material.STONE,
    description = "Replaces generated stone (water + lava) with the set block",
    since = "1.0",
) {

    override fun getKey(): String {
        return "stone_gen_replace"
    }

    override fun getName(): String {
        return "Replace generated stone"
    }

}