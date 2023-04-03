package yt.sehrschlecht.vanillaenhancements.modules.inbuilt

import org.bukkit.Material
import yt.sehrschlecht.vanillaenhancements.modules.GenReplaceModule


class IceGenReplace : GenReplaceModule(
    blockToReplace = Material.ICE,
    description = "Replaces water with the set block when freezing",
    since = "1.0",
) {

    override fun getKey(): String {
        return "ice_gen_replace"
    }

    override fun getName(): String {
        return "Replace generated ice"
    }

}