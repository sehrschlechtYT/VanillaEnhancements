package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Sheep
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.plugin.java.JavaPlugin
import yt.sehrschlecht.vanillaenhancements.config.options.DyeColorOption
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class DefaultSheepColor : VEModule(
    "Makes all sheep spawn with the set color.",
    "1.0",
    INBUILT,
    ModuleTag.APRIL_FOOLS_2023,
    ModuleTag.ENTITIES,
) {

    val color = DyeColorOption(DyeColor.WHITE, "The color all sheep will spawn with.") { _, newValue ->
        updateSheeps(
            newValue
        )
    }

    override fun getKey(): String {
        return "default_sheep_color"
    }

    override fun getDisplayItem(): Material {
        return Material.SHEEP_SPAWN_EGG
    }

    override fun onEnable() {
        updateSheeps(color.get())
    }

    fun updateSheeps(dyeColor: DyeColor) {
        loopEntities {
            if (it !is Sheep) return@loopEntities
            it.color = dyeColor
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onEntitySpawn(event: EntitySpawnEvent) {
        val entity = event.entity
        if (entity !is Sheep) return
        entity.color = color.get()
    }

    override fun getPlugin(): JavaPlugin {
        return veInstance
    }

}