package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.entity.Creeper
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.plugin.java.JavaPlugin
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class ChargedCreepers : VEModule(
    "Makes all creepers charged",
    "1.0",
    INBUILT
) {

    override fun getKey(): String {
        return "charged_creepers"
    }

    override fun onEnable() {
        loopEntities {
            if (it !is Creeper) return@loopEntities
            it.isPowered = true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onCreeperSpawn(event: EntitySpawnEvent) {
        if (event.entity is Creeper) {
            (event.entity as Creeper).isPowered = true
        }
    }

    override fun getPlugin(): JavaPlugin {
        return veInstance
    }

}