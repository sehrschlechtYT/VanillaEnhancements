package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import com.google.gson.annotations.Since
import org.bukkit.Bukkit
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityToggleGlideEvent
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.ticking.Tick
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class AlwaysFlying : VEModule(
    "Makes all entities always fly/glide like with an elytra.",
) {
    val applyToMobs = BooleanOption(true, "Controls whether mobs should forced to fly too.")

    override fun getKey(): String {
        return "always_flying"
    }

    @EventHandler
    fun onToggleGlide(event: EntityToggleGlideEvent) {
        event.isCancelled = true
    }

    @Tick(period = 20, executeNow = true)
    fun makeEntitiesGlide() {
        Bukkit.getServer().worlds.forEach worldLoop@{ world ->
            world?: return@worldLoop
            world.entities.forEach entityLoop@{ entity ->
                entity?: return@entityLoop
                if (entity !is LivingEntity) return@entityLoop
                if (!applyToMobs.get() && entity !is Player) return@entityLoop
                if (!entity.isGliding) entity.isGliding = true
            }
        }
    }

}