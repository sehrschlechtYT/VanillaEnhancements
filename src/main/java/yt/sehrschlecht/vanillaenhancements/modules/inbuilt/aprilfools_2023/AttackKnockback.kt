package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import com.google.gson.annotations.Since
import org.bukkit.Bukkit
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import yt.sehrschlecht.vanillaenhancements.config.options.IntegerOption
import yt.sehrschlecht.vanillaenhancements.modules.VEModule


/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
class AttackKnockback : VEModule() {

    val percentage: IntegerOption = IntegerOption(100, "Multiply the knockback by this percentage", 0, 200)

    override fun getKey(): String {
        return "attack_knockback"
    }

    @EventHandler
    fun onAttack(event: EntityDamageByEntityEvent) {
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            if (event.damager is Projectile) {
                event.entity.velocity = event.damager.velocity.setY(0).normalize().multiply(percentage.get() / 100.0)
                return@Runnable
            }
            event.entity.velocity = event.damager.location.direction.setY(0).normalize().multiply(percentage.get() / 100.0)
        }, 1)
    }

}