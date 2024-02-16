package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.ticking.Tick
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class AlwaysThunder : VEModule(
    "Makes it always thunder in the world",
    "1.0",
    INBUILT,
    ModuleTag.APRIL_FOOLS_2023,
    ModuleTag.WORLD
) {

    override fun getKey(): String {
        return "always_thunder"
    }

    override fun getDisplayItem(): Material {
        return Material.LIGHTNING_ROD
    }

    @Tick(period = 100, executeNow = true)
    fun makeItThunder() {
        Bukkit.getServer().worlds.forEach { world ->
            if (world.environment != World.Environment.NORMAL) return@forEach
            world.setStorm(true)
            world.isThundering = true
        }
    }

    override fun getPlugin(): JavaPlugin {
        return veInstance
    }

}