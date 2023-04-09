package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.DyeColor
import org.bukkit.entity.Sheep
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntitySpawnEvent
import yt.sehrschlecht.vanillaenhancements.config.options.DyeColorOption
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class DefaultSheepColor : VEModule(
    "Makes all sheep spawn with the set color.",
    "1.0"
) {

    val color = DyeColorOption(DyeColor.WHITE, "The color all sheep will spawn with.")

    override fun getKey(): String {
        return "default_sheep_color"
    }

    override fun onEnable() {
        loopEntities {
            if (it !is Sheep) return@loopEntities
            it.color = color.get()
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onEntitySpawn(event: EntitySpawnEvent) {
        val entity = event.entity
        if (entity !is Sheep) return
        entity.color = color.get()
    }

}